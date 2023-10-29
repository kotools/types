# Roadmap

The goal of this roadmap is to give you a big picture about what is coming for
Kotools Types.

Here’s a list of the most important things we focus on delivering:

- **Aligning type converters with our design goals on error handling** by
  suffixing them with `OrNull` if they can fail, or by not suffixing them if
  they shouldn't fail, for being agnostic about how users handle errors.
- **Clearing separation of the experimental API from the stable API** by
  centralizing experimental declarations in the `kotools.types.experimental`
  package.
- **Adhering back to [Semantic Versioning][semantic-versioning]** for
  communicating clearly what's changed in the stable API.

## Version 4.3.2 <a id="v4.3.2"></a>

- Improving documentation on the repository and on the
  [API reference][api-reference].
- Adhering to [Semantic Versioning][semantic-versioning].

See the [corresponding milestone][milestone-4.3.2] for more details.

## Version 4.4.0 <a id="v4.4.0"></a>

- New `ConvertibleToInt` stable type.
- Stabilization of type converters suffixed by `OrNull`.
- Deprecations:
    - Type converters using [Result][kotlin.result].
    - Type converters suffixed by `OrThow`.
    - The `ResultContext` type and its declarations.
- Experimental API:
    - New `ExperimentalKotoolsTypesApi` annotation in the
      `kotools.types.experimental` package.
    - New `StrictlyPositiveDouble` type in the `kotools.types.experimental`
      package and deprecate the same type in the `kotools.types.number` package.
    - New `Bound` and `NotEmptyRange` types in the `kotools.types.experimental`
      package and deprecate the same types in the `kotools.types.range` package.
    - New type converters on `ConvertibleToInt` to [Byte][kotlin.byte],
      [Short][kotlin.short], [Long][kotlin.long], [Float][kotlin.float] and
      [Double][kotlin.double].

See the [corresponding milestone][milestone-4.4.0] for more details.

## Version 5.0.0 <a id="v5.0.0"></a>

- Support [Kotlin 1.8.10][kotlin-1.8.10].
- Hiding internals from Java (see issue [#303]).
- New flattened type system of integer numbers.
- Deletions:
    - Deprecated type converters using [Result][kotlin.result].
    - Deprecated type converters suffixed by `OrThow`.
    - The deprecated `ResultContext` type.
- Experimental API:
    - New equality operations to bounds and ranges.
    - Removing experimental declarations from the stable API.
    - Removing annotations from the `kotools.types.experimental` package, except
      the `ExperimentalKotoolsTypesApi` annotation.

See the [corresponding milestone][milestone-5.0.0] for more details.

## Unplanned

- Support [Kotlin 1.8.22][kotlin-1.8.22].
- Support [Kotlin 1.9.10][kotlin-1.9.10].
- Stabilization of concatenations for the `NotBlankString` type.
- Converting collections to normal classes (because value classes are only
  useful for holding primitives).
- Stabilization of the `Bound` and the `NotEmptyRange` types.

[#303]: https://github.com/kotools/types/issues/303
[api-reference]: https://types.kotools.org
[kotlin-1.8.10]: https://github.com/JetBrains/kotlin/releases/tag/v1.8.10
[kotlin-1.8.22]: https://github.com/JetBrains/kotlin/releases/tag/v1.8.22
[kotlin-1.9.10]: https://github.com/JetBrains/kotlin/releases/tag/v1.9.10
[kotlin.byte]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte
[kotlin.double]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double
[kotlin.float]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float
[kotlin.long]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long
[kotlin.result]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result
[kotlin.short]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short
[milestone-4.3.2]: https://github.com/kotools/types/milestone/22
[milestone-4.4.0]: https://github.com/kotools/types/milestone/7
[milestone-5.0.0]: https://github.com/kotools/types/milestone/27
[semantic-versioning]: https://semver.org
