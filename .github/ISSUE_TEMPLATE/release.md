---
name: Release
about: Publishing a new version.
title: Release version $VERSION
labels: release
---

## Dependencies

Issues of this new version should be done before resolving this issue.

## Checklist

- [ ] Set Gradle project's version, version of unreleased declarations, badge version in README, then add this version in changelog.
- [ ] Run the `publishApiReference.sh` script locally and create a pull request updating the API reference.
- [ ] Deliver packages to the Maven central by running the [delivery workflow](https://github.com/kotools/types/actions/workflows/delivery.yml).
- [ ] Close packages on the Maven central.
- [ ] Prepare a release notes for this version.
- [ ] Plan the announcement of this release on the [#kotools-types channel on Kotlin Slack](https://kotlinlang.slack.com/archives/C05H0L1LD25).
- [ ] Plan the announcement of this release on [Twitter](https://twitter.com/KotoolsContact).
- [ ] Release packages on the Maven central.
- [ ] Merge the pull request updating the API reference.
- [ ] Add this version in the security policy.
- [ ] Create a release on the GitHub repository.
