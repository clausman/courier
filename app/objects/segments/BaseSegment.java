package objects.segments;

import objects.parameters.Parameter;
import objects.parameters.ParameterMap;

import java.util.Collections;
import java.util.List;

/**
 * Base class which override methods from interface.
 * This is simply a helper for other to extend
 */
public abstract class BaseSegment<T> implements Segment<T> {
    private final String name;
    private final String description;

    public BaseSegment(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void generate() {
        return;
    }
}
