package com.maville.model;

import com.maville.database.Database;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ResidentTest {

    private Resident resident;

    @Before
    public void setUp() {
        Database.getResidentialWorkMap().clear();
        resident = new Resident("John", "Doe", "john.doe@example.com",
                "securePassword123", "1234567890", "1234 Street", 30);
    }

    @Test
    public void creerRequeteStoresTheRequestAgainstTheResident() {
        LocalDate startDate = LocalDate.of(2024, 12, 1);
        resident.creerRequete("New Fence Installation",
                "Install a wooden fence around the backyard.",
                WorkType.CONSTRUCTION.label(), startDate, "Plateau-Mont-Royal");

        Map<Resident, ResidentialWorkRequest> requests = Database.getResidentialWorkMap();
        assertTrue(requests.containsKey(resident));

        ResidentialWorkRequest request = requests.get(resident);
        assertNotNull(request);
        assertEquals("New Fence Installation", request.getWorkTitle());
        assertEquals("Install a wooden fence around the backyard.", request.getDetailedWorkDescription());
        assertEquals(WorkType.CONSTRUCTION.label(), request.getWorkType());
        assertEquals(startDate, request.getWorkWishedStartDate());
        assertEquals("Plateau-Mont-Royal", request.getQuartier());
        assertEquals(resident, request.getResident());
    }

    @Test
    public void aNewRequestIsOpenForApplications() {
        resident.creerRequete("Clôture", "Installation d'une clôture.",
                WorkType.CONSTRUCTION.label(), LocalDate.of(2024, 12, 1), "Plateau-Mont-Royal");

        assertTrue(Database.getResidentialWorkMap().get(resident).isWorkAvailable());
    }

    /**
     * The removal used to be keyed by email against a map keyed by Resident, so
     * it removed nothing and the request stayed open forever.
     */
    @Test
    public void fermerRequeteRemovesAClosedRequest() {
        resident.creerRequete("Clôture", "Installation d'une clôture.",
                WorkType.CONSTRUCTION.label(), LocalDate.of(2024, 12, 1), "Plateau-Mont-Royal");
        ResidentialWorkRequest request = Database.getResidentialWorkMap().get(resident);

        request.rendreIndisponible();
        resident.fermerRequete(request);

        assertFalse(Database.getResidentialWorkMap().containsKey(resident));
    }

    @Test
    public void fermerRequeteKeepsARequestThatIsStillOpen() {
        resident.creerRequete("Clôture", "Installation d'une clôture.",
                WorkType.CONSTRUCTION.label(), LocalDate.of(2024, 12, 1), "Plateau-Mont-Royal");
        ResidentialWorkRequest request = Database.getResidentialWorkMap().get(resident);

        resident.fermerRequete(request);

        assertTrue(Database.getResidentialWorkMap().containsKey(resident));
    }

    @Test
    public void creerRequeteTwiceKeepsOnlyTheLatest() {
        resident.creerRequete("Première", "d1", WorkType.CONSTRUCTION.label(),
                LocalDate.of(2024, 12, 1), "Plateau");
        resident.creerRequete("Deuxième", "d2", WorkType.PAYSAGER.label(),
                LocalDate.of(2025, 1, 15), "Rosemont");

        assertEquals(1, Database.getResidentialWorkMap().size());
        assertEquals("Deuxième", Database.getResidentialWorkMap().get(resident).getTitle());
    }

    @Test
    public void exposesTheDetailsItWasBuiltWith() {
        assertEquals("John", resident.getFirstName());
        assertEquals("Doe", resident.getLastName());
        assertEquals("john.doe@example.com", resident.getEmail());
        assertEquals("securePassword123", resident.getPassword());
        assertEquals("1234567890", resident.getPhone());
        assertEquals("1234 Street", resident.getAddress());
        assertEquals(30, resident.getAge());
    }
}
