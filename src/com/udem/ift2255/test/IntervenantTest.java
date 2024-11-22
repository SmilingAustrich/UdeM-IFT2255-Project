package com.udem.ift2255.test;

import com.udem.ift2255.model.Intervenant;
import com.udem.ift2255.model.ResidentialWorkRequest;
import com.udem.ift2255.model.Resident;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class IntervenantTest {

    private Intervenant intervenant;
    private ResidentialWorkRequest request;

    @Before
    public void setUp() {
        Resident resident = new Resident("John", "Doe", "john.doe@example.com", "password123", "1234567890", "123 Main St", 30);
        request = new ResidentialWorkRequest(resident, "Repair Road", "Fixing potholes", "Road Work", LocalDate.now(), "Plateau");
        intervenant = new Intervenant("Jane", "Smith", "jane.smith@example.com", "password456", "12345678", 1);
    }

    @Test
    public void testSoumettreCandidature() {
        // Act
        intervenant.soumettreCandidature(request, "Interested in this work");

        // Assert
        assertTrue(request.isWorkAvailable());
        assertEquals(1, request.getCandidatures().size());
        assertTrue(request.getCandidatures().containsKey(intervenant));
    }

    @Test
    public void testRetirerCandidature() {
        // Arrange
        intervenant.soumettreCandidature(request, "Interested in this work");

        // Vérifie que la candidature existe avant suppression
        assertTrue(request.getCandidatures().containsKey(intervenant));

        // Act
        intervenant.retirerCandidature(request);

        // Assert
        assertFalse(request.getCandidatures().containsKey(intervenant));
    }



    @Test
    public void testConfirmerCandidature() {
        // Arrange
        intervenant.soumettreCandidature(request, "Interested in this work");
        request.rendreIndisponible();

        // Act
        intervenant.confirmerCandidature(request);

        // Assert
        assertTrue(request.isWorkAvailable());
    }
}



