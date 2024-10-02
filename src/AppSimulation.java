public class AppSimulation {

    // Méthode pour simuler le chargement
    public static void simulateLoading() {
        // Simuler une animation de chargement avec des points
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(500); // Pause de 500 ms entre chaque point
                System.out.print("."); // Afficher un point pour le chargement
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(); // Aller à la ligne après le chargement
    }

    // Méthode pour simuler l'écriture d'un texte en temps réel (délai en millisecondes)
    public static void simulateRealTimeWriting(String message) {

        int DELAY = 10;
        for (int i = 0; i < message.length(); i++) {
            // Affiche un caractère à la fois
            System.out.print(message.charAt(i));
            try {
                // Pause après chaque caractère pour simuler une écriture lente
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print(" "); // Aller à la ligne à la fin de l'écriture
    }

    // Méthode pour "effacer" l'écran en imprimant plusieurs lignes vides
    public void clearScreen() {
        for (int i = 0; i < 50; i++) {  // Choisis un nombre suffisant de lignes pour vider l'écran
            System.out.println();
        }
    }

}