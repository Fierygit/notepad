---
title: JVM
date: 2018-05-27
categories: ["java"]
tags: ["JVM", "java"]
---

# <center>JVM</center>

i do not love java at all!



### 内存机制



#### 运行时数据区域

- 程序计数器

线程私有，执行native方法时为空！ 不会OutOfMemoryError！

- java虚拟机栈

线程私有，存放局部变量表，动态连接，返回信息等，每一个方法对应一个栈帧!   

OutOfMemoryError （扩展时无法申请）    OutOfMemoryError(超过允许最大深度)

- 本地方法栈

与虚拟机栈类似， 只是执行本地方法的栈！

- java堆

**唯一目的**： 存放对象实例； 几乎所有的对象实例都在这里分配内存， gc的主要区域！

- 方法区

与堆一样，放**共享数据**， 加载的类信息（存放类的版本，信息，方法，接口等），常量， 静态变量等

- 运行时常量池

属于方法区的一部分， 出了上述的， 还需存放**编译时**生成的字面量和符号引用

- 直接内存

不在堆， 而是直接使用物理内存（使用unsafe 类分配）

#### 对象的创建

- 检查是否被加载
- 分配内存
- 初始化0值
- 设置对象的信息（如是哪个类的实例等等）
- 调用 用户或者默认的< init > 方法



#### 对象的访问

- 通过句柄，将实例数据独立出来， （实例数据比类型数据多了初始化信息等）

![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200221224645.png)

- 直接指针访问

![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200221224710.png)

这两种对象访问方式个有优势，使用句柄来访问的最大好处就是reference中存储的是稳定的句柄地址，在对象被移动（垃圾收集时移动对象是非常普遍的）时只会改变句柄中的实例数据指针，而reference本身不需要修改。

#### 测试代码

- java堆溢出(无限创建变量)

```java
/**
 * @author Firefly
 * @version 1.0
 * @date 2020/2/22 9:20
 * VM args: -Xms20m -Xmx20m
 */
public class testheap {
    static class test {
    }
    public static void main(String[] args) {
        ArrayList<test> tests = new ArrayList<>();
        while (true) {
            tests.add(new test());
        }
    }
}
// Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
```

- 栈溢出(递归调用函数)

```java
public class Teststack {
    private int len = 1;
    public void test(){
        len++;
        test();
    }
    public static void main(String[] args) {
        Teststack t = new Teststack();
        t.test();
    }
}
// Exception in thread "main" java.lang.StackOverflowError
```

栈的内存溢出可以通过建立线程设置得到！

- 常量池溢出

测试不出。。



### 垃圾回收















### 类文件结构



#### class文件的数据结构

javac 生成的是java字节码， 相当于编译原理的前段， javac 没有对代码进行优化， 字节码相当于是一种中间数据结构， 其它语言也可以通过前段编译器编译出字节码， 优化在jvm。

首先是一段代码：

![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200211101432.png)

javac 编译后生成如下的字节码的一部分：

