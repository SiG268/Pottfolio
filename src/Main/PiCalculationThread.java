package Main;

import Exceptions.IntegerOverflowException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Abstrakte Elternklasse von Euler-,Ramanujan- und LeibnizRunner
 */
public abstract class PiCalculationThread extends Thread{
    //Konstanten
     /** {@link #MC} -  Der bei der Berechnung verwendete MathContext*/
    public final MathContext MC = new MathContext(1000, RoundingMode.HALF_EVEN);

    /** {@link #STARTINDEX} -  Start Index von dem Thread welcher im Konstruktor übergeben wird*/
    public final int STARTINDEX;

    /** {@link #NUMTHREADS}  - Anzahl der Threads welcher vom Konstruktor gesetzt wird*/
    public final BigDecimal NUMTHREADS;


    /**Ein-/Ausschalter für den Thread */
    public boolean running = true;

    /** Teilendergebnis vom Thread*/
    public BigDecimal parcialSum = BigDecimal.ZERO;
    /**
     * Index welcher bei der Berechnung der Einzelnen Summanten verwendet wird
     * Dieser wird von der run Methode am Ende increment
     */
    private BigDecimal index;

    /**
     * Getter vom Index
     * @return Liefert den Wert vom Index zurück
     */
    public BigDecimal getIndex() {
        return index;
    }

    /**
     * Setzt den Wert vom Index auf den Wert vom Übergabeparameter
     * @param index BigDecimal welcher auf den Index gesetzt wird
     */
    public void setIndex(BigDecimal index) {
        this.index = index;
    }


    /**
     * Konstruktor welcher den StartIndex und die Thread Anzahl festlegt
     * @param startIndex
     * @param numThreads
     */
    public PiCalculationThread(int startIndex, int numThreads){
        this.index = new BigDecimal(startIndex);
        this.STARTINDEX =startIndex;
        this.NUMTHREADS = new BigDecimal(numThreads);
    }

    /**
     * Berechnet den nächsten Summanten und addiert diesen zum Teilendergebnis des Threads
     * Incrementiert den Index um die Anzahl an Threads
     */
    public void run() {
        while(running) {
            parcialSum = parcialSum.add(CalculateSummand(getIndex()));
            setIndex(getIndex().add(NUMTHREADS));
        }
    }

    /**
     * Summantenberechnung für die einzelnen Threads
     * @param index Index des zu berechnenden Summanten
     * @return den berechneten Summanten
     */
    public abstract BigDecimal CalculateSummand(BigDecimal index);
}
