package io.github.kapilchoudhary.file_system.exception;

public class InvalidPathException extends RuntimeException {
    public InvalidPathException(String path) {
        super("Invalid path: " + path);
    }
}
