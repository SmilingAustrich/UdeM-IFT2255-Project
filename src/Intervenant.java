import java.util.*;

/**
 * La classe {@code Intervenant} représente un utilisateur de type intervenant dans l'application Ma Ville.
 * Un intervenant est un professionnel qui peut soumettre des projets de travaux et consulter des requêtes.
 */
public class Intervenant implements User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String cityIdCode;
    private int entrepreneurType;

    /**
     * Constructeur de la classe {@code Intervenant}.
     *
     * @param firstName        Le prénom de l'intervenant
     * @param lastName         Le nom de famille de l'intervenant
     * @param email            L'adresse email de l'intervenant
     * @param password         Le mot de passe de l'intervenant
     * @param cityIdCode       Le code d'identification de la ville (à 8 chiffres)
     * @param entrepreneurType Le type d'entrepreneur (privé, public, particulier)
     */
    public Intervenant(String firstName, String lastName, String email, String password, String cityIdCode, int entrepreneurType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.entrepreneurType = entrepreneurType;
        this.cityIdCode = cityIdCode;
    }

    /**
     * Retourne le prénom de l'intervenant.
     *
     * @return le prénom de l'intervenant
     */
    @Override
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retourne le nom de famille de l'intervenant.
     *
     * @return le nom de famille de l'intervenant
     */
    @Override
    public String getLastName() {
        return lastName;
    }

    /**
     * Retourne l'adresse email de l'intervenant.
     *
     * @return l'adresse email de l'intervenant
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * Retourne le mot de passe de l'intervenant.
     *
     * @return le mot de passe de l'intervenant
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Retourne le type d'entrepreneur de l'intervenant.
     *
     * @return le type d'entrepreneur (numérique)
     */
    public int getEntrepreneurType() {
        return entrepreneurType;
    }

    /**
     * Retourne le code d'identification de la ville pour l'intervenant.
     *
     * @return le code d'identification de la ville
     */
    public String getCityIdCode() {
        return cityIdCode;
    }


    public void consulterListeRequetesTravaux(Intervenant intervenant, List<String> requetes) {
        Scanner in = new Scanner(System.in);
        System.out.println("Voulez-vous filtrer les requêtes par : ");
        System.out.println("1. Type de travaux");
        System.out.println("2. Quartier");
        System.out.println("3. Date de début");
        System.out.println("Tapez '0' pour retourner au menu principal.");
        System.out.print("Veuillez entrer votre choix (0-3) : ");
        int choix = in.nextInt();
        in.nextLine();

        if (choix == 0) {
            Menu.intervenantMainMenu(intervenant); // Retourne au menu principal
            return;
        }

        switch (choix) {
            case 1:
                System.out.print("Entrez le type de travaux (routiers, électricité, etc.) : ");
                String typeTravaux = in.nextLine();
                if (typeTravaux.equals("0")) {
                    Menu.intervenantMainMenu(intervenant); // Retourne au menu principal
                    return;
                }
                System.out.println("Voici les requêtes pour des travaux du type " + typeTravaux + " :");
                for (String requete : requetes) {
                    if (requete.contains(typeTravaux)) {
                        System.out.println(requete);
                    }
                }
                break;

            case 2:
                System.out.print("Entrez le quartier : ");
                String quartier = in.nextLine();
                if (quartier.equals("0")) {
                    Menu.intervenantMainMenu(intervenant); // Retourne au menu principal
                    return;
                }
                System.out.println("Voici les requêtes pour le quartier " + quartier + " :");
                for (String requete : requetes) {
                    if (requete.contains(quartier)) {
                        System.out.println(requete);
                    }
                }
                break;

            case 3:
                System.out.print("Entrez la date de début (format jj/mm/aaaa) : ");
                String dateDebut = in.nextLine();
                if (dateDebut.equals("0")) {
                    Menu.intervenantMainMenu(intervenant); // Retourne au menu principal
                    return;
                }
                System.out.println("Voici les requêtes à commencer après " + dateDebut + " :");
                for (String requete : requetes) {
                    if (requete.contains(dateDebut)) {
                        System.out.println(requete);
                    }
                }
                break;

            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
        }

        
        
        System.out.println("Tapez sur n'importe quel touche pour retourner au menu principal.");
        in.nextLine();
        System.out.println("Retour au menu principal.");
        Menu.intervenantMainMenu(intervenant);
    }



    /**
     * Permet à l'intervenant de proposer une plage horaire pour les travaux avec une option pour retourner au menu principal.
     */
    public void proposerPlageHoraire(Intervenant intervenant) {
        Scanner in = new Scanner(System.in);
        System.out.println("Veuillez proposer une plage horaire pour les travaux. Tapez '0' à tout moment pour retourner au menu principal.");
        System.out.print("Date (format JJ/MM/AAAA) >: ");
        String date = in.nextLine();
        if (date.equals("0")) {
            Menu.intervenantMainMenu(intervenant); // Retourne au menu principal
        }

        System.out.print("Heure de début (format HH:MM) >: ");
        String heureDebut = in.nextLine();
        if (heureDebut.equals("0")) {
            Menu.intervenantMainMenu(intervenant); // Retourne au menu principal
        }

        System.out.print("Heure de fin (format HH:MM) >: ");
        String heureFin = in.nextLine();
        if (heureFin.equals("0")) {
            Menu.intervenantMainMenu(intervenant); // Retourne au menu principal
        }

        System.out.println("Plage horaire proposée : " + date + " de " + heureDebut + " à " + heureFin);

        
        

        System.out.println("Retour au menu principal");
        Menu.intervenantMainMenu(intervenant);
    }

    /**
     * Permet à l'intervenant de soumettre un nouveau projet de travaux.
     * Lors de la soumission, l'intervenant peut consulter les préférences des résidents
     * et est informé de tout conflit avec ces préférences avant de soumettre le projet.
     */
    public void soumettreProjetTravaux(Intervenant intervenant) {
        Scanner in = new Scanner(System.in);
        List<String> quartiers = Arrays.asList("Plateau", "Rosemont", "Ville-Marie", "Outremont", "Hochelaga");
        List<String> rues = Arrays.asList("Rue Saint-Denis", "Rue Sherbrooke", "Avenue du Parc", "Boulevard Saint-Laurent", "Rue Sainte-Catherine");

        Map<String, String> preferencesResidents = new HashMap<>();
        preferencesResidents.put("Plateau", "Matin (8h-12h)");
        preferencesResidents.put("Rosemont", "Après-midi (12h-16h)");
        preferencesResidents.put("Ville-Marie", "Soirée (16h-20h)");
        preferencesResidents.put("Outremont", "Matin (8h-12h)");
        preferencesResidents.put("Hochelaga", "Après-midi (12h-16h)");

        System.out.println("Tapez '0' à tout moment pour retourner au menu principal.");

        System.out.print("Titre du projet >: ");
        String titre = in.nextLine();
        if (titre.equals("0")) {
            Menu.intervenantMainMenu(intervenant);
            return;
        }

        System.out.print("Description du projet >: ");
        String description = in.nextLine();
        if (description.equals("0")) {
            Menu.intervenantMainMenu(intervenant);
            return;
        }

        System.out.print("Type de travaux (routiers, électricité, etc.) >: ");
        String typeTravaux = in.nextLine();
        if (typeTravaux.equals("0")) {
            Menu.intervenantMainMenu(intervenant);
            return;
        }

        System.out.println("Quartiers affectés disponibles : " + quartiers);
        System.out.print("Entrez le quartier affecté (sélectionnez un seul) >: ");
        String quartier = in.nextLine();
        if (quartier.equals("0")) {
            Menu.intervenantMainMenu(intervenant);
            return;
        }
        if (!quartiers.contains(quartier)) {
            System.out.println("Quartier invalide. Retour au menu principal.");
            Menu.intervenantMainMenu(intervenant);
            return;
        }

        System.out.println("Rues affectées disponibles : " + rues);
        System.out.print("Entrez la rue affectée (sélectionnez une rue) >: ");
        String rue = in.nextLine();
        if (rue.equals("0")) {
            Menu.intervenantMainMenu(intervenant);
            return;
        }
        if (!rues.contains(rue)) {
            System.out.println("Rue invalide. Retour au menu principal.");
            Menu.intervenantMainMenu(intervenant);
            return;
        }

        System.out.print("Date de début (format jj/mm/aaaa) >: ");
        String dateDebut = in.nextLine();
        if (dateDebut.equals("0")) {
            Menu.intervenantMainMenu(intervenant);
            return;
        }

        System.out.print("Date de fin (format jj/mm/aaaa) >: ");
        String dateFin = in.nextLine();
        if (dateFin.equals("0")) {
            Menu.intervenantMainMenu(intervenant);
            return;
        }

        System.out.print("Horaire des travaux (ex: 8h-12h) >: ");
        String horaire = in.nextLine();
        if (horaire.equals("0")) {
            Menu.intervenantMainMenu(intervenant);
            return;
        }

        // Consultation des préférences des résidents pour le quartier sélectionné
        String preferences = preferencesResidents.getOrDefault(quartier, "Pas de préférence spécifique");
        System.out.println("Les préférences des résidents pour le quartier " + quartier + " sont : " + preferences);

        // Simulation d'un conflit potentiel avec les préférences
        if (!preferences.equalsIgnoreCase(horaire)) {
            System.out.println("Conflit potentiel avec les préférences des résidents. Souhaitez-vous continuer malgré tout ? (oui/non)");
            String choix = in.nextLine();
            if (choix.equalsIgnoreCase("non")) {
                System.out.println("Projet annulé. Retour au menu principal.");
                Menu.intervenantMainMenu(intervenant);
                return;
            }
        }

        // Projet soumis avec succès
        System.out.println("Projet soumis avec succès !");
        System.out.println("Détails du projet soumis :");
        System.out.println("Titre : " + titre);
        System.out.println("Description : " + description);
        System.out.println("Type de travaux : " + typeTravaux);
        System.out.println("Quartier affecté : " + quartier);
        System.out.println("Rue affectée : " + rue);
        System.out.println("Date de début : " + dateDebut);
        System.out.println("Date de fin : " + dateFin);
        System.out.println("Horaire des travaux : " + horaire);

        System.out.println("Tapez sur n'importe quel touche pour retourner au menu principal.");
        in.nextInt();
        in.nextLine();

        // Retour au menu principal
        Menu.intervenantMainMenu(intervenant);
    }

    /**
     * Permet à un intervenant de soumettre une candidature pour un travail.
     * @param intervenant L'intervenant soumettant la candidature
     */
    public void soumettreCandidatureTravail(Intervenant intervenant) {
        Scanner in = new Scanner(System.in);

        System.out.println("--------------------------");
        System.out.println("Soumission de candidature pour un travail.");
        System.out.println("Tapez '0' à tout moment pour retourner au menu principal.");

        // Titre du travail
        System.out.print("Titre du travail >: ");
        String titre = in.nextLine();
        if (titre.equals("0")) {
            Menu.intervenantMainMenu(intervenant);
            return;
        }

        // Description du travail
        System.out.print("Description du travail >: ");
        String description = in.nextLine();
        if (description.equals("0")) {
            Menu.intervenantMainMenu(intervenant);
            return;
        }

        // Date de début du travail
        System.out.print("Date de début (jj/mm/aaaa) >: ");
        String dateDebut = in.nextLine();
        if (dateDebut.equals("0")) {
            Menu.intervenantMainMenu(intervenant);
            return;
        }

        // Date de fin du travail
        System.out.print("Date de fin (jj/mm/aaaa) >: ");
        String dateFin = in.nextLine();
        if (dateFin.equals("0")) {
            Menu.intervenantMainMenu(intervenant);
            return;
        }

        // Simulation de la soumission de la candidature
        
        System.out.println("--------------------------");
        System.out.println("Candidature soumise avec succès.");
        System.out.println("Résumé de la candidature soumise :");
        System.out.println("Titre : " + titre);
        System.out.println("Description : " + description);
        System.out.println("Date de début : " + dateDebut);
        System.out.println("Date de fin : " + dateFin);
        System.out.println("--------------------------");

        System.out.println("Tapez sur n'importe quel touche pour retourner au menu principal.");
        in.nextInt();
        in.nextLine();
        // Retour au menu principal
        Menu.intervenantMainMenu(intervenant);
    }


}