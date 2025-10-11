package io.github.kapilchoudhary.file_system.exception;

public class NotAFileException extends RuntimeException {
    public NotAFileException(String path) {
        super(path + " is not a file");
    }
}
