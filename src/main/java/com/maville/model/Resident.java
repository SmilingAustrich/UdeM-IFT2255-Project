package com.maville.model;

import com.maville.database.Database;

import java.io.Serializable;

/**
 * A resident of the city: someone who can look up road work and open a request
 * for work on their own property.
 *
 * <p>This class used to be 780 lines. Alongside the seven fields below it held
 * the screens a resident could reach, the HTTP client for the city's open data
 * portal, the JSON parsing, the console rendering and the {@code Scanner} reads,
 * which is why a change to a menu label meant editing the class that models a
 * person. Those moved to {@code com.maville.ui} and {@code com.maville.data}.
 */
public class Resident implements User, Serializable {

    private static final long serialVersionUID = 1L;

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String phone;
    private final String address;
    private final int age;

    /**
     * @param firstName the resident's first name
     * @param lastName  the resident's family name
     * @param email     the address they sign in with
     * @param password  their password
     * @param phone     their phone number, optional
     * @param address   where they live
     * @param age       their age in completed years
     */
    public Resident(String firstName, String lastName, String email, String password,
                    String phone, String address, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.age = age;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    /**
     * Opens a work request against this resident's property.
     *
     * <p>Kept on the resident rather than on the console screen, because it is
     * the thing a resident does rather than the way it happens to be asked for.
     */
    public void creerRequete(String workTitle, String detailedWorkDescription, String workType,
                             java.time.LocalDate workWishedStartDate, String quartier) {
        ResidentialWorkRequest request = new ResidentialWorkRequest(
                this, workTitle, detailedWorkDescription, workType, workWishedStartDate, quartier);
        Database.getResidentialWorkMap().put(this, request);
    }

    /**
     * Closes a request once the work is no longer on offer.
     *
     * <p>The removal used to be keyed by {@code getEmail()} against a map keyed
     * by {@code Resident}, so it silently removed nothing and the request stayed
     * open. It is keyed by the resident now, which is what the map holds.
     */
    public void fermerRequete(ResidentialWorkRequest requete) {
        if (!requete.isWorkAvailable()) {
            Database.getResidentialWorkMap().remove(this);
        }
    }
}
