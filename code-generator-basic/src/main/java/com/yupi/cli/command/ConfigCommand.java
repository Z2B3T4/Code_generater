package com.yupi.cli.command;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReflectUtil;
import com.yupi.model.MainTemplateConfig;
import picocli.CommandLine;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

@CommandLine.Command(name = "config",description = "查看当前所有参数信息",mixinStandardHelpOptions = true)
public class ConfigCommand implements Runnable{

    @Override
    public void run() {
        Field[] fields = ReflectUtil.getFields(MainTemplateConfig.class);
        for (Field field : fields) {
            System.out.println("字段信息" + field.getName());
            System.out.println("字段类型" + field.getType());
            System.out.println("------");
        }
    }
}
