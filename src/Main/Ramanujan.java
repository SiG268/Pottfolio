package Main;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

public class Ramanujan implements CalculatePi{

    public ArrayList<RamanujanRunner> ThreadList = new ArrayList<RamanujanRunner>();
    public RamanujanRunner r;


    @Override
    public boolean startCalculation() {
        RamanujanRunner t = new RamanujanRunner(0,1);
        ThreadList.add(t);
        t.start();
        return false;
    }

    @Override
    public boolean startCalculation(int numThreads) {
        for(int i = 0;i<numThreads;i++) {
            RamanujanRunner t = new RamanujanRunner(i,numThreads);
            ThreadList.add(t);
            t.start();
        }
        return false;
    }

    @Override
    public void stopCalculation() {
        for(RamanujanRunner t : ThreadList)
            t.running = false;
        }


    @Override
    public BigDecimal getValue() {
        BigDecimal sum = new BigDecimal(0);
        for(RamanujanRunner r : ThreadList){
            sum = sum.add(r.parcialSum);
        }
        sum = sum.divide(new BigDecimal(9801),MathContext.DECIMAL128);
        sum = sum.multiply(new BigDecimal(8).sqrt(MathContext.DECIMAL128));
        BigDecimal pi = new BigDecimal(1).divide(sum,MathContext.DECIMAL128);
        return pi;
    }

    @Override
    public int getInternalSteps() {
        int internalSteps = 0;
        for(RamanujanRunner r : ThreadList){
            internalSteps += r.index;
        }
        return internalSteps;
    }

    @Override
    public String getMethodName() {
        return "Ramanujan series approximation";
    }
}
