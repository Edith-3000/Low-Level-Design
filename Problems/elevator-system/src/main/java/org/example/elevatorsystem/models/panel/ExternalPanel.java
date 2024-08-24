package org.example.elevatorsystem.models.panel;

import lombok.NonNull;
import org.example.elevatorsystem.enums.Direction;
import org.example.elevatorsystem.models.requestdispatcher.ExternalRequestDispatcher;
import org.example.elevatorsystem.models.Floor;
import org.example.elevatorsystem.models.Request;

public class ExternalPanel {
    private final Floor floor;

    public ExternalPanel(@NonNull final Floor floor) {
        this.floor = floor;
    }

    public int pressButton(Direction direction) {
        return ExternalRequestDispatcher.getInstance().dispatchRequest(new Request(floor.getId(), direction));
    }
}
