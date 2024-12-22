/**
 * Classe WorkRequestDTO
 *
 * Cette classe représente un objet de transfert de données (DTO) pour les requêtes de travail.
 * Elle contient des informations essentielles sur une requête, comme son titre, sa description,
 * son type, le quartier concerné, et la date de début souhaitée (sous forme de chaîne).
 * Les dates sont gérées en tant que chaînes mais peuvent être converties en LocalDate
 * avec gestion des erreurs de formatage.
 */
package org.udem.ift2255.dto;

import java.time.LocalDate;            // Représentation des dates sans fuseau horaire
import java.time.format.DateTimeParseException; // Gestion des exceptions lors du parsing des dates

/**
 * WorkRequestDTO
 *
 * Utilisée pour transférer les données des requêtes de travail entre
 * les différentes couches de l'application.
 */
public class WorkRequestDTO {

    // Identifiant unique de la requête
    private Long id;

    // Titre des travaux
    private String workTitle;

    // Description détaillée des travaux
    private String detailedWorkDescription;

    // Quartier concerné par la requête
    private String quartier;

    // Type de travaux (exemple : "Construction", "Paysagisme")
    private String workType;

    // Date de début souhaitée pour les travaux (sous forme de chaîne)
    private String workWishedStartDate;  // String type, parsed as LocalDate in the getter

    /**
     * Constructeur avec tous les paramètres.
     *
     * @param id                      Identifiant de la requête.
     * @param workTitle               Titre des travaux.
     * @param detailedWorkDescription Description détaillée des travaux.
     * @param quartier                Quartier concerné.
     * @param workType                Type de travaux.
     * @param workWishedStartDate     Date de début souhaitée au format String.
     */
    public WorkRequestDTO(Long id, String workTitle, String detailedWorkDescription,
                          String quartier, String workType, String workWishedStartDate) {
        this.id = id;
        this.workTitle = workTitle;
        this.detailedWorkDescription = detailedWorkDescription;
        this.quartier = quartier;
        this.workType = workType;
        this.workWishedStartDate = workWishedStartDate;
    }

    /**
     * Constructeur sans arguments pour la désérialisation.
     */
    public WorkRequestDTO() {}

    /**
     * Retourne l'identifiant de la requête.
     *
     * @return L'ID de la requête sous forme de Long.
     */
    public Long getId() {
        return id;  // Return the ID as a Long
    }

    /**
     * Définit l'identifiant de la requête.
     *
     * @param id L'identifiant unique de la requête.
     */
    public void setId(Long id) {
        this.id = id;  // Set the ID
    }

    /**
     * Retourne le titre des travaux.
     *
     * @return Le titre des travaux sous forme de chaîne.
     */
    public String getWorkTitle() {
        return workTitle;
    }

    /**
     * Définit le titre des travaux.
     *
     * @param workTitle Le titre des travaux.
     */
    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    /**
     * Retourne la description détaillée des travaux.
     *
     * @return La description des travaux sous forme de chaîne.
     */
    public String getDetailedWorkDescription() {
        return detailedWorkDescription;
    }

    /**
     * Définit la description détaillée des travaux.
     *
     * @param detailedWorkDescription La description des travaux à définir.
     */
    public void setDetailedWorkDescription(String detailedWorkDescription) {
        this.detailedWorkDescription = detailedWorkDescription;
    }

    /**
     * Retourne le quartier concerné par la requête.
     *
     * @return Le quartier sous forme de chaîne.
     */
    public String getQuartier() {
        return quartier;
    }

    /**
     * Définit le quartier concerné par la requête.
     *
     * @param quartier Le quartier à définir.
     */
    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    /**
     * Retourne le type de travaux.
     *
     * @return Le type de travaux sous forme de chaîne.
     */
    public String getWorkType() {
        return workType;
    }

    /**
     * Définit le type de travaux.
     *
     * @param workType Le type de travaux à définir.
     */
    public void setWorkType(String workType) {
        this.workType = workType;
    }

    /**
     * Retourne la date de début souhaitée sous forme de LocalDate.
     * Cette méthode tente de parser la chaîne de caractères en LocalDate
     * et lève une exception en cas de format invalide.
     *
     * @return La date de début souhaitée sous forme de LocalDate.
     * @throws IllegalArgumentException Si le format de la date est invalide.
     */
    public LocalDate getWorkWishedStartDate() {
        // Error handling when parsing the date
        try {
            return LocalDate.parse(workWishedStartDate);  // Parsing the String to LocalDate
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + workWishedStartDate, e);
        }
    }

    /**
     * Définit la date de début souhaitée sous forme de chaîne.
     *
     * @param workWishedStartDate La date de début souhaitée au format String.
     */
    public void setWorkWishedStartDate(String workWishedStartDate) {
        this.workWishedStartDate = workWishedStartDate;
    }
}
