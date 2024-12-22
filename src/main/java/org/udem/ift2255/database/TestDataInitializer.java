/**
 * Classe TestDataInitializer
 *
 * Cette classe est responsable de l'initialisation des données de test dans la base de données.
 * Elle crée et persiste plusieurs entités (Resident, Intervenant, Project, ResidentialWorkRequest, Candidature)
 * et utilise également un service de notification pour envoyer des notifications aux résidents.
 */
package org.udem.ift2255.database;

import jakarta.enterprise.context.ApplicationScoped; // Annotation indiquant que la classe a un cycle de vie de type ApplicationScoped
import jakarta.persistence.EntityManager;             // Permet de gérer et manipuler les entités (Entity) en base de données
import jakarta.transaction.Transactional;            // Annotation permettant de gérer les transactions
import org.udem.ift2255.model.*;                     // Import des entités du modèle
import org.udem.ift2255.service.NotificationService; // Import du service de notification

import java.time.LocalDate;                          // Permet de manipuler des dates
import java.util.List;                               // Interface List (structure de données de type liste)
import java.util.function.Supplier;                  // Interface fonctionnelle Supplier (fournit un objet sur demande)

/**
 * Classe servant à initialiser les données de test.
 * Les données créées ici sont utilisées pour alimenter la base de données
 * afin de simuler un environnement complet.
 */
@ApplicationScoped
public class TestDataInitializer {

    /**
     * Fournit l'interface permettant de gérer les entités et d'interagir
     * avec la base de données (persistence context).
     */
    private final EntityManager entityManager;

    /**
     * Service de notification permettant d'envoyer des notifications
     * aux résidents ciblés lors de la création de projets.
     */
    private final NotificationService notificationService;

    /**
     * Constructeur TestDataInitializer.
     *
     * @param entityManager        Gère les opérations de persistence sur les entités.
     * @param notificationService  Permet l'envoi de notifications aux résidents.
     */
    public TestDataInitializer(EntityManager entityManager, NotificationService notificationService) {
        this.entityManager = entityManager;
        this.notificationService = notificationService;
    }

    /**
     * Méthode principale d'initialisation de données de test.
     * Elle est transactionnelle, ce qui signifie que toutes les opérations
     * de persistence effectuées dans cette méthode seront dans une même transaction.
     */
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

    /**
     * Méthode privée permettant de supprimer toutes les données existantes dans la base de données
     * afin d'éviter les conflits ou les duplications lors de l'initialisation des données de test.
     */
    private void clearDatabase() {
        // Supprimer d'abord les entités Project pour éviter les contraintes de clés étrangères
        entityManager.createQuery("DELETE FROM Project").executeUpdate();

        // Supprimer les données des autres tables
        List<String> tables = List.of("Candidature", "ResidentialWorkRequest", "Intervenant", "Resident", "Project");
        for (String table : tables) {
            entityManager.createQuery("DELETE FROM " + table).executeUpdate();
        }
    }

    /**
     * Méthode générique permettant de créer et de persister une entité.
     * Cette méthode utilise un Supplier<T> pour instancier l'entité avant de la persister.
     *
     * @param supplier Une lambda ou méthode renvoyant une nouvelle instance de l'entité à persister.
     * @param <T>      Le type de l'entité à persister.
     * @return         L'entité nouvellement créée et persistée.
     */
    private <T> T persistEntity(Supplier<T> supplier) {
        T entity = supplier.get();
        entityManager.persist(entity);
        return entity;
    }
}
