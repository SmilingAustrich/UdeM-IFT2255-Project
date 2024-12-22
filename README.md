# Ma Ville – Application de Gestion des Travaux

**Ma Ville** est une application développée en **Java** et **Quarkus** pour faciliter la gestion des travaux publics et privés. Elle met l’accent sur la coordination entre **résidents** et **intervenants** (entreprises, particuliers) afin de :
- Réduire les perturbations dans les quartiers
- Améliorer la planification
- Assurer une communication claire et transparente

Ce projet a été réalisé dans le cadre d’un devoir universitaire.

---

## Table des Matières
1. [Fonctionnalités Principales](#fonctionnalités-principales)
2. [Organisation du Répertoire](#organisation-du-répertoire)
3. [Auteurs](#auteurs)
4. [Installation et Exécution](#installation-et-exécution)
5. [Tests](#tests)
6. [Architecture et Design](#architecture-et-design)
7. [Licence](#licence)

---

## Fonctionnalités Principales

### Rôle « Résident »
- **Inscription / Connexion** pour accéder aux fonctionnalités personnalisées.
- **Visualisation des travaux** en cours ou à venir, filtrés par type, quartier, etc.
- **Soumission de requêtes** pour des travaux résidentiels spécifiques (p. ex. : demande de réparation de trottoir).
- **Suivi de l’état** des requêtes (en attente, en cours, terminée) et réception de **notifications**.
- **Partage de préférences** (plages horaires, etc.) pour faciliter la planification inclusive.
- **Évaluation des travaux** pour améliorer la qualité des futurs projets.

### Rôle « Intervenant »
- **Inscription / Connexion** avec un identifiant autorisé.
- **Consultation des requêtes** envoyées par les résidents et possibilité d’y **postuler**.
- **Ajout / Modification** de projets de travaux, en indiquant le quartier, la rue, le type de travaux, etc.
- **Mise à jour de l’avancement** (statut, dates, etc.) pour informer les résidents.

### Rôle « Administrateur » (le cas échéant)
- **Gestion des utilisateurs** (création, suppression, modification).
- **Supervision** de l’ensemble des projets et requêtes.
- **Statistiques globales** sur les chantiers et leurs avancées.

---

## Organisation du Répertoire

```plaintext
src
├── main
│   ├── java
│   │   └── org
│   │       └── udem
│   │           └── ift2255
│   │               ├── api
│   │               │   └── ResidentAPI.java
│   │               ├── database
│   │               │   └── TestDataInitializer.java
│   │               ├── dto
│   │               │   ├── CandidatureRequestDTO.java
│   │               │   ├── LoginRequestDTO.java
│   │               │   ├── ResidentialWorkRequestDTO.java
│   │               │   ├── UserDTO.java
│   │               │   └── WorkRequestDTO.java
│   │               ├── model
│   │               │   ├── Candidature.java
│   │               │   ├── Intervenant.java
│   │               │   ├── Notification.java
│   │               │   ├── Project.java
│   │               │   ├── Resident.java
│   │               │   ├── ResidentialWorkRequest.java
│   │               │   ├── TimeSlot.java
│   │               │   └── User.java
│   │               ├── repository
│   │               │   ├── CandidatureRepository.java
│   │               │   ├── EntraveRepository.java
│   │               │   ├── IntervenantRepository.java
│   │               │   ├── NotificationRepository.java
│   │               │   ├── ResidentialWorkRequestRepository.java
│   │               │   └── ResidentRepository.java
│   │               ├── resource
│   │               │   ├── CandidatureResource.java
│   │               │   ├── EntraveResource.java
│   │               │   ├── IntervenantLoginResource.java
│   │               │   ├── IntervenantResource.java
│   │               │   ├── NotificationResource.java
│   │               │   ├── ProjectResource.java
│   │               │   ├── ResidentialWorkRequestResource.java
│   │               │   ├── ResidentLoginResource.java
│   │               │   ├── ResidentResource.java
│   │               │   ├── SignUpResource.java
│   │               │   └── TravauxResource.java
│   │               ├── service
│   │               │   ├── AuthenticationService.java
│   │               │   ├── CandidatureService.java
│   │               │   ├── EntraveService.java
│   │               │   ├── IntervenantService.java
│   │               │   ├── NotificationService.java
│   │               │   ├── ProjectService.java
│   │               │   └── ResidentialWorkRequestService.java
│   │               └── ui
│   │                   └── ressources
│   │                       ├── Main.java
│   │                       ├── Menu.java
│   │                       └── sample.fxml
│   └── resources
│       └── sql
│           # Scripts SQL éventuels
└── test
    └── java
        └── org
            └── udem
                └── ift2255
                   └── ResidentialWorkRequestResourceUnitTest.java
                    └── ResidentialWorkRequestServiceTest.java

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

2. Ouvrez le projet dans votre IDE préféré.

### Exécution
1. Compilez et exécutez la classe `Main.java`.
2. Suivez les instructions affichées dans l'interface en ligne de commande pour naviguer dans l'application.

### Tests
- Les tests unitaires sont disponibles dans le répertoire `src/test/java`.
- Utilisez **JUnit** pour exécuter les tests, ou bien executez automatiquement les tests avec maven.

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
