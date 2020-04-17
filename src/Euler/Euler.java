package Euler;

import Main.CalculatePi;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Euler implements CalculatePi {
    //Konstanten
    public final MathContext MC = new MathContext(100, RoundingMode.HALF_EVEN);

    public ArrayList<EulerRunner> ThreadList = new ArrayList<EulerRunner>();
    public EulerRunner r;

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
        sum = sum.multiply(new BigDecimal(6));
        sum = sum.sqrt(MC);
        return sum;
    }

    @Override
    public int getInternalSteps() {
        int internalSteps = 0;
        for(EulerRunner r : ThreadList){
            internalSteps += (r.getIndex()-r.STARTINDEX)/r.NUMTHREADS;
        }
        return internalSteps;
    }

    @Override
    public String getMethodName() {
        return "Euler series approximation";
    }
}
