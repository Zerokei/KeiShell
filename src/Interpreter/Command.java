package Interpreter;

import Executor.IOType;
import Executor.Processor;
import Utilities.MyException;

import java.io.*;
import java.util.ArrayList;


public class Command implements Runnable{
    private CmdClass command; // 命令类
    private String commandName; // 命令名称
    private ArrayList<String> args; // 参数表
    public PipedInputStream pipeIn; // 管道输入
    public PipedOutputStream pipeOut; // 管道输出
    public IOType inType; // 输入格式
    public IOType outType; // 输出格式
    public String inputFile; // 输入文件名称（如果从文件读入的话）
    public String outputFile;// 输出文件名称（如果从文件输出的话）
    public Boolean isBackend; // 是否后台运行
    private static Processor proc = new Processor();

    public Command() { // command初始化
        command = CmdClass.empty;
        commandName = "";
        args    = new ArrayList<>();
        pipeIn  = new PipedInputStream();
        pipeOut = new PipedOutputStream(); // 新建对象
        inType  = IOType.STD_IN; // 默认为STD_IN
        outType = IOType.STD_OUT; // 默认为STD_OUT
        inputFile   = null;
        outputFile  = null;
        isBackend   = false;
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
                break;
            case FILE_APPEND_OUT: // 输出到指定文件
                try {
                    out = new FileOutputStream(outputFile, true);
                } catch (Exception e) {
                    System.out.println("[RuntimeError]");
                }
                break;
        }
        switch (inType) {
            case STD_IN: // 标准输入
                StringBuilder input = new StringBuilder();
                for (String s : args) {
                    input.append(s).append(" "); // 将参数逐个塞入输入流
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
                case jobs: // 展示线程信息
                    proc.Jobs(out);
                    break;
                case sleep: // 设置睡眠
                    proc.Sleep(in);
                    break;
                case echo: // 输出字符串
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
                case dir: // 显示当前路径下信息
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
                case test: // 测试某环境变量是否存在
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

    public void SetCommand(CmdClass command) { // 设置 command
        this.command = command;
    } // 设置命令类
    public void SetName(String name) { this.commandName = name; } // 设置进程名称
    public void InsertArgs(String arg) { // 插入变量
        this.args.add(arg);
    } // 插入参数
    public String GetName(){ return this.commandName; } // 获取名称
}
