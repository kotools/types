---
name: 🔖 Release
about: Publish new version
title: 🔖 Release version $VERSION
assignees: LVMVRQUXL
labels: "type: release"
---

## 🔗 Dependencies

Issues of this version should be done before working on this issue.

## ✅ Checklist

- [ ] 🔖 Set Gradle project's version to this new one.
- [ ] 📄 Check copyright notice in [license].
- [ ] 🚀 Run [delivery workflow].
- [ ] 🚀 Release distribution on [Maven Central].
- [ ] 🚀 Publish API reference with `./gradlew :publishApiReference`.
- [ ] 📝 Add release in `CHANGELOG.md` file.
- [ ] 🔖 Create Git tag with `./gradlew :tag`.
- [ ] 🔖 Publish GitHub release on Git tag.
- [ ] 🔖 Set Gradle project's version to the next snapshot.

[delivery workflow]: https://github.com/kotools/types/actions/workflows/delivery.yml
[license]: https://github.com/kotools/types/blob/main/LICENSE.txt
[maven central]: https://central.sonatype.com
