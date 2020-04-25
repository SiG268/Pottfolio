package MonteCarloFast;

import MonteCarlo.Point;

import java.math.BigDecimal;

public class MonteCarloRunnerFast extends Thread {
    /**Ein-/Ausschalter für den Thread */
    public boolean running = true;

    /** Zähler für die Versuche*/
    private BigDecimal versuche;
    /** Zähler für die Treffer*/
    private BigDecimal treffer;

    //getter und setter
    public BigDecimal getVersuche() {
        return versuche;
    }

    public void setVersuche(BigDecimal versuche) {
        this.versuche = versuche;
    }

    public BigDecimal getTreffer() {
        return treffer;
    }

    public void setTreffer(BigDecimal treffer) {
        this.treffer = treffer;
    }

    public void incrementVersuche(){
        this.versuche = this.versuche.add(BigDecimal.ONE);
    }
    public void incrementTreffer(){
        this.treffer = this.treffer.add(BigDecimal.ONE);
    }
    /**
     * Default Konstruktor
     * Initialisiert Treffer und Versuche mit 0
     */
    protected MonteCarloRunnerFast(){
        setTreffer(BigDecimal.ZERO);
        setVersuche(BigDecimal.ZERO);
    }
    /**
     * Run Methode des Threads<br>
     * Generiert zwei Zufallswerte und überprüft diese auf einen Treffer.
     * Zählt Versuche und Treffer.
     */
    public void run(){
        while(running){
            //Zufallswerte x und y erzeugen.
            double x = Math.random();
            double y = Math.random();
            //Wenn x^2 + y^2 <= 1 Treffer erfassen
            if(!(x*x+y*y >1)){
                incrementTreffer();
            }
            //Versuch erfassen
            incrementVersuche();
        }
    }
}
