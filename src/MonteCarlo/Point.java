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

    //Berechnet die Distanz zwischen this (Punkt) und übergebenem Punkt p
    //Allgemein: Distanz d^2 = (this.x-p.x)^2 + (this.y - p.y)^2
    public BigDecimal distance(Point p){
        //Differenz der X Koordinate
        BigDecimal kathX=this.getX().subtract(p.getX());
        //Differenz der Y Koordinate
        BigDecimal kathY=this.getY().subtract(p.getY());
        //Distanz^2 = DifferenzX^2 + DifferenzY^2
        BigDecimal hypo= kathX.pow(2).add(kathY.pow(2));
        //Rückgabe der Wurzel aus hypo = Distanz
        return hypo.sqrt(MC);
    }


}
