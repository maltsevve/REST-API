package com.maltsevve.restApi.controller;

import com.maltsevve.restApi.model.User;
import com.maltsevve.restApi.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserController extends HttpServlet {
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

    UserService userService = new UserService();

    public User save(User user) {
        return userService.save(user);
    }

    public User update(User user) {
        return userService.update(user);
    }

    public User getById(Long aLong) {
        return userService.getById(aLong);
    }

    public List<User> getAll() {
        return userService.getAll();
    }

    public void deleteById(Long aLong) {
        userService.deleteById(aLong);
    }
}
