package MonteCarloFast;

import Exceptions.NoCalculationExecutedException;
import Main.CalculatePi;
import MonteCarlo.MonteCarloRunner;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MonteCarloFast implements CalculatePi {
    //Konstanten
    /**Der bei der Berechnung verwendete MathContext*/
    public final MathContext MC = new MathContext(50, RoundingMode.HALF_EVEN);
    /**Liste welche die Threads enthält um diese zu Verwalten*/
    public final ArrayList<MonteCarloRunnerFast> ThreadList = new ArrayList<MonteCarloRunnerFast>();

    @Override
    public boolean startCalculation() {
        return startCalculation(1);
    }

    @Override
    public boolean startCalculation(int numThreads) {
        if(ThreadList.size()>0){
            boolean threadrunning=false;
            for (MonteCarloRunnerFast t:ThreadList) {
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
            MonteCarloRunnerFast thread = new MonteCarloRunnerFast();
            ThreadList.add(thread);
            thread.start();
        }
        return true;
    }

    @Override
    public void stopCalculation() {
        for (MonteCarloRunnerFast thread:ThreadList) {
            thread.running=false;
        }
    }

    @Override
    public BigDecimal getValue() throws NoCalculationExecutedException {
        BigDecimal pi;
        BigDecimal treffer=BigDecimal.ZERO;
        BigDecimal versuche=BigDecimal.ZERO;
        //Addiert die treffer und die versuche der einzelnen Threads
        for (MonteCarloRunnerFast thread:ThreadList) {
            treffer = treffer.add(thread.getTreffer());
            versuche= versuche.add(thread.getVersuche());
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
        for (MonteCarloRunnerFast thread:ThreadList) {
            steps+=thread.getVersuche().intValue();
        }
        return steps;
    }

    @Override
    public String getMethodName() {
        return "Monte-Carlo approximation";
    }
}
