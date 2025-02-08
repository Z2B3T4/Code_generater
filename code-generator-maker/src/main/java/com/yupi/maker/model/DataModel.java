package com.yupi.maker.model;

import lombok.Data;

/**
 * 动态模版配置
 */
@Data
public class DataModel {

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

