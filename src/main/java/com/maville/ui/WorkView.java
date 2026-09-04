package com.maville.ui;

import com.maville.data.ObstructionRecord;
import com.maville.data.WorkRecord;
import com.maville.model.ResidentialWorkRequest;
import com.maville.model.WorkType;
import com.maville.ui.console.Ansi;
import com.maville.ui.console.ConsolePrompt;

import java.util.List;

/**
 * Prints records to the terminal.
 *
 * <p>Six screens each printed a road work record with their own copy of the
 * same four lines and their own choice of colours, and three of them printed a
 * different subset depending on which field the user had just searched by. They
 * now all call {@link #workList}, so a listing looks the same wherever it is
 * reached from, and an "aucun résultat" case cannot be forgotten in one screen
 * and handled in another.
 */
public final class WorkView {

    private WorkView() {
    }

    public static void workList(String title, List<WorkRecord> works) {
        System.out.println(Ansi.banner(title));
        if (works.isEmpty()) {
            System.out.println(Ansi.error("Aucun travail trouvé."));
            return;
        }
        for (WorkRecord work : works) {
            System.out.println();
            System.out.println(Ansi.field("ID", work.id()));
            System.out.println(Ansi.field("Quartier", work.borough()));
            System.out.println(Ansi.field("Type de travail", work.category()));
            System.out.println(Ansi.field("Nom de l'intervenant", work.organization()));
            System.out.println(Ansi.rule());
        }
        System.out.println(Ansi.info(works.size() + " travaux affichés."));
    }

    public static void obstructionList(String title, List<ObstructionRecord> obstructions) {
        System.out.println(Ansi.banner(title));
        if (obstructions.isEmpty()) {
            System.out.println(Ansi.error("Aucune entrave trouvée."));
            return;
        }
        for (ObstructionRecord obstruction : obstructions) {
            System.out.println();
            System.out.println(Ansi.field("ID du travail", obstruction.workId()));
            System.out.println(Ansi.field("Nom de la rue", obstruction.street()));
            System.out.println(Ansi.field("Impact sur la rue", obstruction.impact()));
            System.out.println(Ansi.rule());
        }
        System.out.println(Ansi.info(obstructions.size() + " entraves affichées."));
    }

    /** The ten work categories, numbered as {@link WorkType#menuNumber()}. */
    public static void workTypeMenu() {
        System.out.println(Ansi.green("\nTypes de travaux disponibles :"));
        for (WorkType type : WorkType.values()) {
            System.out.println(Ansi.option(type.menuNumber(), type.label()));
        }
    }

    public static void request(ResidentialWorkRequest request) {
        System.out.println(Ansi.field("Titre", request.getTitle()));
        System.out.println(Ansi.field("Description", request.getDescription()));
        System.out.println(Ansi.field("Type de travaux", request.getWorkType()));
        System.out.println(Ansi.field("Quartier", request.getNeighbourhood()));
        System.out.println(Ansi.field("Date de début prévue",
                request.getStartDate().format(ConsolePrompt.DATE)));
    }

    public static void requestList(String title, List<ResidentialWorkRequest> requests) {
        System.out.println(Ansi.banner(title));
        if (requests.isEmpty()) {
            System.out.println(Ansi.error("Aucune requête trouvée."));
            return;
        }
        for (ResidentialWorkRequest request : requests) {
            System.out.println();
            request(request);
            System.out.println(Ansi.rule());
        }
    }
}
