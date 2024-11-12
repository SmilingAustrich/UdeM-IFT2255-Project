import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
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
    private int age;

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
    public Resident(String firstName, String lastName, String email, String password, String phone, String address, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.age = age;
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
    public int getDob() {
        return age;
    }


    /**
     * Permet au résident de consulter les travaux en cours ou à venir avec une option pour retourner au menu principal.
     * Le résident peut filtrer les travaux par quartier ou type de travaux.
     */
    public void consulterTravaux() {
        Scanner scanner = new Scanner(System.in);

        try {
            // Étape 1 : Récupérer les données depuis l'API
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Étape 2 : Analyser les données JSON
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonArray travaux = jsonResponse.getAsJsonObject("result").getAsJsonArray("records");

            // Étape 3 : Afficher tous les travaux
            System.out.println("Liste des travaux en cours :\n");
            for (int i = 0; i < travaux.size(); i++) {
                JsonObject travail = travaux.get(i).getAsJsonObject();
                System.out.println("ID: " + getAsStringSafe(travail.get("id")));
                System.out.println("Arrondissement: " + getAsStringSafe(travail.get("boroughid")));
                System.out.println("Motif du travail: " + getAsStringSafe(travail.get("reason_category")));
                System.out.println("Nom de l'intervenant: " + getAsStringSafe(travail.get("organizationname")));
                System.out.println("-------------------------");
            }

            // Étape 4 : Demander au résident s'il souhaite filtrer ou revenir au menu principal
            boolean continueFiltering = true;
            while (continueFiltering) {
                System.out.println("\nVoulez-vous filtrer la liste des travaux ou revenir au menu principal ?");
                System.out.println("1. Filtrer par quartier");
                System.out.println("2. Filtrer par type de travail");
                System.out.println("3. Revenir au menu principal");
                System.out.print("Choisissez une option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consommer la nouvelle ligne

                switch (choice) {
                    case 1:
                        System.out.print("Entrez l'arrondissement: ");
                        String arrondissement = scanner.nextLine();
                        System.out.println("\nTravaux filtrés par arrondissement (" + arrondissement + ") :\n");
                        for (int i = 0; i < travaux.size(); i++) {
                            JsonObject travail = travaux.get(i).getAsJsonObject();
                            if (getAsStringSafe(travail.get("boroughid")).equalsIgnoreCase(arrondissement)) {
                                System.out.println("ID: " + getAsStringSafe(travail.get("id")));
                                System.out.println("Type de travail: " + getAsStringSafe(travail.get("reason_category")));
                                System.out.println("Nom de l'intervenant: " + getAsStringSafe(travail.get("organizationname")));
                                System.out.println("-------------------------");
                            }
                        }
                        break;
                    case 2:
                        System.out.print("Entrez le motif du travail: ");
                        String motif = scanner.nextLine();
                        System.out.println("\nTravaux filtrés par motif du travail (" + motif + ") :\n");
                        for (int i = 0; i < travaux.size(); i++) {
                            JsonObject travail = travaux.get(i).getAsJsonObject();
                            if (getAsStringSafe(travail.get("reason_category")).equalsIgnoreCase(motif)) {
                                System.out.println("ID: " + getAsStringSafe(travail.get("id")));
                                System.out.println("Quartier: " + getAsStringSafe(travail.get("boroughid")));
                                System.out.println("Nom de l'intervenant: " + getAsStringSafe(travail.get("organizationname")));
                                System.out.println("-------------------------");
                            }
                        }
                        break;
                    case 3:
                        continueFiltering = false;
                        Menu.residentMainMenu(this);
                        break;
                    default:
                        System.out.println("Option invalide. Veuillez essayer à nouveau.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère les données d'un élément JSON de manière sécurisée.
     *
     * @param element L'élément JSON
     * @return La valeur de l'élément sous forme de chaîne de caractères ou "N/A" si l'élément est nul
     */
    private String getAsStringSafe(JsonElement element) {
        return element != null && !element.isJsonNull() ? element.getAsString() : "N/A";
    }


    /**
     * Permet au résident de rechercher des travaux par différents critères : titre, type de travaux ou quartier.
     */
    public void rechercherTravaux() {
        Scanner scanner = new Scanner(System.in);

        try {
            // Étape 1 : Récupérer les données depuis l'API
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Étape 2 : Analyser les données JSON
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonArray travaux = jsonResponse.getAsJsonObject("result").getAsJsonArray("records");

            // Étape 3 : Demander au résident pour les critères de recherche
            System.out.println("\nRechercher des travaux :");
            System.out.println("1. Rechercher par titre");
            System.out.println("2. Rechercher par type de travaux");
            System.out.println("3. Rechercher par quartier");
            System.out.print("Choisissez une option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            switch (choice) {
                case 1:
                    System.out.print("Entrez le titre: ");
                    String titre = scanner.nextLine();
                    System.out.println("\nTravaux trouvés par titre (" + titre + ") :\n");
                    boolean foundTitle = false;
                    for (int i = 0; i < travaux.size(); i++) {
                        JsonObject travail = travaux.get(i).getAsJsonObject();
                        if (getAsStringSafe(travail.get("id")).contains(titre)) {
                            foundTitle = true;
                            System.out.println("ID: " + getAsStringSafe(travail.get("id")));
                            System.out.println("Arrondissement: " + getAsStringSafe(travail.get("boroughid")));
                            System.out.println("Motif du travail: " + getAsStringSafe(travail.get("reason_category")));
                            System.out.println("Nom de l'intervenant: " + getAsStringSafe(travail.get("organizationname")));
                            System.out.println("-------------------------");
                        }
                    }
                    if (!foundTitle) {
                        System.out.println("Aucun travail trouvé pour le titre spécifié.");
                    }
                    System.out.print("Appuyez sur une touche pour revenir au menu principal...");
                    scanner.nextLine();
                    Menu.residentMainMenu(this);
                    break;
                case 2:
                    System.out.println("Types de travaux disponibles :");
                    System.out.println("1. Travaux routiers");
                    System.out.println("2. Travaux de gaz ou électricité");
                    System.out.println("3. Construction ou rénovation");
                    System.out.println("4. Entretien paysager");
                    System.out.println("5. Travaux liés aux transports en commun");
                    System.out.println("6. Travaux de signalisation et éclairage");
                    System.out.println("7. Travaux souterrains");
                    System.out.println("8. Travaux résidentiel");
                    System.out.println("9. Entretien urbain");
                    System.out.println("10. Entretien des réseaux de télécommunication");
                    System.out.print("Choisissez le type de travaux (1-10): ");
                    int typeChoice = scanner.nextInt();
                    scanner.nextLine(); // Consommer la nouvelle ligne
                    String typeTravaux = getTypeTravaux(typeChoice);
                    System.out.println("\nTravaux trouvés par type de travaux (" + typeTravaux + ") :\n");
                    boolean foundType = false;
                    for (int i = 0; i < travaux.size(); i++) {
                        JsonObject travail = travaux.get(i).getAsJsonObject();
                        if (getAsStringSafe(travail.get("reason_category")).equalsIgnoreCase(typeTravaux)) {
                            foundType = true;
                            System.out.println("ID: " + getAsStringSafe(travail.get("id")));
                            System.out.println("Arrondissement: " + getAsStringSafe(travail.get("boroughid")));
                            System.out.println("Nom de l'intervenant: " + getAsStringSafe(travail.get("organizationname")));
                            System.out.println("-------------------------");
                        }
                    }
                    if (!foundType) {
                        System.out.println("Aucun travail trouvé pour le type spécifié.");
                    }
                    System.out.print("Appuyez sur une touche pour revenir au menu principal...");
                    scanner.nextLine();
                    Menu.residentMainMenu(this);
                    break;
                case 3:
                    System.out.print("Entrez le quartier: ");
                    String quartier = scanner.nextLine();
                    System.out.println("\nTravaux trouvés par quartier (" + quartier + ") :\n");
                    boolean foundQuartier = false;
                    for (int i = 0; i < travaux.size(); i++) {
                        JsonObject travail = travaux.get(i).getAsJsonObject();
                        if (getAsStringSafe(travail.get("boroughid")).equalsIgnoreCase(quartier)) {
                            foundQuartier = true;
                            System.out.println("ID: " + getAsStringSafe(travail.get("id")));
                            System.out.println("Motif du travail: " + getAsStringSafe(travail.get("reason_category")));
                            System.out.println("Nom de l'intervenant: " + getAsStringSafe(travail.get("organizationname")));
                            System.out.println("-------------------------");
                        }
                    }
                    if (!foundQuartier) {
                        System.out.println("Aucun travail trouvé pour le quartier spécifié.");
                    }
                    System.out.print("Appuyez sur une touche pour revenir au menu principal...");
                    scanner.nextLine();
                    Menu.residentMainMenu(this);
                    break;
                default:
                    System.out.println("Option invalide. Veuillez essayer à nouveau.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère le type de travaux basé sur le choix de l'utilisateur.
     *
     * @param typeChoice Le choix de l'utilisateur
     * @return Le type de travaux correspondant
     */
    private String getTypeTravaux(int typeChoice) {
        switch (typeChoice) {
            case 1: return "Travaux routiers";
            case 2: return "Travaux de gaz ou électricité";
            case 3: return "Construction ou rénovation";
            case 4: return "Entretien paysager";
            case 5: return "Travaux liés aux transports en commun";
            case 6: return "Travaux de signalisation et éclairage";
            case 7: return "Travaux souterrains";
            case 8: return "Travaux résidentiel";
            case 9: return "Entretien urbain";
            case 10: return "Entretien des réseaux de télécommunication";
            default: return "";
        }
    }

    /**
     * Permet au résident de recevoir des notifications personnalisées avec une option pour retourner au menu principal.
     */
    public void recevoirNotificationsPersonalisees() {
        Scanner in = new Scanner(System.in);
        System.out.println("Vous êtes automatiquement abonné aux notifications pour des projets dans votre quartier: Montréal");
        System.out.print("Voulez-vous également recevoir des notifications pour un autre quartier ou une rue spécifique ? (oui/non). Tapez '0' pour retourner au menu principal : ");
        String choix = in.nextLine();
        if (choix.equals("0")) {
            Menu.residentMainMenu(this); // Retourne au menu principal
        }

        if (choix.equalsIgnoreCase("Oui")) {
            System.out.print("Entrez le quartier ou la rue spécifique pour laquelle vous souhaitez recevoir des notifications : ");
            String zoneSupplementaire = in.nextLine();
            if (zoneSupplementaire.equals("0")) {
                Menu.residentMainMenu(this); // Retourne au menu principal
            }
            System.out.println("Vous recevrez maintenant des notifications pour des projets dans la zone : " + zoneSupplementaire);
            AppSimulation.simulateLoading();
            AppSimulation.simulateWaitTime();
            System.out.println("Retour au menu principal.");
            Menu.residentMainMenu(this);
        } else {
            System.out.println("Aucune autre zone n'a été ajoutée. Vous continuerez à recevoir des notifications pour votre quartier.");
            AppSimulation.simulateLoading();
            AppSimulation.simulateWaitTime();
            System.out.println("Retour au menu principal.");
            Menu.residentMainMenu(this);
        }
    }

    /**
     * Permet au résident de soumettre une requête de travail avec une option pour retourner au menu principal.
     * Le résident doit fournir des détails sur le type de travaux, le quartier, et la date prévue de début des travaux.
     *
     * @param resident Le résident actuellement connecté
     */
    public void soumettreRequeteTravail(Resident resident) {
        Scanner in = new Scanner(System.in);
        System.out.println("Tapez '0' à tout moment pour retourner au menu principal.");

        System.out.print("Description des travaux (minimum 5 caractères) >: ");
        String description = in.nextLine();
        if (description.equals("0")) {
            Menu.residentMainMenu(resident); // Retourne au menu principal
            return;
        }
        while (description.length() < 5) {
            System.out.println("La description doit contenir au moins 5 caractères.");
            System.out.print("Description des travaux >: ");
            description = in.nextLine();
            if (description.equals("0")) {
                Menu.residentMainMenu(resident);
                return;
            }
        }

        System.out.print("Type de travaux (routiers, électricité, plomberie, etc.) >: ");
        String typeTravaux = in.nextLine();
        if (typeTravaux.equals("0")) {
            Menu.residentMainMenu(resident); // Retourne au menu principal
            return;
        }
        while (!typeTravaux.matches("routiers|électricité|plomberie|autre")) {
            System.out.println("Type de travaux invalide. Veuillez choisir parmi : routiers, électricité, plomberie, autre.");
            System.out.print("Type de travaux >: ");
            typeTravaux = in.nextLine();
            if (typeTravaux.equals("0")) {
                Menu.residentMainMenu(resident);
                return;
            }
        }

        System.out.print("Quartier concerné (ex : Centre-Ville, NDG, Villeray) >: ");
        String quartier = in.nextLine();
        if (quartier.equals("0")) {
            Menu.residentMainMenu(resident); // Retourne au menu principal
            return;
        }
        while (quartier.length() < 3) {
            System.out.println("Le nom du quartier doit contenir au moins 3 caractères.");
            System.out.print("Quartier concerné >: ");
            quartier = in.nextLine();
            if (quartier.equals("0")) {
                Menu.residentMainMenu(resident);
                return;
            }
        }

        System.out.print("Date de début prévue (format jj/mm/aaaa) >: ");
        String dateDebut = in.nextLine();
        if (dateDebut.equals("0")) {
            Menu.residentMainMenu(resident); // Retourne au menu principal
            return;
        }
        while (!dateDebut.matches("\\d{2}/\\d{2}/\\d{4}")) {
            System.out.println("Le format de la date est invalide. Veuillez entrer la date au format jj/mm/aaaa.");
            System.out.print("Date de début prévue >: ");
            dateDebut = in.nextLine();
            if (dateDebut.equals("0")) {
                Menu.residentMainMenu(resident);
                return;
            }
        }

        // Simulation de la soumission de la requête
        System.out.println("Soumission de la requête en cours...");
        AppSimulation.simulateLoading();

        // Affichage des détails de la requête soumise
        System.out.println("\n------------------------------");
        System.out.println("Requête soumise avec succès !");
        System.out.println("Description des travaux : " + description);
        System.out.println("Type de travaux : " + typeTravaux);
        System.out.println("Quartier concerné : " + quartier);
        System.out.println("Date de début prévue : " + dateDebut);
        System.out.println("------------------------------");

        AppSimulation.simulateWaitTime();
        System.out.println("Tapez sur n'importe quel touche pour retourner au menu principal.");
        in.nextInt();
        in.nextLine();
        System.out.println("Retour au menu principal.");
        Menu.residentMainMenu(resident);
    }


}