package org.example.commandpattern;

public class Main {
    public static void main(String[] args) {
        final Light device = new Light();
//        final Speaker device = new Speaker();

        final Invoker invoker = new Invoker(
                new OnCommand(device),
                new OffCommand(device),
                new UpCommand(device),
                new DownCommand(device)
        );

        invoker.clickOn();
        invoker.clickOff();
        invoker.clickUp();
        invoker.clickDown();
    }
}
