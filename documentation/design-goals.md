# Design goals

This section presents the design goals that we should keep in mind when building
and improving Kotools Types.

## Less is more

Kotools Types focuses primarily on what is essential for building an explicit
and safe API: the types, their factory functions and their serializer.

By having this minimalist approach, we engage to provide what users really need.

Note that we may add other declarations if suggested by the community.
See the [contributing guidelines](/CONTRIBUTING.md) for more details about it.

## Avoid useless dependencies

This project is very light and just ship with one direct dependency (excluding
the Kotlin language): the [kotlinx.serialization] library for serializing or
deserializing the provided types.

Like stated above, these mechanisms are essential for Kotools Types because its
type-system should be usable in any type of application.

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
