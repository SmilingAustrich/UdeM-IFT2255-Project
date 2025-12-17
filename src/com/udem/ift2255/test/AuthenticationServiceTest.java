package com.udem.ift2255.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.udem.ift2255.auth.AuthenticationService;
import com.udem.ift2255.database.Database;
import com.udem.ift2255.model.Intervenant;
import com.udem.ift2255.model.Resident;

/* Classe test codée par Karim*/
public class AuthenticationServiceTest {
    @Test
    public void testLoginIntervenant() {
        Intervenant testIntervenant = new Intervenant("Shaedon", "Sharpe", "shaedon.sharpe@example.com", "test12", "12345678", 1);
    AuthenticationService.signUpIntervenant(testIntervenant);

    boolean loginSuccessful = AuthenticationService.loginIntervenant("shaedon.sharpe@example.com", "test12");
    assertEquals(true, loginSuccessful);

    }

    @Test
    public void testLoginResident() {
    Resident testResident = new Resident("Karim", "Ndoye", "karim.ndoye@exemple.com", "password123", "514-555-1234", "123 Rue de Montréal", 22);
    AuthenticationService.signUpResident(testResident);

    boolean loginSuccessful = AuthenticationService.loginResident("karim.ndoye@exemple.com", "password123");
    assertEquals(true, loginSuccessful);

    }

    @Test
    public void testSignUpResident() {
        Resident testResident = new Resident("Anfernee", "Simons", "anfernee.simons@exemple.com", "password123", "514-555-9876", "456 Avenue Montreal", 30);
        AuthenticationService.signUpResident(testResident);
    
        Resident retrievedResident = Database.getResidentMap().get("anfernee.simons@exemple.com");
        assertEquals(testResident, retrievedResident);
    }
    
}
 