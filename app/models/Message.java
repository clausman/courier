package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Represents the text to send to a user in a notification
 * Is sent to the MessageService for localization and parametrization
 *
 * @author jclausman
 * @since 4/29/14
 */
@Entity
public class Message extends Model {
    @Id
    public Long id;
    @OneToOne(mappedBy = "message")
    public CopyTest copyTest;
    public String key;
    public String url;

    public static final Finder<Long, Message> find = new Finder<>(Long.class, Message.class);
}
