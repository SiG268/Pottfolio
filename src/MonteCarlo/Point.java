package MonteCarlo;
import java.math.BigDecimal;
import java.math.MathContext;

public class Point {
    BigDecimal x;
    BigDecimal y;

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

    public Point(){
        setX(new BigDecimal(0));
        setY(new BigDecimal(0));
    }
    public Point(BigDecimal x, BigDecimal y){
        this();
        setX(x);
        setY(y);
    }

    public String toString(){
        return "X: "+getX()+"\nY: "+getY();
    }

    public BigDecimal distance(Point p){
        BigDecimal kathX=this.getX().subtract(p.getX());
        BigDecimal kathY=this.getY().subtract(p.getY());
        BigDecimal hypo= kathX.pow(2).add(kathY.pow(2));
        return hypo.sqrt(MathContext.DECIMAL128);
    }


}
