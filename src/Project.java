import java.io.Serializable;

public class Project implements Serializable {

    private String projectName;
    private String projectDescription;
    private String projectType;
    private String projectStatus;
    private int StartDate;
    private int EndDate;
    private Intervenant projectOwner;

    public Project(String projectName, String projectDescription, String projectType, String projectStatus, int startDate, int endDate, Intervenant projectOwner) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectType = projectType;
        this.projectStatus = projectStatus;
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.projectOwner = projectOwner;
    }
}

