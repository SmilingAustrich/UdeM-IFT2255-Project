package org.udem.ift2255.resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.udem.ift2255.dto.UserDTO;
import org.udem.ift2255.model.Resident;
import org.udem.ift2255.model.Intervenant;

@Path("/signup")
public class SignUpResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response signup(UserDTO user) {
        if ("resident".equalsIgnoreCase(user.userType)) {
            Resident resident = new Resident();
            resident.setFirstName(user.firstName);
            resident.setLastName(user.lastName);
            resident.setEmail(user.email);
            resident.setPassword(user.password); // Use hashing in production
            resident.setPhone(user.phone);
            resident.setAddress(user.address);
            resident.setAge(user.age);
            resident.persist();

        } else if ("intervenant".equalsIgnoreCase(user.userType)) {
            Intervenant intervenant = new Intervenant();
            intervenant.setFirstName(user.firstName);
            intervenant.setLastName(user.lastName);
            intervenant.setEmail(user.email);
            intervenant.setPassword(user.password); // Use hashing in production
            intervenant.setType(user.type);
            intervenant.setCityId(user.cityId);
            intervenant.persist();

        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Type d'utilisateur invalide").build();
        }

        return Response.ok("Utilisateur créé avec succès").build();
    }
}
