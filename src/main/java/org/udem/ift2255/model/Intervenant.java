package org.udem.ift2255.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

/**
 * La classe {@code org.udem.ift2255.model.Intervenant} représente un utilisateur de type intervenant dans l'application Ma Ville.
 * Un intervenant est un professionnel qui peut soumettre des projets de travaux et consulter des requêtes.
 */
@Entity
@Table(name = "intervenant")
public class Intervenant extends PanacheEntity implements User {



    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "cityIdCode", nullable = false)
    private String cityIdCode;

    @Column(name = "entrepreneurType", nullable = false)
    private int entrepreneurType;

    // Optional: One-to-many relationship with ResidentialWorkRequest if an intervenant can have many requests
    @OneToMany(mappedBy = "chosenIntervenant")
    private List<ResidentialWorkRequest> workRequests;

    /**
     * Constructeur de la classe {@code org.udem.ift2255.model.Intervenant}.
     *
     * @param firstName        Le prénom de l'intervenant
     * @param lastName         Le nom de famille de l'intervenant
     * @param email            L'adresse email de l'intervenant
     * @param password         Le mot de passe de l'intervenant
     * @param cityIdCode       Le code d'identification de la ville (à 8 chiffres)
     * @param entrepreneurType Le type d'entrepreneur (privé, public, particulier)
     */
    public Intervenant(String firstName, String lastName, String email, String password, String cityIdCode, int entrepreneurType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.cityIdCode = cityIdCode;
        this.entrepreneurType = entrepreneurType;
    }

    public Intervenant() {
    }

    /**
     * Retourne le prénom de l'intervenant.
     *
     * @return le prénom de l'intervenant
     */
    @Override
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retourne le nom de famille de l'intervenant.
     *
     * @return le nom de famille de l'intervenant
     */
    @Override
    public String getLastName() {
        return lastName;
    }

    /**
     * Retourne l'adresse email de l'intervenant.
     *
     * @return l'adresse email de l'intervenant
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * Retourne le mot de passe de l'intervenant.
     *
     * @return le mot de passe de l'intervenant
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Retourne le type d'entrepreneur de l'intervenant.
     *
     * @return le type d'entrepreneur (numérique)
     */
    public int getEntrepreneurType() {
        return entrepreneurType;
    }

    /**
     * Retourne le code d'identification de la ville pour l'intervenant.
     *
     * @return le code d'identification de la ville
     */
    public String getCityIdCode() {
        return cityIdCode;
    }


    // Optional: Add a method to get the work requests associated with the intervenant
    public List<ResidentialWorkRequest> getWorkRequests() {
        return workRequests;
    }

    public void setWorkRequests(List<ResidentialWorkRequest> workRequests) {
        this.workRequests = workRequests;
    }

    public void setPassword(String s) {
        this.password = s;
    }

    public Long getId() {
        return id;
    }
}
