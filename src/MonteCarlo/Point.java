package MonteCarlo;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Point {
    //Konstanten
    public final MathContext MC = new MathContext(1000, RoundingMode.HALF_EVEN);
    //Koordinaten
    BigDecimal x;
    BigDecimal y;

    //getter und setter
    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    //Default Constructor -> Punkt(0|0)
    public Point(){
        setX(new BigDecimal(0));
        setY(new BigDecimal(0));
    }

    //Constructor der einen Punkt mit Koordinaten (x|y) erzeugt
    public Point(BigDecimal x, BigDecimal y){
        this();
        setX(x);
        setY(y);
    }

    //Gibt die Koordinaten als String zurück
    @Override
    public String toString(){
        return "X: "+getX()+"\nY: "+getY();
    }

    //Evaluiert den Abstand vom Punkt zu 0/0
    //1=kleiner/gleich 1
    //0=größer 1
    public int eval(){
        BigDecimal hypo= this.getX().pow(2).add(this.getY().pow(2));
        hypo = hypo.sqrt(MC);
        if(hypo.compareTo(BigDecimal.ONE)!=1){
            return 1;
        }
        return 0;
    }


}
