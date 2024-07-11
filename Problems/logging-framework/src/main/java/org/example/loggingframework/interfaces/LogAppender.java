package org.example.loggingframework.interfaces;

import org.example.loggingframework.models.LogMessage;

public interface LogAppender {
    void append(LogMessage logMessage);
}
