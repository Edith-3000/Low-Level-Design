package io.github.kapilchoudhary.file_system.model;

import lombok.NonNull;

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
}
