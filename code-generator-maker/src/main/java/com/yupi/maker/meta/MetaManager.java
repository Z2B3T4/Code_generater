package com.yupi.maker.meta;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;

public class MetaManager {

    // 这里着重说一下这个 volatile 关键字，这个就是在并发编程中，保证内存可见性
    // 当一个线程修改了这个变量的值，其他线程也能立即看到这个修改
    private static volatile Meta meta;

    public static Meta getMetaObject(){
        /**
         * 这里用到了双检索单例模式，也就是说，双检索着的是检查两边 if(meta == null )
         * 单例是指在整个程序执行的过程中保证初始化这种代码值执行一次
         * 双检索：第一个检索是为了防止初始化之后，其他线程再来还要进行抢锁浪费性能，
         *          第二个检索是为了防止多个线程同时抢到锁，然后进行初始化
         */
        if(meta == null ){
            synchronized (MetaManager.class){
                if(meta == null){
                    meta = initMeta();
                }
            }
        }
        return meta;
    }

    private static Meta initMeta(){
        String metaJson = ResourceUtil.readUtf8Str("meta.json");
        Meta newMeta = JSONUtil.toBean(metaJson, Meta.class);
        return newMeta;
    }

}
