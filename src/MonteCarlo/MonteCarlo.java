package MonteCarlo;
import Exceptions.NoCalculationExecutedException;
import Leibniz.LeibnizRunner;
import Main.CalculatePi;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MonteCarlo implements CalculatePi {
    //Konstanten
    public final MathContext MC = new MathContext(10000, RoundingMode.HALF_EVEN);
    //ArrayList zur Threadverwaltung
    public final ArrayList<MonteCarloRunner> ThreadList = new ArrayList<MonteCarloRunner>();

    //startet die Single Thread Berechnung
    //Thread erzeugen -> zur ThreadList hinzufügen -> Thread starten
    @Override
    public boolean startCalculation() {
        return startCalculation(1);
    }

    //startet die multi Thread Berechnung
    @Override
    public boolean startCalculation(int numThreads) {
        if(ThreadList.size()>0){
            boolean threadrunning=false;
            for (MonteCarloRunner t:ThreadList) {
                threadrunning=threadrunning || t.running;
            }
            if(!threadrunning){
                ThreadList.clear();
            }
            else{
                return false;
            }
        }
        for(int i=0; i<numThreads ; i++) {
            MonteCarloRunner thread = new MonteCarloRunner();
            ThreadList.add(thread);
            thread.start();
        }
        return true;
    }

    //Beendet die Berechnung
    @Override
    public void stopCalculation() {
        for (MonteCarloRunner thread:ThreadList) {
            thread.running=false;
        }
    }

    //Berechnet den aktuellen Wert für Pi und gibt diesen zurück
    @Override
    public BigDecimal getValue() throws NoCalculationExecutedException {
        BigDecimal pi;
        BigDecimal treffer=BigDecimal.ZERO;
        BigDecimal versuche=BigDecimal.ZERO;
        //Addiert die treffer und die versuche der einzelnen Threads
        for (MonteCarloRunner thread:ThreadList) {
            treffer = treffer.add(new BigDecimal(thread.getTreffer()));
            versuche= versuche.add(new BigDecimal(thread.getVersuche()));
        }
        if(versuche.equals(BigDecimal.ZERO)){
            throw new NoCalculationExecutedException("No calculation step was executed. Increase delay");
        }
        //Es gilt Treffer / Versuche = pi/4 -> pi = treffer/versuche * 4
        pi=treffer.divide(versuche, MC).multiply(new BigDecimal("4"));
        return pi;
    }

    //Berechnet die gemachten Berechnungsschritte und gibt sie zurück
    @Override
    public int getInternalSteps() {
        //todo Integer Overflow Exception
        int steps=0;
        //Addiert die gemachten Versuche der einzelnen Threads
        for (MonteCarloRunner thread:ThreadList) {
            steps+=thread.getVersuche();
        }
        return steps;
    }

    //Gibt den Namen der verwendeten Berechnungsmethode aus
    @Override
    public String getMethodName() {
        return "Monte-Carlo approximation";
    }
}
