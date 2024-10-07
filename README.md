# Projet "Ma Ville" - Application de Gestion des Travaux

## Brève Description du Projet

**Ma Ville** est une application Java destinée à faciliter la gestion des travaux publics et privés dans la ville de Montréal. Cette application permet aux résidents et aux intervenants de se connecter, consulter, soumettre des requêtes de travaux, et recevoir des notifications concernant les projets dans leur quartier. Le projet a été développé dans le cadre d'un devoir universitaire.

### Fonctionnalités principales :
- **Résidents** peuvent :
  - S'inscrire et se connecter.
  - Consulter les travaux en cours ou à venir.
  - Soumettre des requêtes de travaux.
  - Suivre l'état de leurs requêtes et recevoir des notifications personnalisées.
  - Donner un avis sur les travaux terminés.
  - Signaler un problème à la ville.

- **Intervenants** peuvent :
  - S'inscrire et se connecter.
  - Consulter les requêtes de travaux soumises par les résidents.
  - Soumettre des candidatures pour des travaux.
  - Mettre à jour les informations d'un chantier (statut, avancement).

### Organisation du Répertoire

Le répertoire `src` contient les différentes classes Java qui composent le projet :

```plaintext
src
├── AppSimulation.java        # Classe pour simuler des chargements et délais
├── AuthenticationService.java # Service d'authentification pour résidents et intervenants
├── Intervenant.java           # Classe représentant les intervenants
├── Main.java                  # Classe principale pour démarrer l'application
├── Menu.java                  # Classe pour gérer l'interface de menu (connexion, inscription, etc.)
├── Resident.java              # Classe représentant les résidents
└── User.java                  # Interface utilisateur commune aux résidents et intervenants
```
- Les **auteurs** :
  - Tarik Hireche
  - Ilyesse Bouzammita
  - Karim Ndoye
