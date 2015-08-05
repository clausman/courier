import java.util.List;
import java.util.Map;

import com.avaje.ebean.Ebean;
import com.claws.example.StockPrices;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import models.Notification;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;
import services.MessageService;
import services.PublishingService;
import services.SegmentService;
import services.Store;
import services.local.LocalMessageService;
import services.local.LocalPublishingService;
import services.local.LocalSegmentService;
import services.local.LocalSegmentStore;

/**
 * Setup global settings for app
 *
 * TODO extract most of this away so implementors can do their own registration and such
 * @author jclausman
 * @since 4/28/14
 */
public class Global extends GlobalSettings {
    private static final Injector INJECTOR = createInjector();

    @Override
    public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
        return INJECTOR.getInstance(controllerClass);
    }

    private static Injector createInjector() {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(PublishingService.class).to(LocalPublishingService.class);
                bind(SegmentService.class).to(LocalSegmentService.class);
                bind(MessageService.class).to(LocalMessageService.class);
                bind(Store.class).to(LocalSegmentStore.class);
            }
        });
    }

    @Override
    public void onStart(Application application) {
        super.onStart(application);
        seedData(application);
        registerSegments();
    }

    /**
     * Seed the models with data from the seed-data.yml file
     * @param app
     */
    private void seedData(Application app)
    {
        if (Ebean.find(Notification.class).findRowCount() == 0) {
            Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load("seed-data.yml");
            for(String key : all.keySet())
            {
                Ebean.save(all.get(key));
            }
        }
    }

    private void registerSegments()
    {
        Store store = INJECTOR.getInstance(Store.class);
        store.register(new StockPrices());
    }
}
