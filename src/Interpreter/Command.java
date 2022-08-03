package Interpreter;

import java.util.ArrayList;

public class Command {
    private CmdClass command;
    private ArrayList<String> args;

    public Command() {
        command = CmdClass.empty;
        args    = new ArrayList<>();
    }
    public void SetCommand(CmdClass command) {
        this.command = command;
    }
    public void InsertArgs(String arg) {
        this.args.add(arg);
    }
    public CmdClass GetCommand() { return this.command; }
}
