package Main;

import MonteCarlo.MonteCarlo;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class Main {
    //Konstanten
    public static final int MAX_PRECISION = 5;

    //Delaymethode
    // Erhält Einheit und länge des delays. Wartet für die gewählte Zeit
    public static void someDelay(TimeUnit unit,int delay){
        try {
            unit.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*Vergleicht zwei BigDecimal Zahlen und gibt die Anzahl der gleichen Nachkommastellen
    (bis die erste Stelle sich unterscheidet) zurück*/
    public static int precision(BigDecimal res, BigDecimal newRes){
        //Returnwert. Wenn die Zahlen vor dem Komma nicht übereinstimmen return -1
        int precReturn= -1;
        //numerals ist die kleinere Anzahl an Nachkommastellen der Übergabeparameter
        int numerals = Math.min(getRealPrecision(res),getRealPrecision(newRes));

        //Vergleichsschleife. Bricht ab wenn die Nachkommastellen der 'kürzeren' Zahl überprüft wurden
        while(numerals > precReturn){
            //Vergleich der Vorkomma Zahl
            if(res.intValue()!=newRes.intValue()){
                break;
            }
            //Subtrahiert die Zahl vor dem Komma (aus X.YZ wird 0.YZ)
            res= res.subtract(new BigDecimal(res.intValue()));
            newRes= newRes.subtract(new BigDecimal(newRes.intValue()));
            //todo throw IntegerOverflow RuntimeException


            //Verschiebt den Dezimalpunkt eine Stelle nach Rechts
            //die erste Nachkommastelle steht jetzt vor dem Komma
            res = res.movePointRight(1);
            newRes = newRes.movePointRight(1);
            //erhöhe den Returnwert
            precReturn++;
        }
        return precReturn;
    }

    //Gibt die Anzahl an Nachkommastellen einer BigDecimal zurück
    public static int getRealPrecision(BigDecimal number){
        //Schneidet die Nachkommastellen ab
        int preNumerals = number.intValue();
        //return Gesamtstellen - Stellen vor dem Komma
        return number.precision()-new BigDecimal(preNumerals).precision();
    }

    //TO-DO des Programms
    public static void main(String[] args) {
        CalculatePi pi = new MonteCarlo();
        //Startnachricht und Start der Berechnung
        System.out.println("Start: " + pi.getMethodName());
        pi.startCalculation(8);

        int prec = 0;
        BigDecimal result = BigDecimal.ZERO;
        long timeStart = System.currentTimeMillis();
        int i = 0;
        while(prec < MAX_PRECISION){
            someDelay(TimeUnit.SECONDS,1);
            BigDecimal newResult = pi.getValue();
            int newPrec = precision(result,newResult);
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
