package Main;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public abstract class PiCalculationThread extends Thread{
    public final MathContext mc = new MathContext(1000, RoundingMode.HALF_EVEN);
    private int threadNumber;
    public int index;
    public int numThreads;
    public volatile boolean running = true;
    BigDecimal parcialSum = BigDecimal.ZERO;
    public int getThreadNumber() {
        return threadNumber;
    }

    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }
    public PiCalculationThread(int startIndex, int numThreads){
        this.index = startIndex;
        setThreadNumber(startIndex);
        this.numThreads = numThreads;
    }

    public abstract BigDecimal CalculateSummand(int index);
}
