package MonteCarlo;

import java.math.BigDecimal;

public class MonteCarloRunner extends Thread {
    //Kontrollvariable um den Thread anzuhalten
    public boolean running = true;
    //Zähler
    private int versuche;
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

    //Default Constructor
    public MonteCarloRunner(){
        setTreffer(0);
        setVersuche(0);
    }

    //Erzeugt einen zufälligen Punkt und gibt ihn zurück
    public Point randomPoint(){
        BigDecimal x = BigDecimal.valueOf(Math.random());
        BigDecimal y = BigDecimal.valueOf(Math.random());
        return new Point(x,y);
    }

    //TO-DO des Threads
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
