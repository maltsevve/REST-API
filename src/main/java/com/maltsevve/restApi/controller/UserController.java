package com.maltsevve.restApi.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maltsevve.restApi.model.Event;
import com.maltsevve.restApi.model.User;
import com.maltsevve.restApi.service.UserService;
import com.maltsevve.restApi.util.EventSerializer;
import com.maltsevve.restApi.util.UserSerializer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/v1/users")
public class UserController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        if (req.getParameter("name") != null) {
            User user = new User();
            user.setName(req.getParameter("name"));
            userService.save(user);

            writer.print(new Gson().toJson(user));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        if (req.getParameter("userId") != null && req.getParameter("userId").matches("\\d+")) {
            Long id = Long.parseLong(req.getParameter("userId"));

            User user = userService.getById(id);
            user.setName(req.getParameter("name"));
            userService.update(user);

            writer.print(new Gson().toJson(user));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        Gson gson = new GsonBuilder().
                setPrettyPrinting().registerTypeAdapter(User.class, new UserSerializer()).create();

        if (req.getParameter("userId") != null && req.getParameter("userId").matches("\\d+")) {
            User user = userService.getById(Long.valueOf(req.getParameter("userId")));

            sendGson(writer, gson, user);
        } else {
            List<User> users = userService.getAll();

            for (User user : users) {
                sendGson(writer, gson, user);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (req.getParameter("userId") != null && req.getParameter("userId").matches("\\d+")) {
            Long id = Long.parseLong(req.getParameter("userId"));
            userService.deleteById(id);
        }
    }

    private void sendGson(PrintWriter writer, Gson gson, User user) {
        writer.print(gson.toJson(user));

        List<Event> events = user.getEvents();

        for (Event event : events) {
            gson = new GsonBuilder().
                    setPrettyPrinting().registerTypeAdapter(Event.class, new EventSerializer()).create();
            writer.print(gson.toJson(event));
        }
    }
}
