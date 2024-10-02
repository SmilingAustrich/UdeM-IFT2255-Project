import java.util.Scanner;

public class LoginMenu {
    private AuthenticationService authService = new AuthenticationService();
    private boolean firstTimeLoggingIn = false;
    public void start() {
        if (!firstTimeLoggingIn) {
            System.out.println("Bienvenue dans votre application MaVille!");
            firstTimeLoggingIn = true;
        };

        Scanner scanner = new Scanner(System.in);
        AppSimulation.simulateRealTimeWriting("Veuillez entrer votre nom d'utilisateur :> ");
        String username = scanner.nextLine();

        AppSimulation.simulateRealTimeWriting("Veuillez entrer votre mot de passe :> ");
        String password = scanner.nextLine();

        boolean loggedIn = authService.login(username, password);
        if (loggedIn) {
            System.out.print("Chargement en cours, veuillez patienter ");
            AppSimulation.simulateLoading();
            System.out.println("Connexion réussie!");
            // Dirige l'utilisateur vers differents menus en se basant sur le type d'utilisateur.
        } else {
            System.out.print("Chargement en cours, veuillez patienter ");
            AppSimulation.simulateLoading();
            System.out.println("Informations invalides. Essayez encore..");
            start(); // on ressaie
        }
    }
}
