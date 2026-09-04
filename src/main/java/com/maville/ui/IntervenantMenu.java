package com.maville.ui;

import com.maville.database.Database;
import com.maville.model.Intervenant;
import com.maville.model.ResidentialWorkRequest;
import com.maville.ui.console.Ansi;
import com.maville.ui.console.ConsolePrompt;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Every screen a signed-in contractor can reach.
 *
 * <p>Moved off {@code Intervenant} for the same reason the resident screens
 * moved off {@code Resident}: the class modelling a contractor was also drawing
 * menus and reading the keyboard. Its main menu recursed into itself through
 * the screens it launched; that is a loop here.
 */
public final class IntervenantMenu {

    private final Intervenant intervenant;
    private final ConsolePrompt prompt;

    public IntervenantMenu(Intervenant intervenant, ConsolePrompt prompt) {
        this.intervenant = intervenant;
        this.prompt = prompt;
    }

    public void show() {
        while (true) {
            System.out.print(
                    Ansi.blue("\n★**********************************************************************★") + "\n"
                            + Ansi.green("  Bienvenue, " + intervenant.getFirstName() + "!") + "\n"
                            + Ansi.cyan("  Vous êtes sur le menu principal des intervenants de l'application Ma Ville.") + "\n"
                            + Ansi.cyan("  Veuillez choisir une option dans la liste suivante :") + "\n"
                            + Ansi.white("    1. Consulter la liste des requêtes de travail.") + "\n"
                            + Ansi.white("    2. Soumettre un nouveau projet de travaux.") + "\n"
                            + Ansi.white("    3. Proposer des plages horaires pour des travaux.") + "\n"
                            + Ansi.white("    4. Se déconnecter.") + "\n"
                            + Ansi.blue("★**********************************************************************★") + "\n"
                            + Ansi.yellow("\n Insérer le numéro qui correspond à votre choix >: "));

            int choice = prompt.number("");
            System.out.println("\n" + Ansi.blue("★**********************************************************************★") + "\n");

            switch (choice) {
                case 1 -> consulterRequetes();
                case 2 -> soumettreProjet();
                case 3 -> proposerPlageHoraire();
                case 4 -> {
                    System.out.println(Ansi.yellow("Au revoir, " + intervenant.getFirstName() + "!"));
                    return;
                }
                default -> System.out.println(Ansi.error("Choix invalide. Veuillez réessayer."));
            }
        }
    }

    /** Lists open work requests, filtered, then offers to apply for one. */
    private void consulterRequetes() {
        Map<com.maville.model.Resident, ResidentialWorkRequest> all = Database.getResidentialWorkMap();

        System.out.println(Ansi.banner("FILTRER LES REQUÊTES DE TRAVAIL"));
        System.out.println(Ansi.option(1, "Filtrer par type de travaux"));
        System.out.println(Ansi.option(2, "Filtrer par quartier"));
        System.out.println(Ansi.option(3, "Filtrer par date de début"));
        System.out.println(Ansi.option(4, "Afficher toutes les requêtes"));

        List<ResidentialWorkRequest> requests = List.copyOf(all.values());

        switch (prompt.number(Ansi.GREEN + "Veuillez choisir une option >: " + Ansi.RESET)) {
            case 1 -> {
                String type = prompt.required(Ansi.GREEN + "Veuillez entrer le type de travaux: " + Ansi.RESET);
                requests = requests.stream().filter(r -> r.getWorkType().equalsIgnoreCase(type)).toList();
            }
            case 2 -> {
                String borough = prompt.required(Ansi.GREEN + "Veuillez entrer le quartier: " + Ansi.RESET);
                requests = requests.stream().filter(r -> r.getNeighbourhood().equalsIgnoreCase(borough)).toList();
            }
            case 3 -> {
                Optional<LocalDate> from = ConsolePrompt.parseDate(prompt.line(
                        Ansi.GREEN + "Veuillez entrer la date de début (format: JJ/MM/AAAA): " + Ansi.RESET));
                if (from.isEmpty()) {
                    System.out.println(Ansi.error("Format de date invalide. Aucune requête filtrée."));
                } else {
                    requests = requests.stream().filter(r -> !r.getStartDate().isBefore(from.get())).toList();
                }
            }
            case 4 -> {
            }
            default -> System.out.println(Ansi.error("Option invalide. Affichage de toutes les requêtes."));
        }

        WorkView.requestList("LISTE DES REQUÊTES DE TRAVAUX", requests);
        offrirCandidature(requests);
    }

