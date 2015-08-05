package objects;

/**
 * A message which has already been localized, parametrized, ect and is ready to be sent
 * Holds all data needed for a single notification message
 *
 * @author jclausman
 * @since 4/30/14
 */
public class RawMessage {
    public final String text;
    public final String redirect;
    public RawMessage(String text, String redirect)
    {
        this.text = text;
        this.redirect = redirect;
    }
}
