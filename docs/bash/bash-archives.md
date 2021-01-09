---
title: bash-archives
date: 2020-12-29
categories: ["bash"] 
---




# 归档和备份

## gzip

gzip 程序用来压缩文件，原文件的压缩版（添加`gz`后缀名）会替代原文件。gunzip 程序用来还原压缩版本。

```bash
$ gzip foo.txt
$ gunzip foo.txt.gz
```

`gzip`的参数如下。

- -c	把输出写入到标准输出，并且保留原始文件。也有可能用--stdout 和--to-stdout 选项来指定。
- -d	解压缩。正如 gunzip 命令一样。也可以用--decompress 或者--uncompress 选项来指定.
- -f	强制压缩，即使原始文件的压缩文件已经存在了，也要执行。也可以用--force 选项来指定。
- -h	显示用法信息。也可用--help 选项来指定。
- -l	列出每个被压缩文件的压缩数据。也可用--list 选项。
- -r	若命令的一个或多个参数是目录，则递归地压缩目录中的文件。也可用--recursive 选项来指定。
- -t	测试压缩文件的完整性。也可用--test 选项来指定。
- -v	显示压缩过程中的信息。也可用--verbose 选项来指定。
- -number	设置压缩指数。number 是一个在1（最快，最小压缩）到9（最慢，最大压缩）之间的整数。 数值1和9也可以各自用--fast 和--best 选项来表示。默认值是整数6。

下面是一些例子。

```bash
# 查看解压缩后的内容
$ gunzip -c foo.txt | less
```

`zcat`程序等同于带有-c 选项的 gunzip 命令。它可以像`cat`命令那样，用来查看`gzip`压缩文件。

```bash
$ zcat foo.txt.gz | less
```

## bzip2

`bzip2`程序与`gzip`程序相似，但是使用了不同的压缩算法，舍弃了压缩速度，实现了更高的压缩级别。在大多数情况下，它的工作模式等同于`gzip`。 由`bzip2`压缩的文件，用扩展名`.bz2`表示。

```bash
$ bzip2 foo.txt
$ bunzip2 foo.txt.bz2
```

gzip程序的所有选项（除了`-r`），bzip2 程序同样也支持。同样有 bunzip2 和 bzcat 程序来解压缩文件。bzip2 文件也带有 bzip2recover 程序，其会 试图恢复受损的 .bz2 文件。

## zip

`zip`程序既是压缩工具，也是一个打包工具，读取和写入.zip文件。

```bash
$ zip options zipfile file...
```

它的用法如下。

```bash
# 将指定目录压缩成zip文件
$ zip -r playground.zip playground
```

`zip`与`tar`命令有一个相反之处。如果压缩文件已存在，其将被更新而不是被替代。这意味着会保留此文件包，但是会添加新文件，同时替换匹配的文件。

解压使用`unzip`命令。

```bash
$ unzip ../playground.zip
```

`unzip`命令的参数如下。

- `-l` 列出文件包中的内容而不解压
- `-v` 显示冗余信息
- `-p` 输出发送到标准输出

```bash
$ unzip -p ls-etc.zip | less
```

## tar

`tar`是tape archive的简称，原来是一款制作磁带备份的工具，现在主要用于打包。一个 tar 包可以由一组独立的文件，一个或者多个目录，或者两者混合体组成。

`tar`程序的语法如下。

```bash
$ tar mode[options] pathname...
```

tar支持以下模式。

- c 表示create，为文件和／或目录列表创建归档文件。
- x 抽取归档文件。
- r 追加具体的路径到归档文件的末尾。
- t 列出归档文件的内容。

支持的参数如下。

- f 表示file，用来指定生成的文件。

模式和参数可以写在一起，而且不需要开头的短横线。注意，必须首先指定模式，然后才是其它的选项。

```bash
# 创建子目录的tar包
$ tar cf playground.tar playground

# 查看tar包内容
$ tar tf playground.tar

# 查看更详细的列表信息
$ tar tvf playground.tar

# 还原归档文件
$ tar xf playground.tar

# 还原单个文件
$ tar xf archive.tar pathname

# 还原文件到指定目录
$ tar xvf archive.tar -C /home/me/

# 追加文件
$ tar rf archive.tar file.txt

# 验证归档文件内容是否正确
$ tar tvfW archive.tar

# 支持通配符
$ tar xf ../playground2.tar --wildcards 'home/me/playground/\*.txt'
```

注意，`tar`命令还原的时候，总是还原为相对路径。如果归档的时候，保存的是绝对路径，那么还原的时候，这个绝对路径会整个变成相对路径。

`find`命令可以与`tar`命令配合使用。

```bash
$ find playground -name 'file.txt' -exec tar rf playground.tar '{}' '+'
```

上面的命令先用`find`程序找到所有名为`file.txt`的文件，然后使用追加模式（`r`）的`tar`命令，把匹配的文件添加到归档文件`playground.tar`里面。

这种`tar`和`find`的配合使用，可以创建逐渐增加的目录树或者整个系统的备份。通过`find`命令匹配新于某个时间戳的文件，我们就能够创建一个归档文件，其只包含新于上一个 tar 包的文件。

tar支持压缩功能。

```bash
# 打成gzip压缩包
$ tar czvf assets.tar.gz dist

# 打成bz2压缩包
$ tar cvfj assets.tar.bz2 dist

# 解压 tar.gz 文件
$ tar xzv archive.tar.gz
$ tar xvf archive.tar.gz

# 解压bz2压缩包
$ tar xvf archive.tar.bz2

# 显示gzip压缩包内容
$ tar tvf archive.tar.gz

# 显示bz2压缩包内容
$ tar tvf archive.tar.bz2

# 从gzip压缩包取出单个文件
$ tar zxvf archive.tar.gz file.txt

# 从bz2压缩包取出单个文件
$ tar jxvf archive.tar.bz2 file.txt

# 按通配符取出文件
$ tar zxvf archive.tar.gz --wildcards '*.php'
$ tar jxvf archive.tar.bz2 --wildcards '*.php'

# 追加文件到压缩包
$ tar rvf archive.tar.gz xyz.txt
$ tar rvf archive.tar.bz2 xyz.txt
```

## rsync

`rsync`命令用于在多个目录之间、或者本地与远程目录之间同步。字母`r`表示`remote`。

```bash
$ rsync options source destination
```

source 和 destination 是下列选项之一：

- 一个本地文件或目录
- 一个远端文件或目录，以`[user@]host:path`的形式存在
- 一个远端 rsync 服务器，由`rsync://[user@]host[:port]/path`指定

注意 source 和 destination 两者之一必须是本地文件。rsync 不支持远端到远端的复制。

`rsync`命令的参数如下。

- `-a` 递归和保护文件属性
- `-v` 冗余输出
- `--delete` 删除可能在备份设备中已经存在但却不再存在于源设备中的文件
- `--rsh=ssh` 使用 ssh 程序作为远程 shell，目的地必须标注主机名。

```bash
# 同步两个本地目录
$ rsync -av playground foo

# 删除源设备不存在的文件
$ sudo rsync -av --delete /etc /home /usr/local /media/BigDisk/backup

# 远程同步
$ sudo rsync -av --delete --rsh=ssh /etc /home /usr/local remote-sys:/backup

# 与远程rsync主机同步
$ rsync -av -delete rsync://rsync.gtlib.gatech.edu/path/to/oss fedora-devel
```
# 异步任务

Bash脚本有时候需要同时执行多个任务。通常这涉及到启动一个脚本，依次，启动一个或多个子脚本来执行额外的任务，而父脚本继续运行。然而，当一系列脚本 以这种方式运行时，要保持父子脚本之间协调工作，会有一些问题。也就是说，若父脚本或子脚本依赖于另一方，并且 一个脚本必须等待另一个脚本结束任务之后，才能完成它自己的任务，这应该怎么办？

bash 有一个内置命令，能帮助管理诸如此类的异步执行的任务。wait 命令导致一个父脚本暂停运行，直到一个 特定的进程（例如，子脚本）运行结束。

首先我们将演示一下 wait 命令的用法。为此，我们需要两个脚本，一个父脚本：

```bash
#!/bin/bash
# async-parent : Asynchronous execution demo (parent)
echo "Parent: starting..."
echo "Parent: launching child script..."
async-child &
pid=$!
echo "Parent: child (PID= $pid) launched."
echo "Parent: continuing..."
sleep 2
echo "Parent: pausing to wait for child to finish..."
wait $pid
echo "Parent: child is finished. Continuing..."
echo "Parent: parent is done. Exiting."
```

和一个子脚本：

```bash
#!/bin/bash
# async-child : Asynchronous execution demo (child)
echo "Child: child is running..."
sleep 5
echo "Child: child is done. Exiting."
```

在这个例子中，我们看到该子脚本是非常简单的。真正的操作通过父脚本完成。在父脚本中，子脚本被启动， 并被放置到后台运行。子脚本的进程 ID 记录在 pid 变量中，这个变量的值是 $! shell 参数的值，它总是 包含放到后台执行的最后一个任务的进程 ID 号。

父脚本继续，然后执行一个以子进程 PID 为参数的 wait 命令。这就导致父脚本暂停运行，直到子脚本退出， 意味着父脚本结束。

