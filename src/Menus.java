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
