import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;


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
            URL url = new URL("https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Étape 2 : Analyser les données JSON
                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                JsonArray travaux = jsonResponse.getAsJsonObject("result").getAsJsonArray("records");

                // Étape 3 : Afficher tous les travaux
                System.out.println("\n[1;34m➞ Liste des travaux en cours :\n[0m");
                for (int i = 0; i < travaux.size(); i++) {
                    JsonObject travail = travaux.get(i).getAsJsonObject();
                    System.out.println("\u001B[1;33mID: \u001B[0m" + getAsStringSafe(travail.get("id")));
                    System.out.println("\u001B[1;33mArrondissement: \u001B[0m" + getAsStringSafe(travail.get("boroughid")));
                    System.out.println("\u001B[1;33mMotif du travail: \u001B[0m" + getAsStringSafe(travail.get("reason_category")));
                    System.out.println("\u001B[1;33mNom de l'intervenant: \u001B[0m" + getAsStringSafe(travail.get("organizationname")));
                    System.out.println("\u001B[1;34m-------------------------\u001B[0m");
                }

                // Étape 4 : Demander au résident s'il souhaite filtrer ou revenir au menu principal
                boolean continueFiltering = true;
                while (continueFiltering) {
                    System.out.println("\n[1;32mVoulez-vous filtrer la liste des travaux ou revenir au menu principal ?\u001B[0m");
                    System.out.println("\u001B[1;32m1. Filtrer par quartier\u001B[0m");
                    System.out.println("\u001B[1;32m2. Filtrer par type de travail\u001B[0m");
                    System.out.println("\u001B[1;32m3. Revenir au menu principal\u001B[0m");
                    System.out.print("\u001B[1;36mChoisissez une option: \u001B[0m");

                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consommer la nouvelle ligne

                    switch (choice) {
                        case 1:
                            System.out.print("\u001B[1;35mEntrez l'arrondissement: \u001B[0m");
                            String arrondissement = scanner.nextLine();
                            System.out.println("\n[1;34m➞ Travaux filtrés par arrondissement (" + arrondissement + ") :\n[0m");
                            for (int i = 0; i < travaux.size(); i++) {
                                JsonObject travail = travaux.get(i).getAsJsonObject();
                                if (getAsStringSafe(travail.get("boroughid")).equalsIgnoreCase(arrondissement)) {
                                    System.out.println("\u001B[1;33mID: \u001B[0m" + getAsStringSafe(travail.get("id")));
                                    System.out.println("\u001B[1;33mType de travail: \u001B[0m" + getAsStringSafe(travail.get("reason_category")));
                                    System.out.println("\u001B[1;33mNom de l'intervenant: \u001B[0m" + getAsStringSafe(travail.get("organizationname")));
                                    System.out.println("\u001B[1;34m-------------------------\u001B[0m");
                                }
                            }
                            break;
                        case 2:
                            System.out.print("\u001B[1;35mEntrez le motif du travail: \u001B[0m");
                            String motif = scanner.nextLine();
                            System.out.println("\n[1;34m➞ Travaux filtrés par motif du travail (" + motif + ") :\n[0m");
                            for (int i = 0; i < travaux.size(); i++) {
                                JsonObject travail = travaux.get(i).getAsJsonObject();
                                if (getAsStringSafe(travail.get("reason_category")).equalsIgnoreCase(motif)) {
                                    System.out.println("\u001B[1;33mID: \u001B[0m" + getAsStringSafe(travail.get("id")));
                                    System.out.println("\u001B[1;33mQuartier: \u001B[0m" + getAsStringSafe(travail.get("boroughid")));
                                    System.out.println("\u001B[1;33mNom de l'intervenant: \u001B[0m" + getAsStringSafe(travail.get("organizationname")));
                                    System.out.println("\u001B[1;34m-------------------------\u001B[0m");
                                }
                            }
                            break;
                        case 3:
                            continueFiltering = false;
                            Menu.residentMainMenu(this);
                            break;
                        default:
                            System.out.println("\u001B[1;31mOption invalide. Veuillez essayer à nouveau.\u001B[0m");
                    }
                }
            } else {
                System.out.println("\u001B[1;31mUne erreur est survenue lors de la récupération des données. Veuillez réessayer plus tard.\u001B[0m");
            }

        } catch (Exception e) {
            System.out.println("\u001B[1;31mUne erreur est survenue lors de la récupération des données. Veuillez réessayer plus tard.\u001B[0m");
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
            URL url = new URL("https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Étape 2 : Analyser les données JSON
                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                JsonArray travaux = jsonResponse.getAsJsonObject("result").getAsJsonArray("records");

                // Étape 3 : Demander au résident pour les critères de recherche
                boolean continueSearching = true;
                while (continueSearching) {
                    System.out.println("\n==============================================");
                    System.out.println("           RECHERCHE DES TRAVAUX              ");
                    System.out.println("==============================================");
                    System.out.println("1. Rechercher par titre");
                    System.out.println("2. Rechercher par type de travaux");
                    System.out.println("3. Rechercher par quartier");
                    System.out.println("4. Revenir au menu principal");
                    System.out.println("----------------------------------------------");
                    System.out.print("Choisissez une option: ");

                    int choice;
                    try {
                        choice = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("\nOption invalide. Veuillez entrer un nombre.");
                        continue;
                    }

                    switch (choice) {
                        case 1:
                            System.out.print("\nEntrez le titre: ");
                            String titre = scanner.nextLine();
                            System.out.println("\n==============================================");
                            System.out.println("   TRAVAUX TROUVÉS PAR TITRE (" + titre + ")  ");
                            System.out.println("==============================================");
                            boolean foundTitle = false;
                            for (int i = 0; i < travaux.size(); i++) {
                                JsonObject travail = travaux.get(i).getAsJsonObject();
                                if (getAsStringSafe(travail.get("id")).contains(titre)) {
                                    foundTitle = true;
                                    System.out.println("\nID: " + getAsStringSafe(travail.get("id")));
                                    System.out.println("Arrondissement: " + getAsStringSafe(travail.get("boroughid")));
                                    System.out.println("Motif du travail: " + getAsStringSafe(travail.get("reason_category")));
                                    System.out.println("Nom de l'intervenant: " + getAsStringSafe(travail.get("organizationname")));
                                    System.out.println("----------------------------------------------");
                                }
                            }
                            if (!foundTitle) {
                                System.out.println("\nAucun travail trouvé pour le titre spécifié.");
                            }
                            System.out.print("Appuyez sur une touche pour revenir au menu principal...");
                            scanner.nextLine();
                            Menu.residentMainMenu(this);
                            break;
                        case 2:
                            System.out.println("\nTypes de travaux disponibles :");
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
                            int typeChoice;
                            try {
                                typeChoice = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("\nOption invalide. Veuillez entrer un nombre.");
                                continue;
                            }
                            String typeTravaux = getTypeTravaux(typeChoice);
                            System.out.println("\n==============================================");
                            System.out.println(" TRAVAUX TROUVÉS PAR TYPE DE TRAVAUX (" + typeTravaux + ") ");
                            System.out.println("==============================================");
                            boolean foundType = false;
                            for (int i = 0; i < travaux.size(); i++) {
                                JsonObject travail = travaux.get(i).getAsJsonObject();
                                if (getAsStringSafe(travail.get("reason_category")).equalsIgnoreCase(typeTravaux)) {
                                    foundType = true;
                                    System.out.println("\nID: " + getAsStringSafe(travail.get("id")));
                                    System.out.println("Arrondissement: " + getAsStringSafe(travail.get("boroughid")));
                                    System.out.println("Nom de l'intervenant: " + getAsStringSafe(travail.get("organizationname")));
                                    System.out.println("----------------------------------------------");
                                }
                            }
                            if (!foundType) {
                                System.out.println("\nAucun travail trouvé pour le type spécifié.");
                            }
                            System.out.print("Appuyez sur une touche pour revenir au menu principal...");
                            scanner.nextLine();
                            Menu.residentMainMenu(this);
                            break;
                        case 3:
                            System.out.print("\nEntrez le quartier: ");
                            String quartier = scanner.nextLine();
                            System.out.println("\n==============================================");
                            System.out.println("     TRAVAUX TROUVÉS PAR QUARTIER (" + quartier + ")  ");
                            System.out.println("==============================================");
                            boolean foundQuartier = false;
                            for (int i = 0; i < travaux.size(); i++) {
                                JsonObject travail = travaux.get(i).getAsJsonObject();
                                if (getAsStringSafe(travail.get("boroughid")).equalsIgnoreCase(quartier)) {
                                    foundQuartier = true;
                                    System.out.println("\nID: " + getAsStringSafe(travail.get("id")));
                                    System.out.println("Motif du travail: " + getAsStringSafe(travail.get("reason_category")));
                                    System.out.println("Nom de l'intervenant: " + getAsStringSafe(travail.get("organizationname")));
                                    System.out.println("----------------------------------------------");
                                }
                            }
                            if (!foundQuartier) {
                                System.out.println("\nAucun travail trouvé pour le quartier spécifié.");
                            }
                            System.out.print("Appuyez sur une touche pour revenir au menu principal...");
                            scanner.nextLine();
                            Menu.residentMainMenu(this);
                            break;
                        case 4:
                            continueSearching = false;
                            System.out.println("\nRetour au menu principal...\n");
                            Menu.residentMainMenu(this);
                            break;
                        default:
                            System.out.println("\nOption invalide. Veuillez essayer à nouveau.");
                    }
                }

            } else {
                System.out.println("\nUne erreur est survenue lors de la recherche des travaux. Veuillez réessayer plus tard.");
            }

        } catch (Exception e) {
            System.out.println("\nUne erreur est survenue lors de la recherche des travaux. Veuillez réessayer plus tard.");
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

    /**
     * Permet au résident de consulter les entraves sur le réseau routier causées par les travaux en cours.
     * Le résident peut rechercher les entraves associées à un travail spécifique, par rue, ou voir toutes les entraves.
     */
    public void consulterEntraves() {
        Scanner scanner = new Scanner(System.in);

        try {
            // Étape 1 : Récupérer les données depuis l'API des entraves
            URL url = new URL("https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Étape 2 : Analyser les données JSON
                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                JsonArray entraves = jsonResponse.getAsJsonObject("result").getAsJsonArray("records");

                // Étape 3 : Demander au résident pour les critères de recherche
                boolean continueSearching = true;
                while (continueSearching) {
                    System.out.println("\n[1;34m==============================================[0m");
                    System.out.println("[1;36m           CONSULTATION DES ENTRAVES           [0m");
                    System.out.println("\u001B[1;34m==============================================[0m");
                    System.out.println("\u001B[1;32m1. Voir toutes les entraves[0m");
                    System.out.println("\u001B[1;32m2. Rechercher par identifiant de travail[0m");
                    System.out.println("\u001B[1;32m3. Rechercher par nom de rue[0m");
                    System.out.println("\u001B[1;31m4. Revenir au menu principal[0m");
                    System.out.println("\u001B[1;34m----------------------------------------------[0m");
                    System.out.print("\u001B[1;33mChoisissez une option: [0m");

                    int choice;
                    try {
                        choice = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("\u001B[1;31m\nOption invalide. Veuillez entrer un nombre.[0m");
                        continue;
                    }

                    switch (choice) {
                        case 1:
                            System.out.println("\n[1;34m==============================================[0m");
                            System.out.println("[1;36m           LISTE DE TOUTES LES ENTRAVES        [0m");
                            System.out.println("\u001B[1;34m==============================================[0m");
                            for (int i = 0; i < entraves.size(); i++) {
                                JsonObject entrave = entraves.get(i).getAsJsonObject();
                                System.out.println("\n[1;33mID du travail : [0m" + getAsStringSafe(entrave.get("id_request")));
                                System.out.println("\u001B[1;33mNom de la rue : [0m" + getAsStringSafe(entrave.get("shortname")));
                                System.out.println("\u001B[1;33mImpact sur la rue : [0m" + getAsStringSafe(entrave.get("streetimpacttype")));
                                System.out.println("\u001B[1;34m----------------------------------------------[0m");
                            }
                            break;
                        case 2:
                            System.out.print("\n[1;35mEntrez l'identifiant du travail: [0m");
                            String idTravail = scanner.nextLine();
                            System.out.println("\n[1;34m==============================================[0m");
                            System.out.println("[1;36m  ENTRAVES ASSOCIÉES AU TRAVAIL (ID: " + idTravail + ") [0m");
                            System.out.println("\u001B[1;34m==============================================[0m");
                            boolean foundEntraveById = false;
                            for (int i = 0; i < entraves.size(); i++) {
                                JsonObject entrave = entraves.get(i).getAsJsonObject();
                                if (getAsStringSafe(entrave.get("id_request")).equalsIgnoreCase(idTravail)) {
                                    foundEntraveById = true;
                                    System.out.println("\n[1;33mID du travail : [0m" + getAsStringSafe(entrave.get("id_request")));
                                    System.out.println("\u001B[1;33mNom de la rue : [0m" + getAsStringSafe(entrave.get("shortname")));
                                    System.out.println("\u001B[1;33mImpact sur la rue : [0m" + getAsStringSafe(entrave.get("streetimpacttype")));
                                    System.out.println("\u001B[1;34m----------------------------------------------[0m");
                                }
                            }
                            if (!foundEntraveById) {
                                System.out.println("\u001B[1;31m\nAucune entrave trouvée pour l'identifiant du travail spécifié.[0m");
                            }
                            break;
                        case 3:
                            System.out.print("\n[1;35mEntrez le nom de la rue: [0m");
                            String nomRue = scanner.nextLine();
                            System.out.println("\n[1;34m==============================================[0m");
                            System.out.println("[1;36m       ENTRAVES ASSOCIÉES À LA RUE (" + nomRue + ") [0m");
                            System.out.println("\u001B[1;34m==============================================[0m");
                            boolean foundEntraveByStreet = false;
                            for (int i = 0; i < entraves.size(); i++) {
                                JsonObject entrave = entraves.get(i).getAsJsonObject();
                                if (getAsStringSafe(entrave.get("shortname")).equalsIgnoreCase(nomRue)) {
                                    foundEntraveByStreet = true;
                                    System.out.println("\n[1;33mID du travail : [0m" + getAsStringSafe(entrave.get("id_request")));
                                    System.out.println("\u001B[1;33mImpact sur la rue : [0m" + getAsStringSafe(entrave.get("streetimpacttype")));
                                    System.out.println("\u001B[1;34m----------------------------------------------[0m");
                                }
                            }
                            if (!foundEntraveByStreet) {
                                System.out.println("\u001B[1;31m\nAucune entrave trouvée pour la rue spécifiée.[0m");
                            }
                            break;
                        case 4:
                            continueSearching = false;
                            System.out.println("\u001B[1;36m\nRetour au menu principal...\u001B[0m\n");
                            Menu.residentMainMenu(this);
                            break;
                        default:
                            System.out.println("\u001B[1;31m\nOption invalide. Veuillez essayer à nouveau.[0m");
                    }
                }

            } else {
                System.out.println("\u001B[1;31m\nUne erreur est survenue lors de la consultation des entraves. Veuillez réessayer plus tard.[0m");
            }

        } catch (Exception e) {
            System.out.println("\u001B[1;31m\nUne erreur est survenue lors de la consultation des entraves. Veuillez réessayer plus tard.[0m");
            e.printStackTrace();
        }
    }


}