package io.github.kapilchoudhary.file_system.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// Using composite design pattern
public abstract class FileSystemComponent {
    @Getter private final String name;
    @Getter private final LocalDateTime createdAt;
    @Getter @Setter private LocalDateTime modifiedAt;
    private final Map<String, FileSystemComponent> children;

    public FileSystemComponent(@NonNull final String name) {
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
        this.children = new HashMap<>();
    }

    public Map<String, FileSystemComponent> getChildren() {
        return Collections.unmodifiableMap(children);
    }

    public boolean hasChild(String name) {
        return children.containsKey(name);
    }

    public void addChild(String name, FileSystemComponent fileSystemComponent) {
        children.put(name, fileSystemComponent);
    }

    public FileSystemComponent getChild(String name) {
        return children.get(name);
    }

    public abstract boolean isFile();
}
