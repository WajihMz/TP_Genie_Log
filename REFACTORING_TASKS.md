# Plan de Refactoring - RPG Player Manager

Ce document liste toutes les tâches de refactoring à effectuer pour améliorer la qualité du code, basé sur l'analyse du projet de référence.

## 📋 Vue d'ensemble des changements majeurs

Le projet de référence utilise une architecture orientée objet avec :
- **Héritage** : `AbstractPlayer` comme classe abstraite de base
- **Polymorphisme** : Chaque classe (Adventurer, Archer, Dwarf) étend `AbstractPlayer`
- **Énumérations** : `STATS` pour les statistiques, `ITEM` pour les objets
- **Encapsulation** : Gestion de l'argent via classe `Money`, objets via classe `ITEM`
- **Exceptions personnalisées** : Pour une meilleure gestion d'erreurs
- **StringBuilder** : Pour la construction de chaînes (affichage)

---

## 🎯 Tâches de Refactoring

### Phase 1 : Création des énumérations et structures de base

#### 1.1 Créer l'énumération STATS
- [ ] Créer `src/main/java/re/forestier/edu/rpg/STATS.java`
- [ ] Définir les valeurs : `INT`, `DEF`, `CHA`, `ATK`, `ALC`, `VIS`
- [ ] Commiter : `git add . && git commit -m "refactor: créer énumération STATS pour les statistiques"`

#### 1.2 Créer la classe Money pour gérer l'argent
- [ ] Créer `src/main/java/re/forestier/edu/rpg/Money.java`
- [ ] Implémenter `addMoney(int amount)` avec validation (pas de montant négatif)
- [ ] Implémenter `removeMoney(int amount)` avec validation (pas de montant négatif, pas de solde négatif)
- [ ] Implémenter `getAmount()` pour récupérer le montant
- [ ] Commiter : `git add . && git commit -m "refactor: créer classe Money pour encapsuler la gestion de l'argent"`

#### 1.3 Créer l'énumération ITEM pour les objets
- [ ] Créer `src/main/java/re/forestier/edu/rpg/ITEM.java`
- [ ] Définir tous les objets avec leurs propriétés :
  - `name` : nom de l'objet
  - `description` : description de l'objet
  - `weight` : poids de l'objet
  - `value` : valeur de l'objet
- [ ] Implémenter `randomItem()` pour obtenir un objet aléatoire
- [ ] Implémenter `toString()` et `toMarkdown()` pour l'affichage
- [ ] Remplacer le tableau `objectList` dans `UpdatePlayer` par l'énumération
- [ ] Commiter : `git add . && git commit -m "refactor: créer énumération ITEM pour remplacer les chaînes d'objets"`

#### 1.4 Créer les exceptions personnalisées
- [ ] Créer `src/main/java/re/forestier/edu/Exceptions/InventoryException.java`
- [ ] Créer `src/main/java/re/forestier/edu/Exceptions/MoneyManagerException.java`
- [ ] Créer `src/main/java/re/forestier/edu/Exceptions/NotEnoughMoneyException.java`
- [ ] Toutes doivent étendre `RuntimeException`
- [ ] Commiter : `git add . && git commit -m "refactor: créer exceptions personnalisées pour meilleure gestion d'erreurs"`

---

### Phase 2 : Refactoring de la classe Player

#### 2.1 Créer la classe abstraite AbstractPlayer
- [ ] Créer `src/main/java/re/forestier/edu/rpg/AbstractPlayer.java`
- [ ] Déplacer les champs communs depuis `player` :
  - `playerName`, `avatarName` (renommer `Avatar_name`)
  - `money` → utiliser `Money moneyManager`
  - `maxHealthPoints` (renommer `healthpoints`)
  - `currentHealthPoints` (renommer `currenthealthpoints`)
  - `xp`, `inventory`, `capacity` (nouveau : poids max)
- [ ] Déplacer les méthodes communes :
  - `getMoney()`, `addMoney()`, `removeMoney()`
  - `getXp()`, `addXp()`, `retrieveLevel()`
  - `getCurrentHealthPoints()`, `addCurrentHealthPoints()`, `removeCurrentHealthPoints()`
  - `isKO()`
