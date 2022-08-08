package Interpreter;

import Executor.Executor;
import Executor.IOType;
import Utilities.EType;
import Utilities.MyException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

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
//                    cmd.get(i).inType = IOType.STD_IN;
                }
                else {
                    cmd.get(i).inType = IOType.PIPE_IN;
                    cmd.get(i).pipeIn.connect(cmd.get(i - 1).pipeOut);
                }
                if (i == commands.length - 1) {
//                    cmd.get(i).outType = IOType.STD_OUT;
                } else {
                    cmd.get(i).outType = IOType.PIPE_OUT;
                }
            }
            // 先设置好所有管道，再执行
            for (int i = 0; i < commands.length; ++i) {
                if (cmd.get(i).isBackend) { // 如果后台运行
                    Thread thread = new Thread(cmd.get(i));
//                    thread.setName("Background Process");
                    thread.setDaemon(true);
                    thread.start();
                }
                else { // 前台运行
                    Executor.SetupProcess(cmd.get(i));
                }
            }
        } catch (MyException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("[SyntaxError]: " + e.getMessage());
        }
    }
    public void ProcessFile(InputStream in) { // 从文件中读入指令并处理
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String cmdline = null;
        try {
            while ((cmdline = reader.readLine()) != null) {
                System.out.println(cmdline + "\n");
                Read(cmdline);
            }
        } catch (Exception e) {
            System.out.println("[RuntimeError]: " + e.getMessage());
        }
    }
    public void GetCommand(String s, Command cmd) throws MyException {
        String[] elements = s.split("\\s+"); // 根据空格/回车/换行分隔
        ArrayList<String> elements_list = new ArrayList<String>(Arrays.asList(elements));


        if (Objects.equals(elements_list.get(0), "exec")) { // 如果开头是exec，后面直接执行
            elements_list.remove(0);
        }

        if (Objects.equals(elements_list.get(elements_list.size() - 1), "&")) {
            cmd.isBackend = true;
            elements_list.remove(elements_list.size() - 1);
        }


        // 处理重定向输入
        int inputPosition = elements_list.indexOf("<");
        if (inputPosition == 1) { // file < command
            cmd.inType = IOType.FILE_IN;
            cmd.inputFile = elements_list.get(0);
            elements_list.remove(0); // 删除文件名
            elements_list.remove(0); // 删除 <
        } else {
            cmd.inType = IOType.STD_IN;
        }

        // 处理重定向输出
        int outputPosition = elements_list.indexOf(">");
        if (outputPosition != -1 && outputPosition == elements_list.size() - 2) { // command > file
            cmd.outType = IOType.FILE_OUT;
            cmd.outputFile = elements_list.get(elements_list.size() - 1);
            elements_list.remove(elements_list.size() - 1); // 删除文件名
            elements_list.remove(elements_list.size() - 1); // 删除 >
        } else {
            cmd.outType = IOType.STD_OUT;
        }

        outputPosition = elements_list.indexOf(">>");
        if (outputPosition != -1 && outputPosition == elements_list.size() - 2) { // command >> file
            cmd.outType = IOType.FILE_APPEND_OUT;
            cmd.outputFile = elements_list.get(elements_list.size() - 1);
            elements_list.remove(elements_list.size() - 1); // 删除文件名
            elements_list.remove(elements_list.size() - 1); // 删除 >>
        }

        // 处理指令
        for (int i = 0; i < elements_list.size(); ++i) { // 遍历数组元素
            if(i == 0) {
                if (commandMap.containsKey(elements_list.get(0))) {
                    cmd.SetCommand(commandMap.get(elements_list.get(0)));
                    cmd.SetName(elements_list.get(0));
                } else {
                    throw new MyException(EType.SyntaxError, "Invalid Command: " + elements[0]);
                }
            }
            else {
                cmd.InsertArgs(elements_list.get(i));
            }
        }
    }
}
