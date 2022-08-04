package Interpreter;

import Utilities.EType;
import Utilities.MyException;

import java.util.LinkedHashMap;

// 负责解析输入的语句，包括重定向的功能(<,>,|)
// 设置命令号，将剩余的元素当作Args传入
public class Interpreter {

    public static LinkedHashMap<String, CmdClass> commandMap;
    public Interpreter () {
        commandMap = new LinkedHashMap<String, CmdClass>(){};
        for (CmdClass cc: CmdClass.values()){
            commandMap.put(cc.name(), cc);
        }
    }
    public static void Prompt() { // 输出提示信息

    }
    public Command Read(String s) throws MyException { // 读取并翻译指令
        // 如果是可执行指令，则执行
        // 不然就报错
        String[] elements = s.split("\\s+"); // 根据空格/回车/换行分隔
        Command cmd = new Command();
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
        return cmd;
    }
}
