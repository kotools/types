# 📋 Architecture Decision Records

A collection of architectural decisions made while building Kotools Types. Each
record describes the context, the decision taken, the rationale behind it, and
its consequences.

## 📜 Conventions

### 🔄 Superseding records

ADRs are immutable once accepted: a record describes a decision as it was
made, in its original context. When a later change makes a decision
outdated, it is **not** edited in place. Instead:

1. A new ADR is written documenting the updated decision, referencing the
   old ADR (e.g., "Supersedes ADR-XXX") and the change that motivated it.
2. The old ADR's entry in the index above is updated to "🔄 Superseded by
   ADR-YYY". The old ADR file itself is left untouched.

### 🚫 No links to source files

ADRs must not link to source files. Source files are mutable implementation
details that get renamed, moved, or restructured over time. An ADR is a
historical record of a decision at a point in time. A link to a source file may
keep resolving after such a change while silently pointing to code that no
longer matches what the ADR described, creating a false impression of accuracy.

Cross-references in an ADR must instead target either:

- another ADR, by number and title (e.g. "Supersedes ADR-003"), or
- the published API documentation, when a link to a type's current definition is
  genuinely needed.

## 📄 Index

- [ADR-001]: Euclidean division for `Integer` (✅ Accepted)
- [ADR-002]: Value semantics for `Integer` arithmetic operations (✅ Accepted)
- [ADR-003]: Platform implementation of Euclidean division (🔄 Superseded by
  [ADR-008])
- [ADR-004]: Scaled-integer representation for `Decimal` (✅ Accepted)
- [ADR-005]: Canonical form for `Decimal` (✅ Accepted)
- [ADR-006]: Exclusion of division from `Decimal` arithmetic (✅ Accepted)
- [ADR-007]: Sign-magnitude representation for `NativeInteger` (✅ Accepted)
- [ADR-008]: Platform implementation of Euclidean division (v2) (✅ Accepted)
- [ADR-009]: Explicit serial names for default serializers (✅ Accepted)
- [ADR-010]: Error message format for `org.kotools.types` (✅ Accepted)
- [ADR-011]: String value quote escaping for `org.kotools.types` (✅ Accepted)
- [ADR-012]: 10⁹-chunk decimal I/O for `NativeInteger` (✅ Accepted)
- [ADR-013]: Exclusion of additive operations from `NonZeroInteger` arithmetic
  (✅ Accepted)
- [ADR-014]: Exclusion of subtraction from `NonNegativeInteger` arithmetic (🔄
  Superseded by [ADR-019])
- [ADR-015]: Typed-divisor Euclidean division for `Integer` (✅ Accepted)
- [ADR-016]: Cross-type closure for `NonNegativeInteger` arithmetic (✅ Accepted)
- [ADR-017]: Exclusions from `NonPositiveInteger` arithmetic (✅ Accepted)
- [ADR-018]: Removal of `Integer`-accepting division from `Integer`
  (✅ Accepted)
- [ADR-019]: Exclusion of subtraction from `NonNegativeInteger` arithmetic - v2
  (✅ Accepted)
- [ADR-020]: Release type rules for stable and experimental declarations (✅
  Accepted)

<!----------------------------------- Links ----------------------------------->

[ADR-001]: ADR-001-integer-euclidean-division.md
[ADR-002]: ADR-002-integer-value-semantics.md
[ADR-003]: ADR-003-euclidean-division-platform-impl.md
[ADR-004]: ADR-004-decimal-scaled-integer-repr.md
[ADR-005]: ADR-005-decimal-canonical-form.md
[ADR-006]: ADR-006-decimal-division-exclusion.md
[ADR-007]: ADR-007-native-integer-sign-magnitude-repr.md
[ADR-008]: ADR-008-euclidean-division-platform-impl-v2.md
[ADR-009]: ADR-009-default-serializer-serial-names.md
[ADR-010]: ADR-010-error-message-format.md
[ADR-011]: ADR-011-error-message-quote-escaping.md
[ADR-012]: ADR-012-native-decimal-io-chunking.md
[ADR-013]: ADR-013-nonzerointeger-additive-exclusion.md
[ADR-014]: ADR-014-nonnegativeinteger-subtractive-exclusion.md
[ADR-015]: ADR-015-integer-typed-divisor-division.md
[ADR-016]: ADR-016-nonnegativeinteger-cross-type-closure.md
[ADR-017]: ADR-017-nonpositiveinteger-exclusions.md
[ADR-018]: ADR-018-integer-division-removal.md
[ADR-019]: ADR-019-nonnegativeinteger-subtractive-exclusion-v2.md
[ADR-020]: ADR-020-release-type-rules.md
