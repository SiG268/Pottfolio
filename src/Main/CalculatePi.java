package Main;

import Exceptions.NoCalculationExecutedException;

import java.math.BigDecimal;

public interface CalculatePi {
    /**single threading
     *Ruft {@link #startCalculation(int)} mit einem Thread als Integer auf
     * @return liefert true zurück wenn keine Calculation läuft und alle Threads gestartet wurden
     */
    public boolean startCalculation();

    /**multi threading
     * Überprüft ob laufende Threads vorhanden sind
     * Leert die Threadliste falls nur gestoppte Threads in dieser sind
     * Ruft den Konstrukter des jeweiligen Threads auf und übermittelt ihm gegebenfalls benötigten initialiserungs Parameter.
     * Der Start Index für jeden Thread wird bei jeder Iteration um eins inkrementiert
     * Die einzelnen Threads werden einer Threadliste zugeordnet und gestartet
     * @param numThreads Die Anzahl der zu erzeugenden Threads
     * @return liefert true zurück wenn keine Calculation läuft und alle Threads gestartet wurden
     */
    public boolean startCalculation(int numThreads);

    /**stop threads
     * stoppt die Threads
     */
    public void stopCalculation();

    /**get current PI value
     * Fragt die Einzelergebnisse der Threads ab und berechnet daraus einen Wert
     * @return liefert das berechnete Ergbnis für PI zurück
     * @throws NoCalculationExecutedException   tritt auf wenn die Summe der Einzelergebnisse von den Threads gleich 0 ist. Hierbei wurde somit keine Berechnung von den Threads durchgeführt.
     */
    public BigDecimal getValue() throws NoCalculationExecutedException;

    /**how many calculation steps are performed
     * Summiert die Schritte der einzelnen Thread auf und gibt diese zurück
     * @return Liefert die Gesamtzahl der Thread Schritte zurück
     */
    public int getInternalSteps();

    /**name of calculus principle
     *
     * @return Name der Approximations Methode
     */
    public String getMethodName();
}
