package com.maville.model;

import com.maville.database.Database;
import com.maville.ui.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * La classe {@code Intervenant} représente un utilisateur de type intervenant dans l'application Ma Ville.
 * Un intervenant est un professionnel qui peut soumettre des projets de travaux et consulter des requêtes.
 */
public class Intervenant implements User, Serializable {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String cityIdCode;
    private final int entrepreneurType;
    private static final long serialVersionUID = 1L;
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
     * Permet à un intervenant de consulter la liste des requêtes de travail et de soumettre sa candidature.
     * Les requêtes peuvent être filtrées par type, quartier et par date de début.
     *
     * @param requetes Map des résidents et leurs requêtes de travail
     */
    public void consulterListeRequetesTravaux(Map<Resident, ResidentialWorkRequest> requetes) {
        Scanner in = new Scanner(System.in);

        final String RESET = "[0m";
        final String BORDER_COLOR = "[35m"; // Magenta border for a symmetric look
        final String HEADER_COLOR = "[34m"; // Blue for headers
        final String INPUT_COLOR = "[32m"; // Green for user inputs
        final String OPTION_COLOR = "[36m"; // Cyan for options

        // Fetching all requests from the database
        Map<Resident, ResidentialWorkRequest> allRequests = Database.getResidentialWorkMap();

        // Filtering options
        System.out.println(BORDER_COLOR + "\n===========================================" + RESET);
        System.out.println(HEADER_COLOR + "      Filtrer les requêtes de travail       " + RESET);
        System.out.println(BORDER_COLOR + "===========================================\n" + RESET);
        System.out.println(OPTION_COLOR + "1. Filtrer par type de travaux" + RESET);
        System.out.println(OPTION_COLOR + "2. Filtrer par quartier" + RESET);
        System.out.println(OPTION_COLOR + "3. Filtrer par date de début" + RESET);
        System.out.println(OPTION_COLOR + "4. Afficher toutes les requêtes\n" + RESET);
        System.out.print(INPUT_COLOR + "Veuillez choisir une option >: " + RESET);
        int filterOption = in.nextInt();
        in.nextLine(); // Consume newline

        List<ResidentialWorkRequest> filteredRequests = allRequests.values().stream().collect(Collectors.toList());

        switch (filterOption) {
            case 1:
                System.out.print(INPUT_COLOR + "Veuillez entrer le type de travaux: " + RESET);
                String typeTravaux = in.nextLine();
                filteredRequests = filteredRequests.stream()
                        .filter(req -> req.getWorkType().equalsIgnoreCase(typeTravaux))
                        .collect(Collectors.toList());
                break;
            case 2:
                System.out.print(INPUT_COLOR + "Veuillez entrer le quartier: " + RESET);
                String quartier = in.nextLine();
                filteredRequests = filteredRequests.stream()
                        .filter(req -> req.getNeighbourhood().equalsIgnoreCase(quartier))
                        .collect(Collectors.toList());
                break;
            case 3:
                System.out.print(INPUT_COLOR + "Veuillez entrer la date de début (format: JJ/MM/AAAA): " + RESET);
                String dateDebutStr = in.nextLine();
                try {
                    LocalDate dateDebut = LocalDate.parse(dateDebutStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    filteredRequests = filteredRequests.stream()
                            .filter(req -> !req.getStartDate().isBefore(dateDebut))
                            .collect(Collectors.toList());
                } catch (DateTimeParseException e) {
                    System.out.println(RESET + "\n" + OPTION_COLOR + "Format de date invalide. Aucune requête filtrée." + RESET);
                }
                break;
            case 4:
                // No filtering needed
                break;
            default:
                System.out.println(RESET + "\n" + OPTION_COLOR + "Option invalide. Affichage de toutes les requêtes." + RESET);
        }

        // Display filtered requests
        System.out.println(BORDER_COLOR + "\n===========================================" + RESET);
        System.out.println(HEADER_COLOR + "        Liste des Requêtes de Travaux        " + RESET);
        System.out.println(BORDER_COLOR + "===========================================\n" + RESET);
        for (ResidentialWorkRequest req : filteredRequests) {
            System.out.println(HEADER_COLOR + "Titre: " + RESET + req.getTitle());
            System.out.println(HEADER_COLOR + "Type: " + RESET + req.getWorkType());
            System.out.println(HEADER_COLOR + "Quartier: " + RESET + req.getNeighbourhood());
            System.out.println(HEADER_COLOR + "Date de début: " + RESET + req.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println(BORDER_COLOR + "-------------------------------------------" + RESET);
        }

        // Allowing intervenant to submit candidature
        System.out.print(INPUT_COLOR + "Souhaitez-vous soumettre votre candidature pour une des requêtes ci-dessus? (1. Oui / 2. Non): " + RESET);
        int reponse = in.nextInt();
        in.nextLine(); // Consume newline
        if (reponse == 1) {
            System.out.print(INPUT_COLOR + "Veuillez entrer le titre de la requête: " + RESET);
            String titreRequete = in.nextLine();
            ResidentialWorkRequest requeteChoisie = filteredRequests.stream()
                    .filter(req -> req.getTitle().equalsIgnoreCase(titreRequete))
                    .findFirst()
                    .orElse(null);

            if (requeteChoisie != null) {
                System.out.print(INPUT_COLOR + "Veuillez entrer votre message pour la candidature: " + RESET);
                String message = in.nextLine();
                requeteChoisie.ajouterCandidature(this, message);
                System.out.println(OPTION_COLOR + "Votre candidature a été soumise avec succès." + RESET);
            } else {
                System.out.println(RESET + "\n" + OPTION_COLOR + "Requête non trouvée. Candidature non soumise." + RESET);
            }
        }
        System.out.println(OPTION_COLOR + "Retour au menu principal.\n" + RESET);
        Menu.intervenantMainMenu(this);

    }



    /**
     * Permet à l'intervenant de proposer une plage horaire pour les travaux avec une option pour retourner au menu principal.
     */
    public void proposerPlageHoraire() {
        Scanner in = new Scanner(System.in);
        System.out.println("Veuillez proposer une plage horaire pour les travaux. Tapez '0' à tout moment pour retourner au menu principal.");
        System.out.print("Date (format JJ/MM/AAAA) >: ");
        String date = in.nextLine();
        if (date.equals("0")) {
            Menu.intervenantMainMenu(this); // Retourne au menu principal
        }

        System.out.print("Heure de début (format HH:MM) >: ");
        String heureDebut = in.nextLine();
        if (heureDebut.equals("0")) {
            Menu.intervenantMainMenu(this); // Retourne au menu principal
        }

        System.out.print("Heure de fin (format HH:MM) >: ");
        String heureFin = in.nextLine();
        if (heureFin.equals("0")) {
            Menu.intervenantMainMenu(this); // Retourne au menu principal
        }

        System.out.println("Plage horaire proposée : " + date + " de " + heureDebut + " à " + heureFin);

        
        

        System.out.println("Retour au menu principal");
        Menu.intervenantMainMenu(this);
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = null;
        int attempts = 0;
        final int maxAttempts = 3;

        while (attempts < maxAttempts) {
            System.out.println("Veuillez entrer une date (respectez le format suivant : jj/MM/aaaa) : ");
            String input = in.nextLine();

            try {
                date = LocalDate.parse(input, formatter);
                break;
            } catch (DateTimeParseException e) {
                attempts++;
                System.out.println("Format de date incorrect. Veuillez réessayer.");
                if (attempts == maxAttempts){
                    System.out.println("Désolé, le nombre maximum de tentative a été dépassé.");
                }
            }
        }

        System.out.print("Titre du projet >: ");
        String titre = in.nextLine();
        if (titre.equals("0")) {
            Menu.intervenantMainMenu(this);
            return;
        }

        System.out.print("Description du projet >: ");
        String description = in.nextLine();
        if (description.equals("0")) {
            Menu.intervenantMainMenu(this);
            return;
        }

        System.out.print("Type de travaux (routiers, électricité, etc.) >: ");
        String typeTravaux = in.nextLine();
        if (typeTravaux.equals("0")) {
            Menu.intervenantMainMenu(this);
            return;
        }

        System.out.println("Quartiers affectés disponibles : " + quartiers);
        System.out.print("Entrez le quartier affecté (sélectionnez un seul) >: ");
        String quartier = in.nextLine();
        if (quartier.equals("0")) {
            Menu.intervenantMainMenu(this);
            return;
        }
        if (!quartiers.contains(quartier)) {
            System.out.println("Quartier invalide. Retour au menu principal.");
            Menu.intervenantMainMenu(this);
            return;
        }

        System.out.println("Rues affectées disponibles : " + rues);
        System.out.print("Entrez la rue affectée (sélectionnez une rue) >: ");
        String rue = in.nextLine();
        if (rue.equals("0")) {
            Menu.intervenantMainMenu(this);
            return;
        }
        if (!rues.contains(rue)) {
            System.out.println("Rue invalide. Retour au menu principal.");
            Menu.intervenantMainMenu(this);
            return;
        }

        System.out.print("Date de début (format jj/mm/aaaa) >: ");
        String dateDebut = in.nextLine();
        if (dateDebut.equals("0")) {
            Menu.intervenantMainMenu(this);
            return;
        }

        System.out.print("Date de fin (format jj/mm/aaaa) >: ");
        String dateFin = in.nextLine();
        if (dateFin.equals("0")) {
            Menu.intervenantMainMenu(this);
            return;
        }

        System.out.print("Horaire des travaux (ex: 8h-12h) >: ");
        String horaire = in.nextLine();
        if (horaire.equals("0")) {
            Menu.intervenantMainMenu(this);
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
                Menu.intervenantMainMenu(this);
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
        Menu.intervenantMainMenu(this);
    }


    public void soumettreCandidature(ResidentialWorkRequest requete, String message) {
        if (requete.isWorkAvailable()) {
            requete.ajouterCandidature(this, message);
        } else {
            System.out.println("La requête n'est plus disponible.");
        }
    }

    public void retirerCandidature(ResidentialWorkRequest requete) {
        if (requete.getCandidatures().containsKey(this)) {
            requete.getCandidatures().remove(this); // Supprime la candidature
            System.out.println("Candidature retirée par l'intervenant " + this.firstName);
        } else {
            System.out.println("Aucune candidature à retirer pour cet intervenant.");
        }
    }


    public void confirmerCandidature(ResidentialWorkRequest requete) {
        if (!requete.isWorkAvailable()) {
            requete.rendreDisponible();
            System.out.println("Soumission de la candidature confirmée par l'intervenant " + this.firstName);
        } else {
            System.out.println("La requête est déjà disponible.");
        }
    }




}