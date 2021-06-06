package com.maltsevve.restApi.controller;


import com.maltsevve.restApi.model.Event;
import com.maltsevve.restApi.model.File;
import com.maltsevve.restApi.model.Status;
import com.maltsevve.restApi.service.EventService;
import com.maltsevve.restApi.service.FileService;
import com.maltsevve.restApi.service.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class FileController extends HttpServlet {
    private final FileService fileService = new FileService();
    private final EventService eventService = new EventService();
    private final UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getParameter("crud")) {
            case "save":
                doPost(req, resp);
                break;
            case "update":
                doPut(req, resp);
                break;
            case "getById":
            case "getAll":
                doGet(req, resp);
                break;
            case "delete":
                doDelete(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        String title = "Files";
        String docType = "<!DOCTYPE html>";

        writer.println(docType + "<html><head><title>" + title + "</title></head><body>");

        if (req.getParameter("userId") != null && req.getParameter("userId").matches("\\d+")) {
            File file = new File();
            Event event = new Event();

            file.setFileName(req.getParameter("fileName"));

            event.setStatus(Status.SAVED);
            event.setFile(file);
            event.setUser(userService.getById(Long.valueOf(req.getParameter("userId"))));

            if (fileService.save(file).equals(file)) {
                eventService.save(event);
            }
        } else {
            writer.println("Specify the correct user ID.<br/>");
        }

//        int fileMaxSize = 100 * 1024;
//        int memMaxSize = 100 * 1024;
//
//        String filePath = "C:/Users/Victor/IdeaProjects/rest-api/src/main/resources/uploads";

//        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
//        diskFileItemFactory.setRepository(new java.io.File(filePath));
//        diskFileItemFactory.setSizeThreshold(memMaxSize);
//
//        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
//        upload.setSizeMax(fileMaxSize);
//
//        try {
//            List<org.apache.commons.fileupload.FileItem> fileItems = upload.parseRequest((RequestContext) req);
//            Iterator<FileItem> iterator = fileItems.iterator();
//
//            while (iterator.hasNext()) {
//                FileItem fileItem = iterator.next();
//                java.io.File file;
//
//                if (!fileItem.isFormField()) {
//
//                    String fileName = fileItem.getName();
//                    if (fileName.lastIndexOf("\\") >= 0) {
//                        file = new java.io.File(filePath +
//                                fileName.substring(fileName.lastIndexOf("\\")));
//                    } else {
//                        file = new java.io.File(filePath +
//                                fileName.substring(fileName.lastIndexOf("\\") + 1));
//                    }
//                    fileItem.write(file);
//
//                    writer.println(fileName + " is uploaded.<br>");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        writer.println("</body></html>");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        String title = "Files";
        String docType = "<!DOCTYPE html>";

        writer.println(docType + "<html><head><title>" + title + "</title></head><body>");

        if (req.getParameter("fileId") != null && req.getParameter("fileId").matches("\\d+")) {
            Long id = Long.parseLong(req.getParameter("fileId"));

            if (req.getParameter("userId") != null && req.getParameter("userId").matches("\\d+")) {
                File file = fileService.getById(id);
                Event event = new Event();

                file.setFileName(req.getParameter("fileName"));

                event.setStatus(Status.UPDATED);
                event.setFile(file);
                event.setUser(userService.getById(Long.valueOf(req.getParameter("userId"))));

                if (fileService.update(file).equals(file)) {
                    eventService.save(event);
                }

                writer.println("FIle with ID " + id + " updated. New file name: " + file.getFileName() + "<br/>");
            } else {
                writer.println("Specify the correct user ID.<br/>");
            }
        } else {
            writer.println("Specify the correct file ID.<br/>");
        }

        writer.println("</body></html>");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        String title = "Files";
        String docType = "<!DOCTYPE html>";

        writer.println(docType + "<html><head><title>" + title + "</title></head><body>");

        if (req.getParameter("fileId") != null && req.getParameter("fileId").matches("\\d+")) {
            File file = fileService.getById(Long.valueOf(req.getParameter("fileId")));
            writer.println("ID: " + file.getId());
            writer.println("File name: " + file.getFileName() + "<br/>");
        } else {
            List<File> files = fileService.getAll();

            for (File file : files) {
                writer.println("ID: " + file.getId() + " ");
                writer.println("File name: " + file.getFileName() + "<br/>");
            }
        }

        writer.println("</body></html>");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        String title = "Files";
        String docType = "<!DOCTYPE html>";

        writer.println(docType + "<html><head><title>" + title + "</title></head><body>");

        long id;

        if (req.getParameter("fileId") != null && req.getParameter("fileId").matches("\\d+")) {
            id = Long.parseLong(req.getParameter("fileId"));

            if (req.getParameter("userId") != null && req.getParameter("userId").matches("\\d+")) {
                Event event = new Event();
                event.setStatus(Status.DELETED);
                event.setFile(fileService.getById(id));
                event.setUser(userService.getById(Long.valueOf(req.getParameter("userId"))));

                fileService.deleteById(id);

                if (fileService.getById(id) == null) {
                    eventService.save(event);
                    writer.println("File with ID " + id + " successfully deleted.<br/>");
                } else {
                    writer.println("Couldn't delete file.<br/>");
                }
            } else {
                writer.println("Specify the correct user ID.<br/>");
            }
        } else {
            writer.println("No file with such ID in the data base.<br/>");
        }

        writer.println("</body></html>");
    }
}
