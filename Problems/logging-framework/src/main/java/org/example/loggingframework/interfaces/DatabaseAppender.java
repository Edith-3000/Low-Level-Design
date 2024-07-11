package org.example.loggingframework.interfaces;

import org.example.loggingframework.models.LogMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseAppender implements LogAppender {
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public DatabaseAppender(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public void append(LogMessage logMessage) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO logs (level, message, timestamp) VALUES (?, ?, ?")) {
            preparedStatement.setString(1, logMessage.getLevel().toString());
            preparedStatement.setString(2, logMessage.getMessage());
            preparedStatement.setLong(3, logMessage.getTimestamp());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
