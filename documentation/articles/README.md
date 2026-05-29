# Articles

This directory contains community-facing articles published on Reddit and
Kotlin Slack.

## Naming convention

Each article is a pair of files sharing the same numeric index prefix:

| File | Purpose |
|------|---------|
| `<n>-<slug>.md` | Full article body |
| `<n>-<slug>-teaser.md` | Short teaser for cross-posting |

When creating a new article, check the highest existing index in this directory
and increment it (e.g. the first article uses `1-`, the second uses `2-`).

## Distribution strategy

| File | Published to |
|------|-------------|
| `<n>-<slug>.md` | Reddit (r/Kotlin, cross-post to r/KotlinMultiplatform) and Kotlin Slack `#kotools` |
| `<n>-<slug>-teaser.md` | Kotlin Slack `#multiplatform`, `#library-development`, `#general` — each links back to the `#kotools` post |

## Authoring guidelines

- Write for a technical audience familiar with Kotlin and Kotlin Multiplatform.
- Lead with the problem, then the cause, then the solution.
- Include a platform comparison table and minimal, self-contained code snippets.
- Link to the official API docs at https://types.kotools.org.
- Do not promote unreleased features (SNAPSHOT versions).
