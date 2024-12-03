package com.yupi.cli.pattern;


// 这个是命令模式的设计模式，就是统一制定规范，发送者和接受者之间进行解耦
public interface Command {

    void execute();
}
