package com.yupi.maker.generator;

import java.io.*;

public class JarGenerator {
    // 这个就是使用java来执行命令
    // 不用死记硬背，因为命令本身就比较长，而且命令本身就比较稳定，用的时候看一下会用即可
    public static void doGenerate(String projectDir) throws IOException, InterruptedException {

        // 清理之前的构建并打包
        // 注意不同操作系统，执行的命令不同
        String winMavenCommand = "mvn.cmd clean package -DskipTests=true";
        String otherMavenCommand = "mvn clean package -DskipTests=true";
        String mavenCommand = winMavenCommand;

        // 这里一定要拆分！
        ProcessBuilder processBuilder = new ProcessBuilder(mavenCommand.split(" "));
        processBuilder.directory(new File(projectDir));

        Process process = processBuilder.start();

        // 读取命令的输出，尤其是这里基本就是这么写，不用死记硬背
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        // 等待命令执行完成
        int exitCode = process.waitFor();
        System.out.println("命令执行结束，退出码：" + exitCode);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        doGenerate("C:\\code\\yuzi-generator\\yuzi-generator-maker\\generated\\acm-template-pro-generator");
    }
}
