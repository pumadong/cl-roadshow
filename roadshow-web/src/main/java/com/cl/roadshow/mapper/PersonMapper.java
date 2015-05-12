package com.cl.roadshow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cl.roadshow.model.Person;

public interface PersonMapper {

	/**
	 * 根据name查询单个Person
	 *
	 * @param name
	 * @return
	 */
	Person getPersonByName(@Param("name") String name);

	/**
	 * 根据name模糊查询多个Person
	 * 
	 * @param name
	 * @return
	 */
	List<Person> getPersonListByName(@Param("name") String name);
}
