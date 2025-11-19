# 🔗 Dependency compatibility

This file documents the compatibility of Kotools Types with its API
dependencies: Java, Kotlin and Kotlin Serialization.

## 📦 Java

| Kotools Types | Java |
|---------------|------|
| 1.0.0         | 8    |
| 4.3.0         | 17   |

## 📦 Kotlin

**Source compatibility:** Kotlin language version is only applicable to JVM
and JS platforms. For now, Native platforms don't benefit from it (see
[KT-66755](https://youtrack.jetbrains.com/projects/KT/issues/KT-66755)).

| Kotools Types | Kotlin Gradle Plugin | Kotlin language |
|---------------|----------------------|-----------------|
| 1.0.0         | 1.5.31               | 1.5             |
| 4.1.0         | 1.5.32               | 1.5             |
| 4.2.0         | 1.6.21               | 1.6             |
| 4.3.0         | 1.7.21               | 1.7             |
| 4.3.1         | 1.7.21               | 1.5             |
| 4.4.0         | 1.7.21               | 1.5             |
| 4.5.0         | 1.8.22               | 1.5             |
| 5.0.0         | 1.9.25               | 1.9             |

## 📦 Kotlin Serialization

| Kotools Types | Kotlin Serialization |
|---------------|----------------------|
| 3.0.0         | 1.3.1                |
| 4.2.0         | 1.3.3                |
| 4.3.0         | 1.4.1                |
| 4.5.0         | 1.5.1                |
| 5.0.0         | 1.6.3                |

## ⬆️ Upgrades strategy

Starting from Kotools Types 5.0.0, upgrading our dependencies above must be
included in a **major release**.

See our [versioning strategy](versioning-strategy.md) for more details on our
type of releases.
