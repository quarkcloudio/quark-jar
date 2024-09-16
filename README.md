## 介绍
QuarkJar是一个基于SpringBoot的低代码工具；它提供的丰富组件，能帮助您使用很少的代码就能搭建出功能完善的应用系统。

## 题外话
我知道在Java界，有着“若依”这种优秀的框架，而且用户也比较多；写QuarkJar的初衷并不是想取代谁，而是希望为开发者提供更开阔的思路、更多的选择。

## 系统特性

- 用户管理
- 权限系统
- 菜单管理
- 系统配置
- 操作日志
- 附件管理
- 组件丰富

## 快速开始

1. 进入项目目录中执行如下命令，打包项目：
``` bash
./mvnw.cmd clean package  -DskipTests
```

2. 执行如下命令，启动项目：
``` bash
java -jar quark-starter/target/quark-starter-1.0.0.jar
```
后台地址： ```http://127.0.0.1:8080/admin/```

账号：```administrator```
密码：```123456```

## 技术支持
为了避免打扰作者日常工作，你可以在Github上提交 [Issues](https://github.com/quarkcloudio/quark-jar/issues)

相关教程，你可以查看 [在线文档](http://quarkcloud.io/quark-jar/)

## License
QuarkJar is licensed under The MIT License (MIT).