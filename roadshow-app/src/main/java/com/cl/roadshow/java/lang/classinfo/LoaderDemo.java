package com.cl.roadshow.java.lang.classinfo;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 类加载器、System.getProperty()
 * 
 * http://tracylihui.github.io/2015/09/09/java/Java%E7%9A%84ClassLoader%E5%
 * 8E%9F%E7%90%86/
 * http://www.cnblogs.com/mailingfeng/archive/2012/07/02/2573419.html
 * http://blog.csdn.net/irelandken/article/details/7048817
 * http://blog.csdn.net/kongqz/article/details/3987198
 *
 */

public class LoaderDemo {
	@SuppressWarnings("restriction")
	public static void main(String[] args) {

		System.out.println("BootstrapClassLoader 的加载路径: ");

		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for (URL url : urls)
			System.out.println(url);
		System.out.println("----------------------------");

		// 取得扩展类加载器
		URLClassLoader extClassLoader = (URLClassLoader) ClassLoader
				.getSystemClassLoader().getParent();

		System.out.println(extClassLoader);
		System.out.println("扩展类加载器 的加载路径: ");

		urls = extClassLoader.getURLs();
		for (URL url : urls)
			System.out.println(url);

		System.out.println("----------------------------");

		// 取得应用(系统)类加载器
		URLClassLoader appClassLoader = (URLClassLoader) ClassLoader
				.getSystemClassLoader();

		System.out.println(appClassLoader);
		System.out.println("应用(系统)类加载器 的加载路径: ");

		urls = appClassLoader.getURLs();
		for (URL url : urls)
			System.out.println(url);

		System.out.println("----------------------------");

		// System.getProperty
		System.out.println("System.getProperty: ");
		System.out.println("java_vendor:" + System.getProperty("java.vendor"));
		System.out.println("java_vendor_url:"
				+ System.getProperty("java.vendor.url"));
		System.out.println("java_home:" + System.getProperty("java.home"));
		System.out.println("java_class_version:"
				+ System.getProperty("java.class.version"));
		System.out.println("java_class_path:"
				+ System.getProperty("java.class.path"));
		System.out.println("os_name:" + System.getProperty("os.name"));
		System.out.println("os_arch:" + System.getProperty("os.arch"));
		System.out.println("os_version:" + System.getProperty("os.version"));
		System.out.println("user_name:" + System.getProperty("user.name"));
		System.out.println("user_home:" + System.getProperty("user.home"));
		System.out.println("user_dir:" + System.getProperty("user.dir"));
		System.out.println("java_vm_specification_version:"
				+ System.getProperty("java.vm.specification.version"));
		System.out.println("java_vm_specification_vendor:"
				+ System.getProperty("java.vm.specification.vendor"));
		System.out.println("java_vm_specification_name:"
				+ System.getProperty("java.vm.specification.name"));
		System.out.println("java_vm_version:"
				+ System.getProperty("java.vm.version"));
		System.out.println("java_vm_vendor:"
				+ System.getProperty("java.vm.vendor"));
		System.out
				.println("java_vm_name:" + System.getProperty("java.vm.name"));
		System.out.println("java_ext_dirs:"
				+ System.getProperty("java.ext.dirs"));
		System.out.println("file_separator:"
				+ System.getProperty("file.separator"));
		System.out.println("path_separator:"
				+ System.getProperty("path.separator"));
		System.out.println("line_separator:"
				+ System.getProperty("line.separator"));
	}
}
