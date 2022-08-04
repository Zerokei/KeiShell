package Interpreter;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;

public class Command implements Runnable{
    private CmdClass command;
    private String commandName;
    private ArrayList<String> args;
    private InputStream in;
    private OutputStream out;

    public Command() {
        command = CmdClass.empty;
        commandName = "";
        args    = new ArrayList<>();
    }

    @Override // 复写run
    public void run() { // 调用执行指令的函数，传递in, out
        switch (command) {
            case bg:
                System.out.println("wow\n");
            case fg:
            case jobs:
            case echo:
            case help:
            case clr:
            case exit:
            case exec:
            case myshell:
            case dir:
            case pwd:
            case environ:
            case unmask:
            case time:
            case test:
            case cd:
            case set:
                break;
            default:
                break;
        }
    }

    public void SetCommand(CmdClass command) {
        this.command = command;
    }
    public void SetName(String name) { this.commandName = name; }
    public void InsertArgs(String arg) {
        this.args.add(arg);
    }
    public String GetName(){ return this.commandName; }
    public CmdClass GetCommand() { return this.command; }
}
