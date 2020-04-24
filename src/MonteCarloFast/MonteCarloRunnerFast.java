package MonteCarloFast;

import java.math.BigDecimal;

public class MonteCarloRunnerFast extends Thread {
    /**Ein-/Ausschalter für den Thread */
    public boolean running = true;

    /** {@link #versuche} - Zähler für die Versuche*/
    private BigDecimal versuche;
    /** {@link #treffer} - Zähler für die Treffer*/
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
    public MonteCarloRunnerFast(){
        setTreffer(BigDecimal.ZERO);
        setVersuche(BigDecimal.ZERO);
    }
    public void run(){
        while(running){
            double x = Math.random();
            double y = Math.random();
            if(!(x*x+y*y >1)){
                incrementTreffer();
            }
            incrementVersuche();
        }
    }
}
