package com.sendemail.servlet;

import com.sendemail.converter.CreatePdf;
import com.sendemail.service.EmailService;
import com.sendemail.service.SendMail;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.Reader;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "EmailServlet", value = "/EmailServlet")
public class EmailServlet extends HttpServlet {
    EmailService emailService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try {
           response.addHeader("Access-Control-Allow-Origin", "*");
           response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
           response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
           response.addHeader("Access-Control-Max-Age", "1728000");
           StringBuilder body = new StringBuilder();
           char[] buffer = new char[1024];
           int readChars;
           try (Reader reader = request.getReader()) {
               while ((readChars = reader.read(buffer)) != -1) {
                   body.append(buffer, 0, readChars);
               }
           }
           System.out.println(body);
           String json = body.toString();
          CreatePdf pdf = new CreatePdf();
           String basePath = request.getServletContext().getRealPath("/");
          pdf.create(basePath,json);

           emailService = new EmailService();
           List<String> em = emailService.getEmails();


           SendMail.sendEmail(basePath,em);

       }catch (Exception e){
           e.printStackTrace();
       }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/date.jsp");

        requestDispatcher.forward(request, response);
    }
}
