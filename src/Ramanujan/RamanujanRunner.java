package Ramanujan;

import Main.PiCalculationThread;
import java.math.BigDecimal;

public class RamanujanRunner extends PiCalculationThread {
    //Konstanten
    final BigDecimal NUM1103 = new BigDecimal(1103);
    final BigDecimal NUM396 = new BigDecimal(396);

    BigDecimal facBuffer = new BigDecimal("1");
    BigDecimal facFourBuffer = new BigDecimal("1");

    BigDecimal counter;
    BigDecimal denum;
    BigDecimal parcialSum = BigDecimal.ZERO;

    public RamanujanRunner(int startIndex, int numThreads){
        super(startIndex, numThreads);
    }

    public void updateFactorial(){
        if(index- NUMTHREADS >=0) {
            for (int i = index; i > (index - NUMTHREADS); i--) {
                facBuffer = facBuffer.multiply(new BigDecimal(i));
            }
            for (int i = (4 * index); i > ((index - NUMTHREADS) * 4); i--) {
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
        counter = facFourBuffer.multiply(NUM1103.add(new BigDecimal((26390*index))));
        denum = facBuffer.pow(4);
        denum = denum.multiply(NUM396.pow(index*4));
        BigDecimal summand =counter.divide(denum,super.mc);
        return summand;
    }

    public void run() {
        while(running){
            parcialSum = parcialSum.add(CalculateSummand(index));
            index = index + NUMTHREADS;
        }
    }
}
