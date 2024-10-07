import java.util.Scanner;

public class Menus {
    // Méthode pour afficher le menu de connexion/inscription
    public void greet() {


        Scanner in = new Scanner(System.in);
        System.out.print("Bienvenue sur votre application Ma Ville." +
                "\n Pour continuer, veuillez choisir une action à l'aide du menu ci-dessous." +
                "\n I. Se connecter en tant que:\n" +
                "\t 1. Résident.\n"
                + "\t 2. Intervenant.\n"
                + "II. S'inscrire en tant que:\n" +
                "\t 3. Résident.\n"
                + "\t 4. Intervenant.\n"
                + ":> ");
        int choice = in.nextInt();
        in.nextLine();

        switch (choice) {
            case 1:
                System.out.print("--------------------------\n" +
                        "Chargement du portail de connexion ");
                AppSimulation.simulateLoading();
                System.out.println("--------------------------\n");
                residentLogInMenu();
                break;

            case 2:
                System.out.print("--------------------------\n" +
                        "Chargement du portail de connexion ");
                AppSimulation.simulateLoading();
                System.out.println("--------------------------\n");
                break;

            case 3:
                System.out.println("Inscription en tant que résident.");
                System.out.print("Veuillez entrer votre nom complet >: ");
                String fullNameResident = in.nextLine();
                System.out.print("Adresse courriel >: ");

                System.out.print("Mot de passe >: ");


                AppSimulation.simulateLoading();
                System.out.println("Inscription réussie. Vous pouvez maintenant vous connecter.");
                break;

            case 4:
                System.out.println("Inscription en tant qu'intervenant.");
                System.out.print("Veuillez entrer votre nom complet >: ");
                String fullNameIntervenant = in.nextLine();
                System.out.print("Adresse courriel >: ");
                String emailIntervenant = in.nextLine();
                System.out.print("Mot de passe >: ");
                String passwordIntervenant = in.nextLine();
                System.out.print("Identifiant de la ville (code à 8 chiffres) >: ");
                String cityId = in.nextLine();

                // Appel à un service d'inscription
                System.out.println("Inscription réussie. Vous pouvez maintenant vous connecter.");
                break;

            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
                break;
        }
    }


    public void residentLogInMenu() {

        Scanner in = new Scanner(System.in);

        System.out.println("Bienvenue sur le portail de connexion des résidents.\n" +
                "Veuillez entrer votre nom d'utilisateur ainsi que votre mot de passe.\n");

        System.out.print("Nom d'utilisateur >: ");
        String username = in.nextLine();
        System.out.print("Mot de passe >: ");
        String password = in.nextLine();

        boolean loggedInResident = AuthenticationService.login(username, password);
        while (loggedInResident != true ) {
            System.out.println("Nom d'utilisateur ou mot de passe incorrect. Veuillez réessayer.");

            System.out.print("Nom d'utilisateur >: ");
            username = in.nextLine();
            System.out.print("Mot de passe >: ");
            password = in.nextLine();
            loggedInResident = AuthenticationService.login(username, password);
        }
        if (loggedInResident) {
            System.out.println("Connexion réussie ! Bienvenue, " + username);
            // Redirige vers le menu des résidents
            residentMainMenu();
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
                 " Insérer la lettre qui correspond à votre choix : ");


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

    // Menu des intervenants (à personnaliser selon les fonctionnalités)
    public void intervenantMenu() {

        Scanner in = new Scanner(System.in);

        System.out.println("Bienvenue sur le portail de connexion des intervenants.\n " +
                "Veuillez entrer votre nom d'utilisateur ainsi que votre mot de passe.\n");
        System.out.print("Nom d'utilisateur >: ");

        String username = in.nextLine();
        System.out.print("Mot de passe >: ");
        String password = in.nextLine();

        boolean loggedInIntervenant = AuthenticationService.login(username, password);
        while (loggedInIntervenant != true ) {
            System.out.println("Nom d'utilisateur ou mot de passe incorrect. Veuillez réessayer.");

            System.out.print("Nom d'utilisateur >: ");
            username = in.nextLine();
            System.out.print("Mot de passe >: ");
            password = in.nextLine();
            loggedInIntervenant = AuthenticationService.login(username, password);
        }if (loggedInIntervenant) {
            System.out.println("Connexion réussie ! Bienvenue, " + username);
            // Redirige vers le menu des intervenants
            intervenantMainMenu();
        }
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

    // Méthode pour démarrer l'application
    public void start() {
        greet();
    }

    // Main pour exécuter l'application
    public static void main(String[] args) {
        Menus app = new Menus();
        app.start();
    }
}
