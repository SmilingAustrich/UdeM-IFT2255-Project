package com.maville.ui.console;

import java.io.Console;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Reads and validates one answer from the terminal.
 *
 * <p>Two things pushed this out of {@code Menu}. The validation rules were
 * private methods on a class that also owned every screen, so they could not be
 * tested; they are now {@link #isValidEmail}, {@link #isValidCityId} and
 * {@link #ageOn}, which are static, take their input, and return an answer.
 *
 * <p>The other is the {@link Scanner}. Every screen used to open its own
 * {@code new Scanner(System.in)}. A Scanner buffers ahead, so the reader that
 * happens to be open when a line arrives can swallow input the next one is
 * waiting for. One instance is created at startup and passed down.
 */
public final class ConsolePrompt {

    private static final Pattern EMAIL =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    private static final Pattern CITY_ID = Pattern.compile("\\d{8}");
    private static final Pattern DATE_SHAPE = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");

    /**
     * Strict on purpose. Under the default SMART resolver, "dd/MM/yyyy" accepts
     * 31/02/2024 and quietly hands back 29 February, so a date that does not
     * exist was being stored as one that does. STRICT rejects it, and the
     * pattern uses uuuu rather than yyyy because year-of-era needs an era to
     * resolve strictly.
     */
    public static final DateTimeFormatter DATE = DateTimeFormatter
            .ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    private final Scanner in;

    public ConsolePrompt(Scanner in) {
        this.in = in;
    }

    /** Reads a line as-is, with no validation. */
    public String line(String prompt) {
        System.out.print(prompt);
        return in.nextLine();
    }

    /** Repeats until the answer is not blank. */
    public String required(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = in.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println(Ansi.error("Ce champ est obligatoire. Veuillez entrer une valeur."));
        }
    }

    /** Repeats until the answer parses as an integer. */
    public int number(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = in.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(Ansi.error("Veuillez entrer un nombre."));
            }
        }
    }

    public String email(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = in.nextLine().trim();
            if (isValidEmail(input)) {
                return input;
            }
            System.out.println(Ansi.error("Adresse email invalide. Veuillez entrer une adresse email valide."));
        }
    }

    /** Hides the typed characters when a real terminal is attached. */
    public String password(String prompt) {
        Console console = System.console();
        if (console != null) {
            return new String(console.readPassword(prompt));
        }
        return required(prompt);
    }

    public String cityId(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = in.nextLine().trim();
            if (isValidCityId(input)) {
                return input;
            }
            System.out.println(Ansi.error(
                    "L'identifiant de la ville doit être un code à 8 chiffres, veuillez réessayer."));
        }
    }

    /** Repeats until a dd/MM/yyyy date parses, then returns the age it implies. */
    public int age(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = in.nextLine().trim();
            Optional<LocalDate> birthDate = parseDate(input);
            if (birthDate.isPresent()) {
                return ageOn(birthDate.get(), LocalDate.now());
            }
            System.out.println(Ansi.error("Date invalide. Veuillez entrer une date au format jj/mm/aaaa."));
        }
    }

    /** Repeats until a dd/MM/yyyy date parses that is not in the past. */
    public LocalDate futureDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            Optional<LocalDate> parsed = parseDate(in.nextLine().trim());
            if (parsed.isEmpty()) {
                System.out.println(Ansi.error("Format de date invalide. Veuillez respecter le format JJ/MM/AAAA."));
                continue;
            }
            if (parsed.get().isBefore(LocalDate.now())) {
                System.out.println(Ansi.error("La date ne peut pas être dans le passé. Veuillez entrer une date valide."));
                continue;
            }
            return parsed.get();
        }
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL.matcher(email).matches();
    }

    public static boolean isValidCityId(String cityId) {
        return cityId != null && CITY_ID.matcher(cityId).matches();
    }

    /**
     * Parses dd/MM/yyyy. The shape is checked before parsing so that a value
     * like 1/2/2024 is rejected rather than silently accepted.
     */
    public static Optional<LocalDate> parseDate(String text) {
        if (text == null || !DATE_SHAPE.matcher(text).matches()) {
            return Optional.empty();
        }
        try {
            return Optional.of(LocalDate.parse(text, DATE));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    /** Completed years between the two dates. */
    public static int ageOn(LocalDate birthDate, LocalDate on) {
        return Period.between(birthDate, on).getYears();
    }
}
