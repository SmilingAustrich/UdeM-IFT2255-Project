/**
 * Classe Project
 *
 * Cette classe représente un projet dans l'application Ma Ville.
 * Elle est utilisée pour gérer les informations liées aux projets
 * tels que le nom, la description, le type, le statut, les dates de début et de fin,
 * ainsi que le propriétaire du projet.
 */
package org.udem.ift2255.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity; // Simplifie les interactions avec la base de données
import jakarta.persistence.*;                         // Fournit les annotations JPA pour mapper les entités
import java.time.LocalDate;                           // Représentation des dates sans fuseau horaire

/**
 * Entité Project
 *
 * Cette entité est mappée à une table de base de données nommée "project".
 * Elle contient les informations essentielles à la gestion d'un projet
 * dans le système.
 */
@Entity
@Table(name = "project") // Définit le nom de la table associée en base de données
public class Project extends PanacheEntity {

    // Nom du projet
    @Column(name = "project_name", nullable = false)
    private String projectName;

    // Description détaillée du projet (limite à 500 caractères)
    @Column(name = "project_description", length = 500)
    private String projectDescription;

    // Type de projet (exemple : "Construction", "Paysagisme", etc.)
    @Column(name = "project_type", nullable = false)
    private String projectType;

    // Quartier où le projet est situé
    @Column(name = "neighbourhood", nullable = false)
    private String neighbourhood;

    // Statut du projet (par défaut : "prévu")
    @Column(name = "project_status", nullable = false)
    private String projectStatus;

    // Date de début du projet
    @Column(name = "start_date")
    private LocalDate startDate;

    // Date de fin du projet
    @Column(name = "end_date")
    private LocalDate endDate;

    // Propriétaire du projet (relation Many-to-One avec Intervenant)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_owner_id", nullable = false)
    private Intervenant projectOwner;

    /**
     * Constructeur avec paramètres.
     *
     * @param projectName        Le nom du projet.
     * @param projectDescription La description détaillée du projet.
     * @param projectType        Le type du projet.
     * @param neighbourhood      Le quartier où le projet est situé.
     * @param startDate          La date de début du projet.
     * @param endDate            La date de fin du projet.
     * @param projectOwner       L'intervenant responsable du projet.
     */
    public Project(String projectName, String projectDescription, String projectType, String neighbourhood, LocalDate startDate, LocalDate endDate, Intervenant projectOwner) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectType = projectType;
        this.neighbourhood = neighbourhood;
        this.projectStatus = "prévu"; // Statut par défaut
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectOwner = projectOwner;
    }

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Project() {
    }

    /**
     * Retourne le nom du projet.
     *
     * @return Le nom du projet.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Définit le nom du projet.
     *
     * @param projectName Le nom du projet à définir.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Retourne la description détaillée du projet.
     *
     * @return La description du projet.
     */
    public String getProjectDescription() {
        return projectDescription;
    }

    /**
     * Définit la description détaillée du projet.
     *
     * @param projectDescription La description à définir.
     */
    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    /**
     * Retourne le type de projet.
     *
     * @return Le type du projet.
     */
    public String getProjectType() {
        return projectType;
    }

    /**
     * Définit le type de projet.
     *
     * @param projectType Le type de projet à définir.
     */
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    /**
     * Retourne le statut du projet.
     *
     * @return Le statut du projet.
     */
    public String getProjectStatus() {
        return projectStatus;
    }

    /**
     * Définit le statut du projet.
     *
     * @param projectStatus Le statut à définir.
     */
    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    /**
     * Retourne la date de début du projet.
     *
     * @return La date de début sous forme de LocalDate.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Définit la date de début du projet.
     *
     * @param startDate La date de début à définir.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Retourne la date de fin du projet.
     *
     * @return La date de fin sous forme de LocalDate.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Définit la date de fin du projet.
     *
     * @param endDate La date de fin à définir.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Retourne l'intervenant propriétaire du projet.
     *
     * @return L'intervenant propriétaire.
     */
    public Intervenant getProjectOwner() {
        return projectOwner;
    }

    /**
     * Définit l'intervenant propriétaire du projet.
     *
     * @param projectOwner L'intervenant à associer au projet.
     */
    public void setProjectOwner(Intervenant projectOwner) {
        this.projectOwner = projectOwner;
    }

    /**
     * Retourne le quartier où le projet est situé.
     *
     * @return Le quartier du projet.
     */
    public String getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * Définit le quartier où le projet est situé.
     *
     * @param neighbourhood Le quartier à définir.
     */
    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }
}
