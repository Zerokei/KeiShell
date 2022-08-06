package Executor;

import Interpreter.CmdClass;
import Interpreter.Command;

import java.io.*;

public class Processor { // 执行具体的指令

    public static void dir(InputStream in) {

    }
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
}
