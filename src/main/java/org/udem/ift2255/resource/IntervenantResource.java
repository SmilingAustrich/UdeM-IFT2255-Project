package org.udem.ift2255.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.udem.ift2255.dto.CandidatureRequestDTO;
import org.udem.ift2255.model.Intervenant;
import org.udem.ift2255.model.ResidentialWorkRequest;
import org.udem.ift2255.service.IntervenantService;
import org.udem.ift2255.service.ResidentialWorkRequestService;

import java.util.List;

@Path("/intervenants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IntervenantResource {

    @Inject
    IntervenantService intervenantService;

    @Inject
    ResidentialWorkRequestService workRequestService;

    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public String getIntervenantLoginPage() {
        // Serve the Intervenant login page
        return "<html><body>" +
                "<h2>Intervenant Login</h2>" +
                "<form action='/intervenant/authenticate' method='post'>" +
                "Email: <input type='text' name='email' /><br/>" +
                "Password: <input type='password' name='password' /><br/>" +
                "<input type='submit' value='Login' />" +
                "</form>" +
                "</body></html>";
    }

    @POST
    @Path("/authenticate")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateIntervenant(@FormParam("email") String email, @FormParam("password") String password) {
        boolean isAuthenticated = intervenantService.authenticate(email, password);

        if (isAuthenticated) {
            return Response.ok().entity("Intervenant authenticated successfully").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        }
    }

    @POST
    @Path("/register")
    @Transactional
    public Response registerIntervenant(Intervenant intervenant) {
        if (Intervenant.find("email", intervenant.getEmail()).firstResult() != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Email already registered").build();
        }
        intervenant.persist();
        return Response.status(Response.Status.CREATED).entity("Registration successful").build();
    }

    @POST
    @Path("/login")
    public Response loginIntervenant(@QueryParam("email") String email, @QueryParam("password") String password) {
        Intervenant intervenant = Intervenant.find("email", email).firstResult();
        if (intervenant == null || !intervenant.getPassword().equals(password)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        }
        return Response.ok("Login successful").build();
    }

    @GET
    @Path("/consultRequetes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consulterRequetes(@QueryParam("filterOption") int filterOption,
                                      @QueryParam("filterValue") String filterValue) {
        List<ResidentialWorkRequest> allRequests = workRequestService.getAllRequests(); // Get all requests from DB via service
        List<ResidentialWorkRequest> filteredRequests = intervenantService.consulterListeRequetesTravaux(filterOption, filterValue, allRequests);

        return Response.ok(filteredRequests).build();
    }

    @POST
    @Path("/submitCandidature")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response soumettreCandidature(CandidatureRequestDTO candidatureRequestDTO, @QueryParam("intervenantId") Long intervenantId) {
        // Retrieve the Intervenant object based on the provided intervenantId
        Intervenant intervenant = intervenantService.getIntervenantById(intervenantId);

        if (intervenant == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Intervenant not found").build();
        }

        // Retrieve the work request associated with the given title (or other filtering criteria)
        ResidentialWorkRequest workRequest = workRequestService.getRequestByTitle(candidatureRequestDTO.getTitreRequete());
        if (workRequest == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Work request not found").build();
        }

        // Get the workType and neighbourhood from the workRequest object
        String workType = workRequest.getWorkType(); // Get work type from the request
        String neighbourhood = workRequest.getNeighbourhood(); // Get neighbourhood from the request

        // Retrieve filtered requests based on these parameters
        List<ResidentialWorkRequest> filteredRequests = workRequestService.getFilteredRequests(workType, neighbourhood);

        // Pass all necessary parameters to the service method
        String result = intervenantService.soumettreCandidature(candidatureRequestDTO.getTitreRequete(),
                candidatureRequestDTO.getMessage(),
                filteredRequests,
                intervenant);

        return Response.ok(result).build();
    }
}
