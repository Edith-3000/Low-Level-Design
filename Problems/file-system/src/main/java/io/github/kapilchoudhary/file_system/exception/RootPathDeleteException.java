package io.github.kapilchoudhary.file_system.exception;

public class RootPathDeleteException extends RuntimeException {
    public RootPathDeleteException() {
        super("Cannot delete root path: '/'");
    }
}