- [ ] Créer méthode abstraite `initStats()` pour initialiser les stats par classe
- [ ] Créer méthode abstraite `resolveEndOTurn()` pour la logique de fin de tour
- [ ] Implémenter `toString()` avec `StringBuilder` (remplacer `Affichage.afficherJoueur()`)
- [ ] Implémenter `toMarkdown()` pour l'affichage Markdown
- [ ] Implémenter `getStatistics()` qui retourne toutes les stats actuelles
- [ ] Implémenter `getStatistic(STATS stat)` qui calcule dynamiquement la stat pour le niveau actuel
- [ ] Changer le système de stats : utiliser `HashMap<STATS, Integer[]>` au lieu de `HashMap<String, Integer>`
- [ ] Commiter : `git add . && git commit -m "refactor: créer classe abstraite AbstractPlayer avec logique commune"`

#### 2.2 Refactorer retrieveLevel() dans AbstractPlayer
- [ ] Simplifier `retrieveLevel()` avec un tableau `LVL_ABSOLUTE_XP_REQ = {10, 27, 57, 111}`
- [ ] Utiliser une boucle `while` au lieu de multiples `if/else`
- [ ] Supprimer le HashMap inutile dans la méthode
- [ ] Commiter : `git add . && git commit -m "refactor: simplifier retrieveLevel() avec tableau et boucle"`

#### 2.3 Refactorer addXp() dans AbstractPlayer
- [ ] Simplifier `addXp()` : juste ajouter XP et vérifier si niveau a changé
- [ ] Utiliser `addRandomObject()` qui utilise `ITEM.randomItem()`
- [ ] Supprimer la logique de mise à jour des capacités (sera géré par `getStatistic()`)
- [ ] Commiter : `git add . && git commit -m "refactor: simplifier addXp() et utiliser ITEM.randomItem()"`

#### 2.4 Implémenter la gestion du poids (capacity)
- [ ] Ajouter champ `capacity` (poids max) dans `AbstractPlayer`
- [ ] Implémenter `getLoad()` qui calcule le poids total de l'inventaire
- [ ] Implémenter `getRemainingCapacity()` qui retourne la capacité restante
- [ ] Modifier `addItem()` pour vérifier la capacité avant d'ajouter
- [ ] Commiter : `git add . && git commit -m "refactor: ajouter gestion du poids des objets"`

---

### Phase 3 : Créer les classes concrètes (Adventurer, Archer, Dwarf)

#### 3.1 Créer la classe Adventurer
- [ ] Créer `src/main/java/re/forestier/edu/classes/Adventurer.java`
- [ ] Faire hériter de `AbstractPlayer`
- [ ] Implémenter `initStats()` avec les stats de l'aventurier (tableau par niveau)
- [ ] Implémenter `resolveEndOTurn()` :
  - Si KO → afficher message et retourner
  - Si HP < 50% → ajouter 2 HP
  - Si niveau < 3 → retirer 1 HP
- [ ] Définir `className = "Adventurer"` et `classDescription`
- [ ] Commiter : `git add . && git commit -m "refactor: créer classe Adventurer héritant d'AbstractPlayer"`

#### 3.2 Créer la classe Archer
- [ ] Créer `src/main/java/re/forestier/edu/classes/Archer.java`
- [ ] Faire hériter de `AbstractPlayer`
- [ ] Implémenter `initStats()` avec les stats de l'archer
- [ ] Implémenter `resolveEndOTurn()` :
  - Si KO → afficher message et retourner
  - Si HP < 50% → ajouter 1 HP
  - Si a "Magic Bow" → ajouter (HP/8 - 1) HP
- [ ] Définir `className = "Archer"` et `classDescription`
- [ ] Commiter : `git add . && git commit -m "refactor: créer classe Archer héritant d'AbstractPlayer"`

