package Ramanujan;

import Euler.EulerRunner;
import Main.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Ramanujan implements CalculatePi {
    //Konstanten
    public final MathContext MC = new MathContext(10000, RoundingMode.HALF_EVEN);
    //ArrayList zur Threadverwaltung
    public final ArrayList<RamanujanRunner> ThreadList = new ArrayList<RamanujanRunner>();
    //Koeffizient vor der Summe
    public final BigDecimal COEFFICIENT = new BigDecimal(8).sqrt(MC).divide(new BigDecimal(9801), MC);
    //Platzhalter für EulerRunner Initialisierung in startCalculation
    //maybe delete this
    public RamanujanRunner r;

    //startet die Single Thread Berechnung
    //Thread erzeugen -> zur ThreadList hinzufügen -> Thread starten
    @Override
    public boolean startCalculation() {
        return startCalculation(1);
    }

    //startet die multi Thread Berechnung
    //Erhöht den Startindex bei jedem Durchgang der for Schleife
    //Stellt damit sicher das jeder Thread andere Summanden berechnet -> Dopplungen verhindern
    @Override
    public boolean startCalculation(int numThreads) {
        if(ThreadList.size()>0){
            boolean threadrunning=false;
            for (RamanujanRunner t:ThreadList) {
                threadrunning=threadrunning || t.running;
            }
            if(!threadrunning){
                ThreadList.clear();
            }
            else{
                return false;
            }
        }
                for(int i = 0;i<numThreads;i++) {
            RamanujanRunner t = new RamanujanRunner(i,numThreads);
            ThreadList.add(t);
            t.start();
        }
        return true;
    }

    //Beendet die Berechnung
    @Override
    public void stopCalculation() {
        for(RamanujanRunner t : ThreadList)
            t.running = false;
        }

    //Berechnet den aktuellen Wert für Pi und gibt diesen zurück
    @Override
    public BigDecimal getValue() {
        BigDecimal sum = BigDecimal.ZERO;
        //Addiert die Teilsummen der Threads zusammen
        for(RamanujanRunner r : ThreadList){
            sum = sum.add(r.parcialSum);
        }
        //Summe (der Teilsummen) * Koeffizient
        sum = sum.multiply(COEFFICIENT);
        //Kehrwert bilden
        BigDecimal pi = BigDecimal.ONE.divide(sum, MC);
        return pi;
    }


    //Berechnet die gemachten Berechnungsschritte und gibt sie zurück
    @Override
    public int getInternalSteps() {
        int internalSteps = 0;
        //Addiert die internalSteps der einzelnen Threads
        for (RamanujanRunner r : ThreadList) {
            //Es gilt: Index = STARTINDEX+NUMTHREADS*Rechenschritte
            // => Schritte = (Index-STARTINDEX)/NUMTHREADS
            internalSteps += (r.getIndex() - r.STARTINDEX) / r.NUMTHREADS;
        }
        return internalSteps;
    }

    //Gibt den Namen der verwendeten Berechnungsmethode aus
    @Override
    public String getMethodName() {
        return "Ramanujan series approximation";
    }
}
