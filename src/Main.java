import Interpreter.Interpreter;
import Executor.Processor;
import Utilities.MyException;

import java.io.*;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        // Executor Init
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Interpreter itpt = new Interpreter();

        if (args.length > 0) { // 如果是执行某一文件
            try {
                String path = System.getProperty("user.dir") + "\\" + args[0];
                InputStream in = new FileInputStream(path); // 文件输入流
                itpt.ProcessFile(in);
            } catch (Exception e){
                System.out.println("[RuntimeError]: " + "No such file to read!");
            }
        }
        else { // 否则直接进入shell
            while(true) { // 一直循环等待输入
                // Interpreter prompt
                itpt.Prompt();
                String input = br.readLine(); // 在缓冲区读入一行
                if(!input.equals("")) { // 如果不为空行
                    try {
                        itpt.Read(input);
                        // process
                    } catch (MyException e) {
                        // 重置 itpt
                        System.out.println(e.GetMsg());
                    }
                }
            }
        }
    }
}

