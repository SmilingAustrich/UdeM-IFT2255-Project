import java.util.Scanner;

/**
 * La classe {@code Menu} représente le menu principal de l'application Ma Ville.
 * Elle permet aux utilisateurs de se connecter, de s'inscrire et d'accéder aux différentes fonctionnalités
 * en fonction de leur statut (résident ou intervenant).
 */
public class Menu {

    /**
     * Affiche le menu de connexion et d'inscription pour les résidents et les intervenants.
     * L'utilisateur peut choisir entre se connecter ou s'inscrire.
     */
    public void InitializeApp() {
        Scanner in = new Scanner(System.in);
        System.out.print(
                "----------------------\n" +
                        "Bienvenue sur votre application Ma Ville.\n" +
                        "----------------------\n" +
                        "Pour continuer, veuillez choisir une action à l'aide du menu ci-dessous." +
                        "\n I. Se connecter en tant que:\n" +
                        "\t 1. Résident.\n" +
                        "\t 2. Intervenant.\n" +
                        "II. S'inscrire en tant que:\n" +
                        "\t 3. Résident.\n" +
                        "\t 4. Intervenant.\n" +
                        ":> "
        );
        int choice = in.nextInt();
        in.nextLine();

        switch (choice) {
            case 1:
                residentLogInMenu();
                break;
            case 2:
                intervenantLogInMenu();
                break;
            case 3:
                residentInscriptionMenu();
                break;
            case 4:
                intervenantInscriptionMenu();
                break;
            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
                break;
        }
    }

    /**
     * Affiche le menu de connexion des résidents et gère la connexion en demandant l'email et le mot de passe.
     * En cas d'échec de connexion après deux essais, l'utilisateur est redirigé vers le menu d'inscription.
     */
    public void residentLogInMenu() {
        System.out.print(
                "--------------------------\n" +
                        "Chargement du portail de connexion des résidents."
        );
        AppSimulation.simulateLoading();
        System.out.println("--------------------------\n");

        Scanner in = new Scanner(System.in);
        System.out.println(
                "Bienvenue sur le portail de connexion des résidents.\n" +
                        "Veuillez entrer votre Email ainsi que votre mot de passe.\n"
        );

        boolean loggedInResident = false;
        int numberOfTries = 0;
        Resident resident;

        while (!loggedInResident) {
            System.out.print("Email >: ");
            String email = in.nextLine();
            System.out.print("Mot de passe >: ");
            String password = in.nextLine();

            loggedInResident = AuthenticationService.loginResident(email, password);
            resident = AuthenticationService.getResidentByEmail(email);

            if (!loggedInResident) {
                System.out.print("--------------------------\n");
                System.out.println("Nom d'utilisateur ou mot de passe incorrect. Veuillez réessayer.");
                System.out.print("--------------------------\n");
                numberOfTries++;
            }

            if (numberOfTries >= 2) {
                System.out.println("Désolé, mais votre nom d'utilisateur ou votre mot de passe semble incorrect.");
                System.out.print(
                        "Vous ne semblez pas être inscrits en tant que résident, redirection vers " +
                                "la page d'inscription. "
                );
                AppSimulation.simulateLoading();
                residentInscriptionMenu();
                break;
            }

            if (loggedInResident) {
                System.out.print("\nConnexion réussie ! Bienvenue.\nChargement de la page ");
                AppSimulation.simulateLoading();
                AppSimulation.simulateWaitTime();
                residentMainMenu(resident); // Redirige vers le menu des résidents
            }
        }
    }