当执行后，父子脚本产生如下输出：

```bash
$ async-parent
Parent: starting...
Parent: launching child script...
Parent: child (PID= 6741) launched.
Parent: continuing...
Child: child is running...
Parent: pausing to wait for child to finish...
Child: child is done. Exiting.
Parent: child is finished. Continuing...
Parent: parent is done. Exiting.
```
# Shell 的命令

## 命令的类别

Bash可以使用的命令分成四类。

- 可执行程序
- Shell 提供的命令
- Shell 函数
- 前三类命令的别名

## type, whatis

`type`命令可以显示命令类型。

```bash
$ type command
```

下面是几个例子。

```bash
$ type type
type is a shell builtin

$ type ls
ls is aliased to `ls --color=tty'

$ type cp
cp is /bin/cp
```

`whatis`命令显示指定命令的描述。

```bash
$ whatis ls
ls (1) - list directory contents
```

## apropos

`apropos`命令返回符合搜索条件的命令列表。

```bash
$ apropos floppy
create_floppy_devices (8) - udev callout to create all possible
fdformat (8) - Low-level formats a floppy disk
floppy (8) - format floppy disks
gfloppy (1) - a simple floppy formatter for the GNOME
mbadblocks (1) - tests a floppy disk, and marks the bad
mformat (1) - add an MSDOS filesystem to a low-level
```

## alias, unalias

`alias`命令用来为命令起别名。

```bash
$ alias foo='cd /usr; ls; cd -'

$ type foo
foo is aliased to `cd /usr; ls ; cd -'
```

上面命令指定`foo`为三个命令的别名。以后，执行`foo`就相当于一起执行这三条命令。

注意，默认情况下，别名只在当前Session有效。当前Session结束时，这些别名就会消失。

`alias`命令不加参数时，显示所有有效的别名。

```bash
$ alias
alias l.='ls -d .* --color=tty'
alias ll='ls -l --color=tty'
alias ls='ls --color=tty'
```

`unalias`命令用来取消别名。

```bash
$ unalias foo
$ type foo
bash: type: foo: not found
```

## which

`which`命令显示可执行程序的路径。

```bash
$ which ls
/bin/ls
```

`which`命令用于Shell内置命令时（比如`cd`），将没有任何输出。

## help，man

`help`命令用于查看Shell内置命令的帮助信息，`man`命令用于查看可执行命令的帮助信息。

```bash
$ help cd
$ man ls
```

`man`里面的文档一共有8类，如果同一个命令，匹配多个文档，`man`命令总是返回第一个匹配。如果想看指定类型的文档，命令可以采用下面的形式。

```bash
$ man 5 passwd
```

## script

`script`命令会将输入的命令和它的输出，都保存进一个文件。

```bash
$ script [file]
```

如果没有指定文件名，则所有结果会保存进当前目录下`typescript`文件。结束录制的时候，可以按下`Ctrl + d`。

## export

`export`命令用于将当前进程的变量，输出到所有子进程。

## 命令的连续执行

多个命令可以写在一起。

Bash 提供三种方式，定义它们如何执行。

```bash
# 第一个命令执行完，执行第二个命令
command1; command2

# 只有第一个命令成功执行完（退出码0），才会执行第二个命令
command1 && command2

# 只有第一个命令执行失败（退出码非0），才会执行第二个命令
command1 || command2
```

上面三种执行方法的退出码，都是最后一条执行的命令的退出码。

bash 允许把命令组合在一起。可以通过两种方式完成；要么用一个 group 命令，要么用一个子 shell。 这里是每种方式的语法示例：

组命令：

```bash
{ command1; command2; [command3; ...] }
```

子 shell

```bash
(command1; command2; [command3;...])
```

这两种形式的不同之处在于，组命令用花括号把它的命令包裹起来，而子 shell 用括号。值得注意的是，鉴于 bash 实现组命令的方式， 花括号与命令之间必须有一个空格，并且最后一个命令必须用一个分号或者一个换行符终止。

那么组命令和子 shell 命令对什么有好处呢？ 它们都是用来管理重定向的。

```bash
{ ls -l; echo "Listing of foo.txt"; cat foo.txt; } > output.txt
```

使用一个子 shell 是相似的。

```bash
(ls -l; echo "Listing of foo.txt"; cat foo.txt) > output.txt
```

组命令和子 shell 真正闪光的地方是与管道线相结合。 当构建一个管道线命令的时候，通常把几个命令的输出结果合并成一个流是很有用的。 组命令和子 shell 使这种操作变得很简单。

```bash
{ ls -l; echo "Listing of foo.txt"; cat foo.txt; } | lpr
```

这里我们已经把我们的三个命令的输出结果合并在一起，并把它们用管道输送给命令 lpr 的输入，以便产生一个打印报告。

虽然组命令和子 shell 看起来相似，并且它们都能用来在重定向中合并流，但是两者之间有一个很重要的不同。 然而，一个组命令在当前 shell 中执行它的所有命令，而一个子 shell（顾名思义）在当前 shell 的一个 子副本中执行它的命令。这意味着运行环境被复制给了一个新的 shell 实例。当这个子 shell 退出时，环境副本会消失， 所以在子 shell 环境（包括变量赋值）中的任何更改也会消失。因此，在大多数情况下，除非脚本要求一个子 shell， 组命令比子 shell 更受欢迎。组命令运行很快并且占用的内存也少。

当我们发现管道线中的一个 read 命令 不按我们所期望的那样工作的时候。为了重现问题，我们构建一个像这样的管道线：

```bash
echo "foo" | read
echo $REPLY
```

该 REPLY 变量的内容总是为空，是因为这个 read 命令在一个子 shell 中执行，所以它的 REPLY 副本会被毁掉， 当该子 shell 终止的时候。因为管道线中的命令总是在子 shell 中执行，任何给变量赋值的命令都会遭遇这样的问题。 幸运地是，shell 提供了一种奇异的展开方式，叫做进程替换，它可以用来解决这种麻烦。进程替换有两种表达方式：

一种适用于产生标准输出的进程：

```bash
<(list)
```

另一种适用于接受标准输入的进程：

```bash
>(list)
```

这里的 list 是一串命令列表：

为了解决我们的 read 命令问题，我们可以雇佣进程替换，像这样。

```bash
read < <(echo "foo")
echo $REPLY
```

进程替换允许我们把一个子 shell 的输出结果当作一个用于重定向的普通文件。事实上，因为它是一种展开形式，我们可以检验它的真实值：

```bash
[me@linuxbox ~]$ echo <(echo "foo")
/dev/fd/63
```

通过使用 echo 命令，查看展开结果，我们看到子 shell 的输出结果，由一个名为 /dev/fd/63 的文件提供。
# 文件系统

## pwd

`pwd`命令显示列出当前所在的目录。

```bash
$ pwd
```

## cd

`cd`命令用来改变用户所在的目录。

```bash
# 进入用户的主目录
$ cd

# 进入前一个工作目录
$ cd -

# 进入指定用户的主目录
$ cd ~user_name
```

## ls

`ls`目录可以显示指定目录的内容。不加参数时，显示当前目录的内容。

```bash
$ ls
```

上面命令显示当前目录的内容。

`ls`命令也可以显示指定文件是否存在。

```bash
$ ls foo.txt
foo.txt
```

`-l`参数可以显示文件的详细信息。

```bash
$ ls -l foo.txt
-rw-rw-r-- 1 me   me   0 2016-03-06 14:52 foo.txt
```

上面命令输出结果的第一栏，是文件的类型和权限。

文件类型分为以下几种。

- `-` 普通文件
- `d` 目录
- `l` 符号链接。注意，对于符号链接文件，剩余的文件属性总是"rwxrwxrwx"。
- `c` 字符设备文件，指按照字节流处理数据的设备，比如调制解调器。
- `b` 块设备文件，指按照数据块处理数据的设备，比如硬盘。

其他参数的用法。

```bash
# 显示多个目录的内容
$ ls ~ /usr

# -a --all 显示隐藏文件
$ ls -a

# -A 与-a类似，但是不显示当前目录和上一级目录两个点文件
$ ls -A

# -l 显示详细信息
$ ls -l

# -1 单列显示，每行只显示一个文件
$ ls -1

# -d 显示当前目录本身，而不是它的内容
# 通常与-l配合使用，列出一个目录本身的详细信息
$ ls -dl

# -F 目录名之后添加斜杠，可执行文件后面添加星号
$ ls -F

# -h 与-l配合使用，将文件大小显示为人类可读的格式

# -t 按文件修改时间排序，修改晚的排在前面
$ ls -t

# -s 按文件大小排序，

# --reverse 显示结果倒序排列
$ ls -lt --reverse
```

如果只显示一个目录里面的子目录，不显示文件，可以使用下面这些命令。

```bash
# 只显示常规目录
$ ls -d */
$ ls -F | grep /
$ ls -l | grep ^d
$ tree -dL 1

# 只显示隐藏目录
$ ls -d .*/

# 隐藏目录和非隐藏目录都显示
$ find -maxdepth 1 -type d
```

另一个简便方法是利用自动补全功能，先键入`cd`命令，然后连按两下`tab`键。

## stat

