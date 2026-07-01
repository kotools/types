# 🔒 Making "this integer can never be negative" a type, not a check

"This value can never be negative" is one of the most common invariants in any
codebase — a count, a size, an index. It's usually enforced with a
`require(value >= 0)` sprinkled at every call site that produces or accepts the
value, which is easy to forget, easy to duplicate, and impossible for the
compiler to verify.

## 🐛 The problem

```kotlin
fun repeat(count: Int, block: () -> Unit) {
    require(count >= 0) { "count must not be negative" }
    for (i in 0 until count) block()
}
```

Every function that has this constraint needs its own `require` call, worded
slightly differently, tested separately, and easy to miss on a new call site.
Nothing in the _type_ of `count` tells you, or the compiler, that the constraint
even exists — you find out at runtime, if you're lucky enough to have a test
that exercises the negative case.

## ✅ The fix: sign as a type

Kotools Types 5.2.0 introduces three new types in `org.kotools.types.number`,
each wrapping an `Integer` with a sign guarantee enforced at construction:

- `NonZeroInteger` — never zero.
- `NonNegativeInteger` — greater than or equal to zero.
- `NonPositiveInteger` — less than or equal to zero.

```kotlin
@OptIn(ExperimentalKotoolsTypesApi::class)
fun main() {
    val valid: NonNegativeInteger = NonNegativeInteger.fromLong(42)
    check("$valid" == "42")

    val invalid: NonNegativeInteger? = NonNegativeInteger.fromLongOrNull(-1)
    check(invalid == null) // constructing it is where the check happens
}
```

Once the guarantee is baked into the type, arithmetic operators can preserve it
across operations instead of collapsing everything back down to a plain
`Integer`. For example, multiplying two integers with opposite-or-matching sign
constraints returns the type that matches the mathematical result:

```kotlin
@OptIn(ExperimentalKotoolsTypesApi::class)
fun main() {
    val x: NonNegativeInteger = NonNegativeInteger.fromLong(99999999999999999)
    val y: NonPositiveInteger = NonPositiveInteger.fromLong(-10)
    val result: NonPositiveInteger = x * y // non-negative * non-positive -> non-positive
    check(result == NonPositiveInteger.parse("-999999999999999990"))
}
```

The compiler now enforces what used to be a runtime-only convention: a function
that takes a `NonNegativeInteger` parameter can never be called with a negative
value, full stop — no `require` call needed inside it.

This mirrors an approach the stable `kotools.types.number` package has used for
a while (`PositiveInt`, `NegativeInt`, `NonZeroInt`, and friends) — 5.2.0 brings
the same idea to the newer, still-experimental `org.kotools.types.number` types
built around `Integer`.

These three types are annotated with `@ExperimentalKotoolsTypesApi` and will be
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

Read the [5.2.0 highlights roundup], the [article on Integer's internal
representation], and the [article on Decimal] for the other changes shipped in
this release.

## 💬 Discussion

Where in your own code would `NonNegativeInteger` or `NonZeroInteger` replace a
`require` call you're currently maintaining by hand?

<!----------------------------------- LINKS ----------------------------------->

[5.2.0 highlights roundup]: 2-whats-new-in-5.2.0.md
[API reference]: https://types.kotools.org
[article on Decimal]: 4-decimal-exact-arithmetic.md
[article on Integer's internal representation]: 3-integer-internal-representation.md
[GitHub]: https://github.com/kotools/types
[Installation]: https://github.com/kotools/types/blob/5.2.0/README.md#%EF%B8%8F-installation

---

## 🏷️ Metadata

GitHub Discussion tags: `type: promotion`, `platform: common`

Dev.to tags (only 4): `kotlin`, `kmp`, `programming`, `typesystem`
