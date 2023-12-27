---
name: Java Compatibility
about: New request for making a declaration available for Java users.
title: Making $DECLARATION available on Java
labels: feature,jvm
---

## Description

We would like to make $DECLARATION available for Java users.
This means that its compatibility should be tested and its documentation should indicate their usage from Java code.

<!-- Uncomment this section if your issue depends on another one.
## Dependencies

This issue is blocked by the following ones:
- [ ] $ITEM
-->

## Checklist

- [ ] Make $DECLARATION available for Java users, [test] its compatibility, update the public [API binaries] and update the [unreleased changelog].
- [ ] [Create an issue][new-issue] for stabilizing this declaration in the next [minor release][versioning-strategy].

[api binaries]: https://github.com/kotools/types/blob/main/CONTRIBUTING.md#checking-the-api-binaries
[unreleased changelog]: https://github.com/kotools/types/blob/main/CHANGELOG.md#unreleased
[new-issue]: https://github.com/kotools/types/issues/new/choose
[test]: https://github.com/kotools/types/blob/main/CONTRIBUTING.md#running-tests
[versioning-strategy]: https://github.com/kotools/types/blob/main/documentation/versioning-strategy.md