`stat`命令是加强版的`ls`命令，可以显示一个文件的详细信息。

```bash
$ stat timestamp
File: 'timestamp'
Size: 0 Blocks: 0 IO Block: 4096 regular empty file
Device: 803h/2051d Inode: 14265061 Links: 1
Access: (0644/-rw-r--r--) Uid: ( 1001/ me) Gid: ( 1001/ me)
Access: 2008-10-08 15:15:39.000000000 -0400
Modify: 2008-10-08 15:15:39.000000000 -0400
Change: 2008-10-08 15:15:39.000000000 -0400
```

## touch

`touch`用来设置或更新文件的访问，更改，和修改时间。然而，如果一个文件名参数是一个 不存在的文件，则会创建一个空文件。

```bash
$ touch timestamp
```

上面命令创建了一个名为`timestamp`空文件。如果该文件已经存在，就会把它的修改时间设置为当前时间。

```bash
$ mkdir -p playground/dir-{00{1..9},0{10..99},100}
$ touch playground/dir-{00{1..9},0{10..99},100}/file-{A..Z}
```

上面的命令创建了一个包含一百个子目录，每个子目录中包含了26个空文件。

## file

`file`命令显示指定文件的类型。

```bash
$ file picture.jpg
picture.jpg: JPEG image data, JFIF standard 1.01
```

## chmod

`chmod`命令用于更改文件的权限，是“change mode”的缩写。

```bash
$ chmod 600 foo.txt
```

上面命令将`foo.txt`的权限改成了600。

`chmod`还可以接受四个缩写，为不同的对象单独设置权限。

- `u` 所有者“user”的简写
- `g` 用户组“group”的缩写
- `o` 其他所有人“others”的简写
- `a` 所有人“all”的简写

```bash
# 为所有者添加可执行权限
$ chmod u+x foo.txt

# 删除所有者的可执行权限
$ chmod u-x foo.txt

# 为所有人添加可执行权限，等价于 a+x
$ chmod +x foo.txt

# 删除其他人的读权限和写权限。
$ chmod o-rw foo.txt

# 设定用户组和其他人的权限是读权限和写权限
$ chmod go=rw foo.txt

# 为所有者添加执行权限，设定用户组和其他人为读权限和写权限，多种设定用逗号分隔
$ chmod u+x,go=rw foo.txt
```

添加权限。

- +x 添加执行权限
- +r 设置读权限
- +w 设置写权限
- +rwx 设置所有读、写和执行权限。

删除权限只需将`+`更改为`-`，就可以删除任何已设置的指定权限。可以使用`-R`（或`--recursive`）选项来递归地操作目录和文件。

设置精确权限，可以使用`=`代替`+`或`-`来实现此操作。如果想为用户、组或其他用户设置不同的权限，可以使用逗号将不同表达式分开（例如`ug=rwx,o=rx`）。

由于一共有3种可能的权限。也可以使用八进制数代替符号来设置权限。通过这种方式设置的权限最多使用3个八进制数。第1个数定义用户权限，第2个数定义组权限，第3个数定义其他权限。这3个数中的每一个都通过添加想要的权限设置来构造：读 (4)、写 (2) 和执行 (1)。

- rwx	7
- rw-	6
- r-x	5
- r--	4
- -wx	3
- -w-	2
- --x	1
- ---	0

## umask

`umask`用来查看和设置权限掩码。

```bash
$ umask
0022
```

上面命令显示当前系统之中，默认的文件掩码是`0022`，转为二进制就是`000 000 010 010`。

可以看到，这个掩码是一个12位的二进制数，后面的9位分别代表文件三种使用对象的三类权限。只要对应位置上是`1`，就表示关闭该项权限，所以`010`就表示关闭读权限。

新建文件时，通常不会带有执行权限，也就是说，新建文件的默认权限是`rw-rw-rw-`。如果文件掩码是`0022`，那么用户组和其他人的写权限也会被拿掉。

```bash
$ touch new.txt
$ ls -l new.txt
-rw-r--r-- 1 me   me   0 2016-03-06 14:52 new.txt
```

上面代码中，`new.txt`的用户组和其他人的写权限就没了。

`umask`后面跟着参数，就表示设置权限掩码。

```bash
$ umask 0000
```

上面命令将权限掩码设为`0000`，实际上就是关闭了权限掩码。

`umask`命令设置的掩码值只能在当前Shell会话中生效，若当前Shell会话结束后，则必须重新设置。

## du

`du`命令用于查看指定目录的大小。

```bash
$ du -hs /path/to/directory
```

显示第一层子目录的大小。

```bash
$ du -h --max-depth=1 /path/to/folder
```

参数的含义。

- `-h` 表示人类可读的格式
- `-s` 表示总结信息，否则会显示该目录内所有文件和子目录的信息。

`tree`命令也可以显示子目录大小。

```bash
$ tree --du -h /path/to/directory
```

## md5sum

`md5sum`命令用来显示一个文件的md5校验码。

```bash
$ md5sum image.iso
34e354760f9bb7fbf85c96f6a3f94ece    image.iso
```

## locate

`locate`程序快速搜索本机的路径名数据库，并且输出每个与给定字符串相匹配的文件名。

```bash
$ locate bin/zip
/usr/bin/zip
/usr/bin/zipcloak
/usr/bin/zipgrep
/usr/bin/zipinfo
/usr/bin/zipnote
/usr/bin/zipsplit
```

`locate`数据库由另一个叫做`updatedb`的程序创建。大多数装有 locate 的系统会每隔一天运行一回 updatedb 程序。因为数据库不能被持续地更新，所以当使用 locate 时，你会发现 目前最新的文件不会出现。为了克服这个问题，可以手动运行 updatedb 程序， 更改为超级用户身份，在提示符下运行 updatedb 命令。

`locate`支持正则查找。`--regexp`参数支持基本的正则表达式，`--regex`参数支持扩展的正则表达式。

```bash
$ locate --regex 'bin/(bz|gz|zip)'
```

## find

`locate`程序只能依据文件名来查找文件，而`find`程序能基于各种各样的属性，搜索一个给定目录（以及它的子目录），来查找文件。

```bash
# 输出当前目录的所有子目录和文件（含子目录）
$ find
$ find .

# 显示当前目录的文件总数
$ find . | wc -l

# 当前目录的子目录总数
$ find . -type d | wc -l

# 当前目录的文件总数（不含子目录）
$ find . -type f | wc -l

# 当前目录的文件名匹配“*.JPG”且大于1M的文件总数
$ find . -type f -name "\*.JPG" -size +1M | wc -l
```

`-type`参数支持的文件类型。

- `b`	块设备文件
- `c`	字符设备文件
- `d`	目录
- `f`	普通文件
- `l`	符号链接

`-size`参数支持的文件大小类型。

- b	512 个字节块。如果没有指定单位，则这是默认值。
- c	字节
- w	两个字节的字
- k	千字节
- M	兆字节
- G	千兆字节

`find`程序支持的查询参数。

- -cmin n	匹配的文件和目录的内容或属性最后修改时间正好在 n 分钟之前。 指定少于 n 分钟之前，使用 -n，指定多于 n 分钟之前，使用 +n。
- -cnewer file	匹配的文件和目录的内容或属性最后修改时间早于那些文件。
- -ctime n	匹配的文件和目录的内容和属性最后修改时间在 n\*24小时之前。
- -empty	匹配空文件和目录。
- -group name	匹配的文件和目录属于一个组。组可以用组名或组 ID 来表示。
- -iname pattern	就像-name 测试条件，但是不区分大小写。
- -inum n	匹配的文件的 inode 号是 n。这对于找到某个特殊 inode 的所有硬链接很有帮助。
- -mmin n	匹配的文件或目录的内容被修改于 n 分钟之前。
- -mtime n	匹配的文件或目录的内容被修改于 n\*24小时之前。
- -name pattern	用指定的通配符模式匹配的文件和目录。
- -newer file	匹配的文件和目录的内容早于指定的文件。当编写 shell 脚本，做文件备份时，非常有帮助。 每次你制作一个备份，更新文件（比如说日志），然后使用 find 命令来决定自从上次更新，哪一个文件已经更改了。
- -nouser	匹配的文件和目录不属于一个有效用户。这可以用来查找 属于删除帐户的文件或监测攻击行为。
- -nogroup	匹配的文件和目录不属于一个有效的组。
- -perm mode	匹配的文件和目录的权限已经设置为指定的 mode。mode 可以用 八进制或符号表示法。
- -samefile name	相似于-inum 测试条件。匹配和文件 name 享有同样 inode 号的文件。
- -size n	匹配的文件大小为 n。
- -type c	匹配的文件类型是 c。
- -user name	匹配的文件或目录属于某个用户。这个用户可以通过用户名或用户 ID 来表示。
- -depth	指导 find 程序先处理目录中的文件，再处理目录自身。当指定-delete 行为时，会自动 应用这个选项。
- -maxdepth levels	当执行测试条件和行为的时候，设置 find 程序陷入目录树的最大级别数
- -mindepth levels	在应用测试条件和行为之前，设置 find 程序陷入目录数的最小级别数。
- -mount	指导 find 程序不要搜索挂载到其它文件系统上的目录。
- -regex 指定正则表达式

