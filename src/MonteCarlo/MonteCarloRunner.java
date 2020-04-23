package MonteCarlo;

import java.math.BigDecimal;

public class MonteCarloRunner extends Thread {
    /**Ein-/Ausschalter für den Thread */
    public boolean running = true;

    /**Zähler für die Versuche*/
    private int versuche;
    /**Zähler für die Treffer*/
    private int treffer;

    //getter und setter
    public int getVersuche() {
        return versuche;
    }

    public void setVersuche(int versuche) {
        this.versuche = versuche;
    }

    public int getTreffer() {
        return treffer;
    }

    public void setTreffer(int treffer) {
        this.treffer = treffer;
    }

    /**
     * Default Konstruktor
     * Initialisiert Treffer und Versuche mit 0
     */
    public MonteCarloRunner(){
        setTreffer(0);
        setVersuche(0);
    }

    //Erzeugt einen zufälligen Punkt und gibt ihn zurück

    /**
     * Erzeugt einen random {@link Point}
     * @return Gibt einen {@link Point} zurück
     */
    private Point randomPoint(){
        BigDecimal x = BigDecimal.valueOf(Math.random());
        BigDecimal y = BigDecimal.valueOf(Math.random());
        return new Point(x,y);
    }

    //TO-DO des Threads

    /**
     * Run Methode des Threads<br/>
     * Erzeugt einen Random {@link Point} und schaut ob dieser ein Treffer ist oder nicht.
     */
    public void run(){
        while(running) {
            //Erzeugt einen zufälligen Punkt
            Point random = randomPoint();
            //Wenn die Distanz zwischen Mittelpunkt und erzeugtem Punkt <=1 (also !(>1) erhöhe Trefferzähler
            treffer+=random.eval();
            //Erhöhe Versuchszähler
            this.versuche++;
        }

    }

}
