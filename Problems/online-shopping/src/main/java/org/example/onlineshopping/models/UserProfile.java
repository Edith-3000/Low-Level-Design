package org.example.onlineshopping.models;

import lombok.Getter;
import lombok.NonNull;

public class UserProfile {
    @Getter
    private final String address;
    @Getter
    private final String email;
    @Getter
    private final String phoneNumber;

    public UserProfile(@NonNull final String address, @NonNull final String email, @NonNull final String phoneNumber) {
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
