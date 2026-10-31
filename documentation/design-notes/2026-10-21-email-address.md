# 📧 Design `EmailAddress` in `org.kotools.types.net`

## 🧐 Problem

Email addresses are usually plain strings. Every caller validates them with its
own rules.

- [RFC 5322 section 3.4.1][rfc5322-3.4.1] is too permissive for forms. Most
  simple regular expressions are too strict for real addresses.
- `John@Example.com` and `john@example.com` designate the same mailbox on
  almost every provider. Strings compare them as different.

The repository has two experimental declarations in `subprojects/library`:

- `org.kotools.types.EmailAddress` wraps any text matching a regular
  expression. Equality compares the raw text.
- `org.kotools.types.EmailAddressRegex` defaults to `^\S+@\S+\.\S+$`. This
  pattern accepts invalid values such as `a@@b.c` or `(a)@b.-c`.

Callers:

- `SerializersModule` in `subprojects/kotlinx-serialization`, with its tests,
  its samples and its README.
- Java and Kotlin samples of both types in `subprojects/library`.

Neither the JDK nor the Kotlin standard library offers an email address type.
`javax.mail.internet.InternetAddress` belongs to Jakarta Mail, not to the JDK.

## ⚖️ Decisions

1. Remove `EmailAddress` and `EmailAddressRegex` from `org.kotools.types`,
   without a deprecation cycle. Remove their usages from `SerializersModule`
   without migrating them. The new type gets no serializer while it is
   experimental. A future design note covers it once the type is stable.
   Source: user.
2. Put the new type in the package `org.kotools.types.net`. Source: existing
   design.
3. Introduce the type in the experimental stage, with
   `@ExperimentalKotoolsTypesApi`. Source: existing design.
4. Use the dot-atom form for the local part, with the full `atext` set. Source:
   [RFC 5322 section 3.4.1][rfc5322-3.4.1],
   [RFC 5322 section 3.2.3][rfc5322-3.2.3] and the
   [HTML Standard][html-email].
5. Require a domain with at least two dot-separated labels. This rejects
   `user@localhost`. Source: existing design.
6. Limit the local part to 64 characters. Source:
   [RFC 5321 section 4.5.3.1.1][rfc5321-4.5.3.1.1].
7. Limit each domain label to 63 characters. Source:
   [RFC 1035 section 2.3.4][rfc1035-2.3.4].
8. Limit the whole address to 254 characters. A forward-path has at most 256
   characters, including its angle brackets. Source:
   [RFC 5321 section 4.5.3.1.3][rfc5321-4.5.3.1.3]. The figure 254 is derived
   by subtracting the two brackets.
9. Convert the local part and the domain to lowercase when parsing. Only ASCII
   letters change, whatever the default locale. Domain names are
   case-insensitive. The RFC says local parts are case-sensitive but discourages
   relying on it. This choice deviates from the strict reading. Source:
   [RFC 5321 section 2.4][rfc5321-2.4], [RFC 5321 section 4.1.2][rfc5321-4.1.2]
   and existing design.
10. Apply no other normalization. Provider rules, such as ignoring dots or
    `+tag` suffixes, are out of scope. Source: existing design.
11. Compare addresses structurally on their local part and domain. Source:
    existing design.
12. Provide a throwing `parse` function and a `parseOrNull` function. The
    throwing one fails with an `IllegalArgumentException`. Source: repository
    precedent (`Integer.parse`).
13. Reject these forms. Source: existing design.
    - Quoted-string local parts, such as `"john doe"@example.com`.
    - Comments and folding whitespace.
    - IP address literals as domains, such as `user@[192.0.2.1]`.
    - Domains whose last label has no letter, such as `user@192.0.2.1`.
    - Display names and angle brackets.
    - Obsolete syntaxes, groups and address lists.
    - Non-ASCII characters in the local part or in the domain.
14. Defer internationalized addresses. An internationalized domain name must
    use its ASCII form, such as `xn--bcher-kva.example`. Supporting them later
    only extends the set of valid values. Source:
    [RFC 6531][rfc6531] and existing design.
15. Never trim whitespace in `parse` and `parseOrNull`. Surrounding whitespace
    makes the value invalid. Callers trim themselves. Source: user.
16. Use a fixed message for `IllegalArgumentException`. It never echoes the
    input, because email addresses are personal data and exceptions reach many
    sinks. Callers can log the input themselves. Partial masking is not an
    option, because a masked address is still personal data. Source: user.
17. Return the full address from `toString`, without redaction. The text is the
    only textual form of the value, and callers need it to send mail or render
    a page. The KDoc warns callers not to log it blindly. Source: user.

## 🧩 API

```kotlin
package org.kotools.types.net

@ExperimentalKotoolsTypesApi
public class EmailAddress private constructor(
    public val localPart: String,
    public val domain: String
) {
    public companion object {
        @JvmStatic
        public fun parse(value: String): EmailAddress

        @JvmSynthetic
        public fun parseOrNull(value: String): EmailAddress?
    }

    final override fun equals(other: Any?): Boolean
    final override fun hashCode(): Int
    final override fun toString(): String
}
```

A valid email address follows this grammar, written in Wirth-style EBNF. `ALPHA`
and `DIGIT` are ASCII letters and digits.

