package com.yupi.maker.generator.file;


import cn.hutool.core.io.FileUtil;

import java.io.File;

//静态文件生成器
public class StaticFileGenerator {

    public static void main(String[] args) {
        // 这个是获取文件的根目录信息，这里就是一直到 D盘到code-generator
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
         //输入模板文件路径
        String inputPath = projectPath + File.separator + "code-generator-demo-projects" + File.separator + "acm-templete";
        // 输入输出文件路径
        String outputPath = projectPath ;
        copyFilesByHutool(inputPath,outputPath,false);

    }

    /**
     * 这个是估值文件目录的一个工具类，就是把文件或者文件夹从一个目录复制到另一个目录
     * @param inputPath  被拷贝的源文件目录
     * @param outPutPath  拷贝到的文件目录
     * @param isCover 是否覆盖
     */
    public static void copyFilesByHutool(String inputPath, String outPutPath, boolean isCover){

        FileUtil.copy(inputPath,outPutPath,isCover);
    }




}
