package objects.parameters;

/**
 * Created by jclausman on 5/10/14.
 */
public class Decimal extends Parameter<Double> {
    public Decimal(String name) {
        super(name, Double.class);
    }
}
