package org.udem.ift2255.test;

import org.udem.ift2255.model.Resident;
import org.udem.ift2255.model.ResidentialWorkRequest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.io.IOException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import static org.junit.jupiter.api.Assertions.*;

/* Classe test codée par Tarek */
@QuarkusTest
public class ResidentTest {

//    @Inject
//    Resident resident;
//
//    @BeforeEach
//    @Transactional
//    public void setUp() {
//        // Nettoyer la base de données des requêtes avant chaque test pour assurer un état propre
//        // Assuming there's a method to clear the database or reset the repository
//        resident.deleteAll();
//
//        // Initialisation d'une instance de Resident pour les tests
//        resident = new Resident(
//                "John",         // Prénom
//                "Doe",          // Nom de famille
//                "john.doe@example.com", // Adresse e-mail
//                "securePassword123",    // Mot de passe
//                "1234567890",   // Téléphone
//                "1234 Street",  // Adresse
//                30               // Âge
//        );
//        resident.persist();
//    }
//
//    @Test
//    @Transactional
//    public void testCreerRequete() {
//        // Étape 1 : Préparer les données d'entrée pour la requête de travaux
//        String workTitle = "New Fence Installation";
//        String detailedWorkDescription = "Install a wooden fence around the backyard.";
//        String workType = "Construction";
//        LocalDate workWishedStartDate = LocalDate.of(2024, 12, 1);
//        String quartier = "Plateau-Mont-Royal";
//
//        // Étape 2 : Appeler la méthode pour créer une requête
//        resident.creerRequete(workTitle, detailedWorkDescription, workType, workWishedStartDate, quartier);
//
//        // Étape 3 : Vérifier que la requête a été ajoutée à la base de données
//        ResidentialWorkRequest request = resident.getRequete();
//
//        // Assertions pour vérifier que le résident a une requête de travaux
//        assertNotNull(request);
//        assertEquals(workTitle, request.getWorkTitle());
//        assertEquals(detailedWorkDescription, request.getDetailedWorkDescription());
//        assertEquals(workType, request.getWorkType());
//        assertEquals(workWishedStartDate, request.getWorkWishedStartDate());
//        assertEquals(quartier, request.getQuartier());
//
//        System.out.println("Tous les tests pour la méthode creerRequete ont été réussis.");
//    }
//
//    @Test
//    public void testFetchTravauxData() {
//        try {
//            // Étape 2 : Récupérer les données des travaux depuis l'API
//            // Assuming resident has a method fetchTravauxData that returns JsonArray
//            JsonArray travaux = resident.fetchTravauxData();
//
//            // Étape 3 : Vérifier que les données récupérées ne sont pas nulles et contiennent des éléments
//            assertNotNull(travaux, "Le JSON array ne devrait pas être 'null' ");
//            assertTrue(travaux.size() > 0, "Le JSON array devrait contenir des données");
//
//            // Optionnel : Vérifier la structure du premier enregistrement
//            JsonElement firstRecord = travaux.get(0);
//            assertTrue(firstRecord.isJsonObject(), "Le premier élément doit être un JsonObject");
//            assertTrue(firstRecord.getAsJsonObject().has("id"), "Le premier élément doit avoir une section 'id'");
//            assertTrue(firstRecord.getAsJsonObject().has("boroughid"), "Le premier élément doit avoir une section 'boroughid'");
//            assertTrue(firstRecord.getAsJsonObject().has("reason_category"), "Le premier élément doit avoir une section 'reason_category'");
//            assertTrue(firstRecord.getAsJsonObject().has("organizationname"), "Le premier élément doit avoir une section 'organizationname'");
//
//            // Affichage pour confirmer que les tests sont passés
//            System.out.println("Tous les tests pour la méthode fetchTravauxData ont été réussis.");
//
//        } catch (IOException e) {
//            fail("Une erreur IOException a eu lieu: " + e.getMessage());
//        }
//    }
//
//    @Test
//    @Transactional
//    public void testSuivreRequetesResidentielles() {
//        // Étape 1 : Préparer une requête de travaux pour le résident
//        String workTitle = "Installation de Clôture";
//        String detailedWorkDescription = "Installation d'une clôture en bois autour du jardin.";
//        String workType = "Construction";
//        LocalDate workWishedStartDate = LocalDate.of(2024, 12, 1);
//        String quartier = "Plateau-Mont-Royal";
//        resident.creerRequete(workTitle, detailedWorkDescription, workType, workWishedStartDate, quartier);
//
//        // Étape 2 : Vérifier que la requête existe dans la base de données
//        ResidentialWorkRequest requete = resident.getRequete();
//        assertNotNull(requete, "La requête ne devrait pas être nulle");
//        assertEquals(workTitle, requete.getWorkTitle());
//        assertEquals(detailedWorkDescription, requete.getDetailedWorkDescription());
//        assertEquals(workType, requete.getWorkType());
//        assertEquals(quartier, requete.getQuartier());
//        assertEquals(workWishedStartDate, requete.getWorkWishedStartDate());
//
//        // Affichage pour confirmer que les tests sont passés
//        System.out.println("Tous les tests pour la méthode suivreRequetesResidentielles ont été réussis.");
//    }
}
