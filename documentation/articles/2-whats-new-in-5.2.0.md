# 🚀 What's new in Kotools Types 5.2.0

Kotools Types 5.2.0 is out, and it's the biggest release yet for
`org.kotools.types.number`. Four changes stand out. Each gets its own dedicated,
deep-dive article — this post is the short tour.

## 🧮 `Integer` got a faster internal representation

Before 5.2.0, `Integer` stored its value as a `String` and reparsed it on every
arithmetic operation. That representation is gone: `Integer` now delegates to
`java.lang.BigInteger` on Kotlin/JVM, `BigInt` on Kotlin/JS, and a custom
arbitrary-precision implementation on Kotlin/Native — with no parsing involved
in `+`, `-`, `*`, `div`, or `rem`. This was one of the most frequently requested
changes from the community.

## 🎯 `Decimal` arrives: exact arithmetic without floating-point

`0.1 + 0.2 != 0.3` is a classic trap with `Double`/`Float`, because binary
floating-point can't exactly represent most base-10 decimals. `Decimal`, a
brand-new type in `org.kotools.types.number`, sidesteps the problem entirely by
never using floating-point in the first place — addition, subtraction, and
multiplication are always exact.

## ➗ `Integer` division is now Euclidean, and total

`Integer.div` and `Integer.rem` now always satisfy `0 <= remainder < |divisor|`
— no more negative remainders depending on the sign of the operands. The divisor
is also now a `NonZeroInteger`, a type that rules out division by zero at
compile time, so these operations can no longer throw or return `null` because
of a zero divisor.

## 🔒 Sign as a type: `NonZeroInteger`, `NonNegativeInteger`, `NonPositiveInteger`

Three new types turn "this integer can never be negative" (or positive, or zero)
from a runtime check into a compile-time guarantee, with arithmetic operators
that preserve those guarantees across additions and multiplications.

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

## 💬 Discussion

Which of these four changes affects your code the most?

<!----------------------------------- LINKS ----------------------------------->

[API reference]: https://types.kotools.org
[GitHub]: https://github.com/kotools/types
[Installation]: https://github.com/kotools/types/blob/5.2.0/README.md#%EF%B8%8F-installation

---

## 🏷️ Metadata

GitHub Discussion tags: `type: promotion`, `platform: common`,
`requested by: community`

Dev.to tags (only 4): `kotlin`, `kmp`, `releasenotes`, `programming`
