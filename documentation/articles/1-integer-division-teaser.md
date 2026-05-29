# Integer division teaser — Kotlin Slack cross-post

Post this teaser in `#multiplatform`, `#library-development`, and `#general`,
replacing the URL below with the published Dev.to article URL.

---

**KMP gotcha: `12 / 0` behaves differently on every platform**

On Kotlin/JS, `12 / 0` silently returns `0`. On JVM and Native it throws
`ArithmeticException`. That guard you wrote and tested on JVM? It is silently
bypassed when the same code runs on JS.

We wrote a short post in #kotools about what causes this and how the `Integer`
type in Kotools Types 5.1.1 fixes it consistently across all three targets —
including null-safe `divOrNull` and `remOrNull` variants for those who prefer
not to catch exceptions.

https://dev.to/kotools/<article-slug>
