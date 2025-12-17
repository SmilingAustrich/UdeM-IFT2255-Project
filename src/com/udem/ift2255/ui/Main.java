package com.udem.ift2255.ui;

import com.udem.ift2255.database.Database;

/**
 * Classe principale {@code com.udem.ift2255.ui.Main} pour démarrer l'application Ma Ville.
 * Ce programme a été réalisé dans le cadre d'un devoir universitaire.
 *
 * Auteurs :
 * - Tarik Hireche
 * - Ilyesse Bouzammita
 * - Karim Ndoye
 */
public class Main {

    /**
     * Méthode principale {@code main} qui démarre l'application en créant un objet {@code com.udem.ift2255.ui.Menu}.
     *
     * @param args Arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start();  // Lance l'application Ma Ville
    }
}
