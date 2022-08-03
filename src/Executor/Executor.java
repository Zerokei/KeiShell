package Executor;

import Interpreter.Command;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;

public class Executor {
    private static final InputStream consoleIn  = System.in;
    private static final PrintStream consoleOut = System.out;

    public static LinkedHashMap<String, String> variables;

    public Executor() {
        variables = new LinkedHashMap<>();
        variables.put("SHELL", System.getProperty("user.dir") + "/KeiShell");   // 设置shell程序位置
        variables.put("HOME", System.getProperty("user.home"));                 // 设置家目录
        variables.put("PWD", System.getProperty("user.home"));                  // 设置当前路径
        variables.put("UMASK", "022");                                          // 设置当前目录权限
    }
    public static void Run(Command com) {
        
    }

}
