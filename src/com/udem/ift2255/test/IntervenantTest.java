package com.udem.ift2255.test;

import com.udem.ift2255.model.Intervenant;
import com.udem.ift2255.model.ResidentialWorkRequest;
import com.udem.ift2255.model.Resident;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

// Codé par Ilyesse
public class IntervenantTest {

    @Test
    public void testSoumettreCandidature() {
        // Arrange
        ResidentialWorkRequest request = new ResidentialWorkRequest(
                new Resident("John", "Doe", "john.doe@example.com", "password123", "1234567890", "2910 rue Mt Royal", 30),
                "Repair Road", "Fixing potholes", "Road Work", LocalDate.now(), "LaSalle"
        );
        Intervenant intervenant = new Intervenant("Scottie", "Barnes", "Scottie.Barnes@example.com", "password123", "87654321", 1);

        // Act
        intervenant.soumettreCandidature(request, "Interested in this work");

        // Assert
        assertTrue(request.isWorkAvailable());
        assertEquals(1, request.getCandidaturesMap().size());
        assertTrue(request.getCandidaturesMap().containsKey(intervenant));
        System.out.println("\nTous les tests pour la méthode SoumettreCandidature ont été réussis.\n");

    }

    @Test
    public void testRetirerCandidature() {
        // Arrange
        ResidentialWorkRequest request = new ResidentialWorkRequest(
                new Resident("John", "Doe", "john.doe@example.com", "password123", "1234567890", "1029 rue Lafleur", 30),
                "Repair Road", "Fixing potholes", "Road Work", LocalDate.now(), "LaSalle"
        );
        Intervenant intervenant = new Intervenant("Ilyesse", "Bouzommita", "Ilyesse.Bouzommita@example.com", "password123", "12345678", 1);
        intervenant.soumettreCandidature(request, "Interested in this work");

        // Vérifie que la candidature existe avant suppression
        assertTrue(request.getCandidaturesMap().containsKey(intervenant));

        // Act
        intervenant.retirerCandidature(request);

        // Assert
        assertFalse(request.getCandidaturesMap().containsKey(intervenant));
        System.out.println("\nTous les tests pour la méthode retirerCandidature ont été réussis.\n");

    }

    @Test
    public void testConfirmerCandidature() {
        // Arrange
        ResidentialWorkRequest request = new ResidentialWorkRequest(
                new Resident("John", "Doe", "john.doe@example.com", "password123", "1234567890", "1260 rue Saguenay", 30),
                "Repair Road", "Fixing potholes", "Road Work", LocalDate.now(), "LaSalle"
        );
        Intervenant intervenant = new Intervenant("LaMelo", "Ball", "LaMelo.Ball@example.com", "password123", "56781234", 1);
        intervenant.soumettreCandidature(request, "Interested in this work");
        request.rendreIndisponible();

        // Act
        intervenant.confirmerCandidature(request);

        // Assert
        assertTrue(request.isWorkAvailable());
        System.out.println("\nTous les tests pour la méthode ConfirmerCandidature ont été réussis.\n");
    }

}





