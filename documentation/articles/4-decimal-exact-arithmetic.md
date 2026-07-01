# 🎯 Exact decimal arithmetic without floating-point

```kotlin
val total: Double = 0.1 + 0.2
println(total) // 0.30000000000000004, not 0.3
```

Every Kotlin developer runs into this eventually. It isn't a bug in Kotlin —
it's how binary floating-point works everywhere, in every language. Kotools
Types 5.2.0 introduces `Decimal`, a type built specifically to avoid it.

## 🐛 The problem

`Double` and `Float` represent numbers in base 2. Most "round" base-10
decimals — `0.1`, `0.2`, `3.14` — have no exact binary representation, the same
way `1/3` has no finite representation in base 10. Every arithmetic operation on
such values accumulates tiny rounding errors, which is why `0.1 + 0.2` doesn't
produce exactly `0.3`.

For money, measurements, or any domain where "the numbers must add up exactly",
this is a real correctness problem, not a cosmetic one.

## ✅ The fix: a scaled-integer representation

`Decimal` never uses floating-point. Internally, it represents a value as an
`Integer` paired with a scale — conceptually `unscaledValue x 10^-scale` — so
`3.14` is stored as `(314, scale = 2)`. Addition, subtraction, and
multiplication on scaled integers are always exact, because they never leave the
set of terminating decimal numbers.

```kotlin
@OptIn(ExperimentalKotoolsTypesApi::class)
fun main() {
    val x: Decimal = Decimal.parse("1.5")
    val y: Decimal = Decimal.parse("1.25")
    val sum: Decimal = x + y
    check(sum == Decimal.parse("2.75")) // exact, no rounding error

    val a: Decimal = Decimal.parse("1.1")
    val b: Decimal = Decimal.parse("1.1")
    val product: Decimal = a * b
    check(product == Decimal.parse("1.21")) // exact
}
```

`Decimal` also normalizes its representation: `3.14` and `3.140` compare and
hash as equal, and trailing fractional zeros never leak into `toString()`.

## 🤔 Why there's no `div` or `rem`

Addition, subtraction, and multiplication of two terminating decimals always
produce another terminating decimal — that closure property is exactly what
makes exact arithmetic possible. Division doesn't share it:

```text
1 / 3 = 0.3333333333333333...  // never terminates
```

Dividing two terminating decimals can produce a number that has no finite
decimal representation at all. `Decimal` deliberately has no `div`/`rem`
operator rather than silently rounding or truncating the result to some
arbitrary number of digits — an omission, not a missing feature. If you need
division, convert to `Double` explicitly at the point where you're willing to
accept an approximation.

`Decimal` is annotated with `@ExperimentalKotoolsTypesApi` and will be
stabilized in a future release. It was also a frequently requested addition from
the community.

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

Read the [5.2.0 highlights roundup] for the other changes shipped in this
release.

## 💬 Discussion

Where in your own code have you had to work around floating-point rounding
errors?

<!----------------------------------- LINKS ----------------------------------->

[5.2.0 highlights roundup]: 2-whats-new-in-5.2.0.md
[API reference]: https://types.kotools.org
[GitHub]: https://github.com/kotools/types
[Installation]: https://github.com/kotools/types/blob/5.2.0/README.md#%EF%B8%8F-installation

---

## 🏷️ Metadata

GitHub Discussion tags: `type: promotion`, `platform: common`,
`requested by: community`

Dev.to tags (only 4): `kotlin`, `kmp`, `programming`, `finance`
