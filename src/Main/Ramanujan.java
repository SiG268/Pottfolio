package Main;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Ramanujan implements CalculatePi{

    public ArrayList<RamanujanRunner> ThreadList = new ArrayList<RamanujanRunner>();
    public RamanujanRunner r;
    public final MathContext mc = new MathContext(10000, RoundingMode.HALF_EVEN);
    public final BigDecimal FINAL_CONST = new BigDecimal(8).sqrt(mc).divide(new BigDecimal(9801),mc);

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
        sum = sum.multiply(FINAL_CONST);
        BigDecimal pi = BigDecimal.ONE.divide(sum,mc);
        System.out.println(pi);
        return pi;
    }

    @Override
    public int getInternalSteps() {
        int internalSteps = 0;
        for(RamanujanRunner r : ThreadList){
            internalSteps += (r.index-r.getThreadNumber())/r.numThreads;
        }
        return internalSteps;
    }

    @Override
    public String getMethodName() {
        return "Ramanujan series approximation";
    }
}
