package io.github.kapilchoudhary.file_system.dto;

import lombok.Getter;

public class FileSystemUpdateRequest {

    @Getter
    private String path;

    @Getter
    private String content;
}
