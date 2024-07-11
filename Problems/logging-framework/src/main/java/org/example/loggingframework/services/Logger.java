package org.example.loggingframework.services;

import org.example.loggingframework.enums.LogLevel;
import org.example.loggingframework.interfaces.ConsoleAppender;
import org.example.loggingframework.models.LogMessage;
import org.example.loggingframework.models.LoggerConfig;

public class Logger {
    private static final Logger instance = new Logger();
    private LoggerConfig config;

    private Logger() {
        config = new LoggerConfig(LogLevel.DEBUG, new ConsoleAppender());
    }

    public static Logger getInstance() {
        return instance;
    }

    public void setConfig(LoggerConfig loggerConfig) {
        this.config = loggerConfig;
    }

    private void log(LogLevel logLevel, String message) {
        if (logLevel.ordinal() >= config.getLogLevel().ordinal()) {
            LogMessage logMessage = new LogMessage(logLevel, message);
            config.getLogAppender().append(logMessage);
        }
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void fatal(String message) {
        log(LogLevel.FATAL, message);
    }
}
