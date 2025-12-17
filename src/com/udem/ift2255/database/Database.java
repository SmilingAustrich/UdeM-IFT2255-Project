package com.udem.ift2255.database;

import com.udem.ift2255.model.*;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public abstract class Database {

    private static final String DIRECTORY_PATH = Paths.get(System.getProperty("user.home"), "Desktop", "Ma Ville").toString();

    // Stockage des résidents et des intervenants dans des maps, avec l'email comme clé.
    private static Map<String, Resident> residentMap = new HashMap<>();
    private static Map<String, Intervenant> intervenantMap = new HashMap<>();
    private static Map<Resident, ResidentialWorkRequest> residentialWorkMap = new HashMap<>();

    /**
     * bloc statique pour initialiser les utilisateurs de test.
     */
   static {
        // Création de résidents de test avec des mots de passe
        Resident testResident = new Resident("John", "Smiths", "resident@prototype.com", "password123", "514-555-1234", "123 Rue de la Paix, Montréal", 25);

        Resident resident0 = new Resident("John", "Doe", "test", "test", "514-555-0001", "1 Rue de la Paix, Montréal", 25);
        Resident resident1 = new Resident("John", "Doe", "john.doe@example.com", "password1", "514-555-0001", "1 Rue de la Paix, Montréal", 25);
        Resident resident2 = new Resident("Jane", "Smith", "jane.smith@example.com", "password2", "514-555-0002", "2 Rue de la Paix, Montréal", 30);
        Resident resident3 = new Resident("Alice", "Brown", "alice.brown@example.com", "password3", "514-555-0003", "3 Rue de la Paix, Montréal", 28);
        Resident resident4 = new Resident("Robert", "Miller", "robert.miller@example.com", "password4", "514-555-0004", "4 Rue de la Paix, Montréal", 35);
        Resident resident5 = new Resident("Emily", "Davis", "emily.davis@example.com", "password5", "514-555-0005", "5 Rue de la Paix, Montréal", 27);
        Resident resident6 = new Resident("David", "Wilson", "david.wilson@example.com", "password6", "514-555-0006", "6 Rue de la Paix, Montréal", 32);

        residentMap.put(resident0.getEmail(), resident0);
        residentMap.put(resident1.getEmail(), resident1);
        residentMap.put(resident2.getEmail(), resident2);
        residentMap.put(resident3.getEmail(), resident3);
        residentMap.put(resident4.getEmail(), resident4);
        residentMap.put(resident5.getEmail(), resident5);
        residentMap.put(resident6.getEmail(), resident6);
        residentMap.put(testResident.getEmail(), testResident);

        // Création d'intervenants de test avec des mots de passe
        Intervenant testIntervenant = new Intervenant("Jane", "Doe", "intervenant@prototype.com", "password456", "12345678", 1);

        Intervenant intervenant0 = new Intervenant("Mark", "Taylor", "test", "test", "12345678", 1);
        Intervenant intervenant1 = new Intervenant("Mark", "Taylor", "mark.taylor@example.com", "test1", "12345678", 1);
        Intervenant intervenant2 = new Intervenant("Emma", "Wilson", "emma.wilson@example.com", "test2", "87654321", 2);
        Intervenant intervenant3 = new Intervenant("Luke", "Johnson", "luke.johnson@example.com", "test3", "13579246", 3);
        Intervenant intervenant4 = new Intervenant("Sophia", "White", "sophia.white@example.com", "test4", "24681357", 4);
        Intervenant intervenant5 = new Intervenant("James", "Brown", "james.brown@example.com", "test5", "31415926", 5);
        Intervenant intervenant6 = new Intervenant("Olivia", "Martin", "olivia.martin@example.com", "test6", "98765432", 6);

        intervenantMap.put(intervenant0.getEmail(), intervenant0);
        intervenantMap.put(intervenant1.getEmail(), intervenant1);
        intervenantMap.put(intervenant2.getEmail(), intervenant2);
        intervenantMap.put(intervenant3.getEmail(), intervenant3);
        intervenantMap.put(intervenant4.getEmail(), intervenant4);
        intervenantMap.put(intervenant5.getEmail(), intervenant5);
        intervenantMap.put(intervenant6.getEmail(), intervenant6);
        intervenantMap.put(testIntervenant.getEmail(), testIntervenant);

        // Création de requêtes de travail résidentielles pour les résidents
        ResidentialWorkRequest requete1 = new ResidentialWorkRequest(resident1, "Réparation de toiture", "Réparer la toiture endommagée", "Construction", LocalDate.of(2024, 11, 20), "Vieux-Montréal");
        ResidentialWorkRequest requete2 = new ResidentialWorkRequest(resident2, "Réfection de la clôture", "Refaire la clôture du jardin", "Aménagement paysager", LocalDate.of(2024, 11, 25), "Plateau-Mont-Royal");
        ResidentialWorkRequest requete3 = new ResidentialWorkRequest(resident3, "Installation de panneaux solaires", "Installer des panneaux solaires sur le toit", "Énergie renouvelable", LocalDate.of(2024, 12, 5), "Rosemont");
        ResidentialWorkRequest requete4 = new ResidentialWorkRequest(resident4, "Rénovation de la salle de bain", "Rénover la salle de bain avec de nouveaux équipements", "Construction", LocalDate.of(2024, 12, 10), "Outremont");
        ResidentialWorkRequest requete5 = new ResidentialWorkRequest(resident5, "Peinture extérieure", "Repeindre la façade de la maison", "Entretien paysager", LocalDate.of(2024, 12, 15), "Côte-des-Neiges");
        ResidentialWorkRequest requete6 = new ResidentialWorkRequest(resident6, "Réparation de l'allée", "Réparer l'allée principale en béton", "Travaux routiers", LocalDate.of(2024, 12, 20), "Ahuntsic-Cartierville");

        residentialWorkMap.put(resident1, requete1);
        residentialWorkMap.put(resident2, requete2);
        residentialWorkMap.put(resident3, requete3);
        residentialWorkMap.put(resident4, requete4);
        residentialWorkMap.put(resident5, requete5);
        residentialWorkMap.put(resident6, requete6);
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
