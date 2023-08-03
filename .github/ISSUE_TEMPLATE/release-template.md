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
- [ ] Deliver packages to the Maven central.
- [ ] Close packages on the Maven central.
- [ ] Release packages on the Maven central.
- [ ] Create a release on the repository.
- [ ] Update the related discussion.
- [ ] Wait for the [deployment] workflow to pass.
- [ ] Announce the release on [Twitter] and on the
  [#kotools-types channel on Kotlin Slack][slack].
- [ ] Plan tweets about features introduced by this version.

[deployment]: https://github.com/kotools/types/actions/workflows/deployment.yml
[slack]: https://kotlinlang.slack.com/archives/C05H0L1LD25
[twitter]: https://twitter.com/KotoolsContact
