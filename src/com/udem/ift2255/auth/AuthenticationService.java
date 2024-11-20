package com.udem.ift2255.auth;

import com.udem.ift2255.database.Database;
import com.udem.ift2255.model.*;

public class AuthenticationService {

    /**
     * Inscrit un nouveau résident dans l'application.
     * Si l'email du résident existe déjà, un message d'erreur est affiché.
     *
     * @param resident Le résident à inscrire
     */
    public static void signUpResident(Resident resident) {
        if (Database.getResidentMap().containsKey(resident.getEmail())) {
            System.out.println("Un résident avec cet email existe déjà.");
        } else {
            Database.getResidentMap().put(resident.getEmail(), resident); // Ajouter le résident à la map
            System.out.println("\nInscription réussie pour le résident : " + resident.getFirstName());
        }
    }

    /**
     * Inscrit un nouvel intervenant dans l'application.
     * Si l'email de l'intervenant existe déjà, un message d'erreur est affiché.
     *
     * @param intervenant L'intervenant à inscrire
     */
    public static void signUpIntervenant(Intervenant intervenant) {
        if (Database.getIntervenantMap().containsKey(intervenant.getEmail())) {
            System.out.println("Un intervenant avec cet email existe déjà.");
        } else {
            Database.getIntervenantMap().put(intervenant.getEmail(), intervenant); // Ajouter l'intervenant à la map
            System.out.println("\nInscription réussie pour l'intervenant : " + intervenant.getFirstName());
        }
    }

    /**
     * Vérifie les informations de connexion d'un résident.
     * Si l'email et le mot de passe correspondent, la connexion est validée.
     *
     * @param email    L'adresse email du résident
     * @param password Le mot de passe du résident
     * @return {@code true} si la connexion est réussie, {@code false} sinon
     */
    public static boolean loginResident(String email, String password) {
        Resident resident = Database.getResidentMap().get(email); // Récupérer le résident par email

        if (resident != null && resident.getPassword().equals(password)) {
            return true; // Connexion réussie
        } else {
            return false; // Connexion échouée
        }
    }

    /**
     * Vérifie les informations de connexion d'un intervenant.
     * Si l'email et le mot de passe correspondent, la connexion est validée.
     *
     * @param email    L'adresse email de l'intervenant
     * @param password Le mot de passe de l'intervenant
     * @return {@code true} si la connexion est réussie, {@code false} sinon
     */
    public static boolean loginIntervenant(String email, String password) {
        Intervenant intervenant = Database.getIntervenantMap().get(email); // Récupérer l'intervenant par email

        if (intervenant != null && intervenant.getPassword().equals(password)) {
            return true; // Connexion réussie
        } else {
            return false; // Connexion échouée
        }
    }

}