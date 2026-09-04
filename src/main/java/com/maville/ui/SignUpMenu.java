package com.maville.ui;

import com.maville.auth.AuthenticationService;
import com.maville.model.Intervenant;
import com.maville.model.Resident;
import com.maville.ui.console.Ansi;
import com.maville.ui.console.ConsolePrompt;

import java.util.Optional;

/**
 * Registration for both kinds of user.
 *
 * <p>Each field is read through {@link ConsolePrompt}, so the rules for what
 * counts as a valid email, city id or date of birth are stated once and are
 * testable on their own.
 */
public final class SignUpMenu {

    /** Nobody under this age may hold an account. */
    public static final int MINIMUM_AGE = 16;

    private final ConsolePrompt prompt;

    public SignUpMenu(ConsolePrompt prompt) {
        this.prompt = prompt;
    }

    /** Registers a resident, or returns empty if they are too young. */
    public Optional<Resident> signUpResident() {
        System.out.println(Ansi.banner("INSCRIPTION - RÉSIDENT"));
        System.out.println(Ansi.cyan(
                "Nous sommes ravis de vous accueillir. Veuillez fournir les informations ci-dessous.\n"));

        String firstName = prompt.required(Ansi.YELLOW + "Prénom >: " + Ansi.RESET);
        String lastName = prompt.required(Ansi.YELLOW + "Nom de famille >: " + Ansi.RESET);
        String email = prompt.email(Ansi.YELLOW + "Adresse courriel >: " + Ansi.RESET);
        String password = prompt.password(Ansi.YELLOW + "Mot de passe (minimum 8 caractères) >: " + Ansi.RESET);
        String phone = prompt.required(Ansi.YELLOW + "Numéro de téléphone (optionnel, tapez 0) >: " + Ansi.RESET);
        String address = prompt.required(Ansi.YELLOW + "Adresse >: " + Ansi.RESET);
        int age = prompt.age(Ansi.YELLOW + "Date de naissance (format jj/mm/aaaa) >: " + Ansi.RESET);

        if (age < MINIMUM_AGE) {
            System.out.println(Ansi.error(
                    "Le compte ne peut pas être créé, vous devez avoir au moins " + MINIMUM_AGE + " ans."));
            return Optional.empty();
        }

        Resident resident = new Resident(firstName, lastName, email, password, phone, address, age);
        AuthenticationService.signUpResident(resident);
        System.out.println(Ansi.green("\nInscription réussie. Vous pouvez maintenant vous connecter.\n"));
        return Optional.of(resident);
    }

    public Optional<Intervenant> signUpIntervenant() {
        System.out.println(Ansi.banner("INSCRIPTION - INTERVENANT"));
        System.out.println(Ansi.cyan(
                "Nous sommes ravis de vous accueillir. Veuillez fournir les informations ci-dessous.\n"));

        String firstName = prompt.required(Ansi.YELLOW + "Prénom >: " + Ansi.RESET);
        String lastName = prompt.required(Ansi.YELLOW + "Nom de famille >: " + Ansi.RESET);
        String email = prompt.email(Ansi.YELLOW + "Adresse courriel >: " + Ansi.RESET);
        String password = prompt.password(Ansi.YELLOW + "Mot de passe (minimum 8 caractères) >: " + Ansi.RESET);
        String cityId = prompt.cityId(Ansi.YELLOW + "Identifiant de la ville (code à 8 chiffres) >: " + Ansi.RESET);

        System.out.println(Ansi.blue("\nType d'entrepreneur"));
        System.out.println(Ansi.option(1, "Entreprise privée"));
        System.out.println(Ansi.option(2, "Entreprise publique"));
        System.out.println(Ansi.option(3, "Particulier"));
        int entrepreneurType = prompt.number(Ansi.YELLOW + " >: " + Ansi.RESET);

        Intervenant intervenant =
                new Intervenant(firstName, lastName, email, password, cityId, entrepreneurType);
        AuthenticationService.signUpIntervenant(intervenant);
        System.out.println(Ansi.green("\nInscription réussie. Vous pouvez maintenant vous connecter.\n"));
        return Optional.of(intervenant);
    }
}
