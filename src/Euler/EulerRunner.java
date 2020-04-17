package Euler;

import Main.PiCalculationThread;

import java.math.BigDecimal;

public class EulerRunner extends PiCalculationThread {
    //Constructor matches SuperConstructor
    public EulerRunner(int startIndex, int numThreads) {
        super(startIndex, numThreads);
    }

    //Berechnet den Summanden für einen Index
    //Es gilt: summand = 1/(index^2)
    @Override
    public BigDecimal CalculateSummand(int index){
        BigDecimal summand = new BigDecimal(1);
        summand = summand.divide(new BigDecimal(index).pow(2), MC);
        return summand;
    }

    //TO-DO des Threads
    //Erhöht die Teilsumme um den nächsten Summanden
    //Erhöht den Index um NUMTHREADS
    @Override
    public void run() {
        while (running) {
            parcialSum = parcialSum.add(CalculateSummand(getIndex()));
            setIndex(getIndex() + NUMTHREADS);
        }
    }
}
