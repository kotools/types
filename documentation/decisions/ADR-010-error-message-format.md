# 🏷️ ADR-010: Error message format for `org.kotools.types`

This document records the message format used by exceptions thrown by
declarations in the `org.kotools.types` package and its sub-packages.

## 🤔 Context

Error messages thrown by `org.kotools.types.*` declarations had inconsistent
formats:

- `Decimal.Companion.parse` and
  `Integer.Companion.parse` put the offending value first, followed
  by a description and a trailing period:
  `"\"abc\" is not a valid integer."`.
- `Integer.div` and `Integer.rem` repeated the type name
  in a sentence ending with a period: `"Integer can't be divided by zero."`.
- `EmailAddressRegex.Companion.alphabetic` and
  `EmailAddressRegex.Companion.alphanumeric` put the
  offending value first: `"'<pattern>' is invalid for validating email
  addresses."`.
- The `org.kotools.types.kotlinx.serialization`
  serializers used a `(was: <value>)` parenthetical:
  `"Invalid email address (was: <text>)."`.

This made messages harder to scan consistently and inconsistent with each
other, despite describing the same kind of problem (an invalid value).

## ✅ Decision

Error messages thrown by `org.kotools.types.*` declarations follow a
`<description>: <value>` structure:

- The constraint description comes first, the offending value last,
  separated by `: `.
- The first letter is capitalized, and the message has **no trailing
  period**.
- **String** offending values are wrapped in single quotes (`'...'`) as a
  visual delimiter. **Numeric** offending values are left unquoted, since
  they are self-delimiting.
- If there's no variable offending value to report (e.g. division by zero,
  where the divisor is always the constant `0`), the value is omitted
  entirely — just the capitalized, period-free description.

| Declaration                                                                                                                     | Before                                                     | After                                        |
|---------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------|----------------------------------------------|
| `Decimal.Companion.parse`                                                                                            | `"\"abc\" is not a valid decimal."`                        | `"Invalid decimal representation: 'abc'"`    |
| `Integer.Companion.parse`                                                                                            | `"\"abc\" is not a valid integer."`                        | `"Invalid integer representation: 'abc'"`    |
| `Integer.div` / `Integer.rem`                                                                             | `"Integer can't be divided by zero."`                      | `"Division by zero"`                         |
| `EmailAddressRegex.Companion.alphabetic` / `EmailAddressRegex.Companion.alphanumeric` | `"'<pattern>' is invalid for validating email addresses."` | `"Invalid email address regex: '<pattern>'"` |
| `org.kotools.types.kotlinx.serialization` (`EmailAddress`)                                                 | `"Invalid email address (was: <text>)."`                   | `"Invalid email address: '<text>'"`          |
| `org.kotools.types.kotlinx.serialization` (`EmailAddressRegex`)                                            | `"Invalid email address regex (was: <text>)."`             | `"Invalid email address regex: '<text>'"`    |

The `Decimal` constructor's `"Negative decimal scale: <scale>"`
check already followed this structure and is unchanged.

## 🔗 Consequences

- New error messages added to `org.kotools.types.*` declarations must follow
  this `<description>: <value>` structure.
- The `EmailAddressRegex.Companion.alphabetic`, `alphanumeric` checks and the
  `org.kotools.types.kotlinx.serialization` `EmailAddressRegex` serializer now
  share the same `"Invalid email address regex: '<value>'"` wording.
- This convention applies only to `org.kotools.types.*`. The older
  `kotools.types.*` package has its own message conventions via
  `kotools.types.internal.ErrorMessage`, which are out of scope for this
  decision.
