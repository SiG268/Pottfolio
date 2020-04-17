package Leibniz;

import Main.PiCalculationThread;

import java.math.BigDecimal;

public class LeibnizRunner extends PiCalculationThread {

    public LeibnizRunner(int startIndex, int numThreads) {
        super(2*startIndex-1, 2*numThreads);
    }

    @Override
    public BigDecimal CalculateSummand(int index){

        BigDecimal denumMinus = new BigDecimal(2*index-1);
        BigDecimal denumPlus = new BigDecimal(2*index+1);
        BigDecimal summand = BigDecimal.ONE;
        summand = summand.divide(denumMinus,mc);
        summand = summand.subtract(BigDecimal.ONE.divide(denumPlus,mc));
        return summand;
    }

    public void run(){
        while(running == true) {
            parcialSum = parcialSum.add(CalculateSummand(getIndex()));
            setIndex(getIndex() + NUMTHREADS);
        }
    }
}
