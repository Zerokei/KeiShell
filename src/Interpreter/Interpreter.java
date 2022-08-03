package Interpreter;

import Utilities.MyException;

public class Interpreter {

    public Interpreter () {

    }
    public static void Prompt() { // 输出提示信息

    }
    public void Read(String s) throws MyException { // 读取并翻译指令
        // 如果是可执行指令，则执行
        // 不然就报错
        String[] elements = s.split("\\s+"); // 根据空格/回车/换行分隔
        Command cmd = new Command();
        for (int i = 0; i < elements.length; ++i) {
            if(i == 0) {
                cmd.setCommand(elements[i]);
            }
            else {
                cmd.insertArgs(elements[i]);
            }
        }
    }
}
