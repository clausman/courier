package models;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import play.db.ebean.Model;

/**
 * Each CopyTest represents message to send to users for some duration.
 * Generally we are not sure what messages will do best for a given Notification, so we run many different copy tests
 * to compare their results
 *
 * Each CopyTest represents a distinct test of sending users a particular message
 *
 * @author jclausman
 * @since 4/29/14
 */
@Entity
public class CopyTest extends Model {
    @Id
    public Long id;
    public String notificationUID;
    // The message to send to the user for this test
    @OneToOne(cascade = CascadeType.ALL)
    public Message message;
    // Id of this test to differentiate it from others
    public int testId;
    // Maximum number of copies to send per day
    public int sendLimit;
    public Date startDate;
    public Date endDate;
    @Column(columnDefinition = "boolean default false")
    public boolean enabled = false;
    public Date lastSent;

    public static final Finder<Long, CopyTest> find = new Finder<>(Long.class, CopyTest.class);


    public void setField(String fieldName, String value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {
            switch (fieldName) {
                case "id":
                    testId = Integer.parseInt(value);
                    break;
                case "message":
                    message.key = value;
                    break;
                case "url":
                    message.url = value;
                    break;
                case "sendLimit":
                    sendLimit = Integer.parseInt(value);
                    break;
                case "start":
                    startDate = dateFormat.parse(value);
                    break;
                case "end":
                    endDate = dateFormat.parse(value);
                    break;
                case "enabled":
                    enabled = Boolean.parseBoolean(value);
                    break;
                default:
                    throw new Exception("Could not handle field " + fieldName);
            }
        } catch (Exception e)
        {
            //HACK
            throw new RuntimeException(e);
        }
    }
}
