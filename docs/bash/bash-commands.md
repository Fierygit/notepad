---
title: bash-commands
date: 2020-12-29
categories: ["bash"] 
---

# alias

`alias`命令用于设置别名。通常用于在 Bash 设置文件中，设置别名。

```bash
alias dockerlogin='ssh www-data@adnan.local -p2222'
```
# awk

[`awk`](https://en.wikipedia.org/wiki/AWK)是处理文本文件的一个应用程序，几乎所有 Linux 系统都自带这个程序。

它依次处理文件的每一行，并读取里面的每一个字段。对于日志、CSV 那样的每行格式相同的文本文件，`awk`可能是最方便的工具。

![](https://www.wangbase.com/blogimg/asset/201811/bg2018110702.jpg)

`awk`其实不仅仅是工具软件，还是一种编程语言。不过，这里只介绍它的命令行用法，对于大多数场合，应该足够用了。

## 基本用法

`awk`的基本用法就是下面的形式。

```bash
# 格式
$ awk 动作 文件名

# 示例
$ awk '{print $0}' demo.txt
```

上面示例中，`demo.txt`是`awk`所要处理的文本文件。前面单引号内部有一个大括号，里面就是每一行的处理动作`print $0`。其中，`print`是打印命令，`$0`代表当前行，因此上面命令的执行结果，就是把每一行原样打印出来。

下面，我们先用标准输入（stdin）演示上面这个例子。

```bash
$ echo 'this is a test' | awk '{print $0}'
this is a test
```

上面代码中，`print $0`就是把标准输入`this is a test`，重新打印了一遍。

`awk`会根据空格和制表符，将每一行分成若干字段，依次用`$1`、`$2`、`$3`代表第一个字段、第二个字段、第三个字段等等。

```bash
$ echo 'this is a test' | awk '{print $3}'
a
```

上面代码中，`$3`代表`this is a test`的第三个字段`a`。

下面，为了便于举例，我们把`/etc/passwd`文件保存成`demo.txt`。

```bash
root:x:0:0:root:/root:/usr/bin/zsh
daemon:x:1:1:daemon:/usr/sbin:/usr/sbin/nologin
bin:x:2:2:bin:/bin:/usr/sbin/nologin
sys:x:3:3:sys:/dev:/usr/sbin/nologin
sync:x:4:65534:sync:/bin:/bin/sync
```

这个文件的字段分隔符是冒号（`:`），所以要用`-F`参数指定分隔符为冒号。然后，才能提取到它的第一个字段。

```bash
$ awk -F ':' '{ print $1 }' demo.txt
root
daemon
bin
sys
sync
```

## 变量

除了`$ + 数字`表示某个字段，`awk`还提供其他一些变量。

变量`NF`表示当前行有多少个字段，因此`$NF`就代表最后一个字段。

```bash
$ echo 'this is a test' | awk '{print $NF}'
test
```

`$(NF-1)`代表倒数第二个字段。

```
$ awk -F ':' '{print $1, $(NF-1)}' demo.txt
root /root
daemon /usr/sbin
bin /bin
sys /dev
sync /bin
```

上面代码中，`print`命令里面的逗号，表示输出的时候，两个部分之间使用空格分隔。

变量`NR`表示当前处理的是第几行。

```bash
$ awk -F ':' '{print NR ") " $1}' demo.txt
1) root
2) daemon
3) bin
4) sys
5) sync
```

上面代码中，`print`命令里面，如果原样输出字符，要放在双引号里面。

`awk`的其他内置变量如下。

> - `FILENAME`：当前文件名
> - `FS`：字段分隔符，默认是空格和制表符。
> - `RS`：行分隔符，用于分割每一行，默认是换行符。
> - `OFS`：输出字段的分隔符，用于打印时分隔字段，默认为空格。
> - `ORS`：输出记录的分隔符，用于打印时分隔记录，默认为换行符。
> - `OFMT`：数字输出的格式，默认为`％.6g`。

## 函数

 `awk`还提供了一些内置函数，方便对原始数据的处理。

函数`toupper()`用于将字符转为大写。

```bash
$ awk -F ':' '{ print toupper($1) }' demo.txt
ROOT
DAEMON
BIN
SYS
SYNC
```

上面代码中，第一个字段输出时都变成了大写。

其他常用函数如下。

> - `tolower()`：字符转为小写。
> - `length()`：返回字符串长度。
> - `substr()`：返回子字符串。
> - `sin()`：正弦。
> - `cos()`：余弦。
> - `sqrt()`：平方根。
> - `rand()`：随机数。

`awk`内置函数的完整列表，可以查看[手册](https://www.gnu.org/software/gawk/manual/html_node/Built_002din.html#Built_002din)。

## 条件

`awk`允许指定输出条件，只输出符合条件的行。

输出条件要写在动作的前面。

```bash
$ awk '条件 动作' 文件名
```

请看下面的例子。

```bash
$ awk -F ':' '/usr/ {print $1}' demo.txt
root
daemon
bin
sys
```

上面代码中，`print`命令前面是一个正则表达式，只输出包含`usr`的行。

下面的例子只输出奇数行，以及输出第三行以后的行。

```bash
# 输出奇数行
$ awk -F ':' 'NR % 2 == 1 {print $1}' demo.txt
root
bin
sync

# 输出第三行以后的行
$ awk -F ':' 'NR >3 {print $1}' demo.txt
sys
sync
```

下面的例子输出第一个字段等于指定值的行。

```bash
$ awk -F ':' '$1 == "root" {print $1}' demo.txt
root

$ awk -F ':' '$1 == "root" || $1 == "bin" {print $1}' demo.txt
root
bin
```

## if 语句

`awk`提供了`if`结构，用于编写复杂的条件。

```bash
$ awk -F ':' '{if ($1 > "m") print $1}' demo.txt
root
sys
sync
```

上面代码输出第一个字段的第一个字符大于`m`的行。

`if`结构还可以指定`else`部分。

```bash
$ awk -F ':' '{if ($1 > "m") print $1; else print "---"}' demo.txt
root
---
---
sys
sync
```

## 参考链接

- [An Awk tutorial by Example](https://gregable.com/2010/09/why-you-should-know-just-little-awk.html), Greg Grothaus
- [30 Examples for Awk Command in Text Processing](https://likegeeks.com/awk-command/), Mokhtar Ebrahim




# cal

`cal`命令显示本月的日历。

```bash
$ cal
```
# cat

`cat`命令用于显示一个文本文件的内容。

`cat - >> filename`用于向一个现有文件的尾部追加内容。
# clear

`clear`命令用来清除当前屏幕的显示，运行后会只留下一个提示符。

```bash
$ clear
```
# cp 命令

`cp`命令用于复制文件。

## 参数

`-u`参数只复制那些目标目录里面还不存在的文件，以及那些虽然存在、但是比源目录对应文件更陈旧的文件。
# cut

`cut`命令用于在命令行输出文本文件的指定位置的内容。

它的使用格式如下。

```bash
$ cut OPTION [FILE]
```

如果没有指定文件名，将读取标准输入。

`-b`参数用来指定读取的字节。

```bash
# 输出前三个字节
$ cut file1.txt -b1,2,3

# 输出前十个字节
$ cut file1.txt -b1-10

# 输出从第5个字节开始的所有字节
$ cut file1.txt -b5-

# 输出前5个字节
$ cut file1.txt -b-5
```

`-c`参数用来指定读取的字符，用法与`-b`一样。有的字符是多字节字符，这时候就应该用`-c`代替`-b`。

`-d`参数用来指定分隔符，默认分隔符为制表符。

`-f`参数用来指定字段。

```bash
# 指定每一行的分隔符为逗号，
# 输出第一和第三个字段
$ cut file1.txt -d, -f1,3

# 输出第一、第二、第四和第五个字段
$ cut -f 1-2,4-5 data.txt
```

# date

`date`命令显示当前的日期和时间。

```bash
$ date
```
# dd

`dd`命令用于复制磁盘或文件系统。

## 复制磁盘

```bash
$ dd if=/dev/sda of=/dev/sdb
```

上面命令表示将`/dev/sda`磁盘复制到`/dev/sdb`设备。参数`if`表示来源地，`of`表示目的地。

除了复制，`dd`还允许将磁盘做成一个镜像文件。

```bash
$ dd if=/dev/sda of=/home/username/sdadisk.img
```

`dd`还可以复制单个分区。

```bash
$ dd if=/dev/sda2 of=/home/username/partition2.img bs=4096
```

上面命令中，参数`bs`表示单次拷贝的字节数（bytes）。

要将镜像文件复原，也很简单。

```bash
$ dd if=sdadisk.img of=/dev/sdb
```

## 清除数据

`dd`也可以用于清除磁盘数据。

```bash
# 磁盘数据写满 0
$ dd if=/dev/zero of=/dev/sda1

# 磁盘数据写满随机字符
$ dd if=/dev/urandom of=/dev/sda1
```

## 监控进展

磁盘的复制通常需要很久，为了监控进展，可以使用 Pipe Viewer 工具软件。如果没有安装这个软件，可以使用下面的命令安装。

```bash
$ sudo apt install pv
```

然后，来源地和目的地之间插入广告，就可以看到进展了。

```bash
$ dd if=/dev/urandom | pv | dd of=/dev/sda1
4,14MB 0:00:05 [ 98kB/s] [      <=>                  ]
```

## 参考链接

- David Clinton, [How to use dd in Linux without destroying your disk](https://opensource.com/article/18/7/how-use-dd-linux)
# df

`df`命令显示磁盘信息。
# du

`du`命令显示某个文件或目录的磁盘使用量。

```bash
$ du filename
```

`-h`参数将返回的大小显示为人类可读的格式，即显示单位为 K、M、G 等。

`-s`参数表示总结（summarize）。

`-x`参数表示不显示不在当前分区的目录，通常会忽略`/dev`、`/proc`、`/sys`等目录。

`-c`参数表示显示当前目录总共占用的空间大小。

```bash
# 显示根目录下各级目录占用的空间大小
$ sudo du -shxc /*
```

`--exclude`参数用于排除某些目录或文件。

```bash
$ sudo du -shxc /* --exclude=proc
$ sudo du -sh --exclude=*.iso
```

`--max-depth`参数用于设定目录大小统计到第几层。如果设为`-–max-depth=0`，那么等同于`-s`参数。

```bash
$ sudo du /home/ -hc --max-depth=2
```
# egrep

`egrep`命令用于显示匹配正则模式的行，与`grep -E`命令等价。

下面是`example.txt`文件的内容。

```
Lorem ipsum
dolor sit amet, 
consetetur
sadipscing elitr,
sed diam nonumy
eirmod tempor
invidunt ut labore
et dolore magna
aliquyam erat, sed
diam voluptua. At
vero eos et
accusam et justo
duo dolores et ea
rebum. Stet clita
kasd gubergren,
no sea takimata
sanctus est Lorem
ipsum dolor sit
amet.
```

`egrep`命令显示包括`Lorem`或`dolor`的行。

```bash
$ egrep '(Lorem|dolor)' example.txt
# 或者
$ grep -E '(Lorem|dolor)' example.txt
Lorem ipsum
dolor sit amet,
et dolore magna
duo dolores et ea
sanctus est Lorem
ipsum dolor sit
```

# export

`export`命令用于向子Shell输出变量。

```bash
$ export hotellogs="/workspace/hotel-api/storage/logs"
```

然后执行下面的命令，新建一个子 Shell。

```bash
$ bash
$ cd hotellogs
```

上面命令的执行结果会进入`hotellogs`变量指向的目录。

`export`命令还可以显示所有环境变量。

```bash
$ export
SHELL=/bin/zsh
AWS_HOME=/Users/adnanadnan/.aws
LANG=en_US.UTF-8
LC_CTYPE=en_US.UTF-8
LESS=-R
```

如果想查看单个变量，使用`echo $VARIABLE_NAME`。

```bash
$ echo $SHELL
/usr/bin/zsh
```
# file

`file`命令用来查看某个文件的类型。

```bash
$ file index.html
 index.html: HTML document, ASCII text
```

file 工具可以对所给的文件输出一行简短的介绍，它用文件后缀、头部信息和一些其他的线索来判断文件。你在检查一堆你不熟悉的文件时使用 find 非常方便：

```bash
$ find -exec file {} \;
.:            directory
./hanoi:      Perl script, ASCII text executable
./.hanoi.swp: Vim swap file, version 7.3
./factorial:  Perl script, ASCII text executable
./bits.c:     C source, ASCII text
./bits:       ELF 32-bit LSB executable, Intel 80386, version ...
```
# find

`find`命令用于寻找文件，会包括当前目录的所有下级目录。

如果不带任何参数，`find`文件会列出当前目录的所有文件，甚至还包括相对路径。如果把结果导入 sort 效果更好。

```bash
$ find | sort
.
./Makefile
./README
./build
./client.c
./client.h
./common.h
./project.c
./server.c
./server.h
./tests
./tests/suite1.pl
./tests/suite2.pl
./tests/suite3.pl
./tests/suite4.pl
```

如果想要 ls -l 样式的列表，只要在 find 后面加上 -ls。

```bash
$ find -ls
```

find 有它自己的一套复杂的过滤语句。下面列举的是一些最常用的你可以用以获取某些文件列表的过滤器：

- find -name '*.c' —— 查找符合某 shell 式样式的文件名的文件。用 iname 开启大小写不敏感搜索。
- find -path '*test*' —— 查找符合某 shell 式样式的路径的文件。用 ipath 开启大小写不敏感搜索。
- find -mtime -5 —— 查找近五天内编辑过的文件。你也可以用 +5 来查找五天之前编辑过的文件。
- find -newer server.c —— 查找比 server.c 更新的文件。
- find -type d —— 查找所有文件夹。如果想找出所有文件，那就用 -type f；找符号连接就用 -type l。

要注意，上面提到的这些过滤器都是可以组合使用的，例如找出近两天内编辑过的 C 源码：

```bash
$ find -name '*.c' -mtime -2
```

默认情况下， find 对搜索结果所采取的动作只是简单地通过标准输出输出一个列表，然而其实还有其他一些有用的后续动作。

- -ls —— 如前文，提供了一种类 ls -l 式的列表。
- -delete —— 删除符合查找条件的文件。
- -exec —— 对搜索结果里的每个文件都运行某个命令， `{}`会被替换成适当的文件名，并且命令用`\;`终结。

```bash
$ find -name '*.pl' -exec perl -c {} \;
```

你也可以使用`+`作为终止符来对所有结果运行一次命令。我还发现一个我经常使用的小技巧，就是用 find 生成一个文件列表，然后在 Vim 的垂直分窗中编辑：

```bash
$ find -name '*.c' -exec vim {} +
```
# fmt

`fmt`命令用于对文本指定样式。

下面是`example.txt`的内容，是非常长的一行。

```
Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.
```

`fmt`可以将其输出为每行80个字符。

```bash
cat example.txt | fmt -w 20
Lorem ipsum
dolor sit amet,
consetetur
sadipscing elitr,
sed diam nonumy
eirmod tempor
invidunt ut labore
et dolore magna
aliquyam erat, sed
diam voluptua. At
vero eos et
accusam et justo
duo dolores et ea
rebum. Stet clita
kasd gubergren,
no sea takimata
sanctus est Lorem
ipsum dolor sit
amet.
```
# grep

`grep`命令用于文件内容的搜索，返回所有匹配的行。

```bash
$ grep pattern filename
```

下面是一个例子。

```bash
$ grep admin /etc/passwd
_kadmin_admin:*:218:-2:Kerberos Admin Service:/var/empty:/usr/bin/false
_kadmin_changepw:*:219:-2:Kerberos Change Password Service:/var/empty:/usr/bin/false
_krb_kadmin:*:231:-2:Open Directory Kerberos Admin Service:/var/empty:/usr/bin/false
```

一般情况下，应该使用`grep -R`，递归地找出当前目录下符合`someVar`的文件。

```bash
$ grep -FR 'someVar' .
```

別忘了大小不敏感的参数，因为 `grep` 默认搜索是大小写敏感的。

```bash
$ grep -iR 'somevar' .
```

也可以用`grep -l`光打印出符合条件的文件名而非文件内容选段。

```bash
$ grep -lR 'somevar' .
```

如果你写的脚本或批处理任务需要上面的输出内容，可以使用 `while` 和 `read` 来处理文件名中的空格和其他特殊字符：

```bash
grep -lR someVar | while IFS= read -r file; do
    head "$file"
done
```

如果你在你的项目里使用了版本控制软件，它通常会在 `.svn`， `.git`， `.hg` 目录下包含一些元数据。你也可以很容易地用 `grep -v` 把这些目录移出搜索范围，当然得用 `grep -F` 指定一个恰当且确定的字符串，即要移除的目录名：

```bash
$ grep -R 'someVar' . | grep -vF '.svn'
```

部分版本的 `grep` 包含了 `--exclude` 和 `--exclude-dir` 选项，这看起来更加易读。

## 参数

`-i`参数表示忽略大小写。

`-r`表示搜索某个目录下面的所有文件。

```bash
$ grep -r admin /etc/
```

`-v`过滤包含某个词的行，即`grep`的逆操作。

```bash
# 显示所有包含 vim，但不包含 grep 的行
$ ps | grep vim | grep -v grep
```
# gunzip

`gunzip`命令用于解压`gzip`命令压缩的文件。
# gzcat

`gzcat`命令用于查看一个`gz`文件，但并不实际解压它。

```bash
$ gzcat filename
```

# gzip

`gzip`命令用于压缩文件。
# killall

`killall`命令终止给定名字的一系列相关进程。

```bash
$ killall processname
```
# kill

`kill`命令用户终止指定进程。

```bash
$ kill PID
```
# last

`last`命令显示用户登录系统的记录。

```bash
$ last
```

`last`命令后面加上用户名，会显示该用户上次登录的信息。

```bash
$ last yourUsername
```

# lpq

`lpq`命令显示打印机队列。

```bash
$ lpq
Rank    Owner   Job     File(s)                         Total Size
active  adnanad 59      demo                            399360 bytes
1st     adnanad 60      (stdin)                         0 bytes
```
# lpr

`lpr`命令用于打印文件。

```bash
lpr filename
```

# ls

`ls`命令用于列出当前目录里面的文件和子目录。

## 参数

- a：列出隐藏文件
- l：以长格式列出文件
- t：按最后编辑日期排序，最新的最先。这在某个大目录里找出最近修改的文件列表时很有用，比如将结果导入（ pipe ） head 或者 sed 10q。或许加上 -l 会效果更好。当然如果你想获取最旧的文件列表，只要加 -r 反转列表即可。
- X：按文件类型分类。这在多语言或多后缀的项目中特别方便，比如头文件和源文件分开，或区分开源文件和生成文件或目录。
- v：按照文件名里的版本号排序。
- S：按文件大小排序。
- R：递归地列举文件。这个选项和 -l 组合使用并将结果导出到 less 效果很好。

可以把结果导出给类似 vim 的进程。

```bash
$ ls -XR | vim -
```
# nl

`nl`命令用于显示行号。

下面是`example.txt`文件的内容。

```bash
Lorem ipsum
dolor sit amet,
consetetur
sadipscing elitr,
sed diam nonumy
eirmod tempor
invidunt ut labore
et dolore magna
aliquyam erat, sed
diam voluptua. At
vero eos et
accusam et justo
duo dolores et ea
rebum. Stet clita
kasd gubergren,
no sea takimata
sanctus est Lorem
ipsum dolor sit
amet.
```

`nl`命令让上面这段文本显示行号。

```bash
$ nl -s". " example.txt
     1. Lorem ipsum
     2. dolor sit amet,
     3. consetetur
     4. sadipscing elitr,
     5. sed diam nonumy
     6. eirmod tempor
     7. invidunt ut labore
     8. et dolore magna
     9. aliquyam erat, sed
    10. diam voluptua. At
    11. vero eos et
    12. accusam et justo
    13. duo dolores et ea
    14. rebum. Stet clita
    15. kasd gubergren,
    16. no sea takimata
    17. sanctus est Lorem
    18. ipsum dolor sit
    19. amet.
```

`-s`参数表示行号的后缀。
# ps

`ps`命令列出当前正在执行的进程信息。

由于进程很多，所以为了快速找到某个进程，一般与`grep`配合使用。

```bash
# 找出正在运行 vim 的进程
$ ps | grep vi
```

## 参数

`-u`参数列出指定用户拥有的进程。

```bash
$ ps -u yourusername
```

# scp

## 基本用法

`scp`是 secure copy 的缩写，用来在两台主机之间加密传送文件。它的底层是 SSH 协议，默认端口是22。

它主要用于以下三种复制操作。

- 从本地系统到远程系统。
- 从远程系统到本地系统。
- 在本地系统的两个远程系统之间。

使用`scp`传输数据时，文件和密码都是加密的，不会泄漏敏感信息。

`scp`的语法类似`cp`的语法。

注意，如果传输的文件在本机和远程系统，有相同的名称和位置，`scp`会在没有警告的情况下覆盖文件。

**（1）本地文件复制到远程系统**

复制本机文件到远程系统的基本语法如下。

```bash
# 语法
$ scp SourceFile user@host:directory/TargetFile

# 示例
$ scp file.txt remote_username@10.10.0.2:/remote/directory
```

下面是复制整个目录。

```bash
# 将本机的 documents 目录拷贝到远程主机，
# 会在远程主机创建 documents 目录
$ scp -r documents username@server_ip:/path_to_remote_directory

# 将本机整个目录拷贝到远程目录下
$ scp -r localmachine/path_to_the_directory username@server_ip:/path_to_remote_directory/

# 将本机目录下的所有内容拷贝到远程目录下
$ scp -r localmachine/path_to_the_directory/* username@server_ip:/path_to_remote_directory/
```

**（2）远程文件复制到本地**

从远程主机复制文件到本地的语法如下。

```bash
# 语法
$ scp user@host:directory/SourceFile TargetFile

# 示例
$ scp remote_username@10.10.0.2:/remote/file.txt /local/directory
```

下面是复制整个目录的例子。

```bash
# 拷贝一个远程目录到本机目录下
$ scp -r username@server_ip:/path_to_remote_directory local-machine/path_to_the_directory/

# 拷贝远程目录下的所有内容，到本机目录下
$ scp -r username@server_ip:/path_to_remote_directory/* local-machine/path_to_the_directory/
$ scp -r user@host:directory/SourceFolder TargetFolder
```

**（3）两个远程系统之间的复制**

本机发出指令，从远程主机 A 拷贝到远程主机 B 的语法如下。

```bash
# 语法
$ scp user@host1:directory/SourceFile user@host2:directory/SourceFile

# 示例
$ scp user1@host1.com:/files/file.txt user2@host2.com:/files
```

系统将提示您输入两个远程帐户的密码。数据将直接从一个远程主机传输到另一个远程主机。

## 参数

`-P`用来指定远程主机的 SSH 端口。如果远程主机使用非默认端口22，可以在命令中指定。

```bash
$ scp -P 2222 user@host:directory/SourceFile TargetFile
```

`-p`参数用来保留修改时间（modification time）、访问时间（access time）、文件状态（mode）等原始文件的信息。

```bash
$ scp -C -p ~/test.txt root@192.168.1.3:/some/path/test.txt
```

`-l`参数用来限制传输数据的带宽速率，单位是 Kbit/sec。对于多人分享的带宽，这个参数可以留出一部分带宽供其他人使用。

```bash
$ scp -l 80 yourusername@yourserver:/home/yourusername/* .
```

上面代码中，`scp`命令占用的带宽限制为每秒80K比特位，即每秒10K字节。

`-c`参数用来指定加密算法。

```bash
$ scp -c blowfish some_file your_username@remotehost.edu:~
```

上面代码指定加密算法为`blowfish`。

`-C`表示是否在传输时压缩文件。

```bash
$ scp -c blowfish -C local_file your_username@remotehost.edu:~
```

`-q`参数用来关闭显示拷贝的进度条。

```bash
$ scp -q Label.pdf mrarianto@202.x.x.x:.
```

`-F`参数用来指定 ssh_config 文件。

```bash
$ scp -F /home/pungki/proxy_ssh_config Label.pdf
```

`-v`参数用来显示详细的输出。

```bash
$ scp -v ~/test.txt root@192.168.1.3:/root/help2356.txt
```

`-i`参数用来指定密钥。

```bash
$ scp -vCq -i private_key.pem ~/test.txt root@192.168.1.3:/some/path/test.txt
```

`-r`参数表示是否以递归方式复制目录。
# sed

`sed`命令用于对文本进行过滤和变形处理。

下面是`example.txt`文件的内容。

```bash
Hello This is a Test 1 2 3 4
replace all spaces with hyphens
```

`sed`命令将所有的空格换成连词线`-`。

```bash
$ sed 's/ /-/g' example.txt
Hello-This-is-a-Test-1-2-3-4
```

下面的命令将数字换成字母`d`。

```bash
$ sed 's/[0-9]/d/g' example.txt
Hello This is a Test d d d d
```

# sort

`sort`命令用于文本文件的排序。

下面是`example.txt`文件的内容。

```bash
f
b
c
g
a
e
d
```

执行`sort`命令对其进行排序。

```bash
$ sort example.txt
a
b
c
d
e
f
g
```

## 参数

`-R`参数表示随机排序。

```bash
sort -R example.txt
b
d
a
c
g
e
f
```

# tr

`tr`命令用于按照给定模式转换文本。

下面是`example.txt`文件的内容。

```bash
Hello World Foo Bar Baz!
```

`tr`命令可以将所有小写字母转换为大写字母。

```bash
$ cat example.txt | tr 'a-z' 'A-Z'
HELLO WORLD FOO BAR BAZ!
```

`tr`命令还可以将所有空格转为换行符。

```bash
$ cat example.txt | tr ' ' '\n'
Hello
World
Foo
Bar
Baz!
```

# uname

`uname`命令用来显示内核信息。

```bash
$ uname -a
```
# uniq

`uniq`用于过滤掉重复的行，该命令只对排序后的文件有效。

下面是`example.txt`文件的内容。

```bash
a
a
b
a
b
c
d
c
```

对该文件进行排序后，再过滤掉重复的行。

```bash
$ sort example.txt | uniq
a
b
c
d
```

## 参数

`-c`参数会显示每行一共出现了多少次。

```bash
sort example.txt | uniq -c
    3 a
    2 b
    2 c
    1 d
```

# uptime

`uptime`命令显示本次开机运行的时间。
# wc

`wc`命令返回某个文件的行数、词数和字符数。

```bash
$ wc demo.txt
7459   15915  398400 demo.txt
```

上面代码中，`7459`是行数，`15915`是词数，`398400`是字符数。

# whereis

`whereis`用来显示某个命令的位置。如果有多个程序符合条件，会全部列出。

```bash
$ whereis node
/usr/bin/node /usr/sbin/node
```

# which

`which`命令根据`PATH`环境变量指定的顺序，返回最早发现某个命令的位置。即不指定路径时，实际执行的命令的完整路径。

```bash
$ which node
/usr/bin/node
```
# who

`who`命令显示已经登录的用户。

## 参数

`-b`参数显示上一次系统启动的时间。

```bash
$ who -b
system boot  2017-06-20 17:41
```
# w

`w`命令显示当期谁在线。
