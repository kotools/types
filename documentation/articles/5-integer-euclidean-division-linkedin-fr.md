# 📣 Euclidean division teaser — LinkedIn (FR)

French-language variant of the LinkedIn post described in `README.md`'s
distribution strategy (step 5). Replace the placeholder link below with the
GitHub Discussion URL before posting.

---

**➗ `-7 % 2` ne devrait jamais valoir `-1` — et ce détail a déjà fait planter
du code en production**

Le `%` de Kotlin tronque vers zéro, donc un dividende négatif produit un
reste négatif. Mathématiquement valide, mais rarement ce à quoi s'attend un
développeur qui utilise `value % size` pour indexer un tableau — et qui
découvre le bug en production, sur le seul chemin d'entrée que personne
n'avait testé avec une valeur négative. Kotools Types 5.2.0 rend
`Integer.div`/`rem` euclidiens : le reste est toujours positif ou nul, et le
diviseur est typé `NonZeroInteger`, ce qui élimine la division par zéro à la
compilation.

Nous avons détaillé les trois définitions de la division entière et pourquoi
l'euclidienne est celle qu'on apprend à l'école.

[Lire l'article complet]() 👇
