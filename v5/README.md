# Kotools Types 5

This **under development module** contains code for the next **major** release
of this library.

## Design goals

- **[Bijection] with real world concepts:** Representing one concept with one
  explicit type for building comprehensible software.
- **Less is more:** Primary focus on type representation for designing explicit
  and safer Application Public Interfaces (API).
- **Avoiding useless dependencies:** Plain [Kotlin] objects with no dependencies
  except the standard library. Integrations with other tools should be done
  through specific modules.
- **Fail fast and recover easily:** Operations from Kotools Types throw an
  exception by default, but it is easy to recover by using the corresponding
  operation suffixed by `OrNull`.

[bijection]: https://en.wikipedia.org/wiki/Bijection
[kotlin]: https://kotlinlang.org
