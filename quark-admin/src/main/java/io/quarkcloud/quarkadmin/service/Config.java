package io.quarkcloud.quarkadmin.service;

public class Config {

    //私有构造方法
    private Config(){}
    
    //定义basePackages
    private String[] basePackages = {"io.quarkcloud.quarkstarter.service"};

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
    public String[] getBasePackages() {
        return basePackages;
    }

    //提供设置basePackages的方法
    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }
}
