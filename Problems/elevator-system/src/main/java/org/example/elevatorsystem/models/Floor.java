package org.example.elevatorsystem.models;

import lombok.Getter;
import org.example.elevatorsystem.models.display.FloorDisplay;
import org.example.elevatorsystem.models.panel.ExternalPanel;

public class Floor {
    @Getter
    private final int id;
    @Getter
    private final FloorDisplay floorDisplay;
    @Getter
    private final ExternalPanel externalPanel;

    public Floor(final int id) {
        this.id = id;
        this.floorDisplay = new FloorDisplay();
        this.externalPanel = new ExternalPanel(this);
    }
}
