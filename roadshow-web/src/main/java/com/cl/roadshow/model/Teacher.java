package com.cl.roadshow.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Teacher {

    private Integer id;
	private String name;
	private String createPerson;
	private Date createDate;
	private Set<Student> students = new HashSet<Student>();

    /**
     * 必须有缺省构造函数：SpringMVC通过Model获取值及Mybatis都需要
     */
	public Teacher() {
	}
	
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
