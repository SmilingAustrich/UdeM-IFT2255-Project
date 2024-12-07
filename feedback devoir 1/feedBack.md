
# Glossaire
- Système d'authentification 
- Menu principal 
- README
- Cas d'utilisation 
- Diagramme d'activité
- Besoins fonctionnels

Les termes listés ici ne sont pas pertinents pour le glossaire de votre projet. Votre glossaire doit uniquement contenir des termes techniques liés à la description du projet.

# Diagramme de cas d'utilisation
   - Respect du formalisme
   - Identification des acteurs
   - Cas d'utilisation
- Le CU **Modifier son profil** est manquant dans le diagramme de cas d'utilisation.
- **Partager son avis** est manquant dans le diagramme de cas d'utilisation.
- 🗒️ Le diagramme corrigé peut aussi être consulté dans le fichier feedback diagrammes.

# Scénarios
Le système est marqué comme acteur secondaire, pourtant le système n'est pas un acteur, et on ne voit pas d'acteur secondaire qui intervient pour les CUs dans le diagramme de CU.
- **Consulter les travaux en cours ou à venir** :
   - Le point 1 **Le résident se connecte à l'application** n'est pas cohérent avec la précondition, qui dit que le résident est déjà connecté à l'application.
   - Les points 4 et 5 ne sont pas nécessaires vu que nous souhaitons simplement consulter les travaux en cours et non un travail spécifique. De plus, pour que cela soit cohérent, il aurait fallu mettre **filtrer** les travaux comme inclus (**<< include >>**) à ce CU.
   - Le CU **Recevoir des notifications personnalisées** a été spécifié comme un CU étendant **Consulter les travaux en cours ou à venir**, mais il n'y a pas de cas alternatif qui gère ce CU.
   
- **Recevoir des notifications personnalisées** :
   - Le point 1 **Le résident se connecte à l'application** n'est pas cohérent avec la précondition, qui dit que le résident est déjà connecté à l'application.
   - Le scénario alternatif du point 4 n'est pas pertinent ; un scénario alternatif aurait pu être **les informations sont invalides**.

- **Soumettre une requête de travaux** :
   - Le point 1 **Le résident se connecte à l'application** n'est pas cohérent avec la précondition, qui dit que le résident est déjà connecté à l'application.
   - **Suivre une requête de travaux** est inclus dans ce CU, mais n'apparaît pas dans le scénario.

- **Suivre une requête de travaux** :
   - Ce CU est étendu par **Donner un avis sur les travaux** terminés, mais aucun cas alternatif n'existe pour le traiter.

- **Signaler un problème à la ville** :
   - Le point 1 **Le résident se connecte à l'application** n'est pas cohérent avec la précondition, qui dit que le résident est déjà connecté à l'application.

- **S'inscrire comme intervenant** :
   - Le CU inclut le CU **Valider l'inscription d'un intervenant**, mais cette inclusion n'est pas gérée.

- **Consulter la liste des requêtes de travaux** :
   - Le point 1 **L'intervenant se connecte à l'application** n'est pas cohérent avec la précondition, qui dit que l’intervenant est déjà connecté à l'application.
   - Ce CU est étendu par le CU **Soumettre une candidature pour un travail**, mais il n'y a pas de cas alternatif pour gérer ce CU.

- **Soumettre une candidature pour un projet** :
   - Le point 1 **L'intervenant se connecte à l'application** n'est pas cohérent avec la précondition, qui dit que l’intervenant est déjà connecté à l'application.
   - Ce cas alternatif n'est pas nécessaire et contredit un peu le CU lui-même.

- **Mettre à jour les informations d'un chantier** :
   - Ce CU n'est pas présent dans le diagramme de CU.

- **Scénarios manquants** ⚠️ :
   - Rechercher des travaux
   - Proposer des plages horaires pour les travaux
   - ...

# Diagramme d'activités
- ⚠️ Juste un diagramme d'activité fait.
- Les erreurs relevées sur le diagramme peuvent être consultées dans le fichier feedback diagramme.

# Analyse 📈
#### Risques
#### Besoins non fonctionnels
- **Pouvoir tenir une augmentation du nombre d'utilisateurs** :
   - Les besoins non fonctionnels sont caractérisés par des termes techniques bien spécifiques (e.g. sécurité, performance, etc.).
   
#### Besoins matériels
#### Solution de stockage
#### Solutions d'intégration

# Prototype
   - Le prototype est très bien fait 👏

# Git
- Le README est bien complété ✅
- Selon les commits, tout le monde a participé ✅
- Un release a bien été fait ✅
- La date limite a été respectée ✅

# Rapports
- Rapport bien structuré

# Bonus
- Merci pour l'implémentation de ce prototype fonctionnel.
    - Rmq: il y a un soucis lors qu'on soumette une requete de travail
