<!--
    Copyright 2023 Kotools S.A.S.
    Use of this source code is governed by the MIT license.
-->

# Versioning strategy

For versioning the stable API of Kotools Types, we have a pragmatic approach
based on the work needed from consumers to migrate their code.

Here's the different type of releases that we ship:

- **Major releases (`X`)** that will need a lot of effort from consumers to
  migrate their code.
- **Minor releases (`X.Y`)** that will need a little bit of work from consumers
  to migrate their code.
- **Patch releases (`X.Y.Z`)** that will need no work at all from consumers to
  migrate their code.

Please note that the experimental API of Kotools Types doesn't follow the same
versioning strategy: its declarations can evolve or can be removed at any point
in time.
This means that we can ship changes to the experimental API in any type of
releases listed above.
