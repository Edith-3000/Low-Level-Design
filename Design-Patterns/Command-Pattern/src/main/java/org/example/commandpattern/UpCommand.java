package org.example.commandpattern;

public class UpCommand implements Command {
    private final Receiver receiver;

    public UpCommand(final Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.up();
    }
}
