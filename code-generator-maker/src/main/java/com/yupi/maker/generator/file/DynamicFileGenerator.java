package com.yupi.maker.generator.file;

import cn.hutool.core.io.FileUtil;
import com.yupi.maker.model.DataModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * 动态文件生成器
 */
public class DynamicFileGenerator {
    public static void main(String[] args) throws IOException, TemplateException {
        // 经常性的在这里会出现路径拼接错误，那么就需要自己一点一点去调试
        String projectPath = System.getProperty("user.dir") + File.separator + "code-generator-maker";
        System.out.println(projectPath);
        String inputPath = projectPath + File.separator + "src/main/resources/templates/MainTemplate.java.ftl";
        String outPutPath = projectPath + File.separator + "MainTemplate.java";

        // 创建数据模型
        DataModel dataModel = new DataModel();
        dataModel.setAuthor("yupi");
        dataModel.setLoop(false);
        dataModel.setOutputText("求和结果：");

        doGenerate(inputPath,outPutPath, dataModel);

    }


    /**
     * 生成文件,更加通用的抽象方法
     *
     * @param inputPath 模板文件输入路径
     * @param outputPath 输出路径
     * @param model 数据模型
     * @throws IOException
     * @throws TemplateException
     */
    public static void doGenerate(String inputPath, String outputPath, Object model) throws IOException, TemplateException {
        // new 出 Configuration 对象，参数为 FreeMarker 版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        // 指定模板文件所在的路径
        File templateDir = new File(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateDir);

        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");
        // 这个就是让生成数字中间不要带逗号
        configuration.setNumberFormat("0.######");
        // 创建模板对象，加载指定模板
        String templateName = new File(inputPath).getName();
        Template template = configuration.getTemplate(templateName);

        // 为了防止文件目录不存在报错
        if(!FileUtil.exist(outputPath)){
            FileUtil.touch(outputPath);
        }
        // 生成
        Writer out = new FileWriter(outputPath);
        template.process(model, out);

        // 生成文件后别忘了关闭哦
        out.close();
    }

}
