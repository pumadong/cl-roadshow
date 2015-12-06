package com.cl.roadshow.htw.ex09;

import org.apache.catalina.Connector;
import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Manager;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.session.StandardManager;

public final class Bootstrap {
  public static void main(String[] args) {

    //invoke: http://localhost:8080/myApp/Session

    System.setProperty("catalina.base", System.getProperty("user.dir"));
    Connector connector = new HttpConnector();
    Wrapper wrapper1 = new SimpleWrapper();
    wrapper1.setName("Session");
    wrapper1.setServletClass("SessionServlet");

    Context context = new StandardContext();
    // StandardContext's start method adds a default mapper
    context.setPath("/myApp");
    context.setDocBase("myApp");

    context.addChild(wrapper1);

    // context.addServletMapping(pattern, name);
    // note that we must use /myApp/Session, not just /Session
    // because the /myApp section must be the same as the path, so the cookie will
    // be sent back.
    context.addServletMapping("/myApp/Session", "Session");
    // add ContextConfig. This listener is important because it configures
    // StandardContext (sets configured to true), otherwise StandardContext
    // won't start
    LifecycleListener listener = new SimpleContextConfig();
    ((Lifecycle) context).addLifecycleListener(listener);

    // here is our loader
    Loader loader = new WebappLoader();
    // associate the loader with the Context
    context.setLoader(loader);

    connector.setContainer(context);

    // add a Manager
    Manager manager = new StandardManager();
    context.setManager(manager);

    try {
      connector.initialize();
      ((Lifecycle) connector).start();

      ((Lifecycle) context).start();
      
      // HttpConnector，Tomcat 4里面具体怎么实现的，不知道，在这个应用感觉，好像只监听了一次Http请求，不过，我们不关心细节，只要了解原理就好了
      // 在第三章我们已经了解了HttpConnector原理，以下是逐渐了解各个组件的原理

      // make the application wait until we press a key.
      System.in.read();
      ((Lifecycle) context).stop();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}