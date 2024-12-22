/**
 * Classe UserDTO
 *
 * Cette classe représente un objet de transfert de données (DTO) pour les utilisateurs
 * de l'application. Elle contient les informations nécessaires pour distinguer
 * les utilisateurs de type "resident" et "intervenant" ainsi que leurs attributs spécifiques.
 */
package org.udem.ift2255.dto;

/**
 * UserDTO
 *
 * Cette classe est utilisée pour transférer les informations des utilisateurs
 * entre les différentes couches de l'application.
 * Elle contient des champs communs aux deux types d'utilisateurs
 * ("resident" et "intervenant") ainsi que des champs spécifiques à chaque type.
 */
public class UserDTO {

    // Type de l'utilisateur : peut être "resident" ou "intervenant"
    public String userType;

    // Prénom de l'utilisateur (pour les deux types)
    public String firstName;

    // Nom de famille de l'utilisateur (ajouté pour séparer le nom complet)
    public String lastName;

    // Adresse email de l'utilisateur (unique)
    public String email;

    // Mot de passe de l'utilisateur
    public String password;

    // Numéro de téléphone (spécifique aux résidents)
    public String phone;

    // Adresse résidentielle (spécifique aux résidents)
    public String address;

    // Âge de l'utilisateur (spécifique aux résidents)
    public int age;

    // Type d'intervenant (spécifique aux intervenants, ex: "Plumbing", "Electricity")
    public String type;

    // Identifiant de la ville (spécifique aux intervenants)
    public String cityId;
}
