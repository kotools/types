# 📋 Architecture Decision Records

This directory collects the architectural decisions made while building
Kotools Types. Each record describes the context, the decision taken, the
rationale behind it, and its consequences.

## 📄 Index

| ADR                                                       | Title                                               | Status                   |
|-----------------------------------------------------------|-----------------------------------------------------|--------------------------|
| [ADR-001](ADR-001-integer-euclidean-division.md)          | Euclidean division for `Integer`                    | ✅ Accepted               |
| [ADR-002](ADR-002-integer-value-semantics.md)             | Value semantics for `Integer` arithmetic operations | ✅ Accepted               |
| [ADR-003](ADR-003-euclidean-division-platform-impl.md)    | Platform implementation of Euclidean division       | 🔄 Superseded by ADR-008 |
| [ADR-004](ADR-004-decimal-scaled-integer-repr.md)         | Scaled-integer representation for `Decimal`         | ✅ Accepted               |
| [ADR-005](ADR-005-decimal-canonical-form.md)              | Canonical form for `Decimal`                        | ✅ Accepted               |
| [ADR-006](ADR-006-decimal-division-exclusion.md)          | Exclusion of division from `Decimal` arithmetic     | ✅ Accepted               |
| [ADR-007](ADR-007-native-integer-sign-magnitude-repr.md)  | Sign-magnitude representation for `NativeInteger`   | ✅ Accepted               |
| [ADR-008](ADR-008-euclidean-division-platform-impl-v2.md) | Platform implementation of Euclidean division (v2)  | ✅ Accepted               |
| [ADR-009](ADR-009-default-serializer-serial-names.md)     | Explicit serial names for default serializers       | ✅ Accepted               |
| [ADR-010](ADR-010-error-message-format.md)                | Error message format for `org.kotools.types`        | ✅ Accepted               |
| [ADR-011](ADR-011-error-message-quote-escaping.md)        | String value quote escaping for `org.kotools.types` | ✅ Accepted               |
| [ADR-012](ADR-012-integer-conversion-range-errors.md)     | Out-of-range error handling for `Integer` conversions | ✅ Accepted             |

## 🔄 Superseding records

ADRs are immutable once accepted: a record describes a decision as it was
made, in its original context. When a later change makes a decision
outdated, it is **not** edited in place. Instead:

1. A new ADR is written documenting the updated decision, referencing the
   old ADR (e.g., "Supersedes ADR-XXX") and the change that motivated it.
2. The old ADR's row in the index above is updated to
   "🔄 Superseded by ADR-YYY". The old ADR file itself is left untouched.

[ADR-003](ADR-003-euclidean-division-platform-impl.md) →
[ADR-008](ADR-008-euclidean-division-platform-impl-v2.md) is the first
example of this convention.
