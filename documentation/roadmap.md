# Roadmap

The goal of this roadmap is to give you a big picture about what is coming for
Kotools Types.

Here’s a list of the most important things we focus on delivering:

- **Aligning factory functions with Java & Kotlin standards** by representing
  possible failures with the `null` expression and by conventioning their name
  (discussion [#315]).
- **Clearing separation between the experimental API and the stable API** by
  centralizing experimental declarations in the `kotools.types.experimental`
  package (discussion [#287]).
- **Adhering back to [Semantic Versioning][semantic-versioning]** for
  communicating clearly what's changed in the stable API.

## Version 4.3.2 <a id="v4.3.2"></a>

### General

- Improving documentation on the repository and on the
  [API reference][api-reference] (issue [#317]).

### Stable API

- Adhering to [Semantic Versioning][semantic-versioning] (issue [#215]).

### Experimental API

- Moving experimental declarations to the `kotools.types.experimental` package.
- New `ExperimentalKotoolsTypesApi` annotation.
- Deprecating all annotations in the `kotools.types.experimental` package for
  using the `ExperimentalKotoolsTypesApi` annotation instead.
- New factory functions named `of` or `from` for all types (issue [#316]).
- Deletion of factory functions suffixed by `OrNull` and `OrThrow` for all
  types.

See the [corresponding milestone][milestone-4.3.2] for more details.

## Version 4.4.0 <a id="v4.4.0"></a>

- New `ConvertibleToInt` stable type.
- Stabilization of type converters suffixed by `OrNull` (see issue [#262]).
- Deprecations:
    - Type converters using [Result][kotlin.result] (see issue [#263]).
    - Type converters suffixed by `OrThow` (see issue [#312]).
    - The `ResultContext` type and its declarations (see issue [#264]).
- Experimental API:
    - New type converters on `ConvertibleToInt` to [Byte][kotlin.byte],
      [Short][kotlin.short], [Long][kotlin.long], [Float][kotlin.float] and
      [Double][kotlin.double].

See the [corresponding milestone][milestone-4.4.0] for more details.

## Version 5.0.0 <a id="v5.0.0"></a>

- Support [Kotlin 1.8.10][kotlin-1.8.10] (see issue [#172]).
- Hiding internals from Java (see issue [#303]).
- Deletions:
    - Factory functions using [Result][kotlin.result] (see issue [#267]).
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
- New flattened type system of integer numbers.

[#172]: https://github.com/kotools/types/issues/172
[#215]: https://github.com/kotools/types/issues/215
[#263]: https://github.com/kotools/types/issues/263
[#264]: https://github.com/kotools/types/issues/264
[#267]: https://github.com/kotools/types/issues/267
[#287]: https://github.com/kotools/types/discussions/287
[#303]: https://github.com/kotools/types/issues/303
[#312]: https://github.com/kotools/types/issues/312
[#315]: https://github.com/kotools/types/discussions/315
[#316]: https://github.com/kotools/types/issues/316
[#317]: https://github.com/kotools/types/issues/317
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
