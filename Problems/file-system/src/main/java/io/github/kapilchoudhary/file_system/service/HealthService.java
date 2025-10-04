package io.github.kapilchoudhary.file_system.service;

import io.github.kapilchoudhary.file_system.repository.HealthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    @Autowired
    private HealthRepository healthRepository;

    public String getHealthStatus() {
        boolean dbUp = healthRepository.isDatabaseUp();
        return dbUp ? "UP" : "DOWN";
    }
}
