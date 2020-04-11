package Main;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final int MAX_PRECISION = 100;

    public static void someDelay(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int precision(BigDecimal res, BigDecimal newRes){
        int precision = 0;
        BigDecimal difference = newRes.subtract(res);
        MathContext mc = new MathContext(2, RoundingMode.DOWN);
        for(int i = 0;i<MAX_PRECISION+5;i++){
            if(difference.round(mc).intValue() == 0) {
                difference = difference.movePointRight(1);
                precision ++;
            }else{
                break;
            }
        }
        return precision;
    }

    public static void main(String[] args) {
        CalculatePi pi = new Ramanujan();

        System.out.println("Start: " + pi.getMethodName());
        pi.startCalculation(1);

        int prec = 0;
        BigDecimal result = BigDecimal.ZERO;
        long timeStart = System.currentTimeMillis();
        int i = 0;
        while(prec < MAX_PRECISION){
            someDelay();
            BigDecimal newResult = pi.getValue();
            int newPrec = precision(result,newResult);
            if(newPrec != prec){
                System.out.println("pi (" + newPrec + "): " + newResult);
                prec = newPrec;
            }
            result = newResult;
        }
        System.out.println("Last pi: " + result);
        System.out.println("Pi: "+ result);
        long timeStop = System.currentTimeMillis();
        pi.stopCalculation();
        System.out.println((timeStop - timeStart) + " ms");
        System.out.println(pi.getInternalSteps() + " calculation steps");
    }
}
