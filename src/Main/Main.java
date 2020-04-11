package Main;


import jdk.jshell.spi.SPIResolutionException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final int MAX_PRECISION = 20;

    public static void someDelay(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int precision(BigDecimal res, BigDecimal newRes){
        int precReturn= 0; //change to -1 to return -1 if decimal places differ

        MathContext mc = new MathContext(1,RoundingMode.DOWN);
        BigDecimal currentNumeral;
        int realResPrec = res.precision()-res.round(mc).precision();
        int realNewResPrec = newRes.precision()-newRes.round(mc).precision();
        int numerals = Math.min(realResPrec,realNewResPrec);
        while(numerals > precReturn){
            currentNumeral = res.movePointRight(precReturn + 1).round(mc);
            if(currentNumeral.compareTo(newRes.movePointRight(precReturn+1).round(mc))!=0) {
                    break;
            }
            res.subtract(currentNumeral);
            newRes.subtract(currentNumeral);
            precReturn++;
        }
        return precReturn;
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
