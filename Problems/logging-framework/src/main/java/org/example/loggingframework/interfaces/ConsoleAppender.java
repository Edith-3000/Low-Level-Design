package org.example.loggingframework.interfaces;

import org.example.loggingframework.models.LogMessage;

public class ConsoleAppender implements LogAppender {
    @Override
    public void append(LogMessage logMessage) {
        System.out.println(logMessage);
    }
}
