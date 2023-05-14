public class ErrorLogger extends Logger {
    public ErrorLogger(Logger nextLogger) {
        super(nextLogger);
    }

    public void log(int logLevel, String message) {
        if(logLevel == ERROR) {
            System.out.println("ERROR: " + message);
        }

        else {
            super.log(logLevel, message);
        }
    }
}
