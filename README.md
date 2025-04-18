# ZombieDungeon 🔫 Un jeu 2D orienté objet en Kotlin

Un jeu vidéo **solo en 2D**, développé en **Kotlin**, dans lequel vous explorez des cartes, combattez des ennemis, et débloquez de nouveaux niveaux après avoir vaincu un boss.  
Projet réalisé dans le cadre d’un travail scolaire à l’**École Polytechnique de Bruxelles**.

---

![Class Diagram](https://raw.githubusercontent.com/elingeniero-skully/ZombieDungeon/refs/heads/main/UML/class_diagram.png)
![Screen 1](https://raw.githubusercontent.com/elingeniero-skully/ZombieDungeon/refs/heads/main/screenshots/1.png)
![Screen 2](https://raw.githubusercontent.com/elingeniero-skully/ZombieDungeon/refs/heads/main/screenshots/2.png)

--

## 🎮 Gameplay

- 🌍 **Exploration libre** sur une map personnalisable.
- ⚔️ **Combat dynamique** : utilisez un **Gun** (attaque à distance) ou un **Knife** (corps-à-corps).
- 👾 **Mobs & Boss** : éliminez les ennemis, battez le boss pour ouvrir la porte du niveau suivant.
- 🎒 **Inventaire intégré** : Changez d’arme à tout moment.
- 🧱 **Système de cartes JSON** : les maps sont générées automatiquement à partir de fichiers `.json`, ce qui permet une création ultra-simple de nouveaux niveaux.

---

## 🛠️ Technologies

- **Langage** : Kotlin
- **Lib graphique** : Android (Canvas, Views, etc.)
- **Structure** : Architecture orientée objet
- **Outils** :
  - IntelliJ IDEA / Android Studio
  - Gradle
  - Git / GitHub

---

## 🗺️ Ajouter une carte (niveau)

Créez un fichier .json dans le dossier assets/ et ajoutez l'include dans le fichier Game.kt. 
Voici level1.json à titre d'exemple :

```json
{
  "width":8,
  "height":8,
  "cases":[
    {"x" : 0, "y" : 0, "type" : "WALL", "details" : {}},
    {"x" : 1, "y" : 0, "type" : "DOOR", "details" : {}},
    {"x" : 4, "y" : 3, "type" : "PLAYER", "details" : {
      "weapons" : [
      {"type" : "GUN", "name" : "FN Scar", "damage" : 75},
      {"type" : "KNIFE", "name" : "Couteau de Kebab", "damage" : 25}
    ]
    }},
    {"x" : 0, "y" : 4, "type" : "WALL", "details" : {}},
    {"x" : 0, "y" : 2, "type" : "BOSS", "details" : {
      "movementPattern" : "random",
      "weapons" : [
        {"type" : "GUN", "name" : "FN Five-Seven", "damage" : 50}
      ]
    }},
    {"x" : 1, "y" : 4, "type" : "MOB", "details" : {
      "movementPattern" : "follow",
      "weapons" : [
        {"type" : "GUN", "name" : "FN Five-Seven", "damage" : 10}
      ]
    }}
  ]
}

```
