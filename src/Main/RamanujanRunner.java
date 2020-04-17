package Main;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class RamanujanRunner extends PiCalculationThread{
    BigDecimal facBuffer = new BigDecimal("1");
    BigDecimal facFourBuffer = new BigDecimal("1");
    final BigDecimal ONEK_CONST = new BigDecimal(1103);
    final BigDecimal THREE_CONST = new BigDecimal(396);

    BigDecimal counter;
    BigDecimal denum;
    BigDecimal parcialSum = BigDecimal.ZERO;

    public RamanujanRunner(int startIndex, int numThreads){
        super(startIndex, numThreads);
    }

    public void updateFactorial(){
        if(index-numThreads>=0) {
            for (int i = index; i > (index - numThreads); i--) {
                facBuffer = facBuffer.multiply(new BigDecimal(i));
            }
            for (int i = (4 * index); i > ((index - numThreads) * 4); i--) {
                facFourBuffer = facFourBuffer.multiply(new BigDecimal(i));
            }
        }else{
            for (int i = index; i > 0; i--) {
                facBuffer = facBuffer.multiply(new BigDecimal(i));
            }
            for (int i = (4 * index); i > 0; i--) {
                facFourBuffer = facFourBuffer.multiply(new BigDecimal(i));
            }
        }
    }

    @Override
    public BigDecimal CalculateSummand(int index){
        updateFactorial();
        counter = facFourBuffer.multiply(ONEK_CONST.add(new BigDecimal((26390*index))));
        denum = facBuffer.pow(4);
        denum = denum.multiply(THREE_CONST.pow(index*4));
        BigDecimal summand =counter.divide(denum,super.mc);
        return summand;
    }

    public void run() {
        while(running){
            parcialSum = parcialSum.add(CalculateSummand(index));
            index = index + numThreads;
        }
    }
}
