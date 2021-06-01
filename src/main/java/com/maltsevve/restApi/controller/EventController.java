package com.maltsevve.restApi.controller;

import com.maltsevve.restApi.model.Event;
import com.maltsevve.restApi.service.EventService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class EventController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    EventService eventService = new EventService();

    public Event save(Event event) {
        return eventService.save(event);
    }

    public Event update(Event event) {
        return eventService.update(event);
    }

    public Event getById(Long aLong) {
        return eventService.getById(aLong);
    }

    public List<Event> getAll() {
        return eventService.getAll();
    }

    public void deleteById(Long aLong) {
        eventService.deleteById(aLong);
    }
}