```bash
# 找出包括空格或其它不规范字符的文件名或路径名
$ find . -regex '.*[^-\_./0-9a-zA-Z].*'
```

`find`程序还支持逻辑操作符。

- `-and`	如果操作符两边的测试条件都是真，则匹配。可以简写为 -a。 注意若没有使用操作符，则默认使用 -and。
- `-or`	若操作符两边的任一个测试条件为真，则匹配。可以简写为 -o。
- `-not`	若操作符后面的测试条件是真，则匹配。可以简写为一个感叹号（!）。
- `()`	把测试条件和操作符组合起来形成更大的表达式。这用来控制逻辑计算的优先级。注意 因为圆括号字符对于 shell 来说有特殊含义，所以在命令行中使用它们的时候，它们必须 用引号引起来，才能作为实参传递给 find 命令。通常反斜杠字符被用来转义圆括号字符。

```bash
# 或关系
( expression 1 ) -or ( expression 2 )

# 找出不是600权限的文件，或者不是700权限的目录
$ find ~ \( -type f -not -perm 0600 \) -or \( -type d -not -perm 0700 \)
```

`find`程序的逻辑表达式，具有“短路运算”的特点，即对于`expr1 -operator expr2`这个表达式，`expr2`不一定执行。这是为了提高运行速度。

- expr1 为真，且操作符为`-and`，expr2 总是执行
- expr1 为假，且操作符为`-and`，expr2 从不执行
- expr1 为真，且操作符为`-or`，expr2 从不执行
- expr1 为假，且操作符为`-or`，expr2 总是执行

为了方便执行一些常见操作，`find`程序定义了一些预定义操作。

- -delete	删除当前匹配的文件。
- -ls	对匹配的文件执行等同的 ls -dils 命令。并将结果发送到标准输出。
- -print	把匹配文件的全路径名输送到标准输出。如果没有指定其它操作，这是 默认操作。
- -quit	一旦找到一个匹配，退出。

```bash
# 找到匹配的文件，并显示在标准输出
# -print 是默认操作，可以省略
$ find . -print

# 删除后缀名为BAK的文件
# 执行 delete 操作前，最好先执行 print 操作，确认要删除哪些文件
$ find . -type f -name '*.BAK' -delete
```

预定义操作可以与逻辑表达式，结合使用。

```bash
$ find ~ -type f -and -name '*.BAK' -and -print
```

除了预定义操作以外，用户还可以使用`-exec`参数自定义操作。

```bash
-exec command {} ;
```

上面的命令中，`command`是一个命令行命令，`{}`用来指代当前路径，分号表示命令结束。

```bash
# 预定义的 -delete 操作，等同于下面的操作
-exec rm '{}' ';'
```

`-exec`使用时，每次找到一个匹配的文件，会启动一个新的指定命令的实例。

```bash
$ find ~ -type f -name 'foo*' -exec ls -l '{}' ';'
```

执行上面的命令，`ls`程序可能会被调用多次。

```bash
$ ls -l file1
$ ls -l file2
```

如果想改成`ls`程序只调用一次，要把`find`命令里面的分号，改成加号。

```bash
$ ls -l file1 file2
# 相当于
$ find ~ -type f -name 'foo*' -exec ls -l '{}' +
```

## xargs

`xargs`命令从标准输入接受输入，并把输入转换为一个特定命令的参数列表。

```bash
$ find ~ -type f -name 'foo\*' -print | xargs ls -l
```
# 文件操作

## cp

`cp`命令用于将文件（或目录）拷贝到目的地。

```bash
# 拷贝单个文件
$ cp source dest

# 拷贝多个文件
$ cp source1 source2 source3 dest

# -i 目的地有同名文件时会提示确认
$ cp -i file1 file2

# -r 递归拷贝，将dir1拷贝到dir2，完成后dir2生成一个子目录dir1
# dir2如果不存在，将被创建
# 拷贝目录时，该参数是必需的
$ cp -r dir1 dir2

# -u --update 只拷贝目的地没有的文件，或者比目的地同名文件更新的文件
$ cp -u *.html destination
```

其他参数

- `-a` 拷贝时保留所有属性，包括所有者与权限
- `-v` 显示拷贝的详细信息

## mkdir

`mkdir`命令用于新建目录。

```bash
# 新建多个目录
$ mkdir dir1 dir2 dir3
```

## mv

`mv`命令用于将源文件移动到目的地。

```bash
# 移动单个文件
$ mv item1 item2

# 移动多个文件
$ mv file1 file2 dir1

# 将dir1拷贝进入dir2，完成后dir2将多出一个子目录dir1
# 如果dir2不存在，将会被创建
$ mv dir1 dir2
```

参数

- `-i` 覆盖已经存在的文件时，会提示确认
- `-u` 只移动目的地不存在的文件，或比目的地更新的文件

## rm

`rm`命令用于删除文件。

参数。

- `-i` 文件存在时，会提示确认。
- `-r` 递归删除一个子目录
- `-f` 如果删除不存在的文件，不报错
- `-v` 删除时展示详细信息

## ln

`ln`命令用于建立链接文件。

```bash
# 新建硬链接
$ ln file link

# 新建软链接
$ ln -s item link
```

# 硬件操作

## df

`df`命令查看硬盘信息。

```bash
$ df
Filesystem 1K-blocks Used Available Use% Mounted on
/dev/sda2 15115452 5012392 9949716 34% /
/dev/sda5 59631908 26545424 30008432 47% /home
/dev/sda1 147764 17370 122765 13% /boot
```

## free

`free`命令查看内存占用情况。

```bash
$ free
 total used free shared buffers cached
Mem: 513712 503976 9736 0 5312 122916
-/+ buffers/cache: 375748 137964
Swap: 1052248 104712 947536
```

## 硬盘

文件`/etc/fstab`配置系统启动时要挂载的设备。

```
LABEL=/12               /               ext3        defaults        1   1
LABEL=/home             /home           ext3        defaults        1   2
LABEL=/boot             /boot           ext3        defaults        1   2
```

输出结果一共有6个字段，含义依次如下。

- 设备名：与物理设备相关联的设备文件（或设备标签）的名字，比如说`/dev/hda1`（第一个 IDE 通道上第一个主设备分区）。
- 挂载点：设备所连接到的文件系统树的目录。
- 文件系统类型：Linux 允许挂载许多文件系统类型。
- 选项：文件系统可以通过各种各样的选项来挂载。
- 频率：一位数字，指定是否和在什么时间用 dump 命令来备份一个文件系统。
- 次序：一位数字，指定 fsck 命令按照什么次序来检查文件系统。

## mount

`mount`不带参数时，显示当前挂载的文件系统。

```bash
$ mount
/dev/sda2 on / type ext3 (rw)
proc on /proc type proc (rw)
sysfs on /sys type sysfs (rw)
devpts on /dev/pts type devpts (rw,gid=5,mode=620)
/dev/sda5 on /home type ext3 (rw)
```

这个列表的格式是：设备 on 挂载点 type 文件系统类型（可选的）。

`mount`带参数时，用于将设备文件挂载到挂载点，`-t`参数用来指定文件系统类型。

```bash
$ mount -t iso9660 /dev/hdc /mnt/cdrom

# 挂载一个iso文件
$ mount -t iso9660 -o loop image.iso /mnt/iso_image
```

## umount

`umount`命令用来卸载设备。

```bash
$ umount [设备名]

$ umount /dev/hdc
```

## fdisk

`fdisk`命令用于格式化磁盘。

```bash
$ sudo umount /dev/sdb1
$ sudo fdisk /dev/sdb
```

## mkfs

`mkfs`命令用于在一个设备上新建文件系统。

```bash
$ sudo mkfs -t ext3 /dev/sdb1
$ sudo mkfs -t vfat /dev/sdb1
```

## fsck

`fsck`命令用于检查（修复）文件系统。

```bash
$ sudo fsck /dev/sdb1
```

## dd

`dd`命令用于将大型数据块，从一个磁盘复制到另一个磁盘。

```bash
$ dd if=input_file of=output_file [bs=block_size [count=blocks]]

# 将 /dev/sdb 的所有数据复制到 /dev/sdc
$ dd if=/dev/sdb of=/dev/sdc

# 将 /dev/sdb 的所有数据拷贝到一个镜像文件
$ dd if=/dev/sdb of=flash_drive.img

# 从cdrom制作一个iso文件
$ dd if=/dev/cdrom of=ubuntu.iso
```

## dmidecode

`dmidecode`命令用于输出BIOS信息。

```bash
$ sudo dmidecode
```

以上命令会输出全部BIOS信息。为了便于查看，往往需要指定所需信息的类别。

- 0 BIOS
- 1 System
- 2 Base Board
- 3 Chassis 4 Processor
- 5 Memory Controller
- 6 Memory Module
- 7 Cache
- 8 Port Connector
- 9 System Slots
- 10 On Board Devices
- 11 OEM Strings
- 12 System Configuration Options
- 13 BIOS Language
- 14 Group Associations
- 15 System Event Log
- 16 Physical Memory Array
- 17 Memory Device
- 18 32-bit Memory Error
- 19 Memory Array Mapped Address
- 20 Memory Device Mapped Address
- 21 Built-in Pointing Device
- 22 Portable Battery
- 23 System Reset
- 24 Hardware Security
- 25 System Power Controls
- 26 Voltage Probe
- 27 Cooling Device
- 28 Temperature Probe
- 29 Electrical Current Probe
- 30 Out-of-band Remote Access
- 31 Boot Integrity Services
- 32 System Boot
- 33 64-bit Memory Error
- 34 Management Device
- 35 Management Device Component
- 36 Management Device Threshold Data
- 37 Memory Channel
- 38 IPMI Device
- 39 Power Supply

