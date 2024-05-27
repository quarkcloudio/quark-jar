## 介绍
QuarkJar是一个基于SpringBoot的低代码工具；它提供的丰富组件，能帮助您使用很少的代码就能搭建出功能完善的应用系统。

## 系统特性

- 用户管理
- 权限系统
- 菜单管理
- 系统配置
- 操作日志
- 附件管理
- 组件丰富

## 快速开始
```java
package io.quarkcloud.quarkstarter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import io.quarkcloud.quarkcore.QuarkApplication;
import io.quarkcloud.quarkcore.autoconfigure.QuarkAdminApplication;

@SpringBootApplication(scanBasePackages = {"io.quarkcloud.quarkstarter","io.quarkcloud.quarkadmin"})
@QuarkAdminApplication(scanBasePackages = {"io.quarkcloud.quarkstarter.service.admin"})
@MapperScan(basePackages = {"io.quarkcloud.quarkstarter.mapper","io.quarkcloud.quarkadmin.mapper"})
@ComponentScan(basePackages = {"io.quarkcloud.quarkstarter","io.quarkcloud.quarkcore.service","io.quarkcloud.quarkadmin"})
public class QuarkStarterApplication {

    public static void main(String[] args) {

        // Quark服务
        QuarkApplication.run(QuarkStarterApplication.class, args);

        // SpringBoot服务
        SpringApplication.run(QuarkStarterApplication.class, args);
    }
}
```

后台地址： ```http://127.0.0.1:3000/admin/```

账号：```administrator```
密码：```123456```

## 技术支持
为了避免打扰作者日常工作，你可以在Github上提交 [Issues](https://github.com/quarkcloudio/quark-jar/issues)

相关教程，你可以查看 [在线文档](http://quarkcloud.io/quark-jar/)

## License
QuarkJar is licensed under The MIT License (MIT).