    /**
     * Affiche le menu de connexion des intervenants et gère la connexion en demandant l'email et le mot de passe.
     * En cas d'échec de connexion après deux essais, l'utilisateur est redirigé vers le menu d'inscription.
     */
    public void intervenantLogInMenu() {
        System.out.print(
                "--------------------------\n" +
                        "Chargement du portail de connexion des intervenants."
        );
        AppSimulation.simulateLoading();
        System.out.println("--------------------------");

        System.out.println(
                "Bienvenue sur le portail de connexion des intervenants.\n" +
                        "Veuillez entrer votre email ainsi que votre mot de passe."
        );

        Scanner in = new Scanner(System.in);
        boolean loggedInIntervenant = false;
        int numberOfTries = 0;

        while (!loggedInIntervenant) {
            System.out.print("Email >: ");
            String email = in.nextLine();
            System.out.print("Mot de passe >: ");
            String password = in.nextLine();

            loggedInIntervenant = AuthenticationService.loginIntervenant(email, password);
            Intervenant intervenant = AuthenticationService.getIntervenantByEmail(email);

            if (!loggedInIntervenant) {
                System.out.println("--------------------------");
                System.out.println("Nom d'utilisateur ou mot de passe incorrect. Veuillez réessayer.");
                System.out.println("--------------------------");
                numberOfTries++;
                AppSimulation.simulateWaitTime();
            }

            if (numberOfTries >= 2) {
                System.out.println("Désolé, mais votre nom d'utilisateur ou votre mot de passe semble incorrect.");
                System.out.print(
                        "Vous ne semblez pas être inscrits en tant que résident, redirection vers " +
                                "la page d'inscription. "
                );
                AppSimulation.simulateLoading();
                AppSimulation.simulateWaitTime();
                intervenantInscriptionMenu();
                break;
            }

            if (loggedInIntervenant) {
                System.out.println("Connexion réussie ! Bienvenue, intervenant.");
                intervenantMainMenu(intervenant); // Redirige vers le menu des intervenants
            }
        }
    }

    /**
     * Affiche le menu d'inscription pour les résidents et enregistre un nouveau résident.
     * Une fois l'inscription réussie, le résident est redirigé vers le menu de connexion.
     */
    public void residentInscriptionMenu() {
        System.out.print("--------------------------\n" +
                "Chargement du portail d'inscription des résidents. ");
        AppSimulation.simulateLoading();
        System.out.println("--------------------------");

        Scanner in = new Scanner(System.in);
        System.out.println("Bienvenue sur le portail d'inscription des résident!");

        System.out.print("Veuillez entrer votre prénom >: ");
        String firstNameResident = in.nextLine();
        System.out.print("Veuillez entrer votre nom de famille >: ");
        String lastNameResident = in.nextLine();
        System.out.print("Adresse courriel >: ");
        String emailResident = in.nextLine();
        System.out.print("Mot de passe >: ");
        String passwordResident = in.nextLine();
        System.out.print("Numéro de téléphone (optionnel) >: ");
        String phoneResident = in.nextLine();
        System.out.print("Adresse >: ");
        String addressResident = in.nextLine();
        System.out.print("Date de naissance (format dd/mm/yy) >: ");
        String dobResident = in.nextLine();

        Resident resident = new Resident(
                firstNameResident, lastNameResident, emailResident, passwordResident,
                phoneResident, addressResident, dobResident
        );

        AuthenticationService.signUpResident(resident);

        System.out.println("Bienvenue " + resident.getFirstName() + "! Vous pouvez maintenant vous connecter.");
        residentLogInMenu();
    }

    /**
     * Affiche le menu d'inscription pour les intervenants et enregistre un nouvel intervenant.
     * Une fois l'inscription réussie, l'intervenant est redirigé vers le menu de connexion.
     */
    public void intervenantInscriptionMenu() {
        System.out.print("--------------------------\n" +
                "Chargement du portail d'inscription des intervenants. ");
        AppSimulation.simulateLoading();
        System.out.println("--------------------------");

        Scanner in = new Scanner(System.in);
        System.out.println("Bienvenue sur le portail d'inscription des intervenants.\n");

        System.out.print("Veuillez entrer votre prénom >: ");
        String firstNameIntervenant = in.nextLine();
        System.out.print("Veuillez entrer votre nom de famille >: ");
        String lastNameIntervenant = in.nextLine();
        System.out.print("Adresse courriel >: ");
        String emailIntervenant = in.nextLine();
        System.out.print("Mot de passe >: ");
        String passwordIntervenant = in.nextLine();
        System.out.print("Identifiant de la ville (code à 8 chiffres) >: ");
        String cityId = in.nextLine();
        while (cityId.length() != 8) {
            System.out.print("L'identifiant de la ville doit être un code à 8 chiffres, veuillez réessayer >: ");
            cityId = in.nextLine();
        }
        System.out.print("Type d'entrepreneur (numérique)\n" +
                "1. Entreprise privée\n" +
                "2. Entreprise public\n" +
                "3. Particulier\n >: ");
        int entrepreneurType = in.nextInt();

        Intervenant intervenant = new Intervenant(
                firstNameIntervenant, lastNameIntervenant, emailIntervenant, passwordIntervenant, cityId, entrepreneurType
        );

        AuthenticationService.signUpIntervenant(intervenant);
        AppSimulation.simulateLoading();

        System.out.println("Bienvenue " + intervenant.getFirstName() + "! Vous pouvez maintenant vous connecter.");
        AppSimulation.simulateLoading();
        intervenantLogInMenu();
    }

