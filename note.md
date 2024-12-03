# 代码生成器的笔记
## 1.初始化项目
使用
```angular2html
git rm -rf --cached .
```
来清除git的暂存区状态

## 2.准备静态文件

使用 

```
File.sepearator 
```

来分割路径

```java
    public static void main(String[] args) {
        // 这个是获取文件的根目录信息，这里就是一直到 D盘到code-generator
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
         //输入模板文件路径
        String inputPath = projectPath + File.separator + "code-generator-demo-projects" + File.separator + "acm-templete";
        // 输入输出文件路径
        String outputPath = projectPath ;
        copyFilesByHutool(inputPath,outputPath, false);

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
```

**像这种文件目录拷贝的可以用来集成到大项目中作为显示树状结构，复制等**

## 3.FreeMarker

### 3.1 快速入门

准备模版文件

```html
<!DOCTYPE html>
<html>
<head>
    <title>鱼皮官网</title>
</head>
<body>
<h1>欢迎来到鱼皮官网</h1>
<ul>
    <#-- 循环渲染导航条 -->
    <#list menuItems as item>
        <li><a href="${item.url}">${item.label}</a></li>
    </#list>
</ul>
<#-- 底部版权信息（注释部分，不会被输出）-->
<footer>
    ${currentYear} 鱼皮官网. All rights reserved.
</footer>
</body>
</html>

```

引入依赖

```java
    <!-- https://freemarker.apache.org/index.html -->
    <dependency>
    <groupId>org.freemarker</groupId>
    <artifactId>freemarker</artifactId>
    <version>2.3.32</version>
    </dependency>
```

**如果是springboot引入这个**

```java
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>

```

指定静态资源位置

```java
import freemarker.template.Configuration;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class FreeMarkerTest {

    @Test
    public void test() throws IOException {
        // new 出 Configuration 对象，参数为 FreeMarker 版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        // 指定模板文件所在的路径,如果有多个模版文件，则可以添加多个configuration
        configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");

    }
}

```

然后指定对应的文件，并准备参数，指定输出路径

```java
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreeMarkerTest {

    @Test
    public void test() throws IOException, TemplateException {
        // new 出 Configuration 对象，参数为 FreeMarker 版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        // 指定模板文件所在的路径,如果有多个模版文件，则可以添加多个configuration
        configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");

        // 创建模板对象，加载指定模板
        Template template = configuration.getTemplate("myweb.html.ftl");
		// 准备参数
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("currentYear", 2023);

        List<Map<String, Object>> menuItems = new ArrayList<>();
        Map<String, Object> menuItem1 = new HashMap<>();

        menuItem1.put("url", "https://codefather.cn");
        menuItem1.put("label", "编程导航");

        Map<String, Object> menuItem2 = new HashMap<>();
        menuItem2.put("url", "https://laoyujianli.com");
        menuItem2.put("label", "老鱼简历");

        menuItems.add(menuItem1);
        menuItems.add(menuItem2);

        dataModel.put("menuItems", menuItems);

        // 这个就会在项目的根目录下生成这个html文件
        Writer out = new FileWriter("myweb.html");
        template.process(dataModel, out);

        // 生成文件后别忘了关闭哦
        out.close();


    }
}
```

执行这个，就会在根目录下出现这个文件

```html
<!DOCTYPE html>
<html>
<head>
    <title>鱼皮官网</title>
</head>
<body>
<h1>欢迎来到鱼皮官网</h1>
<ul>
            <li><a href="https://codefather.cn">编程导航</a></li>
        <li><a href="https://laoyujianli.com">老鱼简历</a></li>
</ul>
<footer>
    2,023 鱼皮官网. All rights reserved.
</footer>
</body>
</html>
```

### 3.2 基本语法/用法

```
表达式：${100 + money}
<#if user == "鱼皮">
  我是鱼皮
<#else>
  我是猪皮
</#if>
<#if user??>
  存在用户
<#else>
  用户不存在
</#if>
默认值
${user!"用户为空"}
循环
<#list users as user>
  ${user}
</#list>
宏定义
<#macro card userName>     
---------    
${userName}
---------
</#macro>

示例
<@card userName="鱼皮"/>
<@card userName="二黑"/>
结果
---------    
鱼皮
---------
---------    
二黑
---------


```

其他的参考官方文档

[FreeMarker 中文官方参考手册](http://freemarker.foofun.cn/toc.html)

### 3.2 动态文件生成

**其实最核心的就是准备一个配置类，作为之前的hashmap的替代品，然后想这个config文件中进行赋值，最后传入到抽象出来的工具类中进行生成，这里就是尤其注意，很多问题都是出现子啊文件的路径错误上，所以可以多多调试**

配置用户设置参数文件

```java
package com.yupi.model;

import lombok.Data;

/**
 * 动态模版配置
 */
@Data
public class MainTemplateConfig {

    /**
     * 是否生成循环
     */
    private boolean loop = true;

    /**
     * 作者注释，这里就是添加默认值
     */
    private String author = "zbt";

    /**
     * 输出信息
     */
    private String outputText = "zbt学编程";
}


```

动态生成文件的工具类

```java
/**
 * 生成文件,更加通用的抽象方法
 *
 * @param inputPath 模板文件输入路径
 * @param outputPath 输出路径
 * @param model 数据模型，就是配置类文件
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

    // 生成
    Writer out = new FileWriter(outputPath);
    template.process(model, out);

    // 生成文件后别忘了关闭哦
    out.close();
}
```

主函数调用

```java
public static void main(String[] args) throws IOException, TemplateException {
    // 经常性的在这里会出现路径拼接错误，那么就需要自己一点一点去调试
    String projectPath = System.getProperty("user.dir") + File.separator + "code-generator-basic";
    System.out.println(projectPath);
    String inputPath = projectPath + File.separator + "src/main/resources/templates/MainTemplate.java.ftl";
    String outPutPath = projectPath + File.separator + "MainTemplate.java";

    // 创建数据模型
    MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
    mainTemplateConfig.setAuthor("yupi");
    mainTemplateConfig.setLoop(false);
    mainTemplateConfig.setOutputText("求和结果：");

    doGenerate(inputPath,outPutPath,mainTemplateConfig);

}
```





## 4. Picoli

### 4.1 快速入门

