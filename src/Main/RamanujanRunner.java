package Main;

import java.math.BigDecimal;
import java.math.MathContext;

public class RamanujanRunner extends Thread{
    public boolean running = true;
    public int index;
    public int numThreads;

    BigDecimal facBuffer = new BigDecimal(1);
    BigDecimal facFourBuffer = new BigDecimal(1);


    BigDecimal counter;
    BigDecimal denum;
    BigDecimal parcialSum = new BigDecimal(0);

    public RamanujanRunner(int startIndex, int numThreads){
        this.index = startIndex;
        this.numThreads = numThreads;
    }
    public void updateFactorial(){
        if(index-numThreads>0) {
            for (int i = index; i > (index - numThreads); i--) {
                facBuffer = facBuffer.multiply(new BigDecimal(i));
            }
            for (int i = (4 * index); i > ((index - numThreads) * 4); i--) {
                facFourBuffer = facFourBuffer.multiply(new BigDecimal(i));
            }
        }else{
            for (int i = index; i > 0; i--) {
                facBuffer = facBuffer.multiply(new BigDecimal(i));
            }
            for (int i = (4 * index); i > 0; i--) {
                facFourBuffer = facFourBuffer.multiply(new BigDecimal(i));
            }
        }
    }

    @Override
    public void run() {
        while(running){
            updateFactorial();
            counter = facFourBuffer.multiply(new BigDecimal(1103).add(new BigDecimal(26390*index)));
            denum = facBuffer.pow(4);
            denum = denum.multiply(new BigDecimal(396).pow(index*4));
            parcialSum = parcialSum.add(counter.divide(denum,MathContext.DECIMAL128));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            index = index + numThreads;
        }
    }
}
