package Main;

import Exceptions.NoCalculationExecutedException;

import java.math.BigDecimal;

/**
 * Schnittstelle fuer die PI-Kalkulationsklassen der verschiedenen Approximations Verfahren
 */
public interface CalculatePi {
    /**
     * Single Threading
     * Ruft {@link #startCalculation(int)} mit einem Thread als Integer auf
     *
     * @return liefert true zurueck wenn keine Calculation laeuft und alle Threads gestartet wurden
     */
    public boolean startCalculation();

    /**
     * Multi Threading<br>
     * Ueberprueft ob laufende Threads vorhanden sind undl leert die Threadliste falls noch gestoppte Threads in dieser sind.<br>
     * Ruft den Konstrukter des jeweiligen Threads auf und uebermittelt ihm gegebenfalls benoetigten initialiserungs Parameter.<br>
     * Der Start Index fuer jeden Thread wird bei jeder Iteration um den Wert Eins inkrementiert.<br>
     * Die einzelnen Threads werden einer Threadliste zugeordnet und gestartet.
     *
     * @param numThreads Die Anzahl der zu erzeugenden Threads
     * @return liefert true zurueck wenn keine Calculation laeuft und alle Threads gestartet wurden
     */
    public boolean startCalculation(int numThreads);

    /**
     * stoppt die Threads
     */
    public void stopCalculation();

    /**
     * Fragt die Einzelergebnisse der Threads ab und berechnet daraus einen approximativen Wert fuer PI
     *
     * @return liefert das berechnete Ergbnis fuer PI zurueck
     * @throws NoCalculationExecutedException tritt auf wenn die Summe der Einzelergebnisse von den Threads gleich 0 ist. Hierbei wurde somit keine Berechnung von den Threads durchgefuehrt.
     */
    public BigDecimal getValue() throws NoCalculationExecutedException;

    /**
     * Summiert die Schritte der einzelnen Thread auf und gibt diese zurueck
     *
     * @return Liefert die Gesamtzahl der Thread Schritte zurueck
     */
    public int getInternalSteps();

    /**
     * Gibt den Namen der Approximations Methode zurueck
     * @return Name der Approximations Methode
     */
    public String getMethodName();
}
