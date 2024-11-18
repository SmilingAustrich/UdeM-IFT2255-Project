import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;


public class ResidentialWorkRequest implements Serializable {
    private String workTitle;
    private String detailedWorkDescription;
    private String workType;
    private String neighbourhood;
    private LocalDate workWishedStartDate;
    private Resident resident;



    private boolean isWorkAvailable;
    private Map<Intervenant, String> candidatures;
    private Intervenant chosenIntervenant;
    private static final long serialVersionUID = 1L;

    public ResidentialWorkRequest(Resident resident, String workTitle, String detailedWorkDescription, String workType, LocalDate workWishedStartDate, String quartier) {
        this.resident = resident;
        this.isWorkAvailable = true;
        this.candidatures = new HashMap<>();
        this.workTitle = workTitle;
        this.detailedWorkDescription = detailedWorkDescription;
        this.workType = workType;
        this.workWishedStartDate = workWishedStartDate;
        this.neighbourhood = quartier;
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

    //TODO: implementer et lié la méthode pour le devoir 3
    public void choisirCandidature(Intervenant intervenantChoisi, String messageResident){
        if (candidatures.containsKey(intervenantChoisi)){
            this.chosenIntervenant = intervenantChoisi;
            System.out.println("Candidature choisie : " + intervenantChoisi.getFirstName());
            if (messageResident != null){
                System.out.println("Message du résident : " + messageResident);
            }
        }
    }



    public String getTitle() {
        return workTitle;
    }

    public LocalDate getStartDate() {
        return workWishedStartDate;
    }

    public String getWorkType() {
        return workType;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }
}

