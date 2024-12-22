/**
 * Classe LoginRequestDTO
 *
 * Cette classe représente un objet de transfert de données (DTO) utilisé
 * pour capturer les informations de connexion fournies par un utilisateur
 * lors de l'authentification. Elle contient les champs nécessaires,
 * comme l'email et le mot de passe.
 */
package org.udem.ift2255.dto;

import com.fasterxml.jackson.annotation.JsonProperty; // Annotation pour lier les propriétés JSON aux champs Java

/**
 * LoginRequestDTO
 *
 * Les annotations @JsonProperty permettent de mapper les clés JSON
 * aux attributs correspondants dans cette classe lors de la désérialisation.
 */
public class LoginRequestDTO {

    // Champ pour capturer l'email de l'utilisateur
    @JsonProperty("email")
    private String email;

    // Champ pour capturer le mot de passe de l'utilisateur
    @JsonProperty("password")
    private String password;

    /**
     * Retourne l'email de l'utilisateur.
     *
     * @return L'email de l'utilisateur sous forme de chaîne de caractères.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'email de l'utilisateur.
     *
     * @param email L'email de l'utilisateur à définir.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne le mot de passe de l'utilisateur.
     *
     * @return Le mot de passe de l'utilisateur sous forme de chaîne de caractères.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe de l'utilisateur.
     *
     * @param password Le mot de passe de l'utilisateur à définir.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
