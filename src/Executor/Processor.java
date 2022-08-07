package Executor;

import Interpreter.CmdClass;
import Interpreter.Command;
import Utilities.EType;
import Utilities.MyException;

import java.io.*;
import java.util.Map;
import java.util.Objects;
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

    public static void Test(InputStream in, OutputStream out) {
        try {
            BufferedWriter out_writer = new BufferedWriter(new OutputStreamWriter(out));
            Scanner scan = new Scanner(in);
            String key = scan.next();
            String value = Executor.variables.get(key);
            if (value == null) {
                out_writer.write("The key <\033[0;36m" + key + "\033[0m> is not been set.\n");
            } else {
                out_writer.write("The value of the key <\033[0;36m" + key + "\033[0m> is " + value + "\n");
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

    public static void Dir(InputStream in, OutputStream out) { // 响应 dir
        try {
            Scanner scan = new Scanner(in);
            BufferedWriter out_writer = new BufferedWriter(new OutputStreamWriter(out));
            if (scan.hasNext()) { // 指定扫描某路径

            } else {
                File dir = new File(Executor.GetWD());
                String[] children = dir.list();
                for(int i = 0; i < children.length; ++i) {
                    out_writer.write(children[i]);
                    if (i == children.length - 1) {
                        out_writer.write("\n");
                    } else {
                        out_writer.write(" ");
                    }
                }
            }
            out_writer.flush();
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }

    public static void CD(InputStream in) throws MyException { // 响应 cd
        try {
            Scanner scan = new Scanner(in);
            if (!scan.hasNext()) {
                throw new MyException(EType.RuntimeError, "Missing parameters\n"); // 缺少参数
            }
            String input = scan.next(); // 获取 input
            if (Objects.equals(input, "..")){
                File dir = new File(Executor.GetWD());
                String target = dir.getParent(); // 获取上一级目录
                if (target != null) { // 如果非空，即存在上一级目录
                    Executor.SetPath(target); // 设置工作路径
                } else {
                    throw new MyException(EType.RuntimeError, "Cannot go to the previous level of the directory\n");
                }
            } else {
                String target = Executor.GetWD() + "\\" + input; // 切换目录
                System.out.println(target + "\n");
                File dir = new File(target);
                if (!dir.exists()) {
                    throw new MyException(EType.RuntimeError, "The directory is not exists\n");
                } else if (!dir.isDirectory()){ // 当前路径不是文件夹
                    throw new MyException(EType.RuntimeError, "It is not a directory!\n");
                } else { // 正常，设置路径
                    Executor.SetPath(target);
                }
            }
        } catch (MyException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }

    public static void Exit() { // 响应 exit
        try {
            System.out.println("[Exit Shell Process]\n"); // 输出提示信息
            System.exit(0); // 退出程序
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }

    public static void Set(InputStream in) { // 设置环境变量
        try {
            Scanner scan = new Scanner(in);
            String key = scan.next(); // 获取 key
            String value = scan.next(); // 获取 value
            Executor.SetVariable(key, value);
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }

    public static void Echo(InputStream in, OutputStream out) { // 响应 echo
        try {
            Scanner scan = new Scanner(in);
            BufferedWriter out_writer = new BufferedWriter(new OutputStreamWriter(out));
            out_writer.write(scan.next()+"\n"); // 输出相关信息并换行
            out_writer.flush();
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }

    public static void Sleep(InputStream in) { // 响应 sleep
        try {
            Scanner scan = new Scanner(in);
            String timeString = scan.next();
            int sleepTime = 0; // 获取睡眠秒数
            try {
                sleepTime = Integer.parseInt(timeString);
            } catch (Exception e) {
                System.out.println("[SyntaxError] " + "Sleep's time must be integer!");
            }
            Thread.sleep(sleepTime); // 线程睡眠
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }

//    public static void bg(InputStream in, OutputStream out) {
//        try {
//            Scanner scan = new Scanner(in);
//            String timeString = scan.next();
//            int sleepTime = 0; // 获取睡眠秒数
//            try {
//                sleepTime = Integer.parseInt(timeString);
//            } catch (Exception e) {
//                System.out.println("[SyntaxError] " + "Thread id must be integer!");
//            }
//        } catch (Exception e) {
//            System.out.println("[RuntimeError] " + e.getMessage());
//        }
//    }

    public static void Jobs(OutputStream out){ // 响应 jobs
        try {
            ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
            int noThreads = currentGroup.activeCount();
            Thread[] lstThreads = new Thread[noThreads];
            currentGroup.enumerate(lstThreads); // 获取所有线程信息
            BufferedWriter out_writer = new BufferedWriter(new OutputStreamWriter(out));
            out_writer.write("There are " + noThreads + " threads\n");
            for (int i = 0; i < noThreads; i++) {
                out_writer.write("\033[0;36mThread ID:\033[0m " + i); // 输出ID
                if (lstThreads[i].isDaemon()) // 输出前台/后台运行
                    out_writer.write(" \033[0;36mState:\033[0m running in the backend  ");
                else out_writer.write(" \033[0;36mState:\033[0m running in the frontend ");
                out_writer.write("\033[0;36mName:\033[0m " + lstThreads[i].getName() + "\n"); // 输出名字
            }
            out_writer.flush();
        } catch (Exception e) {
            System.out.println("[RuntimeError] " + e.getMessage());
        }
    }
}
