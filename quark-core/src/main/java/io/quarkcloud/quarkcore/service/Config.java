package io.quarkcloud.quarkcore.service;

import java.util.HashMap;
import java.util.Map;

public class Config {

    //私有构造方法
    private Config(){}
    
    //定义basePackages
    private Map<String,String[]> basePackages = new HashMap<String, String[]>();

    //在静态代码块中进行创建
    private static Config instance;
    static {
        instance = new Config();
    }
    
    //对外提供静态方法获取该对象
    public static Config getInstance(){
        return instance;
    }

    //提供获取basePackages的方法
    public String[] getBasePackages(String packageKey) {
        return basePackages.get(packageKey);
    }

    //提供设置basePackages的方法
    public void setBasePackages(String packageKey,String[] basePackages) {
        this.basePackages.put(packageKey, basePackages);
    }
}
