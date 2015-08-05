package objects.parameters;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

/**
 * An entire entry in a segment. If you think of a segment as a table of data, this corresponds to a single row
 *
 * @author jclausman
 * @since 5/2/14
 */
public class ParameterMap {
    private Map<Parameter, Object> values = Maps.newHashMap();

    public static final ParameterMap EMPTY = new ParameterMap();

    public <T> T getValue(Parameter<T> paramater)
    {
        return (T) values.get(paramater);
    }

    public <T> T getValue(Parameter<T> parameter, T defaultValue)
    {
        T v = getValue(parameter);
        return v != null ? v : defaultValue;
    }

    public Collection<Parameter> getParameters()
    {
        return ImmutableList.copyOf(values.keySet());
    }

    private ParameterMap() { }
    private ParameterMap(ParameterMap other)
    {
        this.values.putAll(other.values);
    }

    private <T> void put(Parameter<T> p, T v)
    {
        this.values.put(p, v);
    }

    public static class Builder
    {
        private final ParameterMap intermediate;

        public Builder()
        {
            this.intermediate = new ParameterMap();
        }
        public Builder(ParameterMap base)
        {
            this.intermediate = new ParameterMap(base);
        }

        public <T> Builder with(Parameter<T> p, T v)
        {
            intermediate.put(p, v);
            return this;
        }

        public ParameterMap build()
        {
            return new ParameterMap(intermediate);
        }
    }
}
