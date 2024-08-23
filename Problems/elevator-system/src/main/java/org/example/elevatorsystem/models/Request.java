package org.example.elevatorsystem.models;

import lombok.Getter;
import lombok.NonNull;
import org.example.elevatorsystem.enums.Direction;

public class Request {
    @Getter
    private final int sourceFloorId;
    private final int destinationFloorId;
    @Getter
    private final Direction direction;

    // For external request
    public Request(final int sourceFloorId, @NonNull final Direction direction) {
        this.sourceFloorId = sourceFloorId;
        this.direction = direction;
        this.destinationFloorId = -1; // Not significant in case of external request
    }

    // For internal request
    public Request(final int sourceFloorId, final int destinationFloorId) {
        this.sourceFloorId = sourceFloorId;
        this.destinationFloorId = destinationFloorId;
        this.direction = (sourceFloorId < destinationFloorId) ? Direction.UP : Direction.DOWN;
    }
}
