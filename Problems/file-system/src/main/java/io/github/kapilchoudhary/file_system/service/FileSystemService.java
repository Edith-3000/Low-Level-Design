package io.github.kapilchoudhary.file_system.service;

import io.github.kapilchoudhary.file_system.dto.FileSystemCreateRequest;
import io.github.kapilchoudhary.file_system.enums.FileSystemComponentType;
import io.github.kapilchoudhary.file_system.exception.InvalidPathException;
import io.github.kapilchoudhary.file_system.model.Directory;
import io.github.kapilchoudhary.file_system.model.File;
import io.github.kapilchoudhary.file_system.model.FileSystemComponent;
import org.springframework.stereotype.Service;

@Service
public class FileSystemService {
    private final FileSystemComponent root;

    public FileSystemService() {
        this.root = new Directory("/");
    }

    public void create(FileSystemCreateRequest request) {
        String path = request.getPath();

        if (!isValidPath(path)) {
            throw new InvalidPathException(path);
        }

        String[] pathComponents = path.split("/");
        FileSystemComponent current = root;

        for (int i = 0; i < pathComponents.length - 1; i++) {
            String component = pathComponents[i];

            if (component.isEmpty()) {
                continue;
            }

            if (!current.hasChild(component)) {
                FileSystemComponent newDirectory = new Directory(component);
                current.addChild(component, newDirectory);
            }

            FileSystemComponent childComponent = current.getChild(component);
            if (childComponent.isFile()) {
                throw new InvalidPathException(path);
            }

            current = childComponent;
        }

        String lastComponent = pathComponents[pathComponents.length - 1];
        if (lastComponent.isEmpty()) {
            throw new InvalidPathException(path);
        }

        FileSystemComponentType type = request.getType();
        FileSystemComponent newComponent = null;

        if (type == FileSystemComponentType.FILE) {
            newComponent = new File(lastComponent);
        } else {
            newComponent = new Directory(lastComponent);
        }

        current.addChild(lastComponent, newComponent);
    }

    private boolean isValidPath(String path) {
        return (path != null) && (path.startsWith("/"));
    }
}
