package Exceptions;

/**
 * Wird geschmissen falls eine Ergebnisabfrage erfolgt, bevor eine Berechnung durchgefuehrt wurde.
 */
public class NoCalculationExecutedException extends Exception {
    public NoCalculationExecutedException(String errorMessage) {
        super(errorMessage);
    }
}
