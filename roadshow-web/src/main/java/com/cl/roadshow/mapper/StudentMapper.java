package com.cl.roadshow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.cl.roadshow.model.Student;
import com.cl.roadshow.model.StudentSearchModel;

public interface StudentMapper {

	/**
	 * 根据name查询单个Student
	 *
	 * @param name
	 * @return
	 */
	Student getStudentByName(@Param("name") String name);

	/**
	 * 根据name模糊查询多个Person
	 * 
	 * @param name
	 * @return
	 */
	List<Student> getStudentListByName(@Param("name") String name);
	
    /**
     * 根据条件查询学生总数
     * @param searchModel
     * @return
     */
    Integer getStudentTotalBySearch(StudentSearchModel searchModel);
    
    /**
     * 根据条件查询学生List
     * @param searchModel
     * @return
     */
    List<Student> getStudentListBySearch(StudentSearchModel searchModel,RowBounds rowBounds);
}
