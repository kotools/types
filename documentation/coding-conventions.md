# 🧹 Coding conventions

## 🔢 Arithmetic operations ordering

Arithmetic operations on [`Integer`][Integer] and its refined variants (e.g.
[`NonZeroInteger`][NonZeroInteger], [`NonNegativeInteger`][NonNegativeInteger],
[`NonPositiveInteger`][NonPositiveInteger]) are declared in the following
fixed order:

1. `unaryMinus`
2. `plus`
3. `minus`
4. `times`
5. `div`
6. `rem`

When a type implements only a subset of these operations, the relative order
above is preserved (e.g. a type with only `unaryMinus` and `times` declares
`unaryMinus` first).

Overloads of the same operation for different operand types stay adjacent to
one another, ordered consistently with how their operand type itself relates
to other refined types (e.g. `times(NonNegativeInteger)` before
`times(NonPositiveInteger)` on `NonNegativeInteger`, mirroring the type's own
position relative to `NonPositiveInteger`).

This ordering applies consistently across production code, KDoc samples, and
tests.

<!----------------------------------- Links ----------------------------------->

[Integer]: ../subprojects/library/src/commonMain/kotlin/org/kotools/types/number/Integer.kt
[NonZeroInteger]: ../subprojects/library/src/commonMain/kotlin/org/kotools/types/number/NonZeroInteger.kt
[NonNegativeInteger]: ../subprojects/library/src/commonMain/kotlin/org/kotools/types/number/NonNegativeInteger.kt
[NonPositiveInteger]: ../subprojects/library/src/commonMain/kotlin/org/kotools/types/number/NonPositiveInteger.kt
