# Articles

This directory contains community-facing articles published across Kotlin
community channels.

## Naming convention

Each article is a pair of files sharing the same numeric index prefix:

| File                   | Purpose                        |
|------------------------|--------------------------------|
| `<n>-<slug>.md`        | Full article body              |
| `<n>-<slug>-teaser.md` | Short teaser for cross-posting |

When creating a new article, check the highest existing index in this directory
and increment it (e.g. the first article uses `1-`, the second uses `2-`).

## Distribution strategy

Publish in this order so that a canonical public URL exists before submitting
to aggregators and newsletters.

### Step 1 — Establish a canonical public URL (Dev.to)

Publish `<n>-<slug>.md` on **Dev.to** first (tags: `kotlin`, `multiplatform`,
`kmp`, `programming`). Dev.to renders Markdown natively. The resulting URL is
used as the reference link in all subsequent steps.

> **Canonical URL tradeoff:** Publishing on Dev.to without setting a
> `canonical_url` builds SEO authority for `dev.to`, not `kotools.org`. This
> is acceptable for now, but if a dedicated blog under `kotools.org` is set up
> in the future, the flow should change to: publish on `kotools.org` first →
> syndicate to Dev.to with `canonical_url` pointing back to the Kotools post.
> Reddit cannot be used as a canonical source — it does not support canonical
> meta tags.

### Step 2 — Kotlin Slack (same day)

| Channel                | Action                                           |
|------------------------|--------------------------------------------------|
| `#kotools`             | Post the full article — canonical Slack location |
| `#multiplatform`       | Post `<n>-<slug>-teaser.md` + Dev.to link        |
| `#library-development` | Post `<n>-<slug>-teaser.md` + Dev.to link        |
| `#general`             | Post `<n>-<slug>-teaser.md` + Dev.to link        |

### Step 3 — Reddit (same day)

Link to the Dev.to article:
- Primary: r/Kotlin
- Cross-post: r/KotlinMultiplatform

### Step 4 — Kotlin Weekly (within the week)

Submit the Dev.to URL to Kotlin Weekly (kotlinweekly.net, ~10k subscribers).
Check the site for the current submission process.

### Step 5 — Social media (same day as Dev.to)

| Platform                 | Action                                                                |
|--------------------------|-----------------------------------------------------------------------|
| Twitter / X              | Short post with `#kotlin` `#kmp` `#kotlinmultiplatform` + Dev.to link |
| Mastodon (kotlin.social) | Same short post                                                       |
| LinkedIn                 | Professional angle — cross-platform bugs in production                |

### Step 6 — JetBrains amplification (passive)

Tag the Dev.to post and social posts with `#KotlinServerSide`. JetBrains scouts
community content monthly and amplifies standout posts on @Kotlin and in Kotlin
Blog roundups. No direct submission needed.

### Step 7 — DZone (optional, longer lead time)

DZone accepts articles ≥ 800 words with a 7–12 business-day review. Submit if
broader reach beyond the Kotlin community is desired.

## Authoring guidelines

- Write for a technical audience familiar with Kotlin and Kotlin Multiplatform.
- Lead with the problem, then the cause, then the solution.
- Include a platform comparison table and minimal, self-contained code snippets.
- Link to the official API docs at https://types.kotools.org.
- Do not promote unreleased features (SNAPSHOT versions).
