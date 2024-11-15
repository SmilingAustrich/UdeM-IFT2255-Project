import java.util.HashMap;
import java.util.Map;

public class RequeteTravailResidentiel {
    private Resident resident;
    private String description;
    private boolean isAvailable;
    private Map<Intervenant, String> candidatures;
    private Intervenant intervenantChoisi;

    public RequeteTravailResidentiel(Resident resident, String description) {
        this.resident = resident;
        this.description = description;
        this.isAvailable = true;
        this.candidatures = new HashMap<>();
    }

    public Resident getResident() {
        return resident;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rendreDisponible() {
        this.isAvailable = true;
    }

    public void rendreIndisponible() {
        this.isAvailable = false;
    }

    public void ajouterCandidature(Intervenant intervenant, String message) {
        if (isAvailable){
            candidatures.put(intervenant,message);
            System.out.println("Candidature soumise par " + intervenant.getFirstName());
        }
    }

    public void retirerCandidature(Intervenant intervenant){
        candidatures.remove(intervenant);
        System.out.println("Candidature retirée par " + intervenant.getFirstName());
    }

    public void choisirCandidature(Intervenant intervenantChoisi, String messageResident){
        if (candidatures.containsKey(intervenantChoisi)){
            this.intervenantChoisi = intervenantChoisi;
            System.out.println("Candidature choisie : " + intervenantChoisi.getFirstName());
            if (messageResident != null){
                System.out.println("Message du résident : " + messageResident);
            }
        }
    }

    public void confirmerCandidature(){
        if (intervenantChoisi != null){
            rendreIndisponible();
            System.out.println("Candidature confirmée par l'intervenant " + intervenantChoisi.getFirstName());
        }
    }
}

