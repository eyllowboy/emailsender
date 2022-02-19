package com.sendemail.servlet;

import com.sendemail.converter.CreatePdf;
import com.sendemail.service.EmailService;
import com.sendemail.service.SendMail;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "EmailServlet", value = "/EmailServlet")
public class EmailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("!!!!!!!!!");
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
            pdf.create("",json);

            emailService = new EmailService();
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/date.jsp");

            requestDispatcher.forward(request, response);

            // List<String> em = emailService.getEmails();
//            List<String> em= new ArrayList<>();em.add("si-roga@yandex.ru");
//
//            SendMail.sendEmail(em);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    EmailService emailService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try {
           System.out.println("!!!!!!!!!");
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
          pdf.create("",json);

           emailService = new EmailService();
          // List<String> em = emailService.getEmails();
           List<String> em= new ArrayList<>();em.add("si-roga@yandex.ru");

           SendMail.sendEmail(em);
           RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/date.jsp");

           requestDispatcher.forward(request, response);
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}
