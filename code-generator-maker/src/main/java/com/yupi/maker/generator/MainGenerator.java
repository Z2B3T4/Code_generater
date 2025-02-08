package com.yupi.maker.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.yupi.maker.generator.file.DynamicFileGenerator;
import com.yupi.maker.meta.Meta;
import com.yupi.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

// 这里面写入主要的根据传入的信息和模版代码生成代码
public class MainGenerator {
    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        // 读取配置文件
        Meta meta = MetaManager.getMetaObject();
        //System.out.println(meta);

        // 首先将代码生成到 src/main/java/generate 文件夹中
        String projectPath = System.getProperty("user.dir");
        // 这个就截止到自定义的报的名称的那个文件夹的名字
        String outputPath = projectPath + File.separator + "generated" + File.separator + meta.getName();
        if(!FileUtil.exist(outputPath)){
            FileUtil.mkdir(outputPath);
        }

        // 解析json中的basePackage，因为那个是根据 . 分隔的，所以要变为 / 分隔
        // 这个是确定 com.yupi 这个用户输入什么要替换为什么
        String outputBasePackage = meta.getBasePackage();
        String outputBasePackagePath = StrUtil.join("/", StrUtil.split(outputBasePackage, "."));
        String outputBasePackageJavaPath = outputPath + File.separator + "src/main/java/" + outputBasePackagePath;

        // 找到resource中的模版文件作为动态生成代码的输入路径
        ClassPathResource classPathResource = new ClassPathResource("");
        String inputResourcePath = classPathResource.getAbsolutePath();
        // 输出到文件的就是刚才创建的文件夹的里面的model的包下
        String inputFilePath;
        String outputFilePath;

        inputFilePath = inputResourcePath + File.separator + "templates/java/model/DataModel.java.ftl";
        outputFilePath = outputBasePackageJavaPath + File.separator + "model/DataModel.java";

        DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        // cli.command.ConfigCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/ConfigCommand.java.ftl";
        outputFilePath = outputBasePackageJavaPath + "/cli/command/ConfigCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // cli.command.GenerateCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/GenerateCommand.java.ftl";
        outputFilePath = outputBasePackageJavaPath + "/cli/command/GenerateCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // cli.command.ListCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/ListCommand.java.ftl";
        outputFilePath = outputBasePackageJavaPath + "/cli/command/ListCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // cli.CommandExecutor
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/CommandExecutor.java.ftl";
        outputFilePath = outputBasePackageJavaPath + "/cli/CommandExecutor.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // Main
        inputFilePath = inputResourcePath + File.separator + "templates/java/Main.java.ftl";
        outputFilePath = outputBasePackageJavaPath + "/Main.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // generator.DynamicGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/DynamicGenerator.java.ftl";
        outputFilePath = outputBasePackageJavaPath + "/generator/DynamicGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // generator.MainGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/MainGenerator.java.ftl";
        outputFilePath = outputBasePackageJavaPath + "/generator/MainGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // generator.StaticGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/StaticGenerator.java.ftl";
        outputFilePath = outputBasePackageJavaPath + "/generator/StaticGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // pom.xml
        inputFilePath = inputResourcePath + File.separator + "templates/pom.xml.ftl";
        outputFilePath = outputPath + File.separator + "pom.xml";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // 构建 jar 包
        JarGenerator.doGenerate(outputPath);

    }
}
