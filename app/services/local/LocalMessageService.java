package services.local;

import models.Message;
import objects.*;
import objects.parameters.Parameter;
import objects.parameters.ParameterMap;
import services.MessageService;

/**
 * TODO Describe something...
 *
 * @author jclausman
 * @since 5/2/14
 */
public class LocalMessageService implements MessageService {
    @Override
    public RawMessage interpret(Message message, User user, ParameterMap data) {
        String messageText = message.key;
        for (Parameter param : data.getParameters())
        {
            messageText = parameterize(messageText, param, data.getValue(param, ""));
        }
        return new RawMessage(messageText, message.url);
    }

    private static <T> String parameterize(String original, Parameter<T> param, T value)
    {
        // No fancy logic, just parameterize based on object tostring
        final String toMatch = String.format("@\\[%s\\]", param.getName());
        return original.replaceAll(toMatch, value.toString());
    }

}