查看内存信息的命令如下。

```bash
$ sudo dmidecode -t 17
# 或者
$ dmidecode --type 17
```

以下是其他一些选项。

```bash
# 查看BIOS信息
$ sudo dmidecode –t 0

# 查看CPU信息
$ sudo dmidecode -t 4
```

`dmidecode`也支持关键词查看，关键词与类别的对应关系如下。

- bios 0, 13
- system 1, 12, 15, 23, 32
- baseboard 2, 10
- chassis 3
- processor 4
- memory 5, 6, 16, 17
- cache 7
- connector 8
- slot 9

查看系统信息的命令如下。

```bash
$ sudo dmidecode -t system
```

## lspci

`lspci`命令列出本机的所有PCI设备。

```bash
$ lspci
```

该命令输出信息的格式如下。

```bash
03:00.0 Unassigned class [ff00]: Realtek Semiconductor Co., Ltd. RTS5209 PCI Express Card Reader (rev 01)
```

输出信息一共分成三个字段。

- Field 1：PCI bus slot 的编号
- Field 2：PCI slot的名字
- Field 3：设备名和厂商名

如果想查看更详细信息，可以使用下面的命令。

```bash
$ lspci -vmm
```

## lsusb

`lsusb`命令用于操作USB端口。

下面命令列出本机所有USB端口。

```bash
$ lsusb
```

它的输出格式如下。

```bash
Bus 002 Device 003: ID 0781:5567 SanDisk Corp. Cruzer Blade
```

各个字段的含义如下。

- Bus 002 : bus编号
- Device 003：bus 002连接的第三个设备
- ID 0781:5567：当前设备的编号，冒号前是厂商编号，冒号后是设备编号
- SanDisk Corp. Cruzer Blade：厂商和设备名

找出本机有多少个USB接口可用。

```bash
$ find /dev/bus/
/dev/bus/
/dev/bus/usb
/dev/bus/usb/002
/dev/bus/usb/002/006
/dev/bus/usb/002/005
/dev/bus/usb/002/004
/dev/bus/usb/002/002
/dev/bus/usb/002/001
/dev/bus/usb/001
/dev/bus/usb/001/007
/dev/bus/usb/001/003
/dev/bus/usb/001/002
/dev/bus/usb/001/001
```

查看某个USB设备的详细情况。

```bash
$ lsusb -D /dev/bus/usb/002/005
```

查看所有设备的详细情况。

```bash
$ lsusb -v
```

查看USB端口的版本。

```bash
$ lsusb -v | grep -i bcdusb
```
# 主机管理

## hostname命令

`hostname`命令返回当前服务器的主机名。

```bash
$ hostname
```
# 命名管道

在大多数类似 Unix 的操作系统中，有可能创建一种特殊类型的文件，叫做命名管道。命名管道用来在 两个进程之间建立连接，也可以像其它类型的文件一样使用。

命令管道的行为类似于文件，但实际上形成了先入先出（FIFO）的缓冲。和普通（未命令的）管道一样， 数据从一端进入，然后从另一端出现。通过命令管道，有可能像这样设置一些东西：

```bash
process1 > named_pipe
```

和

```bash
process2 < named_pipe
```

表现出来就像这样：

```bash
process1 | process2
```

## 设置一个命名管道

使用 mkfifo 命令能够创建命令管道：

```bash
$ mkfifo pipe1
$ ls -l pipe1
prw-r--r-- 1 me me 0 2009-07-17 06:41 pipe1
```

这里我们使用 mkfifo 创建了一个名为 pipe1 的命名管道。使用 ls 命令，我们查看这个文件， 看到位于属性字段的第一个字母是 “p”，表明它是一个命名管道。

## 使用命名管道

为了演示命名管道是如何工作的，我们将需要两个终端窗口（或用两个虚拟控制台代替）。 在第一个终端中，我们输入一个简单命令，并把命令的输出重定向到命名管道：

```bash
$ ls -l > pipe1
```

我们按下 Enter 按键之后，命令将会挂起。这是因为在管道的另一端没有任何接受数据。当这种现象发生的时候， 据说是管道阻塞了。一旦我们绑定一个进程到管道的另一端，该进程开始从管道中读取输入的时候，这种情况会消失。 使用第二个终端窗口，我们输入这个命令。

```bash
$ cat < pipe1
```

然后产自第一个终端窗口的目录列表出现在第二个终端中，并作为来自 cat 命令的输出。在第一个终端 窗口中的 ls 命令一旦它不再阻塞，会成功地结束。


# 进程管理

## ps

`ps`命令用来列出进程信息。

```bash
$ ps
PID TTY           TIME CMD
5198 pts/1    00:00:00 bash
10129 pts/1   00:00:00 ps
```

不带任何参数时，`ps`只列出与当前Session相关的进程。输出结果中，`PID`是进程ID、`TTY`是进程的终端号（如果显示`?`，则表示进程没有终端），`TIME`是消耗的CPU时间，`CMD`是触发进程的命令。

`x`参数列出所有进程的详细信息，包括不在当前Session的信息。

```bash
$ ps x
PID TTY   STAT   TIME COMMAND
2799 ?    Ssl    0:00 /usr/libexec/bonobo-activation-server –ac
2820 ?    Sl     0:01 /usr/libexec/evolution-data-server-1.10 --
```

这时的输出结果，会多出`STAT`一栏，表示状态。它的各种值如下。

- `R` 正在运行或准备运行
- `S` 正在睡眠，即没有运行，正在等待一个事件唤醒
- `D` 不可中断睡眠。进程正在等待 I/O，比如磁盘驱动器的I/O
- `T` 已停止，即进程停止运行
- `Z` “僵尸”进程。即这是一个已经终止的子进程，但父进程还没有清空它（没有把子进程从进程表中删除）
- `<` 高优先级进程。这可能会授予一个进程更多重要的资源，给它更多的 CPU 时间。
- `N` 低优先级进程。一个低优先级进程（一个“好”进程）只有当其它高优先级进程执行之后，才会得到处理器时间。

`aux`参数可以显示更多信息。

```bash
$ ps aux
USER   PID  %CPU  %MEM     VSZ    RSS  TTY   STAT   START   TIME  COMMAND
root     1   0.0   0.0    2136    644  ?     Ss     Mar05   0:31  init
root     2   0.0   0.0       0      0  ?     S&lt;     Mar05   0:00  [kt]
```

输出结果包含的列的含义如下。

- `USER` 用户ID，表示进程的所有者
- `%CPU` 百分比表示的 CPU 使用率
- `%MEM` 百分比表示的内存使用率
- `VSZ` 虚拟内存大小
- `RSS` 进程占用的物理内存的大小，以千字节为单位。
- `START` 进程运行的起始时间。若超过24小时，则用天表示。

## top

`top`命令可以查看机器的当前状态。

```bash
$ top
```

它的输出结果分为两部分，最上面是系统概要，下面是进程列表，以 CPU 的使用率排序。

输出结果是动态更新的，默认每三分钟更新一次。

## jobs

`jobs`命令用来查看后台任务。

```bash
$ jobs
[1]+ Running            xlogo &
```

输出结果之中，每个后台任务会有一个编号。上面结果中，`xlogo`的编号是`1`，`+`表示正在运行。

## fg

`fg`命令用于将后台任务切换到前台。

```bash
$ fg %1
```

`fg`命令之后，跟随着一个百分号和工作序号，用来指定切换哪一个后台任务。如果只有一个后台任务，那么`fg`命令可以不带参数。

## bg

`bg`命令用于将一个暂停的前台任务，转移到后台。只有暂停的任务，才能使用`bg`命令，因为正在运行的任务，命令行是无法输入的。

```bash
$ bg %1
```

`Ctrl + z`可以暂停正在运行的前台任务。

## kill

`kill`命令用于杀死进程。它的参数是进程ID。

```bash
$ kill 28401
```

`kill`命令的实质是操作系统向进程发送信号。在使用 Ctrl-c 的情况下，会发送一个叫做 INT（中断）的信号；当使用 Ctrl-z 时，则发送一个叫做 TSTP（终端停止）的信号。

`kill`命令可以用来向进程发送指定信号。

```bash
$ kill [-signal] PID
```

下面是常见信号。

