package Main;


import Exceptions.IntegerOverflowException;
import Exceptions.NoCalculationExecutedException;
import Euler.Euler;
import Leibniz.Leibniz;
import MonteCarlo.MonteCarlo;
import MonteCarloFast.MonteCarloFast;
import Ramanujan.Ramanujan;


import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class Main {
    //Konstanten
    /**Enthaelt den Wert bis zu welcher Praezision das PI berechnet wird*/
    public static final int MAX_PRECISION = 12;

    /**Wert bei dem der Interger in das negative uebergeht*/
    public static final BigDecimal OVERFLOW_INT = new BigDecimal("2147483648");

    /**
     * Delaymethode: <br>
     * Erhaelt Einheit und laenge des delays. Wartet fuer die gewaehlte Zeit
     * @param unit  Einheit des Delays
     * @param delay Laenge des Delays
     */

    public static void someDelay(TimeUnit unit,int delay){
        try {
            unit.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Errechnet die Praezision von zweit uebergebenen Werten.Vergleicht zwei BigDecimal Zahlen und gibt die Anzahl der gleichen Nachkommastellen zurueck.
     *
     * @param res BigDecimal welches verglichen wird
     * @param newRes BigDecimal welches verglichen wird
     * @return -1 keine Praezision, 0-n gleichen Nachkommastellen der uebergabeparameter
     * @throws IntegerOverflowException wird geschmissen wenn die BigDecimals zu gross sind um diese in ein Integer umzuwandeln
     */
    /*Vergleicht zwei BigDecimal Zahlen und gibt die Anzahl der gleichen Nachkommastellen
    (bis die erste Stelle sich unterscheidet) zurueck*/
    public static int precision(BigDecimal res, BigDecimal newRes) throws IntegerOverflowException{
        if(res.compareTo(OVERFLOW_INT)!=-1||newRes.compareTo(OVERFLOW_INT)!=-1){
            throw new IntegerOverflowException("Can´t evaluate pi precision");
        }
        //Returnwert. Wenn die Zahlen vor dem Komma nicht uebereinstimmen return -1
        int precReturn= -1;
        //numerals ist die kleinere Anzahl an Nachkommastellen der uebergabeparameter
        int numerals = Math.min(getRealPrecision(res),getRealPrecision(newRes));

        //Vergleichsschleife. Bricht ab wenn die Nachkommastellen der 'kuerzeren' Zahl ueberprueft wurden
        while(numerals > precReturn){
            //Vergleich der Vorkomma Zahl
            if(res.intValue()!=newRes.intValue()){
                break;
            }
            //Subtrahiert die Zahl vor dem Komma (aus X.YZ wird 0.YZ)
            res= res.subtract(new BigDecimal(res.intValue()));
            newRes= newRes.subtract(new BigDecimal(newRes.intValue()));

            //Verschiebt den Dezimalpunkt eine Stelle nach Rechts
            //die erste Nachkommastelle steht jetzt vor dem Komma
            res = res.movePointRight(1);
            newRes = newRes.movePointRight(1);
            //erhoehe den Returnwert
            precReturn++;
        }
        return precReturn;
    }

    /**
     * Berechnet die Anzahl der Nachkommastellen
     * @param number BigDecimal bei dem die Nachkommastellen berechnet werden sollen
     * @return int Wert von der Anzahl der Nachkommastellen
     */
    //Gibt die Anzahl an Nachkommastellen einer BigDecimal zurueck
    private static int getRealPrecision(BigDecimal number){
        //Schneidet die Nachkommastellen ab
        int preNumerals = number.intValue();
        //return Gesamtstellen - Stellen vor dem Komma
        return number.precision()-new BigDecimal(preNumerals).precision();
    }

    //TO-DO des Programms
    public static void main(String[] args) {
        CalculatePi pi = new Euler();
        //Startnachricht und Start der Berechnung
        System.out.println("Start: " + pi.getMethodName());
        pi.startCalculation(8);

        int prec = 0;
        BigDecimal result = BigDecimal.ZERO;
        long timeStart = System.currentTimeMillis();
        while(prec < MAX_PRECISION){
            someDelay(TimeUnit.SECONDS,5);
            BigDecimal newResult = BigDecimal.ZERO;
            try {
                newResult = pi.getValue();
            } catch (NoCalculationExecutedException e) {
                e.printStackTrace();
                break;
            }
            int newPrec = 0;
            try {
                newPrec = precision(result,newResult);
            } catch (IntegerOverflowException e) {
                e.printStackTrace();
                System.out.println("pi (" + newPrec + "): " + newResult);
                break;
            }
            if(newPrec != prec){
                System.out.println("pi (" + newPrec + "): " + newResult);
                prec = newPrec;
            }
            result = newResult;
        }
        long timeStop = System.currentTimeMillis();
        pi.stopCalculation();
        System.out.println((timeStop - timeStart) + " ms");
        System.out.println(pi.getInternalSteps() + " calculation steps");
    }
}
