package services;

import models.Message;
import objects.User;
import objects.parameters.ParameterMap;
import objects.RawMessage;

/**
 * Service to convert a message to raw text so that it can be sent to a user
 *
 * @author jclausman
 * @since 4/30/14
 */
public interface MessageService {
    public RawMessage interpret(Message message, User user, ParameterMap data);
}
