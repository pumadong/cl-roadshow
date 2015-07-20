package com.cl.roadshow.model;

import java.util.Date;

public class Student {

    private Integer id;
	private String name;
	private Integer teacherId;
	private Integer score;
	private String createPerson;
	private Date createDate;

	/**
	 * 必须有缺省构造函数：SpringMVC通过Model获取值及Mybatis都需要
	 */
	public Student() {
	}
	
	public Student(String name) {
	    this.name = name;
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

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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
}
