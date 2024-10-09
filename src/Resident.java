import java.util.Scanner;

/**
 * La classe {@code Resident} représente un utilisateur résident dans l'application Ma Ville.
 * Un résident peut consulter les travaux, signaler des problèmes, et recevoir des notifications personnalisées.
 */
public class Resident implements User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String dob;

    /**
     * Constructeur de la classe {@code Resident}.
     *
     * @param firstName Le prénom du résident
     * @param lastName  Le nom de famille du résident
     * @param email     L'adresse email du résident
     * @param password  Le mot de passe du résident
     * @param phone     Le numéro de téléphone du résident (optionnel)
     * @param address   L'adresse résidentielle
     * @param dob       La date de naissance du résident (format dd/mm/yy)
     */
    public Resident(String firstName, String lastName, String email, String password, String phone, String address, String dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
    }

    /**
     * Retourne le prénom du résident.
     *
     * @return le prénom du résident
     */
    @Override
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retourne le nom de famille du résident.
     *
     * @return le nom de famille du résident
     */
    @Override
    public String getLastName() {
        return lastName;
    }

    /**
     * Retourne l'adresse email du résident.
     *
     * @return l'adresse email du résident
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * Retourne le mot de passe du résident.
     *
     * @return le mot de passe du résident
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Retourne le numéro de téléphone du résident.
     *
     * @return le numéro de téléphone du résident
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Retourne l'adresse de résidence du résident.
     *
     * @return l'adresse de résidence du résident
     */
    public String getAddress() {
        return address;
    }

    /**
     * Retourne la date de naissance du résident.
     *
     * @return la date de naissance du résident (format dd/mm/yy)
     */
    public String getDob() {
        return dob;
    }

    /**
     * Simule le signalement d'un problème à la ville par le résident avec une option pour retourner au menu principal.
     * Le résident doit fournir le type et la description du problème.
     */
    public void signalerProbleme(Resident resident) {
        Scanner in = new Scanner(System.in);

        System.out.println("Signalement de problème pour le résident: " + this.getFirstName() + " " + this.getLastName());
        System.out.println("Adresse courriel: " + this.getEmail());
        System.out.println("Adresse de résidence: " + this.getAddress());

        System.out.println("Tapez '0' à tout moment pour retourner au menu principal.");

        System.out.print("Veuillez indiquer le type de problème: ");
        String typeProbleme = in.nextLine();
        if (typeProbleme.equals("0")) {
            return; // Retourne au menu principal
        }

        System.out.print("Veuillez entrer une description détaillée du problème: ");
        String descriptionProbleme = in.nextLine();
        if (descriptionProbleme.equals("0")) {
            Menu.residentMainMenu(resident);
        }

        // Simulation de l'envoi des données
        System.out.print("Envoi des données ");
        AppSimulation.simulateLoading();
        System.out.println("\n------------------------------");
        System.out.println("Problème signalé à la ville :\n");
        System.out.println("Nom du résident : " + this.getFirstName() + " " + this.getLastName());
        System.out.println("Adresse courriel : " + this.getEmail());
        System.out.println("Adresse de résidence : " + this.getAddress());
        System.out.println("Type de problème : " + typeProbleme);
        System.out.println("Description du problème : " + descriptionProbleme);
        System.out.println("------------------------------");
        System.out.println("Votre problème a été signalé à la Ville de Montréal.");
    }


    /**
     * Permet au résident de consulter les travaux en cours ou à venir avec une option pour retourner au menu principal.
     * Le résident peut filtrer les travaux par quartier, type de travaux, ou rue.
     */
    public void consulterTravaux(Resident resident) {
        Scanner in = new Scanner(System.in);
        System.out.println("Voulez-vous filtrer les travaux par : ");
        System.out.println("1. Quartier");
        System.out.println("2. Type de travaux");
        System.out.println("3. Rue");
        System.out.println("Tapez '0' pour retourner au menu principal.");
        System.out.print("Veuillez entrer votre choix (0-3) : ");
        int choix = in.nextInt();
        in.nextLine(); // Consomme le retour de ligne

        if (choix == 0) {
            Menu.residentMainMenu(resident);
        }

        switch (choix) {
            case 1:
                System.out.print("Entrez le quartier : ");
                String quartier = in.nextLine();
                if (quartier.equals("0")) {
                    Menu.residentMainMenu(resident);
                }
                System.out.println("Voici les travaux pour le quartier " + quartier + " :");
                // Afficher les travaux pour le quartier donné
                break;

            case 2:
                System.out.print("Entrez le type de travaux (routiers, électricité, etc.) : ");
                String typeTravaux = in.nextLine();
                if (typeTravaux.equals("0")) {
                    Menu.residentMainMenu(resident);; // Retourne au menu principal
                }
                System.out.println("Voici les travaux du type " + typeTravaux + " :");
                // Afficher les travaux par type
                break;

            case 3:
                System.out.print("Entrez le nom de la rue : ");
                String rue = in.nextLine();
                if (rue.equals("0")) {
                    Menu.residentMainMenu(resident);; // Retourne au menu principal
                }
                System.out.println("Voici les travaux pour la rue " + rue + " :");
                // Afficher les travaux pour la rue donnée
                break;

            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
        }
    }


    /**
     * Permet au résident de recevoir des notifications personnalisées avec une option pour retourner au menu principal.
     */
    public void recevoirNotificationsPersonalisees(Resident resident) {
        Scanner in = new Scanner(System.in);
        System.out.println("Vous êtes automatiquement abonné aux notifications pour des projets dans votre quartier: Montréal");
        System.out.print("Voulez-vous également recevoir des notifications pour un autre quartier ou une rue spécifique ? (oui/non). Tapez '0' pour retourner au menu principal : ");
        String choix = in.nextLine();
        if (choix.equals("0")) {
            Menu.residentMainMenu(resident); // Retourne au menu principal
        }

        if (choix.equalsIgnoreCase("Oui")) {
            System.out.print("Entrez le quartier ou la rue spécifique pour laquelle vous souhaitez recevoir des notifications : ");
            String zoneSupplementaire = in.nextLine();
            if (zoneSupplementaire.equals("0")) {
                Menu.residentMainMenu(resident); // Retourne au menu principal
            }
            System.out.println("Vous recevrez maintenant des notifications pour des projets dans la zone : " + zoneSupplementaire);
        } else {
            System.out.println("Aucune autre zone n'a été ajoutée. Vous continuerez à recevoir des notifications pour votre quartier.");
        }
    }

    /**
     * Permet au résident de soumettre une requête de travail avec une option pour retourner au menu principal.
     */
    public void soumettreRequeteTravail(Resident resident) {
        Scanner in = new Scanner(System.in);
        System.out.println("Tapez '0' à tout moment pour retourner au menu principal.");
        System.out.print("Description des travaux >: ");
        String description = in.nextLine();
        if (description.equals("0")) {
            Menu.residentMainMenu(resident); // Retourne au menu principal
        }

        System.out.println("Requête soumise avec succès : " + description);
    }
}