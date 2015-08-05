package controllers;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import models.Notification;
import models.SegmentMetadata;
import objects.segments.Segment;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import services.Store;

import java.util.List;

/**
 * TODO Describe something...
 *
 * @author jclausman
 * @since 4/24/14
 */
public class Notifications extends Controller {

    @Inject
    private Store<Notification> store;

    private final Form<Notification> notifForm = Form.form(Notification.class);

    public Result list() {
        return ok(views.html.notifications.list.render(Notification.find.all()));
    }

    public Result create() {
        return ok(views.html.notifications.create.render(notifForm, getSegmentNames()));
    }

    public Result details(String uid) {
        final Notification notif = Notification.find
                .where().eq("id", id)
                .findUnique();
        if (notif == null)
        {
            notFound();
        }
        final Segment segment = store.findByName(notif.segmentName);
        if (segment == null)
        {
            return notFound("Segment '"+notif.segmentName+"' could not be found");
        }
        return ok(views.html.notifications.details.render(notif, new SegmentMetadata(segment)));
    }

    public Result save() {
        Form<Notification> boundForm = notifForm.bindFromRequest();
        if (boundForm.hasErrors()) {
            flash("error", "Please correct the form below");
            return badRequest(views.html.notifications.create.render(boundForm, getSegmentNames()));
        }
        Notification notif = boundForm.get();
        notif.save();
        flash("success", "Successfully created new notification");
        return redirect(routes.Notifications.list());
    }

    private List<String> getSegmentNames()
    {
        return Lists.newArrayList(
            Iterables.transform(store.all(),
                    new Function<Segment, String>() {
                        @Override
                        public String apply(Segment input) {
                            return input.getName();
                        }
                    }
            ));
    }
}
