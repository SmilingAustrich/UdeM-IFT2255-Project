package org.udem.ift2255.database;

import org.udem.ift2255.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class TestDataInitializer {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Méthode pour initialiser les données de test dans la base de données.
     */
    @Transactional
    public void initializeTestData() {
        // Créer des résidents de test
        Resident testResident = new Resident("John", "Smiths", "resident@prototype.com", "password123", "514-555-1234", "123 Rue de la Paix, Montréal", 25);
        Resident resident0 = new Resident("John", "Doe", "test", "test", "514-555-0001", "1 Rue de la Paix, Montréal", 25);
        Resident resident1 = new Resident("John", "Doe", "john.doe@example.com", "password1", "514-555-0001", "1 Rue de la Paix, Montréal", 25);
        Resident resident2 = new Resident("Jane", "Smith", "jane.smith@example.com", "password2", "514-555-0002", "2 Rue de la Paix, Montréal", 30);
        Resident resident3 = new Resident("Alice", "Brown", "alice.brown@example.com", "password3", "514-555-0003", "3 Rue de la Paix, Montréal", 28);
        Resident resident4 = new Resident("Robert", "Miller", "robert.miller@example.com", "password4", "514-555-0004", "4 Rue de la Paix, Montréal", 35);
        Resident resident5 = new Resident("Emily", "Davis", "emily.davis@example.com", "password5", "514-555-0005", "5 Rue de la Paix, Montréal", 27);
        Resident resident6 = new Resident("David", "Wilson", "david.wilson@example.com", "password6", "514-555-0006", "6 Rue de la Paix, Montréal", 32);

        // Persist the residents
        entityManager.persist(testResident);
        entityManager.persist(resident0);
        entityManager.persist(resident1);
        entityManager.persist(resident2);
        entityManager.persist(resident3);
        entityManager.persist(resident4);
        entityManager.persist(resident5);
        entityManager.persist(resident6);

        // Créer des intervenants de test
        Intervenant testIntervenant = new Intervenant("Jane", "Doe", "intervenant@prototype.com", "password456", "12345678", 1);
        Intervenant intervenant0 = new Intervenant("Mark", "Taylor", "test", "test", "12345678", 1);
        Intervenant intervenant1 = new Intervenant("Mark", "Taylor", "mark.taylor@example.com", "test1", "12345678", 1);
        Intervenant intervenant2 = new Intervenant("Emma", "Wilson", "emma.wilson@example.com", "test2", "87654321", 2);
        Intervenant intervenant3 = new Intervenant("Luke", "Johnson", "luke.johnson@example.com", "test3", "13579246", 3);
        Intervenant intervenant4 = new Intervenant("Sophia", "White", "sophia.white@example.com", "test4", "24681357", 4);
        Intervenant intervenant5 = new Intervenant("James", "Brown", "james.brown@example.com", "test5", "31415926", 5);
        Intervenant intervenant6 = new Intervenant("Olivia", "Martin", "olivia.martin@example.com", "test6", "98765432", 6);

        // Persist the intervenants
        entityManager.persist(testIntervenant);
        entityManager.persist(intervenant0);
        entityManager.persist(intervenant1);
        entityManager.persist(intervenant2);
        entityManager.persist(intervenant3);
        entityManager.persist(intervenant4);
        entityManager.persist(intervenant5);
        entityManager.persist(intervenant6);

        // Créer des requêtes de travail pour les résidents
        ResidentialWorkRequest workRequest1 = new ResidentialWorkRequest(resident1, "Réparation de la toiture", "Réparer la toiture endommagée", "Construction", LocalDate.of(2024, 11, 20), "Vieux-Montréal");
        ResidentialWorkRequest workRequest2 = new ResidentialWorkRequest(resident2, "Réfection de la clôture", "Refaire la clôture du jardin", "Aménagement paysager", LocalDate.of(2024, 11, 25), "Plateau-Mont-Royal");

        // Enregistrer les requêtes de travail dans la base de données
        entityManager.persist(workRequest1);
        entityManager.persist(workRequest2);

        // Debugging: Check if data was inserted correctly
        List<Resident> residents = entityManager.createQuery("SELECT r FROM Resident r", Resident.class).getResultList();
        List<Intervenant> intervenants = entityManager.createQuery("SELECT i FROM Intervenant i", Intervenant.class).getResultList();

        System.out.println("Number of residents in the database: " + residents.size());
        System.out.println("Number of intervenants in the database: " + intervenants.size());
    }
}
