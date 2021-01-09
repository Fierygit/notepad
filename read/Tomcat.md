---
title: Tomcat 学习笔记
date: 2020-11-04
categories:  ["read"]
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
      int subscript = st把把age;
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

stage 会不断增加，调用所有的value，每个 value调用 invokeNext 即可



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



![](https://raw.githubusercontent.com/Fierygit/picbed/master/20201214204829.png)





### Loader



### Lifecycle

#### lifecycle接口

```java
/**
 * Common interface for component life cycle methods.  Catalina components
 * may, but are not required to, implement this interface (as well as the
 * appropriate interface(s) for the functionality they support) in order to
 * provide a consistent mechanism to start and stop the component.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.6 $ $Date: 2002/06/09 02:10:50 $
 */

public interface Lifecycle {
    public static final String START_EVENT = "start";
    public static final String BEFORE_START_EVENT = "before_start";
    public static final String AFTER_START_EVENT = "after_start";
    public static final String STOP_EVENT = "stop";
    public static final String BEFORE_STOP_EVENT = "before_stop";
    public static final String AFTER_STOP_EVENT = "after_stop";

    public void addLifecycleListener(LifecycleListener listener);
    public LifecycleListener[] findLifecycleListeners();
    public void removeLifecycleListener(LifecycleListener listener);


    /**
     * Prepare for the beginning of active use of the public methods of this
     * component.  This method should be called before any of the public
     * methods of this component are utilized.  It should also send a
     * LifecycleEvent of type START_EVENT to any registered listeners.
     */
    public void start() throws LifecycleException;


    /**
     * Gracefully terminate the active use of the public methods of this
     * component.  This method should be the last one called on a given
     * instance of this component.  It should also send a LifecycleEvent
     * of type STOP_EVENT to any registered listeners.
     */
    public void stop() throws LifecycleException;
}
```

为了一致性启动与停止

#### LifecycleEvent接口

生命周期的事件监听器是org.apache.catalina.LifecycleListener 接口的实例

```java
/**
 * General event for notifying listeners of significant changes on a component
 * that implements the Lifecycle interface.  In particular, this will be useful
 * on Containers, where these events replace the ContextInterceptor concept in
 * Tomcat 3.x.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.3 $ $Date: 2001/07/22 20:13:30 $
 */

public final class LifecycleEvent   extends EventObject {

    public LifecycleEvent(Lifecycle lifecycle, String type) {
        this(lifecycle, type, null);
    }
    public LifecycleEvent(Lifecycle lifecycle, String type, Object data) {
        super(lifecycle);
        this.lifecycle = lifecycle;
        this.type = type;
        this.data = data
    }

    private Object data = null;
    private Lifecycle lifecycle = null;
    private String type = null;

    public Object getData() {        return (this.data);    }
    public Lifecycle getLifecycle() {        return (this.lifecycle);    }
    public String getType() {        return (this.type);    }
}
```

#### LifecycleListener 接口

```java
/**
 * Interface defining a listener for significant events (including "component
 * start" and "component stop" generated by a component that implements the
 * Lifecycle interface.
 * @author Craig R. McClanahan
 * @version $Revision: 1.3 $ $Date: 2001/07/22 20:13:30 $
 */
public interface LifecycleListener {
    public void lifecycleEvent(LifecycleEvent event);
}
```



![](https://raw.githubusercontent.com/Fierygit/picbed/master/20201214212139.png)



调用链

1、bootstrap

```   ((Lifecycle) connector).start();
((Lifecycle) connector).start();
((Lifecycle) context).start();
```

2、调用 listener， 递归调用子容器的

```
// Notify our interested LifecycleListeners
lifecycle.fireLifecycleEvent(BEFORE_START_EVENT, null);
started = true;
try {
// Start our subordinate components, if any
if ((loader != null) && (loader instanceof Lifecycle))
((Lifecycle) loader).start();
// Start our child containers, if any
Container children[] = findChildren();
for (int i = 0; i < children.length; i++) {
if (children[i] instanceof Lifecycle)
((Lifecycle) children[i]).start();
}
// Start the Valves in our pipeline (including the basic),
// if any
if (pipeline instanceof Lifecycle)
((Lifecycle) pipeline).start();
// Notify our interested LifecycleListeners
lifecycle.fireLifecycleEvent(START_EVENT, null);
```

3、 listenersupport 中回调实现的监听器

```java
public void fireLifecycleEvent(String type, Object data) {
    LifecycleEvent event = new LifecycleEvent(lifecycle, type, data);
    LifecycleListener interested[] = null;
    synchronized (listeners) {
        interested = (LifecycleListener[]) listeners.clone();
    }
    for (int i = 0; i < interested.length; i++)
        interested[i].lifecycleEvent(event);
}
```



### session

Session 接口是作为Catalina 内部的外观类使用的。Session 接口的标准实现StandardSession
类也实现了javax.servlet.http.HttpSession 接口

#### Session

```java
public interface Session {
    public static final String SESSION_CREATED_EVENT = "createSession";
    public static final String SESSION_DESTROYEO_EVENT = "destroySession"
    public String getAuthType();
    public void setAuthType(String authType);
    public long getCreationTime();
    public void setCreationTime(long time);
    public String get I d();
    public void setld(String id);
    public String get Info();
    public long getLastAccessedTime ();
    public Manager getManager (l;
    public void setManager(Manager manager);
    public int getMaxi nactiveinterval();
    public void setMaxinactiveinterval(int interval);
    public void setNew(boolean i sNew);
    public Principal getPrincipal ();
    public void setPri ncipal(Pri ncipal principal);
    public HttpSession getSession();
    public void set Valid (boolean isValid);
    public boolean isValid ();
    public void access();
    public void addSessionListener(SessionList ener listener);
    public void expire ();
    public Object getNote(String name);
    public Iterator getNoteNames ();
    public void recycle() ;
    public void removeNote(String name);
    public void removeSessionListener(SessionListener listener);
    public void setNote(String name, Object value);
 }             
```



#### StandardSession

```java
class StandardSession implements HttpSession, Session, Serializable{}
```

StandardSession 类是Session 接口的标准实现。除了实现javax.servlet.http.HttpSession 接口
和org.apache.catalioa.Session 接口外， StandardSession 类还实现java.lang.Serializable 接口，便千
序列化Session 对象。



#### StandardSessionFacade

```java
public class StandardSessionFacade  implements HttpSession {
    public StandardSessionFacade(StandardSession session) {
        super();
        this.session = (HttpSession) session;
    }

    public StandardSessionFacade(HttpSession session) {
        super();
        this.session = session;
    }

    private HttpSession session = null;
}
```

为了传递一个Session 对象给servlet 实例， Session 被封装在外观类StandardSessionFacade 类，该类仅仅实现了javax.servlet.http.HttpSession 接口中的方法。这样， servlet 程序员就不能将HttpSession 对象向下转换为StandardSession 类型，也阻止了servlet程序员访问一些敏感方法。



#### Manager

```java
public interface Manager {
    public Container get Container ();
    public void setContainer(Container container);
    public DefaultContext getDefaultContext ();
    public void set DefaultContext(DefaultContext defaultContext);
    public boolean getDistributable();
    public void setDistributable(boolean distributable);
    public String getlnfo();
    public int getMaxinactiveinterval ();
    public void setMaxinactiveinterval(int interval);
    public void add(Session session);
    public void addPropertyChangeListener(PropertyChangeListener listener);
    public Session createSession();
    public Session findSession(String id) throws IOException;
    public Session [] findSess ions ();
    public void load() throws ClassNotFoundExcept i on, IOException;
    public void remove(Session session);
    public void removeProperty ChangeListener{PropertyChangeListener listener);
    public void unload() throws IOException;
}                                           
```



#### ManagerBase

```java
public abstract class ManagerBase implements Manager {
    
    public void add(Session session) {
        synchronized (sessions) {
            sessions.put(session.getId(), session);
        }
    }
    
    // 生成唯一id
    protected synchronized String generateSessionId() {
    	
    }
    
}
```

- 该类提供了很多功能方便其子类使用。

- 此外， ManagerBase 类的createSession(）方法会创建一个新的Session对象。每个Session 对象都有一个唯一的标识符，可以通过ManagerBase 类受保护的方法generateSessionld(）方法来返回一个唯一的标识符。

- 某个Context 容器的Session 管理器会管理该Context 容器中所有活动的Session 对象。这些
  活动的Session 对象都存储在一个名为sessions 的HashMap 变量中：

``````java
protected HashMap sessions = new HashMap ();
``````



#### StandardManager

```java
public class StandardManager  extends ManagerBase
    implements Lifecycle, PropertyChangeListener, Runnable {
    
    
    public void start() throws LifecycleException {
        lifecycle.fireLifecycleEvent(START_EVENT, null);
        started = true;
        // Load unloaded sessions, if any
        load();
        // Start the background reaper thread
        threadStart();
    }

    public void stop() throws LifecycleException {
        lifecycle.fireLifecycleEvent(STOP_EVENT, null);
        started = false;
        // Stop the background reaper thread
        threadStop();
        // Write out sessions
        unload();
        // Expire all active sessions
        Session sessions[] = findSessions();
        for (int i = 0; i < sessions.length; i++) {
            StandardSession session = (StandardSession) sessions[i];
            if (!session.isValid())   continue;
            session.expire();
        }
    }
	
    private void threadStart() {
        threadDone = false;
        threadName = "StandardManager[" + container.getName() + "]";
        // 这里创建 thread， 传入大的this 指针
        thread = new Thread(this, threadName);
        thread.setDaemon(true);
        thread.setContextClassLoader(container.getLoader().getClassLoader());
        thread.start();
    }

    private void threadStop() {
        threadDone = true;
        thread.interrupt();
        thread.join();
        thread = null;
    }
	public void run() {
        // Loop until the termination semaphore is set
        while (!threadDone) {
            threadSleep();
            processExpires();
        }
    }
    
}
```

- unload(）方法，以便将有效的Session对象序列化到一个名为“SESSION.ser" 的文件中，而且每个Context 容器都会产生一个这样的文件。SESSION.ser 文件位千环境变量CATALINA_HOME 指定的目录下的work 目录中。



### Standard Context

在创建了StandardContext 实例后，必须调用其start(）方法来为引入的每个HTTP 请求提供服务。

```java
public class StandardContext extends ContainerBase implements Context {
 	// available 属性表明StandardContext 对象是否可用。 start 时候设置
    private boolean available = false;
    // 表明StandardContext 实例是否正确设置
    private boolean configured = false;
	
    public StandardContext() {  
        super();
        pipeline.setBasic(new StandardContextValve()); //设置 basic
        namingResources.setContainer(this);
    }
    
    public synchronized void start() throws LifecycleException {
        lifecycle.fireLifecycleEvent(BEFORE_START_EVENT, null);
        setAvailable(false);
        setConfigured(false);
        boolean ok = true;
        // Add missing components as necessary
        if (getResources() == null) {   // (1) Required by Loader
            if ((docBase != null) && (docBase.endsWith(".war")))
                setResources(new WARDirContext());
            else  setResources(new FileDirContext());
        }
        if (ok && (resources instanceof ProxyDirContext)) {
            DirContext dirContext =  ((ProxyDirContext) resources).getDirContext();
            if ((dirContext != null)
                && (dirContext instanceof BaseDirContext)) {
                ((BaseDirContext) dirContext).setDocBase(getBasePath());
                ((BaseDirContext) dirContext).allocate();
            }
        }
        if (getLoader() == null) {      // (2) Required by Manager
            if (getPrivileged()) 
                setLoader(new WebappLoader(this.getClass().getClassLoader()));
             else  setLoader(new WebappLoader(getParentClassLoader()));        
        }
        if (getManager() == null) {     // (3) After prerequisites
            setManager(new StandardManager());
        }
        // Initialize character set mapper
        getCharsetMapper();
        // Post work directory
        postWorkDirectory();
        // Reading the "catalina.useNaming" environment variable
        // Binding thread
        ClassLoader oldCCL = bindThread();
        if (ok) {
            addDefaultMapper(this.mapperClass);
            started = true;
            ((Lifecycle) loader).start();
            ((Lifecycle) logger).start();
            // Unbinding thread
            unbindThread(oldCCL);
            // Binding thread
            oldCCL = bindThread();
            ((Lifecycle) cluster).start();
            ((Lifecycle) realm).start();
            ((Lifecycle) resources).start();
            // Start our Mappers, if any
            Mapper mappers[] = findMappers();
            for (int i = 0; i < mappers.length; i++) {
                if (mappers[i] instanceof Lifecycle)
                    ((Lifecycle) mappers[i]).start();
            }
            // Start our child containers, if any
            Container children[] = findChildren();
            for (int i = 0; i < children.length; i++) {
                if (children[i] instanceof Lifecycle)
                    ((Lifecycle) children[i]).start();
            }
            // Start the Valves in our pipeline (including the basic),
            ((Lifecycle) pipeline).start();
            // Notify our interested LifecycleListeners
            lifecycle.fireLifecycleEvent(START_EVENT, null);
            ((Lifecycle) manager).start();
        }
        if (!getConfigured())  ok = false;
        // We put the resources into the servlet context
        if (ok)  getServletContext().setAttribute(Globals.RESOURCES_ATTR, getResources());
        // Binding thread
        oldCCL = bindThread();
        // Create context attributes that will be required
        if (ok)             postWelcomeFiles();        
        // Configure and call application event listeners and filters
        if (ok && !listenerStart())    ok = false;
        if (ok && !filterStart())  ok = false;
        // Load and initialize all "load on startup" servlets
        if (ok)  loadOnStartup(findChildren());
        // Unbinding thread
        unbindThread(oldCCL);
        // Set available status depending upon startup success
        if (ok) {
            setAvailable(true);
        } else {
            stop();
            setAvailable(false);
        }
        lifecycle.fireLifecycleEvent(AFTER_START_EVENT, null);
    }
    
    public void invoke(Request request, Response response)
        throws IOException, ServletException {
        // Wait if we are reloading
        while (getPaused())   Thread.sleep(1000);  
        super.invoke(request, response);
    }
}
```

#### ContainerBase

```java
public abstract class ContainerBase implements Container, Lifecycle, Pipeline {
    public void invoke(Request request, Response response)
        throws IOException, ServletException {
        pipeline.invoke(request, response);
    }
}
```

#### Context

```java
// 定义 context 的一些接口， 
public interface Context extends Container {}
```

#### Container

```java
// 定义容器最基本的接口方法
public interface Container {}
```



### Server

Server 接口的实例表示Catalina 的整个servlet 引擎，囊括了所有的组件。服务器组件是非常有用的，因为它使用了一种优雅的方法来启动／关闭整个系统，不需要再对连接器和容器分别启动／关闭。

1. 启动类

```java
Service service = new StandardService();
service.setName("Stand-alone Service");
Server server = new StandardServer();
server.addService(service);

service.addConnector(connector);
//StandardService class's setContainer will call all its connector's setContainer method
service.setContainer(engine);

// Start the new server
if (server instanceof Lifecycle) {
    try {
        server.initialize();//****************************************
        ((Lifecycle) server).start();//********************************
        server.await();
        // the program waits until the await method returns,
        // i.e. until a shutdown command is received.
    }
    catch (LifecycleException e) {
        e.printStackTrace(System.out);
    }
}
```

2. 

```java
public final class StandardServer implements Lifecycle, Server {
    public void initialize() throws LifecycleException {
        if (initialized)
            throw new LifecycleException (sm.getString("standardServer.initialize.initialized"));
        initialized = true;
        // Initialize our defined Services
        for (int i = 0; i < services.length; i++) {
            services[i].initialize();
        }
    }
    public void start() throws LifecycleException {
        // Validate and update our current component state
        if (started)
            throw new LifecycleException(sm.getString("standardServer.start.started"));
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(BEFORE_START_EVENT, null);
        lifecycle.fireLifecycleEvent(START_EVENT, null);
        started = true;
        // Start our defined Services
        synchronized (services) {
            for (int i = 0; i < services.length; i++) {
                if (services[i] instanceof Lifecycle)
                    ((Lifecycle) services[i]).start();
            }
        }
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(AFTER_START_EVENT, null);
    }
     public void stop() throws LifecycleException {
        // Validate and update our current component state
        if (!started)
            throw new LifecycleException(sm.getString("standardServer.stop.notStarted"));
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(BEFORE_STOP_EVENT, null);
        lifecycle.fireLifecycleEvent(STOP_EVENT, null);
        started = false;
        // Stop our defined Services
        for (int i = 0; i < services.length; i++) {
            if (services[i] instanceof Lifecycle)
                ((Lifecycle) services[i]).stop();
        }
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(AFTER_STOP_EVENT, null);
    }
    
     public void await() {
        // Set up a server socket to wait on
        ServerSocket serverSocket = 
                new ServerSocket(port, 1,InetAddress.getByName("127.0.0.1"));
        // Loop waiting for a connection and a valid command
        while (true) {
            // Wait for the next connection
            Socket socket = serverSocket.accept();      
            // Read a set of characters from the socket
            StringBuffer command = new StringBuffer();
            int expected = 1024; // Cut off to avoid DoS attack
            while (expected < shutdown.length()) {
                if (random == null)
                    random = new Random(System.currentTimeMillis());
                expected += (random.nextInt() % 1024);
            }
            while (expected > 0) {
                int ch = -1;
                try {
                    ch = stream.read();
                } catch (IOException e) {
                    System.err.println("StandardServer.await: read: " + e);
                    e.printStackTrace();
                    ch = -1;
                }
                if (ch < 32)  // Control character or EOF terminates loop
                    break;
                command.append((char) ch);
                expected--;
            }
            // Close the socket now that we are done with it
            socket.close();
            // Match against our command string
            boolean match = command.toString().equals(shutdown);
            if (match) {
                break;
            } else  System.err.println("StandardServer.await: Invalid command '");
        }
        serverSocket.close();
    }
    
}
```



### ShutdownHookDemo

钩子函数， 当 Tomcat 要关闭的时候， 执行最后的关闭函数， 防止 ctrl + c 造成问题！

```java
package basic.thread;
import java.io.IOException;
public class ShutdownHookDemo {
    public void start(){
        System.out.println("demo");
        Runtime.getRuntime().addShutdownHook(new ShutDownHook());
    }

    public static void main(String[] args) throws IOException {
        new ShutdownHookDemo().start();
        System.in.read();
    }
}

class  ShutDownHook extends Thread{
    @Override
    public void run() {
        while (true) {
            System.out.println("sleep for a while");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```























