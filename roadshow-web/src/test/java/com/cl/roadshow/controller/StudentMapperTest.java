package com.cl.roadshow.controller;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.cl.roadshow.mapper.StudentMapper;
import com.cl.roadshow.model.Student;
import com.cl.roadshow.model.StudentSearchModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class StudentMapperTest {

	@Autowired
	private StudentMapper studentMapper;

	@Test
	public void getStudentByName() {
		String name = "张三";
		System.out.println("studentMapper.getStudentByName(name):" + studentMapper.getStudentByName(name));
	}
	
    @Test
    public void getStudentTotalBySearch() {
        StudentSearchModel searchModel = new StudentSearchModel();
        searchModel.setName("张三");
        System.out.println("studentMapper.getStudentTotalBySearch(searchModel):" + studentMapper.getStudentTotalBySearch(searchModel));
    }
    
    @Test
    public void getStudentListBySearch() {
        StudentSearchModel searchModel = new StudentSearchModel();
        List<Student> students = studentMapper.getStudentListBySearch(searchModel,
                new RowBounds((searchModel.getPageNo() - 1) * searchModel.getPageSize(), searchModel.getPageSize()));
        System.out.println("===================================");
        for(Student s : students) {
            System.out.println(JSON.toJSONString(s));
        }
        System.out.println("===================================");
    }
}
