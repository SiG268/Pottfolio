package Main;

import java.math.BigDecimal;

public interface CalculatePi {
    public boolean startCalculation();
    public boolean startCalculation(int numThreads);
    public void stopCalculation();
    public BigDecimal getValue();
    public int getInternalSteps();
    public String getMethodName();
}
