package com.maville.ui;

import com.maville.data.MontrealOpenData;
import com.maville.data.OpenDataSource;
import com.maville.ui.console.Ansi;
import com.maville.ui.console.ConsolePrompt;

/**
 * The screen the application opens on: sign in, or register.
 *
 * <p>This class was 579 lines and owned everything. It held both sign-in
 * screens, both registration screens, both post-sign-in menus, the five input
 * validation helpers and the banner. It now does one thing, and hands off to
 * {@link LoginMenu}, {@link SignUpMenu}, {@link ResidentMenu} and
 * {@link IntervenantMenu}.
 */
public final class Menu {

    private final ConsolePrompt prompt;
    private final OpenDataSource openData;
    private final LoginMenu login;
    private final SignUpMenu signUp;

    public Menu(ConsolePrompt prompt) {
        this(prompt, new MontrealOpenData());
    }

    /** Takes the data source so a test can run the menus without a network. */
    public Menu(ConsolePrompt prompt, OpenDataSource openData) {
        this.prompt = prompt;
        this.openData = openData;
        this.login = new LoginMenu(prompt);
        this.signUp = new SignUpMenu(prompt);
    }

    public void start() {
        logo();
        while (true) {
            System.out.print(
                    Ansi.green("\n==================================================") + "\n"
                            + Ansi.white("|            MENU PRINCIPAL - MA VILLE           |") + "\n"
                            + Ansi.green("==================================================") + "\n"
                            + Ansi.yellow("  I. Se connecter en tant que :") + "\n"
                            + Ansi.white("     1. Résident") + "\n"
                            + Ansi.white("     2. Intervenant") + "\n"
                            + Ansi.yellow("  II. S'inscrire en tant que :") + "\n"
                            + Ansi.white("     3. Résident") + "\n"
                            + Ansi.white("     4. Intervenant") + "\n"
                            + Ansi.white("     5. Quitter") + "\n"
                            + Ansi.red("  Vous devez avoir au moins " + SignUpMenu.MINIMUM_AGE
                            + " ans pour utiliser Ma Ville.") + "\n"
                            + Ansi.green("==================================================") + "\n"
                            + Ansi.cyan(":> "));

            switch (prompt.number("")) {
                case 1 -> login.signInResident()
                        .ifPresentOrElse(this::openResident, this::signUpResidentThenSignIn);
                case 2 -> login.signInIntervenant()
                        .ifPresentOrElse(this::openIntervenant, this::signUpIntervenantThenSignIn);
                case 3 -> signUpResidentThenSignIn();
                case 4 -> signUpIntervenantThenSignIn();
                case 5 -> {
                    System.out.println(Ansi.yellow("Au revoir."));
                    return;
                }
                default -> System.out.println(Ansi.error("Choix invalide. Veuillez réessayer."));
            }
        }
    }

    private void signUpResidentThenSignIn() {
        signUp.signUpResident().ifPresent(
                resident -> login.signInResident().ifPresent(this::openResident));
    }

    private void signUpIntervenantThenSignIn() {
        signUp.signUpIntervenant().ifPresent(
                intervenant -> login.signInIntervenant().ifPresent(this::openIntervenant));
    }

    private void openResident(com.maville.model.Resident resident) {
        new ResidentMenu(resident, prompt, openData).show();
    }

    private void openIntervenant(com.maville.model.Intervenant intervenant) {
        new IntervenantMenu(intervenant, prompt).show();
    }

    private static void logo() {
        System.out.println(
                Ansi.blue("**************************************************") + "\n"
                        + Ansi.blue("*                                                *") + "\n"
                        + Ansi.yellow("*   BIENVENUE SUR VOTRE APPLICATION MA VILLE!    *") + "\n"
                        + Ansi.blue("*                                                *") + "\n"
                        + Ansi.blue("**************************************************"));
    }
}