    private void offrirCandidature(List<ResidentialWorkRequest> requests) {
        if (requests.isEmpty()) {
            return;
        }
        System.out.println(Ansi.option(1, "Soumettre une candidature"));
        System.out.println(Ansi.option(2, "Retourner au menu principal"));
        if (prompt.number(Ansi.GREEN + "Choisissez une option >: " + Ansi.RESET) != 1) {
            return;
        }

        String title = prompt.required(Ansi.GREEN + "Veuillez entrer le titre de la requête: " + Ansi.RESET);
        Optional<ResidentialWorkRequest> chosen = requests.stream()
                .filter(r -> r.getTitle().equalsIgnoreCase(title))
                .findFirst();

        if (chosen.isEmpty()) {
            System.out.println(Ansi.error("Requête non trouvée. Candidature non soumise."));
            return;
        }

        String message = prompt.required(Ansi.GREEN + "Votre message pour la candidature: " + Ansi.RESET);
        intervenant.soumettreCandidature(chosen.get(), message);
    }

    /** Collects a project and warns when its hours clash with the borough's stated preference. */
    private void soumettreProjet() {
        System.out.println(Ansi.banner("SOUMISSION D'UN PROJET DE TRAVAUX"));

        String title = prompt.required(Ansi.GREEN + "Titre du projet >: " + Ansi.RESET);
        String description = prompt.required(Ansi.GREEN + "Description du projet >: " + Ansi.RESET);
        String workType = prompt.required(Ansi.GREEN + "Type de travaux >: " + Ansi.RESET);

        System.out.println(Ansi.cyan("Quartiers disponibles : " + Intervenant.QUARTIERS));
        String borough = prompt.required(Ansi.GREEN + "Quartier affecté >: " + Ansi.RESET);
        if (!Intervenant.QUARTIERS.contains(borough)) {
            System.out.println(Ansi.error("Quartier invalide. Retour au menu principal."));
            return;
        }

        System.out.println(Ansi.cyan("Rues disponibles : " + Intervenant.RUES));
        String street = prompt.required(Ansi.GREEN + "Rue affectée >: " + Ansi.RESET);
        if (!Intervenant.RUES.contains(street)) {
            System.out.println(Ansi.error("Rue invalide. Retour au menu principal."));
            return;
        }

        LocalDate start = prompt.futureDate(Ansi.GREEN + "Date de début (format jj/mm/aaaa) >: " + Ansi.RESET);
        LocalDate end = prompt.futureDate(Ansi.GREEN + "Date de fin (format jj/mm/aaaa) >: " + Ansi.RESET);
        String hours = prompt.required(Ansi.GREEN + "Horaire des travaux (ex: 8h-12h) >: " + Ansi.RESET);

        String preference = Intervenant.preferenceHoraire(borough);
        System.out.println(Ansi.info(
                "Les préférences des résidents pour " + borough + " sont : " + preference));

        if (!preference.equalsIgnoreCase(hours)) {
            String answer = prompt.line(Ansi.YELLOW
                    + "Conflit avec les préférences des résidents. Continuer malgré tout ? (oui/non) >: "
                    + Ansi.RESET);
            if (!answer.equalsIgnoreCase("oui")) {
                System.out.println(Ansi.yellow("Projet annulé. Retour au menu principal."));
                return;
            }
        }

        System.out.println(Ansi.green("\nProjet soumis avec succès."));
        System.out.println(Ansi.field("Titre", title));
        System.out.println(Ansi.field("Description", description));
        System.out.println(Ansi.field("Type de travaux", workType));
        System.out.println(Ansi.field("Quartier affecté", borough));
        System.out.println(Ansi.field("Rue affectée", street));
        System.out.println(Ansi.field("Date de début", start.format(ConsolePrompt.DATE)));
        System.out.println(Ansi.field("Date de fin", end.format(ConsolePrompt.DATE)));
        System.out.println(Ansi.field("Horaire des travaux", hours));
    }

    private void proposerPlageHoraire() {
        System.out.println(Ansi.cyan("Veuillez proposer une plage horaire pour les travaux."));
        LocalDate date = prompt.futureDate(Ansi.GREEN + "Date (format JJ/MM/AAAA) >: " + Ansi.RESET);
        String from = prompt.required(Ansi.GREEN + "Heure de début (format HH:MM) >: " + Ansi.RESET);
        String to = prompt.required(Ansi.GREEN + "Heure de fin (format HH:MM) >: " + Ansi.RESET);

        System.out.println(Ansi.info("Plage horaire proposée : "
                + date.format(ConsolePrompt.DATE) + " de " + from + " à " + to));
    }
}
