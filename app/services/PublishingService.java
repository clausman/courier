package services;

import objects.User;
import objects.RawMessage;

/**
 * TODO Describe something...
 *
 * @author jclausman
 * @since 4/29/14
 */
public interface PublishingService {
    public void publish(RawMessage message, User user);
}
