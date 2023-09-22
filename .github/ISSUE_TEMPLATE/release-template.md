---
name: Release Template
about: New release
title: Release version $VERSION
labels: release
---

## Dependencies

Issues of this new version should be done before resolving this issue.

## Checklist

- [ ] Upgrade the application's version.
- [ ] Add a new version in changelog.
- [ ] Update version in readme.
- [ ] Deliver packages to the Maven central by running the [delivery workflow].
- [ ] Close packages on the Maven central.
- [ ] Release packages on the Maven central.
- [ ] Run the [library/publishApiReference.sh] script locally.
- [ ] Push the API reference to GitHub Pages.
- [ ] Create a release on the repository.
- [ ] Update the related discussion.
- [ ] Announce the release on [Twitter] and on the [#kotools-types channel on Kotlin Slack][slack].
- [ ] Plan tweets about features introduced by this version.

[delivery workflow]: https://github.com/kotools/types/actions/workflows/delivery.yml
[library/publishApiReference.sh]: https://github.com/kotools/types/blob/b4a82be05bc9a0fd2ab16ee35644773426a18a4f/library/publishApiReference.sh
[slack]: https://kotlinlang.slack.com/archives/C05H0L1LD25
[twitter]: https://twitter.com/KotoolsContact
