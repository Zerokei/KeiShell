package Executor;

import Interpreter.CmdClass;
import Interpreter.Command;

import java.io.*;

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

    public static void Environ(OutputStream out) {
        try {
            BufferedWriter out_writer = new BufferedWriter(new OutputStreamWriter(out));
            out_writer.write("\033[0;36m Shell:\033[0m " + Executor.GetShell() + '\n');
            out_writer.write("\033[0;36m Home:\033[0m " + Executor.GetHome() + '\n');
            out_writer.write("\033[0;36m Present Path:\033[0m " + Executor.GetWD() + '\n');
            out_writer.write("\033[0;36m UMask:\033[0m " + Executor.GetUMask() + '\n');
            out_writer.write("\033[0;36m Time:\033[0m " + Executor.GetTime() + '\n');
            out_writer.flush();
        } catch (Exception e) {
            System.out.println(("[RuntimeError] " + e.getMessage()));
        }
    }
}
