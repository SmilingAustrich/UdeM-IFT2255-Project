package org.udem.ift2255.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

/**
 * La classe {@code Resident} représente un résident dans l'application Ma Ville.
 */
@Entity
@Table(name = "resident")
public class Resident extends PanacheEntity implements User {



    @OneToOne
    @JoinColumn(name = "requete_id")
    private ResidentialWorkRequest requete;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false)
    private String phone;


    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "age", nullable = false)
    private int age;

    public Resident(String firstName, String lastName, String email, String password, String phone, String address, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.age = age;
    }
    public Resident() {}
        // No-argument constructor for PanacheEntity

        // Getters and Setters (or you can use Lombok to auto-generate them)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ResidentialWorkRequest getRequete() {
        return requete;
    }

    public void setRequete(ResidentialWorkRequest requete) {
        this.requete = requete;
    }


}
