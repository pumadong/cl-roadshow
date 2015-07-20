package com.cl.roadshow.mapper;

import org.apache.ibatis.annotations.Param;
import com.cl.roadshow.model.Teacher;

public interface TeacherMapper {

	/**
	 * 根据name查询单个Teacher
	 *
	 * @param name
	 * @return
	 */
	Teacher getTeacherByName(@Param("name") String name);

}
