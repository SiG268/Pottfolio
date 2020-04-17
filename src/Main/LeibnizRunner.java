package Main;

import java.math.BigDecimal;

public class LeibnizRunner extends PiCalculationThread{

    public LeibnizRunner(int startIndex, int numThreads) {
        super(2*startIndex-1, 2*numThreads);
    }

    @Override
    public BigDecimal CalculateSummand(int index){
        BigDecimal summand = BigDecimal.ONE;
        summand = summand.divide(new BigDecimal(2*index-1),mc);
        summand = summand.subtract(BigDecimal.ONE.divide(new BigDecimal(2*index+1),mc));
        return summand;
    }

    public void run(){
        while(running == true) {
            parcialSum = parcialSum.add(CalculateSummand(index));
            index = index + numThreads;
        }
    }
}
