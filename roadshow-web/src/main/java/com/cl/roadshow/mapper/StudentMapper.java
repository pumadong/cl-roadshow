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
    
    /**
     * 根据ID列表查询学生List
     * @param ids
     * @param limit
     * @return
     */
    List<Student> getStudentListByIds(@Param("ids") List<Integer> ids, @Param("limit") Integer limit);
    
    /**
     * 查询小于某个ID的学生列表，注意<的处理
     * @param ids
     * @param limit
     * @return
     */
    List<Student> getStudentListLessId(@Param("id") Integer id);
    
    /**
     * 批量插入
     * @param recordList
     * @return
     */
    int batchInsert(@Param("recordList") List<Student> recordList);
    
    /**
     * 批量存储：插入或者更新
     * @param recordList
     * @return
     */
    int saveBatch(@Param("recordList")List<Student> recordList);
}
