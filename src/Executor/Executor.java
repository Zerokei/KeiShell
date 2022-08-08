package Executor;

import Interpreter.Command;
import Interpreter.Interpreter;
import Utilities.EType;
import Utilities.MyException;

import java.io.InputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

public class Executor { // 执行器，维护基本执行信息，与正在执行的线程
    public static LinkedHashMap<String, String> variables;

    public Executor() {
        variables = new LinkedHashMap<>();
        variables.put("SHELL", System.getProperty("user.dir") + "\\KeiShell");   // 设置shell程序位置
        variables.put("HOME", System.getProperty("user.dir"));                 // 设置家目录
        variables.put("PWD", System.getProperty("user.dir"));                  // 设置当前路径
        variables.put("UMASK", "022");                                          // 设置当前目录权限
    }

    public static String GetShell() { // 获取shell地址
        return variables.get("SHELL");
    } // 获取Shell信息

    public static String GetHome(){ // 获取home目录
        return variables.get("HOME");
    } // 获取Home信息

    public static String GetWD() {
        return variables.get("PWD");
    } // 获取路径信息

    public static String GetUMask() { // 获取UMask
        return variables.get("UMASK");
    } // 获取umask信息

    public static void SetPath(String val) { variables.put("PWD", val); } // 设置路径
    public static void SetVariable(String var, String val) {
        variables.put(var, val);
    } // 设置变量

    public static String GetTime() { // 获取系统时间
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); // 设置时间格式化字符串
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return df.format(date); // 返回时间
    }

    public static void SetupProcess(Command cmd) throws MyException {
        Thread thread = new Thread(cmd, cmd.GetName());
        try {
            thread.start();
            thread.join();// 等待该进程结束
        } catch (Exception e) {
            throw new MyException(EType.RuntimeError, "Failed to run the process.");
        }
    }
}
