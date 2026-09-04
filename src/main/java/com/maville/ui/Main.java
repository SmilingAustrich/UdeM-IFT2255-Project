package com.maville.ui;

import com.maville.ui.console.ConsolePrompt;

import java.util.Scanner;

/**
 * Starts Ma Ville.
 *
 * <p>The single {@link Scanner} is opened here and passed down. Every screen
 * used to open its own, which meant one screen's read-ahead could consume a
 * line the next screen was waiting for.
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            new Menu(new ConsolePrompt(in)).start();
        }
    }
}
