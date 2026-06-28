# 🏷️ ADR-012: 10⁹-chunk decimal I/O for `NativeInteger`

This document records the chunk size chosen for batching decimal digit
processing in the Kotlin/Native implementation of `Integer`.

## 🤔 Context

`NativeInteger` (see [ADR-007]) stores its magnitude as a `UIntArray` in base
2³² (little-endian limbs). Before this decision, `parse()` and `toString()`
processed one decimal digit per iteration — _d_ iterations for a _d_-digit
number — making both operations O(_d_ × _n\_limbs_).

Batching multiple digits into a single chunk reduces the number of iterations to
⌈_d_ / chunk\_size⌉ while keeping the per-iteration cost roughly the same,
yielding a near-linear speed-up proportional to the chunk size.

## ✅ Decision

Batch decimal I/O in chunks of **10⁹** (one billion) — i.e., nine decimal digits
at a time.

The constraint is that every chunk value must fit in a `UInt`:

```
max chunk value = 999_999_999 (nine 9s)
UInt max        = 4_294_967_295 (= 2³² − 1)
999_999_999 < 4_294_967_295  ✓
```

10⁹ is the largest power of 10 that satisfies this constraint:

```
10⁹  = 1_000_000_000 < 2³² − 1  ✓  (chosen)
10¹⁰ = 10_000_000_000 > 2³² − 1  ✗  (does not fit)
```

Using `UInt` for the chunk avoids a `Long` intermediate and keeps the arithmetic
consistent with the rest of the magnitude helper functions.

## 🔗 Consequences

- A file-level `private val BILLION: UIntArray = uintArrayOf(1_000_000_000u)`
  constant holds the base used by `parse()`.
- `parse()` processes the digit string in groups of nine, multiplying the
  accumulated magnitude by `BILLION` and adding each chunk — ⌈_d_/9⌉ iterations
  instead of _d_.
- A `divideByBillion()` extension on `UIntArray` mirrors `divideByTen()` but
  divides by 10⁹, returning a `(quotient, remainder)` pair where the remainder
  is at most 999,999,999 and therefore fits in `Int`.
- `toString()` collects nine-digit chunks via `divideByBillion()` and formats
  them with `padStart(9, '0')` to preserve leading zeros in interior groups —
  ⌈_d_/9⌉ iterations instead of _d_, with no final `reversed()` call.
- `divideByTen()` is removed as dead code.
- Iteration count for both operations drops by ~9× for large numbers.

<!----------------------------------- Links ----------------------------------->

[ADR-007]: ADR-007-native-integer-sign-magnitude-repr.md
