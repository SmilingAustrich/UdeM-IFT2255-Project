# 🌆 Projet "Ma Ville" - Application de Gestion des Travaux

## 📄 Brève Description du Projet

**Ma Ville** est une application développée en **Java** visant à simplifier la gestion des travaux publics et privés à Montréal. Elle permet aux **résidents** et aux **intervenants** de se connecter, consulter, soumettre des requêtes de travaux, et recevoir des notifications personnalisées liées aux projets dans leurs quartiers. Ce projet a été réalisé dans le cadre d'un **devoir universitaire**.

## 🚀 Fonctionnalités Principales

### Résidents 🏡
Les résidents ont accès aux fonctionnalités suivantes :
- **S'inscrire** et **se connecter**.
- **Consulter les travaux en cours ou à venir** dans leur quartier.
- **Soumettre des requêtes de travaux** en fonction de leurs besoins.
- **Suivre l'état de leurs requêtes** et **recevoir des notifications personnalisées**.
- **Donner un avis** sur les travaux une fois terminés.
- **Participer à la planification des travaux**
- **Signaler un problème** à la ville.

### Intervenants 🛠️
Les intervenants peuvent :
- **S'inscrire** et **se connecter**.
- **Consulter les requêtes de travaux** soumises par les résidents.
- **Soumettre leur candidature** pour participer à des travaux.
- **Soumettre un nouveau projet** de travaux
- **Mettre à jour les informations d'un chantier** en cours (statut, avancement, etc.).

## 📂 Organisation du Répertoire

Le répertoire `src` contient les classes Java principales du projet, organisées de la manière suivante :

```plaintext
src
├── AppSimulation.java        # Gère la simulation des chargements et des délais.
├── AuthenticationService.java # Service pour gérer l'authentification des résidents et intervenants.
├── Intervenant.java           # Classe représentant un intervenant (entreprise ou particulier).
├── Main.java                  # Classe principale pour démarrer l'application.
├── Menu.java                  # Gère l'interface en ligne de commande pour la navigation.
├── Resident.java              # Classe représentant un résident de Montréal.
└── User.java                  # Interface commune pour les utilisateurs résidents et intervenants.
```

## 👥 Auteurs

Ce projet a été réalisé par :

- **Tarik Hireche**  
  * _Développement du service d'authentification, de l'interface utilisateur ainsi que de ses implémentations_
  * _Création du diagramme de cas d'utilisations, scénarios et du README_
  
- **Karim Ndoye**  
  * _Développement des fonctionnalités résidents et intervenant._
  * _Création du glossaire, analyse_
  

- **Ilyesse Bouzammita**  
  * _Développement du menu principal des intervenants et des résidents._
  * _Création du diagramme d'activités_


