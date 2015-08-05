package services.local;

import objects.User;
import objects.RawMessage;
import services.PublishingService;

/**
 * TODO Describe something...
 *
 * @author jclausman
 * @since 4/29/14
 */
public class LocalPublishingService implements PublishingService {

    @Override
    public void publish(RawMessage message, User user) {
        System.out.println(user.userid + ": " + message.text + " -> " + message.redirect);
    }
}
