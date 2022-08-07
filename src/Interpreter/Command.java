package Interpreter;

import Executor.IOType;
import Executor.Processor;
import Utilities.MyException;

import java.io.*;
import java.util.ArrayList;


public class Command implements Runnable{
    private CmdClass command;
    private String commandName;
    private ArrayList<String> args;
    public PipedInputStream pipeIn;
    public PipedOutputStream pipeOut;
    public IOType inType;
    public IOType outType;
    public String inputFile;
    public String outputFile;
    private static Processor proc = new Processor();

    public Command() {
        command = CmdClass.empty;
        commandName = "";
        args    = new ArrayList<>();
        pipeIn  = new PipedInputStream();
        pipeOut = new PipedOutputStream();
        inType  = IOType.STD_IN;
        outType = IOType.STD_OUT;
    }

    @Override // 复写run
    public void run() { // 调用执行指令的函数，传递in, out
        InputStream in = null;
        OutputStream out = null;
        switch (outType) {
            case STD_OUT: // 标准输出
                out = System.out; // 重定向out到System.out，即屏幕输出
                break;
            case PIPE_OUT: // 管道输出
                out = pipeOut; // 输出到管道
                break;
            case FILE_OUT: // 输出到指定文件
                try {
                    out = new FileOutputStream(outputFile);
                } catch (Exception e) {
                    System.out.println("[RuntimeError]");
                }
        }
        switch (inType) {
            case STD_IN: // 标准输入
                StringBuilder input = new StringBuilder();
                for (String s : args) {
                    input.append(s).append(" ");
                }
                try {
                    in = new ByteArrayInputStream(input.toString().getBytes("UTF-8"));
                } catch (Exception e) {
                    System.out.println("[RuntimeError in read args]: " + e.getMessage());
                }
                break;
            case PIPE_IN: // 管道读入
                in = pipeIn; // 从管道中读入 (因为不涉及参数之类，所以不再从 args 中读入)
                break;
            case FILE_IN:
                try {
                    in = new FileInputStream(inputFile);
                } catch (Exception e) {
                    System.out.println("[RuntimeError]: " + "No such file to read!");
                }
        }

        try {
            switch (command) {
                case bg:
                case fg:
                case jobs:
                case exec:
                case myshell:
                case sleep:
                    proc.Sleep(in);
                    break;
                case echo:
                    proc.Echo(in, out);
                    break;
                case help: // 输出帮助手册
                    proc.Help(out);
                    break;
                case clr: // 清屏
                    proc.Clear();
                    break;
                case exit: // 退出
                    proc.Exit();
                    break;
                case dir:
                    proc.Dir(in, out);
                    break;
                case pwd: // 输出路径
                    proc.Pwd(out);
                    break;
                case environ: // 输出环境信息
                    proc.Environ(out);
                    break;
                case umask: // 显示umask
                    proc.Umask(out);
                    break;
                case time: // 显示时间
                    proc.Time(out);
                    break;
                case test:
                    proc.Test(in, out);
                    break;
                case cd: // 进入目录
                    proc.CD(in);
                    break;
                case set: // 设置变量
                    proc.Set(in);
                    break;
                default:
                    break;
            }
        } catch (MyException e) {
            System.out.println(e.GetMsg());
        } catch (Exception e){
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
