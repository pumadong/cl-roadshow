package com.cl.roadshow.java.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * XStream方式进行XML和Object互换
 *
 * 参考网址：http://xstream.codehaus.org/
 *
 */
public class XStreamDemo {
	public static void main(String[] args) {
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Student><AGE>22</AGE><score><math>100</math></score></Student>";
		XStream xstream = new XStream();

		// xstream.aliasPackage("", "com.cl.roadshow.java.xml");

		// 把Student节点转换为StudentX对象
		xstream.alias("Student", StudentX.class);
		xstream.alias("Score", Score.class);

		xstream.aliasField("AGE", StudentX.class, "age");

		StudentX s = new StudentX();
		s.setAge("11");
		System.out.println(xstream.toXML(s));

		System.out.println("--------------------------------------");

		Object object = xstream.fromXML(xmlStr);
		if (StudentX.class.isInstance(object)) {
			StudentX studentX = (StudentX) object;
			System.out.println(studentX.getAge());
			System.out.println(studentX.getScore().getMath());
		} else {
			System.out.println("null");
		}
	}
}

class StudentX {

	// 注意，这个注解如果要生效，则需要注解解释器
	// 别名注解，这个别名就是XML文档中的元素名，Java的属性名不一定要与别名一致
	@XStreamAlias("AGE")
	private String age;
	private Score score;

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

}

class Score {
	private Integer math;

	public Integer getMath() {
		return math;
	}

	public void setMath(Integer math) {
		this.math = math;
	}
}