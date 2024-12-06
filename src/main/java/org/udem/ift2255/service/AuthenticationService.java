package org.udem.ift2255.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.udem.ift2255.model.Intervenant;
import org.udem.ift2255.model.Resident;

@ApplicationScoped
public class AuthenticationService {

    // Authenticate using Panache query methods for both Resident and Intervenant
    @Transactional
    public boolean authenticate(String email, String password, String userType) {
        if ("intervenant".equalsIgnoreCase(userType)) {
            // Authenticate Intervenant
            Intervenant intervenant = Intervenant.find("email", email).firstResult();
            if (intervenant != null) {
                // Compare the passwords (in real apps, make sure to hash the password)
                return intervenant.getPassword().equals(password); // Replace with secure password comparison
            }
        } else if ("resident".equalsIgnoreCase(userType)) {
            // Authenticate Resident
            Resident resident = Resident.find("email", email).firstResult();
            if (resident != null) {
                // Compare the passwords (in real apps, make sure to hash the password)
                return resident.getPassword().equals(password); // Replace with secure password comparison
            }
        }
        return false; // If no user found or passwords don't match
    }
}
