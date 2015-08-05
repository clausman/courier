package objects.segments;

import objects.User;
import objects.parameters.Parameter;
import objects.parameters.ParameterMap;

/**
 * Created by jclausman on 5/14/14.
 */
public class Entry<T> {
    private final User user;
    private final T data;

    private Entry(User user, T params)
    {
        this.user = user;
        this.data = params;
    }

    public User getUser() {
        return user;
    }

    public T getData() {
        return data;
    }
}
