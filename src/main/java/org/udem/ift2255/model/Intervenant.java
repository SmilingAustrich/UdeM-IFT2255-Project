/**
 * Classe Intervenant
 *
 * Cette classe représente un utilisateur de type intervenant dans l'application Ma Ville.
 * Un intervenant est un professionnel qui peut soumettre des projets de travaux, consulter des requêtes
 * et gérer des informations spécifiques comme le type d'entrepreneur et le code d'identification de la ville.
 * Elle est mappée à une table de base de données nommée "intervenant".
 */
package org.udem.ift2255.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity; // Fournit des fonctionnalités simplifiées pour l'accès aux données avec Panache
import jakarta.persistence.*;                         // Gestion des entités et annotations JPA

import java.io.Serializable;                         // Interface pour la sérialisation des objets (utile pour les entités JPA)
import java.util.List;                               // Structure de données pour gérer une liste d'objets

/**
 * Représente un intervenant, un professionnel associé à des travaux résidentiels dans l'application.
 */
@Entity
@Table(name = "intervenant") // Définit le nom de la table en base de données
public class Intervenant extends PanacheEntity implements User {

    // Prénom de l'intervenant
    @Column(name = "first_name", nullable = false)
    private String firstName;

    // Nom de famille de l'intervenant
    @Column(name = "last_name", nullable = false)
    private String lastName;

    // Adresse email de l'intervenant (doit être unique)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Mot de passe de l'intervenant
    @Column(name = "password", nullable = false)
    private String password;

    // Code d'identification de la ville pour l'intervenant (8 chiffres)
    @Column(name = "cityIdCode", nullable = false)
    private String cityIdCode;

    // Type d'entrepreneur (exemple : privé, public, particulier)
    @Column(name = "entrepreneurType", nullable = false)
    private String entrepreneurType;

    // Relation One-to-Many avec les requêtes de travail résidentiel (facultatif)
    @OneToMany(mappedBy = "chosenIntervenant")
    private List<ResidentialWorkRequest> workRequests;

    /**
     * Constructeur de la classe Intervenant.
     *
     * @param firstName        Le prénom de l'intervenant
     * @param lastName         Le nom de famille de l'intervenant
     * @param email            L'adresse email de l'intervenant
     * @param password         Le mot de passe de l'intervenant
     * @param cityIdCode       Le code d'identification de la ville
     * @param entrepreneurType Le type d'entrepreneur
     */
    public Intervenant(String firstName, String lastName, String email, String password, String cityIdCode, String entrepreneurType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.cityIdCode = cityIdCode;
        this.entrepreneurType = entrepreneurType;
    }

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Intervenant() {
    }

    /**
     * Retourne le prénom de l'intervenant.
     *
     * @return Le prénom de l'intervenant.
     */
    @Override
    public String getFirstName() {
        return firstName;
    }

    /**
     * Définit le prénom de l'intervenant.
     *
     * @param firstName Le prénom à définir.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retourne le nom de famille de l'intervenant.
     *
     * @return Le nom de famille de l'intervenant.
     */
    @Override
    public String getLastName() {
        return lastName;
    }

    /**
     * Définit le nom de famille de l'intervenant.
     *
     * @param lastName Le nom de famille à définir.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retourne l'adresse email de l'intervenant.
     *
     * @return L'adresse email de l'intervenant.
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'adresse email de l'intervenant.
     *
     * @param email L'adresse email à définir.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne le mot de passe de l'intervenant.
     *
     * @return Le mot de passe de l'intervenant.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe de l'intervenant.
     *
     * @param password Le mot de passe à définir.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retourne le type d'entrepreneur de l'intervenant.
     *
     * @return Le type d'entrepreneur.
     */
    public String getEntrepreneurType() {
        return entrepreneurType;
    }

    /**
     * Définit le type d'entrepreneur de l'intervenant.
     *
     * @param type Le type d'entrepreneur à définir.
     */
    public void setType(String type) {
        this.entrepreneurType = type;
    }

    /**
     * Retourne le code d'identification de la ville pour l'intervenant.
     *
     * @return Le code d'identification de la ville.
     */
    public String getCityIdCode() {
        return cityIdCode;
    }

    /**
     * Définit le code d'identification de la ville pour l'intervenant.
     *
     * @param cityId Le code de la ville à définir.
     */
    public void setCityId(String cityId) {
        this.cityIdCode = cityId;
    }

    /**
     * Retourne la liste des requêtes de travail associées à l'intervenant.
     *
     * @return La liste des requêtes de travail.
     */
    public List<ResidentialWorkRequest> getWorkRequests() {
        return workRequests;
    }

    /**
     * Définit la liste des requêtes de travail associées à l'intervenant.
     *
     * @param workRequests La liste des requêtes de travail à définir.
     */
    public void setWorkRequests(List<ResidentialWorkRequest> workRequests) {
        this.workRequests = workRequests;
    }

    /**
     * Retourne l'identifiant de l'intervenant.
     *
     * @return L'identifiant unique de l'intervenant.
     */
    public Long getId() {
        return id;
    }
}
