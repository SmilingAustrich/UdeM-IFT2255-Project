import java.util.Scanner;

public class Menu {
    // Méthode pour afficher le menu de connexion/inscription
    public void greet() {
        Scanner in = new Scanner(System.in);
        System.out.print("----------------------\n" +
                "Bienvenue sur votre application Ma Ville.\n" +
                "----------------------\n" +
                "Pour continuer, veuillez choisir une action à l'aide du menu ci-dessous." +
                "\n I. Se connecter en tant que:\n" +
                "\t 1. Résident.\n"
                + "\t 2. Intervenant.\n"
                + "II. S'inscrire en tant que:\n"
                + "\t 3. Résident.\n"
                + "\t 4. Intervenant.\n"
                + ":> ");
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

    public void residentLogInMenu() {

        System.out.print("--------------------------\n" +
                "Chargement du portail de connexion des résidents.");
        AppSimulation.simulateLoading();
        System.out.println("--------------------------\n");

        Scanner in = new Scanner(System.in);

        System.out.println("Bienvenue sur le portail de connexion des résidents.\n" +
                "Veuillez entrer votre Email ainsi que votre mot de passe.\n");

        boolean loggedInResident = false;
        int numberOfTries = 0;

        while (!loggedInResident) {
            System.out.print("Email >: ");
            String email = in.nextLine();
            System.out.print("Mot de passe >: ");
            String password = in.nextLine();

            loggedInResident = AuthenticationService.loginResident(email, password); // On regarde si

            if (!loggedInResident) {
                System.out.print("--------------------------\n");
                System.out.println("Nom d'utilisateur ou mot de passe incorrect. Veuillez réessayer.");
                System.out.print("--------------------------\n");
                numberOfTries++;
            }

            if(numberOfTries >= 2) {
                System.out.println("\nDésolé, mais votre nom d'utilisateur ou votre mot de passe semble incorrect.");
                System.out.print("Vous ne semblez pas être inscrits en tant que résident, redirection vers " +
                        "la page d'inscription. ");
                AppSimulation.simulateLoading();
                residentInscriptionMenu();
                break;
            }

        }

        if (loggedInResident) {
            System.out.print("\nConnexion réussie ! Bienvenue.\nChargement de la page ");
            AppSimulation.simulateLoading();
            AppSimulation.simulateWaitTime();
            // Redirige vers le menu des résidents
            residentMainMenu();
        }
    }

    public void intervenantLogInMenu() {
        System.out.print("--------------------------\n" +
                "Chargement du portail de connexion des intervenants.");
        AppSimulation.simulateLoading();
        System.out.println("--------------------------");

        System.out.println("Bienvenue sur le portail de connexion des intervenants.\n" +
                "Veuillez entrer votre email ainsi que votre mot de passe.");

        Scanner in = new Scanner(System.in);
        boolean loggedInIntervenant = false;
        int numberOfTries = 0;
        while (!loggedInIntervenant) {
            System.out.print("Email >: ");
            String username = in.nextLine();
            System.out.print("Mot de passe >: ");
            String password = in.nextLine();

            loggedInIntervenant = AuthenticationService.loginIntervenant(username, password);

            if (!loggedInIntervenant) {
                System.out.println("--------------------------");
                System.out.println("Nom d'utilisateur ou mot de passe incorrect. Veuillez réessayer.");
                System.out.println("--------------------------");
                numberOfTries++;
                AppSimulation.simulateWaitTime();
            }
            if(numberOfTries >= 2) {
                System.out.println("Désolé, mais votre nom d'utilisateur ou votre mot de passe semble incorrect.");
                System.out.print("Vous ne semblez pas être inscrits en tant que résident, redirection vers " +
                        "la page d'inscription. ");
                AppSimulation.simulateLoading();
                AppSimulation.simulateWaitTime();

                intervenantInscriptionMenu();
                break;
            }
        }

        if (loggedInIntervenant) {
            System.out.println("Connexion réussie ! Bienvenue, intervenant.");
            // Redirige vers le menu des intervenants
            intervenantMainMenu();
        }
    }

    public void residentInscriptionMenu() {

        System.out.print("--------------------------\n" +
                "Chargement du portail d'inscription des résidents. ");
        AppSimulation.simulateLoading();
        System.out.println("--------------------------");

        Scanner in = new Scanner(System.in);

        System.out.println("Bienvenue sur le portail d'inscription des résident.");

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


        // Création du nouvel objet Resident
        Resident resident = new Resident(firstNameResident, lastNameResident, emailResident, passwordResident, phoneResident, addressResident, dobResident);

        // Enregistrement du résident
        AuthenticationService.signUpResident(resident);

        System.out.println("Bienvenue "+ resident.getFirstName() +"! Vous pouvez maintenant vous connecter.");

        residentLogInMenu();

    }

    public void intervenantInscriptionMenu(){

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
        while(cityId.length() != 8){
            System.out.print("L'identifiant de la ville doit être un code à 8 chiffres, veuillez réessayez >: ");
            cityId = in.nextLine();
        }
        System.out.print("Type d'entrepreneur (numérique)\n" +
                "1. Entreprise privée\n" +
                "2. Entreprise public\n" +
                "3. Particulier\n >: ");

        int entrepreneurType = in.nextInt();

        // Création du nouvel objet Intervenant
        Intervenant intervenant = new Intervenant(firstNameIntervenant, lastNameIntervenant, emailIntervenant, passwordIntervenant, cityId, entrepreneurType);

        // Enregistrement de l'intervenant
        AuthenticationService.signUpIntervenant(intervenant);

        AppSimulation.simulateLoading();

        System.out.println("Bienvenue "+ intervenant.getFirstName() +
                "! Vous pouvez maintenant vous connecter.");
        AppSimulation.simulateLoading();

        intervenantLogInMenu();
    }

    public void intervenantMainMenu() {
        Scanner in = new Scanner(System.in);
        System.out.print("\n*******************************\n" +
                "Vous êtes actuellement sur le menu des intervenants de l'application Ma Ville.\n" +
                "\n Veuillez choisir une option dans la liste de souhait suivante \n" +
                "\t 1. Consulter la liste des requêtes de travail.\n" +
                "\t 2. Soumettre un nouveau projet de travaux.\n" +
                "\t 3. Mettre à jour les informations d'un chantier.\n" +
                " Insérer la lettre qui correspond à votre choix : ");
        int choice = in.nextInt();
        in.nextLine();
        System.out.println("*******************************");
        switch (choice) {
            case 1 :
                System.out.println("Voulez vous consuler la liste des requêtes de travaux et soumettre " +
                        "votre candidature ?");
                break;
            case 2 :
                System.out.println("Fournissez les informations suivantes (titre du projet, description du projet," +
                        "types de travaux, quartiers affectés, rue affectées, date de début, date de fin," +
                        " horaire des travaux) pour soumettre un projet :");
                break;
            case 3 :
                System.out.println("Que voulez-vous mettre à jour ? La description du projet, la date de fin prévu" +
                        " ou voulez-vous changer le statut du projet ? :");
                break;
            default :
                System.out.println("Choix invalide. Veuillez réessayer.");
        }
    }

    public void residentMainMenu(){
        Scanner in = new Scanner(System.in);
        System.out.print("\n*******************************\n" +
                "Vous êtes actuellement sur le menu des résidents de l'application Ma Ville.\n" +
                "\n Veuillez choisir une option dans la liste de souhait suivante : \n" +
                "\t 1. Consulter les travaux en cours ou à venir.\n" +
                "\t 2. Rechercher des travaux\n" +
                "\t 3. Recevoir des notifications personnalisées.\n" +
                "\t 4. Permettre une planification participative. \n" +
                "\t 5. Soumettre une requête de travail. \n" +
                "\t 6. Signaler un problème à la ville. \n" +
                "\nInsérer le numéro qui correspond à votre choix :> ");
        int choice = in.nextInt();
        in.nextLine();
        System.out.println("*******************************");
        switch (choice) {
            case 1 :
                System.out.println("Voulez-vous consulter les travaux en cours ou sur les 3 prochains mois ?");
                break;
            case 2 :
                System.out.println("Entrer les critères de recherche (par titre, types de travaux ou quartier) :");
                break;
            case 3 :
                System.out.println("Configurez votre rue et votre quartier afin de recevoir des notifications " +
                        "spécifiques à votre zone :");
                break;
            case 4 :
                System.out.println("Voulez-vous fournir des préférences ou consulter celles des autres ?");
                break;
            case 5 :
                System.out.println("Fournissez les spécifications (titre du travail a réalisé, description détaillée, " +
                        "date de début espérée...) pour la requête de travail :");
                break;
            case 6 :
                System.out.println("Fournissez les informations suivantes (nom du résident, adresse courriel," +
                        "adresse de résidence, type de problème, description du problème) pour signaler un problème :");
            default :
                System.out.println("Choix invalide. Veuillez réessayer.");
        }
    }

    // Méthode pour démarrer l'application
    public void start() {
        greet();
    }

}
