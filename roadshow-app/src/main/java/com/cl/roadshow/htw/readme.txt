
前言


《How Tomcat Works》，2004年出版。
内容：剖析了Tomcat 4.1.12和Tomcat 5.0.18--一个免费的、开源的、深受大众欢迎的、代号为Catalina的servlet容器，并讲解其容器的内部运行机制。通过迭代实现一个简化版软件来讲述相应原理。
作者：Budi Kurniawan & Paul Deck，是非常有经验的咨询师和架构师。
中文版：2012年出版，名为《深入剖析 Tomcat》，曹旭东译，目录介绍如下：http://www.cnblogs.com/hzbook/archive/2011/12/31/2308670.html
原书已经有很好的目录介绍，及评论，我写这篇文章，是在阅读本书的过程中，从一些其他角度，谈谈感想，聊聊技术。同时如果能看得懂，尽量看英文版吧，也是很简单易懂，没什么难的。
看这本书，配合着这本书自带的源码来看，是很好的过程，可运行源码已经集成到Maven项目：https://github.com/pumadong/cl-roadshow
注意一点：这本书，每把一章的源码演示完毕之后，其实相当于实现了Tomcat中功能的简化版，到了下一章，就会直接用Tomcat的功能代替这个实现，来进行下一个功能的演示。

感想


技术

从高层到底层，从业务应用到通用框架，最后都会回到底层上来，底层的东西，更像技术。越往高层，业务占得比重越大，越容易脱离技术本身。
底层的东西有什么呢？比如：
1、Socket，这是TCP的实现，一般来说，任何操作系统，都会实现，任何语言，都会封装到基本框架中，比如JDK的ServerSocket和Socket
2、协议，各种应用协议，通讯协议，这是计算机世界的标准，就像人类世界的法律，规定了我们行动的条条框框
3、数据结构，任何一种语言，必然一定会实现多种数据结构，适用于各种场景
4、算法，计算方法，通用的算法是为了效率；特定场景的算法，是为了解决特殊的问题
5、通讯，不只网络之间，CPU和内存和硬盘，也是通讯，其实就是数据流的传输，byte、bit
设计

作为一个世界范围广泛使用的强大框架，Tomcat必然有非常多的设计思想、设计模式，让我们学习。
不看这种包含设计思想的框架，只看关于设计的书籍，只做业务应用系统的开发，比较难提高设计能力，因为，业务系统更关注业务问题，可能比较常会遇到大数据、高并发类问题的解决，但是设计思想很少。

图书目录


Chapter 1 : A Simple Web Server


这个章节，简单的通过 JDK 中的 ServerSocket ，监听某个端口， 然后在浏览器输入 URL ，神奇的 出现了一个 网页，就像访问某个网站一样。
其实也没什么神奇的，过程如下：
1、首先，JDK帮我们封装了TCP的实现，ServerSocket/Socket，这样，我们就可以开一个端口来监听 打到这个端口的数据
2、其次，当我们浏览器输入URL时，浏览器对于Http://IP:PORT/...，遵从 HTTP 这种应用层协议，按照这种协议规定的格式，组成一个字符串，打到目标主机和端口
3、ServerSocket 拿到数据之后，按照 HTTP 协议规定的格式进行解释，然后使用 File 类，从硬盘上读文件，返回个调用方
4、调用方接收到数据后，在浏览器上把HTML渲染出来

这一章总结为一句话：协议，都是协议。

Chapter 2：A Simple Servlet Container


这一章，又对前一章进行了加工，实现了一个 Servlet 容器。你是否会疑惑：天啊，Servlet 容器，这么容易实现吗？难道不是需要高深负责的数吗？
过程如下：
1、根据URI，判断，静态还是Servlet，在实际的生产中，我们的Servlet其实也都是配置在配置文件里面，告诉 Servlet 容器的，不然，容器不会知道
2、如果是 Servlet，则走 Servlet 处理步骤，通过 JDK 的类装载器，从磁盘目录把类文件装置进来，然后实例化这个类，处理ServetRequest，返回ServetResponse
另外本章，还用一个具体的场景，教我们 Facade（门面）（相关链接：http://blog.csdn.net/puma_dong/article/details/23107263）这种设计模式如何使用。

