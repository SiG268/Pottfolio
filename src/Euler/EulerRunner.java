package Euler;

import Main.PiCalculationThread;

import java.math.BigDecimal;

public class EulerRunner extends PiCalculationThread {
    public EulerRunner(int startIndex, int numThreads) {
        super(startIndex, numThreads);
    }

    @Override
    public BigDecimal CalculateSummand(int index){
        BigDecimal summand = new BigDecimal(1);
        summand = summand.divide(new BigDecimal(index).pow(2),mc);
        return summand;
    }
    public void run() {
        while (running) {
            parcialSum = parcialSum.add(CalculateSummand(index));
            index = index + NUMTHREADS;
        }
    }
}
