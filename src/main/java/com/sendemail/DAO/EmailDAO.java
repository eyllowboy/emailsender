package com.sendemail.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmailDAO {

    private String jdbcURL = "jdbc:postgresql://34.116.245.1:5432/maildb?useSSL=false";
   // private String jdbcURL = "jdbc:postgresql://34.116.245.1:5432/maildb?useSSL=false";
    private String jdbcUsername = "mail";
    private String jdbcPassword = "strongpassword";
    private static final String SELECT_ALL_EMAILS = "select * from emails";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<String> getAllEmails() {

        List<String> emails = new ArrayList<>();
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EMAILS);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String email = rs.getString("email");
                emails.add(email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emails;
    }
}
