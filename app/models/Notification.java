package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * A single type of notification.
 * e.g. dormant users
 * Each notification is bound to a segment from which is gets its data that defines who to send CopyTests to
 * The CopyTests are all the tests for this notification, they do the actual sending
 *
 * @author jclausman
 * @since 4/24/14
 */
public abstract class Notification<T> {
    private final String uid;
    private final String name;
    private final String description;
    public Notification(String uid, String name, String description)
    {
        this.uid = uid;
        this.name = name;
        this.description = description;
    }
    public String getUID()
    {
        return uid;
    }
    public String getFriendlyName()
    {
        return name;
    }
    public String getDescription()
    {
        return description;
    }
}
