package objects.parameters;

/**
 * Created by jclausman on 5/10/14.
 */
public class Number extends Parameter<Long> {
    public Number(String name) {
        super(name, Long.class);
    }
}