![](https://raw.githubusercontent.com/Fierygit/picbed/master/image-20200211103302553.png)

- 魔数

前4个字节 CAFABABE 表示魔数， 大多数文件都有魔数 ， 很多java书都是一杯咖啡！

- 接下来 4 个字节为 版本号， 我用1.8 编译的 所以`00 00 00 37` 表示8，

- 接下来是 常量池

由于java使用了虚拟机， 因此没有连接这一步骤， 在class 加载的时候动态连接，只需要保存名字。

图中` 00 21` 表示 常量池的长度， 表示接下来21 个字节长度为常量池

下图是常量池的数据类型：

![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200211104155.png)

0A 查表表示 method， 由表得知下一字节为索引， 索引类型 0， 即字符串， 然后得到一个utf 常量值！

javap 可以自动帮我们转换， 因此， java是可以反编译的！

javap -verbose TestClass.class  java字节码转换成了人类看的懂得形式

```
Classfile /D:/c/TestClass.class
  Last modified 2020年2月11日; size 474 bytes
  MD5 checksum 2924e1bfe2ffb03842152f3c1e83460e
  Compiled from "TestClass.java"
public class jvm.TestClass
  minor version: 0
  major version: 55
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #5                          // jvm/TestClass   
  super_class: #6                         // java/lang/Object
  interfaces: 0, fields: 1, methods: 3, attributes: 1        
Constant pool:
   #1 = Methodref          #6.#19         // java/lang/Object."<init>":()V
   #2 = Fieldref           #5.#20         // jvm/TestClass.a:I
   #3 = Fieldref           #21.#22        // java/lang/System.out:Ljava/io/PrintStream;
   #4 = Methodref          #23.#24        // java/io/PrintStream.println:(I)V
   #5 = Class              #25            // jvm/TestClass
   #6 = Class              #26            // java/lang/Object
   #7 = Utf8               a
   #8 = Utf8               I
   #9 = Utf8               <init>
  #10 = Utf8               ()V
  #11 = Utf8               Code
  #12 = Utf8               LineNumberTable
  #13 = Utf8               getA
  #14 = Utf8               ()I
  #15 = Utf8               main
  #16 = Utf8               ([Ljava/lang/String;)V
  #17 = Utf8               SourceFile
  #18 = Utf8               TestClass.java
  #19 = NameAndType        #9:#10         // "<init>":()V
  #20 = NameAndType        #7:#8          // a:I
  #21 = Class              #27            // java/lang/System
  #22 = NameAndType        #28:#29        // out:Ljava/io/PrintStream;
  #23 = Class              #30            // java/io/PrintStream
  #24 = NameAndType        #31:#32        // println:(I)V
  #25 = Utf8               jvm/TestClass
  #26 = Utf8               java/lang/Object
  #27 = Utf8               java/lang/System
  #28 = Utf8               out
  #29 = Utf8               Ljava/io/PrintStream;
  #30 = Utf8               java/io/PrintStream
  #31 = Utf8               println
  #32 = Utf8               (I)V
```



- 访问标志

常量池结束后就是 访问标志了，

![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200211105542.png)

vscode 插件 hex for dump 方便就找到了常量池的最后一个符号， 然后在往后两个字节  .! 的utf编码值00 21 ,后面的为索引索引常量的名字，得到类名， 父类名， 方法属性的访问权限的的等等，这一部分就不读了， 没有必要记住， 知道字节码是存放什么就行了。



#### jvm字节码指令简介

上面部分了解了class文件的数据结构， 在编译原理上可以通过 词法分析， 语法分析， 语义分析生成得到。

jvm的指令只有一个字节，所以查看返汇编的代码， 很多操作都是只有一个操作码， 没有操作数，jvm是面向操作数栈而不是寄存器的架构， 操作数在栈里！ 

```c
do{
    自动计算PC寄存器加1；
    根据PC寄存器的指示位置， 从字节码流中取出操作码
    if(字节码存在操作数)  从字节码中取出操作数
     执行操作码所定义的操作
} while( 字节码长度 > 0);
```



- 加载存储指令

load， store  ，  push  对应的数值类型有  i a l等

- 运算指令

add， sub， mul， div， rem， neg， shl， or， and， xor， inc， cmpg

注意的是：

1. 只有除数为零的时候才会抛出ArithmeticException 异常，其它不应该抛出异常
2. 浮点数舍入选择最近的数字， 浮点数到整形选择 向下舍入
3. 处理浮点数不会报错

- 对象的创建和访问

  创建对象： new	创建数组： newarray

- 异常处理指令

通过异常表来跳转

- 同步指令， 通过 管程实现  monitorEnter monitorExit



#### 虚拟机类加载机制

java 天生可以动态扩展的语言特性就是依赖运行期动态加载和动态连接这个特点实现的。

类的生命周期分为7个阶段 加载 - 验证 - 准备 - 解析 - 初始化 - 使用 - 卸载

只有5种情况会立即初始化：

1. new， getstatic， putstatic， invokestatic
2. 使用 reflect 包进行反射调用
3. 初始化时父类还没初始化
4. 需要一个main
5. methodHandle

例子：

1)被动引用才会初始化

```java
public class SUperClass{
    static{
        System.out.println("SuperClass init!");
    }
    public static value int value = 123;
}
public class SubClass enxtends SuperClass{
    static{
        System.out.println("SubClass init!");
    }
	public static class void main(String[] args){
        System.out.println(SubClass.value);
    }
}
```

上面代码父类被引用了， 所以运行后父类会初始化

2) 数组对象的初始化不会初始化对象

```java
public static void main(String[] args){
    SuperClass sca = new SuperClass[10];
}
```

