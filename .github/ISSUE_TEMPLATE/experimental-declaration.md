---
name: Experimental Declaration
about: New request for introducing an experimental declaration.
title: New $DECLARATION
labels: feature
---

## Description

We would like to introduce an **experimental** $DECLARATION for $REASON.
This declaration shouldn't be available yet for Java users.

<!-- Uncomment this section if your issue depends on another one.
## Dependencies

This issue is blocked by the following ones:
- [ ] $ITEM
-->

## Checklist

- [ ] Add this declaration, [test] it on the JVM platform, update the public [API binaries] and update the [unreleased changelog].
- [ ] [Create an issue][new-issue] for making this declaration available for Java users in the next [patch release][versioning-strategy].

[api binaries]: https://github.com/kotools/types/blob/main/CONTRIBUTING.md#checking-the-api-binaries
[new-issue]: https://github.com/kotools/types/issues/new/choose
[test]: https://github.com/kotools/types/blob/main/CONTRIBUTING.md#running-tests
[unreleased changelog]: https://github.com/kotools/types/blob/main/CHANGELOG.md#unreleased
[versioning-strategy]: https://github.com/kotools/types/blob/main/documentation/versioning-strategy.md
