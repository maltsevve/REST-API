package com.maltsevve.restApi.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.maltsevve.restApi.model.Event;

import java.lang.reflect.Type;

public class EventSerializer implements JsonSerializer<Event> {
    @Override
    public JsonElement serialize(Event event, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();

        result.addProperty("Id", event.getId());
        result.addProperty("Status", event.getStatus().toString());
        result.addProperty("User id", event.getUser().getId());
        result.addProperty("User name", event.getUser().getName());
        result.addProperty("File id", event.getFile().getId());
        result.addProperty("File name", event.getFile().getFileName());
        result.addProperty("Time", event.getEventTime().getTime());

        return result;
    }
}
