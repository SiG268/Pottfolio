package Ramanujan;

import Main.PiCalculationThread;
import java.math.BigDecimal;

public class RamanujanRunner extends PiCalculationThread {
    //Konstanten
    public final BigDecimal NUM1103 = new BigDecimal(1103);
    public final BigDecimal NUM396 = new BigDecimal(396);
    //Zwischenspeicher für Fakultätswerte
    private BigDecimal bufferFactorial = BigDecimal.ONE;
    private BigDecimal bufferFactorial4 = BigDecimal.ONE;

    //Constructor matches SuperConstructor
    public RamanujanRunner(int startIndex, int numThreads){
        super(startIndex, numThreads);
    }

    //Aktualisiert die Fakultätswerte auf den aktuellen Index
    public void updateFactorial(){
        //Überprüft ob die Methode das erste Mal ausgeführt wird (Startindex ist immer kleiner als Threadzahl)
        //Es gilt für n > k: n! = k! * (k+1)*...*(n)
        // (k+1) bis n 'fehlen' in der Berechnung
        //Beim ersten Aufruf -> else
        if(getIndex()- NUMTHREADS >=0) {
            //Multipliziert den FakultätsBuffer mit den 'fehlenden' Zahlen
            for (int i = getIndex(); i > (getIndex() - NUMTHREADS); i--) {
                bufferFactorial = bufferFactorial.multiply(new BigDecimal(i));
            }
            //Multipliziert den FakultätsBuffer für 4n mit den 'fehlenden' Zahlen
            for (int i = (4 * getIndex()); i > ((getIndex() - NUMTHREADS) * 4); i--) {
                bufferFactorial4 = bufferFactorial4.multiply(new BigDecimal(i));
            }

        }else{
            //Beim ersten Ausführen
            //Berechnet die Fakultät mit n! = n*(n-1)*...*1
            for (int i = getIndex(); i > 0; i--) {
                bufferFactorial = bufferFactorial.multiply(new BigDecimal(i));
            }
            //Berechnet die Fakultät mit 4n! = 4n*(4n-1)*...*1
            for (int i = (4 * getIndex()); i > 0; i--) {
                bufferFactorial4 = bufferFactorial4.multiply(new BigDecimal(i));
            }
        }
    }

    //Berechnet den Summanden für einen Index
    @Override
    public BigDecimal CalculateSummand(int index){
        //Aktualisiert die Fakultätswerte auf den aktuellen Index
        updateFactorial();
        //Berechnet den Zähler
        BigDecimal counter = bufferFactorial4.multiply(NUM1103.add(new BigDecimal((26390*index))));
        //Berechnet den Nenner
        BigDecimal denum = bufferFactorial.pow(4);
        denum = denum.multiply(NUM396.pow(index*4));
        //Summand = Zähler / Nenner
        BigDecimal summand =counter.divide(denum,super.MC);
        return summand;
    }

}
