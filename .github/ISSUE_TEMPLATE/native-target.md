---
name: Kotlin Native target
about: New request for supporting a Kotlin Native target.
labels: automation, feature, native
---

## Description

We would like to support the `TARGET` Kotlin Native target.

See the [targets supported] by Kotlin Native for more details about it.

[targets supported]: https://kotlinlang.org/docs/native-target-support.html

<!-- Uncomment this section if your issue depends on another one.
## Dependencies

This issue is blocked by the following ones:
- [ ] #ITEM
-->

## Checklist

- [ ] Add the target in our Multiplatform Gradle plugin and update the [unreleased changelog].
- [ ] Add the target in the [integration workflow].
- [ ] Add the target in the [delivery workflow].
- [ ] Close this issue as completed and update tracking ones if present.

[delivery workflow]: https://github.com/kotools/types/actions/workflows/delivery.yml
[integration workflow]: https://github.com/kotools/types/actions/workflows/integration.yml
[unreleased changelog]: https://github.com/kotools/types/blob/main/CHANGELOG.md#unreleased
