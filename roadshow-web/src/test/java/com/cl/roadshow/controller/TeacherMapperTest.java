package com.cl.roadshow.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.cl.roadshow.mapper.TeacherMapper;

/**
 * 
 * Mybatis级联查询单元测试
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class TeacherMapperTest {

	@Autowired
	private TeacherMapper teacherMapper;

	@Test
	public void getTeacherByName() {
		String name = "王老师";
		System.out.println("studentMapper.getStudentByName(name):" + JSON.toJSONString(teacherMapper.getTeacherByName(name)));
	}
}
