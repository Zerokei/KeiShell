package Utilities;

public class MyException extends java.lang.Exception { // 异常信息
    public String msg;
    public EType type;

    public MyException(EType type, String msg) { // 初始化
        this.msg  = msg;
        this.type = type;
    }

    public String getMsg() {
        return "[" + type.name() + ']' + msg;
    }
}
