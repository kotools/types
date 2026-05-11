# 📐 `Integer` specifications

This document is normative. Any implementation of `Integer` must satisfy all
specifications described here.

## Domain assumptions

- `Integer` represents a mathematical integers.
- `Integer` operations are total and never overflow.
- Canonical decimal representation has no leading `+` and no leading zeros.

## Non-goals

Unlike Kotlin integer types (`Byte`, `Short`, `Int`, `Long`), `Integer` does not
model machine integers, overflow semantics, truncating division, IEEE-754
behavior.

## Motivations

### Integer overflow

**Problem:** Adding, subtracting, or multiplying Kotlin integer types uses
bounded machine arithmetic, which may overflow and produce mathematically
incorrect results.

```kotlin
val x = 9223372036854775807
val y = 10

check(x + y == -9223372036854775799) // instead of 9223372036854775817
check(-x - y == 9223372036854775799) // instead of -9223372036854775817
check(x * y == -10L) // instead of 92233720368547758070
```

**Solution:** The `Integer` type represents mathematical integers and performs
arithmetic without overflow.

```kotlin
val x: Integer = Integer.of(9223372036854775807)
val y: Integer = Integer.of(10)

check(x + y == Integer.parse("9223372036854775817"))
check(-x - y == Integer.parse("-9223372036854775817"))
check(x * y == Integer.parse("92233720368547758070"))
```

### Division by zero

**Problem:** Division by zero on Kotlin integer types has platform-dependent
behavior: throw an `ArithmeticException` on JVM and Native platforms, and return
`0` on JS platform.

```kotlin
// JVM and Native platforms

val quotient: Result<Int> = kotlin.runCatching { 12 / 0 }
val remainder: Result<Int> = kotlin.runCatching { 12 % 0 }

check(quotient.exceptionOrNull() is ArithmeticException)
check(remainder.exceptionOrNull() is ArithmeticException)
```

```kotlin
// JS platform

check(12 / 0 == 0)
check(12 % 0 == 0)
```

**Solution:** `Integer` defines consistent Euclidean division semantics across
all supported platforms. Dividing an `Integer` by zero always throws an
`ArithmeticException`.

```kotlin
 // Common code

val x: Integer = Integer.of(12)
val y: Integer = Integer.of(0)

val quotient: Result<Integer> = kotlin.runCatching { x quotient y }
val remainder: Result<Integer> = kotlin.runCatching { x modulo y }

check(quotient.exceptionOrNull() is ArithmeticException)
check(remainder.exceptionOrNull() is ArithmeticException)
```

### Euclidean remainder

**Problem**: Remainder operations on Kotlin integer types may produce negative
results, which is not compatible with Euclidean division and modular arithmetic.

```kotlin
check(-7 % 3 == -1)
```

**Solution**: Remainder operation on `Integer` is always non-negative.

```kotlin
val x = Integer.of(-7)
val y = Integer.of(3)

check(x modulo y == Integer.of(2))
```

## Formal specifications

### Creation from `Long`

```kotlin
class Integer {
  companion object {
    fun of(value: Long): Integer = TODO()
  }
}
```

- Creation from `Long` preserves canonical representation:
  `Integer.of(x).toString() == "$x"`.

### Parsing

```kotlin
class Integer {
  companion object {
    fun parse(value: String): Integer = TODO()
    fun parseOrNull(value: String): Integer? = TODO()
  }
}
```

- Parsing preserves canonical representation: if `s` is canonical, then
  `parse(s).toString() == s` and `parseOrNull(s).toString() == s`.
- Parsing ignores plus sign: `parse("+1").toString() == "1"` and
  `parseOrNull("+1").toString() == "1"`.
- Parsing removes leading zeros: `parse("01").toString() == "1"` and
  `parseOrNull("01").toString() == "1"`.
- Strings that don't represent an integer can't be parsed: if `s` is not a valid
  integer representation, `parse(s)` throws `NumberFormatException` and
  `parseOrNull(s)` returns `null`.
- `parseOrNull` is not available from Java code due to its non-explicit support
  for nullable types.
- Zero has unique decimal string representation: if `s = "0"`, then
  `parse(s).toString() == s`.
- Negative zero has no representation: `parse("-0").toString() == "0"`.

### Conversion to `String`

```kotlin
class Integer {
  override fun toString(): String = TODO()
}
```

- `toString` function always returns canonical decimal representation.

### Equality

```kotlin
class Integer {
  override fun equals(other: Any?): Boolean = TODO()
  override fun hashCode(): Int = TODO()
}
```

- Equal integers have equal hash codes: if `x == y`, then
  `x.hashCode() == y.hashCode()`.
- `Integer` is reflexive: `x == x`.
- `Integer` is symmetrical: if `x == y`, then `y == x`.
- `Integer` is transitive: if `x == y` and `y == z`, then `x == z`.

### Order

```kotlin
class Integer {
  operator fun compareTo(other: Integer): Int = TODO()
}
```

- Order is total: `x == y` | `x < y` | `x > y`.
- Order supports antisymmetry: if `x <= y` and `y <= x`, then `x == y`.
- Order is transitive: if `x <= y` and `y <= z`, then `x <= z`.
- Comparison is consistent with equality: if `x == y`, then
  `x.compareTo(y) == 0`.

