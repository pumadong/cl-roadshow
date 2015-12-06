/* explains Tomcat's default container */
package com.cl.roadshow.htw.ex04;

import org.apache.catalina.connector.http.HttpConnector;

public final class Bootstrap {
  public static void main(String[] args) {
    HttpConnector connector = new HttpConnector();
    SimpleContainer container = new SimpleContainer();
    connector.setContainer(container);
    try {
      connector.initialize();
      connector.start();
      
      // HttpConnector，Tomcat 4里面具体怎么实现的，不知道，在这个应用感觉，好像只监听了一次Http请求，不过，我们不关心细节，只要了解原理就好了
      // 在第三章我们已经了解了HttpConnector原理，以下是逐渐了解各个组件的原理

      // make the application wait until we press any key.
      System.in.read();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}