package com.yupi.cli.command;

import cn.hutool.core.io.FileUtil;
import picocli.CommandLine;

import java.io.File;
import java.util.List;
@CommandLine.Command(name = "list", version = "list 1.0", mixinStandardHelpOptions = true)
public class ListCommand implements Runnable {
    @Override
    public void run() {
        String projectPath = System.getProperty("user.dir");
        File parentFile = new File(projectPath).getParentFile();
        String inputPath = new File(parentFile, "code-generator-demo-projects" + File.separator + "acm-templete").getAbsolutePath();
        System.out.println(inputPath);
        List<File> files = FileUtil.loopFiles(inputPath);
        for (File file : files) {
            System.out.println("file:" + file.getName());
        }


    }
}
