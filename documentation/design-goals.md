# Design goals

This section presents the design goals that we should keep in mind when building
and improving Kotools Types.

## Less is more

Kotools Types focus primarily on what is essential for building explicit and
safer APIs: the types and their factory functions.
Other declarations could be added if suggested by the community.
By having this minimalist approach, we engage to provide what users really need.

## Avoid useless dependencies

This project is very light and just ship with one direct dependency:
[kotlinx.serialization] for serializing or deserializing the provided types.
Knowing that these types could be used in any type of API, this feature is
essential for this library.

See the [dependency compatibility](dependencies.md) documentation for more
details about the compatibility of Kotools Types with its dependencies.

## Error handling agnostic

Users should be responsible for deciding how to handle errors, not this library.
Externalizing this responsibility to consumers implies that Kotools Types should
provide an explicit API by definition.
This is why we are using the [Result][kotlin.Result] type from Kotlin for
representing a result that can be a success or a failure.

[kotlin.Result]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result
[kotlinx.serialization]: https://github.com/Kotlin/kotlinx.serialization
