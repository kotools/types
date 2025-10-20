---
name: 🔖 Release
about: Template for publishing a new version.
title: 🔖 Release version $VERSION
assignees: LVMVRQUXL
labels: release
---

## 🔗 Dependencies

Issues of this version should be done before working on this issue.

## ✅ Checklist

- [ ] 🔖 Set Gradle project's version to this new one.
- [ ] 🏷️ Add version in `KotoolsTypesVersion` type.
- [ ] ♻️ Replace all `KotoolsTypesVersion.Unreleased` usages by previously added version.
- [ ] 📄 Check copyright notice in [license].
- [ ] 🚀 [Deliver][delivery workflow] publication to Central Portal.
- [ ] 🚀 Release publication on Central Portal.
- [ ] 🚀 Publish API reference with `./gradlew :publishApiReference`.
- [ ] 📝 Move unreleased changelog to GitHub release draft.
- [ ] 🔖 Create Git tag with `./gradlew :tag`.
- [ ] 🔖 Publish GitHub release on Git tag.
- [ ] 🔖 Set Gradle project's version to the next snapshot.
- [ ] 📝 Announce this release on [Reddit].
- [ ] 📝 Announce this release on [Slack].
- [ ] 📝 Announce this release on [Twitter].

[delivery workflow]: https://github.com/kotools/types/actions/workflows/delivery.yml
[license]: https://github.com/kotools/types/blob/main/LICENSE.txt
[reddit]: https://www.reddit.com/r/Kotlin
[slack]: https://kotlinlang.slack.com/archives/C05H0L1LD25
[twitter]: https://twitter.com/kotools_org
