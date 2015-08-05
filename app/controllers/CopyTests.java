package controllers;

import java.text.SimpleDateFormat;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.CopyTest;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Message;
import models.Notification;
import objects.User;
import objects.RawMessage;
import objects.parameters.Parameter;
import objects.parameters.ParameterMap;
import objects.segments.Segment;
import org.apache.commons.lang3.StringUtils;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.MessageService;
import services.Store;

/**
 * Functions for copy tests. Mainly limited to CRUD methods since listing is generally done via notification methods
 *
 * @author jclausman
 * @since 5/5/14
 */
public class CopyTests extends Controller {
    @BodyParser.Of(BodyParser.Json.class)
    public Result list(Long notificationId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        List<CopyTest> tests = CopyTest.find.where().eq("notification.id", notificationId).findList();
        ObjectNode result = Json.newObject();
        ArrayNode dataNode = result.arrayNode();
        result.put("copyTests", dataNode);
        // Each test is its own array of values
        // If there are no copy tests, then make a default one
        // This solves some rendering problems with handsometables
        if (tests.isEmpty())
        {
            CopyTest test = new CopyTest();
            test.testId = 1;
            test.notification = Notification.find.byId(notificationId);
            test.message = new Message();
            test.save();
            test.message.save();
            tests.add(test);
        }
        for (CopyTest test : tests)
        {
            ObjectNode obj = dataNode.objectNode();
            obj.put("id", test.testId);
            obj.put("message", test.message.key);
            obj.put("url", test.message.url);
            obj.put("sendLimit", test.sendLimit);
            obj.put("enabled", test.enabled);
            obj.put("start", test.startDate != null ? format.format(test.startDate) : "");
            obj.put("end", test.endDate != null ? format.format(test.endDate) : "");

            dataNode.add(obj);
        }

        return ok(result);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result save()
    {
        JsonNode json = request().body().asJson();
        if (json == null)
        {
            return badRequest("expecting json data");
        }
        Long notifId = json.get("notifId").asLong();

        String key = json.get("key").asText();
        String newValue = json.get("newValue").asText();

        if (notifId == null || key == null || newValue == null)
        {
            return badRequest("missing some required parameters");
        }
        CopyTest test;
        if (json.has("testId") && json.get("testId") != null &&
                !"null".equalsIgnoreCase(json.get("testId").asText())
                && StringUtils.isNotBlank(json.get("testId").asText())) {
            Integer testId = json.get("testId").asInt();
            test = CopyTest.find.
                    where().eq("notification_id", notifId).eq("testId", testId).
                    findUnique();

            if (test == null) {
                return badRequest("Could not find copy test for "+testId);
            }
        } else {
            test = new CopyTest();
            test.message = new Message();
            test.notification = Notification.find.byId(notifId);
            // Set the testId to the maximum testId + 1
            //HACK This does NOT work in parallel
            int maxId = 0;
            for (CopyTest ct : CopyTest.find.where().eq("notification_id", notifId).findList())
            {
                if (ct.testId > maxId) maxId = ct.testId;
            }
            test.testId = maxId+1;
        }

        test.setField(key, newValue);
        test.save();
        test.message.save();

        return ok(""+test.testId);
    }

    @Inject
    private MessageService messageService;

    @Inject
    private Store segmentStore;

    @BodyParser.Of(BodyParser.Json.class)
    public Result resolve()
    {
        JsonNode json = request().body().asJson();
        if (json == null)
        {
            return badRequest("expecting json data");
        }
        Long notifId = json.get("notifId").asLong();
        Long testId = json.get("testId").asLong();
        JsonNode parameters = json.get("parameters");

        CopyTest test = CopyTest.find.
                where().eq("notification_id", notifId).eq("testId", testId).
                findUnique();
        Notification notification = Notification.find.byId(notifId);
        if (test == null || notification == null)
        {
            return badRequest();
        }
        Segment segment = segmentStore.findByName(notification.segmentName);
        ParameterMap.Builder mapBuilder = new ParameterMap.Builder();
        for (Parameter param : segment.getOutputParameters())
        {
            //TODO This will only work with string parameters
            mapBuilder.with(param, parameters.get(param.getName()).asText());
        }
        RawMessage rawMessage = messageService.interpret(test.message, User.NOBODY, mapBuilder.build());

        ObjectNode result = Json.newObject();
        result.put("text", rawMessage.text);
        result.put("url", rawMessage.redirect);

        return ok(result);
    }
}
