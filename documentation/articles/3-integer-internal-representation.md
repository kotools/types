# ⚡ Why `Integer` no longer stores its value as a `String`

If you've used the experimental `Integer` type from Kotools Types before 5.2.0,
you may have noticed arithmetic getting slower as numbers grew larger. That
wasn't your imagination — it came from how `Integer` stored its value
internally.

## 🐛 The problem

Every `Integer` operation — `+`, `-`, `*`, comparisons, even `toString()` — had
to work with a `String` representation of the number. That means every single
operation reparsed the digits from scratch before it could do any arithmetic,
and re-serialized them back to a `String` afterward:

```kotlin
// Simplified: what every operation used to pay for
val x = "99999999999999999999" // stored as a String
val y = "1"
// '+' had to parse both strings into a computable form,
// add them, then format the result back into a String.
```

For a handful of small numbers this cost is invisible. For code that performs
many operations on large integers, it adds up — and it's entirely avoidable,
since the underlying value doesn't actually change shape between operations.

## 🤔 Why it happened

`String` was a convenient first representation: printing an `Integer` is free,
and arbitrary precision comes for granted. But parsing and formatting are O(_d_)
operations for a _d_-digit number. Doing that on _every_ arithmetic call means
the total cost of a chain of operations grows with both the number of operations
and the number of digits. That's the case even though a proper big-integer
representation only needs to pay the parsing/formatting cost once, at the
boundary.

## ✅ The fix: a real arbitrary-precision representation per platform

`Integer` from Kotools Types 5.2.0 delegates to whatever native
arbitrary-precision integer type each platform already provides — or, where none
exists, to a purpose-built implementation:

| Platform   | Representation                       |
|------------|--------------------------------------|
| JVM        | `java.lang.BigInteger`               |
| JavaScript | `BigInt`                             |
| Native     | Custom sign-magnitude implementation |

None of these store digits as text. Arithmetic operates directly on the
underlying numeric representation, with parsing and formatting only happening at
the actual boundaries — `parse()`, `fromLong()`, and `toString()` — not on every
`+` or `*` in between.

```kotlin
@OptIn(ExperimentalKotoolsTypesApi::class)
fun main() {
    val x: Integer = Integer.fromLong(9223372036854775807)
    val y: Integer = Integer.fromLong(10)

    val sum: Integer = x + y
    check(sum == Integer.parse("9223372036854775817"))

    val product: Integer = x * y
    check(product == Integer.parse("92233720368547758070"))
}
```

As a side benefit, dropping the previous approach also let the library remove
its only third-party runtime dependency on Kotlin/Native — one less dependency
to audit and update.

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

Read the [5.2.0 highlights roundup] for the other changes shipped in this
release.

## 💬 Discussion

Have you hit performance issues from string-based arbitrary-precision arithmetic
in other libraries? How did you work around it?

<!----------------------------------- LINKS ----------------------------------->

[5.2.0 highlights roundup]: 2-whats-new-in-5.2.0.md
[API reference]: https://types.kotools.org
[GitHub]: https://github.com/kotools/types
[Installation]: https://github.com/kotools/types/blob/5.2.0/README.md#%EF%B8%8F-installation

---

## 🏷️ Metadata

GitHub Discussion tags: `type: promotion`, `platform: jvm`, `platform: js`,
`platform: native`, `requested by: community`

Dev.to tags (only 4): `kotlin`, `kmp`, `programming`, `performance`
