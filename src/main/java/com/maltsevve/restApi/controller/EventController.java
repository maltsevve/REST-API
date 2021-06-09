package com.maltsevve.restApi.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maltsevve.restApi.model.Event;
import com.maltsevve.restApi.service.EventService;
import com.maltsevve.restApi.util.EventSerializer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/v1/events")
public class EventController extends HttpServlet {
    private final EventService eventService = new EventService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        if (req.getParameter("eventId") != null && req.getParameter("eventId").matches("\\d+")) {
            Event event = eventService.getById(Long.valueOf(req.getParameter("eventId")));

            Gson gson = new GsonBuilder().
                    setPrettyPrinting().registerTypeAdapter(Event.class, new EventSerializer()).create();
            writer.print(gson.toJson(event));
        } else {
            List<Event> events = eventService.getAll();

            for (Event event : events) {
                Gson gson = new GsonBuilder().
                        setPrettyPrinting().registerTypeAdapter(Event.class, new EventSerializer()).create();
                writer.print(gson.toJson(event));
            }
        }
    }
}
