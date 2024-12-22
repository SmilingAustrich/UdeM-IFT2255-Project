/**
 * Classe Resident
 *
 * Cette classe représente un résident dans l'application Ma Ville.
 * Un résident peut soumettre des requêtes de travail, consulter les travaux en cours
 * et gérer ses informations personnelles.
 */
package org.udem.ift2255.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity; // Simplifie les interactions avec la base de données
import jakarta.persistence.*;                         // Fournit les annotations JPA pour le mapping des entités

/**
 * Entité Resident
 *
 * Cette entité est mappée à une table de base de données nommée "resident".
 * Elle contient les informations personnelles et les préférences du résident,
 * ainsi que ses relations avec d'autres entités, comme les requêtes de travail.
 */
@Entity
@Table(name = "resident") // Définit le nom de la table associée en base de données
public class Resident extends PanacheEntity implements User {

    // Quartier de résidence du résident
    @Column(name = "neighbourhood", nullable = false)
    private String neighbourhood;

    // Heures préférées pour les travaux (optionnel)
    @Column(name = "preferred_hours")
    private String preferredHours;

    // Relation One-to-One avec une requête de travail résidentiel
    @OneToOne
    @JoinColumn(name = "requete_id")
    private ResidentialWorkRequest requete;

    // Prénom du résident
    @Column(name = "first_name", nullable = false)
    private String firstName;

    // Nom de famille du résident
    @Column(name = "last_name", nullable = false)
    private String lastName;

    // Adresse email unique du résident
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Mot de passe du résident
    @Column(name = "password", nullable = false)
    private String password;

    // Numéro de téléphone du résident
    @Column(name = "phone", nullable = false)
    private String phone;

    // Adresse physique du résident
    @Column(name = "address", nullable = false)
    private String address;

    // Âge du résident
    @Column(name = "age", nullable = false)
    private int age;

    /**
     * Constructeur avec paramètres.
     *
     * @param firstName    Le prénom du résident.
     * @param lastName     Le nom de famille du résident.
     * @param email        L'adresse email du résident.
     * @param password     Le mot de passe du résident.
     * @param phone        Le numéro de téléphone du résident.
     * @param address      L'adresse physique du résident.
     * @param age          L'âge du résident.
     * @param neighbourhood Le quartier du résident.
     */
    public Resident(String firstName, String lastName, String email, String password, String phone, String address, int age, String neighbourhood) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.age = age;
        this.neighbourhood = neighbourhood;
    }

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Resident() {}

    /**
     * Retourne le prénom du résident.
     *
     * @return Le prénom du résident.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Définit le prénom du résident.
     *
     * @param firstName Le prénom à définir.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retourne le nom de famille du résident.
     *
     * @return Le nom de famille du résident.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Définit le nom de famille du résident.
     *
     * @param lastName Le nom de famille à définir.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retourne l'adresse email du résident.
     *
     * @return L'adresse email du résident.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'adresse email du résident.
     *
     * @param email L'adresse email à définir.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne le mot de passe du résident.
     *
     * @return Le mot de passe du résident.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe du résident.
     *
     * @param password Le mot de passe à définir.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retourne le numéro de téléphone du résident.
     *
     * @return Le numéro de téléphone du résident.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Définit le numéro de téléphone du résident.
     *
     * @param phone Le numéro de téléphone à définir.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Retourne l'adresse physique du résident.
     *
     * @return L'adresse du résident.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Définit l'adresse physique du résident.
     *
     * @param address L'adresse à définir.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retourne l'âge du résident.
     *
     * @return L'âge du résident.
     */
    public int getAge() {
        return age;
    }

    /**
     * Définit l'âge du résident.
     *
     * @param age L'âge à définir.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Retourne la requête de travail associée au résident.
     *
     * @return La requête de travail résidentielle.
     */
    public ResidentialWorkRequest getRequete() {
        return requete;
    }

    /**
     * Définit la requête de travail associée au résident.
     *
     * @param requete La requête à associer.
     */
    public void setRequete(ResidentialWorkRequest requete) {
        this.requete = requete;
    }

    /**
     * Retourne les heures préférées pour les travaux.
     *
     * @return Les heures préférées sous forme de chaîne.
     */
    public String getPreferredHours() {
        return preferredHours;
    }

    /**
     * Définit les heures préférées pour les travaux.
     *
     * @param preferredHours Les heures préférées à définir.
     */
    public void setPreferredHours(String preferredHours) {
        this.preferredHours = preferredHours;
    }

    /**
     * Retourne le quartier du résident.
     *
     * @return Le quartier du résident.
     */
    public String getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * Définit le quartier du résident.
     *
     * @param neighbourhood Le quartier à définir.
     */
    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }
}
