package Main;

import Exceptions.NoCalculationExecutedException;

import java.math.BigDecimal;

public interface CalculatePi {
    public boolean startCalculation();
    public boolean startCalculation(int numThreads);
    public void stopCalculation();
    public BigDecimal getValue() throws NoCalculationExecutedException;
    public int getInternalSteps();
    public String getMethodName();
}
