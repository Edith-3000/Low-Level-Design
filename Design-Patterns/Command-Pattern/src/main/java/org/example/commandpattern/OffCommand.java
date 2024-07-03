package org.example.commandpattern;

public class OffCommand implements Command {
    private final Receiver receiver;

    public OffCommand(final Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.off();
    }
}
