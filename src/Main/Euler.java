package Main;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Euler implements CalculatePi{

    public ArrayList<EulerRunner> ThreadList = new ArrayList<EulerRunner>();
    public EulerRunner r;
    public final MathContext mc = new MathContext(100, RoundingMode.HALF_EVEN);
    public final BigDecimal SIX_CONST = new BigDecimal(6);
    @Override
    public boolean startCalculation() {
        r = new EulerRunner(0,1);
        ThreadList.add(r);
        r.start();
        return false;
    }

    @Override
    public boolean startCalculation(int numThreads) {
        for(int i = 1;i<=numThreads;i++) {
            r = new EulerRunner(i,numThreads);
            ThreadList.add(r);
            r.start();
        }
        return false;
    }

    @Override
    public void stopCalculation() {
        for(EulerRunner t : ThreadList)
            t.running = false;
    }


    @Override
    public BigDecimal getValue() {
        BigDecimal sum = new BigDecimal(0);
        for(EulerRunner t : ThreadList){
            sum = sum.add(t.parcialSum);
        }
        sum = sum.multiply(SIX_CONST);
        sum = sum.sqrt(mc);
        return sum;
    }

    @Override
    public int getInternalSteps() {
        int internalSteps = 0;
        for(EulerRunner r : ThreadList){
            internalSteps += (r.index-r.getThreadNumber())/r.numThreads;
        }
        return internalSteps;
    }

    @Override
    public String getMethodName() {
        return "Euler series approximation";
    }
}
