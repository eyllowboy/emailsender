package com.sendemail.service;

import com.sendemail.DAO.EmailDAO;

import java.util.List;

public class EmailService {

    EmailDAO emailDAO;

    public List<String> getEmails() {

        emailDAO = new EmailDAO();
        return emailDAO.getAllEmails();

    }
}
