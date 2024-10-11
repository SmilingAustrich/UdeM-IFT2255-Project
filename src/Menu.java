import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
        System.out.println("Bienvenue sur le portail d'inscription des résidents!");

        // Validation des différents champs
        String firstNameResident = promptForNonEmptyInput(in, "Veuillez entrer votre prénom >: ");
        String lastNameResident = promptForNonEmptyInput(in, "Veuillez entrer votre nom de famille >: ");
        String emailResident = promptForValidEmail(in, "Adresse courriel >: ");
        String passwordResident = promptForPassword(in, "Mot de passe >: ");
        String phoneResident = promptForNonEmptyInput(in, "Numéro de téléphone (optionnel, tapez 0 et valider) >: "); // Optional field
        String addressResident = promptForNonEmptyInput(in, "Adresse >: ");
        String dobResident = promptForValidDate(in, "Date de naissance (format jj/mm/aaaa) >: ");

        Resident resident = new Resident(
                firstNameResident, lastNameResident, emailResident, passwordResident,
                phoneResident, addressResident, dobResident
        );

        AuthenticationService.signUpResident(resident);

        System.out.println("Bienvenue " + resident.getFirstName() + "! Vous pouvez maintenant vous connecter.");
        residentLogInMenu();
    }

    /**
     * Méthode d'assistance pour demander une entrée jusqu'à ce qu'une valeur non vide soit fournie.
     */
    private String promptForNonEmptyInput(Scanner in, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = in.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Ce champ est obligatoire. Veuillez entrer une valeur.");
            }
        } while (input.isEmpty());
        return input;
    }

    /**
     * Valide l'adresse email.
     */
    private String promptForValidEmail(Scanner in, String prompt) {
        String email;
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
        do {
            System.out.print(prompt);
            email = in.nextLine().trim();
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                System.out.println("Adresse email invalide. Veuillez entrer une adresse email valide.");
            }
        } while (!pattern.matcher(email).matches());
        return email;
    }

    /**
     * Demande un mot de passe en cachant les caractères avec des étoiles.
     */
    private String promptForPassword(Scanner in, String prompt) {
        Console console = System.console();
        String password;
        if (console != null) {
            password = new String(console.readPassword(prompt));
        } else {
            // En cas d'absence de console (IDE), utilisation classique
            password = promptForNonEmptyInput(in, prompt);
        }
        return password;
    }

    /**
     * Valide la date de naissance selon le format jj/mm/aaaa.
     */
    private String promptForValidDate(Scanner in, String prompt) {
        String date;
        Pattern pattern = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");
        do {
            System.out.print(prompt);
            date = in.nextLine().trim();
            Matcher matcher = pattern.matcher(date);
            if (!matcher.matches()) {
                System.out.println("Date invalide. Veuillez entrer une date au format jj/mm/aaaa.");
            }
        } while (!pattern.matcher(date).matches());
        return date;
    }

    /**
     * Valide que l'identifiant de la ville est un code à 8 chiffres.
     */
    private String promptForValidCityId(Scanner in, String prompt) {
        String cityId;
        do {
            System.out.print(prompt);
            cityId = in.nextLine().trim();
            if (!cityId.matches("\\d{8}")) {
                System.out.println("L'identifiant de la ville doit être un code à 8 chiffres, veuillez réessayer.");
            }
        } while (!cityId.matches("\\d{8}"));
        return cityId;
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

        // Validation des différents champs
        String firstNameIntervenant = promptForNonEmptyInput(in, "Veuillez entrer votre prénom >: ");
        String lastNameIntervenant = promptForNonEmptyInput(in, "Veuillez entrer votre nom de famille >: ");
        String emailIntervenant = promptForValidEmail(in, "Adresse courriel >: ");
        String passwordIntervenant = promptForPassword(in, "Mot de passe >: ");
        String cityId = promptForValidCityId(in, "Identifiant de la ville (code à 8 chiffres) >: ");

        System.out.print("Type d'entrepreneur (numérique)\n" +
                "1. Entreprise privée\n" +
                "2. Entreprise publique\n" +
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
    public static void intervenantMainMenu(Intervenant intervenant) {
        Scanner in = new Scanner(System.in);
        System.out.print(
                "\n--------------------------\n" +
                        "Bienvenue " + intervenant.getFirstName() +
                        "! Vous êtes actuellement sur le menu des intervenants de l'application Ma Ville.\n" +
                        "\nVeuillez choisir une option dans la liste suivante \n" +
                        "\t 1. Consulter la liste des requêtes de travail.\n" +
                        "\t 2. Soumettre un nouveau projet de travaux.\n" +
                        "\t 3. Mettre à jour les informations d'un chantier.\n" +
                        "\t 4. Proposer une plage horaire pour les travaux.\n" +
                        "\t 5. Soumettre une candidature pour un travail.\n" +
                        "\t 6. Se déconnnecter.\n" +
                        "\r - Tapez '0' à tout moment pour retourner au menu principal.\n" +
                        "\nInsérer le numéro qui correspond à votre choix : "
        );
        int choice = in.nextInt();
        in.nextLine();
        System.out.println("--------------------------");

        switch (choice) {
            case 0:
                intervenantMainMenu(intervenant); // Retourne au menu principal de l'application
                break;
            case 1:
                System.out.println("Consultation des requêtes de travaux disponibles...");
                intervenant.consulterListeRequetesTravaux(intervenant,new ArrayList<>(Arrays.asList("Travaux routiers", "Entretien électrique")));
                break;
            case 2:
                System.out.println("Soumission d'un nouveau projet de travaux...");
                intervenant.soumettreProjetTravaux(intervenant);
                break;
            case 3:
                System.out.println("Mettre à jour les informations d'un chantier.");
                System.out.println("Que voulez-vous mettre à jour ? La description du projet, la date de fin prévue" +
                        " ou voulez-vous changer le statut du projet ? :");
                System.out.println("Tapez '0' pour retourner au menu principal.");
                int updateChoice = in.nextInt();
                in.nextLine();
                if (updateChoice == 0) {
                    intervenantMainMenu(intervenant); // Retourne au menu principal de l'intervenant
                } else {
                    System.out.println("Mise à jour des informations du chantier...");
                }
                break;
            case 4:
                System.out.println("Proposer une nouvelle plage horaire pour les travaux...");
                intervenant.proposerPlageHoraire(intervenant);
                break;
            case 5:
                System.out.println("Soumission d'une candidature pour un travail...");
                intervenant.soumettreCandidatureTravail(intervenant);
                break;
            case 6:
                System.out.print("Déconnexion en cours ");
                AppSimulation.simulateLoading();
                System.out.println("Au revoir " + intervenant.getFirstName());
                break;

            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
                intervenantMainMenu(intervenant); // Rappelle le menu si choix invalide
        }
    }

    /**
     * Affiche le menu principal pour les résidents une fois connectés.
     * @param resident Le résident connecté
     */
    public static void residentMainMenu(Resident resident) {
        Scanner in = new Scanner(System.in);
        System.out.print(
                "\n----------------------------\n" +
                        "Bienvenue " + resident.getFirstName() +
                        "! Vous êtes actuellement sur le menu des résidents de l'application Ma Ville.\n" +
                        "\nVeuillez choisir une option dans la liste suivante: \n" +
                        "\t 1. Consulter les travaux en cours ou à venir.\n" +
                        "\t 2. Rechercher des travaux\n" +
                        "\t 3. Recevoir des notifications personnalisées.\n" +
                        "\t 4. Proposer des plages horaires pour des travaux. \n" +
                        "\t 5. Soumettre une requête de travail. \n" +
                        "\t 6. Signaler un problème à la ville. \n" +
                        "\t 7. Se déconnecter. \n" +
                        "\nInsérer le numéro qui correspond à votre choix :> "
        );
        int choice = in.nextInt();
        in.nextLine();
        System.out.println("----------------------------");

        switch (choice) {
            case 1:
                resident.consulterTravaux(resident);
                break;
            case 2:
                System.out.println("Entrer les critères de recherche (par titre, types de travaux ou quartier) :");
                System.out.println("1. Par titre\n" + "2. Par types de travaux\n" + "3. Par Quartier" +
                        "\n4. Tapez '0' pour retourner au menu principal.");
                System.out.print("Insérer le numéro qui correspond à votre choix :> ");
                choice = in.nextInt();
                in.nextLine();
                // Simuler la liste des travaux
                String[] travaux = {
                        "Travaux de réfection de la route sur Rue Saint-Denis",
                        "Travaux de réaménagement des espaces verts dans le Quartier Mile-End",
                        "Installation de nouvelles conduites d'eau sur Rue de la Montagne",
                        "Travaux de rénovation de l'éclairage public dans le Quartier Vieux-Montréal"
                };

                switch(choice){
                    case 1:
                        System.out.println("Voici les travaux pour votre critère numéro " + choice + " :");
                        AppSimulation.simulateLoading();
                        for (String travail : travaux) {
                            System.out.println(travail);
                        }
                        AppSimulation.simulateWaitTime();
                        System.out.println("Tapez sur n'importe quelle touche pour retourner au menu principal.");
                        in.nextLine();  // Attend que l'utilisateur appuie sur Entrée

                        residentMainMenu(resident);
                        break;
                    case 2:
                        System.out.println("Voici les travaux pour votre critère numéro " + choice + " :");
                        AppSimulation.simulateLoading();
                        for (String travail : travaux) {
                            System.out.println(travail);
                        }
                        AppSimulation.simulateWaitTime();
                        System.out.println("Tapez sur n'importe quelle touche pour retourner au menu principal.");
                        in.nextLine();  // Attend que l'utilisateur appuie sur Entrée

                        residentMainMenu(resident);
                        break;
                    case 3:
                        System.out.println("Voici les travaux pour votre critère numéro " + choice + " :");
                        AppSimulation.simulateLoading();
                        for (String travail : travaux) {
                            System.out.println(travail);
                        }
                        AppSimulation.simulateWaitTime();
                        System.out.println("Tapez sur n'importe quelle touche pour retourner au menu principal.");
                        in.nextLine();  // Attend que l'utilisateur appuie sur Entrée

                        residentMainMenu(resident);
                        break;
                    case 0:
                        residentMainMenu(resident);
                        break;
                }
                break;
            case 3:
                resident.recevoirNotificationsPersonalisees(resident);
                break;
            case 4:
                System.out.println("Voulez-vous fournir des préférences ou consulter celles des autres ?");
                System.out.println(
                        "1. Fournir mes préférences\n" + "2. Fournir celles des autres\n" +
                                "3. Tapez '0' pour retourner au menu principal.");

                // Simuler les préférences
                String[] preferencesPersonnelles = {
                        "Préférence 1 : Travaux routiers en semaine uniquement.",
                        "Préférence 2 : Pas de travaux après 18h.",
                        "Préférence 3 : Minimiser les interruptions d'eau."
                };

                String[] preferencesAutres = {
                        "Préférence 1 : Travaux durant le week-end seulement.",
                        "Préférence 2 : Pas de travaux durant les vacances scolaires.",
                        "Préférence 3 : Préférence pour travaux sur les trottoirs."
                };

                switch(choice){
                    case 1:
                        System.out.println("Voici les dernières préférences que vous avez mises et celles des autres (selon le critère numéro " + choice + ") :");
                        AppSimulation.simulateLoading();
                        for (String preference : preferencesPersonnelles) {
                            System.out.println(preference);
                        }
                        AppSimulation.simulateWaitTime();

                        System.out.println("Tapez sur n'importe quelle touche pour retourner au menu principal.");
                        in.nextLine();  // Attend que l'utilisateur appuie sur Entrée

                        residentMainMenu(resident);
                        break;
                    case 2:
                        System.out.println("Voici les dernières préférences que vous avez mises et celles des autres (selon le critère numéro " + choice + ") :");
                        AppSimulation.simulateLoading();
                        for (String preference : preferencesAutres) {
                            System.out.println(preference);
                        }
                        AppSimulation.simulateWaitTime();
                        System.out.println("Tapez sur n'importe quelle touche pour retourner au menu principal.");
                        in.nextLine();  // Attend que l'utilisateur appuie sur Entrée

                        in.nextLine();
                        residentMainMenu(resident);
                        break;
                    case 0:
                        residentMainMenu(resident);
                        break;
                }
                choice = in.nextInt();
                in.nextLine();
                if(choice==0){
                    residentMainMenu(resident);}
                break;
            case 5:
                resident.soumettreRequeteTravail(resident);
                break;
            case 6:
                resident.signalerProbleme(resident);
                break;
            case 7:
                System.out.print("Déconnexion en cours ");
                AppSimulation.simulateLoading();
                System.out.println("Au revoir " + resident.getFirstName());
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
