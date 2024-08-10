package org.example.pubsubsystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Message {
    private final String id;
    private final long timestamp;
    private final String content;
}
