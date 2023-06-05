# Kotools Types

[![Kotools Types][kotools-types-badge]][kotools-types-project]
[![Kotlin][kotlin-badge]][kotlin]
[![kotlinx.serialization][kotlinx.serialization-badge]][kotlinx.serialization]

Type safety is a must-have nowadays and reducing runtime errors to compile-time
errors feels like magic!
But even with the [Kotlin] type system, we still have runtime issues that can
fail our logic or break our software, like dividing a number by zero or
receiving a negative index...

How can we solve that? Defining more explicit types!
Luckily for you, this is basically what this library does: providing types for
improving the preciseness of your code.
Here's an example dividing an integer by an integer other than zero, for
avoiding an [`ArithmeticException`][kotlin.ArithmeticException] to be thrown:

```kotlin
import kotools.types.number.NonZeroInt
import kotools.types.number.div
import kotools.types.number.toStrictlyPositiveInt

val x = 42
val y: NonZeroInt = 6.toNonZeroInt().getOrThrow()
println(x / y) // 7
```

Using explicit types in your code is perfect for:

- ensuring that your data is valid through all your application
- striving for [total functions][total-functions] by reducing the possible
  inputs or outputs (like the `div` function used in the example above)
- testing your code using the compiler effectively without writing tests (this
  is how we reduce runtime checks to compile-time ones).

Cherry on top: Kotools Types is a multiplatform library, so you can use it in
all your [Kotlin] projects!

[kotools-types-badge]: https://img.shields.io/static/v1?label=version&message=4.1.0&color=blue
[kotools-types-project]: https://github.com/kotools/types
[kotlin]: https://kotlinlang.org
[kotlin-badge]: https://img.shields.io/badge/kotlin-1.6.21-blue?logo=kotlin
[kotlin.ArithmeticException]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-arithmetic-exception
[kotlinx.serialization]: https://github.com/Kotlin/kotlinx.serialization
[kotlinx.serialization-badge]: https://img.shields.io/badge/kotlinx.serialization-1.3.3-blue
[total-functions]: https://xlinux.nist.gov/dads/HTML/totalfunc.html

## Design goals

### Less is more

Kotools Types focus primarily on what is essential for building explicit and
safer APIs: the types and their builders.
Other declarations could be added if suggested by the community.
By having this minimalist approach, we engage to provide what users really need.

### Avoid useless dependencies

This project is very light and just ship with one direct dependency:
[kotlinx.serialization] for serializing or deserializing the provided types.
Knowing that these types could be used in any type of API, this feature is
essential for this library.

### Error handling agnostic

Users should be responsible for deciding how to handle errors, not this library.
Externalizing this responsibility to consumers implies that Kotools Types should
provide an explicit API by definition.
This is why we are using the [`Result`][kotlin.Result] type from Kotlin for
representing a result that can be a success or a failure.

[kotlin.Result]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result

## Installation

> The value of the `$version` or the `${kotools.types.version}` variables is
> available in the [version badge](#kotools-types).

### Gradle

#### Kotlin DSL

```kotlin
implementation("org.kotools:types:$version")
```

#### Groovy DSL

```groovy
implementation "org.kotools:types:$version"
```

### Maven

```xml
<dependencies>
    <dependency>
        <groupId>org.kotools</groupId>
        <artifactId>types</artifactId>
        <version>${kotools.types.version}</version>
    </dependency>
</dependencies>
```

## Contributing

Contributions are welcomed!
Please check the [contribution guidelines] for doing so.

You can also join us on [GitHub Discussions]!
We use this place for communicating [ideas] or [announcements] to our community.
Here's where you can [get started].

[announcements]: https://github.com/kotools/types/discussions/categories/announcements
[contribution guidelines]: https://github.com/kotools/types/blob/main/contributing.md
[get started]: https://github.com/kotools/types/discussions/24
[GitHub Discussions]: https://github.com/kotools/types/discussions
[ideas]: https://github.com/kotools/types/discussions/categories/ideas

## Acknowledgements

Thanks to [Loïc Lamarque] for creating and sharing this project with the open
source community.

Thanks to [all the people that ever contributed] through code or other means such
as bug reports, feature suggestions and so on.

[all the people that ever contributed]: https://github.com/kotools/types/graphs/contributors
[Loïc Lamarque]: https://github.com/LVMVRQUXL

## License

This project is licensed under the [MIT License].

[MIT License]: https://choosealicense.com/licenses/mit
