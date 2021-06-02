package com.maltsevve.restApi.controller;


import com.maltsevve.restApi.model.File;
import com.maltsevve.restApi.service.FileService;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

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

        if (req.getParameter("fileId") != null && req.getParameter("fileId").matches("\\d+")) {
            Long l = Long.valueOf(req.getParameter("fileId"));
            File file = fileService.getById(Long.valueOf(req.getParameter("fileId")));
            writer.println("ID: " + file.getId());
            writer.println("File name: " + file.getFileName() + "<br/>");
        } else {
            List<File> files = fileService.getAll();
            files.forEach(file -> writer.println("ID: " + file.getId() + " File name: " + file.getFileName() + "<br/>"));
        }
        writer.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int fileMaxSize = 100 * 1024;
        int memMaxSize = 100 * 1024;

        String filePath = "C:/Users/Victor/IdeaProjects/REST-API/src/main/resources/uploads";
        java.io.File file;

        resp.setContentType("text/html");

        String docType = "<!DOCTYPE html>";
        String title = "File Uploading Demo";

        PrintWriter writer = resp.getWriter();


        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setRepository(new java.io.File(filePath));
        diskFileItemFactory.setSizeThreshold(memMaxSize);

        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
        upload.setSizeMax(fileMaxSize);

        try {
            List<FileItem> fileItems = upload.parseRequest((RequestContext) req);

            Iterator<FileItem> iterator = fileItems.iterator();

            writer.println(docType +
                    "<html>" +
                    "<head>" +
                    "<title>" + title + "</title>" +
                    "</head>" +
                    "<body>");

            while (iterator.hasNext()) {
                FileItem fileItem = iterator.next();
                if (!fileItem.isFormField()) {

                    String fileName = fileItem.getName();
                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new java.io.File(filePath +
                                fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new java.io.File(filePath +
                                fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fileItem.write(file);

                    //Сохраняем имя файла в БД
                    File file1 = new File();
                    file1.setFileName(fileName);
                    fileService.save(file1);

                    writer.println(fileName + " is uploaded.<br>");
                }
            }
            writer.println("</body></html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //update
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//    }
}