#### 3.3 Créer la classe Dwarf
- [ ] Créer `src/main/java/re/forestier/edu/classes/Dwarf.java`
- [ ] Faire hériter de `AbstractPlayer`
- [ ] Implémenter `initStats()` avec les stats du nain
- [ ] Implémenter `resolveEndOTurn()` :
  - Si KO → afficher message et retourner
  - Si HP < 50% :
    - Si a "Holy Elixir" → ajouter 2 HP
    - Sinon → ajouter 1 HP
- [ ] Définir `className = "Dwarf"` et `classDescription`
- [ ] Commiter : `git add . && git commit -m "refactor: créer classe Dwarf héritant d'AbstractPlayer"`

---

### Phase 4 : Refactoring de UpdatePlayer

#### 4.1 Supprimer abilitiesPerTypeAndLevel()
- [ ] Supprimer la méthode `abilitiesPerTypeAndLevel()` de `UpdatePlayer`
- [ ] Les stats sont maintenant gérées dans chaque classe concrète via `initStats()`
- [ ] Commiter : `git add . && git commit -m "refactor: supprimer abilitiesPerTypeAndLevel(), stats gérées par classes"`

#### 4.2 Refactorer majFinDeTour() → resolveEndOTurn()
- [ ] Supprimer `majFinDeTour()` de `UpdatePlayer`
- [ ] La logique est maintenant dans chaque classe via `resolveEndOTurn()`
- [ ] Utiliser le polymorphisme : appeler `player.resolveEndOTurn()`
- [ ] Commiter : `git add . && git commit -m "refactor: remplacer majFinDeTour() par resolveEndOTurn() polymorphique"`

#### 4.3 Simplifier UpdatePlayer
- [ ] Vérifier si `UpdatePlayer` est encore nécessaire
- [ ] Si oui, ne garder que les méthodes vraiment statiques/utilitaires
- [ ] Sinon, supprimer la classe
- [ ] Commiter : `git add . && git commit -m "refactor: simplifier/supprimer UpdatePlayer si nécessaire"`

---

### Phase 5 : Refactoring de Affichage

#### 5.1 Supprimer la classe Affichage
- [ ] Supprimer `Affichage.java`
- [ ] L'affichage est maintenant géré par `AbstractPlayer.toString()`
- [ ] Mettre à jour les tests pour utiliser `player.displayPlayer()` ou `player.toString()`
- [ ] Commiter : `git add . && git commit -m "refactor: supprimer Affichage, utiliser toString() d'AbstractPlayer"`

#### 5.2 Améliorer toString() avec StringBuilder
- [ ] Vérifier que `toString()` dans `AbstractPlayer` utilise `StringBuilder`
- [ ] Optimiser la construction de la chaîne
- [ ] N'afficher que les stats non nulles
- [ ] Commiter : `git add . && git commit -m "refactor: optimiser toString() avec StringBuilder"`

---

### Phase 6 : Refactoring de la classe player (suppression)

#### 6.1 Remplacer player par les classes concrètes
- [ ] Supprimer `player.java`
- [ ] Mettre à jour tous les tests pour utiliser `Adventurer`, `Archer`, `Dwarf`
- [ ] Mettre à jour `Main.java` si nécessaire
- [ ] Commiter : `git add . && git commit -m "refactor: supprimer player.java, utiliser classes concrètes"`

---

### Phase 7 : Améliorations supplémentaires

#### 7.1 Améliorer la gestion de l'argent
- [ ] Remplacer `Integer money` par `Money moneyManager` dans `AbstractPlayer`
- [ ] Utiliser les opérateurs `+=` et `-=` dans `Money`
- [ ] Commiter : `git add . && git commit -m "refactor: améliorer gestion argent avec Money et opérateurs"`

#### 7.2 Améliorer la gestion des HP
- [ ] Utiliser `Math.min()` et `Math.max()` pour plafonner les HP
- [ ] Simplifier les méthodes `addCurrentHealthPoints()` et `removeCurrentHealthPoints()`
- [ ] Commiter : `git add . && git commit -m "refactor: améliorer gestion HP avec Math.min/max"`

