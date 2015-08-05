package objects;

/**
 * TODO Describe something...
 *
 * @author jclausman
 * @since 4/30/14
 */
public class User {
    public final String firstName;
    public final String lastName;
    public final String locale;
    public final String userid;

    public User(String firstName, String lastName, String locale, String userid)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.locale = locale;
        this.userid = userid;
    }

    public static final User NOBODY = new User("Jane", "Doe", "en_US", "0");
}
