package org.udem.ift2255.database;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.udem.ift2255.model.Intervenant;
import org.udem.ift2255.model.Resident;
import org.udem.ift2255.model.ResidentialWorkRequest;

import java.time.LocalDate;
import java.util.function.Supplier;

@ApplicationScoped
public class TestDataInitializer {

    private final EntityManager entityManager;

    public TestDataInitializer(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void initializeTestData() {
        // Initialize Residents
        persistIfNotExists("resident@prototype.com", Resident.class,
                () -> new Resident("John", "Smiths", "resident@prototype.com", "password123", "514-555-1234", "123 Rue de la Paix, Montréal", 25));
        persistIfNotExists("john.doe@example.com", Resident.class,
                () -> new Resident("John", "Doe", "john.doe@example.com", "password1", "514-555-0001", "1 Rue de la Paix, Montréal", 30));
        persistIfNotExists("jane.doe@example.com", Resident.class,
                () -> new Resident("Jane", "Doe", "jane.doe@example.com", "securepass", "514-555-6789", "2 Rue de la Paix, Montréal", 28));

        // Initialize Intervenants
        persistIfNotExists("intervenant@prototype.com", Intervenant.class,
                () -> new Intervenant("Jane", "Doe", "intervenant@prototype.com", "password456", "12345678", 1));
        persistIfNotExists("emma.wilson@example.com", Intervenant.class,
                () -> new Intervenant("Emma", "Wilson", "emma.wilson@example.com", "test2", "87654321", 2));
        persistIfNotExists("jack.smith@example.com", Intervenant.class,
                () -> new Intervenant("Jack", "Smith", "jack.smith@example.com", "strongpass", "98765432", 3));

        // Initialize Work Requests
        persistWorkRequestIfNotExists("Réparation de la toiture", () ->
                new ResidentialWorkRequest(findResidentByEmail("john.doe@example.com"),
                        "Réparation de la toiture", "Réparer la toiture endommagée", "Construction",
                        LocalDate.of(2024, 11, 20), "Vieux-Montréal"));
        persistWorkRequestIfNotExists("Réfection de trottoir", () ->
                new ResidentialWorkRequest(findResidentByEmail("jane.doe@example.com"),
                        "Réfection de trottoir", "Remplacement des dalles de béton", "Travaux routiers",
                        LocalDate.of(2024, 12, 15), "Plateau-Mont-Royal"));
        persistWorkRequestIfNotExists("Installation d'éclairage public", () ->
                new ResidentialWorkRequest(findResidentByEmail("resident@prototype.com"),
                        "Installation d'éclairage public", "Ajout de lampadaires écoénergétiques",
                        "Signalisation et éclairage", LocalDate.of(2024, 12, 31), "Hochelaga-Maisonneuve"));
    }

    private <T> void persistIfNotExists(String email, Class<T> entityClass, Supplier<Object> supplier) {
        long count = entityManager.createQuery("SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e WHERE e.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        if (count == 0) {
            entityManager.persist(supplier.get());
        } else {
            System.out.println(entityClass.getSimpleName() + " with email " + email + " already exists. Skipping.");
        }
    }

    private void persistWorkRequestIfNotExists(String title, Supplier<ResidentialWorkRequest> supplier) {
        long count = entityManager.createQuery("SELECT COUNT(w) FROM ResidentialWorkRequest w WHERE w.workTitle = :title", Long.class)
                .setParameter("title", title)
                .getSingleResult();
        if (count == 0) {
            entityManager.persist(supplier.get());
        } else {
            System.out.println("Work request with title \"" + title + "\" already exists. Skipping.");
        }
    }

    private Resident findResidentByEmail(String email) {
        return entityManager.createQuery("SELECT r FROM Resident r WHERE r.email = :email", Resident.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
