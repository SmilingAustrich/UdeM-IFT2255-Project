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

    public static void simulateWaitTime(){

        try {
            Thread.sleep(1500); // Pause de 500 ms entre chaque point
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}