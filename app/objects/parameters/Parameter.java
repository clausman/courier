package objects.parameters;

/**
 * Represents the lookup to a value, but does not hold the value itself.
 * Instances are immutable so they can be safely used as lookup keys.
 *
 * @author jclausman
 * @since 4/30/14
 */
public class Parameter<T> {
    public final String name;
    public final Class<T> valueClass;

    public Parameter(String name, Class<T> valueClass)
    {
        this.valueClass = valueClass;
        this.name = name;
    }

    /**
     * Get the name of this parameter, for lookup in messages
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Get the class this parameter resolves to
     */
    public Class<T> getValueClass() {
        return this.valueClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        if (name != null ? !name.equals(parameter.name) : parameter.name != null) return false;
        if (valueClass != null ? !valueClass.equals(parameter.valueClass) : parameter.valueClass != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (valueClass != null ? valueClass.hashCode() : 0);
        return result;
    }
}
