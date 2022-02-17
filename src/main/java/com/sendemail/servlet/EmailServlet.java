package com.sendemail.servlet;

import com.sendemail.converter.PDFout;
import com.sendemail.service.EmailService;
import com.sendemail.service.SendMail;
import org.docx4j.openpackaging.exceptions.Docx4JException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@WebServlet(name = "EmailServlet", value = "/EmailServlet")
public class EmailServlet extends HttpServlet {

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
           try {
              String s= getServletContext().getRealPath("document.docx");
               PDFout.savePDF(json,s);
           } catch (Docx4JException e) {
               e.printStackTrace();
           }
           emailService = new EmailService();
           List<String> em = emailService.getEmails();
           SendMail.sendEmail(em);
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}
