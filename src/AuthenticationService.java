import java.util.HashMap;
import java.util.Map;

public class AuthenticationService {

    // Stocker les résidents et les intervenants
    private static Map<String, Resident> residentMap = new HashMap<>(); // Clé = email
    private static Map<String, Intervenant> intervenantMap = new HashMap<>(); // Clé = email

    // Méthode pour inscrire un résident
    public static void signUpResident(Resident resident) {
        if (residentMap.containsKey(resident.getEmail())) {
            System.out.println("Un résident avec cet email existe déjà.");
        } else {
            residentMap.put(resident.getEmail(), resident); // Ajouter le résident à la map
            System.out.println("Inscription réussie pour le résident : " + resident.getFirstName());
        }
    }

    // Méthode pour inscrire un intervenant
    public static void signUpIntervenant(Intervenant intervenant) {
        if (intervenantMap.containsKey(intervenant.getEmail())) {
            System.out.println("Un intervenant avec cet email existe déjà.");
        } else {
            intervenantMap.put(intervenant.getEmail(), intervenant); // Ajouter l'intervenant à la map
            System.out.println("Inscription réussie pour l'intervenant : " + intervenant.getFirstName());
        }
    }

    // Méthode pour vérifier la connexion du résident
    public static boolean loginResident(String email, String password) {
        Resident resident = residentMap.get(email); // Récupérer le résident par email

        if (resident != null && resident.getPassword().equals(password)) {
            return true; // Connexion réussie
        } else {
            return false; // Connexion échouée
        }
    }

    // Méthode pour vérifier la connexion de l'intervenant
    public static boolean loginIntervenant(String email, String password) {
        Intervenant intervenant = intervenantMap.get(email); // Récupérer l'intervenant par email

        if (intervenant != null && intervenant.getPassword().equals(password)) {
            return true; // Connexion réussie
        } else {
            return false; // Connexion échouée
        }
    }
}
