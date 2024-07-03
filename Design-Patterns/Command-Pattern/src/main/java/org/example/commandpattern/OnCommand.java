package org.example.commandpattern;

public class OnCommand implements Command {
    private final Receiver receiver;

    public OnCommand(final Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.on();
    }
}
