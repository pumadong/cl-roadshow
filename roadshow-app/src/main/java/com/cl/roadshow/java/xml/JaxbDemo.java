package com.cl.roadshow.java.xml;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB（Java Architecture for XML Binding) 是一个业界的标准，是一项可以根据XML Schema产生Java类的技术
 * 
 * 参考网址：http://hbiao68.iteye.com/blog/1958413
 *
 */
public class JaxbDemo {

	public static void main(String[] args) {
		beanToXML();
		System.out.println();
		XMLStringToBean();
	}

	public static void beanToXML() {
		Classroom classroom = new Classroom(1, "软件工程", 4);
		Student student = new Student(101, "张三", 22, classroom);

		try {
			JAXBContext context = JAXBContext.newInstance(Student.class);
			Marshaller marshaller = context.createMarshaller();
			System.out.println("--------------------------------------");
			marshaller.marshal(student, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	public static void XMLStringToBean() {
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><student><age>22</age><classroom><grade>4</grade><id>1</id><name>软件工程</name></classroom><id>101</id><name>张三</name></student>";
		try {
			JAXBContext context = JAXBContext.newInstance(Student.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Student student = (Student) unmarshaller.unmarshal(new StringReader(xmlStr));
			System.out.println("--------------------------------------");
			System.out.println(student.getAge());
			System.out.println(student.getClassroom().getName());
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}
}

class Classroom {
	private int id;
	private String name;
	private int grade;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Classroom(int id, String name, int grade) {
		super();
		this.id = id;
		this.name = name;
		this.grade = grade;
	}

	public Classroom() {
		super();
	}

}

@XmlRootElement
class Student {
	private int id;
	private String name;
	private int age;
	private Classroom classroom;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}

	public Student(int id, String name, int age, Classroom classroom) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.classroom = classroom;
	}

	// 无参够着函数一定需要，否则JXBContext无法正常解析。
	public Student() {
		super();
	}
}