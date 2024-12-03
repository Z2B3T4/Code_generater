package com.yupi.cli.example;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

// @Command 表明当前的类是要作为cli管理的，name是自己为这个命令行起的名字
// version 指定版本，一般目前（2024.12.3）都指定这个，mixinStandardHelpOptions = true表示自动生成帮助文档
@Command(name = "ASCIIArt", version = "ASCIIArt 1.0", mixinStandardHelpOptions = true) 
public class ASCIIArt implements Runnable { 

    // 这个就是动态可以把 -s 20 将这个20赋值给fontSize
    @Option(names = { "-s", "--font-size" }, description = "Font size") 
    int fontSize = 19;

    // 这个就是用户输入的这种 aaa bbb 就是这种不带-的进行赋值给数组
    @Parameters(paramLabel = "<word>", defaultValue = "Hello, picocli", 
               description = "Words to be translated into ASCII art.")
    private String[] words = { "Hello,", "picocli" }; 

    // 这个就是当永辉回车的时候，执行这个run方法
    @Override
    public void run() {
        // 自己实现业务逻辑
        System.out.println("fontSize = " + fontSize);
        System.out.println("words = " + String.join(",", words));
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ASCIIArt()).execute(args); 
        System.exit(exitCode); 
    }
}
