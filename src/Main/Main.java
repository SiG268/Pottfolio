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
        int precReturn= -1;
        //Check Min Numerals
        int numerals = Math.min(getRealPrecision(res),getRealPrecision(newRes));

        while(numerals > precReturn){
            if(res.intValue()!=newRes.intValue()){
                break;
            }
            //Cut the head
            res= res.subtract(new BigDecimal(res.intValue()));
            newRes= newRes.subtract(new BigDecimal(newRes.intValue()));
            //todo throw IntegerOverflow Exception


            //Move to Right
            res = res.movePointRight(1);
            newRes = newRes.movePointRight(1);
            precReturn++;
        }
        return precReturn;
    }


    public static int getRealPrecision(BigDecimal number){
        int preNumerals = number.intValue();
        return number.precision()-new BigDecimal(preNumerals).precision();
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
