---
name: 🔖 Release
about: Publish new version
title: 🔖 Release version $VERSION
assignees: LVMVRQUXL
labels: release
---

## 🔗 Dependencies

Issues of this version should be done before working on this issue.

## ✅ Checklist

- [ ] 🔖 Set Gradle project's version to this new one.
- [ ] 🏷️ Add version in `KotoolsTypesVersion` type and replace usages of `KotoolsTypesVersion.Unreleased`.
- [ ] 📄 Check copyright notice in [license](https://github.com/kotools/types/blob/main/LICENSE.txt).
- [ ] 🚀 Run [delivery workflow](https://github.com/kotools/types/actions/workflows/delivery.yml).
- [ ] 🚀 Release distribution on [Maven Central](https://central.sonatype.com).
- [ ] 🚀 Publish API reference with `./gradlew :publishApiReference`.
- [ ] 📝 Move unreleased changelog to GitHub release draft, and add new release in changelog.
- [ ] 🔖 Create Git tag with `./gradlew :tag`.
- [ ] 🔖 Publish GitHub release on Git tag.
- [ ] 🔖 Set Gradle project's version to the next snapshot.
- [ ] 📝 Announce this release on [r/Kotlin](https://www.reddit.com/r/Kotlin) and [r/KotlinMultiplatform](https://www.reddit.com/r/KotlinMultiplatform).
- [ ] 📝 Announce this release on [Slack](https://kotlinlang.slack.com/archives/C05H0L1LD25) (`kotools` and `feed` channels).
- [ ] 📝 Announce this release on [Twitter](https://twitter.com/kotools_org).
