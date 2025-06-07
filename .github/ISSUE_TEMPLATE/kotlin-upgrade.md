---
name: ⬆️ Kotlin upgrade
about: Template for upgrading Kotlin.
title: ⬆️ Kotlin NEXT_VERSION
labels: dependencies
---

## 📝 Description

We want to upgrade the embedded Kotlin version from **CURRENT_VERSION** to
**NEXT_VERSION**. For more details on changes included in this new version, see
what's new in Kotlin [NEXT_VERSION]().

<!--
As an example, the link to the Kotlin documentation to specify for Kotlin 2.0.0
is https://kotlinlang.org/docs/whatsnew20.html.
-->

For aligning to this new Kotlin version, we also want to upgrade the following
dependencies:

- Kotlin Serialization from [CURRENT_VERSION]() to [NEXT_VERSION]().
- Gradle from [CURRENT_VERSION]() to [NEXT_VERSION]().
- Dokka Gradle plugin from [CURRENT_VERSION]() to [NEXT_VERSION]().
- Kotlin Binary Compatibility Validator Gradle plugin from [CURRENT_VERSION]()
  to [NEXT_VERSION]().

<!--
Links provided for each dependency listed above must point to their official
website or GitHub project. Here's an example for each of these:

- Kotlin Serialization 1.7.3: https://github.com/Kotlin/kotlinx.serialization/releases/tag/v1.7.3
- Gradle 8.14.2: https://docs.gradle.org/8.14.2/release-notes.html
- Dokka 2.0.0: https://github.com/Kotlin/dokka/releases/tag/v2.0.0
- Kotlin Binary Compatibility Validator 0.17.0: https://github.com/Kotlin/binary-compatibility-validator/releases/tag/0.16.3
-->

## 🔗 Dependencies

This issue is blocked by the following items:

- [ ] #PREVIOUS_KOTLIN_UPGRADE_ISSUE

## ✅ Checklist

> See the [_Issue implementation_ section in the contributing guidelines](https://github.com/kotools/types/blob/main/CONTRIBUTING.md#issue-implementation) before addressing the following checklist.

- [ ] ⬆️ Upgrade the embedded Kotlin version and test all Gradle projects.
- [ ] 📌 Upgrade the `yarn.lock` file and test all Gradle projects.
- [ ] ⬆️ Upgrade the Kotlin Serialization library and test all Gradle projects.
- [ ] ⬆️ Upgrade the Dokka Gradle plugin and test the generation of the API reference.
- [ ] ⬆️ Upgrade the Kotlin Binary Compatibility Validator Gradle plugin and test all Gradle projects.
- [ ] 📝 Update badges in the README documentation of the root project and the `types-kotlinx-serialization` project.
- [ ] 📝 Update the dependency compatibility documentation.
- [ ] 📝 Update the unreleased changelog for this issue.

<!-- Include the following step in case of tracking issues.
- [ ] 📝 After closing this issue, update the status of tracking issues depending only on this one.
-->
