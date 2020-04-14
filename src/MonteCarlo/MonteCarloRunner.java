package MonteCarlo;
import java.math.BigDecimal;

public class MonteCarloRunner extends Thread {
    public boolean running = true;
    private int threadNumber;
    private int numThreads;
    private int versuche;
    private int treffer;
    Point middle = new Point();

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

    public MonteCarloRunner(){
        this.numThreads=1;
        setTreffer(0);
        setVersuche(0);
    }

    public MonteCarloRunner(int numThreads){
        this();
        this.numThreads=numThreads;

    }

    public Point randomPoint(){
        BigDecimal x = BigDecimal.valueOf(Math.random());
        BigDecimal y = BigDecimal.valueOf(Math.random());
        return new Point(x,y);
    }

    public void run(){
        while(running) {
            Point random = randomPoint();
            if (random.distance(middle).compareTo(BigDecimal.valueOf(1)) == -1) {
                this.treffer++;
            }
            this.versuche++;
        }

    }
}
