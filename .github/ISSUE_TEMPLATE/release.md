---
name: Release
about: Publishing a new version.
title: Release version $VERSION
labels: release
---

## Dependencies

Issues of the milestone corresponding to this version should be done before resolving this issue.

## Checklist

- [ ] Set Gradle project's version, version of unreleased declarations and add this version in changelog.
- [ ] Create a release branch for this new version.
- [ ] Set Gradle project's version with the next version, suffixed by `SNAPSHOT`.
- [ ] Check the copyright notice in the [license] documentation, then apply the corresponding commit to the release branch if updated.
- [ ] Add this version in the [security policy] documentation, then apply the corresponding commit to the release branch.
- [ ] Add this version in the [dependency compatibility] documentation, then apply the corresponding commit to the release branch.
- [ ] Deliver packages to the [Maven central] by running the [delivery workflow].
- [ ] Close valid packages and drop invalid ones on the [Maven central].
- [ ] Prepare a release notes for this version, then apply the corresponding commit to the release branch.
- [ ] Release packages on the [Maven central].
- [ ] Run the `publishApiReference.sh` script locally from the release branch and update the API reference.
- [ ] Update the badge version in [README].
- [ ] Create a release on the GitHub repository.
- [ ] Announce this release on [Slack] and on [Twitter].

[delivery workflow]: https://github.com/kotools/types/actions/workflows/delivery.yml
[dependency compatibility]: https://github.com/kotools/types/blob/main/documentation/dependencies.md
[license]: https://github.com/kotools/types/blob/main/LICENSE.txt
[maven central]: https://s01.oss.sonatype.org
[readme]: https://github.com/kotools/types/blob/main/README.md
[security policy]: https://github.com/kotools/types/blob/main/SECURITY.md
[slack]: https://kotlinlang.slack.com/archives/C05H0L1LD25
[twitter]: https://twitter.com/KotoolsContact
