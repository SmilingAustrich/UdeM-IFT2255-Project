package org.udem.ift2255.ui;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.udem.ift2255.database.TestDataInitializer;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * This is a Quarkus JAX-RS resource class that initializes test data on app startup
 * and redirects the user to the login page at the root URL.
 */
@Path("/")  // Path for the root URL (http://localhost:8080/)
@Singleton   // Ensures this is a Singleton for the application lifecycle
public class Main {

    @Inject
    TestDataInitializer testDataInitializer;  // Inject TestDataInitializer

    public void onStart(@Observes StartupEvent event) {
        try {
            testDataInitializer.initializeTestData();
            System.out.println("Test data initialized successfully!");
        } catch (Exception e) {
            System.err.println("Failed to initialize test data: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Redirect to the login page (DefaultPage.html)
    @GET
    public Response redirectToLogin() {
        // Redirect to DefaultPage.html located in src/main/resources/META-INF/resources/
        return Response.seeOther(java.net.URI.create("/login.html")).build();
    }
}
