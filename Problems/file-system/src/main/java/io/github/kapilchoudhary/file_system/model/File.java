package io.github.kapilchoudhary.file_system.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class File extends FileSystemComponent {

    @Getter
    private String extension;

    @Getter
    @Setter
    private String content;

    public File(@NonNull final String name, String content) {
        super(name);
        this.extension = extractExtension(name);
        this.content = content;
    }

    private String extractExtension(@NonNull final String name) {
        int lastDotIndex = name.lastIndexOf(".");
        return (lastDotIndex > 0) ? name.substring(lastDotIndex + 1) : "";
    }

    public boolean isFile() {
        return true;
    }

    public long getSize() {
        return (content == null) ? 0 : content.getBytes().length;
    }

    public String display(int depth) {
        return " ".repeat(depth * 2) +
                getName() + " (" + getSize() + " bytes)";
    }
}
