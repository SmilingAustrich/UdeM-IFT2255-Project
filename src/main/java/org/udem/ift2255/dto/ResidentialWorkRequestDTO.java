/**
 * Classe ResidentialWorkRequestDTO
 *
 * Cette classe représente un objet de transfert de données (DTO) pour les requêtes
 * de travaux résidentiels. Elle encapsule les informations nécessaires, telles que
 * le titre des travaux, leur description, le type de travaux, le quartier concerné,
 * et la date de début souhaitée.
 */
package org.udem.ift2255.dto;

import java.time.LocalDate; // Permet de manipuler les dates sans fuseau horaire

/**
 * ResidentialWorkRequestDTO
 *
 * Cette classe est utilisée pour transférer les données des requêtes de travaux
 * résidentiels entre les différentes couches de l'application.
 */
public class ResidentialWorkRequestDTO {

    // Identifiant unique de la requête de travail
    private Long id;

    // Titre des travaux
    private String workTitle;

    // Description détaillée des travaux
    private String detailedWorkDescription;

    // Type de travaux (par exemple : "Construction", "Paysagisme", etc.)
    private String workType;

    // Quartier où les travaux doivent être réalisés
    private String neighbourhood;

    // Date souhaitée pour le début des travaux
    private LocalDate workWishedStartDate;

    /**
     * Retourne le titre des travaux.
     *
     * @return Le titre des travaux sous forme de chaîne de caractères.
     */
    public String getWorkTitle() {
        return workTitle;
    }

    /**
     * Définit le titre des travaux.
     *
     * @param workTitle Le titre des travaux à définir.
     */
    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    /**
     * Retourne la description détaillée des travaux.
     *
     * @return La description des travaux sous forme de chaîne de caractères.
     */
    public String getDetailedWorkDescription() {
        return detailedWorkDescription;
    }

    /**
     * Définit la description détaillée des travaux.
     *
     * @param detailedWorkDescription La description à définir.
     */
    public void setDetailedWorkDescription(String detailedWorkDescription) {
        this.detailedWorkDescription = detailedWorkDescription;
    }

    /**
     * Retourne le type de travaux.
     *
     * @return Le type de travaux sous forme de chaîne de caractères.
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
     * Retourne le quartier concerné par les travaux.
     *
     * @return Le nom du quartier sous forme de chaîne de caractères.
     */
    public String getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * Définit le quartier concerné par les travaux.
     *
     * @param neighbourhood Le nom du quartier à définir.
     */
    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    /**
     * Retourne la date souhaitée pour le début des travaux.
     *
     * @return La date de début souhaitée sous forme de LocalDate.
     */
    public LocalDate getWorkWishedStartDate() {
        return workWishedStartDate;
    }

    /**
     * Définit la date souhaitée pour le début des travaux.
     *
     * @param workWishedStartDate La date de début souhaitée à définir.
     */
    public void setWorkWishedStartDate(LocalDate workWishedStartDate) {
        this.workWishedStartDate = workWishedStartDate;
    }

    /**
     * Définit l'identifiant de la requête de travail.
     *
     * @param id L'identifiant unique à définir.
     */
    public void setId(Long id) {
        this.id = id;
    }
}
