package ApproximativeReihenverfahren.Leibniz;

import Main.PiCalculationThread;

import java.math.BigDecimal;

/**
 * Threadklasse des ApproximativeReihenverfahren.Leibniz Approximations Verfahrens
 */
public class LeibnizRunner extends PiCalculationThread {
    private static final BigDecimal TWO = new BigDecimal(2);

    /**
     * Konstruktor<br>
     * Ruft Superkonstruktor von {@link PiCalculationThread} auf
     *
     * @param startIndex Start Index
     * @param numThreads Anzahl an Threads
     */
    //Index und numThreads werden veraendert, da CalculateSummand ein Summandenpaar berechnet
    public LeibnizRunner(int startIndex, int numThreads) {
        super(2 * startIndex - 1, 2 * numThreads);
    }

    //summand = 1/(2*index-1)-1/(2*index+1)
    @Override
    public BigDecimal CalculateSummand(BigDecimal index) {
        BigDecimal denumMinus = getIndex().multiply(TWO).subtract(BigDecimal.ONE);
        BigDecimal denumPlus = getIndex().multiply(TWO).add(BigDecimal.ONE);
        BigDecimal summand = BigDecimal.ONE;
        summand = summand.divide(denumMinus, MC);
        summand = summand.subtract(BigDecimal.ONE.divide(denumPlus, MC));
        return summand;
    }

}
