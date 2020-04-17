package Leibniz;

import Main.CalculatePi;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Leibniz implements CalculatePi {

    public ArrayList<LeibnizRunner> ThreadList = new ArrayList<LeibnizRunner>();
    public LeibnizRunner r;


    @Override
    public boolean startCalculation() {
        r = new LeibnizRunner(0,1);
        ThreadList.add(r);
        r.start();
        return false;
    }

    @Override
    public boolean startCalculation(int numThreads) {
        for(int i = 1;i<=numThreads;i++) {
            r = new LeibnizRunner(i,numThreads);
            ThreadList.add(r);
            r.start();
        }
        return false;
    }

    @Override
    public void stopCalculation() {
        for(LeibnizRunner t : ThreadList)
            t.running = false;
    }


    @Override
    public BigDecimal getValue() {
        BigDecimal pi = new BigDecimal(0);
        for(LeibnizRunner r : ThreadList){
            pi = pi.add(r.parcialSum);
        }
        pi = pi.multiply(new BigDecimal("4"));
        return pi;
    }

    @Override
    public int getInternalSteps() {
        int internalSteps = 0;
        for(LeibnizRunner r : ThreadList){
            internalSteps += (r.getIndex()-(2*r.STARTINDEX)+1)/r.NUMTHREADS;
        }
        return internalSteps;
    }

    @Override
    public String getMethodName() {
        return "Leibniz series approximation";
    }
}
