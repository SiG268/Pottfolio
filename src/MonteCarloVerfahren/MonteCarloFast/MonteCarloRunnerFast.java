package MonteCarloVerfahren.MonteCarloFast;

import java.math.BigDecimal;

/**
 * Ueberarbeitete und Verbesserte Threadklasse
 */
public class MonteCarloRunnerFast extends Thread {
    /**
     * Ein-/Ausschalter fuer den Thread
     */
    public boolean running = true;

    /**
     * Zaehler fuer die Versuche
     */
    private BigDecimal versuche;
    /**
     * Zaehler fuer die Treffer
     */
    private BigDecimal treffer;

    //getter und setter
    /**
     * Rueckgabe der benoetigten Versuch
     * @return Versuche
     */
    public BigDecimal getVersuche() {
        return versuche;
    }
    /**
     * Setzen der Versuche
     * @param versuche Versuche
     */
    public void setVersuche(BigDecimal versuche) {
        this.versuche = versuche;
    }
    /**
     * Rueckgabe der erzielten Treffer
     * @return Treffer
     */
    public BigDecimal getTreffer() {
        return treffer;
    }
    /**
     * Setzen der Treffer
     * @param treffer Treffer
     */
    public void setTreffer(BigDecimal treffer) {
        this.treffer = treffer;
    }

    /**
     * Erhoehung der Versuche um den Wert Eins
     */
    public void incrementVersuche() {
        setVersuche(getVersuche().add(BigDecimal.ONE));
    }

    /**
     * Erhoehung der Treffer um den Wert Eins
     */
    public void incrementTreffer() {
        setTreffer(getTreffer().add(BigDecimal.ONE));
    }

    /**
     * Default Konstruktor
     * Initialisiert Treffer und Versuche mit 0
     */
    protected MonteCarloRunnerFast() {
        setTreffer(BigDecimal.ZERO);
        setVersuche(BigDecimal.ZERO);
    }

    /**
     * Run Methode des Threads<br>
     * Generiert zwei Zufallswerte und ueberprueft diese auf einen Treffer.
     * Zaehlt Versuche und Treffer.
     */
    public void run() {
        while (running) {
            //Zufallswerte x und y erzeugen.
            double x = Math.random();
            double y = Math.random();
            //Wenn x^2 + y^2 <= 1 Treffer erfassen
            if (!(x * x + y * y > 1)) {
                incrementTreffer();
            }
            //Versuch erfassen
            incrementVersuche();
        }
    }
}
