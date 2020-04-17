package Main;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public abstract class PiCalculationThread extends Thread{
    //Konstanten
    public final MathContext MC = new MathContext(1000, RoundingMode.HALF_EVEN);
    public final int STARTINDEX;
    public final int NUMTHREADS;

    //Kontrollvariable um den Thread anzuhalten
    public volatile boolean running = true;

    //Zwischenspeicher für die Teilsumme (Ergebnis) des Threads
    public BigDecimal parcialSum = BigDecimal.ZERO;

    //Fortlaufender Index, wird in run() erhöht
    private int index;

    //getter und setter
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    //Allgemeiner Constructor, der Index, STARTINDEX und NUMTHREADS initialisiert
    public PiCalculationThread(int startIndex, int numThreads){
        this.index = startIndex;
        this.STARTINDEX =startIndex;
        this.NUMTHREADS = numThreads;
    }

    //Abstracte Methode zur Berechnung des Summenglieds aus dem Index
    //wird in der Kindklasse implementiert
    public abstract BigDecimal CalculateSummand(int index);
}
