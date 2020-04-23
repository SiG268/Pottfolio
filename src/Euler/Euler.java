package Euler;

import Exceptions.IntegerOverflowException;
import Exceptions.NoCalculationExecutedException;
import Leibniz.LeibnizRunner;
import Main.CalculatePi;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Euler implements CalculatePi {
    //Konstanten
    public final MathContext MC = new MathContext(100, RoundingMode.HALF_EVEN);
    //ArrayList zur Threadverwaltung
    private final ArrayList<EulerRunner> ThreadList = new ArrayList<EulerRunner>();
    //Platzhalter für EulerRunner Initialisierung in startCalculation
    //maybe delete this
    public EulerRunner r;

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
            for (EulerRunner t:ThreadList) {
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
            r = new EulerRunner(i,numThreads);
            ThreadList.add(r);
            r.start();
        }
        return true;
    }

    //Beendet die Berechnung
    @Override
    public void stopCalculation() {
        for(EulerRunner t : ThreadList)
            t.running = false;
    }

    //Berechnet den aktuellen Wert für Pi und gibt diesen zurück
    @Override
    public BigDecimal getValue() throws NoCalculationExecutedException {
        BigDecimal pi = BigDecimal.ZERO;
        //Addiert die Teilsummen der Threads zusammen
        for(EulerRunner t : ThreadList){
            pi = pi.add(t.parcialSum);
        }
        if(pi.equals(BigDecimal.ZERO)){
            throw new NoCalculationExecutedException("No calculation step was executed. Increase delay");
        }
        //Teilsummen ergeben (pi^2)/6 -> Auflösen nach Pi
        pi = pi.multiply(new BigDecimal(6));
        pi = pi.sqrt(MC);
        return pi;
    }

    //Berechnet die gemachten Berechnungsschritte und gibt sie zurück
    @Override
    public int getInternalSteps() {
        int internalSteps = 0;
        //Addiert die internalSteps der einzelnen Threads
        for(EulerRunner r : ThreadList){
            //Es gilt: Index = STARTINDEX+NUMTHREADS*Rechenschritte
            // => Schritte = (Index-STARTINDEX)/NUMTHREADS
            internalSteps += (r.getIndex()-r.STARTINDEX)/r.NUMTHREADS;
        }
        return internalSteps;
    }

    //Gibt den Namen der verwendeten Berechnungsmethode aus
    @Override
    public String getMethodName() {
        return "Euler series approximation";
    }
}
