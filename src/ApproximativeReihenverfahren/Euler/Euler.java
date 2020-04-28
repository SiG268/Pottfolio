package ApproximativeReihenverfahren.Euler;

import Exceptions.NoCalculationExecutedException;
import Main.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * PI Kalkulationsklasse des Euler Approximations Verfahrens
 */
public class Euler implements CalculatePi {
    //Konstanten
    /**
     * Der bei der Berechnung verwendete MathContext
     */
    public final MathContext MC = new MathContext(100, RoundingMode.HALF_EVEN);
    /**
     * Liste welche die Threads enthaelt um diese zu Verwalten
     */
    private final ArrayList<EulerRunner> ThreadList = new ArrayList<EulerRunner>();


    @Override
    public boolean startCalculation() {
        return startCalculation(1);
    }

    @Override
    public boolean startCalculation(int numThreads) {
        if (ThreadList.size() > 0) {
            boolean threadrunning = false;
            for (EulerRunner t : ThreadList) {
                threadrunning = threadrunning || t.running;
            }
            if (!threadrunning) {
                ThreadList.clear();
            } else {
                return false;
            }
        }
        for (int i = 1; i <= numThreads; i++) {
            EulerRunner t = new EulerRunner(i, numThreads);
            ThreadList.add(t);
            t.start();
        }
        return true;
    }

    @Override
    public void stopCalculation() {
        for (EulerRunner t : ThreadList)
            t.running = false;
    }

    @Override
    public BigDecimal getValue() throws NoCalculationExecutedException {
        BigDecimal pi = BigDecimal.ZERO;
        //Addiert die Teilsummen der Threads zusammen
        for (EulerRunner t : ThreadList) {
            pi = pi.add(t.parcialSum);
        }
        if (pi.equals(BigDecimal.ZERO)) {
            throw new NoCalculationExecutedException("No calculation step was executed. Increase delay");
        }
        //Teilsummen ergeben (pi^2)/6 -> Aufloesen nach Pi
        pi = pi.multiply(new BigDecimal(6));
        pi = pi.sqrt(MC);
        return pi;
    }

    @Override
    public int getInternalSteps() {
        int internalSteps = 0;
        //Addiert die internalSteps der einzelnen Threads
        for (EulerRunner r : ThreadList) {
            //Es gilt: Index = STARTINDEX+NUMTHREADS*Rechenschritte
            // => Schritte = (Index-STARTINDEX)/NUMTHREADS
            internalSteps += (r.getIndex().intValue() - r.STARTINDEX) / r.NUMTHREADS.intValue();
        }
        return internalSteps;
    }

    @Override
    public String getMethodName() {
        return "Euler series approximation";
    }
}
