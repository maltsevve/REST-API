//package com.maltsevve.restApi.controller;
//
//import com.maltsevve.restApi.model.Event;
//import com.maltsevve.restApi.model.Status;
//import com.maltsevve.restApi.model.User;
//import com.maltsevve.restApi.service.EventService;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Date;
//import java.util.List;
//
//public class EventController extends HttpServlet {
//    private final EventService eventService = new EventService();
//    private final static String TITLE = "Events";
//    private final static String DOC_TYPE = "<!DOCTYPE html>";
//
//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        switch (req.getParameter("crud")) {
//            case "save":
//                doPost(req, resp);
//                break;
//            case "update":
//                doPut(req, resp);
//                break;
//            case "getById":
//            case "getAll":
//                doGet(req, resp);
//                break;
//            case "delete":
//                doDelete(req, resp);
//                break;
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html");
//        PrintWriter writer = resp.getWriter();
//
//        writer.println(DOC_TYPE + "<html><head><title>" + TITLE + "</title></head><body>");
//
//        Event event = new Event();
//        event.setEventTime(new Date());
//        event.setStatus(Status.SAVED);
//        eventService.save(event);
//
//        writer.println("</body></html>");
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html");
//        PrintWriter writer = resp.getWriter();
//
//        writer.println(DOC_TYPE + "<html><head><title>" + TITLE + "</title></head><body>");
//
//        if (req.getParameter("userId") != null && req.getParameter("userId").matches("\\d+")) {
//            Long id = Long.parseLong(req.getParameter("userId"));
//
//            User user = userService.getById(id);
//            user.setName(req.getParameter("name"));
//            userService.update(user);
//
//            writer.println("User with ID " + id + " updated. New user name: " + user.getName() + "<br/>");
//        }
//
//        writer.println("</body></html>");
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html");
//        PrintWriter writer = resp.getWriter();
//
//        writer.println(DOC_TYPE + "<html><head><title>" + TITLE + "</title></head><body>");
//
//        if (req.getParameter("userId") != null && req.getParameter("userId").matches("\\d+")) {
//            User user = userService.getById(Long.valueOf(req.getParameter("userId")));
//            writer.println("ID: " + user.getId());
//            writer.println("User name: " + user.getName() + "<br/>");
//
//            for (Event event : user.getEvents()) {
//                writer.println("ID " + event.getId());
//                writer.println(" Event time: " + event.getEventTime());
//                writer.println(" Saved file name: " + event.getFile() + "<br/>");
//            }
//        } else {
//            List<User> users = userService.getAll();
//
//            for (User user : users) {
//                writer.println("ID " + user.getId() + " ");
//                writer.println("File name: " + user.getName() + "<br/>");
//
//                for (Event event : user.getEvents()) {
//                    writer.println("ID " + event.getId());
//                    writer.println(" Event time: " + event.getEventTime());
//                    writer.println(" Saved file name: " + event.getFile() + "<br/>");
//                }
//            }
//        }
//
//        writer.println("</body></html>");
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html");
//        PrintWriter writer = resp.getWriter();
//
//        writer.println(DOC_TYPE + "<html><head><title>" + TITLE + "</title></head><body>");
//
//        if (req.getParameter("userId") != null && req.getParameter("userId").matches("\\d+")) {
//            Long id = Long.parseLong(req.getParameter("userId"));
//            userService.deleteById(id);
//            if (userService.getById(id) == null) {
//                writer.println("User with ID " + id + " successfully deleted.<br/>");
//            } else {
//                writer.println("Couldn't delete user.<br/>");
//            }
//        } else {
//            writer.println("No user with such ID in the data base.<br/>");
//        }
//
//        writer.println("</body></html>");
//    }
//}
