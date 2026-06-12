# 🏷️ ADR-011: String value quote escaping for `org.kotools.types`

This document records how [`errorMessage`][ErrorMessage] (see [ADR-010])
escapes `String` values that themselves contain a single quote.

## 🤔 Context

[ADR-010] wraps `String` offending values in single quotes (`'...'`) as a
visual delimiter, e.g. `Invalid email address: 'abc'`.

If the offending value itself contains a `'`, this delimiter becomes
ambiguous. For example, [`EmailAddress`][EmailAddress] accepts RFC 5322
quoted-string local parts, so a value like `"o'brien"@example.com` produces:

```
Invalid email address: 'o'brien@example.com'
```

It's unclear here where the offending value starts and ends.

## ✅ Decision

[`errorMessage`][ErrorMessage] escapes any `'` character within a `String`
value with a backslash (`\'`) before wrapping it in single quotes.

| Input value           | Resulting message                               |
|------------------------|--------------------------------------------------|
| `o'brien@example.com` | `Invalid email address: 'o\'brien@example.com'` |

## 🔗 Consequences

- [`errorMessage`][ErrorMessage]'s `String` branch escapes `'` as `\'`
  before wrapping the value in single quotes.
- This applies to every `org.kotools.types.*` declaration that builds its
  error messages with [`errorMessage`][ErrorMessage], without requiring any
  change at call sites.

<!----------------------------------- Links ----------------------------------->

[ADR-010]: ADR-010-error-message-format.md
[ErrorMessage]: ../../subprojects/internal/src/commonMain/kotlin/org/kotools/types/internal/ErrorMessage.kt
[EmailAddress]: ../../subprojects/library/src/commonMain/kotlin/org/kotools/types/EmailAddress.kt
