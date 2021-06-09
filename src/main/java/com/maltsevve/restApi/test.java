package com.maltsevve.restApi;

import com.maltsevve.restApi.model.User;
import com.maltsevve.restApi.service.UserService;

import java.util.List;

public class test {
    public static void main(String[] args) {
        UserService userService = new UserService();

        List<User> users = userService.getAll();

        for (User user : users) {
            System.out.println(user.getName());
        }
    }
}
