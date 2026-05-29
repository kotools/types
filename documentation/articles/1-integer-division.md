# Integer division by zero in Kotlin Multiplatform: silent on JavaScript, exception on JVM and Native — and how to fix it

If you write Kotlin Multiplatform code that involves integer division, you may
have already hit this: the exact same expression behaves completely differently
depending on which platform compiles it.

## The problem

Take this innocuous expression:

```kotlin
val quotient = 12 / 0
val remainder = 12 % 0
```

On **JVM and Native**, both lines throw an `ArithmeticException`. That is the
behaviour most Kotlin developers expect and design around.

On **JavaScript**, both lines execute without any exception and silently return
`0`.

Here is a concrete illustration drawn directly from the Kotlin test suites for
each platform:

```kotlin
// Kotlin/JS
check(12 / 0 == 0)   // passes — no exception
check(12 % 0 == 0)   // passes — no exception

// Kotlin/JVM and Kotlin/Native
val quotient: Result<Int> = runCatching { 12 / 0 }
val remainder: Result<Int> = runCatching { 12 % 0 }
check(quotient.exceptionOrNull() is ArithmeticException)   // passes
check(remainder.exceptionOrNull() is ArithmeticException)  // passes
```

**Summary table:**

| Expression | JVM / Native          | JavaScript |
|------------|-----------------------|------------|
| `12 / 0`   | `ArithmeticException` | `0`        |
| `12 % 0`   | `ArithmeticException` | `0`        |

## Why it happens

JavaScript's native `BigInt` type returns `0n` for `0n / 0n` by specification.
The Kotlin/JS compiler does not wrap arithmetic operations in additional
exception checks, so the platform behaviour leaks through.

JVM and Native follow Java's long-standing contract: integer division by zero
is always an `ArithmeticException`.

The practical consequence is that any guard you write and test on JVM — a
`try/catch(ArithmeticException)` or a pre-condition check that relies on an
exception — is **silently bypassed** when the same code runs on JS. No compile
error, no warning, just a wrong result.

## The fix: `Integer` from Kotools Types 5.1.1

The `Integer` type in Kotools Types explicitly checks for a zero divisor before
delegating to the platform, so both `div` and `rem` throw `ArithmeticException`
consistently on JVM, JavaScript, and Native.

```kotlin
@OptIn(ExperimentalKotoolsTypesApi::class)
fun main() {
    val a: Integer = Integer.of(12)
    val b: Integer = Integer.of(0)

    a / b   // ArithmeticException on JVM, JS, and Native
    a % b   // ArithmeticException on JVM, JS, and Native
}
```

If you prefer to avoid exception handling altogether, `divOrNull` and
`remOrNull` return `null` instead of throwing:

```kotlin
@OptIn(ExperimentalKotoolsTypesApi::class)
fun main() {
    val a: Integer = Integer.of(12)
    val b: Integer = Integer.of(0)

    val quotient: Integer? = a.divOrNull(b)   // null — no exception
    val remainder: Integer? = a.remOrNull(b)  // null — no exception
}
```

`Integer` is annotated with `@ExperimentalKotoolsTypesApi` and will be
stabilised in a future release.

## Adding Kotools Types to your project

```kotlin
// build.gradle.kts
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("org.kotools.types:types:5.1.1")
        }
    }
}
```

Opt in either per call-site with `@OptIn(ExperimentalKotoolsTypesApi::class)`
or project-wide via the compiler argument:

```kotlin
// build.gradle.kts
kotlin {
    compilerOptions {
        freeCompilerArgs.add("-opt-in=org.kotools.types.ExperimentalKotoolsTypesApi")
    }
}
```

## Discussion

Have you run into this inconsistency in a real project? How do you currently
guard against division by zero in your multiplatform code?

API reference: https://types.kotools.org/types/org.kotools.types/-integer/index.html  
GitHub: https://github.com/kotools/types
