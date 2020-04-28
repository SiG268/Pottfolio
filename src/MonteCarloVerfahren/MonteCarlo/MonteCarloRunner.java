package MonteCarloVerfahren.MonteCarlo;

import java.math.BigDecimal;

/**
 * Threadklasse des Monte Carlo Approximations Verfahrens
 */
public class MonteCarloRunner extends Thread {
    /**
     * Ein-/Ausschalter fuer den Thread
     */
    public boolean running = true;

    /**
     * Zaehler fuer die Versuche
     */
    private int versuche;
    /**
     * Zaehler fuer die Treffer
     */
    private int treffer;

    //getter und setter

    /**
     * Rueckgabe der benoetigten Versuch
     * @return Versuche
     */
    public int getVersuche() {
        return versuche;
    }

    /**
     * Setzen der Versuche
     * @param versuche Versuche
     */
    public void setVersuche(int versuche) {
        this.versuche = versuche;
    }

    /**
     * Rueckgabe der erzielten Treffer
     * @return Treffer
     */
    public int getTreffer() {
        return treffer;
    }
    /**
     * Setzen der Treffer
     * @param treffer Treffer
     */
    public void setTreffer(int treffer) {
        this.treffer = treffer;
    }

    /**
     * Default Konstruktor
     * Initialisiert Treffer und Versuche mit 0
     */
    protected MonteCarloRunner() {
        setTreffer(0);
        setVersuche(0);
    }

    //Erzeugt einen zufaelligen Punkt und gibt ihn zurueck

    /**
     * Erzeugt einen random {@link Point}
     *
     * @return Gibt einen {@link Point} zurueck
     */
    private Point randomPoint() {
        BigDecimal x = BigDecimal.valueOf(Math.random());
        BigDecimal y = BigDecimal.valueOf(Math.random());
        return new Point(x, y);
    }

    //TO-DO des Threads

    /**
     * Run Methode des Threads
     * Erzeugt einen Random {@link Point} und schaut ob dieser ein Treffer ist oder nicht.
     */
    public void run() {
        while (running) {
            //Erzeugt einen zufaelligen Punkt
            Point random = randomPoint();
            //Wenn die Distanz zwischen Mittelpunkt und erzeugtem Punkt <=1 (also !(>1) erhoehe Trefferzaehler
            setTreffer(getTreffer()+random.eval());
            //Erhoehe Versuchszaehler
            setVersuche(getVersuche()+1);
        }

    }

}
