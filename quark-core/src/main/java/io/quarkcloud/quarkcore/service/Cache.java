package io.quarkcloud.quarkcore.service;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;

public class Cache {
    
    // 缓存时间，默认30秒
    private static long timeout = 30 * 1000;

    // 启动定时任务，每5秒清理一次过期条目
    private static long delay = 5 * 1000;

    // 私有构造方法
    private Cache(){}
    
    // 在静态代码块中进行创建
    private static TimedCache<Object, Object> instance;
    static {
        instance = CacheUtil.newTimedCache(timeout);
        instance.schedulePrune(delay);
    }

    // 对外提供静态方法获取该对象
    public static TimedCache<Object, Object> getInstance(){
        return instance;
    }
}
