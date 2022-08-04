package Interpreter;

import Executor.Processor;

import java.io.*;
import java.util.ArrayList;

public class Command implements Runnable{
    private CmdClass command;
    private String commandName;
    private ArrayList<String> args;
    private PipedInputStream pipeIn;
    private PipedOutputStream pipeOut;
    private static Processor proc;

    public Command() {
        command = CmdClass.empty;
        commandName = "";
        args    = new ArrayList<>();
        proc    = new Processor();
    }

    @Override // 复写run
    public void run() { // 调用执行指令的函数，传递in, out
        InputStream in = null;
        OutputStream out = System.out; // 重定向out到System.out
//        out = System.out;

        switch (command) {
            case bg:
                proc.Print(in, out);
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
        try {
        }catch (Exception e){
            System.out.println(e.getMessage());
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
