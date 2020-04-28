package ApproximativeReihenverfahren.Ramanujan;

import Exceptions.NoCalculationExecutedException;
import Main.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * PI Kalkulationsklasse des Ramanujan Approximations Verfahrens
 */
public class Ramanujan implements CalculatePi {

    /**
     * {@link #MC} -  Der bei der Berechnung verwendete MathContext
     */
    public final MathContext MC = new MathContext(10000, RoundingMode.HALF_EVEN);
    /**
     * {@link #ThreadList} - Liste welche die Threads enthaelt um diese zu Verwalten
     */
    public final ArrayList<RamanujanRunner> ThreadList = new ArrayList<RamanujanRunner>();
    /**
     * {@link #COEFFICIENT} - Koeffizient vor der Summe
     */
    public final BigDecimal COEFFICIENT = new BigDecimal(8).sqrt(MC).divide(new BigDecimal(9801), MC);

    @Override
    public boolean startCalculation() {
        return startCalculation(1);
    }

    @Override
    public boolean startCalculation(int numThreads) {
        if (ThreadList.size() > 0) {
            boolean threadrunning = false;
            for (RamanujanRunner t : ThreadList) {
                threadrunning = threadrunning || t.running;
            }
            if (!threadrunning) {
                ThreadList.clear();
            } else {
                return false;
            }
        }
        for (int i = 0; i < numThreads; i++) {
            RamanujanRunner t = new RamanujanRunner(i, numThreads);
            ThreadList.add(t);
            t.start();
        }
        return true;
    }

    @Override
    public void stopCalculation() {
        for (RamanujanRunner t : ThreadList)
            t.running = false;
    }

    @Override
    public BigDecimal getValue() throws NoCalculationExecutedException {
        BigDecimal sum = BigDecimal.ZERO;
        //Addiert die Teilsummen der Threads zusammen
        for (RamanujanRunner t : ThreadList) {
            sum = sum.add(t.parcialSum);
        }
        if (sum.equals(BigDecimal.ZERO)) {
            throw new NoCalculationExecutedException("No calculation step was executed. Increase delay");
        }
        //Summe (der Teilsummen) * Koeffizient
        sum = sum.multiply(COEFFICIENT);
        //Kehrwert bilden
        BigDecimal pi = BigDecimal.ONE.divide(sum, MC);
        return pi;
    }

    @Override
    public int getInternalSteps() {
        int internalSteps = 0;
        //Addiert die internalSteps der einzelnen Threads
        for (RamanujanRunner t : ThreadList) {
            //Es gilt: Index = STARTINDEX+NUMTHREADS*Rechenschritte
            // => Schritte = (Index-STARTINDEX)/NUMTHREADS
            internalSteps += (t.getIndex().intValue() - t.STARTINDEX) / t.NUMTHREADS.intValue();
        }
        return internalSteps;
    }

    @Override
    public String getMethodName() {
        return "Ramanujan series approximation";
    }
}
