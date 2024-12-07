# Révision 
- Pour le devoir, l'acteur secondaire ici devrait être **l'API de la ville**.
- Pas de généralisation entre les deux acteurs dans le diagramme de CU, comme proposé dans la solution du diagramme de CU.

---

# Architecture 
- Votre architecture donne l'impression qu'il y a deux applications, une pour les intervenants et une pour les résidents, mais partageant la même base de données.
- Le menu principal ne communique pas directement avec la base de données, de même pour l'API.
- L'API est externe à notre application.
- La frontière du système n'est pas claire et ne permet pas de distinguer les composantes internes du système des dépendances externes.

---

# Diagramme de classe
- **Diagramme de classe incomplet**

## Identification des entités
- On aurait pu généraliser les classes **Intervenant** et **Résident**.
- Manque de types de paramètres pour certains attributs.
- Paramètres manquants dans certains cas.
- Absence de classes importantes comme **Requête**, **Travail**, **Entrave**, et **Notification**.

## Identification des relations
- Les multiplicités ne sont pas bien définies.
- Voir plus de détails dans l'autre fichier de feedback.

---

# Diagramme de séquence

## Formalisme et cohérence 
- Certaines classes sont absentes du diagramme de classe.
- Méthodes manquantes dans le diagramme de classe.
- Méthodes utilisées dans le diagramme de séquence avec une signature différente de celle présente dans le diagramme de classe.

## Consulter les entraves
- Voir les détails de la correction dans l'autre fichier de feedback.

## Soumettre une requête de travail
- Voir les détails de la correction dans l'autre fichier de feedback.

## Consulter la liste des requêtes de travail
- Voir les détails de la correction dans l'autre fichier de feedback.

---
## Discussion design
---

# Rapport et Git
- **Ok**

---

# Tests
- Impossible d'exécuter les tests, car une dépendance utilisée est manquante dans les fichiers du projet, ce qui empêche leur exécution.

---

# Implémentation

## Fonctionnalités 
- **Consulter la liste des requêtes de travail (intervenant)** :
  - Lorsque l'on choisit l'option 1 pour consulter la liste des requêtes de travail, les requêtes ne s'affichent pas.

## Robustesse & Utilisabilité

### Gestion des erreurs
- Pas de gestion des erreurs : le code génère une erreur en cas de mauvaise entrée.

## Code 
- **Ok**

## Bonus 1: Exploitation de Git 
- **Ok**
##  Bonus 2: Architecture REST