    /**
     * Affiche le menu principal pour les intervenants une fois connectés.
     * @param intervenant L'intervenant connecté
     */
    public void intervenantMainMenu(Intervenant intervenant) {
        Scanner in = new Scanner(System.in);
        System.out.print(
                "\n*******************************\n" +
                        "Bienvenue " + intervenant.getFirstName() +
                        "! Vous êtes actuellement sur le menu des intervenants de l'application Ma Ville.\n" +
                        "\nVeuillez choisir une option dans la liste de souhait suivante \n" +
                        "\t 1. Consulter la liste des requêtes de travail.\n" +
                        "\t 2. Soumettre un nouveau projet de travaux.\n" +
                        "\t 3. Mettre à jour les informations d'un chantier.\n" +
                        "\nInsérer le numéro qui correspond à votre choix : "
        );
        int choice = in.nextInt();
        in.nextLine();
        System.out.println("*******************************");

        switch (choice) {
            case 1:
                System.out.println("Voulez-vous consulter la liste des requêtes de travaux et soumettre votre candidature ?");
                break;
            case 2:
                System.out.println("Fournissez les informations suivantes (titre du projet, description du projet," +
                        "types de travaux, quartiers affectés, rues affectées, date de début, date de fin, horaire des travaux) pour soumettre un projet :");
                break;
            case 3:
                System.out.println("Que voulez-vous mettre à jour ? La description du projet, la date de fin prévue" +
                        " ou voulez-vous changer le statut du projet ? :");
                break;
            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
        }
    }

    /**
     * Affiche le menu principal pour les résidents une fois connectés.
     * @param resident Le résident connecté
     */
    public void residentMainMenu(Resident resident) {
        Scanner in = new Scanner(System.in);
        System.out.print(
                "\n*******************************\n" +
                        "Bienvenue " + resident.getFirstName() +
                        "! Vous êtes actuellement sur le menu des résidents de l'application Ma Ville.\n" +
                        "\nVeuillez choisir une option dans la liste de souhait suivante : \n" +
                        "\t 1. Consulter les travaux en cours ou à venir.\n" +
                        "\t 2. Rechercher des travaux\n" +
                        "\t 3. Recevoir des notifications personnalisées.\n" +
                        "\t 4. Proposer des plages horaires pour des travaux. \n" +
                        "\t 5. Soumettre une requête de travail. \n" +
                        "\t 6. Signaler un problème à la ville. \n" +
                        "\nInsérer le numéro qui correspond à votre choix :> "
        );
        int choice = in.nextInt();
        in.nextLine();
        System.out.println("*******************************");

        switch (choice) {
            case 1:
                resident.consulterTravaux();
                break;
            case 2:
                System.out.println("Entrer les critères de recherche (par titre, types de travaux ou quartier) :");
                break;
            case 3:
                resident.receivePersonalizedNotifications();
                break;
            case 4:
                System.out.println("Voulez-vous fournir des préférences ou consulter celles des autres ?");
                break;
            case 5:
                System.out.println("Fournissez les spécifications (titre du travail à réaliser, description détaillée, " +
                        "date de début espérée...) pour la requête de travail :");
                break;
            case 6:
                resident.signalerProbleme();
                break;
            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
        }
    }

    /**
     * Démarre l'application en affichant le menu principal.
     */
    public void start() {
        InitializeApp();
    }
}
