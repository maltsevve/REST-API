package com.maltsevve.restApi.controller;

import com.maltsevve.restApi.model.Event;
import com.maltsevve.restApi.service.EventService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class EventController extends HttpServlet {
    private final EventService eventService = new EventService();
    private final static String TITLE = "Events";
    private final static String DOC_TYPE = "<!DOCTYPE html>";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getParameter("crud")) {
            case "getById":
            case "getAll":
                doGet(req, resp);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        writer.println(DOC_TYPE + "<html><head><title>" + TITLE + "</title></head><body>");

        if (req.getParameter("eventId") != null && req.getParameter("eventId").matches("\\d+")) {
            Event event = eventService.getById(Long.valueOf(req.getParameter("eventId")));
            writer.println("ID: " + event.getId());
            writer.println(" Event: " + event.getStatus());
            writer.println(" User: " + event.getUser());
            writer.println(" File: " + event.getFile());
            writer.println(" Time: " + event.getEventTime() + "<br/>");
        } else {
            List<Event> events = eventService.getAll();

            for (Event event : events) {
                writer.println("ID: " + event.getId());
                writer.println(" Event: " + event.getStatus());
                writer.println(" User: " + event.getUser());
                writer.println(" File: " + event.getFile());
                writer.println(" Time: " + event.getEventTime() + "<br/>");
            }
        }

        writer.println("</body></html>");
    }
}