- HUP：编号1，表示挂起。发送这个信号到前台程序，程序会终止。许多守护进程也使用这个信号，来重新初始化。这意味着，当发送这个信号到一个守护进程后， 这个进程会重新启动，并且重新读取它的配置文件。Apache 网络服务器守护进程就是一个例子。
- INT：编号2，中断。实现和`Ctrl-c`一样的功能，由终端发送。通常，它会终止一个程序。
- KILL：编号9，杀死。进程可能选择忽略这个信号。所以，操作系统不发送该信号到目标进程，而是内核立即终止这个进程。当一个进程以这种方式终止的时候，它没有机会去做些“清理”工作，或者是保存劳动成果。因为这个原因，把 KILL 信号看作杀手锏，当其它终止信号失败后，再使用它。
- TERM：编号15，终止。这是 kill 命令发送的默认信号。如果程序仍然“活着”，可以接受信号，那么这个信号终止。
- CONT：编号18，继续。在停止一段时间后，进程恢复运行。
- STOP：编号19，停止。这个信号导致进程停止运行，而没有终止。像 KILL 信号，它不被 发送到目标进程，因此它不能被忽略。
- QUIT：编号3，退出
- SEGV：编号11，段错误。如果一个程序非法使用内存，就会发送这个信号。也就是说，程序试图写入内存，而这个内存空间是不允许此程序写入的。
- TSTP：编号20，终端停止。当按下 Ctrl-z 组合键后，终端发送这个信号。不像 STOP 信号， TSTP 信号由目标进程接收，且可能被忽略。
- WINCH：编号28，改变窗口大小。当改变窗口大小时，系统会发送这个信号。 一些程序，像 top 和 less 程序会响应这个信号，按照新窗口的尺寸，刷新显示的内容。

`-l`参数可以列出所有信号。

```bash
$ kill -l
```

## killall

`killall`命令用于向指定的程序或用户发送信号。

```bash
$ killall [-u user] [-signal] name
```

## 其他进程相关命令

- `pstree` 输出树型结构的进程列表，这个列表展示了进程间父/子关系。
- `vmstat` 输出一个系统资源使用快照，包括内存，交换分区和磁盘 I/O。 为了看到连续的显示结果，则在命令名后加上延时的时间（以秒为单位）。例如，“vmstat 5”。 终止输出，按下 Ctrl-c 组合键。
- `xload` 一个图形界面程序，可以画出系统负载的图形。
- `tload` 与`xload`程序相似，但是在终端中画出图形。使用 Ctrl-c，来终止输出。
# 重定向

重定向指的是将命令行输出写入指定位置。

- `cmd1 | cmd2`：Pipe; take standard output of cmd1 as standard input to cmd2.
- `> file`：Direct standard output to file.
- `< file`：Take standard input from file.
- `>> file`：Direct standard output to file; append to file if it already exists.
- `>| file`：Force standard output to file even if noclobber is set.
- `n>| file`：Force output to file from file descriptor n even if noclobber is set.
- `<> file`：Use file as both standard input and standard output.
- `n<> file`：Use file as both input and output for file descriptor n.
- `<< label`：Here-document; see text.
- `n > file`：Direct file descriptor n to file.
- `n < file`：Take file descriptor n from file.
- `n >> file`：Direct file descriptor n to file; append to file if it already exists.
- `n>&`：Duplicate standard output to file descriptor n.
- `n<&`：Duplicate standard input from file descriptor n.
- `n>&m`：File descriptor  n is made to be a copy of the output file descriptor.
- `n<&m`：File descriptor  n is made to be a copy of the input file descriptor.
- `&>file`：Directs standard output and standard error to file.
- `<&-`：Close the standard input.
- `>&-`：Close the standard output.
- `n>&-`：Close the output from file descriptor  n.
- `n<&-`：Close the input from file descriptor  n.
- `n>&word`：If  n is not specified, the standard output (file descriptor 1) is used. If the digits in word do not specify a file descriptor open for output, a redirection error occurs. As a special case, if n is omitted, and word does not expand to one or more digits, the standard output and standard error are redirected as described previously.
- `n<&word`：If word expands to one or more digits, the file descriptor denoted by  n is made to be a copy of that file descriptor. If the digits in word do not specify a file descriptor open for input, a redirection error occurs. If word evaluates to -, file descriptor n is closed. If n is not specified, the standard input (file descriptor 0) is used.
- `n>&digit-`：Moves the file descriptor digit to file descriptor  n, or the standard output (file descriptor 1) if n is not specified.
- `n<&digit-`：Moves the file descriptor digit to file descriptor  n, or the standard input (file descriptor 0) if n is not specified. digit is closed after being duplicated to n.


`>`用来将标准输出重定向到指定文件。

```bash
$ ls -l /usr/bin > ls-output.txt
```

如果重定向后的指定文件已经存在，就会被覆盖，不会有任何提示。

如果命令没有任何输出，那么重定向之后，得到的是一个长度为`0`的文件。因此，`>`具有创建新文件或改写现存文件、将其改为长度`0`的作用。

```bash
$ > ls-output.txt
```

`>>`用来将标准输出重定向追加到指定文件。

```bash
$ ls -l /usr/bin >> ls-output.txt
```

`2>`用来将标准错误重定向到指定文件。

```bash
$ ls -l /bin/usr 2> ls-error.txt
```

标准输出和标准错误，可以重定向到同一个文件。

```bash
$ ls -l /bin/usr > ls-output.txt 2>&1
# 或者
$ ls -l /bin/usr &> ls-output.txt

# 追加到同一个文件
$ ls -l /bin/usr &>> ls-output.txt
```

如果不希望输出错误信息，可以将它重定向到一个特殊文件`/dev/null`。

```bash
$ ls -l /bin/usr 2> /dev/null
```

`|`用于将一个命令的标准输出，重定向到另一个命令的标准输入。

```bash
$ ls -l /usr/bin | less
```

不要将`>`与`|`混淆。

```bash
$ ls > less
```

上面命令会在当前目录，生成一个名为`less`的文本文件。

下面是标准错误重定向的一个例子。

```bash
invalid_input () {
    echo "Invalid input '$REPLY'" >&2
    exit 1
}
read -p "Enter a single item > "
[[ -z $REPLY ]] && invalid_input
```

## tee

`tee`命令用于同时将标准输出重定向到文件，以及另一个命令的标准输入。

```bash
$ ls /usr/bin | tee ls.txt | grep zip
```

## 命令替换

命令替换（command substitution）指的是将一个命令的输出，替换进入另一个命令。`$(command)`表示命令替换，另一种写法是使用反引号。

```bash
$ echo $(ls)
# 或者
$ echo `ls`

$ ls -l $(which cp)
# 或者
$ ls -l `which cp`
```

## basename

`basename`命令清除 一个路径名的开头部分，只留下一个文件的基本名称。

```bash
#!/bin/bash
# file_info: simple file information program
PROGNAME=$(basename $0)
if [[ -e $1 ]]; then
    echo -e "\nFile Type:"
    file $1
    echo -e "\nFile Status:"
    stat $1
else
    echo "$PROGNAME: usage: $PROGNAME file" >&2
    exit 1
fi
```
# 正则表达式

`正则表达式`是表达文本模式的方法。

- `.`：匹配任何单个字符。
- `?`：上一项是可选的，最多匹配一次。
- `*`：前一项将被匹配零次或多次。
- `+`：前一项将被匹配一次或多次。
- `{N}`：上一项完全匹配N次。
- `{N，}`：前一项匹配N次或多次。
- `{N，M}`：前一项至少匹配N次，但不超过M次。
- `--`：表示范围，如果它不是列表中的第一个或最后一个，也不是列表中某个范围的终点。
- `^`：匹配行首的空字符串；也代表不在列表范围内的字符。
- `$`：匹配行尾的空字符串。
- `\b`：匹配单词边缘的空字符串。
- `\B`：匹配空字符串，前提是它不在单词的边缘。
- `\<`：匹配单词开头的空字符串。
- `\>`：匹配单词末尾的空字符串。

## 元字符

`元字符`是表示特殊函数的字符，包括以下这些`^ $ . [ ] { } - ? * + ( ) | \\`。除了元字符，其他字符在正则表达式中，都表示原来的含义。

- `.` 匹配任意字符，但不含空字符
- `^` 匹配文本行开头
- `$` 匹配文本行结尾

```bash
$ grep -h '.zip' dirlist*.txt
```

上面命令在文件中查找包含正则表达式“.zip”的文本行。注意，上面命令不会匹配`zip`程序，因为`zip`只有三个字符，而`.zip`要求四个字符。

```bash
$ grep -h '^zip' dirlist*.txt
$ grep -h 'zip$' dirlist*.txt
```

上面命令分别在文件列表中搜索行首，行尾以及行首和行尾同时包含字符串“zip”（例如，zip 独占一行）的匹配行。 注意正则表达式‘^$’（行首和行尾之间没有字符）会匹配空行。

## 方括号

方括号之中的字符，表示可以任意匹配其中的一个。

```bash
$ grep -h '[bg]zip' dirlist*.txt
```

上面命令匹配包含字符串“bzip”或者“gzip”的任意行。

注意，元字符放入方括号之中，会失去其特殊含义。但有两种情况除外，`^`在方括号的开头，表示否定，否则只是一个普通字符，表示原义。

```bash
$ grep -h '[^bg]zip' dirlist*.txt
```

上面命令匹配不以`b`或`g`开头的`zip`字符串。注意，上面命令不会匹配`zip`，因为一个否定的字符集仍然要求存在一个字符。

`-`在方括号之中表示一个字符区域。

```bash
$ grep -h '^[A-Z]' dirlist*.txt
```

