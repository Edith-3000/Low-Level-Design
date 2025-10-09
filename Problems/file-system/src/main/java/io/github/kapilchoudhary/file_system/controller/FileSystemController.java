package io.github.kapilchoudhary.file_system.controller;

import io.github.kapilchoudhary.file_system.dto.FileSystemCreateRequest;
import io.github.kapilchoudhary.file_system.dto.FileSystemReadResponse;
import io.github.kapilchoudhary.file_system.service.FileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fs")
public class FileSystemController {

    @Autowired
    private FileSystemService fileSystemService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody FileSystemCreateRequest request) {
        fileSystemService.create(request);
        return ResponseEntity.ok("Created successfully at: " + request.getPath());
    }

    @GetMapping("/read")
    public ResponseEntity<FileSystemReadResponse> read(@RequestParam String path) {
        return ResponseEntity.ok(fileSystemService.read(path));
    }
}
