# Versioning strategy

The [Semantic Versioning][semantic-versioning] specifications being widely used
in open-source projects for communicating what type of changes are included in
a new release, the stable API of Kotools Types will follow this versioning
strategy starting from version `4.3.2`.

For instance, if the current version is `X.Y.Z`:

- We will release a **patch** version `X.Y.(Z + 1)` if we've introduced backward
  compatible fixes to the stable API.
  This includes implementation fixes, documentation improvements...
- We will release a **minor** version `X.(Y + 1).0` if we've introduced
  compatible changes to the stable API.
  This includes stabilization of experimental declarations, deprecations of
  stable declarations...
- We will release a **major** version `(X + 1).0.0` if we've introduced
  incompatible changes to the stable API.
  This includes removing declarations, adding a type to a sealed type
  hierarchy...

Please note that the experimental API will not follow the
[Semantic Versioning][semantic-versioning] specifications: its declarations can
evolve or can be removed at any point in time.
This means that we can ship changes to the experimental API in any type of
release listed above.

[semantic-versioning]: https://semver.org
