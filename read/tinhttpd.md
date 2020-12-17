---
title: tinhttpd
date: 2020-05-18
categories:  ["read"]
---

# tinhttpd 

https://www.cnblogs.com/nengm1988/p/7816618.html

不断创建线程接受连接， 并解析http，解析完了之后，创建多个进程处理，每个子进程处理然后发送给父进程， 这里把处理和回复解耦， 处理的子进程可以调用其它的服务器，然后接受数据通过管道发给父进程， 父进程再返回给浏览器

![image-20200426210029601](C:%5CUsers%5CFirefly%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20200426210029601.png)



