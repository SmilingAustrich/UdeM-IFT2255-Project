package com.udem.ift2255.test;

import com.udem.ift2255.auth.AuthenticationService;
import com.udem.ift2255.database.Database;
import com.udem.ift2255.model.Intervenant;
import com.udem.ift2255.model.Resident;
import com.udem.ift2255.ui.Menu;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class IntervenantTest {

    @Test
    public void testSignUpIntervenant() {
        Intervenant testIntervenant = new Intervenant("Ilyesse", "Bouzommita", "Ilyesse.bouzommita@outlook.com", "Ilyesse123", "10982345", 2);
        AuthenticationService.signUpIntervenant(testIntervenant);

        Intervenant retrievedIntervenant = Database.getIntervenantMap().get("Ilyesse.bouzommita@outlook.com");
        assertEquals(testIntervenant, retrievedIntervenant);
    }

    @Test
    public void testIntervenantLogInMenu() {
        Intervenant testIntervenant = new Intervenant("Ilyesse", "Bouzommita", "Ilyesse.bouzommita@outlook.com", "Ilyesse123", "10982345", 2);
        AuthenticationService.signUpIntervenant(testIntervenant);

        String simulatedInput = "Ilyesse.bouzommita@outlook.com\nIlyesse123\n\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Menu menu = new Menu();
        menu.intervenantLogInMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("Connexion réussie ! Bienvenue, Ilyesse."));
    }

    @Test
    public void testIntervenantInscriptionMenu() {
        String simulatedInput = "Ilyesse\nBouzommita\nIlyesse.bouzommita@outlook.com\nIlyesse123\n10982345\n2\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Menu menu = new Menu();
        menu.intervenantInscriptionMenu();

        Intervenant retrievedIntervenant = Database.getIntervenantMap().get("Ilyesse.bouzommita@outlook.com");
        assertNotNull(retrievedIntervenant);
        assertEquals("Ilyesse", retrievedIntervenant.getFirstName());
        assertEquals("Bouzommita", retrievedIntervenant.getLastName());

        String output = outputStream.toString();
        assertTrue(output.contains("Inscription réussie ! Vous pouvez maintenant vous connecter."));
    }




}

