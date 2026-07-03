# 📣 5.2.0 highlights teaser — LinkedIn (FR)

French-language variant of the LinkedIn post described in `README.md`'s
distribution strategy (step 5). Replace the placeholder link below with the
GitHub Discussion URL before posting.

---

**🚀 Kotools Types 5.2.0 : moins de bugs multiplateformes, moins de code de
validation à maintenir**

Pour les équipes qui exécutent du code Kotlin Multiplatform en production, cette
version s'attaque à quatre sources de bugs et de dette technique : une
représentation interne d'`Integer` plus rapide, un type `Decimal` pour une
arithmétique exacte (fini les erreurs d'arrondi `0.1 + 0.2 != 0.3`), une
division d'`Integer` désormais euclidienne et totale (sans exception ni valeur
nulle liée à un diviseur nul), et un signe encodé directement dans le type
(`NonZeroInteger`, `NonNegativeInteger`, `NonPositiveInteger`) pour supprimer
des `require()` disséminés dans le code.

Nous avons écrit un tour d'horizon de ces quatre changements.

[Lire l'article complet](https://github.com/kotools/types/discussions/1049) 👇
