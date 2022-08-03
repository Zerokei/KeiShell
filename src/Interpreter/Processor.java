package Interpreter;

public class Processor {
    public static void Process(Command cmd) {
        CmdClass wow = cmd.GetCommand();
        switch (wow) {
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
}
