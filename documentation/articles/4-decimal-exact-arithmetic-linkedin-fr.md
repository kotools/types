# 📣 Decimal teaser — LinkedIn (FR)

French-language variant of the LinkedIn post described in `README.md`'s
distribution strategy (step 5). Replace the placeholder link below with the
GitHub Discussion URL before posting.

---

**🎯 `0.1 + 0.2 != 0.3` : un piège classique qui devient un vrai risque en
production dès qu'on manipule de l'argent**

Kotools Types 5.2.0 introduit `Decimal`, un type qui n'utilise jamais de
virgule flottante : en interne, un entier mis à l'échelle plutôt qu'un
`Double`. Résultat : addition, soustraction et multiplication sont toujours
exactes — un point critique pour tout ce qui touche à la facturation, aux
montants financiers ou aux mesures. La division est volontairement absente de
l'API : diviser deux décimaux exacts peut produire un nombre sans
représentation décimale finie, et `Decimal` refuse de l'arrondir
silencieusement à votre place.

Nous avons détaillé la représentation interne et ce choix de conception.

[Lire l'article complet]() 👇
