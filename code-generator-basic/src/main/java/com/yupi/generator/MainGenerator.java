package com.yupi.generator;

import com.yupi.model.MainTemplateConfig;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class MainGenerator {

    public static void doGenerator(Object model) throws TemplateException, IOException {
        // 这里先生成静态文件
        // 这个是获取文件的根目录信息，这里就是一直到 D盘到code-generator
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
        File parentFile = new File(projectPath).getParentFile();
        //输入模板文件路径
        String inputPath = parentFile.getPath() + File.separator + "code-generator-demo-projects" + File.separator + "acm-templete";
        // 输入输出文件路径
        String outputPath = projectPath ;
        StaticGenerator.copyFilesByRecursive(inputPath,outputPath);

        // 然后在把动态文件替换进去
        // 经常性的在这里会出现路径拼接错误，那么就需要自己一点一点去调试
        String dynamicprojectPath = System.getProperty("user.dir") ;
        System.out.println(projectPath);
        String dynamicinputPath = dynamicprojectPath  + File.separator + "src/main/resources/templates/MainTemplate.java.ftl";
        String dynamicoutPutPath = dynamicprojectPath + File.separator + "acm-templete/src/yupi/acm/MainTemplate.java";

        DynamicGenerator.doGenerate(dynamicinputPath,dynamicoutPutPath,model);
    }

    public static void main(String[] args) throws TemplateException, IOException {
        // 创建数据模型
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("yupi");
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setOutputText("求和结果333：");

        doGenerator(mainTemplateConfig);
    }
}
