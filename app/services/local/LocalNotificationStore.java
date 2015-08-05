package services.local;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import models.Notification;
import services.Store;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * Created by jclausman on 5/27/14.
 */
@Singleton
public class LocalNotificationStore implements Store<Notification> {
    private final Collection<Notification> notifications = Lists.newArrayList();
    @Override
    public Collection<Notification> all() {
        return ImmutableList.copyOf(notifications);
    }

    @Override
    public boolean register(Notification item) {
        return notifications.add(item);
    }

    @Override
    public Notification findByName(final String name) {
        return Iterables.getFirst(
                Iterables.filter(notifications, new Predicate<Notification>() {
                    @Override
                    public boolean apply(@Nullable Notification input) {
                        return input != null && input.getUID().equalsIgnoreCase(name);
                    }
                }), null
        );
    }
}