上面命令匹配所有以大写字母开头的文本行。类似的，`^[A-Za-z0-9]`表示以大写字母、小写字母、数字开头的文本行。

注意，连字号如果不构成一个字符区域，则表示其本来的含义。

```bash
$ grep -h '[-AZ]' dirlist*.txt
```

上面命令匹配包含一个连字符，或一个大写字母“A”，或一个大写字母“Z”的文件名。

## 预定义字符类

由于`locale`设置不同，Shell展开正则表达式`[A-Z]`时，可能不是解释为所有大写字母，而是解释为包括所有字母的字典顺序。

```bash
$ ls /usr/sbin/[A-Z]*
```

上面命令在某些发行版里面，会返回所有大写字母或小写字母开头的文件。

为了避免这个问题，可以使用正则表达式的预定义字符类。

- `[:alnum:]`	字母数字字符。在 ASCII 中，等价于：`[A-Za-z0-9]`
- `[:word:]`	与`[:alnum:]`相同, 但增加了下划线字符。
- `[:alpha:]`	字母字符。在 ASCII 中，等价于`[A-Za-z]`
- `[:blank:]`	包含空格和 tab 字符。
- `[:cntrl:]`	ASCII 的控制码。包含了0到31，和127的 ASCII 字符。
- `[:digit:]`	数字0到9
- `[:graph:]`	可视字符。在 ASCII 中，它包含33到126的字符。
- `[:lower:]`	小写字母。
- `[:punct:]`	标点符号字符。
- `[:print:]`	可打印的字符。等于`[:graph:]`中的所有字符，再加上空格字符。
- `[:space:]`	空白字符，包括空格，tab，回车，换行，vertical tab, 和 form feed.在 ASCII 中， 等价于`[ \t\r\n\v\f]`
- `[:upper:]`	大写字母。
- `[:xdigit:]`	用来表示十六进制数字的字符。在 ASCII 中，等价于`[0-9A-Fa-f]`

```bash
$ ls /usr/sbin/[[:upper:]]*
```

上面命令返回所有大写字母开头的文件名。

## 选择

`|`表示匹配一系列字符串之中的一个。注意与方括号区分，方括号表示匹配一系列字符之中的一个。

```bash
$ echo "AAA" | grep -E 'AAA|BBB'
AAA
$ echo "BBB" | grep -E 'AAA|BBB'
BBB
$ echo "CCC" | grep -E 'AAA|BBB'
$
```

上面代码中，`AAA|BBB`表示匹配字符串`AAA`或者是字符串`BBB`。`grep`程序使用`-E`参数，表示按照正则表达式规则匹配。并且，这个正则表达式放在单引号之中，为的是阻止Shell把`|`解释为管道操作符。

`|`可以多个连用，也可以与其他正则规则结合使用。

```bash
$ echo "AAA" | grep -E 'AAA|BBB|CCC'

$ grep -Eh '^(bz|gz|zip)' dirlist*.txt
```

## 量词操作符

量词操作符表示一个元素被匹配的次数。

- `?` 匹配前面的元素出现0次或1次
- `*` 匹配前面的元素出现0次或多次
- `+` 匹配前面的元素出现1次或多次
- `{n}` 匹配前面的元素出现了`n`次
- `{n,m}` 匹配前面的元素它至少出现了`n`次，但是不多于`m`次
- `{n,}` 匹配前面的元素至少出现了`n`次
- `{,m}` 匹配前面的元素，如果它出现的次数不多于 m 次。
# 系统信息

## uname

`uname`命令返回当前机器的信息。

```bash
# 内核的版本
$ uname -r
3.2.0-24-virtual

# CPU 架构
$ uname -m
x86_64
```

如果要了解操作系统的版本，可以查看`/etc/issue`文件。

```bash
$ cat /etc/issue
Debian GNU/Linux 9 \n \l
```

## service

`service`命令可以查看当前正在运行的服务。

```bash
$ service --status-all
 [ + ]  apache2
 [ ? ]  atd
 [ - ]  bootlogd
```

上面代码中，`+`表示正在运行，`-`表示已经停止，`?`表示`service`命令不了解相关信息。
# 文本处理

## cat

`cat`可以文件的内容，显示在标准输出。

```bash
$ cat text1
1 apple
2 pear
3 banana
```

它也可以同时输出多个文件内容。

```bash
$ cat text1 text2
```

它与重定向结合，就可以合并多个文件。

```bash
# 合并文本文件
$ cat text* > text.all

# 合并二进制文件
$ cat movie.mpeg.0* > movie.mpeg
```

如果调用`cat`命令时没有任何参数，它将读取标准输入，然后显示到标准输出。按下`Ctrl + d`，将会结束`cat`读取标准输入。利用这一点，可以将键盘输入写入指定文件，按下`Ctrl + d`结束输入。

```bash
$ cat > lazy_dog.txt
```

它的参数如下。

- `-n` 输出结果显示行号
- `-s` 将多个连续的空白行，输出为一行
- `-A` 输出结果中显示控制符，比如Tab键显示为`^I`，行尾显示`$`

`cat`支持Here document，显示多行文本。

```bash
cat << _EOF_
<HTML>
         <HEAD>
                <TITLE>$TITLE</TITLE>
         </HEAD>
         <BODY>
                <H1>$TITLE</H1>
                <P>$TIME_STAMP</P>
         </BODY>
</HTML>
_EOF_
```

Here document 常在脚本当中作为输入的手段。

```bash
$ sort -k2 <<END
> 1 apple
> 2 pear
> 3 banana
> END
1 apple
3 banana
2 pear
```

如果使用`<<-`代替`<<`，行首的tab键将被剥离。

## nl

`nl`命令为文本文件添加行号，显示在标准输出。

```bash
$ nl example.txt
```

## sort

`sort`命令将文本文件的所有行排序后输出。

```bash
$ sort file1.txt file2.txt file3.txt > final_sorted_list.txt
```

它的参数如下。

- `-b` `--ignore-leading-blanks` 默认情况下，排序用的是每行的第一个字符。这个参数忽略每行开头的空格，从第一个非空白字符开始排序。
- `-f` `--ignore-case` 让排序不区分大小写。
- `-n` `--numeric-sort` 按照数值排序，而不是字符值，用于行首是数值的情况。
- `-r` `--reverse` 按相反顺序排序。结果按照降序排列，而不是升序。
- `-k` `--key=field1[,field2]` 指定按照每行的第几个字段（从1开始）排序，而不是按照行首字符排序。该属性可以多个连用，用于指定多重排序标准，还可以指定每个字段指定排序标准，这些值与全局属性一致，比如b（忽略开头的空格），n（数值排序），r（逆向排序）等等。
- `-m` `--merge` 把每个参数看作是一个预先排好序的文件。把多个文件合并成一个排好序的文件，而没有执行额外的排序。
- `-o` `--output=file` 把排好序的输出结果发送到文件，而不是标准输出。
- `-t` `--field-separator=char` 定义字段分隔字符。默认情况下，字段由空格或制表符分隔。
- `-u` 输出结果中删除重复行

```bash
$ sort --key=1,1 --key=2n distros.txt
```

上面命令中，第一个`--key`指定第一排序标准是只用第一字段（`1,1`），也可以指定使用第一字段第一个字符（`1.1`）；第二排序标准是第二字段，按数值排序。

## uniq

`uniq`命令在排序后的行中，删除所有重复的行，保证所有输出没有重复。

```bash
$ ls /bin /usr/bin | sort | uniq
```

它的参数如下。

- `-c`	输出所有的重复行，并且每行开头显示重复的次数。
- `-d`	只输出重复行，而不是不重复的文本行。
- `-f n`	忽略每行开头的 n 个字段，字段之间由空格分隔，正如 sort 程序中的空格分隔符；然而， 不同于 sort 程序，uniq 没有选项来设置备用的字段分隔符。
- `-i`	在比较文本行的时候忽略大小写。
- `-s n`	跳过（忽略）每行开头的 n 个字符。
- `-u`	只是输出独有的文本行。这是默认的。
- `-V` 按照版本号排序。

`-V`参数可以按版本号排列（从小到大）。

```bash
$ sort -V input.txt
1.0.15
1.3.0
2.1.2
3.0.0
```

`-rV`参数可以按版本号逆序排列。

```bash
$ sort -rV input.txt
3.0.0
2.1.2
1.3.0
1.0.15
```

## cut

`cut`程序用来从文本行中抽取文本，并把其输出到标准输出。它能够接受多个文件参数或者标准输入。

它的参数如下。

- `-c char_list` 抽取指定范围的文本
- `-f field_list` 抽取指定字段，字段之间可以tab分隔也可以逗号分隔
- `-d delim_char` 指定字段分隔符，默认是tab键
- `--complement`	抽取整个文本行，除了那些由-c 和／或-f 选项指定的文本。

```bash
# 抽取每行的第三个字段
$ cut -f 3 distros.txt

# 抽取每行的第7到第10个字符
$ cut -c 7-10 distros.txt

# 抽取每行的第23个到结尾的字符1
$ cut -c 23- distros.txt

# 指定字段分隔符为冒号
$ cut -d ':' -f 1 /etc/passwd
```

## paste

`paste`程序将多个文本文件按行合并，即每一行都由原来文本文件的每一行组成，显示在标准输出。

