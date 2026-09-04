package com.maville.ui;

import com.maville.data.ObstructionRecord;
import com.maville.data.OpenDataSource;
import com.maville.data.WorkRecord;
import com.maville.database.Database;
import com.maville.model.Resident;
import com.maville.model.ResidentialWorkRequest;
import com.maville.model.WorkType;
import com.maville.ui.console.Ansi;
import com.maville.ui.console.ConsolePrompt;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Every screen a signed-in resident can reach.
 *
 * <p>These flows used to be methods on {@code Resident}, which meant the class
 * holding a resident's name and address also opened HTTP connections, parsed
 * JSON, drew menus and read from {@code System.in}. Moving them here leaves
 * {@code Resident} as the thing it is named after.
 *
 * <p>One behavioural change came with the move. A screen used to return to the
 * main menu by calling {@code Menu.residentMainMenu(this)} from inside itself,
 * so the main menu called the screen and the screen called the main menu back,
 * and the stack grew for the whole session rather than unwinding. Returning to
 * the menu is now a return, and {@link #show()} loops.
 */
public final class ResidentMenu {

    private final Resident resident;
    private final ConsolePrompt prompt;
    private final OpenDataSource openData;

    public ResidentMenu(Resident resident, ConsolePrompt prompt, OpenDataSource openData) {
        this.resident = resident;
        this.prompt = prompt;
        this.openData = openData;
    }

    /** Runs until the resident signs out. */
    public void show() {
        while (true) {
            System.out.print(
                    Ansi.blue("\n★**********************************************************************★") + "\n"
                            + Ansi.green("  Bienvenue, " + resident.getFirstName() + "!") + "\n"
                            + Ansi.cyan("  Vous êtes sur le menu principal des résidents de l'application Ma Ville.") + "\n"
                            + Ansi.cyan("  Veuillez choisir une option dans la liste suivante :") + "\n"
                            + Ansi.white("    1. Consulter les travaux en cours ou à venir.") + "\n"
                            + Ansi.white("    2. Rechercher des travaux.") + "\n"
                            + Ansi.white("    3. Recevoir des notifications personnalisées.") + "\n"
                            + Ansi.white("    4. Proposer des plages horaires pour des travaux.") + "\n"
                            + Ansi.white("    5. Soumettre une requête de travail résidentiel.") + "\n"
                            + Ansi.white("    6. Consulter les entraves en cours.") + "\n"
                            + Ansi.white("    7. Suivre mes requêtes de travaux résidentiels.") + "\n"
                            + Ansi.white("    8. Se déconnecter.") + "\n"
                            + Ansi.blue("★**********************************************************************★") + "\n"
                            + Ansi.yellow("\n Insérer le numéro qui correspond à votre choix >: "));

            int choice = prompt.number("");
            System.out.println("\n" + Ansi.blue("★**********************************************************************★") + "\n");

            switch (choice) {
                case 1 -> consulterTravaux();
                case 2 -> rechercherTravaux();
                case 3 -> notifications();
                case 4 -> preferences();
                case 5 -> soumettreRequete();
                case 6 -> consulterEntraves();
                case 7 -> suivreRequetes();
                case 8 -> {
                    System.out.println(Ansi.yellow(" Déconnexion en cours..."));
                    System.out.println(Ansi.yellow("Au revoir, " + resident.getFirstName() + "!"));
                    return;
                }
                default -> System.out.println(Ansi.error("Choix invalide. Veuillez réessayer."));
            }
        }
    }

    /** Lists current road work, then offers to filter it. */
    private void consulterTravaux() {
        List<WorkRecord> works = fetchWork();
        if (works == null) {
            return;
        }
        WorkView.workList("LISTE DES TRAVAUX EN COURS", works);

        while (true) {
            System.out.println(Ansi.green("\n[OPTIONS] Voulez-vous filtrer la liste des travaux ou revenir au menu principal ?"));
            System.out.println(Ansi.option(1, "Filtrer par quartier"));
            System.out.println(Ansi.option(2, "Filtrer par type de travail"));
            System.out.println(Ansi.option(3, "Revenir au menu principal"));

            switch (prompt.number(Ansi.YELLOW + "Choisissez une option >: " + Ansi.RESET)) {
                case 1 -> {
                    String borough = prompt.required(Ansi.YELLOW + "Veuillez entrer le nom du quartier >: " + Ansi.RESET);
                    WorkView.workList("TRAVAUX DANS LE QUARTIER (" + borough + ")",
                            works.stream().filter(work -> work.isInBorough(borough)).toList());
                }
                case 2 -> {
                    WorkView.workTypeMenu();
                    String category = chooseWorkTypeLabel("");
                    WorkView.workList("TRAVAUX DE TYPE (" + category + ")",
                            works.stream().filter(work -> work.isOfCategory(category)).toList());
                }
                case 3 -> {
                    return;
                }
                default -> System.out.println(Ansi.error("Option invalide. Veuillez essayer à nouveau."));
            }
        }
    }

    /** Searches the same dataset by id, by category or by borough. */
    private void rechercherTravaux() {
        List<WorkRecord> works = fetchWork();
        if (works == null) {
            return;
        }

        while (true) {
            System.out.println(Ansi.banner("RECHERCHE DES TRAVAUX"));
            System.out.println(Ansi.option(1, "Rechercher par identifiant"));
            System.out.println(Ansi.option(2, "Rechercher par type de travaux"));
            System.out.println(Ansi.option(3, "Rechercher par quartier"));
            System.out.println(Ansi.option(4, "Revenir au menu principal"));

            switch (prompt.number(Ansi.YELLOW + "Choisissez une option: " + Ansi.RESET)) {
                case 1 -> {
                    String id = prompt.required(Ansi.YELLOW + "Entrez l'identifiant: " + Ansi.RESET);
                    WorkView.workList("TRAVAUX TROUVÉS PAR IDENTIFIANT (" + id + ")",
                            works.stream().filter(work -> work.id().contains(id)).toList());
                }
                case 2 -> {
                    WorkView.workTypeMenu();
                    String category = chooseWorkTypeLabel("");
                    WorkView.workList("TRAVAUX TROUVÉS PAR TYPE (" + category + ")",
                            works.stream().filter(work -> work.isOfCategory(category)).toList());
                }
                case 3 -> {
                    String borough = prompt.required(Ansi.YELLOW + "Entrez le quartier: " + Ansi.RESET);
                    WorkView.workList("TRAVAUX TROUVÉS PAR QUARTIER (" + borough + ")",
                            works.stream().filter(work -> work.borough().equalsIgnoreCase(borough)).toList());
                }
                case 4 -> {
                    return;
                }
                default -> System.out.println(Ansi.error("Option invalide. Veuillez essayer à nouveau."));
            }
        }
    }

    /** Lists street obstructions, filtered by work id or by street. */
    private void consulterEntraves() {
        List<ObstructionRecord> obstructions;
        try {
            obstructions = openData.fetchObstructions();
        } catch (IOException e) {
            System.out.println(Ansi.error(
                    "Impossible de consulter les entraves pour le moment: " + e.getMessage()));
            return;
        }

        while (true) {
            System.out.println(Ansi.banner("CONSULTATION DES ENTRAVES"));
            System.out.println(Ansi.option(1, "Voir toutes les entraves"));
            System.out.println(Ansi.option(2, "Rechercher par identifiant de travail"));
            System.out.println(Ansi.option(3, "Rechercher par nom de rue"));
            System.out.println(Ansi.option(4, "Revenir au menu principal"));

            switch (prompt.number(Ansi.YELLOW + "Choisissez une option: " + Ansi.RESET)) {
                case 1 -> {
                    WorkView.obstructionList("LISTE DE TOUTES LES ENTRAVES", obstructions);
                    System.out.println(Ansi.info(resident.getFirstName()
                            + ", nous sommes désolés pour toute gêne occasionnée lors des travaux."));
                }
                case 2 -> {
                    String workId = prompt.required(Ansi.MAGENTA + "Entrez l'identifiant du travail: " + Ansi.RESET);
                    WorkView.obstructionList("ENTRAVES DU TRAVAIL (" + workId + ")",
                            obstructions.stream().filter(o -> o.belongsToWork(workId)).toList());
                }
                case 3 -> {
                    String street = prompt.required(Ansi.MAGENTA + "Entrez le nom de la rue: " + Ansi.RESET);
                    WorkView.obstructionList("ENTRAVES SUR LA RUE (" + street + ")",
                            obstructions.stream().filter(o -> o.isOnStreet(street)).toList());
                }
                case 4 -> {
                    return;
                }
                default -> System.out.println(Ansi.error("Option invalide. Veuillez essayer à nouveau."));
            }
        }
    }

    private void notifications() {
        System.out.println(Ansi.info(
                "Vous êtes automatiquement abonné aux notifications pour des projets dans votre quartier: Montréal"));
        String answer = prompt.line(Ansi.YELLOW
                + "Voulez-vous également recevoir des notifications pour un autre quartier ou une rue ? (oui/non) >: "
                + Ansi.RESET);

        if (!answer.equalsIgnoreCase("oui")) {
            System.out.println(Ansi.info(
                    "Aucune autre zone n'a été ajoutée. Vous continuerez à recevoir des notifications pour votre quartier."));
            return;
        }

        String zone = prompt.required(Ansi.YELLOW + "Entrez le quartier ou la rue >: " + Ansi.RESET);
        System.out.println(Ansi.info("Vous recevrez maintenant des notifications pour la zone : " + zone));
    }

    /** Collects a work request and stores it against this resident. */
    private void soumettreRequete() {
        System.out.println(Ansi.banner("SOUMISSION DE REQUÊTE DE TRAVAUX"));

        String title = prompt.required(Ansi.GREEN + "Titre des travaux >: " + Ansi.RESET);
        String description = prompt.required(Ansi.GREEN + "Description détaillée des travaux >: " + Ansi.RESET);
        String borough = prompt.required(Ansi.GREEN + "Quartier >: " + Ansi.RESET);

        WorkView.workTypeMenu();
        String workType = chooseWorkTypeLabel("Inconnu");

        LocalDate startDate = prompt.futureDate(
                Ansi.GREEN + "Date prévue de début des travaux (format: JJ/MM/AAAA) >: " + Ansi.RESET);

        resident.creerRequete(title, description, workType, startDate, borough);

        System.out.println(Ansi.banner("RÉSUMÉ DE LA REQUÊTE"));
        WorkView.request(Database.getResidentialWorkMap().get(resident));
        System.out.println(Ansi.green("\nVotre requête a été soumise avec succès."));
    }

    /** Shows this resident's open request and offers to close it. */
    private void suivreRequetes() {
        ResidentialWorkRequest request = Database.getResidentialWorkMap().get(resident);
        if (request == null) {
            System.out.println(Ansi.error("Vous n'avez aucune requête en cours."));
            return;
        }

        System.out.println(Ansi.info("Votre requête actuelle :"));
        WorkView.request(request);
        System.out.println(Ansi.green("\n[OPTIONS] Que souhaitez-vous faire avec cette requête ?"));
        System.out.println(Ansi.option(1, "Fermer la requête"));
        System.out.println(Ansi.option(2, "Retourner au menu principal"));

        int choice = prompt.number(Ansi.YELLOW + "Choisissez une option >: " + Ansi.RESET);
        if (choice == 1) {
            resident.fermerRequete(request);
        } else if (choice != 2) {
            System.out.println(Ansi.error("Option invalide. Veuillez essayer à nouveau."));
        }
    }

    private void preferences() {
        System.out.println(Ansi.cyan("Voulez-vous fournir des préférences ou consulter celles des autres ?"));
        System.out.println(Ansi.option(1, "Fournir mes préférences"));
        System.out.println(Ansi.option(2, "Consulter celles des autres"));
        System.out.println(Ansi.option(0, "Retourner au menu principal"));

        switch (prompt.number(Ansi.YELLOW + "Choisissez une option >: " + Ansi.RESET)) {
            case 1 -> {
                System.out.println(Ansi.cyan("Voici les préférences personnelles :"));
                System.out.println(Ansi.white("- Préférence 1 : Travaux routiers en semaine uniquement."));
                System.out.println(Ansi.white("- Préférence 2 : Pas de travaux après 18h."));
                System.out.println(Ansi.white("- Préférence 3 : Minimiser les interruptions d'eau."));
            }
            case 2 -> {
                System.out.println(Ansi.cyan("Voici les préférences des autres résidents :"));
                System.out.println(Ansi.white("- Préférence 1 : Travaux durant le week-end seulement."));
                System.out.println(Ansi.white("- Préférence 2 : Pas de travaux durant les vacances scolaires."));
                System.out.println(Ansi.white("- Préférence 3 : Travaux sur les trottoirs préférés."));
            }
            case 0 -> {
            }
            default -> System.out.println(Ansi.error("Choix invalide. Retour au menu principal..."));
        }
    }

    /**
     * Reads a number from the work type menu. Out of range gives back
     * {@code fallback}, which each caller picks: the search screens want a
     * label that matches nothing, the submission screen wants "Inconnu".
     */
    private String chooseWorkTypeLabel(String fallback) {
        int number = prompt.number(
                Ansi.GREEN + "Veuillez entrer le numéro correspondant au type de travaux (1-10) >: " + Ansi.RESET);
        Optional<WorkType> type = WorkType.fromMenuNumber(number);
        if (type.isEmpty()) {
            System.out.println(Ansi.error("Type de travaux inconnu."));
        }
        return type.map(WorkType::label).orElse(fallback);
    }

    /** Returns null and prints the reason when the city API cannot be read. */
    private List<WorkRecord> fetchWork() {
        try {
            return openData.fetchWork();
        } catch (IOException e) {
            System.out.println(Ansi.error(
                    "Impossible de récupérer les travaux pour le moment: " + e.getMessage()));
            return null;
        }
    }
}
