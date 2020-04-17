package Main;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class RamanujanRunner extends PiCalculationThread{
//    public volatile boolean running = true;
//    public int index;
//    public int numThreads;
//    private int threadNumber;

    BigDecimal facBuffer = new BigDecimal("1");
    BigDecimal facFourBuffer = new BigDecimal("1");


    BigDecimal counter;
    BigDecimal denum;
    BigDecimal parcialSum = BigDecimal.ZERO;

//    public int getThreadNumber() {
//        return threadNumber;
//    }
//
//    public void setThreadNumber(int threadNumber) {
//        this.threadNumber = threadNumber;
//    }

    public RamanujanRunner(int startIndex, int numThreads){
        super(startIndex, numThreads);
//        this.index = startIndex;
//        setThreadNumber(startIndex);
//        this.numThreads = numThreads;
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
    public BigDecimal CalculateSummand(int index) throws PrecisionLimitReachedException{
        updateFactorial();
        counter = facFourBuffer.multiply(new BigDecimal("1103").add(new BigDecimal((26390*index))));
        denum = facBuffer.pow(4);
        denum = denum.multiply(new BigDecimal("396").pow(index*4));
        BigDecimal summand =counter.divide(denum,super.mc);
        if(summand.equals(BigDecimal.ZERO)){
            System.out.println(summand);
            throw new PrecisionLimitReachedException(this);
        }
            return summand;
    }

    public void run() {
        while(running){
            try {
                parcialSum = parcialSum.add(CalculateSummand(index));
            } catch (PrecisionLimitReachedException e) {
                e.printStackTrace();
            }
            index = index + numThreads;
        }
    }
}
