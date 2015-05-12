package com.cl.roadshow.mapper;

import org.apache.ibatis.annotations.Param;

import com.cl.roadshow.model.Person;

public interface PersonMapper {

	/**
	 * 根据姓名用户
	 * 
	 * @param name
	 * @return
	 */
	Person getPersonByName(@Param("name") String name);
}
