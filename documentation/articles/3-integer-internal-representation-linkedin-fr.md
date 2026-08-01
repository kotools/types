# 📣 Integer representation teaser — LinkedIn (FR)

French-language variant of the LinkedIn post described in `README.md`'s
distribution strategy (step 5). Replace the placeholder link below with the
GitHub Discussion URL before posting.

---

**⚡ Pourquoi votre `Integer` Kotlin Multiplatform ralentissait à mesure que
les nombres grandissaient**

Avant la 5.2.0, `Integer` de Kotools Types stockait sa valeur sous forme de
`String` et la reparsait à chaque opération arithmétique — un coût invisible
sur de petits nombres, mais qui s'accumule en production dès que le code
manipule de grands entiers en boucle. La 5.2.0 délègue désormais à une vraie
représentation en précision arbitraire par plateforme (`BigInteger` sur JVM,
`BigInt` sur JS, une implémentation dédiée sur Native), sans reparsing entre
deux opérations.

Nous avons détaillé ce qui a changé, et pourquoi — l'un des changements les
plus demandés par la communauté.

[Lire l'article complet](https://github.com/kotools/types/discussions/1050) 👇
