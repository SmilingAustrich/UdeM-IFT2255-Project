/**
 * Classe TimeSlot
 *
 * Cette classe représente une plage horaire avec un jour, une heure de début,
 * et une heure de fin. Elle est utilisée pour définir des créneaux horaires
 * dans l'application.
 */
package org.udem.ift2255.model;

/**
 * TimeSlot
 *
 * Cette classe encapsule les informations d'un créneau horaire,
 * telles que le jour, l'heure de début et l'heure de fin.
 * Elle inclut des constructeurs pour l'initialisation et des getters
 * et setters pour accéder et modifier les données.
 */
public class TimeSlot {

    // Jour de la semaine associé à la plage horaire
    private String day;

    // Heure de début de la plage horaire (format attendu : HH:mm)
    private String startTime;

    // Heure de fin de la plage horaire (format attendu : HH:mm)
    private String endTime;

    /**
     * Constructeur avec paramètres.
     *
     * @param day       Le jour de la semaine (exemple : "Lundi").
     * @param startTime L'heure de début de la plage horaire (exemple : "08:00").
     * @param endTime   L'heure de fin de la plage horaire (exemple : "17:00").
     */
    public TimeSlot(String day, String startTime, String endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Constructeur par défaut requis pour la désérialisation JSON.
     */
    public TimeSlot() {
    }

    /**
     * Retourne le jour de la plage horaire.
     *
     * @return Le jour de la semaine sous forme de chaîne.
     */
    public String getDay() {
        return day;
    }

    /**
     * Définit le jour de la plage horaire.
     *
     * @param day Le jour de la semaine à définir (exemple : "Mardi").
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * Retourne l'heure de début de la plage horaire.
     *
     * @return L'heure de début sous forme de chaîne (format : HH:mm).
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Définit l'heure de début de la plage horaire.
     *
     * @param startTime L'heure de début à définir (format : HH:mm).
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Retourne l'heure de fin de la plage horaire.
     *
     * @return L'heure de fin sous forme de chaîne (format : HH:mm).
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Définit l'heure de fin de la plage horaire.
     *
     * @param endTime L'heure de fin à définir (format : HH:mm).
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
