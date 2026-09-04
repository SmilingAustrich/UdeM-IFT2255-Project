package com.maville.ui.console;

/**
 * Terminal colour codes, in one place.
 *
 * <p>Before this class the codes were inline string literals spread over four
 * files. The same escape was spelled three different ways: as an octal escape
 * and as a unicode escape in two different cases. On 113 lines it was none of
 * those: a raw ESC control byte had been committed straight into the source,
 * where it is invisible in an editor and survives neither a copy-paste nor a
 * review.
 */
public final class Ansi {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[1;31m";
    public static final String GREEN = "\u001B[1;32m";
    public static final String YELLOW = "\u001B[1;33m";
    public static final String BLUE = "\u001B[1;34m";
    public static final String MAGENTA = "\u001B[1;35m";
    public static final String CYAN = "\u001B[1;36m";
    public static final String WHITE = "\u001B[1;37m";

    private static final String RULE = "----------------------------------------------";
    private static final String DOUBLE_RULE = "==============================================";

    private Ansi() {
    }

    public static String red(String text) {
        return RED + text + RESET;
    }

    public static String green(String text) {
        return GREEN + text + RESET;
    }

    public static String yellow(String text) {
        return YELLOW + text + RESET;
    }

    public static String blue(String text) {
        return BLUE + text + RESET;
    }

    public static String magenta(String text) {
        return MAGENTA + text + RESET;
    }

    public static String cyan(String text) {
        return CYAN + text + RESET;
    }

    public static String white(String text) {
        return WHITE + text + RESET;
    }

    /** A light rule between records. */
    public static String rule() {
        return blue(RULE);
    }

    /** A banner: a rule, a centred title, a rule. */
    public static String banner(String title) {
        int padding = Math.max(0, (DOUBLE_RULE.length() - title.length()) / 2);
        return "\n" + blue(DOUBLE_RULE)
                + "\n" + cyan(" ".repeat(padding) + title)
                + "\n" + blue(DOUBLE_RULE);
    }

    /** A labelled value, as printed on every record listing. */
    public static String field(String label, String value) {
        return YELLOW + label + ": " + RESET + value;
    }

    public static String info(String text) {
        return green("[INFO] " + text);
    }

    public static String error(String text) {
        return red("[ERREUR] " + text);
    }

    public static String option(int number, String text) {
        return cyan(number + ". " + text);
    }
}