```
email-address = local-part "@" domain .
local-part = atext { atext } { "." atext { atext } } .
domain = label "." label { "." label } .
label = let-dig [ { ldh } let-dig ] .
atext = ALPHA | DIGIT | "!" | "#" | "$" | "%" | "&" | "'" | "*" | "+" | "-"
    | "/" | "=" | "?" | "^" | "_" | "`" | "{" | "|" | "}" | "~" .
let-dig = ALPHA | DIGIT .
ldh = ALPHA | DIGIT | "-" .
```

The local part can't start or end with a dot. It can't contain two consecutive
dots. A label can't start or end with a hyphen. The last label must contain at
least one letter, so an all-numeric domain such as `192.0.2.1` is invalid.

Parsing normalizes the casing. The original casing is not preserved.

```kotlin
val address: EmailAddress = EmailAddress.parse("John.Doe@Example.COM")
check(address.localPart == "john.doe")
check(address.domain == "example.com")
check(address.toString() == "john.doe@example.com")
```

Both functions produce equal values for equal normalized text.

```kotlin
val x: EmailAddress = EmailAddress.parse("John@Example.COM")
val y: EmailAddress? = EmailAddress.parseOrNull("john@example.com")
check(x == y)
check(EmailAddress.parseOrNull("john@localhost") == null)
check(EmailAddress.parseOrNull("john@192.0.2.1") == null)
```

`parse` throws an `IllegalArgumentException` for an invalid value.
`parseOrNull` returns `null` instead. It is hidden from Java.

`toString` returns the local part and the domain separated by `@`, in
lowercase. Parsing this result gives an address equal to the original. It
equals the parsed text only when that text is already in lowercase.

## 🕳️ Deep-module check

- **`EmailAddress`** hides the grammar, the three length limits and the
  normalization. Its interface is two strings and two factories.
- **`parse`** hides the multistep validation and the lowercase conversion.
- **`parseOrNull`** hides the same validation behind a nullable return. It is
  not a pass-through of `parse`, because callers would need exception handling
  otherwise.
- **`localPart` and `domain`** expose the two parts without further parsing.
  Callers would otherwise split the string themselves.
- **`equals`, `hashCode` and `toString`** carry the structural equality
  contract on the normalized form.

## ⚠️ Risks

- The grammar is stricter than the old pattern. Values accepted before, such as
  `a@b`, become invalid.
- Lowercasing loses the original casing. A strict reading of the RFC treats
  local parts as case-sensitive. Few providers do.
- Internationalized addresses are rejected until a later version.
- Removing the old types breaks code that uses them, and drops the serializers
  for them. Both types are experimental, so this is allowed.
- The new type has no serializer until it is stable.
- `toString` returns personal data. Callers can leak it by logging the value.

## 🛠️ Implementation hand-off

- Implement `EmailAddress` in `subprojects/library` under
  `org.kotools.types.net`. Ship tests, Kotlin and Java samples, and the ABI dump
  in the same change.
- Remove `EmailAddress` and `EmailAddressRegex` from `org.kotools.types`, with
  their tests, Java samples and ABI entries.
- Remove their usages from `SerializersModule`: the serializers, the tests, the
  samples and the README.

## 📚 Sources

- [RFC 1035 section 2.3.4][rfc1035-2.3.4]: label size limit of 63 octets.
- [RFC 5321 section 2.4][rfc5321-2.4]: case sensitivity of local parts and
  domains.
- [RFC 5321 section 4.1.2][rfc5321-4.1.2]: advice to avoid case-sensitive local
  parts.
- [RFC 5321 section 4.5.3.1.1][rfc5321-4.5.3.1.1]: local part limit of 64
  octets.
- [RFC 5321 section 4.5.3.1.3][rfc5321-4.5.3.1.3]: path limit of 256 octets.
- [RFC 5322 section 3.2.3][rfc5322-3.2.3]: `atext` character set.
- [RFC 5322 section 3.4.1][rfc5322-3.4.1]: dot-atom addr-spec.
- [RFC 6531][rfc6531]: internationalized email, deferred.
- [HTML Standard][html-email]: valid email address.

The 254-character limit is derived. The content of RFC 5322 sections and of the
HTML Standard comes from the previous design, not from a new fetch.

<!----------------------------------- Links ----------------------------------->

[html-email]: https://html.spec.whatwg.org/multipage/input.html#valid-e-mail-address
[rfc1035-2.3.4]: https://www.rfc-editor.org/rfc/rfc1035#section-2.3.4
[rfc5321-2.4]: https://www.rfc-editor.org/rfc/rfc5321#section-2.4
[rfc5321-4.1.2]: https://www.rfc-editor.org/rfc/rfc5321#section-4.1.2
[rfc5321-4.5.3.1.1]: https://www.rfc-editor.org/rfc/rfc5321#section-4.5.3.1.1
[rfc5321-4.5.3.1.3]: https://www.rfc-editor.org/rfc/rfc5321#section-4.5.3.1.3
[rfc5322-3.2.3]: https://www.rfc-editor.org/rfc/rfc5322#section-3.2.3
[rfc5322-3.4.1]: https://www.rfc-editor.org/rfc/rfc5322#section-3.4.1
[rfc6531]: https://www.rfc-editor.org/rfc/rfc6531
