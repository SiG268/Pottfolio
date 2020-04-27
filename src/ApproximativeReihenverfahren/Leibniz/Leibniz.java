package ApproximativeReihenverfahren.Leibniz;

import Exceptions.NoCalculationExecutedException;
import Main.CalculatePi;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * PI Kalkulationsklasse des ApproximativeReihenverfahren.Leibniz Approximations Verfahrens
 */
public class Leibniz implements CalculatePi {
    /**Liste welche die Threads enthaelt um diese zu Verwalten*/
    public final ArrayList<LeibnizRunner> ThreadList = new ArrayList<LeibnizRunner>();

    @Override
    public boolean startCalculation() {
        return startCalculation(1);
    }

    @Override
    public boolean startCalculation(int numThreads) {
        if(ThreadList.size()>0){
            boolean threadrunning=false;
            for (LeibnizRunner t:ThreadList) {
                threadrunning=threadrunning || t.running;
            }
            if(!threadrunning){
                ThreadList.clear();
            }
            else{
                return false;
            }
        }
        for(int i = 1;i<=numThreads;i++) {
            LeibnizRunner t = new LeibnizRunner(i,numThreads);
            ThreadList.add(t);
            t.start();
        }
        return true;
    }

    @Override
    public void stopCalculation() {
        for(LeibnizRunner t : ThreadList)
            t.running = false;
    }

    @Override
    public BigDecimal getValue() throws NoCalculationExecutedException {
        BigDecimal pi = BigDecimal.ZERO;
        //Addiert die Teilsummen der Threads zusammen
        for(LeibnizRunner r : ThreadList){
            pi = pi.add(r.parcialSum);
        }
        if(pi.equals(BigDecimal.ZERO)){
            throw new NoCalculationExecutedException("No calculation step was executed. Increase delay");
        }
        //Teilsummen ergeben pi/4 -> Aufloesen nach Pi
        pi = pi.multiply(new BigDecimal("4"));
        return pi;
    }

    @Override
    public int getInternalSteps() {
        int internalSteps = 0;
        //Addiert die internalSteps der einzelnen Threads
        for(LeibnizRunner r : ThreadList){
            // => Schritte = (Index-STARTINDEX)/NUMTHREADS
            internalSteps += (r.getIndex().intValue()-(r.STARTINDEX))/r.NUMTHREADS.intValue();
        }
        return internalSteps;
    }

    @Override
    public String getMethodName() {
        return "ApproximativeReihenverfahren.Leibniz series approximation";
    }
}
