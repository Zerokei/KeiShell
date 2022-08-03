import Interpreter.Interpreter;
import Utilities.MyException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Executor Init
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Interpreter itpt = new Interpreter();

        while(true) { // 一直循环等待输入
            // Interpreter prompt
            String input = br.readLine(); // 在缓冲区读入一行
            if(!input.equals("")) { // 如果不为空行
                try {
                    itpt.Read(input);
                    // process
                } catch (MyException e) {
                    // 重置 itpt
                    System.out.println(e.getMsg());
                }
            }
        }
    }
}
