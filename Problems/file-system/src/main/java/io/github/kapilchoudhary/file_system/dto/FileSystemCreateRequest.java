package io.github.kapilchoudhary.file_system.dto;

import io.github.kapilchoudhary.file_system.enums.FileSystemComponentType;
import lombok.Getter;

public class FileSystemCreateRequest {

    @Getter
    private String path;

    @Getter
    private FileSystemComponentType type;

    @Getter
    private String content;
}
