package MonteCarlo;
import Main.CalculatePi;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

public class MonteCarlo implements CalculatePi {
    public ArrayList<MonteCarloRunner> ThreadList = new ArrayList<MonteCarloRunner>();
    @Override
    public boolean startCalculation() {
        MonteCarloRunner thread= new MonteCarloRunner();
        ThreadList.add(thread);
        thread.start();
        return false;
    }

    @Override
    public boolean startCalculation(int numThreads) {
        for(int i=0; i<numThreads ; i++) {
            MonteCarloRunner thread = new MonteCarloRunner(numThreads);
            ThreadList.add(thread);
            thread.start();
        }
        return false;
    }

    @Override
    public void stopCalculation() {
        for (MonteCarloRunner thread:ThreadList) {
            thread.running=false;
        }
    }

    @Override
    public BigDecimal getValue() {
        BigDecimal pi;
        BigDecimal treffer=BigDecimal.ZERO;
        BigDecimal versuche=BigDecimal.ZERO;
        for (MonteCarloRunner thread:ThreadList) {
            treffer = treffer.add(new BigDecimal(thread.getTreffer()));
            versuche= versuche.add(new BigDecimal(thread.getVersuche()));
        }
        pi=treffer.divide(versuche, MathContext.DECIMAL128).multiply(new BigDecimal("4"));
        return pi;
    }

    @Override
    public int getInternalSteps() {
        int steps=0;
        for (MonteCarloRunner thread:ThreadList) {
            steps+=thread.getVersuche();
        }
        return steps;
    }

    @Override
    public String getMethodName() {
        return "Monte-Carlo approximation";
    }
}
