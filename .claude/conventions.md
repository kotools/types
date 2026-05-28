# Code style & conventions

Supplement to `CLAUDE.md`. Captures rules enforced by `.editorconfig` plus
quick-reference constraints for the most error-prone areas.

## Formatting (from `.editorconfig`)

- **Encoding**: UTF-8
- **Line endings**: LF (`end_of_line = lf`)
- **Final newline**: always insert one
- **Max line length**: **80 characters**
  (visual guides at 80 and 90; off for issue templates and `gradle/*.toml`)
- **Continuation indent**: 8 spaces (Kotlin/KTS)
- **Star imports**: disabled — threshold set to 999 999 to prevent them

| File type       | Indent style | Size |
|-----------------|--------------|------|
| `*.kt`, `*.kts` | space        | 4    |
| `*.md`          | space        | 4    |
| `*.yml`         | space        | 2    |

## Key Kotlin rules (from `CLAUDE.md`)

- Explicit visibility modifier on **every** public declaration.
- New public API → annotate with `@ExperimentalKotoolsTypesApi`.
- KDoc samples → `SAMPLE: <FQN>` reference, never inline code blocks.
- ABI change → run `./gradlew :types:apiDump` and commit the updated
  `.api` files in `subprojects/library/src/api/`.
- `allWarningsAsErrors` is on — fix warnings, don't suppress them.

## Commit format

`<gitmoji> <message> (<#issue-or-PR>)`
