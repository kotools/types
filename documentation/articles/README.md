# 📰 Articles

This directory contains community-facing articles published across Kotlin
community channels.

## 🏷️ Naming convention

Each article is a pair of files sharing the same numeric index prefix:

| File                   | Purpose                        |
|------------------------|--------------------------------|
| `<n>-<slug>.md`        | Full article body              |
| `<n>-<slug>-teaser.md` | Short teaser for cross-posting |

When creating a new article, check the highest existing index in this directory
and increment it (e.g. the first article uses `1-`, the second uses `2-`).

## 📤 Distribution strategy

A single checklist, doable in one sitting (~1-2 hours). Publish on GitHub
Discussions first to establish the canonical URL, then reuse that link for every
other step.

Target cadence: one article per month, leaving room for core library
development between releases.

1. **GitHub Discussions** — Post `<n>-<slug>.md` as a new discussion in the
   **Announcements** category of kotools/types. This is the canonical URL —
   copy it for every step below.
2. **Dev.to** — Publish `<n>-<slug>.md` under the Kotools-branded Dev.to
   account/organization (consistent with the `@kotools_org` handle used on
   other channels) (tags: `kotlin`, `multiplatform`, `kmp`, `programming`,
   `kotlinserverside`) with `canonical_url` set to the GitHub Discussion URL.
3. **Kotlin Slack** — Post `<n>-<slug>-teaser.md` + the GitHub Discussion
   link in `#kotools`, `#multiplatform`, `#library-development`, and `#general`.
4. **Reddit** — Link to the GitHub Discussion in r/Kotlin, then cross-post to
   r/KotlinMultiplatform.
5. **Social media** — Short post + GitHub Discussion link, tagged `#kotlin`
   `#kmp` `#kotlinmultiplatform` `#KotlinServerSide`:
    - Twitter/X
    - Mastodon (kotlin.social)
    - LinkedIn — professional angle (e.g. cross-platform bugs in production)
6. **Kotlin Weekly** — Submit the GitHub Discussion URL at kotlinweekly.net
   (check the site for the current submission process).

The `#KotlinServerSide` tag also covers JetBrains' monthly community scouting
(amplification on @Kotlin / Kotlin Blog roundups) — no separate submission
needed.

> **Canonical URL:** The GitHub Discussion is the canonical source — it already
> lives in the project's own ecosystem, with no extra infrastructure to
> maintain. Dev.to syndicates it via `canonical_url`, deferring its SEO
> authority to the discussion. Reddit and social platforms don't support
> canonical tags, but since they only link out (never host a copy of the full
> text), this doesn't create duplicate-content issues.

## 📊 Publishing status

| Article              | Status   |
|----------------------|----------|
| `1-integer-division` | 📝 Draft |

Update the status to "✅ Published" once an article has gone through the full
checklist above. Add a new row for each new article, named after its numeric
prefix and slug.

## ✍️ Authoring guidelines

- Write for a technical audience familiar with Kotlin and Kotlin Multiplatform.
- Lead with the problem, then the cause, then the solution.
- Include a platform comparison table and minimal, self-contained code snippets.
- Link to the official API docs at https://types.kotools.org.
- Do not promote unreleased features (SNAPSHOT versions).
