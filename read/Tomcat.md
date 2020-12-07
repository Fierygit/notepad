---
title: Tomcat 学习笔记
date: 2020-11-4
categories:  read
---
## Tomcat





### connector







connector 在解析完了http 协议之后会调用 containner 的 invoke 方法！

```java
connector.getContainer().invoke(request, response);
```

接下来看是 container 容器的 invoke 方法

```java
public void invoke(Request request, Response response)
    throws IOException, ServletException {
    pipeline.invoke(request, response);
}
```

下面看容器的实现方法！



### Servlet

servlet 容器是用来处理请求servlet 资源，并为Web 客户端填充response 对象的模块。
servlet 容器是org.apache.catalina.Container 接口的实例。

#### Container 接口

在Tomcat 中，共有4 种类型的容器，分别是： Engine 、Host 、Context 和Wrapper

- Engine, 表示整个Catalina servlet 引擎
- Host: 表示包含有一个或多个Context 容器的虚拟主机
- Context: 表示一个Web 应用程序。一个Contex t 可以有多个Wrapper
- Wrapper: 表示一个独立的servlet 

Tomcat 中的servlet 容器必须要实现org.apache.catalina.Container 接口。

#### 管道任务

管道包含该servlet 容器将要调用的任务。一个阀表示一个具体的执行任务。在servlet 容器
的管道中，有一个基础阀，但是，可以添加任意数量的阀。阀的数量指的是额外添加的阀数量，
即，不包括基础阀。

Tomcat 中使用内部类来实现遍历管道， 引人接口org.apache.catalina.ValveContext。

```java
public class SimplePipeline implements Pipeline, Lifecycle {
  // The basic Valve (if any) associated with this Pipeline.
  protected Valve basic = null;
  // The Container with which this Pipeline is associated.
  protected Container container = null;
  // the array of Valves
  protected Valve valves[] = new Valve[0];

  public void invoke(Request request, Response response)
    throws IOException, ServletException {
    // Invoke the first Valve in this pipeline for this request
   (new StandardPipelineValveContext()).invokeNext(request, response);
  }
  // this class is copied from org.apache.catalina.core.StandardPipeline class's
  // StandardPipelineValveContext inner class.
  protected class StandardPipelineValveContext implements ValveContext {
    protected int stage = 0;
    public String getInfo() {
      return null;
    }
    public void invokeNext(Request request, Response response)
      throws IOException, ServletException {
      int subscript = stage;
      stage = stage + 1;
      // Invoke the requested Valve for the current request thread
      if (subscript < valves.length) {
        valves[subscript].invoke(request, response, this);
      }
      else if ((subscript == valves.length) && (basic != null)) {
        basic.invoke(request, response, this);
      }
      else {
        throw new ServletException("No valve");
      }
    }
  } // end of inner class
}
```

stage 会不断增加，调用所有的value，每个 value调用 invokeNext即可



先看看各个接口：

##### Pipline

对于 Pipeline 接口，首先要提到的一个方法是invoke() 方法， servlet 容器调用invoke(）方法
来开始调用管道中的阀和基础阀。通过调用Pipeline 接口的addValve(）方法，可以向管道中添加
新的阀，同样，也可以调用removeValve(）方法从管道中删除某个阀。最后，调用setBasic(）方法
将基础阀设置到管道中，调用其getBasic(）方法则可以获取基础阀。基础阀是最后调用的阀，负
责处理request 对象及其对应的response 对象。

```java
import javax.servlet.ServletException;
public interface Pipeline (
    public Valve getBasic();
    public void setBasic(Valve valve);
    public void addVa l ve(Valve val ve);
    public Valve [ J get Valves () ;
    public voi d invoke(Request request, Response response)
                  throws IOException, ServletException;
    public void removeValve(Valve valve);
}
```

##### Value

阀是Valve 接口的实例，用来处理接收到的请求。该接口有两个方法， invoke(）方法和  getlnfo(）方法。invoke(）方法已经在前面讨论过了， getlnfo(）方法返回阀的实现信息。

```java
package org.apache.catalina;
import java.io.IOException;
import javax.servlet.ServletException;
public interface Valve {
    public String getInfo();
    public void invoke(Request request, Response response,
                       ValveContext context)
        throws IOException, ServletException;
}
```

##### ValveContext 

该接口有两个方法， invokeNext(）和getlnfo(）。invokeNext(）方法已经在前面讨论过了,getlnfo(）方法会返回ValveContext 的实现信息。

```java
public interface ValveContext {
    public String getlnfo() ;
    public void invokeNext(Request request, Response r esponse)
	 throws IOException, ServletException;
}
```

##### Contained 

阀可以选择是否实现org.apache.catalina.Contained 接口，该接口的实现类可以通过接口中的
方法至多与一个servlet 容器相关联。

```java
package org.apache.catalina ;
public interface Contained{
    public Container getContainer ();
    public void setContainer (Container container) ;
}
```

#### Wrapper

Wrapper 级的servlet 容器是一个org.apache. catalina.Wrapper 接口的实例，**表示一个独立的servlet 定义**。

Wrapper 接口继承自Container 接口，又添加了一些额外的方法。

Wrapper 接口的实现类要负责管理其基础servlet 类的servlet 生命周期，即调用servlet 的init(）、service(）、destroy(）等方法。由千Wrapper 已经是最低级的servlet 容器了，因此不能再向其中添加子容器。若是Wrapper 的addChild(）方法被调用，则抛出IllegalArgumantException 异常。

Wrapper 接口中比较重要的方法是load(） 和allocate(）方法。allocate(）方法会分配一个已
经初始化的servlet 实例， 而且 allocate(）方法还要考虑下该servlet 类是否实现了javax .servlet.
SingleThreadModel 接口。load(）方法载人并初始化servlet 类。





### Loader



