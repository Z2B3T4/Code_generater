package com.yupi.cli.command;

import cn.hutool.core.bean.BeanUtil;
import com.yupi.generator.MainGenerator;
import com.yupi.model.MainTemplateConfig;
import lombok.Data;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@Data
@CommandLine.Command(name = "generate", version = "generate 1.0", mixinStandardHelpOptions = true)
public class GenerateCommand implements Callable<Integer> {

    /**
     * 是否生成循环
     */
    @CommandLine.Option(names = {"-l", "--loop"},
            description = "循环",
            interactive = true,
            arity = "0..1",
            echo = true
    )
    private boolean loop = true;

    /**
     * 作者注释，这里就是添加默认值
     */
    @CommandLine.Option(names = {"-a", "--author"},
            description = "作者",
            interactive = true,
            arity = "0..1",
            echo = true
    )
    private String author = "zbt";

    /**
     * 输出信息
     */
    @CommandLine.Option(names = {"-t", "--text"},
            description = "输入文本",
            interactive = true,
            arity = "0..1",
            echo = true
    )
    private String outputText = "zbt学编程";

    @Override
    public Integer call() throws Exception {
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        // 将用户输入的参数保存到 mainTemplateConfig 对象中
        BeanUtil.copyProperties(this, mainTemplateConfig);
        // 调用方法生成模板
        MainGenerator.doGenerator(mainTemplateConfig);
        return 0;
    }
}
