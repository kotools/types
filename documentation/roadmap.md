<!--
    Copyright 2023 Loïc Lamarque.
    Use of this source code is governed by the MIT license.
-->

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
  communicating clearly what's changed in the stable API (issue [#215]).

## Version 4.3.2 <a id="v4.3.2"></a>

### General <a id="v4.3.2-general"></a>

- Documentation of the versioning strategy (issue [#215]).
- Documentation of the declarations lifecycle (issue [#307]).

### Experimental API <a id="v4.3.2-experimental-api"></a>

- New `ExperimentalKotoolsTypesApi` annotation (issue [#191]).
- Moving experimental declarations to the `kotools.types.experimental` package
  (issue [#319]).
- Deletion of annotations in the `kotools.types.experimental` package, except
  the `ExperimentalKotoolsTypesApi` annotation (issue [#318]).

See the [corresponding milestone][milestone-4.3.2] for more details.

## Version 4.3.3 <a id="v4.3.3"></a>

### Experimental API <a id="v4.3.3-experimental-api"></a>

- New factory functions named `of` or `from` for all types (issue [#316]).
- Deletion of factory functions suffixed by `OrNull` and `OrThrow` for all
  types (issue [#258]).
- New equality operations for the `Bound` type (issue [#223]).
- New equality operations for the `NotEmptyRange` type (issue [#225]).
- New type converters on `AnyInt` to [Byte][kotlin.byte],
  [Short][kotlin.short], [Long][kotlin.long], [Float][kotlin.float] and
  [Double][kotlin.double].

See the [corresponding milestone][milestone-4.3.3] for more details.

## Version 4.4.0 <a id="v4.4.0"></a>

### Stable API <a id="v4.4.0-stable-api"></a>

- Stabilization of factory functions named `of` or `from`.
- Deprecation of factory functions using [Result][kotlin.result] (issue
  [#263]).
- Deprecation of the `ResultContext` type and its declarations (issue [#264]).

See the [corresponding milestone][milestone-4.4.0] for more details.

## Version 5.0.0 <a id="v5.0.0"></a>

### General <a id="v5.0.0-general"></a>

- Support [Kotlin 1.8.10][kotlin-1.8.10] (issue [#172]).
- Hiding internals from Java (issue [#303]).

### Stable API <a id="v5.0.0-stable-api"></a>

- Deletion of factory functions using [Result][kotlin.result] (issue [#267]).
- Deletion of the `ResultContext` type and its declarations (issue [#270]).

### Experimental API <a id="v5.0.0-experimental-api"></a>

- Remove deprecated annotations in the `kotools.types.experimental` package.

See the [corresponding milestone][milestone-5.0.0] for more details.

## Discussing

- Support [Kotlin 1.8.22][kotlin-1.8.22].
- Support [Kotlin 1.9.10][kotlin-1.9.10].
- Stabilization of concatenations for the `NotBlankString` type.
- Converting collections to normal classes (because value classes are only
  useful for holding primitives).
- Stabilization of the `Bound` and the `NotEmptyRange` types.
- New flattened type system of integer numbers.
- Converting extension functions to member functions.

[#172]: https://github.com/kotools/types/issues/172
[#191]: https://github.com/kotools/types/issues/191
[#215]: https://github.com/kotools/types/issues/215
[#223]: https://github.com/kotools/types/issues/223
[#225]: https://github.com/kotools/types/issues/225
[#258]: https://github.com/kotools/types/issues/258
[#263]: https://github.com/kotools/types/issues/263
[#264]: https://github.com/kotools/types/issues/264
[#267]: https://github.com/kotools/types/issues/267
[#270]: https://github.com/kotools/types/issues/270
[#287]: https://github.com/kotools/types/discussions/287
[#303]: https://github.com/kotools/types/issues/303
[#307]: https://github.com/kotools/types/issues/307
[#312]: https://github.com/kotools/types/issues/312
[#315]: https://github.com/kotools/types/discussions/315
[#316]: https://github.com/kotools/types/issues/316
[#318]: https://github.com/kotools/types/issues/318
[#319]: https://github.com/kotools/types/issues/319
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
[milestone-4.3.3]: https://github.com/kotools/types/milestone/29
[milestone-4.4.0]: https://github.com/kotools/types/milestone/7
[milestone-5.0.0]: https://github.com/kotools/types/milestone/27
[semantic-versioning]: https://semver.org
