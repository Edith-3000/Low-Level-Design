package io.github.kapilchoudhary.file_system.model;

import lombok.NonNull;

import java.util.Map;

public class Directory extends FileSystemComponent {

    public Directory(@NonNull final String name) {
        super(name);
    }

    public boolean isFile() {
        return false;
    }

    public long getSize() {
        long size = 0L;

        for (FileSystemComponent childrenComponent: getChildren().values()) {
            size += childrenComponent.getSize();
        }

        return size;
    }

    public String display(int depth) {
        StringBuilder fileSystem = new StringBuilder();

        String name = " ".repeat(depth * 2) +
                (getName().equals("/") ? "root" : getName());

        fileSystem.append(name).append("/");

        for (Map.Entry<String, FileSystemComponent> entry: getChildren().entrySet()) {
            fileSystem.append("\n");
            String representation = entry.getValue().display(depth + 1);
            fileSystem.append(representation);
        }

        return fileSystem.toString();
    }
}
