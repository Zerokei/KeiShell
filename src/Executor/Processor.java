package Executor;

import Interpreter.CmdClass;
import Interpreter.Command;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class Processor { // 执行具体的指令

    public static void Print(InputStream in, OutputStream out) {
        try {
            BufferedWriter out_writer = new BufferedWriter(new OutputStreamWriter(out));
            String wow = "wow";
            out_writer.write(wow);
            out_writer.flush();
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }

    public static void Time(OutputStream out) { // 响应 time
        try {
            BufferedWriter out_writer = new BufferedWriter(new OutputStreamWriter(out));
            out_writer.write(Executor.GetTime() + '\n');
            out_writer.flush();
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }

    public static void Umask(OutputStream out) { // 响应 umask
        try {
            BufferedWriter out_writer = new BufferedWriter(new OutputStreamWriter(out));
            out_writer.write(Executor.GetUMask() + '\n');
            out_writer.flush();
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }

    public static void Pwd(OutputStream out) { // 响应 pwd
        try {
            BufferedWriter out_writer = new BufferedWriter(new OutputStreamWriter(out));
            out_writer.write(Executor.GetWD() + '\n');
            out_writer.flush();
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }

    public static void Help(OutputStream out) { // 响应 help
        try {
            String fileName = System.getProperty("user.dir") + "/Manual"; // 打开manual
            File file = new File(fileName); // 创建文件对象
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            BufferedWriter out_writer = new BufferedWriter(new OutputStreamWriter(out));
            while((line = br.readLine()) != null) { // 逐行读入
                out_writer.write(line + '\n');
            }
            out_writer.flush();
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }

    public static void Environ(OutputStream out) { // 响应 environ
        try {
            BufferedWriter out_writer = new BufferedWriter(new OutputStreamWriter(out));
            out_writer.write("\033[0;36m Shell:\033[0m " + Executor.GetShell() + '\n'); // 输出shell信息
            out_writer.write("\033[0;36m Home:\033[0m " + Executor.GetHome() + '\n'); // 输出home信息
            out_writer.write("\033[0;36m Present Path:\033[0m " + Executor.GetWD() + '\n'); // 输出当前路径
            out_writer.write("\033[0;36m UMask:\033[0m " + Executor.GetUMask() + '\n'); // 输出当前umask
            out_writer.write("\033[0;36m Time:\033[0m " + Executor.GetTime() + '\n'); // 输出当前时间
            for (Map.Entry<String, String> entry : Executor.variables.entrySet()) { // 输出其他环境变量（通过set设置）
                if (entry.getKey() == "SHELL" || entry.getKey() == "HOME" || entry.getKey() == "PWD" || entry.getKey() == "UMASK") continue;
                out_writer.write(" " + entry.getKey() + ": " + entry.getValue() + "\n");
            }
            out_writer.flush();
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }

    public static void Clear() { // 响应 clr
        try {
            System.out.println("\033[H\033[2J"); // 清屏
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }

    public static void CD(InputStream in) {
    }

    public static void Exit() { // 响应 exit
        try {
            System.out.println("[Exit Shell Process]\n"); // 输出提示信息
            System.exit(0); // 退出程序
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }

    public static void Set(InputStream in) {
        try {
            Scanner scan = new Scanner(in);
            String a = scan.next();
            String b = scan.next();
            Executor.SetVariable(a, b);
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }

    public static void Echo(InputStream in, OutputStream out) {
        try {
            Scanner scan = new Scanner(in);
            BufferedWriter out_writer = new BufferedWriter(new OutputStreamWriter(out));
            out_writer.write(scan.next()+"\n");
            out_writer.flush();
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }
}
