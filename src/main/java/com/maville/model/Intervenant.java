package com.maville.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * A contractor: someone accredited by the city who can bid on residents'
 * work requests and submit projects of their own.
 *
 * <p>Like {@code Resident}, this class used to carry its own screens. What is
 * left is the contractor and the four things one can do to a work request.
 */
public class Intervenant implements User, Serializable {

    private static final long serialVersionUID = 1L;

    /** Boroughs a project may be filed against. */
    public static final List<String> QUARTIERS =
            List.of("Plateau", "Rosemont", "Ville-Marie", "Outremont", "Hochelaga");

    /** Streets a project may be filed against. */
    public static final List<String> RUES = List.of(
            "Rue Saint-Denis", "Rue Sherbrooke", "Avenue du Parc",
            "Boulevard Saint-Laurent", "Rue Sainte-Catherine");

    /** The hours residents of each borough have asked work to be done in. */
    private static final Map<String, String> PREFERENCES_HORAIRES = Map.of(
            "Plateau", "Matin (8h-12h)",
            "Rosemont", "Après-midi (12h-16h)",
            "Ville-Marie", "Soirée (16h-20h)",
            "Outremont", "Matin (8h-12h)",
            "Hochelaga", "Après-midi (12h-16h)");

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String cityIdCode;
    private final int entrepreneurType;

    /**
     * @param cityIdCode       the eight digit accreditation number issued by the city
     * @param entrepreneurType private company, public company or sole trader
     */
    public Intervenant(String firstName, String lastName, String email, String password,
                       String cityIdCode, int entrepreneurType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.cityIdCode = cityIdCode;
        this.entrepreneurType = entrepreneurType;
    }

    /** The hours residents of {@code quartier} prefer, or a default. */
    public static String preferenceHoraire(String quartier) {
        return PREFERENCES_HORAIRES.getOrDefault(quartier, "Pas de préférence spécifique");
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

    public int getEntrepreneurType() {
        return entrepreneurType;
    }

    public String getCityIdCode() {
        return cityIdCode;
    }

    /** Applies for a request, if it is still open. */
    public void soumettreCandidature(ResidentialWorkRequest requete, String message) {
        if (requete.isWorkAvailable()) {
            requete.ajouterCandidature(this, message);
        } else {
            System.out.println("La requête n'est plus disponible.");
        }
    }

    /** Withdraws an application. */
    public void retirerCandidature(ResidentialWorkRequest requete) {
        if (requete.getCandidatures().containsKey(this)) {
            requete.getCandidatures().remove(this);
            System.out.println("Candidature retirée par l'intervenant " + firstName);
        } else {
            System.out.println("Aucune candidature à retirer pour cet intervenant.");
        }
    }

    /** Confirms an application on a request that had been taken off the market. */
    public void confirmerCandidature(ResidentialWorkRequest requete) {
        if (!requete.isWorkAvailable()) {
            requete.rendreDisponible();
            System.out.println("Soumission de la candidature confirmée par l'intervenant " + firstName);
        } else {
            System.out.println("La requête est déjà disponible.");
        }
    }
}
