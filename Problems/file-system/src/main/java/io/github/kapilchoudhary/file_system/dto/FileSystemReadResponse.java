package io.github.kapilchoudhary.file_system.dto;

import io.github.kapilchoudhary.file_system.enums.FileSystemComponentType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class FileSystemReadResponse {

    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private FileSystemComponentType type;
    private long size;

    // File-specific
    private String content;

    // Directory-specific
    private List<String> children;
}
