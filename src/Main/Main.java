package Main;

import ApproximativeReihenverfahren.Euler.Euler;
import ApproximativeReihenverfahren.Leibniz.Leibniz;
import ApproximativeReihenverfahren.Ramanujan.Ramanujan;
import Exceptions.IntegerOverflowException;
import Exceptions.NoCalculationExecutedException;
import MonteCarloVerfahren.MonteCarloFast.MonteCarloFast;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Main Klasse<br>
 *   Zustaendig fuer die Benutzereingabe, Berchnung der Genauigkeit, und starten/stoppen der Verfahren
 */
public class Main {
    //Konstanten
    /**
     * Enthaelt den Wert bis zu welcher Praezision das PI berechnet wird
     */
    private static final int MAX_PRECISION = 12;
    /**
     * MathContext ein Pi Ergebnis auf 30 Nachkommastellen rundet
     */
    private static MathContext MC30 = new MathContext(31, RoundingMode.HALF_EVEN);
    /**
     * Wert bei dem der Interger in das negative uebergeht
     */
    private static final BigDecimal OVERFLOW_INT = new BigDecimal("2147483648");

    /**
     * Delaymethode: <br>
     * Erhaelt Einheit und laenge des delays. Wartet fuer die gewaehlte Zeit
     *
     * @param unit  Einheit des Delays
     * @param delay Laenge des Delays
     */

    private static void someDelay(TimeUnit unit, int delay) {
        try {
            unit.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Benutzereingabe zur Auswahl des Approximationsverfahren
     * @return ausgewaehltes Approximationsverfahren
     */
    private static CalculatePi userInputApprox() {
        Scanner scanner = new Scanner(System.in);
        CalculatePi pi;
        int number = 0;
        System.out.println("Welches Approximations Verfahren moechten Sie verwenden?\n" +
                "1 - Euler\n" +
                "2 - Leibniz\n" +
                "3 - Ramanujan\n" +
                "4 - Monte-Carlo");
        while (true) {
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                switch (number) {
                    case 1:
                        System.out.println("Euler");
                        return new Euler();

                    case 2:
                        System.out.println("Leibniz");
                        return new Leibniz();

                    case 3:
                        System.out.println("Ramanujan");
                        return new Ramanujan();

                    case 4:
                        System.out.println("Monte-Carlo");
                        return new MonteCarloFast();

                    default:
                        System.out.println("Bitte nur Zahlen von 1-4 eingeben");
                        break;
                }
            } else {
                System.out.println("Bitte nur Zahlen von 1-4 eingeben");
                scanner.next();
            }
        }
    }

    /**
     * Benutzereingabe zur festlegung der Threads
     * @return Anzahl der Threads
     */
    private static int userInputThreads() {
        Scanner scanner = new Scanner(System.in);
        int number = 0;
        do {
            System.out.println("Wie viele Threads moechten sie verwenden? (1-n)");
            while (!scanner.hasNextInt()) {
                System.out.println("Bitte gib eine positive natuerliche Zahl ein");
                scanner.next(); // this is important!
            }
            number = scanner.nextInt();
        } while (number <= 0);
        return number;
    }

    /**
     * Errechnet die Praezision von zweit uebergebenen Werten.Vergleicht zwei BigDecimal Zahlen und gibt die Anzahl der gleichen Nachkommastellen zurueck.
     *
     * @param res    BigDecimal welches verglichen wird
     * @param newRes BigDecimal welches verglichen wird
     * @return -1 keine Praezision, 0-n gleichen Nachkommastellen der uebergabeparameter
     * @throws IntegerOverflowException wird geschmissen wenn die BigDecimals zu gross sind um diese in ein Integer umzuwandeln
     */
    /*Vergleicht zwei BigDecimal Zahlen und gibt die Anzahl der gleichen Nachkommastellen
    (bis die erste Stelle sich unterscheidet) zurueck*/
    private static int precision(BigDecimal res, BigDecimal newRes) throws IntegerOverflowException {
        if (res.compareTo(OVERFLOW_INT) != -1 || newRes.compareTo(OVERFLOW_INT) != -1) {
            throw new IntegerOverflowException("Can´t evaluate pi precision");
        }
        //Returnwert. Wenn die Zahlen vor dem Komma nicht uebereinstimmen return -1
        int precReturn = -1;
        //numerals ist die kleinere Anzahl an Nachkommastellen der uebergabeparameter
        int numerals = Math.min(getRealPrecision(res), getRealPrecision(newRes));

        //Vergleichsschleife. Bricht ab wenn die Nachkommastellen der 'kuerzeren' Zahl ueberprueft wurden
        while (numerals > precReturn) {
            //Vergleich der Vorkomma Zahl
            if (res.intValue() != newRes.intValue()) {
                break;
            }
            //Subtrahiert die Zahl vor dem Komma (aus X.YZ wird 0.YZ)
            res = res.subtract(new BigDecimal(res.intValue()));
            newRes = newRes.subtract(new BigDecimal(newRes.intValue()));

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
     *
     * @param number BigDecimal bei dem die Nachkommastellen berechnet werden sollen
     * @return int Wert von der Anzahl der Nachkommastellen
     */
    //Gibt die Anzahl an Nachkommastellen einer BigDecimal zurueck
    private static int getRealPrecision(BigDecimal number) {
        //Schneidet die Nachkommastellen ab
        int preNumerals = number.intValue();
        //return Gesamtstellen - Stellen vor dem Komma
        return number.precision() - new BigDecimal(preNumerals).precision();
    }

    //TO-DO des Programms

    /**
     * Main-Methode zur Durchfuehrung der Berechnung
     * @param args Parameter
     */
    public static void main(String[] args) {
        //Benutzereingaben
        CalculatePi pi = userInputApprox();
        int threads = userInputThreads();

        //Startnachricht und Start der Berechnung
        System.out.println("Start: " + pi.getMethodName()+ "(" + threads + " Threads)");
        pi.startCalculation(threads);

        int prec = 0;
        BigDecimal result = BigDecimal.ZERO;
        long timeStart = System.currentTimeMillis();
        while (prec < MAX_PRECISION) {
            someDelay(TimeUnit.SECONDS, 5);
            BigDecimal newResult = BigDecimal.ZERO;
            try {
                newResult = pi.getValue();
            } catch (NoCalculationExecutedException e) {
                e.printStackTrace();
                break;
            }
            int newPrec = 0;
            try {
                newPrec = precision(result, newResult);
            } catch (IntegerOverflowException e) {
                e.printStackTrace();
                System.out.println("pi (" + newPrec + "): " + newResult.round(MC30));
                break;
            }
            if (newPrec != prec) {
                System.out.println("pi (" + newPrec + "): " + newResult.round(MC30));
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
