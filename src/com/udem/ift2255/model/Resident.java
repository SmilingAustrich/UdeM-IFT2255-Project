package com.udem.ift2255.model;

import com.udem.ift2255.ui.*;

import java.io.Serializable;
import java.util.Map;
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.udem.ift2255.database.Database;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * La classe {@code com.udem.ift2255.model.Resident} représente un utilisateur résident dans l'application Ma Ville.
 * Un résident peut consulter les travaux, signaler des problèmes, et recevoir des notifications personnalisées.
 */
public class Resident implements User, Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private int age;
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur de la classe {@code com.udem.ift2255.model.Resident}.
     *
     * @param firstName Le prénom du résident
     * @param lastName  Le nom de famille du résident
     * @param email     L'adresse email du résident
     * @param password  Le mot de passe du résident
     * @param phone     Le numéro de téléphone du résident (optionnel)
     * @param address   L'adresse résidentielle
     * @param age       La date de naissance du résident (format dd/mm/yy)
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
        return this.email;
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

                // [34mÉtape 3 : Afficher tous les travaux[0m
                System.out.println("\n[32m[INFO] Liste des travaux en cours :\n[0m");
                for (int i = 0; i < travaux.size(); i++) {
                    JsonObject travail = travaux.get(i).getAsJsonObject();
                    System.out.println("\u001b[33m[ID]:\u001b[0m " + getAsStringSafe(travail.get("id")));
                    System.out.println("\u001b[33m[Quartier]:\u001b[0m " + getAsStringSafe(travail.get("boroughid")));
                    System.out.println("\u001b[33m[Type de travail]:\u001b[0m " + getAsStringSafe(travail.get("reason_category")));
                    System.out.println("\u001b[33m[Nom de l'intervenant]:\u001b[0m " + getAsStringSafe(travail.get("organizationname")));
                    System.out.println("\u001b[35m-------------------------\u001b[0m");
                }

                // Étape 4 : Demander au résident s'il souhaite filtrer ou revenir au menu principal
                boolean continueFiltering = true;
                while (continueFiltering) {
                    System.out.println("\n[32m[OPTIONS] Voulez-vous filtrer la liste des travaux ou revenir au menu principal ?\u001b[0m");
                    System.out.println("\u001b[36m1. Filtrer par quartier\u001b[0m");
                    System.out.println("\u001b[36m2. Filtrer par type de travail\u001b[0m");
                    System.out.println("\u001b[36m3. Revenir au menu principal\u001b[0m");
                    System.out.print("\u001b[33mChoisissez une option >: \u001b[0m");

                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consommer la nouvelle ligne

                    switch (choice) {
                        case 1:
                            System.out.print("\u001b[33m Veuillez entrer le nom du quartier s'il vous plait >: \u001b[0m");
                            String Quartier = scanner.nextLine();
                            System.out.println("\n[32m[INFO] Merci. Travaux filtrés par Quartier (" + Quartier + ") :\n[0m");
                            for (int i = 0; i < travaux.size(); i++) {
                                JsonObject travail = travaux.get(i).getAsJsonObject();
                                String boroughId = getAsStringSafe(travail.get("boroughid")).toLowerCase();
                                String quartier = Quartier.toLowerCase();

                                if (boroughId.contains(quartier)) {
                                    System.out.println("\u001b[33m[ID]:\u001b[0m " + getAsStringSafe(travail.get("id")));
                                    System.out.println("\u001b[33m[Type de travail]:\u001b[0m " + getAsStringSafe(travail.get("reason_category")));
                                    System.out.println("\u001b[33m[Nom de l'intervenant]:\u001b[0m " + getAsStringSafe(travail.get("organizationname")));
                                    System.out.println("\u001b[35m-------------------------\u001b[0m");
                                }
                            }
                            System.out.println("\n[32m[INFO] Voici tous les travaux filtrés pour votre quartier:(" + Quartier + ") :\n[0m");System.out.println("\n[32m[INFO] Voici tous les travaux filtrés pour votre quartier:(" + Quartier + ") :\n[0m");
                            break;
                        case 2:
                            System.out.print("\u001b[33mEntrez le Type de travail: \u001b[0m");
                            String motif = scanner.nextLine();
                            System.out.println("\n[32m[INFO] Travaux filtrés par Type de travail (" + motif + ") :\n[0m");
                            for (int i = 0; i < travaux.size(); i++) {
                                JsonObject travail = travaux.get(i).getAsJsonObject();
                                if (getAsStringSafe(travail.get("reason_category")).equalsIgnoreCase(motif)) {
                                    System.out.println("\u001b[33m[ID]:\u001b[0m " + getAsStringSafe(travail.get("id")));
                                    System.out.println("\u001b[33m[Quartier]:\u001b[0m " + getAsStringSafe(travail.get("boroughid")));
                                    System.out.println("\u001b[33m[Nom de l'intervenant]:\u001b[0m " + getAsStringSafe(travail.get("organizationname")));
                                    System.out.println("\u001b[35m-------------------------\u001b[0m");
                                }
                            }
                            break;
                        case 3:
                            continueFiltering = false;
                            Menu.residentMainMenu(this);
                            break;
                        default:
                            System.out.println("\u001b[31m[ERREUR] Option invalide. Veuillez essayer à nouveau.\u001b[0m");
                    }
                }
            } else {
                System.out.println("\u001b[31m[ERREUR] Une erreur est survenue lors de la récupération des données. Veuillez réessayer plus tard.\u001b[0m");
            }

        } catch (Exception e) {
            System.out.println("\u001b[31m[ERREUR] Une erreur est survenue lors de la récupération des données. Veuillez réessayer plus tard.\u001b[0m");
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
            // [34mÉtape 1 : Récupérer les données depuis l'API
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
                    System.out.println("\n[32m==============================================[0m");
                    System.out.println("[32m           RECHERCHE DES TRAVAUX              [0m");
                    System.out.println("[32m==============================================[0m");
                    System.out.println("[36m1. Rechercher par titre[0m");
                    System.out.println("[36m2. Rechercher par type de travaux[0m");
                    System.out.println("[36m3. Rechercher par quartier[0m");
                    System.out.println("[36m4. Revenir au menu principal[0m");
                    System.out.println("[35m----------------------------------------------[0m");
                    System.out.print("[33mChoisissez une option: [0m");

                    int choice;
                    try {
                        choice = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("\n[31m[ERREUR] Option invalide. Veuillez entrer un nombre.[0m");
                        continue;
                    }

                    switch (choice) {
                        case 1:
                            System.out.print("\n[33mEntrez le titre: [0m");
                            String titre = scanner.nextLine();
                            System.out.println("\n[32m==============================================[0m");
                            System.out.println("[32m   TRAVAUX TROUVÉS PAR TITRE (" + titre + ")  [0m");
                            System.out.println("[32m==============================================[0m");
                            boolean foundTitle = false;
                            for (int i = 0; i < travaux.size(); i++) {
                                JsonObject travail = travaux.get(i).getAsJsonObject();
                                if (getAsStringSafe(travail.get("id")).contains(titre)) {
                                    foundTitle = true;
                                    System.out.println("\n[33m[ID]:[0m " + getAsStringSafe(travail.get("id")));
                                    System.out.println("[33m[Quartier]:[0m " + getAsStringSafe(travail.get("boroughid")));
                                    System.out.println("[33m[Type de travail]:[0m " + getAsStringSafe(travail.get("reason_category")));
                                    System.out.println("[33m[Nom de l'intervenant]:[0m " + getAsStringSafe(travail.get("organizationname")));
                                    System.out.println("[35m----------------------------------------------[0m");
                                }
                            }
                            if (!foundTitle) {
                                System.out.println("\n[31m[ERREUR] Aucun travail trouvé pour le titre spécifié.[0m");
                            }
                            System.out.print("[33mAppuyez sur une touche pour revenir au menu principal...[0m");
                            scanner.nextLine();
                            Menu.residentMainMenu(this);
                            break;
                        case 2:
                            System.out.println("\n[32mTypes de travaux disponibles :[0m");
                            System.out.println("[36m1. Travaux routiers[0m");
                            System.out.println("[36m2. Travaux de gaz ou électricité[0m");
                            System.out.println("[36m3. Construction ou rénovation[0m");
                            System.out.println("[36m4. Entretien paysager[0m");
                            System.out.println("[36m5. Travaux liés aux transports en commun[0m");
                            System.out.println("[36m6. Travaux de signalisation et éclairage[0m");
                            System.out.println("[36m7. Travaux souterrains[0m");
                            System.out.println("[36m8. Travaux résidentiel[0m");
                            System.out.println("[36m9. Entretien urbain[0m");
                            System.out.println("[36m10. Entretien des réseaux de télécommunication[0m");
                            System.out.print("[33mChoisissez le type de travaux (1-10): [0m");
                            int typeChoice;
                            try {
                                typeChoice = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("\n[31m[ERREUR] Option invalide. Veuillez entrer un nombre.[0m");
                                continue;
                            }
                            String typeTravaux = getTypeTravaux(typeChoice);
                            System.out.println("\n[32m==============================================[0m");
                            System.out.println("[32m TRAVAUX TROUVÉS PAR TYPE DE TRAVAUX (" + typeTravaux + ") [0m");
                            System.out.println("[32m==============================================[0m");
                            boolean foundType = false;
                            for (int i = 0; i < travaux.size(); i++) {
                                JsonObject travail = travaux.get(i).getAsJsonObject();
                                if (getAsStringSafe(travail.get("reason_category")).equalsIgnoreCase(typeTravaux)) {
                                    foundType = true;
                                    System.out.println("\n[33m[ID]:[0m " + getAsStringSafe(travail.get("id")));
                                    System.out.println("[33m[Quartier]:[0m " + getAsStringSafe(travail.get("boroughid")));
                                    System.out.println("[33m[Nom de l'intervenant]:[0m " + getAsStringSafe(travail.get("organizationname")));
                                    System.out.println("[35m----------------------------------------------[0m");
                                }
                            }
                            if (!foundType) {
                                System.out.println("\n[31m[ERREUR] Aucun travail trouvé pour le type spécifié.[0m");
                            }
                            System.out.print("[33mAppuyez sur une touche pour revenir au menu principal...[0m");
                            scanner.nextLine();
                            Menu.residentMainMenu(this);
                            break;
                        case 3:
                            System.out.print("\n[33mEntrez le quartier: [0m");
                            String quartier = scanner.nextLine();
                            System.out.println("\n[32m==============================================[0m");
                            System.out.println("[32m     TRAVAUX TROUVÉS PAR QUARTIER (" + quartier + ")  [0m");
                            System.out.println("[32m==============================================[0m");
                            boolean foundQuartier = false;
                            for (int i = 0; i < travaux.size(); i++) {
                                JsonObject travail = travaux.get(i).getAsJsonObject();
                                if (getAsStringSafe(travail.get("boroughid")).equalsIgnoreCase(quartier)) {
                                    foundQuartier = true;
                                    System.out.println("\n[33m[ID]:[0m " + getAsStringSafe(travail.get("id")));
                                    System.out.println("[33m[Type de travail]:[0m " + getAsStringSafe(travail.get("reason_category")));
                                    System.out.println("[33m[Nom de l'intervenant]:[0m " + getAsStringSafe(travail.get("organizationname")));
                                    System.out.println("[35m----------------------------------------------[0m");
                                }
                            }
                            if (!foundQuartier) {
                                System.out.println("\n[31m[ERREUR] Aucun travail trouvé pour le quartier spécifié.[0m");
                            }
                            System.out.print("[33mAppuyez sur une touche pour revenir au menu principal...[0m");
                            scanner.nextLine();
                            Menu.residentMainMenu(this);
                            break;
                        case 4:
                            continueSearching = false;
                            System.out.println("\n[32mRetour au menu principal...\n[0m");
                            Menu.residentMainMenu(this);
                            break;
                        default:
                            System.out.println("\n[31m[ERREUR] Option invalide. Veuillez essayer à nouveau.[0m");
                    }
                }

            } else {
                System.out.println("\n[31m[ERREUR] Une erreur est survenue lors de la recherche des travaux. Veuillez réessayer plus tard.[0m");
            }

        } catch (Exception e) {
            System.out.println("\n[31m[ERREUR] Une erreur est survenue lors de la recherche des travaux. Veuillez réessayer plus tard.[0m");
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
            
            
            System.out.println("Retour au menu principal.");
            Menu.residentMainMenu(this);
        } else {
            System.out.println("Aucune autre zone n'a été ajoutée. Vous continuerez à recevoir des notifications pour votre quartier.");
            
            
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
    public void soumettreRequeteTravail(Resident this) {
        String workTitle;
        String quartier;
        String detailedWorkDescription;
        int workType;

        Scanner in = new Scanner(System.in);

        final String RESET = "[0m";
        final String BORDER_COLOR = "[35m"; // Magenta border for a symmetric look
        final String HEADER_COLOR = "[34m"; // Blue for headers
        final String INPUT_COLOR = "[32m"; // Green for user inputs
        final String OPTION_COLOR = "[36m"; // Cyan for options
        final String CONFIRMATION_COLOR = "[33m"; // Yellow for confirmation messages

        // Introduction and instructions
        System.out.println(BORDER_COLOR + "\n===========================================" + RESET);
        System.out.println(HEADER_COLOR + "        Soumission de Requête de Travaux       " + RESET);
        System.out.println(BORDER_COLOR + "===========================================\n" + RESET);

        // Collecting work title
        System.out.print(INPUT_COLOR + "Titre des travaux >: " + RESET);
        workTitle = in.nextLine();

        // Collecting detailed work description
        System.out.print(INPUT_COLOR + "Description détaillée des travaux >: " + RESET);
        detailedWorkDescription = in.nextLine();

        // Collecting detailed work description
        System.out.print(INPUT_COLOR + "Quartier >: " + RESET);
        quartier = in.nextLine();

        // Selecting work type
        System.out.println(BORDER_COLOR + "\n-------------------------------------------" + RESET);
        System.out.println(HEADER_COLOR + "Types de travaux disponibles: " + RESET);
        System.out.println(OPTION_COLOR + "1. Travaux routiers" + RESET);
        System.out.println(OPTION_COLOR + "2. Travaux de gaz ou électricité" + RESET);
        System.out.println(OPTION_COLOR + "3. Construction ou rénovation" + RESET);
        System.out.println(OPTION_COLOR + "4. Entretien paysager" + RESET);
        System.out.println(OPTION_COLOR + "5. Travaux liés aux transports en commun" + RESET);
        System.out.println(OPTION_COLOR + "6. Travaux de signalisation et éclairage" + RESET);
        System.out.println(OPTION_COLOR + "7. Travaux souterrains" + RESET);
        System.out.println(OPTION_COLOR + "8. Travaux résidentiels" + RESET);
        System.out.println(OPTION_COLOR + "9. Entretien urbain" + RESET);
        System.out.println(OPTION_COLOR + "10. Entretien des réseaux de télécommunication" + RESET);
        System.out.println(BORDER_COLOR + "-------------------------------------------\n" + RESET);
        System.out.print(INPUT_COLOR + "Veuillez entrer le numéro correspondant au type de travaux >: " + RESET);
        workType = in.nextInt();
        in.nextLine(); // Consume newline

        String workTypeString;
        switch (workType) {
            case 1:
                workTypeString = "Travaux routiers";
                break;
            case 2:
                workTypeString = "Travaux de gaz ou électricité";
                break;
            case 3:
                workTypeString = "Construction ou rénovation";
                break;
            case 4:
                workTypeString = "Entretien paysager";
                break;
            case 5:
                workTypeString = "Travaux liés aux transports en commun";
                break;
            case 6:
                workTypeString = "Travaux de signalisation et éclairage";
                break;
            case 7:
                workTypeString = "Travaux souterrains";
                break;
            case 8:
                workTypeString = "Travaux résidentiels";
                break;
            case 9:
                workTypeString = "Entretien urbain";
                break;
            case 10:
                workTypeString = "Entretien des réseaux de télécommunication";
                break;
            default:
                workTypeString = "Inconnu";
        }

        // Collecting wished start date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();
        LocalDate workStartDate = null;
        String workWishedStartDate;
        boolean validDate = false;

        while (!validDate) {
            System.out.print(INPUT_COLOR + "Date prévue de début des travaux (format: JJ/MM/AAAA): " + RESET);
            workWishedStartDate = in.nextLine();
            try {
                workStartDate = LocalDate.parse(workWishedStartDate, formatter);
                if (workStartDate.isBefore(today)) {
                    System.out.println(RESET + "\n" + CONFIRMATION_COLOR + "La date ne peut pas être dans le passé. Veuillez entrer une date valide." + RESET);
                } else {
                    validDate = true;
                }
            } catch (DateTimeParseException e) {
                System.out.println(RESET + "\n" + CONFIRMATION_COLOR + "Format de date invalide. Veuillez respecter le format JJ/MM/AAAA." + RESET);
            }
        }

        // Display a summary of the work request
        System.out.println(BORDER_COLOR + "\n===========================================" + RESET);
        System.out.println(HEADER_COLOR + "             Résumé de la requête             " + RESET);
        System.out.println(BORDER_COLOR + "===========================================\n" + RESET);
        System.out.println(HEADER_COLOR + "Titre: " + RESET + workTitle);
        System.out.println(HEADER_COLOR + "Description: " + RESET + detailedWorkDescription);
        System.out.println(HEADER_COLOR + "Quartier: " + RESET + quartier);
        System.out.println(HEADER_COLOR + "Type: " + RESET + workTypeString);
        System.out.println(HEADER_COLOR + "Date prévue de début: " + RESET + workStartDate.format(formatter));
        System.out.println(BORDER_COLOR + "===========================================\n" + RESET);

        // Inform the resident that the request has been submitted successfully
        System.out.println(CONFIRMATION_COLOR + "Votre requête a été soumise avec succès." + RESET);
        System.out.println(HEADER_COLOR + "Appuyez sur Entrée pour retourner au menu principal." + RESET);
        in.nextLine();
        creerRequete(workTitle,detailedWorkDescription,workTypeString,workStartDate, quartier);
        Menu.residentMainMenu(this);
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
                            System.out.println("\n[32m[INFO] " + this.getFirstName() +", voici la liste des entraves en cours. Nous sommes désolé pour toute gène occasionnée lors des travaux. [0m");
                            System.out.println("\n[32m Retour au menu de consultation des entraves. [0m");

                            break;
                        case 2:
                            System.out.print("[1;35mEntrez l'identifiant du travail: [0m");
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

    public void creerRequete(String workTitle, String detailedWorkDescription, String workType, LocalDate workWishedStartDate, String quartier){
        ResidentialWorkRequest requete = new ResidentialWorkRequest(this, workTitle, detailedWorkDescription, workType, workWishedStartDate, quartier);
        Database.getResidentialWorkMap().put(this,requete);
        Database.saveData(); // update
//        Map<com.udem.ift2255.model.Resident, com.udem.ift2255.model.ResidentialWorkRequest > lol = com.udem.ift2255.database.Database.getRequeteTravailMap();
    }

    public void fermerRequete(ResidentialWorkRequest requete){
        if (!requete.isWorkAvailable()){
            System.out.println("La requête est fermée par " + this.getFirstName());
            Database.getResidentialWorkMap().remove(this.getEmail());
        }
    }

    public void suivreRequetesResidentielles() {
        Scanner scanner = new Scanner(System.in);
        Map<Resident, ResidentialWorkRequest> requetes = Database.getResidentialWorkMap();

        // Check if there is an active work request for this resident
        if (!requetes.containsKey(this)) {
            System.out.println("\u001B[31m[INFO] Vous n'avez aucune requête en cours.\u001B[0m");
            Menu.residentMainMenu(this);
        }

        // Display the current work request
        ResidentialWorkRequest requete = requetes.get(this);
        System.out.println("\u001B[34m[INFO] Votre requête actuelle :\u001B[0m");
        System.out.println("\u001B[33mTitre :\u001B[0m " + requete.getTitle());
        System.out.println("\u001B[33mDescription :\u001B[0m " + requete.getDescription());
        System.out.println("\u001B[33mType de travaux :\u001B[0m " + requete.getWorkType());
        System.out.println("\u001B[33mQuartier :\u001B[0m " + requete.getNeighbourhood());
        System.out.println("\u001B[33mDate de début prévue :\u001B[0m " + requete.getStartDate());
        System.out.println("\n\u001B[32m[OPTIONS] Que souhaitez-vous faire avec cette requête ?\u001B[0m");
        System.out.println("\u001B[36m1. Fermer la requête\u001B[0m");
        System.out.println("\u001B[36m2. Retourner au menu principal\u001B[0m");
        System.out.print("\u001B[33mChoisissez une option >: \u001B[0m");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                fermerRequete(requete);
                break;
            case 2:
                Menu.residentMainMenu(this);
                break;
            default:
                System.out.println("\u001B[31m[ERREUR] Option invalide. Veuillez essayer à nouveau.\u001B[0m");
                Menu.residentMainMenu(this);
        }
    }



}