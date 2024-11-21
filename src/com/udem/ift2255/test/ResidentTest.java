package com.udem.ift2255.test;

import static org.junit.Assert.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.udem.ift2255.model.Resident;
import com.udem.ift2255.model.ResidentialWorkRequest;
import com.udem.ift2255.database.Database;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;


/* Classe test codée par Tarek */
public class ResidentTest {

    private Resident resident;

    @Before
    public void setUp() {
        // Nettoyer la base de données des requêtes avant chaque test pour assurer un état propre
        Database.getResidentialWorkMap().clear();

        // Initialisation une instance de Resident pour les test
        resident = new Resident(
                "John",         // Prénom
                "Doe",          // Nom de famille
                "john.doe@example.com", // Adresse e-mail
                "securePassword123",    // Mot de passe
                "1234567890",   // Téléphone
                "1234 Street",  // Adresse
                30              // Âge
        );
    }

    @Test
    public void testCreerRequete() {
        // Étape 1 : Préparer les données d'entrée pour la requête de travaux
        String workTitle = "New Fence Installation";
        String detailedWorkDescription = "Install a wooden fence around the backyard.";
        String workType = "Construction";
        LocalDate workWishedStartDate = LocalDate.of(2024, 12, 1);
        String quartier = "Plateau-Mont-Royal";

        // Étape 2 : Appeler la méthode pour créer une requête
        resident.creerRequete(workTitle, detailedWorkDescription, workType, workWishedStartDate, quartier);

        // Étape 3 : Vérifier que la requête a été ajoutée à la base de données
        Map<Resident, ResidentialWorkRequest> workRequests = Database.getResidentialWorkMap();

        // Assertions pour vérifier que le résident a une requête de travaux
        assertTrue(workRequests.containsKey(resident));

        ResidentialWorkRequest request = workRequests.get(resident);
        assertNotNull(request);
        assertEquals(workTitle, request.getWorkTitle());
        assertEquals(detailedWorkDescription, request.getDetailedWorkDescription());
        assertEquals(workType, request.getWorkType());
        assertEquals(workWishedStartDate, request.getWorkWishedStartDate());
        assertEquals(quartier, request.getQuartier());

        System.out.println("Tous les tests pour la méthode creerRequete ont été réussis.");

    }

    @Test
    public void testFetchTravauxData() {
        // Étape 1 : Créer une instance de Resident
        Resident resident = new Resident(
                "John",
                "Doe",
                "john.doe@example.com",
                "securePassword123",
                "1234567890",
                "1234 Street",
                30
        );

        try {
            // Étape 2 : Récupérer les données des travaux depuis l'API
            JsonArray travaux = resident.fetchTravauxData();

            // Étape 3 : Vérifier que les données récupérées ne sont pas nulles et contiennent des éléments
            assertNotNull("Le JSON array ne devrait pas être 'null' ", travaux);
            assertTrue("Le JSON array devrait contenir des données", travaux.size() > 0);

            // Optionnel : Vérifier la structure du premier enregistrement
            JsonElement firstRecord = travaux.get(0);
            assertTrue("Le premier élément doit être un JsonObject", firstRecord.isJsonObject());
            assertTrue("Le premier élément doit avoir une section 'id'", firstRecord.getAsJsonObject().has("id"));
            assertTrue("Le premier élément doit avoir une section 'boroughid'", firstRecord.getAsJsonObject().has("boroughid"));
            assertTrue("Le premier élément doit avoir une section 'reason_category'", firstRecord.getAsJsonObject().has("reason_category"));
            assertTrue("Le premier élément doit avoir une section 'organizationname'", firstRecord.getAsJsonObject().has("organizationname"));

            // Affichage pour confirmer que les tests sont passés
            System.out.println("Tous les tests pour la méthode fetchTravauxData ont été réussis.");

        } catch (IOException e) {
            fail("Une erreur IOException a eu lieu: " + e.getMessage());
        }
    }

    @Test
    public void testSuivreRequetesResidentielles() {

        // Étape 1 : Préparer une requête de travaux pour le résident
        String workTitle = "Installation de Clôture";
        String detailedWorkDescription = "Installation d'une clôture en bois autour du jardin.";
        String workType = "Construction";
        LocalDate workWishedStartDate = LocalDate.of(2024, 12, 1);
        String quartier = "Plateau-Mont-Royal";
        resident.creerRequete(workTitle, detailedWorkDescription, workType, workWishedStartDate, quartier);

        // Étape 2 : Vérifier que la requête existe dans la base de données
        Map<Resident, ResidentialWorkRequest> requetes = Database.getResidentialWorkMap();
        assertTrue("Le résident devrait avoir une requête active", requetes.containsKey(resident));

        ResidentialWorkRequest requete = requetes.get(resident);
        assertNotNull("La requête ne devrait pas être nulle", requete);
        assertEquals(workTitle, requete.getTitle());
        assertEquals(detailedWorkDescription, requete.getDescription());
        assertEquals(workType, requete.getWorkType());
        assertEquals(quartier, requete.getNeighbourhood());
        assertEquals(workWishedStartDate, requete.getStartDate());

        // Affichage pour confirmer que les tests sont passés
        System.out.println("Tous les tests pour la méthode suivreRequetesResidentielles ont été réussis.");
    }
}