这一章总结为一句话：遵从规范（Servlet），利用JDK本身。基础，都是基础。
设计模式：Facase（门面）。

Chapter 3：Connector


这一章，介绍了Catalina的两个主要模块之一，连接器。本章对于Http协议Head的大部分，进行了解析，我们可以看到，协议解析的过程，实际也是根据既定的Http协议规范，把字符串解析后，转换成 Request 对象的过程。
另外，本章，对于 多种语言 错误提示信息的读取，使用了一种“多例”模式（相关链接：http://blog.csdn.net/puma_dong/article/details/22597085 ），这种场景，也是多例模式的典型应用场景

这一章总结为一句话：模块化的职能划分产出了连接器。高层协议的解析很大部分就是字符串解析，底层协议的解析很大部分是字节解析。
设计模式：多例。

Chapter 4: Tomcat Default Connector


这一章，介绍了Tomcat缺省的连接器的实现，Connector包含两种主要的线程：Connector Thread和Processor Thread，一个Connector Thread，用于进行ServerSocket监听，维护一个Processor Thread的线程池，多个Processor Thread，用来解析HTTP协议，把处理的结果返回给客户端。
Connector Thread和Processor Thread之间，通过Wait/Notify系统进行通讯，Connection Thread接收Http请求，是串行的，但是当把一个Http请求分配给一个Process Thread成功之后，马上返回，然后进入下一个Http请求的处理。

这一章总结为一句话：Connector分为两个模块，维护两类线程，对于比较耗时的Http解析，维护一个线程池，在线程之间，通过Wait/Notify系统进行通讯。

Chapter 5: Container


容器，是Tomcat处理Request、填充Response的重要组件，我们经常把Tomcat、Jetty等称为 Servlet 容器，也是因为这是Tomcat最重要、最复杂的组件。
容器层次结构，分为4层：
1、Engine（Represents the entire Catalina servlet engine）
2、Host（Represents a virtual host with a number of contexts）
3、Context（Represents a web application. A context contains one or more wrappers.）
4、Wrapper（Represents an individual servlet）
在一个容器里面，还有两个重要的接口：Pipline和Valve，管线和阀门；这两个概念，可以类比我们应用开发中用到的：Filter Chain和Filter。

这一章总结为一句话：Container是Tomcat中，除了Connector之外，第二重要的模块。

Chapter 6: Lifecycle


生命周期接口，提供一种统一的管理对象生命周期的接口，一些其他框架也使用了类似的思想（比如Spring）。
比如，Catalina，有很多组件，Catalina启动时，这些组件要启动，停止时，这些组件要清理资源，于是，通过Lifecycle、LifecycleListener、LifecycleEvent，Catalina实现了两种统一的启动和停止的方式：
1、显示的调用：所有的子容器、关联类，只要实现了Lifecycle接口，就显示的调用其start()、stop()方法
2、观察者模式：所有的监听类，只要实现了LifecycleListener，可以在容器触发事件时，监听事件并处理

这一章总结为一句话：生命周期接口，提供了一种统一的启动和关闭的方式。
设计模式：观察者。
https://github.com/pumadong/cl-roadshow/blob/master/roadshow-app/src/main/java/com/cl/roadshow/designpattern/behavior/ObserverDemo.java

Chapter 7: Logger
Chapter 8: Loader
Chapter 9: Session Management
Chapter 10: Security
Chapter 11: StandardWrapper
Chapter 12: StandardContext
Chapter 13: Host and Engine
Chapter 14: Server and Service
Chapter 15: Digester
Chapter 16: Shutdown Hook
Chapter 17: Tomcat Startup
Chapter 18: Deployer
Chapter 19: Manager Servlet
Chapter 20: JMX-Based Management