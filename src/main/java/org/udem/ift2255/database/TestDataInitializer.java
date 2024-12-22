package org.udem.ift2255.database;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.udem.ift2255.model.*;
import org.udem.ift2255.service.NotificationService;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

@ApplicationScoped
public class TestDataInitializer {

    private final EntityManager entityManager;

    // Inject NotificationService
    private final NotificationService notificationService;

    public TestDataInitializer(EntityManager entityManager, NotificationService notificationService) {
        this.entityManager = entityManager;
        this.notificationService = notificationService;
    }

    @Transactional
    public void initializeTestData() {
        // Supprimer toutes les données existantes
        clearDatabase();

        // Initialiser les Résidents
        Resident resident1 = persistEntity(() -> new Resident("John", "Smith", "resident@prototype.com", "test", "514-555-1234", "123 Rue de la Paix, Montréal", 25, "Vieux-Montréal"));
        Resident resident2 = persistEntity(() -> new Resident("Jane", "Doe", "jane.doe@example.com", "securepass", "514-555-6789", "1 Rue de la Paix, Montréal", 30, "Vieux-Montréal"));
        Resident resident3 = persistEntity(() -> new Resident("John", "Doe", "john.doe@example.com", "password1", "514-555-0001", "1 Rue de la Paix, Montréal", 28, "Plateau-Mont-Royal"));
        Resident resident4 = persistEntity(() -> new Resident("Sarah", "Lee", "sarah.lee@example.com", "strongpass", "514-555-0002", "10 Rue Sainte-Catherine, Montréal", 35, "Outremont"));
        Resident resident5 = persistEntity(() -> new Resident("Michael", "Johnson", "michael.johnson@example.com", "newpass", "514-555-9999", "123 Rue Sherbrooke, Montréal", 27, "Verdun"));

        // Initialiser les Intervenants
        Intervenant intervenant1 = persistEntity(() -> new Intervenant("Alice", "Martin", "intervenant@prototype.com", "test", "12345", "Plumbing"));
        Intervenant intervenant2 = persistEntity(() -> new Intervenant("Bob", "Johnson", "bob.johnson@example.com", "pass456", "67890", "Construction"));
        Intervenant intervenant3 = persistEntity(() -> new Intervenant("Charlie", "Brown", "charlie.brown@example.com", "pass789", "11223", "Electricity"));
        Intervenant intervenant4 = persistEntity(() -> new Intervenant("David", "Wilson", "david.wilson@example.com", "pass101", "44556", "Renovation"));
        Intervenant intervenant5 = persistEntity(() -> new Intervenant("Eve", "White", "eve.white@example.com", "pass202", "77889", "Landscaping"));

        // Initialiser les Projets
        Project project1 = persistEntity(() -> new Project("Réparation de la toiture", "Réparer la toiture endommagée", "Construction", "Vieux-Montréal", LocalDate.of(2024, 11, 20), LocalDate.of(2024, 12, 20), intervenant1));
        Project project2 = persistEntity(() -> new Project("Réfection de trottoir", "Remplacement des dalles de béton", "Travaux routiers", "Plateau-Mont-Royal", LocalDate.of(2024, 12, 15), LocalDate.of(2025, 1, 15), intervenant2));
        Project project3 = persistEntity(() -> new Project("Installation d'éclairage public", "Ajout de lampadaires écoénergétiques", "Signalisation et éclairage", "Outremont", LocalDate.of(2024, 12, 31), LocalDate.of(2025, 2, 15), intervenant3));
        Project project4 = persistEntity(() -> new Project("Entretien du jardin", "Aménagement des espaces verts", "Paysagisme", "Vieux-Montréal", LocalDate.of(2024, 9, 20), LocalDate.of(2024, 11, 20), intervenant4));
        Project project5 = persistEntity(() -> new Project("Maintenance réseau", "Mise à jour de la fibre optique", "Télécommunications", "Verdun", LocalDate.of(2025, 1, 15), LocalDate.of(2025, 3, 15), intervenant5));

        // Initialiser les Requêtes Résidentielles
        ResidentialWorkRequest request1 = persistEntity(() -> new ResidentialWorkRequest(resident1, "Réparation de la toiture", "Réparer la toiture endommagée", "Construction", LocalDate.of(2024, 11, 20), "Vieux-Montréal"));
        ResidentialWorkRequest request2 = persistEntity(() -> new ResidentialWorkRequest(resident2, "Réfection de trottoir", "Remplacement des dalles de béton", "Travaux routiers", LocalDate.of(2024, 12, 15), "Plateau-Mont-Royal"));
        ResidentialWorkRequest request3 = persistEntity(() -> new ResidentialWorkRequest(resident3, "Installation d'éclairage public", "Ajout de lampadaires écoénergétiques", "Signalisation et éclairage", LocalDate.of(2024, 12, 31), "Vieux-Montréal"));
        ResidentialWorkRequest request4 = persistEntity(() -> new ResidentialWorkRequest(resident4, "Entretien du jardin", "Aménagement des espaces verts", "Paysagisme", LocalDate.of(2024, 9, 20), "Outremont"));
        ResidentialWorkRequest request5 = persistEntity(() -> new ResidentialWorkRequest(resident5, "Maintenance réseau", "Mise à jour de la fibre optique", "Télécommunications", LocalDate.of(2025, 1, 15), "Verdun"));

        // Ajouter des Candidatures à certaines requêtes de travail
        Candidature candidature1 = persistEntity(() -> new Candidature(intervenant1, "Je suis intéressé par ce projet", request1));
        Candidature candidature2 = persistEntity(() -> new Candidature(intervenant2, "Je peux prendre en charge ce travail", request2));

        // Envoyer des notifications aux résidents du même quartier pour les projets
        notificationService.sendProjectNotification(project1);
        notificationService.sendProjectNotification(project2);
    }

    private void clearDatabase() {
        // Delete from Project first to remove any foreign key constraints
        entityManager.createQuery("DELETE FROM Project").executeUpdate();

        // Now delete from other tables
        List<String> tables = List.of("Candidature", "ResidentialWorkRequest", "Intervenant", "Resident", "Project");
        for (String table : tables) {
            entityManager.createQuery("DELETE FROM " + table).executeUpdate();
        }
    }


    private <T> T persistEntity(Supplier<T> supplier) {
        T entity = supplier.get();
        entityManager.persist(entity);
        return entity;
    }
}
