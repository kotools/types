---
name: 🔖 Release
about: Template for publishing a new version.
title: 🔖 Release version $VERSION
assignees: LVMVRQUXL
labels: release
---

## 🔗 Dependencies

Issues of the milestone corresponding to this version should be done before resolving this issue.

## ✅ Checklist

> See the [_Issue implementation_ section in the contributing guidelines](https://github.com/kotools/types/blob/main/CONTRIBUTING.md#issue-implementation) before addressing the following checklist.

- [ ] 🔖 Set Gradle project's version to this new one.
- [ ] 📝 Check the copyright notice in the [license] documentation.
- [ ] 🚀 Deliver packages to the [Maven central] by running the [delivery workflow].
- [ ] 🚀 Close valid packages and drop invalid ones on the [Maven central].
- [ ] 🚀 Release packages on the [Maven central].
- [ ] 🚀 Run the `./gradlew :publishApiReference` command for publishing the new API reference.
- [ ] 📝 Move the unreleased changelog to a GitHub release draft.
- [ ] 🔖 Run the `./gradlew :tag` command for creating a Git annotated tag for this version.
- [ ] 🔖 Publish the GitHub release on the annotated tag.
- [ ] 🔖 Set Gradle project's version to the next snapshot.
- [ ] 📝 Announce this release on [Reddit].
- [ ] 📝 Announce this release on [Slack].
- [ ] 📝 Announce this release on [Twitter].

[delivery workflow]: https://github.com/kotools/types/actions/workflows/delivery.yml
[license]: https://github.com/kotools/types/blob/main/LICENSE.txt
[maven central]: https://s01.oss.sonatype.org
[reddit]: https://www.reddit.com/r/Kotlin
[slack]: https://kotlinlang.slack.com/archives/C05H0L1LD25
[twitter]: https://twitter.com/kotools_org
