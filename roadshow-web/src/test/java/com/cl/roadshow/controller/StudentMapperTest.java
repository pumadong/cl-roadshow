package com.cl.roadshow.controller;

import java.util.ArrayList;
import java.util.Date;
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
    
    @Test
    public void getStudentListByIds() {
        @SuppressWarnings("serial")
		List<Student> students = studentMapper.getStudentListByIds(new ArrayList<Integer>(){{add(1);add(2);add(3);}}, 2);
        System.out.println("===================================");
        for(Student s : students) {
            System.out.println(JSON.toJSONString(s));
        }
        System.out.println("===================================");
    }
    
    @Test
    public void getStudentListLessId() {
		List<Student> students = studentMapper.getStudentListLessId(10);
        System.out.println("===================================");
        for(Student s : students) {
            System.out.println(JSON.toJSONString(s));
        }
        System.out.println("===================================");
    }
    
    @Test
    public void batchInsert() {
		List<Student> students = new ArrayList<Student>();
		Student student1 = new Student();
		student1.setCreateDate(new Date());
		student1.setCreatePerson("test");
		student1.setName("student1");
		student1.setScore(100);
		student1.setTeacherId(1);
		Student student2 = new Student();
		student2.setCreateDate(new Date());
		student2.setCreatePerson("test");
		student2.setName("student2");
		student2.setScore(100);
		student2.setTeacherId(1);
		
		students.add(student1);
		students.add(student1);
		
		int result = studentMapper.batchInsert(students);
		
        System.out.println("===================================");
        System.out.println(result);
        System.out.println("===================================");
    }
}
