---
name: Release Template
about: New release
title: Release version $VERSION
labels: release
---

## Dependencies

Issues of this new version should be done before resolving this issue.

## Checklist

- [ ] Set Gradle project's version, version of unreleased declarations, badge version in README, add this version in changelog and commit local changes.
- [ ] Deliver packages to the Maven central by running the [delivery workflow].
- [ ] Close packages on the Maven central.
- [ ] Release packages on the Maven central.
- [ ] Create a release on the repository and update the related discussion.
- [ ] Run the [library/publishApiReference.sh] script locally.
- [ ] Push the API reference to GitHub Pages.
- [ ] Add this version in the security policy.
- [ ] Announce the release on [Twitter] and on the [#kotools-types channel on Kotlin Slack][slack].
- [ ] Plan tweets about features introduced by this version.

[delivery workflow]: https://github.com/kotools/types/actions/workflows/delivery.yml
[library/publishApiReference.sh]: https://github.com/kotools/types/blob/b4a82be05bc9a0fd2ab16ee35644773426a18a4f/library/publishApiReference.sh
[slack]: https://kotlinlang.slack.com/archives/C05H0L1LD25
[twitter]: https://twitter.com/KotoolsContact
