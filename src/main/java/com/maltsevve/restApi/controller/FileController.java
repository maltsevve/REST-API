package com.maltsevve.restApi.controller;


import com.google.gson.Gson;
import com.maltsevve.restApi.model.Event;
import com.maltsevve.restApi.model.File;
import com.maltsevve.restApi.model.Status;
import com.maltsevve.restApi.service.EventService;
import com.maltsevve.restApi.service.FileService;
import com.maltsevve.restApi.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/v1/files/*")
public class FileController extends HttpServlet {
    private final static String SAVE_DIR = "C:/Users/Victor/IdeaProjects/REST-API/src/main/resources/uploads/";
    private final static int BUFFER_SIZE = 4096;

    private final FileService fileService = new FileService();
    private final EventService eventService = new EventService();
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        if (req.getParameter("userId") != null && req.getParameter("userId").matches("\\d+")) {
            if (req.getParameter("fileName") != null) {
                String fileName = req.getParameter("fileName");
                java.io.File saveFile = new java.io.File(SAVE_DIR + fileName);

                uploadFile(saveFile, req);

                File file = new File();
                file.setFileName(fileName);
                fileService.save(file);

                Event event = new Event();
                event.setFile(file);
                event.setStatus(Status.SAVED);
                event.setUser(userService.getById(Long.valueOf(req.getParameter("userId"))));
                eventService.save(event);

                writer.print(new Gson().toJson(file));
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        if (req.getParameter("fileId") != null && req.getParameter("fileId").matches("\\d+")) {
            Long id = Long.parseLong(req.getParameter("fileId"));

            if (req.getParameter("userId") != null && req.getParameter("userId").matches("\\d+")) {
                if (req.getParameter("fileName") != null) {
                    String fileName = req.getParameter("fileName");
                    java.io.File saveFile = new java.io.File(SAVE_DIR + fileName);

                    uploadFile(saveFile, req);

                    File file = fileService.getById(id);

                    if (!file.getFileName().equals(fileName)) {
                        java.io.File oldFile = new java.io.File(SAVE_DIR + file.getFileName());
                        oldFile.delete();
                    }

                    file.setFileName(fileName);

                    Event event = new Event();
                    event.setFile(file);
                    event.setStatus(Status.UPDATED);
                    event.setUser(userService.getById(Long.valueOf(req.getParameter("userId"))));

                    if (fileService.update(file).equals(file)) {
                        eventService.save(event);
                    }

                    writer.print(new Gson().toJson(file));
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        String[] splitReqURI = req.getRequestURI().split("/");

        if (splitReqURI[splitReqURI.length - 1].matches("\\d+")) {
            File file = fileService.getById(Long.valueOf(splitReqURI[splitReqURI.length - 1]));

            writer.print(new Gson().toJson(file));
        } else {
            List<File> files = fileService.getAll();

            for (File file : files) {
                writer.print(new Gson().toJson(file));
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();
        long id;

        if (req.getParameter("fileId") != null && req.getParameter("fileId").matches("\\d+")) {
            id = Long.parseLong(req.getParameter("fileId"));

            if (req.getParameter("userId") != null && req.getParameter("userId").matches("\\d+")) {
                File file = fileService.getById(id);

                java.io.File deletingFile = new java.io.File(SAVE_DIR + file.getFileName());
                deletingFile.delete();

                if (!deletingFile.exists()) {
                    Event event = new Event();
                    event.setFile(file);
                    event.setStatus(Status.DELETED);
                    event.setUser(userService.getById(Long.valueOf(req.getParameter("userId"))));

                    fileService.update(file);
                    eventService.save(event);
                }
            }
        }
    }

    private void uploadFile(java.io.File file, HttpServletRequest req) {
        try (InputStream inputStream = req.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(file)) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
