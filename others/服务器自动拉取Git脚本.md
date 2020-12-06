---
title: 服务器自动拉取git脚本
date: 2019-10-1
author: Firefly
top: false
cover: false
# coverImg: /images/red.jpg
toc: true
mathjax: false
categories: 经验
tags:
  - 脚本
---


# linux 自动运行脚本代码


## 背景

Blog每次提交到git的时候，都要上服务器拉取一下，而且每次执行相同的操作，这种机械的操作机器是最擅长的，于是乎，上网查了，git可以使用hooks，在push了代码后，自动先向务器发出post请求，然后让服务器更新代码，尝试了一下，php文件不是很会，于是另寻它径。

## 实现

既然php程序不会写，那就直接写sh， 然后让crontab定时器，每分钟更新一遍代码！！！

首先写一个sh文件

```
echo off
cd /usr/share/nginx/html/firefly/
echo 'opened'

git pull # 拉取代码

echo 'success'
```

然后使用定时器，每分钟执行一遍代码

```
>> crontab -e   # 进入编辑模式
输入：
 */1 * * *  /root/deploy.sh   # 这里是要执行的脚本
```


执行```cd /var/log/```查看执行的指令

```tail -n 3 /var/log/cron```这个可以看到一点输出

