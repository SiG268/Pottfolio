package Exceptions;

/**
 * Wird geschmissen wenn ein Integer ins Negative überfliest
 */
public class IntegerOverflowException extends Exception {
    public IntegerOverflowException(String errorMessage){
        super(errorMessage);
    }
}
