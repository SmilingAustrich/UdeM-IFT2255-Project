import java.util.List;
import java.util.Scanner;

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

    /**
     * Permet à l'intervenant de consulter la liste des requêtes de travaux.
     * L'intervenant peut filtrer les requêtes par type de travaux, quartier ou date de début.
     *
     * @param requetes La liste des requêtes de travaux disponibles
     */


    public void consulterListeRequetesTravaux(List<String> requetes) {
        Scanner in = new Scanner(System.in);
        System.out.println("Voulez-vous filtrer les requêtes par : ");
        System.out.println("1. Type de travaux");
        System.out.println("2. Quartier");
        System.out.println("3. Date de début");
        System.out.print("Veuillez entrer votre choix (1-3) : ");
        int choix = in.nextInt();
        in.nextLine();

        switch (choix) {
            case 1:
                System.out.print("Entrez le type de travaux (routiers, électricité, etc.) : ");
                String typeTravaux = in.nextLine();
                System.out.println("Voici les requêtes pour des travaux du type " + typeTravaux + " :");
                // Afficher les requêtes filtrées par type de travaux
                break;

            case 2:
                System.out.print("Entrez le quartier : ");
                String quartier = in.nextLine();
                System.out.println("Voici les requêtes pour le quartier " + quartier + " :");
                // Afficher les requêtes filtrées par quartier
                break;

            case 3:
                System.out.print("Entrez la date de début (format jj/mm/aaaa) : ");
                String dateDebut = in.nextLine();
                System.out.println("Voici les requêtes à commencer après " + dateDebut + " :");
                // Afficher les requêtes filtrées par date de début
                break;

            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
        }
    }

    /**
     * Permet à l'intervenant de proposer une plage horaire pour les travaux.
     */
    public void proposerPlageHoraire() {
        Scanner in = new Scanner(System.in);
        System.out.println("Veuillez proposer une plage horaire pour les travaux.");
        System.out.print("Date (format JJ/MM/AAAA) >: ");
        String date = in.nextLine();
        System.out.print("Heure de début (format HH:MM) >: ");
        String heureDebut = in.nextLine();
        System.out.print("Heure de fin (format HH:MM) >: ");
        String heureFin = in.nextLine();
        System.out.println("Plage horaire proposée : " + date + " de " + heureDebut + " à " + heureFin);
        
    }

    /**
     * Permet à l'intervenant de soumettre une candidature pour un travail.
     */
    public void soumettreCandidatureTravail() {
        Scanner in = new Scanner(System.in);
        System.out.println("Soumettre une candidature pour un travail.");
        System.out.print("Nom du travail >: ");
        String jobTitle = in.nextLine();
        
        System.out.println("Candidature soumise avec succès pour le travail : " + jobTitle);
    }
}
