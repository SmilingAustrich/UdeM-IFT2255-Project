import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
     * Affiche un dessin de logo de bienvenue pour l'application "Ma Ville".
     */
    private void afficherLogoBienvenue() {
        System.out.println(
                "\033[1;34m**************************************************\033[0m\n" +
                        "\033[1;34m*                                                *\033[0m\n" +
                        "\033[1;34m*   \033[1;33mBIENVENUE SUR tVOTRE APPLICATION MA VILLE!\033[1;34m    *\033[0m\n" +
                        "\033[1;34m*                                                *\033[0m\n" +
                        "\033[1;34m**************************************************\033[0m\n"
        );
    }

    /**
     * Affiche le menu de connexion et d'inscription pour les résidents et les intervenants.
     * L'utilisateur peut choisir entre se connecter ou s'inscrire.
     */
    public void InitializeApp() {
        afficherLogoBienvenue();
        Scanner in = new Scanner(System.in);
        System.out.print(
                "\033[1;32m==================================================\033[0m\n" +
                        "\033[1;32m|            \033[1;37mMENU PRINCIPAL - MA VILLE\033[1;32m            |\033[0m\n" +
                        "\033[1;32m==================================================\033[0m\n" +
                        "\033[1;32m|                                                |\033[0m\n" +
                        "\033[1;32m|  \033[1;33mI. Se connecter en tant que :\033[1;32m                 |\033[0m\n" +
                        "\033[1;32m|     \033[1;37m1. Résident\033[1;32m                                |\033[0m\n" +
                        "\033[1;32m|     \033[1;37m2. Intervenant\033[1;32m                             |\033[0m\n" +
                        "\033[1;32m|                                                |\033[0m\n" +
                        "\033[1;32m|  \033[1;33mII. S'inscrire en tant que :\033[1;32m                  |\033[0m\n" +
                        "\033[1;32m|     \033[1;37m3. Résident\033[1;32m                                |\033[0m\n" +
                        "\033[1;32m|     \033[1;37m4. Intervenant\033[1;32m                             |\033[0m\n" +
                        "\033[1;32m|                                                |\033[0m\n" +
                        "\033[1;32m==================================================\033[0m\n" +
                        "\033[1;36m:> \033[0m"
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
                System.out.println("\033[1;31m\nChoix invalide. Veuillez réessayer.\033[0m");
                InitializeApp();
                break;
        }
    }

    /**
     * Affiche le menu de connexion des résidents et gère la connexion en demandant l'email et le mot de passe.
     * En cas d'échec de connexion après deux essais, l'utilisateur est redirigé vers le menu d'inscription.
     */
    public void residentLogInMenu() {
        System.out.print(
                "\033[1;32m\n==================================================\033[0m\n" +
                        "\033[1;32m|          \033[1;37mPORTAIL DE CONNEXION - RÉSIDENT\033[1;32m       |\033[0m\n" +
                        "\033[1;32m==================================================\033[0m\n"
        );
        AppSimulation.simulateLoading();

        Scanner in = new Scanner(System.in);
        System.out.println(
                "\n\033[1;36mBienvenue sur le portail de connexion des résidents.\033[0m\n" +
                        "\033[1;36mVeuillez entrer votre email ainsi que votre mot de passe.\033[0m\n"
        );

        boolean loggedInResident = false;
        int numberOfTries = 0;
        Resident resident = null;

        while (!loggedInResident && numberOfTries < 3) {
            System.out.print("\033[1;33mEmail >: \033[0m");
            String email = in.nextLine();
            System.out.print("\033[1;33mMot de passe >: \033[0m");
            String password = in.nextLine();

            loggedInResident = AuthenticationService.loginResident(email, password);
            if (loggedInResident) {
                resident = AuthenticationService.getResidentByEmail(email);
                if (resident != null) {
                    System.out.print("\n\033[1;32mConnexion réussie ! Bienvenue, " + resident.getFirstName() + ".\033[0m\n");
                    AppSimulation.simulateLoading();
                    residentMainMenu(resident);
                } else {
                    System.out.println("\033[1;31mErreur : le résident n'a pas été trouvé dans la base de données.\033[0m");
                    loggedInResident = false; // Set to false to retry or fail
                }
            } else {
                System.out.print("\033[1;31m\n--------------------------------------------------\n");
                System.out.println("Nom d'utilisateur ou mot de passe incorrect. Veuillez réessayer.");
                System.out.print("--------------------------------------------------\033[0m\n");
                numberOfTries++;
            }

            if (numberOfTries >= 2 && !loggedInResident) {
                System.out.println("\n\033[1;31mDésolé, mais votre nom d'utilisateur ou votre mot de passe semble incorrect.\033[0m");
                System.out.print(
                        "\033[1;33mVous ne semblez pas être inscrit en tant que résident, redirection vers " +
                                "la page d'inscription.\033[0m\n"
                );
                AppSimulation.simulateLoading();
                residentInscriptionMenu();
                break;
            }
        }
    }


    /**
     * Affiche le menu de connexion des intervenants et gère la connexion en demandant l'email et le mot de passe.
     * En cas d'échec de connexion après deux essais, l'utilisateur est redirigé vers le menu d'inscription.
     */
    public void intervenantLogInMenu() {
        System.out.print(
                "\033[1;32m\n==================================================\033[0m\n" +
                        "\033[1;32m|         \033[1;37mPORTAIL DE CONNEXION - INTERVENANT\033[1;32m     |\033[0m\n" +
                        "\033[1;32m==================================================\033[0m\n"
        );
        AppSimulation.simulateLoading();

        Scanner in = new Scanner(System.in);
        System.out.println(
                "\n\033[1;36mBienvenue sur le portail de connexion des intervenants.\033[0m\n" +
                        "\033[1;36mVeuillez entrer votre email ainsi que votre mot de passe.\033[0m\n"
        );

        boolean loggedInIntervenant = false;
        int numberOfTries = 0;
        Intervenant intervenant = null;

        while (!loggedInIntervenant && numberOfTries < 3) {
            System.out.print("\033[1;33mEmail >: \033[0m");
            String email = in.nextLine();
            System.out.print("\033[1;33mMot de passe >: \033[0m");
            String password = in.nextLine();

            loggedInIntervenant = AuthenticationService.loginIntervenant(email, password);
            if (loggedInIntervenant) {
                intervenant = AuthenticationService.getIntervenantByEmail(email);
                if (intervenant != null) {
                    System.out.print("\n\033[1;32mConnexion réussie ! Bienvenue, " + intervenant.getFirstName() + ".\033[0m\n");
                    AppSimulation.simulateLoading();
                    intervenantMainMenu(intervenant);
                } else {
                    System.out.println("\033[1;31mErreur : l'intervenant n'a pas été trouvé dans la base de données.\033[0m");
                    loggedInIntervenant = false; // Set to false to retry or fail
                }
            } else {
                System.out.print("\033[1;31m\n--------------------------------------------------\n");
                System.out.println("Nom d'utilisateur ou mot de passe incorrect. Veuillez réessayer.");
                System.out.print("--------------------------------------------------\033[0m\n");
                numberOfTries++;
            }

            if (numberOfTries >= 2 && !loggedInIntervenant) {
                System.out.println("\n\033[1;31mDésolé, mais votre nom d'utilisateur ou votre mot de passe semble incorrect.\033[0m");
                System.out.print(
                        "\033[1;33mVous ne semblez pas être inscrit en tant qu'intervenant, redirection vers " +
                                "la page d'inscription.\033[0m\n"
                );
                AppSimulation.simulateLoading();
                intervenantInscriptionMenu();
                break;
            }
        }
    }



    /**
     * Affiche le menu d'inscription pour les résidents et enregistre un nouveau résident.
     * Une fois l'inscription réussie, le résident est redirigé vers le menu de connexion.
     */
    public String residentInscriptionMenu() {
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
        int age = promptForValidDate(in, "Date de naissance (format jj/mm/aaaa) >: ");

        if(age < 16){
            return "Le compte ne peut pas être créer, vous devez avoir minimum 16 ans.";
        }
        Resident resident = new Resident(
                firstNameResident, lastNameResident, emailResident, passwordResident,
                phoneResident, addressResident, age
        );

        AuthenticationService.signUpResident(resident);

        System.out.println("Bienvenue " + resident.getFirstName() + "! Vous pouvez maintenant vous connecter.");
        residentLogInMenu();
        return firstNameResident;
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
    private int promptForValidDate(Scanner in, String prompt) {
        String date;
        Pattern pattern = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate birthDate = null;
        do {
            System.out.print(prompt);
            date = in.nextLine().trim();
            Matcher matcher = pattern.matcher(date);
            if (!matcher.matches()) {
                System.out.println("Date invalide. Veuillez entrer une date au format jj/mm/aaaa.");
                continue;
            }
            try {
                birthDate = LocalDate.parse(date, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Erreur de formatage de la date. Veuillez réessayer.");
            }
        } while (birthDate == null);

        // Calculate age
        return Period.between(birthDate, LocalDate.now()).getYears();
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
     * Affiche un menu d'inscription magnifiquement présenté pour les intervenants, et enregistre un nouvel intervenant.
     * Une fois l'inscription réussie, l'intervenant est redirigé vers le menu de connexion.
     */
    public void intervenantInscriptionMenu() {
        System.out.print("\n==========================================\n" +
                "\u2728 Bienvenue au Portail d'Inscription des Intervenants \u2728\n" +
                "==========================================\n\n");

        System.out.print("Chargement du portail d'inscription …\n");
        AppSimulation.simulateLoading();
        System.out.print("\n------------------------------------------\n");

        Scanner in = new Scanner(System.in);
        System.out.println("\u2764\ufe0f Nous sommes ravis de vous accueillir ! Veuillez fournir les informations ci-dessous pour vous inscrire.\n");

        // Validation des différents champs
        String firstNameIntervenant = promptForNonEmptyInput(in, "\ud83d\udc64 Prénom >: ");
        String lastNameIntervenant = promptForNonEmptyInput(in, "\ud83d\udc65 Nom de famille >: ");
        String emailIntervenant = promptForValidEmail(in, "\ud83d\udcbb Adresse courriel >: ");
        String passwordIntervenant = promptForPassword(in, "\ud83d\udd12 Mot de passe (minimum 8 caractères) >: ");
        String cityId = promptForValidCityId(in, "\ud83c\udfd9 Identifiant de la ville (code à 8 chiffres) >: ");

        System.out.print("\n\ud83c\udf93 Type d'entrepreneur (numérique)\n" +
                "1. \ud83d\udcbc Entreprise privée\n" +
                "2. \ud83c\udfe2 Entreprise publique\n" +
                "3. \ud83c\udf6d Particulier\n >: ");
        int entrepreneurType = in.nextInt();

        Intervenant intervenant = new Intervenant(
                firstNameIntervenant, lastNameIntervenant, emailIntervenant, passwordIntervenant, cityId, entrepreneurType
        );

        System.out.println("\n\ud83c\udf89 Merci pour votre inscription, " + intervenant.getFirstName() + " ! Nous validons vos informations …");
        AppSimulation.simulateLoading();
        AuthenticationService.signUpIntervenant(intervenant);

        System.out.print("\n\ud83d\udee0\ufe0f Inscription réussie ! Vous pouvez maintenant vous connecter.\n\n");
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
                "\033[1;33m\n⭐️****************************⭐️\033[0m\n" +
                        "\033[1;32m🥇 Bienvenue, " + intervenant.getFirstName() + "! 🥇\033[0m\n" +
                        "\033[1;36mVous êtes sur le menu principal des intervenants de l'application Ma Ville.\033[0m\n" +
                        "\033[1;36m💻 Veuillez choisir une option dans la liste suivante :\033[0m\n" +
                        "\033[1;37m    1. 🛠️ Consulter la liste des requêtes de travail.\033[0m\n" +
                        "\033[1;37m    2. 📈 Soumettre un nouveau projet de travaux.\033[0m\n" +
                        "\033[1;37m    3. 🔧 Mettre à jour les informations d'un chantier.\033[0m\n" +
                        "\033[1;37m    4. 📅 Proposer une plage horaire pour les travaux.\033[0m\n" +
                        "\033[1;37m    5. 🛍️ Soumettre une candidature pour un travail.\033[0m\n" +
                        "\033[1;37m    6. 🔒 Se déconnecter.\033[0m\n" +
                        "\033[1;36m⏳ Tapez '0' à tout moment pour retourner au menu principal.\033[0m\n" +
                        "\033[1;33m\n✍️ Insérer le numéro qui correspond à votre choix : \033[0m"
        );
        int choice = in.nextInt();
        in.nextLine();
        System.out.println("\033[1;33m⭐️****************************⭐️\033[0m\n");

        switch (choice) {
            case 0:
                intervenantMainMenu(intervenant); // Retourne au menu principal de l'application
                break;
            case 1:
                System.out.println("\033[1;36m📚 Consultation des requêtes de travaux disponibles...\033[0m");
                intervenant.consulterListeRequetesTravaux(intervenant, new ArrayList<>(Arrays.asList("Travaux routiers", "Entretien électrique")));
                break;
            case 2:
                System.out.println("\033[1;36m📖 Soumission d'un nouveau projet de travaux...\033[0m");
                intervenant.soumettreProjetTravaux(intervenant);
                break;
            case 3:
                System.out.println("\033[1;36m🔨 Mise à jour des informations d'un chantier.\033[0m");
                System.out.println("\033[1;36mQue voulez-vous mettre à jour ? La description du projet, la date de fin prévue\033[0m" +
                        "\033[1;36m ou voulez-vous changer le statut du projet ? :\033[0m");
                System.out.println("\033[1;36mTapez '0' pour retourner au menu principal.\033[0m");
                int updateChoice = in.nextInt();
                in.nextLine();
                if (updateChoice == 0) {
                    intervenantMainMenu(intervenant); // Retourne au menu principal de l'intervenant
                } else {
                    System.out.println("\033[1;36m🔧 Mise à jour des informations du chantier en cours...\033[0m");
                }
                break;
            case 4:
                System.out.println("\033[1;36m📅 Proposer une nouvelle plage horaire pour les travaux...\033[0m");
                intervenant.proposerPlageHoraire(intervenant);
                break;
            case 5:
                System.out.println("\033[1;36m🛍️ Soumission d'une candidature pour un travail...\033[0m");
                intervenant.soumettreCandidatureTravail(intervenant);
                break;
            case 6:
                System.out.print("\033[1;33m🚶️ Déconnexion en cours\033[0m");
                AppSimulation.simulateLoading();
                System.out.println("\033[1;33mAu revoir, " + intervenant.getFirstName() + "! 👋\033[0m");
                break;

            default:
                System.out.println("\033[1;31m⚠ Choix invalide. Veuillez réessayer.\033[0m");
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
                "\033[1;34m\n🏠****************************🏠\033[0m\n" +
                        "\033[1;32m🍀 Bienvenue, " + resident.getFirstName() + "! 🍀\033[0m\n" +
                        "\033[1;36mVous êtes sur le menu principal des résidents de l'application Ma Ville.\033[0m\n" +
                        "\033[1;36m📄 Veuillez choisir une option dans la liste suivante :\033[0m\n" +
                        "\033[1;37m    1. 🛠️ Consulter les travaux en cours ou à venir.\033[0m\n" +
                        "\033[1;37m    2. 🔍 Rechercher des travaux.\033[0m\n" +
                        "\033[1;37m    3. 📢 Recevoir des notifications personnalisées.\033[0m\n" +
                        "\033[1;37m    4. 📅 Proposer des plages horaires pour des travaux.\033[0m\n" +
                        "\033[1;37m    5. 📜 Soumettre une requête de travail.\033[0m\n" +
                        "\033[1;37m    6. 🔗 Consulter les entraves en cours.\033[0m\n" +
                        "\033[1;37m    7. 🔒 Se déconnecter.\033[0m\n" +
                        "\033[1;33m\n✍️ Insérer le numéro qui correspond à votre choix : \033[0m"
        );
        int choice = in.nextInt();
        in.nextLine();
        System.out.println("\033[1;34m🏠****************************🏠\033[0m\n");

        switch (choice) {
            case 1:
                System.out.println("\033[1;36m🛠️ Consultation des travaux en cours ou à venir...\033[0m");
                resident.consulterTravaux();
                break;
            case 2:
                System.out.println("\033[1;36m🔍 Recherche de travaux...\033[0m");
                resident.rechercherTravaux();
                break;
            case 3:
                System.out.println("\033[1;36m📢 Configuration des notifications personnalisées...\033[0m");
                resident.recevoirNotificationsPersonalisees();
                break;
            case 4:
                System.out.println("\033[1;36m📅 Voulez-vous fournir des préférences ou consulter celles des autres ?\033[0m");
                System.out.println(
                        "\033[1;36m1. 🌱 Fournir mes préférences\033[0m\n" +
                                "\033[1;36m2. 🎓 Consulter celles des autres\033[0m\n" +
                                "\033[1;36m3. ⏹ Tapez '0' pour retourner au menu principal.\033[0m"
                );
                int preferenceChoice = in.nextInt();
                in.nextLine();
                switch (preferenceChoice) {
                    case 1:
                        System.out.println("\033[1;36m📖 Voici les préférences personnelles :\033[0m");
                        AppSimulation.simulateLoading();
                        System.out.println("\033[1;37m- Préférence 1 : Travaux routiers en semaine uniquement.\033[0m");
                        System.out.println("\033[1;37m- Préférence 2 : Pas de travaux après 18h.\033[0m");
                        System.out.println("\033[1;37m- Préférence 3 : Minimiser les interruptions d'eau.\033[0m");
                        AppSimulation.simulateWaitTime();
                        break;
                    case 2:
                        System.out.println("\033[1;36m📖 Voici les préférences des autres résidents :\033[0m");
                        AppSimulation.simulateLoading();
                        System.out.println("\033[1;37m- Préférence 1 : Travaux durant le week-end seulement.\033[0m");
                        System.out.println("\033[1;37m- Préférence 2 : Pas de travaux durant les vacances scolaires.\033[0m");
                        System.out.println("\033[1;37m- Préférence 3 : Travaux sur les trottoirs préférés.\033[0m");
                        AppSimulation.simulateWaitTime();
                        break;
                    case 0:
                        residentMainMenu(resident);
                        return;
                    default:
                        System.out.println("\033[1;31m⚠ Choix invalide. Retour au menu principal...\033[0m");
                        break;
                }
                residentMainMenu(resident);
                break;
            case 5:
                System.out.println("\033[1;36m📜 Soumission d'une requête de travail...\033[0m");
                resident.soumettreRequeteTravail(resident);
                break;
            case 6:
                System.out.println("\033[1;36m🔗 Consultation des entraves en cours...\033[0m");
                resident.consulterEntraves();
                break;
            case 7:
                System.out.print("\033[1;33m🚶️ Déconnexion en cours\033[0m");
                AppSimulation.simulateLoading();
                System.out.println("\033[1;33mAu revoir, " + resident.getFirstName() + "! 👋\033[0m");
                break;
            default:
                System.out.println("\033[1;31m⚠ Choix invalide. Veuillez réessayer.\033[0m");
                residentMainMenu(resident);
        }
    }

    /**
     * Démarre l'application en affichant le menu principal.
     */
    public void start() {
        InitializeApp();
    }
}
