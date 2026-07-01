# ➗ Why `-7 % 2` should never be `-1`

A previous article covered how Kotlin Multiplatform's built-in integer division
behaves inconsistently across platforms when the divisor is zero. This one
covers a different inconsistency in the _same_ operation: what the remainder
looks like when the operands have different signs.

## 🐛 The problem

```kotlin
val remainder = -7 % 2
check(remainder == -1) // passes — but is this what you expected?
```

Kotlin's `%` truncates toward zero, so a negative dividend produces a negative
remainder. That's mathematically valid — there's more than one consistent
definition of integer division — but it routinely surprises callers who expect a
remainder to behave like "the reading on a clock", always non-negative. Code
that indexes into an array with `value % size` and forgets this will crash on
negative inputs, in production, on the one input path nobody tested.

## 🤔 Why it happens

Three definitions of integer division exist for negative operands, and they
disagree only when the signs of the dividend and divisor differ:

- **Truncated division** (Kotlin / Java / C default): remainder has the same
  sign as the dividend.
- **Floored division**: remainder has the same sign as the divisor.
- **Euclidean division**: remainder is always non-negative, regardless of either
  operand's sign.

Truncating division is what the platform gives you by default, since it matches
how CPU division instructions work. It is not, however, the definition most
people learn as "division with remainder" in school — that's Euclidean division,
where `0 <= remainder < |divisor|` always holds.

## ✅ The fix: `Integer` uses Euclidean division, and it's total

`Integer.div` and `Integer.rem` always satisfy `0 <= remainder < |divisor|`,
regardless of the sign of either operand:

```kotlin
@OptIn(ExperimentalKotoolsTypesApi::class)
fun main() {
    val x: Integer = Integer.fromLong(-7)
    val y: NonZeroInteger = NonZeroInteger.fromLong(2)

    val quotient: Integer = x / y
    val remainder: NonNegativeInteger = x % y

    check(quotient == Integer.fromLong(-4))
    check(remainder == NonNegativeInteger.fromLong(1)) // never negative
    check(x == quotient * y.toInteger() + remainder.toInteger())
}
```

Notice the divisor's type: `NonZeroInteger`, not `Integer`. Because the type
system already rules out a zero divisor, `div` / `rem` no longer need to throw
an `ArithmeticException` or return `null` for that case — the operation is
total. The previous `Integer`-accepting overloads, which could throw on a zero
divisor, were removed in 5.2.0 in favor of this typed-divisor version.

`Integer` is annotated with `@ExperimentalKotoolsTypesApi` and will be
stabilized in a future release.

## 🛠️ Adding Kotools Types to your project

See the [Installation] section of the project's README for Gradle/Maven setup —
this article was written against version `5.2.0`.

This API is `@ExperimentalKotoolsTypesApi`, so opt in either per call-site with
`@OptIn(ExperimentalKotoolsTypesApi::class)` or project-wide via the compiler
argument:

```kotlin
// build.gradle.kts
kotlin {
    compilerOptions {
        freeCompilerArgs.add("-opt-in=org.kotools.types.ExperimentalKotoolsTypesApi")
    }
}
```

See also: [GitHub], [API reference]

Read the [5.2.0 highlights roundup] and the [article on Integer's internal
representation] for the other changes shipped in this release.

## 💬 Discussion

Has a negative remainder ever caused a bug in your own code — an off-by-one
array index, a modular arithmetic check that assumed non-negative results?

<!----------------------------------- LINKS ----------------------------------->

[5.2.0 highlights roundup]: 2-whats-new-in-5.2.0.md
[API reference]: https://types.kotools.org
[article on Integer's internal representation]: 3-integer-internal-representation.md
[GitHub]: https://github.com/kotools/types
[Installation]: https://github.com/kotools/types/blob/5.2.0/README.md#%EF%B8%8F-installation

---

## 🏷️ Metadata

GitHub Discussion tags: `type: promotion`, `platform: jvm`, `platform: js`,
`platform: native`

Dev.to tags (only 4): `kotlin`, `kmp`, `programming`, `math`
