# 🌆 Projet "Ma Ville" - Application de Gestion des Travaux (Devoir 2)

## 📄 Brève Description du Projet

**Ma Ville** est une application innovante développée en **Java** pour simplifier la gestion des travaux publics et privés à Montréal. Conçue pour répondre aux besoins des **résidents** et des **intervenants**, elle facilite la communication, améliore la planification des travaux, et contribue à minimiser les perturbations causées par les chantiers. Ce projet a été réalisé dans le cadre d'un **devoir universitaire**.

L'application offre une meilleure coordination entre les divers acteurs, assurant ainsi une plus grande transparence et une expérience utilisateur améliorée grâce à des notifications personnalisées et des fonctionnalités de planification participative.

## ✨ Fonctionnalités Principales

### Résidents 🏡
Les résidents peuvent :
- **S'inscrire** et **se connecter** pour accéder aux fonctionnalités personnalisées.
- **Consulter les travaux en cours ou à venir** dans leur quartier avec des filtres par type de travaux, rue, ou quartier.
- **Soumettre des requêtes de travaux** pour des projets résidentiels spécifiques.
- **Suivre l'état de leurs requêtes** et **recevoir des notifications** sur l'avancement.
- **Donner un avis** sur les travaux une fois terminés pour améliorer la qualité des projets futurs.
- **Participer à la planification des travaux**, en partageant des plages horaires préférées, favorisant ainsi une planification inclusive et harmonieuse.
- **Recevoir des notifications personnalisées** pour rester informé des projets dans leur quartier.

### Intervenants 🛠️
Les intervenants ont la possibilité de :
- **S'inscrire** et **se connecter** avec un identifiant valide de la ville.
- **Consulter les requêtes de travaux** soumises par les résidents et **soumettre leur candidature**.
- **Soumettre un nouveau projet** en précisant les quartiers et rues affectés ainsi que les détails des travaux.
- **Mettre à jour les informations d'un chantier**, notamment le statut et l'avancement, pour garantir une communication claire avec les résidents.

## 📂 Organisation du Répertoire

Le répertoire `src` contient les classes Java principales du projet, organisées comme suit :

```plaintext

src
├── User.java                   # Interface commune pour les utilisateurs résidents et intervenants.
├── Intervenant.java            # Classe représentant un intervenant (entreprise ou particulier).
├── Resident.java               # Classe représentant un résident avec des méthodes pour consulter les entraves et travaux.
├── Main.java                   # Classe principale pour démarrer l'application.
├── Menu.java                   # Gère l'interface en ligne de commande pour la navigation.
├── AuthenticationService.java  # Service pour gérer l'authentification des résidents et intervenants.
├── Database.java               # Classe contenant toutes les données du système.

```

## 👥 Auteurs

Ce projet a été réalisé par :

- **Tarik Hireche**  
  * _Développement du service d'authentification, de l'interface utilisateur et de ses implémentations_
  * _Création du diagramme de cas d'utilisation, scénarios et du README_
  * _Mise à jour des exigences et des risques pour le devoir 2_

- **Karim Ndoye**  
  * _Développement des fonctionnalités résidents et intervenant_
  * _Création du glossaire, analyse_
  * _Mise à jour de l'analyse des besoins matériels et solution de stockage pour le devoir 2_

- **Ilyesse Bouzammita**  
  * _Développement du menu principal des intervenants et des résidents_
  * _Création du diagramme d'activités_
  * _Mise à jour du diagramme d'activités et contribution à l'architecture pour le devoir 2_

## 📋 Instructions d'Installation et d'Exécution

### Prérequis
- **Java Development Kit (JDK)** version 8 ou supérieure.
- **IntelliJ IDEA** ou un autre IDE pour Java.

### Installation
1. Clonez le dépôt GitHub sur votre machine locale :
   ```sh
   git clone <url_du_dépôt>
   ```
2. Ouvrez le projet dans votre IDE préféré.

### Exécution
1. Compilez et exécutez la classe `Main.java`.
2. Suivez les instructions affichées dans l'interface en ligne de commande pour naviguer dans l'application.

### Tests
- Les tests unitaires sont disponibles dans le répertoire `src/test/java`.
- Utilisez **JUnit** pour exécuter les tests.

### Release
- Une release est disponible pour chaque jalon du projet. Vous pouvez la télécharger depuis l'onglet **Releases** du dépôt GitHub.

## 🎨 Architecture et Design

Le design du projet a été pensé pour favoriser la modularité, la flexibilité et l'évolution de l'application. L'architecture choisie permet une communication fluide entre les différentes composantes, avec un accent particulier sur l'intégration des services externes, tels que les APIs de la Ville de Montréal.

### Diagrammes
- **Diagramme de cas d'utilisation** : Illustrant les interactions des résidents et intervenants avec l'application.
- **Diagramme d'activités** : Détaillant les principaux processus de l'application, mis à jour pour refléter les nouvelles exigences.
- **Diagramme de classes** : Présentant les différentes classes et leur relation, conçu pour respecter les principes de cohésion et de couplage minimal.

## 📄 Licence
Ce projet est réalisé dans le cadre d'un devoir universitaire et n'est pas destiné à un usage commercial.

---

Merci de votre intérêt pour **Ma Ville** ! Nous espérons que cette application contribuera à améliorer la communication et la gestion des travaux dans la ville de Montréal. Pour toute question ou suggestion, n'hésitez pas à nous contacter via le dépôt GitHub. ✨
