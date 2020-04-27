package ApproximativeReihenverfahren.Euler;

import Main.PiCalculationThread;

import java.math.BigDecimal;

/**
 * Threadklasse des ApproximativeReihenverfahren.Euler Approximations Verfahrens
 */
public class EulerRunner extends PiCalculationThread {

    /**
     * Konstruktor<br>
     * Ruft Superkonstruktor von {@link PiCalculationThread} auf
     *
     * @param startIndex Start Index
     * @param numThreads Anzahl an Threads
     */
    public EulerRunner(int startIndex, int numThreads) {
        super(startIndex, numThreads);
    }

    //Es gilt: summand = 1/(index^2)
    @Override
    public BigDecimal CalculateSummand(BigDecimal index) {
        BigDecimal summand = new BigDecimal(1);
        summand = summand.divide(getIndex().pow(2), MC);
        return summand;
    }

}
