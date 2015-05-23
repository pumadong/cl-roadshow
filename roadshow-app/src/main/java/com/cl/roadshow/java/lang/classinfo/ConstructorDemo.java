package com.cl.roadshow.java.lang.classinfo;

/**
 * 这是一个最简单的无显示构造函数类，我们用javac编译，然后用javap解析类文件
 * 
 * 可以看到，编译器在编译成字节码时，帮我们生成了默认的构造函数
 *
 */
public class ConstructorDemo {

}

// javap ConstructorDemo.class

// 结果如下：

// Compiled from "ConstructorDemo.java"
// public class com.cl.roadshow.java.lang.classinfo.ConstructorDemo {
// public com.cl.roadshow.java.lang.classinfo.ConstructorDemo();
// }