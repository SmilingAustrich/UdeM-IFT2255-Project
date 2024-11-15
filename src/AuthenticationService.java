import java.util.HashMap;
import java.util.Map;

/**
 * La classe {@code AuthenticationService} gère l'authentification des résidents et intervenants dans l'application
 * Ma Ville. Elle permet d'inscrire des utilisateurs, vérifier les connexions et récupérer les utilisateurs par email.
 */
public class AuthenticationService {

    // Stockage des résidents et des intervenants dans des maps, avec l'email comme clé.
    private static Map<String, Resident> residentMap = new HashMap<>(); // On map pour chaque résident, un String (son email)
    private static Map<String, Intervenant> intervenantMap = new HashMap<>(); // On map pour chaque intervenant, un String (son email)

    /**
     * Bloc statique pour initialiser des utilisateurs de test dans l'application.
     * Un résident et un intervenant de test sont créés avec des emails et mots de passe prédéfinis.
     */
    static {
        // Création d'un résident de test avec un mot de passe
        Resident testResident = new Resident("John", "Smiths", "resident@prototype.com", "password123", "514-555-1234", "123 Rue de la Paix, Montréal", 18);
        residentMap.put(testResident.getEmail(), testResident);

        Resident fastTestResident = new Resident("John", "Smiths", "test", "test", "514-555-1234", "123 Rue de la Paix, Montréal", 18);
        residentMap.put(fastTestResident.getEmail(), fastTestResident);

        // Création d'un intervenant de test avec un mot de passe
        Intervenant testIntervenant = new Intervenant("Jane", "Doe", "intervenant@prototype.com", "password456", "12345678", 1);
        intervenantMap.put(testIntervenant.getEmail(), testIntervenant);
    }

    /**
     * Inscrit un nouveau résident dans l'application.
     * Si l'email du résident existe déjà, un message d'erreur est affiché.
     *
     * @param resident Le résident à inscrire
     */
    public static void signUpResident(Resident resident) {
        if (residentMap.containsKey(resident.getEmail())) {
            System.out.println("Un résident avec cet email existe déjà.");
        } else {
            residentMap.put(resident.getEmail(), resident); // Ajouter le résident à la map
            System.out.println("\nInscription réussie pour le résident : " + resident.getFirstName());
        }
    }

    /**
     * Inscrit un nouvel intervenant dans l'application.
     * Si l'email de l'intervenant existe déjà, un message d'erreur est affiché.
     *
     * @param intervenant L'intervenant à inscrire
     */
    public static void signUpIntervenant(Intervenant intervenant) {
        if (intervenantMap.containsKey(intervenant.getEmail())) {
            System.out.println("Un intervenant avec cet email existe déjà.");
        } else {
            intervenantMap.put(intervenant.getEmail(), intervenant); // Ajouter l'intervenant à la map
            System.out.println("\nInscription réussie pour l'intervenant : " + intervenant.getFirstName());
        }
    }

    /**
     * Vérifie les informations de connexion d'un résident.
     * Si l'email et le mot de passe correspondent, la connexion est validée.
     *
     * @param email    L'adresse email du résident
     * @param password Le mot de passe du résident
     * @return {@code true} si la connexion est réussie, {@code false} sinon
     */
    public static boolean loginResident(String email, String password) {
        Resident resident = residentMap.get(email); // Récupérer le résident par email

        if (resident != null && resident.getPassword().equals(password)) {
            return true; // Connexion réussie
        } else {
            return false; // Connexion échouée
        }
    }

    /**
     * Vérifie les informations de connexion d'un intervenant.
     * Si l'email et le mot de passe correspondent, la connexion est validée.
     *
     * @param email    L'adresse email de l'intervenant
     * @param password Le mot de passe de l'intervenant
     * @return {@code true} si la connexion est réussie, {@code false} sinon
     */
    public static boolean loginIntervenant(String email, String password) {
        Intervenant intervenant = intervenantMap.get(email); // Récupérer l'intervenant par email

        if (intervenant != null && intervenant.getPassword().equals(password)) {
            return true; // Connexion réussie
        } else {
            return false; // Connexion échouée
        }
    }

    /**
     * Récupère un résident par son adresse email.
     *
     * @param email L'adresse email du résident
     * @return Le résident correspondant à cet email, ou {@code null} si aucun résident n'existe avec cet email
     */
    public static Resident getResidentByEmail(String email) {
        return residentMap.get(email); // Retourne le résident correspondant à cet email
    }

    /**
     * Récupère un intervenant par son adresse email.
     *
     * @param email L'adresse email de l'intervenant
     * @return L'intervenant correspondant à cet email, ou {@code null} si aucun intervenant n'existe avec cet email
     */
    public static Intervenant getIntervenantByEmail(String email) {
        return intervenantMap.get(email); // Retourne l'intervenant correspondant à cet email
    }

    public static Map<String, Resident> getResidentMap() {
        return residentMap;
    }

    public static Map<String, Intervenant> getIntervenantMap() {
        return intervenantMap;
    }
}

