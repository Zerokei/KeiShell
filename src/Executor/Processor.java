package Executor;

import Interpreter.CmdClass;
import Interpreter.Command;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    public static void Time(OutputStream out) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            BufferedWriter out_writer = new BufferedWriter(new OutputStreamWriter(out));
            out_writer.write(df.format(date)+'\n');
            out_writer.flush();
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }
}
