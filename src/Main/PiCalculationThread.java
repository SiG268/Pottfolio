package Main;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public abstract class PiCalculationThread extends Thread{
    //Konstanten
    public final MathContext mc = new MathContext(1000, RoundingMode.HALF_EVEN);
    public final int STARTINDEX;
    public final int NUMTHREADS;

    public volatile boolean running = true;
    public BigDecimal parcialSum = BigDecimal.ZERO;

    private int index;
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }



    public PiCalculationThread(int startIndex, int numThreads){
        this.index = startIndex;
        this.STARTINDEX =startIndex;
        this.NUMTHREADS = numThreads;
    }

    public abstract BigDecimal CalculateSummand(int index);
}