```bash
$ paste distros-dates.txt distros-versions.txt
```

## wc

`wc`命令输出一个文本文件的统计信息（word count），一共有三个值，分别为行数、词数和字节数。

```bash
$ wc ls-output.txt
 7902 64566 503634 ls-output.txt
```

如果使用`-l`参数，则只输出行数。

```bash
$ ls /bin /usr/bin | sort | uniq | wc -l
 2728
```

## head

`head`命令返回文本文件的头部，默认显示10行。

`-n`参数指定显示的行数。

```bash
$ head -n 5 ls-output.txt
```

## tail

`tail`命令返回文本文件的尾部，默认显示10行。

`-n`参数指定显示的行数。

```bash
$ tail -n 5 ls-output.txt
```

`-f`会实时追加显示新增的内容，常用于实时监控日志，按`Ctrl + c`停止。

```bash
$ tail -f /var/log/messages
```

## grep

`grep`程序用于在指定文件之中，搜索符合某个模式的行，并把搜索结果输出到标准输出。

```bash
$ grep keyword foo.txt
```

上面命令输出`foo.txt`之中匹配`keyword`的行。

`grep`程序可以同时搜索多个文件。

```bash
$ grep keyword f*.txt
```

上面命令输出多个文件中匹配`keyword`的行。

`-l`参数输出匹配的文件名，而不是文件行。

```bash
$ grep -l bzip dirlist*.txt
```

如果想搜索文件名，而不是文件内容，可以使用重定向。

```bash
$ ls /usr/bin | grep zip
```

上面命令会输出`/usr/bin`目录中，文件名中包含子字符串`zip`的所有文件。

参数的含义。

- `-c`或`--count` 输出匹配的数量，而不是匹配的文本行。如果使用了`-v`，则输出不匹配的数量。
- `-h`或`--no-filename` 应用于多文件搜索，不在每行匹配的文本前，输出文件名
- `-i`或`--ignore-case` 忽略大小写
- `-l`或`--files-with-matches` 输出包含匹配项的文件名，而不是文本行本身
- `-L`或`--files-without-match` 类似于`-l`，但输出不包含匹配项的文件名
- `-n`或`--line-number` 每个匹配行之前输出其对应的行号
- `-v`或`--invert-match` 只返回不符合模式的行

## sed

`sed`是一个强大的文本编辑工具。

```bash
# 输出前5行
$ sed -n '1,5p' distros.txt

# 输出包含指定内容的行
$ sed -n '/SUSE/p' distros.txt

# 输出不包含指定内容的行
$ sed -n '/SUSE/!p' distros.txt

# 替换内容（只替换第一个）
$ sed 's/regexp/replacement/' distros.txt

# 替换内容（全局替换）
$ sed 's/regexp/replacement/g' distros.txt
```
# 时间管理

## date 命令

`date`命令用于输出当前时间

```bash
$ date
2016年 03月 14日 星期一 17:32:35 CST
```

`date`命令后面用加号（`+`）指定显示的格式。

```bash
$ date +%d_%b_%Y
10_Sep_2018

$ date +%D
09/10/18

$ date +%F-%T
2018-09-10-11:09:51
```

完整的格式参数如下。

- %a 星期名的缩写（Sun）
- %A 星期名的全称（Sunday）
- %b 月份的缩写（Jan）
- %B 月份的全称（January）
- %c 日期和时间（Thu Mar  3 23:05:25 2005）
- %C 世纪，就是年份数省略后两位（20）
- %d 一个月的第几天（01）
- %D 日期，等同于`%m/%d/%y`
- %e 一个月的第几天，用空格补零，等同于`%_d`
- %F 完整的日期，等同于`%Y-%m-%d`
- %g     last two digits of year of ISO week number (see %G)
- %G     year of ISO week number (see %V); normally useful only with %V
- %h   等同于`%b`
- %H   小时（00..23）
- %I   小时（01..12）
- %j     day of year (001..366)
- %k     hour ( 0..23)
- %l     hour ( 1..12)
- %m     month (01..12)
- %M     minute (00..59)
- %N     nanoseconds (000000000..999999999)
- %p     locale’s equivalent of either AM or PM; blank if not known
- %P     like %p, but lower case
- %r     locale’s 12-hour clock time (e.g., 11:11:04 PM)
- %R     24-hour hour and minute; same as %H:%M
- %s     seconds since 1970-01-01 00:00:00 UTC
- %S     second (00..60)
- %T     time; same as %H:%M:%S
- %u     day of week (1..7); 1 is Monday
- %U     week number of year, with Sunday as first day of week (00..53)
- %V     ISO week number, with Monday as first day of week (01..53)
- %w     day of week (0..6); 0 is Sunday
- %W     week number of year, with Monday as first day of week (00..53)
- %x     locale’s date representation (e.g., 12/31/99)
- %X     locale’s time representation (e.g., 23:13:48)
- %y     last two digits of year (00..99)
- %Y     year
- %z     +hhmm numeric timezone (e.g., -0400)
- %:z    +hh:mm numeric timezone (e.g., -04:00)
- %::z   +hh:mm:ss numeric time zone (e.g., -04:00:00)
- %Z     alphabetic time zone abbreviation (e.g., EDT)

## cal 命令

`cal`命令用于显示日历。不带有参数时，显示的是当前月份。

```bash
$ cal
      三月 2016
日 一 二 三 四 五 六
       1  2  3  4  5
 6  7  8  9 10 11 12
13 14 15 16 17 18 19
20 21 22 23 24 25 26
27 28 29 30 31
```
# 用户管理

## id

`id`命令用于查看指定用户的用户名和组名。

```bash
$ id
uid=500(me) gid=500(me) groups=500(me)
```

`id`输出结果分为三个部分，分别是UID（用户编号和用户名）、GID（组编号和组名），groups（用户所在的所有组）。

用户帐户的信息，存放在`/etc/passwd`文件里面；用户组的信息，存放在`/etc/group`文件里面。

```bash
# 返回UID
$ id -u [UserName]

# 返回GID
$ id -g [UserName]

# 返回用户名
$ id -un [UserName]

# 返回组名
$ id -gn [UserName]
```

上面的命令，如果省略用户名，则返回当前用户的信息。

## su

`su`命令允许你以另一个用户的身份，启动一个新的 shell 会话，或者是以这个用户的身份来发布一个命令。

```bash
$ su otherUser
```

执行上面的命令以后，系统会提示输入密码。通过以后，就以另一个用户身份在执行命令了。

如果不加用户名，则表示切换到root用户。

```bash
$ su
```

`-l`参数表示启动一个需要登录的新的Shell，这意味着工作目录会切换到该用户的主目录。它的缩写形式是`-`。

```bash
$ su -
```

上面命令表示，切换到root用户的身份，且工作目录也切换到root用户的主目录。

`-c`参数表示只以其他用户的身份，执行单个命令，而不是启动一个新的Session。

```bash
$ su -c 'command'

# 实例
$ su -c 'ls -l /root/*'
```

## sudo

`sudo`命令很类似`su`命令，但有几点差别。

- 对于管理员来说，`sudo`命令的可配置性更高
- `sudo`命令通常只用于执行单个命令，而不是开启另一个Session。
- `sudo`命令不要求超级用户的密码，而是用户使自己的密码来认证。

`sudo`的设置在文件`/etc/sudoers`之中。

`-l`参数列出用户拥有的所有权限。

```bash
$ sudo -l
```

## chown

`chown`命令用来更改文件或目录的所有者和用户组。使用这个命令需要超级用户权限。

```bash
$ chown [owner][:[group]] file
```

下面是一些例子。

```bash
# 更改文件所有者
$ sudo chown bob foo.txt

# 更改文件所有者和用户组
$ sudo chown bob:users foo.txt

# 更改用户组
$ sudo chown :admins foo.txt

# 更改文件所有者和用户组（用户 bob 登录系统时，所属的用户组）
$ sudo chown bob: foo.txt
```

## chgrp

`chgrp`命令更改用户组，用法与`chown`命令类似。

## useradd

`useradd`命令用来新增用户。

```bash
$ useradd -G admin -d /home/bill -s /bin/bash -m bill
```

上面命令新增用户`bill`，参数`-G`指定用户所在的组，参数`d`指定用户的主目录，参数`s`指定用户的 Shell，参数`m`表示如果该目录不存在，则创建该目录。

## usermod

`usermod`命令用来修改用户的各项属性。

```bash
$ usermod -g sales jerry
```

上面的命令修改用户`jerry`属于的主要用户组为`sales`。

```bash
$ usermod -G sales jerry
```

上面的命令修改用户`jerry`属于的次要用户组为`sales`。

## adduser

`adduser`命令用来将一个用户加入用户组。

```bash
$ sudo adduser username grouptoadd
```

## groupadd

`groupadd`命令用来新建一个用户组。

```bash
$ sudo groupadd group1
$ sudo adduser foobar group1
```

## groupdel

`groupdel`命令用来删除一个用户组。

```bash
$ sudo groupdel group1
```

## passwd

`passwd`命令用于修改密码。

```bash
# 修改自己的密码
$ passwd

# 修改其他用户的密码
$ sudo passwd [user]
```
