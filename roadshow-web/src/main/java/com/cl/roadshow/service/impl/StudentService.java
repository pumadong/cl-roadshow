package com.cl.roadshow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.roadshow.mapper.StudentMapper;
import com.cl.roadshow.model.Student;
import com.cl.roadshow.service.IStudentService;

@Service
public class StudentService implements IStudentService {

	@Autowired
	private StudentMapper studentMapper;

	@Override
	public Student getStudentByName(String name) {
		return studentMapper.getStudentByName(name);
	}
}
