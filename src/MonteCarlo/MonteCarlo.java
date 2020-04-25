package MonteCarlo;
import Exceptions.NoCalculationExecutedException;
import Main.CalculatePi;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * PI Kalkulationsklasse des Monte Carlo Approximations Verfahrens
 * @deprecated Slow. Replaced by {@link MonteCarloFast}
 */
public class MonteCarlo implements CalculatePi {
    //Konstanten
    /** Der bei der Berechnung verwendete MathContext*/
    public final MathContext MC = new MathContext(10000, RoundingMode.HALF_EVEN);
    /** Liste welche die Threads enthaelt um diese zu Verwalten*/
    public final ArrayList<MonteCarloRunner> ThreadList = new ArrayList<MonteCarloRunner>();

    @Override
    public boolean startCalculation() {
        return startCalculation(1);
    }

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

    @Override
    public void stopCalculation() {
        for (MonteCarloRunner thread:ThreadList) {
            thread.running=false;
        }
    }

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

    @Override
    public int getInternalSteps() {
        int steps=0;
        //Addiert die gemachten Versuche der einzelnen Threads
        for (MonteCarloRunner thread:ThreadList) {
            steps+=thread.getVersuche();
        }
        return steps;
    }

    @Override
    public String getMethodName() {
        return "Monte-Carlo approximation";
    }
}
