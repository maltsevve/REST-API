package com.maltsevve.restApi.controller;

import com.maltsevve.restApi.model.File;
import com.maltsevve.restApi.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileController extends HttpServlet {
    private final FileService fileService = new FileService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getParameter("crud")) {
            case "save" -> {
                doPost(req, resp);
            }
            case "update" -> {
                doPut(req, resp);
            }
            case "getById", "getAll" -> {
                doGet(req, resp);
            }
            case "delete" -> {
                doDelete(req, resp);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        String title = "Files";
        String docType = "<!DOCTYPE html>";

        writer.println(docType + "<html><head><title>" + title + "</title></head><body>");
        writer.println("<h1>Files</h1>");
        writer.println("<br/>");

        if (req.getParameter("fileId").matches("\\d+")) {
            Long l = Long.valueOf(req.getParameter("fileId"));
            File file = fileService.getById(Long.valueOf(req.getParameter("fileId")));
            writer.println("ID: " + file.getId());
            writer.println("File name: " + file.getFileName() + "<br/>");
        } else {
            List<File> files = fileService.getAll();
            files.forEach(file -> writer.println("ID: " + file.getId() + "File name: " + file.getFileName() + "<br/>"));
        }

        writer.println("</body></html>");
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
}
