# 📣 Bounded hierarchy teaser — LinkedIn (FR)

French-language variant of the LinkedIn post described in `README.md`'s
distribution strategy (step 5). Replace the placeholder link below with the
GitHub Discussion URL before posting.

---

**🔒 "Cet entier ne peut jamais être négatif" : un `require()` répété à
chaque appel, ou une garantie du compilateur ?**

Dans la plupart des bases de code, cette contrainte est vérifiée avec un
`require(value >= 0)` copié à chaque point d'entrée — facile à oublier, à
dupliquer, et impossible à vérifier pour le compilateur. Kotools Types 5.2.0
ajoute `NonZeroInteger`, `NonNegativeInteger` et `NonPositiveInteger` : le
signe devient une propriété du type, avec des opérateurs arithmétiques qui
préservent cette garantie à travers additions et multiplications.

Nous avons détaillé cette nouvelle hiérarchie de types et son arithmétique
qui préserve les contraintes de signe.

[Lire l'article complet]() 👇
