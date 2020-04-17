package Leibniz;

import Main.PiCalculationThread;

import java.math.BigDecimal;

public class LeibnizRunner extends PiCalculationThread {
    //Constructor matches SuperConstructor
    //Index und numThreads werden verändert, da CalculateSummand ein Summandenpaar berechnet
    public LeibnizRunner(int startIndex, int numThreads) {
        super(2*startIndex-1, 2*numThreads);
    }

    //Berechnet das Summandenpaar für einen Index
    //summand = 1/(2*index-1)-1/(2*index+1)
    @Override
    public BigDecimal CalculateSummand(int index){
        BigDecimal denumMinus = new BigDecimal(2*index-1);
        BigDecimal denumPlus = new BigDecimal(2*index+1);
        BigDecimal summand = BigDecimal.ONE;
        summand = summand.divide(denumMinus, MC);
        summand = summand.subtract(BigDecimal.ONE.divide(denumPlus, MC));
        return summand;
    }

    //TO-DO des Threads
    //Erhöht die Teilsumme um den nächsten Summanden
    //Erhöht den Index um NUMTHREADS
    @Override
    public void run(){
        while(running == true) {
            parcialSum = parcialSum.add(CalculateSummand(getIndex()));
            setIndex(getIndex() + NUMTHREADS);
        }
    }
}
