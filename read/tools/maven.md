---
title: maven
date: 2018-02-28
categories: 
- 工具
tag: maven
---
# maven



以下的命令会按顺序执行，后面命令会调用前面的命令



```
mvn clean
```

把编译好的类清除掉， 因为每个开发环境不同

```
mvn compile
```

编译主要的类

```
mvn test
```

编译测试的类

```
mvn package
```

把工程打包成jar 包

编译测试类

```
mvn install
```

执行上面的所有命令， 并且放入本地仓库中



maven工程可以直接使用：

```
tomcat:run 
```

直接使用tomcat 运行项目



windows安装：

首先， 下载可执行文件：[http://maven.apache.org/download.cgi](http://maven.apache.org/download.cgi)

![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200209203336.png)



 下载后解压记住路径， 然后添加系统变量和环境变量。

![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200209203517.png)

![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200209203615.png)

最后测试：

```
mvn -version
```

![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200209203655.png)





**本地仓库**:  使用时先在本地找依赖， 如果没有就去网络找！网络找到后会放在这里

默认在： users / {username}  / .m2

![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200209204252.png)



在目录下更改 **本地仓库** 的位置: `{M2_HOME}\conf\setting.xml`, 搜索 LocalResposity，把注释取消掉

![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200209204658.png)



maven 在本地的找不到依赖就去网络找。

- 首先,去自定义的远程仓库找,  在 pom.xml 定义自己公司的仓库：

```properties
<mirrors>
    <mirror>
        <id>nexus-aliyun</id>
        <mirrorOf>*</mirrorOf>
        <name>Nexus aliyun</name>
        <url> ｛url｝</url>
    </mirror> 
  </mirrors>
```

- 然后，自定义的远程仓库没有，最后maven 会在中心远程仓库找，仓库在国外， 更换阿里的！`.\conf\setting.xml`

```properties
<mirrors>
    <mirror>
        <id>nexus-aliyun</id>
        <mirrorOf>*</mirrorOf>
        <name>Nexus aliyun</name>
        <url>http://maven.aliyun.com/nexus/content/groups/public</url>
    </mirror> 
  </mirrors>
```





