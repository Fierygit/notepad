---
title: windows的脚本命令一件部署后端服务
date: 2019-9-11
author: Firefly
cover: true
top: false
toc: true
categories:
- 经验
tags:
  - 脚本
---

#### windows的脚本命令一件部署后端服务


 实习一个月了，其中有许多有意思的东西希望能记住一些小技巧，今天想记录的是，花了一个下午才研究通透的一个windows脚本命令。

 ## 一，BAT命令简要说明
  这个是windows的批处理文件，在这里可以这接写dos命令，一些恶意小程序就是可以在这里写，和linux的sh不同是，不用给文件赋予权限，bat只需要，把后缀改为bat就行了，其实windwos部署服务很少，习惯了linux，突然用dos感觉好不习惯。
 - 首先是注释  ：：  两个冒号代表注释一行，为了防止写着脚本自己写到哪里了都不知道，注释还是有作用的。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190808105234421.png)
-     goto  :next
      `echo 'pull sucess!'
      `echo 'pull sucess!'
      :next
            
		上面的goto语句是跳过中间的两个命令， 简洁明了

- echo off
	如果不想要每次都显示路径这个就可以只显示执行的情况。所以一般开头加一句

-	call  
		这个命令值得注意下，如果在bat命令中调用了其它的bat，其它bat里面运行完一般会有退出命令，所以，如果不想在调用了其它命令后退出，就call 这个命令。比如说mvn命令，这个调用了之后运行完会直接退出，所以加个call



## 二，流程
	

 1.  ::先随便到一个目录，把该目录下的jcSrc  保存
cd  E:\jc
::Q表示不显示文件名C错误也复制E所有子目录Y不提示覆盖
xcopy  E:\jc\jcSrc  E:\jc\jcSrcHistory/E/C/Y/Q
解释：此命令只是把先前的src复制一遍存起来
 2. cd jcSrc
git pull origin master
echo 'pull sucess!'
解释：上面的三条脚本在bash测试成功后放到这里，Git拉取src，git还要配置环境变量
 3. ::mvn打包
cd   E:\jc\jcSrc\common
call mvn clean compile
call mvn clean package
echo 'mvn package success'
这个也要配置环境变量
4. ::删除原来的包,注意改名jar
cd  E:\jc\jcJar
rd/s/q    common-0.0.1-SNAPSHOT.jar
echo ‘delete success！’
写到这里想起差点把我的一个大文件给删了。 注意注意注意

5. ::将打包好的jar包复制到jcJar中
xcopy  E:\jc\jcSrc\common\target\common-0.0.1-SNAPSHOT.jar  E:\jc\jcJar/E/C/Y/Q
echo 'copy jar success'

6. set port=8092
for /f "tokens=1-5" %%i in ('netstat -ano^|findstr ":%port%"') do taskkill /F /pid %%m
 echo 'delete port success'
 解释：这一步很重要，先要删除端口，很高兴在网上找到了删除端口对应程序的代码，在2008里面既然不能用，后来发现，是这个脚本命令不能换行。不然换行写多舒服！
 
7. ::运行
cd  E:\jc\jcJar
java -jar common-0.0.1-SNAPSHOT.jar --server.port=8092
echo '如果你在运行的时候看到我,就说明启动失败啦!!!,上面的某一条命令没有执行成功'
echo 'success!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!'
pause
解释：
			1.因为java -jar  运行后端服务是持续的，所以后面的echo是要等到java运行完才打印的，如果没有启动成功，才会打印，=
			2.还是因为这是2008的上古神机windows版本，所以用了java命令，如果是window10，可以直接用javaw， 命令，这个命令可以让jar在后台执行。

最后来一个全的

    ::先随便到一个目录，把该目录下的jcSrc  保存
    cd  E:\jc
    ::Q表示不显示文件名C错误也复制E所有子目录Y不提示覆盖
    xcopy  E:\jc\jcSrc  E:\jc\jcSrcHistory/E/C/Y/Q
    
    echo 'copy sucess!'
    
    cd jcSrc
    git pull origin master
    echo 'pull sucess!'
    
    
    ::mvn 打包
    cd   E:\jc\jcSrc\common
    call mvn clean compile
    call mvn clean package
    echo 'mvn package success'
    
    
    ::删除原来的包,注意改名jar
    cd  E:\jc\jcJar
    rd/s/q    common-0.0.1-SNAPSHOT.jar
    echo ‘delete success！’
    
    ::将打包好的jar包复制到jcJar中
    
    xcopy  E:\jc\jcSrc\common\target\common-0.0.1-SNAPSHOT.jar  E:\jc\jcJar/E/C/Y/Q
    echo 'copy jar success'
    
    
    set port=8092
    for /f "tokens=1-5" %%i in ('netstat -ano^|findstr ":%port%"') do taskkill /F /pid %%m
      
    echo 'delete port success'
    
    ::运行
    cd  E:\jc\jcJar
    java -jar common-0.0.1-SNAPSHOT.jar --server.port=8092
    echo '如果你在运行的时候看到我,就说明启动失败啦!!!,上面的某一条命令没有执行成功'
    echo 'success!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!'
    
    pause

