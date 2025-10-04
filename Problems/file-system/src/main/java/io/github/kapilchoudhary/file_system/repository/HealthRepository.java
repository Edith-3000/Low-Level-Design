package io.github.kapilchoudhary.file_system.repository;

import org.springframework.stereotype.Repository;

@Repository
public class HealthRepository {

    public boolean isDatabaseUp() {
        // In the future, you could check DB connectivity or run a test query.
        // For now, always return true.
        return true;
    }
}
