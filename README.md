Courier is a generic application for sending messages to users. Some common examples include email and Facebook notifications. Courier breaks down the process into the following steps:
  1) Generate list of users and their data that will be needed to populate messages
  2) Resolve message templates into resolved messages which will be sent to users
  3) Send the resolved messages to users

The application design aims to make each step pluggable, so it is easy to adopt the system to new domains. For example, if you had a courier implementation for sending user's emails, switching it to send user's Facebook notifications would be as simple as changing the 'send' step to publish to Facebook rather than an email service.

The design also aims to allow easy extension of each step, so custom templating or i18N solutions are trivial to implement. Each steps logic is abstracted into a service which has a default implementation that you more than likely will want to override in most scenarios. Here is a description of each default implementation:

FileSegmentService - Reads segment data from local files.
FileMessageService - Reads message templates from local files. Uses StringTemplate to resolve templates. Does not include and i18N support.
EmailLocalPublishService - publishes messages using the machines java mail setup. Is fine for testing but should not be used on large scale systems.
LocalSegmentStore - Resolves segment names to segment objects using a map. Since segments are generally bound at startup, this store should be sufficient in nearly all cases



Here are some example scenarios where you would need to implement custom services and stub code for doing it.


++ Use external email service
First off implement the email service to send emails to users
`
public class EmailService implements PublishService {
	public void publish(RawMessage message, User user) {
		// TODO Implement logic to send emails to users 
	}
}
`

Next extend custom settings to setup dependency injection to use your email service
`
public class EmailSettings extends CustomSettings {
	@Override
	public Injector createInjector()
	{
		Guice.createInjector(new AbstractModule(){
			@Override
			protected void configure() {
				bind(PublishService.class).to(EmailService.class)
			}
		})
	}
}
`


++ TODO Add more examples



======================================================
Creating Segments
======================================================
All messages are backed by segments. A segment can be though of as a generated tables of data.
All rows in the data have the same structure, and each row of data is used to populate a single message.
This means each row must have the required data to not only identify the user the message should be sent to, but
also all the data required to resolve a message.

While you certainly can implement your own SegmentStore, the out-of-the-box Courier represents each segment as its own class.
Creating a new segment is a simple matter of implementing the segment interface or one of the helper base classes

`
public class BadgeEarnedSegment extends GeneratedSegment {
  public static final Text BADGE_NAME = new Text("badge_name");

  // Bind the name of the segment
  public BadgeEarnedSegment() { super("Badge Earned"); };

  @Override
  public Iterable<Entry> fetchEntries() {
    // Find all badges earned yesterday
    return Iterables.transform(
        Badge.find.where().gt("earned_date", DateHelper.yesterday()).lt("earned_date", DateHelper.today())
            .findList(),
        BadgeEarnedSegment.toEntry);
  }

  private static final Function<Badge, Entry> toEntry = new Function() {
    @Override
    public Entry transform(Badge badge) {
        return new Entry.Builder()
            .for(badge.getUser())
            .with(BadgeEarnedSegment.BADGE_NAME, badge.getName())
            .build();
    }
  }
}
`

Using the built-in MessageService, some example messages for the above segment would be:
  "Congrats on earning the <badge_name> badge!"
  "Nice work! You just earned the <badge_name> badge!"



