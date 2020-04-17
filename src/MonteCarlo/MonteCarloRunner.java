package MonteCarlo;
import java.math.BigDecimal;

public class MonteCarloRunner extends Thread {
    //Kontrollvariable um den Thread anzuhalten
    public boolean running = true;
    //TODO Never used maybe delete?
    private int threadNumber;
    private int numThreads;
    //Zähler
    private int versuche;
    private int treffer;
    //Mittelpunkt = (0|0)
    Point middle = new Point();

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
        this.numThreads=1;
        setTreffer(0);
        setVersuche(0);
    }

    //Constructor, der numThreads übergibt (bei Multithreadding)
    //TODO numThreads nie verwendet maybe delete?
    public MonteCarloRunner(int numThreads){
        this();
        this.numThreads=numThreads;

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
            if (random.distance(middle).compareTo(BigDecimal.ONE) != 1) {
                this.treffer++;
            }
            //Erhöhe Versuchszähler
            this.versuche++;
        }

    }
}
