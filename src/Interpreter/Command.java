package Interpreter;

import java.util.ArrayList;

public class Command {
    private String command;
    private ArrayList<String> args;

    public Command() {
        command = "";
        args    = new ArrayList<>();
    }
    public void setCommand(String command) {
        this.command = command;
    }

    public void insertArgs(String arg) {
        this.args.add(arg);
    }
}
