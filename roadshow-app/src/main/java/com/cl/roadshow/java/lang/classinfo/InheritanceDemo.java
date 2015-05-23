package com.cl.roadshow.java.lang.classinfo;

/**
 * 这是一个最简单的继承，我们用javac编译，然后用javap解析类文件
 * 
 * 可以看到，编译器在编译子类时，已经把父类的方法继承过来了
 *
 */

public class InheritanceDemo extends InherianceParent {

}

abstract class InherianceParent {
	public void show() {
	};
}

// javap InheritanceDemo.class

// 结果如下：

// Compiled from "InheritanceDemo.java"
// public class com.cl.roadshow.java.lang.classinfo.InheritanceDemo extends
// com.cl.roadshow.java.lang.classinfo.InherianceParent {
// public com.cl.roadshow.java.lang.classinfo.InheritanceDemo();
// public void show();
// }