### Absolute value

```kotlin
class Integer {
  fun absoluteValue(): Integer = TODO()
}
```

- Absolute value is always non-negative: `|x| >= 0`.
- Absolute value is definite: if `x == 0`, then `|x| == 0`.
- Absolute value is symmetric: `|x| == |-x|`.
- Absolute value has triangle inequality: `|x + y| <= |x| + |y|`.

### Negation

```kotlin
class Integer {
  operator fun unaryMinus(): Integer = TODO()
}
```

- Negation inverses sign:
  - if `x > 0`, then `-x < 0`.
  - if `x < 0`, then `-x > 0`.
- Zero negation is neutral: `-0 == 0`.
- Double negation is neutral: `-(-x) == x`.

### Addition

```kotlin
class Integer {
  operator fun plus(other: Integer): Integer = TODO()
}
```

- Addition is commutative: `x + y == y + x`.
- Addition is associative: `(x + y) + z == x + (y + z)`.
- `0` is additive identity: `x + 0 == x` and `0 + x == x`.
- Addition has inverse element: `x + (-x) == 0` and `(-x) + x == 0`.
- Addition has unique inverse element: if `x + y == 0`, then `x == -y`.
- Addition satisfies cancellation: if `x + z == y + z`, then `x == y`.
- Addition is consistent with equality: if `x == y`, then `x + z == y + z`.
- Addition preserves order: if `x <= y`, then `x + z <= y + z`.
- Addition distributes negation: `-(x + y) == (-x) + (-y)`.

### Subtraction

```kotlin
class Integer {
  operator fun minus(other: Integer): Integer = TODO()
}
```

- Subtraction definition: `x - y == x + (-y)`.
- `0` is right-subtractive identity: `x - 0 == x`.
- Subtraction has self-annihilation: `x - x == 0`.
- Subtraction is consistent with equality: if `x == y`, then `x - z == y - z`.

### Multiplication

```kotlin
class Integer {
  operator fun times(other: Integer): Integer = TODO()
}
```

- Multiplication is commutative: `x * y == y * x`.
- Multiplication is associative: `(x * y) * z == x * (y * z)`.
- `1` is multiplicative identity: `x * 1 == x` and `1 * x == x`.
- `0` annihilates multiplication: `x * 0 == 0` and `0 * x == 0`.
- Multiplication is distributive over addition:
  `x * (y + z) == (x * y) + (x * z)`, and `(x + y) * z == (x * z) + (y * z)`.
- Multiplication satisfies cancellation if no zero involved: if `z != 0` and
  `x * z == y * z`, then `x == y`.
- Multiplication is consistent with equality: if `x == y`, then
  `x * z == y * z`.
- Multiplication preserves order:
  - if `z > 0` and `x <= y`, then `x * z <= y * z`.
  - if `z < 0` and `x <= y`, then `x * z >= y * z`.

### Euclidean division

```kotlin
class Integer {
  infix fun quotient(other: Integer): Integer = TODO()
  infix fun modulo(other: Integer): Integer = TODO()
}
```

- Euclidean division's definition: if `y != 0`, then
  `x == y * (x quotient y) + (x modulo y)`.
- Euclidean division is stable in case of dividend smaller than divisor: if
  `0 <= x < |y|`, then `x quotient y == 0` and `x modulo y == x`.
- Euclidean division is exact: if `x == y * k`, then `x quotient y == k` and
  `x modulo y == 0`.
- Euclidean division is stable under modular addition:
  `(x + k * y) modulo y == x modulo y`.
- Quotient of Euclidean division is unique: if `x == y * q1 + r1`,
  `x == y * q2 + r2`, `0 <= r1 < |y|`, `0 <= r2 < |y|`, then `q1 == q2` and
  `r1 == r2`.
- Quotient of Euclidean division is monotonic with positive divisor: if `z > 0`
  and `x <= y`, then `x quotient z <= y quotient z`.
- Remainder of Euclidean division is always non-negative:
  `0 <= (x modulo y) < |y|`.
- Remainder of Euclidean division is independent of sign:
  `x modulo y == x modulo (-y)`.
- Remainder of Euclidean division is idempotent:
  `(x modulo y) modulo y == x modulo y`.
- Quotient and remainder of Euclidean division are compatible:
  `x modulo y == x - y * (x quotient y)`.
- `0` is stable under Euclidean division: `0 quotient x == 0` and
  `0 modulo x == 0`.
- Euclidean division by `0` is undefined: `x quotient 0` and `x modulo 0` throws
  `ArithmeticException`.
- `1` is a special divisor in Euclidean division: `x quotient 1 == x` and
  `x modulo 1 == 0`.
- `-1` is a special divisor in Euclidean division: `x quotient (-1) == -x` and
  `x modulo (-1) == 0`.

### Sanity checks

```
1 + 1 == 2
1 * 0 == 0
(-1) + 1 == 0
2 * 3 == 6
0 - 5 == -5
7 == 3 * 2 + 1
6 == 3 * 2 + 0
1 == 2 * 0 + 1
-1 == 2 * (-1) + 1
-7 == 3 * (-3) + 2
7 == -3 * (-2) + 1
-7 == -3 * 3 + 2
```
