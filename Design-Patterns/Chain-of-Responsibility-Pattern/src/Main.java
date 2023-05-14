public class Main {
    public static void main(String[] args) {
        Logger loggerObject = new InfoLogger(new DebugLogger(new ErrorLogger(null)));

        loggerObject.log(Logger.INFO, "This is just an information");
        loggerObject.log(Logger.DEBUG, "Need to debug at this point");
        loggerObject.log(Logger.ERROR, "Some exception happened");

        loggerObject.log(4, "A different type of logger");
    }
}