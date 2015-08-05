package objects.segments;

import objects.parameters.Parameter;
import objects.parameters.ParameterMap;

import java.util.List;

/**
 * Created by jclausman on 5/14/14.
 */
public interface Segment<T> {
    String getName();
    String getDescription();
    Iterable<Entry<T>> fetchEntries(ParameterMap segmentParameters);
    void generate();
}
