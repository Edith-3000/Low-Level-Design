public class DebugLogger extends Logger {
    public DebugLogger(Logger nextLogger) {
        super(nextLogger);
    }

    public void log(int logLevel, String message) {
        if(logLevel == DEBUG) {
            System.out.println("DEBUG: " + message);
        }

        else {
            super.log(logLevel, message);
        }
    }
}
