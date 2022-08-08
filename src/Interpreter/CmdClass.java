package Interpreter;

public enum CmdClass { // shell 功能及和
    empty, // 默认 empty
    jobs, // 显示任务
    echo, // 输出字符串
    help, // 获取帮助
    clr, // 清屏
    exit, // 退出
    exec, // 执行命令
    dir, // 获取目录下文件信息
    pwd, // 输出当前路径
    environ, // 获取环境变量
    umask, // 获取umask
    time, // 获取时间
    test, // 检测环境变量是否存在
    cd, // 切换路径
    set, // 设置环境变量
    sleep // 睡眠
}