#### 7.3 Réduire la complexité cognitive
- [ ] Simplifier les conditions imbriquées dans `resolveEndOTurn()`
- [ ] Extraire des méthodes privées si nécessaire
- [ ] Utiliser des early returns pour réduire l'imbrication
- [ ] Commiter : `git add . && git commit -m "refactor: réduire complexité cognitive avec early returns"`

#### 7.4 Améliorer la cohérence du code
- [ ] Uniformiser les noms de variables (camelCase)
- [ ] Uniformiser les noms de méthodes
- [ ] Ajouter des commentaires Javadoc pour les méthodes publiques
- [ ] Commiter : `git add . && git commit -m "refactor: améliorer cohérence et documentation"`

#### 7.5 Implémenter la méthode sell()
- [ ] Ajouter `sell(ITEM item)` dans `AbstractPlayer`
- [ ] Ajouter `sell(ITEM item, AbstractPlayer other)` pour les ventes entre joueurs
- [ ] Gérer les exceptions appropriées
- [ ] Commiter : `git add . && git commit -m "refactor: implémenter méthode sell() pour vendre objets"`

---

### Phase 8 : Nettoyage final

#### 8.1 Supprimer le code mort
- [ ] Supprimer les commentaires inutiles (ex: recette en russe dans player.java)
- [ ] Supprimer les variables non utilisées
- [ ] Supprimer les imports inutilisés
- [ ] Commiter : `git add . && git commit -m "refactor: supprimer code mort et commentaires inutiles"`

#### 8.2 Vérifier que tous les tests passent
- [ ] Exécuter `./gradlew test`
- [ ] Vérifier que tous les tests passent
- [ ] Corriger les éventuels problèmes
- [ ] Commiter : `git add . && git commit -m "refactor: vérifier que tous les tests passent"`

#### 8.3 Relancer PIT
- [ ] Exécuter PIT pour vérifier la couverture de mutations
- [ ] Noter que certaines mutations ne sont plus couvertes (normal après refactoring)
- [ ] Documenter pourquoi certaines mutations ne sont plus couvertes

---

## 📝 Notes importantes

### Règles à respecter
- ✅ **NE PAS modifier la logique des tests** (seulement renommer méthodes/classes si nécessaire)
- ✅ **Commiter après chaque petite modification**
- ✅ **Vérifier que les tests passent après chaque commit**
- ✅ **Faire des changements minimes à la fois**

### Ordre recommandé
1. Commencer par les structures de base (énumérations, Money, ITEM)
2. Créer AbstractPlayer avec les fonctionnalités communes
3. Créer les classes concrètes une par une
4. Supprimer l'ancien code progressivement
5. Nettoyer et optimiser

### Points d'attention
- Les tests doivent continuer à fonctionner à chaque étape
- Utiliser le polymorphisme au lieu de `if/else` sur les types
- Encapsuler les données (pas de champs publics sauf si vraiment nécessaire)
- Utiliser des exceptions appropriées au lieu de retourner silencieusement

---

## 🎯 Résultat attendu

Après le refactoring, le code devrait :
- ✅ Utiliser l'héritage et le polymorphisme
- ✅ Avoir une meilleure séparation des responsabilités
- ✅ Être plus facile à maintenir et étendre
- ✅ Respecter les principes SOLID
- ✅ Avoir une complexité cognitive réduite
- ✅ Utiliser des structures de données appropriées (énumérations, classes)

---

## 📚 Références

- Projet de référence : `/Users/mz.wajih/Desktop/M1/GénieLogiciel/TP/tp-m1-genielog-main`
- Structure cible :
  - `rpg/AbstractPlayer.java` : Classe abstraite de base
  - `rpg/STATS.java` : Énumération des statistiques
  - `rpg/ITEM.java` : Énumération des objets
  - `rpg/Money.java` : Gestion de l'argent
  - `classes/Adventurer.java` : Classe concrète
  - `classes/Archer.java` : Classe concrète
  - `classes/Dwarf.java` : Classe concrète
  - `Exceptions/` : Exceptions personnalisées

