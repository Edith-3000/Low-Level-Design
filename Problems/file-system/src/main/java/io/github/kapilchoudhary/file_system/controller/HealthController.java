package io.github.kapilchoudhary.file_system.controller;

import io.github.kapilchoudhary.file_system.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private HealthService healthService;

    @GetMapping
    public Map<String, String> healthCheck() {
        String status = healthService.getHealthStatus();
        return Map.of("status", status);
    }
}
