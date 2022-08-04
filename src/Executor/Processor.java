package Executor;

import Interpreter.CmdClass;
import Interpreter.Command;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

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
            System.out.println(e.getMessage());
        }
    }
}
