package com.maville.ui;

import com.maville.auth.AuthenticationService;
import com.maville.database.Database;
import com.maville.model.Intervenant;
import com.maville.model.Resident;
import com.maville.model.User;
import com.maville.ui.console.Ansi;
import com.maville.ui.console.ConsolePrompt;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Signing in, for both kinds of user.
 *
 * <p>The resident and intervenant sign-in screens were two fifty-line methods
 * that differed only in which service call checked the password and which map
 * the user was then read from. They are now one {@link #signIn} loop, given
 * those two as arguments.
 */
public final class LoginMenu {

    /**
     * Attempts allowed before the user is sent to registration. The original
     * loop was written as {@code while (tries < 3)} with a redirect at
     * {@code tries >= 2}, so the third attempt was unreachable; two is what it
     * actually did and what it does here.
     */
    public static final int MAX_ATTEMPTS = 2;

    private final ConsolePrompt prompt;

    public LoginMenu(ConsolePrompt prompt) {
        this.prompt = prompt;
    }

    public Optional<Resident> signInResident() {
        return signIn("PORTAIL DE CONNEXION - RÉSIDENT",
                AuthenticationService::loginResident,
                Database::getResidentByEmail);
    }

    public Optional<Intervenant> signInIntervenant() {
        return signIn("PORTAIL DE CONNEXION - INTERVENANT",
                AuthenticationService::loginIntervenant,
                Database::getIntervenantByEmail);
    }

    /**
     * @param authenticate checks an email and password pair
     * @param lookup       reads the account back once the password is accepted
     * @return the signed-in user, or empty once the attempts are used up
     */
    private <T extends User> Optional<T> signIn(String title,
                                                BiPredicate<String, String> authenticate,
                                                Function<String, T> lookup) {
        System.out.println(Ansi.banner(title));
        System.out.println(Ansi.cyan("Veuillez entrer votre email ainsi que votre mot de passe.\n"));

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            String email = prompt.line(Ansi.YELLOW + "Email >: " + Ansi.RESET);
            String password = prompt.password(Ansi.YELLOW + "Mot de passe >: " + Ansi.RESET);

            if (!authenticate.test(email, password)) {
                System.out.println(Ansi.error("Nom d'utilisateur ou mot de passe incorrect."));
                continue;
            }

            T user = lookup.apply(email);
            if (user == null) {
                // Authenticated against a record the directory cannot return.
                System.out.println(Ansi.error(
                        "Le compte n'a pas été trouvé dans la base de données."));
                continue;
            }

            System.out.println(Ansi.green("\nConnexion réussie. Bienvenue, " + user.getFirstName() + "."));
            return Optional.of(user);
        }

        System.out.println(Ansi.yellow(
                "\nVous ne semblez pas être inscrit. Redirection vers la page d'inscription."));
        return Optional.empty();
    }
}
