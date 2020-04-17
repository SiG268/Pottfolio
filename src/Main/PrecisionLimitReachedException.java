package Main;

public class PrecisionLimitReachedException extends Exception {

    public PrecisionLimitReachedException(PiCalculationThread t){
        System.out.println("Thread " + t.getThreadNumber()+": Precision limit reached. Shutting down.");
        t.running = false;
    }
}
