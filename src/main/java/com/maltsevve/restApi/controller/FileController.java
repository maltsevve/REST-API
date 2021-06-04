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
//            case "save":
//                doPost(req, resp);
//                break;
//            case "update":
//                doPut(req, resp);
//                break;
            case "getById":
            case "getAll":
                doGet(req, resp);
                break;
            case "delete":
                doDelete(req, resp);
                break;
        }
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        int fileMaxSize = 100 * 1024;
//        int memMaxSize = 100 * 1024;
//
//        String filePath = "C:/Users/Victor/IdeaProjects/rest-api/src/main/resources/uploads";
//        java.io.File file;
//
//        resp.setContentType("text/html");
//        PrintWriter writer = resp.getWriter();

//        String title = "Files";
//        String docType = "<!DOCTYPE html>";
//
//        org.apache.commons.fileupload.disk.DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
//        diskFileItemFactory.setRepository(new java.io.File(filePath));
//        diskFileItemFactory.setSizeThreshold(memMaxSize);
//
//        org.apache.commons.fileupload.servlet.ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
//        upload.setSizeMax(fileMaxSize);
//
//        try {
//            List<org.apache.commons.fileupload.FileItem> fileItems = upload.parseRequest((RequestContext) req);
//            Iterator<org.apache.commons.fileupload.FileItem> iterator = fileItems.iterator();
//
//            writer.println(docType + "<html><head><title>" + title + "</title></head><body>");
//
//            while (iterator.hasNext()) {
//                FileItem fileItem = iterator.next();
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
//                    //Сохраняем имя файла в БД
//                    File file1 = new File();
//                    file1.setFileName(fileName);
//                    fileService.save(file1);
//
//                    writer.println(fileName + " is uploaded.<br>");
//                }
//            }
//            writer.println("</body></html>");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html");
//        PrintWriter writer = resp.getWriter();
//
//        String title = "Files";
//        String docType = "<!DOCTYPE html>";
//
//        writer.println(docType + "<html><head><title>" + title + "</title></head><body>");

//    }

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
            fileService.deleteById(id);
            if (fileService.getById(id) == null) {
                writer.println("File with ID " + id + " successfully deleted.<br/>");
            } else {
                writer.println("Couldn't delete file.<br/>");
            }
        } else {
            writer.println("No file with such ID in the data base.<br/>");
        }

        writer.println("</body></html>");
    }
}
