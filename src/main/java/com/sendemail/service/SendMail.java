package com.sendemail.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class SendMail {

    public static void sendEmail(String path, List<String> emailsTo) {


        //String to = emailsTo;
        String from = "eyllowboy@gmail.com";
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("eyllowboy@gmail.com", "abc6108306");
            }
        });
        for (String em : emailsTo) {

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(em));
                message.setSubject("Daily report from the blue team");
                Multipart multipart = new MimeMultipart();
                MimeBodyPart attachmentPart = new MimeBodyPart();
                MimeBodyPart textPart = new MimeBodyPart();

                try {
                    File f = new File(path+"document.pdf");
                    attachmentPart.attachFile(f);
                    textPart.setText("Report");
                    multipart.addBodyPart(textPart);
                    multipart.addBodyPart(attachmentPart);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                message.setContent(multipart);
                System.out.println("sending...");
                Transport.send(message);
                System.out.println("Sent message successfully....");
            } catch (MessagingException mex) {
                mex.printStackTrace();
            }

        }
    }

}