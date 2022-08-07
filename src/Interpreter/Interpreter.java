package Interpreter;

import Executor.Executor;
import Executor.IOType;
import Utilities.EType;
import Utilities.MyException;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

// 负责解析输入的语句，包括重定向的功能(<,>,|)
// 设置命令号，将剩余的元素当作Args传入
public class Interpreter {
    public static Executor exec;
    public static LinkedHashMap<String, CmdClass> commandMap;
    public Interpreter () {
        commandMap = new LinkedHashMap<String, CmdClass>(){};
        for (CmdClass cc: CmdClass.values()){
            commandMap.put(cc.name(), cc);
        }
        exec = new Executor(){};
    }
    public static void Prompt() { // 输出提示信息
        System.out.print("\033[38;2;255;155;66m"+"["+exec.GetWD()+"]"+"\033[0m"+"$ ");
    }
    public void Read(String s) throws MyException { // 读取并翻译指令
        // 如果是可执行指令，则执行
        // 不然就报错
        String[] commands = s.split("\\|");
        ArrayList<Command> cmd = new ArrayList<Command>();
        try {
            for (int i = 0; i < commands.length; ++i) {
                cmd.add(new Command());
                GetCommand(commands[i].trim(), cmd.get(i));
                if (i == 0) {
                    cmd.get(i).inType = IOType.STD_IN;
                }
                else {
                    cmd.get(i).inType = IOType.PIPE_IN;
                    cmd.get(i).pipeIn.connect(cmd.get(i - 1).pipeOut);
                }
                if (i == commands.length - 1) {
                    cmd.get(i).outType = IOType.STD_OUT;
                } else {
                    cmd.get(i).outType = IOType.PIPE_OUT;
                }
            }
            // 先设置好所有管道，再执行
            for (int i = 0; i < commands.length; ++i) {
                Executor.SetupProcess(cmd.get(i));
            }
        } catch (MyException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("[SyntaxError]: " + e.getMessage());
        }

    }
    public void GetCommand(String s, Command cmd) throws MyException {
        String[] elements = s.split("\\s+"); // 根据空格/回车/换行分隔
        for (int i = 0; i < elements.length; ++i) {
            if(i == 0) {
                if (commandMap.containsKey(elements[0])) {
                    cmd.SetCommand(commandMap.get(elements[0]));
                    cmd.SetName(elements[0]);
                } else {
                    throw new MyException(EType.SyntaxError, "Invalid Command: " + elements[0]);
                }
            }
            else {
                cmd.InsertArgs(elements[i]);
            }
        }
    }
}
