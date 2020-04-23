package MonteCarlo;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Point {
    //Konstanten
    /**{@link #MC} -  Der bei der Berechnung verwendete MathContext*/
    public final MathContext MC = new MathContext(10000, RoundingMode.HALF_EVEN);
    /**{@link #x} -  X Koordinate vom Punkt*/
    private BigDecimal x;
    /**{@link #y} -  Y Koordinate vom Punkt*/
    private BigDecimal y;

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

    /**
     * Default Konstruktor<br/>
     * Erzeugt einen Punkt an der Position x=0,y=0
     */
    public Point(){
        setX(new BigDecimal(0));
        setY(new BigDecimal(0));
    }

    /**
     * Konstrukter<br/>
     * Erzeugt einen Punkt mit den Übergabeparamtern
     * @param x X Koordinate
     * @param y Y Koordinate
     */
    public Point(BigDecimal x, BigDecimal y){
        this();
        setX(x);
        setY(y);
    }

    /**
     * Ermöglicht es den Punkt als String auszugeben
     * @return "X: Value, Y: Value"
     */
    @Override
    public String toString(){
        return "X: "+getX()+", Y: "+getY();
    }


    /**
     * Errechnet den Abstand zum Punkt: x=0, y=0
     * @return (1, wenn Abstand<=1) (0, wenn Abstand>1)
     */
    public int eval(){
        BigDecimal hypo= this.getX().pow(2).add(this.getY().pow(2));
        hypo = hypo.sqrt(MC);
        if(hypo.compareTo(BigDecimal.ONE)!=1){
            return 1;
        }
        return 0;
    }


}
