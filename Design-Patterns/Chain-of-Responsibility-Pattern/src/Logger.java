public class Logger {
    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;

    private final Logger nextLogger;

    protected Logger(Logger nextLogger) {
        this.nextLogger = nextLogger;
    }

    protected void log(int logLevel, String message) {
        if(nextLogger != null) {
            nextLogger.log(logLevel, message);
        }

        else {
            System.out.println("Cannot process request.");
        }
    }
}
