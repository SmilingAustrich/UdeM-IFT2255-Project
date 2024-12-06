package org.udem.ift2255.model;

/**
 * L'interface {@code com.udem.ift2255.model.User} définit les méthodes essentielles pour obtenir les informations d'un utilisateur,
 * telles que le mot de passe, l'email, le prénom et le nom de famille.
 */
public interface User{

    /**
     * Retourne le mot de passe de l'utilisateur.
     *
     * @return le mot de passe de l'utilisateur
     */
    String getPassword();

    /**
     * Retourne l'adresse email de l'utilisateur.
     *
     * @return l'adresse email de l'utilisateur
     */
    String getEmail();

    /**
     * Retourne le prénom de l'utilisateur.
     *
     * @return le prénom de l'utilisateur
     */
    String getFirstName();

    /**
     * Retourne le nom de famille de l'utilisateur.
     *
     * @return le nom de famille de l'utilisateur
     */
    String getLastName();

}
