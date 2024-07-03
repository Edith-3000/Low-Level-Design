package org.example.commandpattern;

public class DownCommand implements Command {
    private final Receiver receiver;

    public DownCommand(final Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.down();
    }
}
