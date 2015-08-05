package services;

import objects.segments.Segment;

import java.util.Collection;

/**
 * Store which holds all the segment definitions.
 */
public interface Store<T> {
    public Collection<T> all();
    public boolean register(T segment);
    public T findByName(String name);
}
