package io.github.kapilchoudhary.file_system.model;

import lombok.NonNull;

public class File extends FileSystemComponent {
    private String extension;

    public File(@NonNull final String name) {
        super(name);
        this.extension = extractExtension(name);
    }

    private String extractExtension(@NonNull final String name) {
        int lastDotIndex = name.lastIndexOf(".");
        return (lastDotIndex > 0) ? name.substring(lastDotIndex + 1) : "";
    }

    public boolean isFile() {
        return true;
    }
}
