package Main;

import java.math.BigDecimal;

public class EulerRunner extends PiCalculationThread{
    public EulerRunner(int startIndex, int numThreads) {
        super(startIndex, numThreads);
    }

    @Override
    public BigDecimal CalculateSummand(int index) throws PrecisionLimitReachedException {
        BigDecimal summand = new BigDecimal(1);
        summand = summand.divide(new BigDecimal(index).pow(2),mc);
        return summand;
    }
    public void run() {
        while (running) {
            try {
                parcialSum = parcialSum.add(CalculateSummand(index));
            } catch (PrecisionLimitReachedException e) {
                e.printStackTrace();
            }
            index = index + numThreads;
        }
    }
}
