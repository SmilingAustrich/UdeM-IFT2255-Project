//package org.udem.ift2255.test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import io.quarkus.test.junit.QuarkusTest;
//import org.junit.jupiter.api.Test;
//import org.udem.ift2255.auth.AuthenticationService;
//import org.udem.ift2255.model.Intervenant;
//import org.udem.ift2255.model.Resident;
//
//import jakarta.inject.Inject;
//
//@QuarkusTest
//public class AuthenticationServiceTest {
//
////    @Inject
////    AuthenticationService authenticationService;
////
////    @Test
////    public void testLoginIntervenant() {
////        Intervenant testIntervenant = new Intervenant("Shaedon", "Sharpe", "shaedon.sharpe@example.com", "test12", "12345678", 1);
////        authenticationService.signUpIntervenant(testIntervenant);
////
////        boolean loginSuccessful = authenticationService.loginIntervenant("shaedon.sharpe@example.com", "test12");
////        assertEquals(true, loginSuccessful);
////    }
////
////    @Test
////    public void testLoginResident() {
////        Resident testResident = new Resident("Karim", "Ndoye", "karim.ndoye@exemple.com", "password123", "514-555-1234", "123 Rue de Montréal", 22);
////        authenticationService.signUpResident(testResident);
////
////        boolean loginSuccessful = authenticationService.loginResident("karim.ndoye@exemple.com", "password123");
////        assertEquals(true, loginSuccessful);
////    }
////
////    @Test
////    public void testSignUpResident() {
////        Resident testResident = new Resident("Anfernee", "Simons", "anfernee.simons@exemple.com", "password123", "514-555-9876", "456 Avenue Montreal", 30);
////        authenticationService.signUpResident(testResident);
////
////        Resident retrievedResident = authenticationService.getResidentRepository().find("email", "anfernee.simons@exemple.com").firstResult();
////        assertEquals(testResident.getEmail(), retrievedResident.getEmail());
////    }
//}
