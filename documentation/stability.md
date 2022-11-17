# Stability of Kotools components

The Kotools components follow the _Feedback Loop Principle_ by releasing
improvements early for the community to try out.
For doing so, components are not yet released as **Stable**.

## Stability levels

Here's a list of available stability levels and their meaning:

- **Experimental**: component marked with this level should be used only in toy
  projects. If the idea introduced by this component doesn't work out, we may
  drop it any minute.
- **Alpha**: component marked with this level should be used wisely, because
  migration issues is still possible at this stage.
- **Beta**: component marked with this level can be used as a pre-stable stage.
  At this stage, we should do our best to minimize migration issues for users.
- **Stable**: component marked with this level is considered as mature enough
  for being used in production code.

## Stability in version numbers

While adhering to the
[Semantic Versioning](https://semver.org/spec/v2.0.0.html), we also indicate the
stability of a release by prefixing the version number like so:

- `MAJOR.MINOR.PATH-SNAPSHOT`: version currently in development. **No release
  should have this version.**
- `MAJOR.MINOR.PATCH-alpha.N`, where `N > 0`: pre-stable release that could be
  integrated in other **Alpha** projects. All new features at this stage should
  be introduced as **Alpha**.
- `MAJOR.MINOR.PATCH-beta.N`, where `N > 0`: pre-stable release that can be used
  in other **Alpha** or **Beta** projects. All new features at this stage should
  be introduced as **Beta**.
- `MAJOR.MINOR.PATCH`: stable release that can be used in production code.
