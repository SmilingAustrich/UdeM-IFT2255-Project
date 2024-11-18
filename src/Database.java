import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public abstract class Database {

    private static final String DIRECTORY_PATH = Paths.get(System.getProperty("user.home"), "Desktop", "Ma Ville").toString();
    private static final String FILE_PATH = DIRECTORY_PATH + "/database.ser";

    // Stockage des résidents et des intervenants dans des maps, avec l'email comme clé.
    private static Map<String, Resident> residentMap = new HashMap<>();
    private static Map<String, Intervenant> intervenantMap = new HashMap<>();
    private static Map<Resident, ResidentialWorkRequest> residentialWorkMap = new HashMap<>();

    /**
     * Bloc statique pour initialiser ou charger des utilisateurs dans l'application.
     */
    static {
        System.out.println("\u001B[33mChargement du fichier de configuration...\u001B[0m");
        loadData();
        if (residentMap.isEmpty() && intervenantMap.isEmpty() && residentialWorkMap.isEmpty()) {
            System.out.println("\u001B[31mFichier non existant, création du répertoire Ma Ville...\u001B[0m");
            initializeTestData();
            saveData();
        }
    }

    /**
     * Méthode pour initialiser les utilisateurs de test.
     */
    private static void initializeTestData() {
        // Création de résidents de test avec des mots de passe
        Resident resident0 = new Resident("John", "Doe", "test", "test", "514-555-0001", "1 Rue de la Paix, Montréal", 25);
        Resident resident1 = new Resident("John", "Doe", "john.doe@example.com", "password1", "514-555-0001", "1 Rue de la Paix, Montréal", 25);
        Resident resident2 = new Resident("Jane", "Smith", "jane.smith@example.com", "password2", "514-555-0002", "2 Rue de la Paix, Montréal", 30);
        Resident resident3 = new Resident("Alice", "Brown", "alice.brown@example.com", "password3", "514-555-0003", "3 Rue de la Paix, Montréal", 28);

        residentMap.put(resident0.getEmail(), resident0);
        residentMap.put(resident1.getEmail(), resident1);
        residentMap.put(resident2.getEmail(), resident2);
        residentMap.put(resident3.getEmail(), resident3);

        // Création d'intervenants de test avec des mots de passe
        Intervenant intervenant0 = new Intervenant("Mark", "Taylor", "test", "test", "12345678", 1);
        Intervenant intervenant1 = new Intervenant("Mark", "Taylor", "mark.taylor@example.com", "test1", "12345678", 1);
        Intervenant intervenant2 = new Intervenant("Emma", "Wilson", "emma.wilson@example.com", "test2", "87654321", 2);
        Intervenant intervenant3 = new Intervenant("Luke", "Johnson", "luke.johnson@example.com", "test3", "13579246", 3);

        intervenantMap.put(intervenant0.getEmail(), intervenant0);
        intervenantMap.put(intervenant1.getEmail(), intervenant1);
        intervenantMap.put(intervenant2.getEmail(), intervenant2);
        intervenantMap.put(intervenant3.getEmail(), intervenant3);

        // Création de requêtes de travail résidentielles pour les résidents
        ResidentialWorkRequest requete1 = new ResidentialWorkRequest(resident1, "Réparation de toiture", "Réparer la toiture endommagée", "Construction", LocalDate.of(2024, 11, 20), "Vieux-Montréal");
        ResidentialWorkRequest requete2 = new ResidentialWorkRequest(resident2, "Réfection de la clôture", "Refaire la clôture du jardin", "Aménagement paysager", LocalDate.of(2024, 11, 25), "Plateau-Mont-Royal");
        ResidentialWorkRequest requete3 = new ResidentialWorkRequest(resident3, "Installation de panneaux solaires", "Installer des panneaux solaires sur le toit", "Énergie renouvelable", LocalDate.of(2024, 12, 5), "Rosemont");

        residentialWorkMap.put(resident1, requete1);
        residentialWorkMap.put(resident2, requete2);
        residentialWorkMap.put(resident3, requete3);
    }

    /**
     * Méthode pour sauvegarder les données dans un fichier.
     */
    public static void saveData() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            System.out.println("\u001B[31mCréation du répertoire Ma Ville...\u001B[0m");
            directory.mkdirs();
        }

        File file = new File(FILE_PATH);
        if (file.exists()) {
            System.out.println("\u001B[33mLe fichier database.ser existe déjà, aucune sauvegarde supplémentaire n'est effectuée pour éviter l'écrasement des données.\u001B[0m");
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            System.out.println("\u001B[32mCréation du fichier database.ser...\u001B[0m");
            oos.writeObject(residentMap);
            oos.writeObject(intervenantMap);
            oos.writeObject(residentialWorkMap);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des données: " + e.getMessage());
        }
    }

    /**
     * Méthode pour charger les données depuis un fichier.
     */
    @SuppressWarnings("unchecked")
    public static void loadData() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                residentMap = (Map<String, Resident>) ois.readObject();
                intervenantMap = (Map<String, Intervenant>) ois.readObject();
                residentialWorkMap = (Map<Resident, ResidentialWorkRequest>) ois.readObject();
                System.out.println("\u001B[32mChargement réussi des données de configuration.\u001B[0m");

                // Debugging : Afficher les données chargées
                System.out.println("Résidents chargés : " + residentMap);
                System.out.println("Intervenants chargés : " + intervenantMap);
                System.out.println("Requêtes chargées : " + residentialWorkMap);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erreur lors du chargement des données: " + e.getMessage());
            }
        } else {
            System.out.println("\u001B[31mFichier de configuration non trouvé, création des données initiales...\u001B[0m");
        }
    }

    /**
     * Récupère un intervenant par son adresse email.
     *
     * @param email L'adresse email de l'intervenant
     * @return L'intervenant correspondant à cet email, ou {@code null} si aucun intervenant n'existe avec cet email
     */
    public static Intervenant getIntervenantByEmail(String email) {
        return intervenantMap.get(email);
    }

    /**
     * Récupère un résident par son adresse email.
     *
     * @param email L'adresse email du résident
     * @return Le résident correspondant à cet email, ou {@code null} si aucun résident n'existe avec cet email
     */
    public static Resident getResidentByEmail(String email) {
        return residentMap.get(email);
    }

    public static Map<String, Intervenant> getIntervenantMap() {
        return intervenantMap;
    }

    public static Map<String, Resident> getResidentMap() {
        return residentMap;
    }

    public static Map<Resident, ResidentialWorkRequest> getResidentialWorkMap() {
        return residentialWorkMap;
    }


}
