# 🔗 Dependency compatibility

This file documents the compatibility of Kotools Types with its dependencies:
the [Kotlin] language and the [kotlinx.serialization] library.

## 📦 Kotlin

Here's the compatibility of this project with [Kotlin], including its embedded
version and its language version for source compatibility:

| Kotools Types version | Embedded Kotlin version | Kotlin language version |
|-----------------------|-------------------------|-------------------------|
| 5.0                   | 1.9.25                  | 1.9                     |
| 4.5                   | 1.8.22                  | 1.5                     |
| 4.4                   | 1.7.21                  | 1.5                     |
| 4.3.1                 | 1.7.21                  | 1.5                     |
| 4.3.0                 | 1.7.21                  | 1.7                     |
| 4.2                   | 1.6.21                  | 1.6                     |
| 4.1                   | 1.5.32                  | 1.5                     |
| <= 4.0                | 1.5.31                  | 1.5                     |

## 📦 kotlinx.serialization

Here's the compatibility of this project with [kotlinx.serialization] for
serializing or deserializing the provided types:

| Kotools Types version | kotlinx.serialization version |
|-----------------------|-------------------------------|
| 5.0                   | 1.6.3                         |
| 4.5                   | 1.5.1                         |
| <= 4.4                | 1.4.1                         |
| 4.2                   | 1.3.3                         |
| <= 4.1                | 1.3.1                         |
| < 3.0                 | -                             |

## ⬆️ Upgrades strategy

Starting from Kotools Types 5.0, we have the following strategy for upgrading
our dependencies:

- Upgrading the embedded [Kotlin] version must be done for a **minor release**
  or a **major release**.
- Upgrading the [Kotlin] language version must be done for a **major release**.
- Upgrading the [kotlinx.serialization] version must be done for a **minor
  release** or a **major release**.

See our [versioning strategy](versioning-strategy.md) for more details on these
releases.

<!-- Shared links -->

[kotlin]: https://kotlinlang.org
[kotlinx.serialization]: https://github.com/Kotlin/kotlinx.serialization
