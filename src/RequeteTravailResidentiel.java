import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;


public class RequeteTravailResidentiel implements Serializable {
    private String workTitle;
    private String detailedWorkDescription;
    private String workType;
    private String quartier;
    private LocalDate workWishedStartDate;
    private Resident resident;

    private boolean isWorkAvailable;
    private Map<Intervenant, String> candidatures;
    private Intervenant intervenantChoisi;

    public RequeteTravailResidentiel(Resident resident, String workTitle, String detailedWorkDescription, String workType, LocalDate workWishedStartDate, String quartier) {
        this.resident = resident;
        this.isWorkAvailable = true;
        this.candidatures = new HashMap<>();
        this.workTitle = workTitle;
        this.detailedWorkDescription = detailedWorkDescription;
        this.workType = workType;
        this.workWishedStartDate = workWishedStartDate;
        this.quartier = quartier;
    }

    public Resident getResident() {
        return resident;
    }

    public String getDescription() {
        return detailedWorkDescription;
    }

    public boolean isWorkAvailable() {
        return isWorkAvailable;
    }

    public void rendreDisponible() {
        this.isWorkAvailable = true;
    }

    public void rendreIndisponible() {
        this.isWorkAvailable = false;
    }

    public void ajouterCandidature(Intervenant intervenant, String message) {
        if (isWorkAvailable){
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

    public String getTitre() {
        return workTitle;
    }

    public LocalDate getDateDebut() {
        return workWishedStartDate;
    }

    public String getTypeTravaux() {
        return workType;
    }

    public String getQuartier() {
        return quartier;
    }
}

