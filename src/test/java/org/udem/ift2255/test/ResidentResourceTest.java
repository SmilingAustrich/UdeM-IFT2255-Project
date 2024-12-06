//package org.udem.ift2255.test;
//
//import io.quarkus.test.junit.QuarkusTest;
//import io.quarkus.test.InjectMock;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.udem.ift2255.dto.WorkRequestDTO;
//import org.udem.ift2255.model.Resident;
//import jakarta.ws.rs.core.Response;
//import org.udem.ift2255.service.AuthenticationService;
//import org.udem.ift2255.service.ResidentService;
//
//import static org.hamcrest.Matchers.is;
//import static io.restassured.RestAssured.given;
//
//@QuarkusTest
//public class ResidentResourceTest {
//
//    @InjectMock
//    AuthenticationService authenticationService;  // Injecting AuthenticationService, not ResidentService
//
//    @InjectMock
//    ResidentService residentService;
//    @Test
//    public void testAuthenticateResidentSuccess() {
//        // Mock the service call to simulate successful authentication
//        Mockito.when(authenticationService.authenticate("test@example.com", "password123", "resident")).thenReturn(true);
//
//        // Make the HTTP request and assert the response
//        given()
//                .formParam("email", "test@example.com")
//                .formParam("password", "password123")
//                .formParam("userType", "resident")  // Add the userType parameter
//                .when().post("/residents/authenticate")
//                .then()
//                .statusCode(Response.Status.OK.getStatusCode())
//                .body(is("Resident authenticated successfully"));
//    }
//
//    @Test
//    public void testAuthenticateResidentFailure() {
//        // Mock the service call to simulate failed authentication
//        Mockito.when(authenticationService.authenticate("test@example.com", "wrongpassword", "resident")).thenReturn(false);
//
//        // Make the HTTP request and assert the response
//        given()
//                .formParam("email", "test@example.com")
//                .formParam("password", "wrongpassword")
//                .formParam("userType", "resident")  // Add the userType parameter
//                .when().post("/residents/authenticate")
//                .then()
//                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode())
//                .body(is("Invalid credentials"));
//    }
//
//    @Test
//    public void testRegisterResidentSuccess() {
//        Resident resident = new Resident("John", "Doe", "john@example.com", "password123", "123-456-7890", "Some Address", 30);
//
//        given()
//                .contentType("application/json")
//                .body(resident)
//                .when().post("/residents/register")
//                .then()
//                .statusCode(Response.Status.CREATED.getStatusCode())
//                .body(is("Registration successful"));
//    }
//
//
//}
