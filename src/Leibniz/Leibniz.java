package Leibniz;

import Euler.EulerRunner;
import Main.CalculatePi;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Leibniz implements CalculatePi {
    //ArrayList zur Threadverwaltung
    public final ArrayList<LeibnizRunner> ThreadList = new ArrayList<LeibnizRunner>();
    //Platzhalter für EulerRunner Initialisierung in startCalculation
    //maybe delete this
    public LeibnizRunner r;

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
            for (LeibnizRunner t:ThreadList) {
                threadrunning=threadrunning || t.running;
            }
            if(!threadrunning){
                ThreadList.clear();
            }
            else{
                return false;
            }
        }
        for(int i = 1;i<=numThreads;i++) {
            r = new LeibnizRunner(i,numThreads);
            ThreadList.add(r);
            r.start();
        }
        return true;
    }

    //Beendet die Berechnung
    @Override
    public void stopCalculation() {
        for(LeibnizRunner t : ThreadList)
            t.running = false;
    }

    //Berechnet den aktuellen Wert für Pi und gibt diesen zurück
    @Override
    public BigDecimal getValue() {
        BigDecimal pi = BigDecimal.ZERO;
        //Addiert die Teilsummen der Threads zusammen
        for(LeibnizRunner r : ThreadList){
            pi = pi.add(r.parcialSum);
        }
        //Teilsummen ergeben pi/4 -> Auflösen nach Pi
        pi = pi.multiply(new BigDecimal("4"));
        return pi;
    }

    //Berechnet die gemachten Berechnungsschritte und gibt sie zurück
    @Override
    public int getInternalSteps() {
        int internalSteps = 0;
        //Addiert die internalSteps der einzelnen Threads
        for(LeibnizRunner r : ThreadList){
            // => Schritte = (Index-STARTINDEX)/NUMTHREADS
            internalSteps += (r.getIndex()-(r.STARTINDEX))/r.NUMTHREADS;
        }
        return internalSteps;
    }

    //Gibt den Namen der verwendeten Berechnungsmethode aus
    @Override
    public String getMethodName() {
        return "Leibniz series approximation";
    }
}
