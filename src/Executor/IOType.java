package Executor;

public enum IOType { // 设置输入输出流的格式
    PIPE_IN, // 管道输入
    PIPE_OUT, // 管道输出
    STD_IN, // 标准输入
    STD_OUT, // 标准输出
    FILE_IN, // 文件输入
    FILE_OUT, // 文件输出
    FILE_APPEND_OUT // 文件追加输出
}
