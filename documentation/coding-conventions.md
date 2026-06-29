# 🧹 Coding conventions

## 📦 Deep modules in the public API

A public declaration earns its place in the API only when it is a **deep
module** (Ousterhout, _A Philosophy of Software Design_): its interface must be
meaningfully simpler than the functionality it hides.

**Depth = functionality provided / interface complexity.**

A declaration qualifies when it hides at least one of:

- A type's valid range or invariant — callers produce valid values without
  knowing the bounds.
- A multistep construction, validation, or normalization algorithm.
- A platform-specific or implementation-specific detail.

**Antipatterns to reject:**

| Anti-pattern            | Symptom                                                                             |
|-------------------------|-------------------------------------------------------------------------------------|
| Pass-through            | Delegates to one or two existing calls with no added behaviour.                     |
| Name-exposes-everything | The name already reveals what would be hidden, so nothing is truly abstracted away. |
| Shallow convenience     | One- or two-line body behind a trivially simple interface.                          |

**Test before proposing:** write the equivalent user code without the new
declaration. If the result is short and self-explanatory, the candidate is
likely shallow and should be rejected.

**Examples from this codebase:**

- `StrictlyPositiveInt.random()` — *deep*: hides the valid range
  `1..Int.MAX_VALUE`; callers produce a valid value without knowing the bounds.
- `Integer.fromLong(Long)` — *deep*: hides `PlatformInteger` construction and
  canonical-form enforcement.
- `Integer.randomInIntRange()` — *rejected (shallow)*: one-line pass-through;
  the name already states the range, so nothing is abstracted away.

## 🔢 Arithmetic operations ordering

Arithmetic operations on `Integer` and its refined variants (e.g.
`NonZeroInteger`) are declared in the following fixed order:

1. `unaryMinus`
2. `plus`
3. `minus`
4. `times`
5. `div`
6. `rem`

When a type implements only a subset of these operations, the relative order
above is preserved (e.g. a type with only `unaryMinus` and `times` declares
`unaryMinus` first).

Overloads of the same operation for different operand types stay adjacent to one
another, ordered consistently with how their operand type itself relates to
other refined types (e.g. `times(NonNegativeInteger)` before
`times(NonPositiveInteger)` on `NonNegativeInteger`, mirroring the type's own
position relative to `NonPositiveInteger`).

This ordering applies consistently across production code, KDoc samples, and
tests.
