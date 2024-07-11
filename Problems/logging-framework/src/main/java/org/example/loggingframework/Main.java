package org.example.loggingframework;

import org.example.loggingframework.enums.LogLevel;
import org.example.loggingframework.interfaces.DatabaseAppender;
import org.example.loggingframework.interfaces.FileAppender;
import org.example.loggingframework.models.LoggerConfig;
import org.example.loggingframework.services.Logger;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static void logMessages(Logger logger) {
        String threadName = Thread.currentThread().getName();

        logger.debug("{" + threadName + "} This is a debug message");
        logger.info("{" + threadName + "} This is an info message");
        logger.warn("{" + threadName + "} This is a warn message");
        logger.error("{" + threadName + "} This is an error message");
        logger.fatal("{" + threadName + "} This is a fatal message");
    }

    public static void main(String[] args) {
        Logger logger = Logger.getInstance();

        logMessages(logger);

        LoggerConfig loggerConfig = new LoggerConfig(LogLevel.WARN, new FileAppender("app.log"));

//        LoggerConfig loggerConfig = new LoggerConfig(LogLevel.WARN, new DatabaseAppender(
//                "jdbc:postgresql://my-db.cluster-op997ch972.us-east-1.rds.amazonaws.com:5432/postgres",
//                "superuser",
//                "random-password"
//        ));

        logger.setConfig(loggerConfig);

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
               logMessages(logger);
            });
        }

        executorService.shutdown();
    }
}
