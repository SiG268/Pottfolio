package Ramanujan;

import Main.PiCalculationThread;
import java.math.BigDecimal;

/**
 * Threadklasse des Ramanujan Approximations Verfahrens
 */
public class RamanujanRunner extends PiCalculationThread {
    //Konstanten
    public final BigDecimal NUM1103 = new BigDecimal(1103);
    public final BigDecimal NUM26390 = new BigDecimal(26390);
    public final BigDecimal NUM396 = new BigDecimal(396);
    //Zwischenspeicher fuer Fakultaetswerte
    /** Buffer fuer die Fakultaetsberechnung*/
    private BigDecimal bufferFactorial = BigDecimal.ONE;
    /** Buffer fuer die Fakultaetsberechnung*/
    private BigDecimal bufferFactorial4 = BigDecimal.ONE;


    /**
     * Konstruktor<br>
     * Ruft Superkonstruktor von {@link PiCalculationThread} auf
     * @param startIndex Start Index
     * @param numThreads Anzahl an Threads
     */
    public RamanujanRunner(int startIndex, int numThreads){
        super(startIndex, numThreads);
    }

    /**
     * Berechnet die Fakultaet fuer n und 4n
     */
    private void updateFactorial(){
        //ueberprueft ob die Methode das erste Mal ausgefuehrt wird (Startindex ist immer kleiner als Threadzahl)
        //Es gilt fuer n > k: n! = k! * (k+1)*...*(n)
        // (k+1) bis n 'fehlen' in der Berechnung
        //Beim ersten Aufruf -> else
        if(getIndex().subtract(NUMTHREADS).compareTo(BigDecimal.ZERO)!=-1) {
            //Multipliziert den FakultaetsBuffer mit den 'fehlenden' Zahlen
            for (int i = getIndex().intValue(); i > (getIndex().subtract(NUMTHREADS)).intValue(); i--) {
                bufferFactorial = bufferFactorial.multiply(new BigDecimal(i));
            }
            //Multipliziert den FakultaetsBuffer fuer 4n mit den 'fehlenden' Zahlen
            for (int i = getIndex().intValue()*4; i > (getIndex().subtract(NUMTHREADS)).intValue()*4; i--) {
                bufferFactorial4 = bufferFactorial4.multiply(new BigDecimal(i));
            }

        }else{
            //Beim ersten Ausfuehren
            //Berechnet die Fakultaet mit n! = n*(n-1)*...*1
            for (int i = getIndex().intValue(); i > 0; i--) {
                bufferFactorial = bufferFactorial.multiply(new BigDecimal(i));
            }
            //Berechnet die Fakultaet mit 4n! = 4n*(4n-1)*...*1
            for (int i = (4 * getIndex().intValue()); i > 0; i--) {
                bufferFactorial4 = bufferFactorial4.multiply(new BigDecimal(i));
            }
        }
    }

    @Override
    public BigDecimal CalculateSummand(BigDecimal index){
        //Aktualisiert die Fakultaetswerte auf den aktuellen Index
        updateFactorial();
        //Berechnet den Zaehler
        BigDecimal counter = bufferFactorial4.multiply(NUM1103.add(NUM26390.multiply(getIndex())));
        //Berechnet den Nenner
        BigDecimal denum = bufferFactorial.pow(4);
        denum = denum.multiply(NUM396.pow(getIndex().intValue()*4));
        //return Zaehler / Nenner
        return counter.divide(denum,super.MC);
    }

}