上面的代码不会调用  SuperCLass的构造函数， 会创建一个内置的数组对象类， 并封装了越界检查，在指令上。

3） 常量的引用

```java
public calss  ConstClass{
    static {
        System.out.println("ConstClass init!");
    }
    public static final String HELLOWORLD = "hello world!";
	public static void main(String args[]){
		System.out.println(ConstClass.HELLOWORLD);
    }
}
```

上面的代码不会调用构造函数！而是放到常量池



#### 类加载的过程

- 加载

1. 通过一个类的全限定名来获取定义此类的二进制字节流
2. 将这个字节流所表示的静态存储结构转化为方法区的运行时数据结构
3. 在内存中生成一个代表这个类的Class 对象， 作为方法区这个类的各种数据的访问入口

字节码的获取可以来自任何地方

- 验证

相当于编译原理的 语义分析， 检查类型时候正确等等

如果自己的第三方包已经反复测试没有问题， 可以使用 -Xverify:none关闭验证，加快加载速度

- 准备

为类变量分配内存并且设置类变量的阶段。

public static int value = 123， 这个变量会设置初始值0，到方法区。

而类的**实例变量**，会随对象初始化在堆中， ```pirvate int value``` 

还有一个特殊情况，  `public static final int value = 123`，加了final的变量会初始化为123



- 解析   v

将常量池的符号引用替换为直接的内存引用， 相当于java自动帮程序员转换指针， 因此程序员获取不到指针。

直接引用： 一个指向目标的指针， 相对偏移量或是一个能间接定位到目标的句柄

符号引用 ：一组符号描述目标

这一部分还涉及到语义分析的过程， 当一个变量重复定义的情况等等。



- 初始化

开始执行java程序（字节码）

调用  < cinit >() 方法：

1. 收集 static{}, 此时只可以赋值， 不可以访问， 访问会报 非法向前引用
2. 不用调用父类的构造函数， jvm确保已经初始化
3. 后面的值会覆盖前面的值
4. 如果没有静态语句块， 不会生成这个方法
5. jvm 默认保证这个方法是线程安全的





#### 类加载器

类加载的获取二进制文件的方法放到了类加载器中实现！



比较两个类是否相等，只有在这两个类是由==同一个类加载器==加载的前提下才有意义！



##### 双亲委派模型

![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200213105357.png)

- 启动类在  <java_home>\lib 目录下

- 扩展类在 <java_home>\lib\ext 目录下

- 应用类用户目录下



工作过程： 如果一个类加载器收到了类加载的请求， 它首先不会自己去尝试加载这个类，而是把这个请求**委派**给父加载器加载， 当父加载器不能加载这个类的时候才自己加载



作用： 使得类的加载有优先层次关系， 会首先加载内置的类， 如果重名就加载不了！



### 虚拟机字节码执行引擎

虚拟机为不同的物理机提供统一的外观！

#### 栈帧结构

- 局部变量表

用于存放方法参数 和 局部变量， 如果局部变量是一个类， 则存储它的引用。

- 操作数

后入先出的栈（广义上的栈）， jvm执行指令时， 操作数就在栈里， 所谓基于栈的执行引擎的栈， 就是这里的栈。

- 动态连接

指向运行时常量池中改栈帧所属方法的引用，持有这个引用是为了支持方法调用过程中的动态链接。

- 方法返回地址

存放返回的地址， 异常不会使用



#### 方法调用

在解析的时候， 满足条件的有： 编译期可知， 运行期不可变， 有静态方法和私有方法两种！叫做解析调用

分派（dispatch）调用可以是静态的也可以是动态的

- 静态分派（Method overload Resotion） （方法重载）

```java
public  class TestStaticDispatcher {
    static abstract class  Human{}
    static class Man extends Human{}
    static class Woman extends Human{}
    static void  sayHello(Human human){System.out.println("hey guy");}
    static void  sayHello(Man human){System.out.println("hey man");}
    static void  sayHello(Woman human){System.out.println("hey human");}

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Man();
        new TestStaticDispatcher().sayHello(man);
        new TestStaticDispatcher().sayHello(woman);
    }
}
```

```java
//实际类型变化
Human man = new Man();
man = new Woman();
//静态类型变化
sr,sayHello((Man)man);// 确定的
```



- 动态分派 （重写）





















































