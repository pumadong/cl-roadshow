package com.cl.roadshow.json.googlegson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * http://www.programcreek.com/java-api-examples/index.php?api=com.google.gson.ExclusionStrategy
 * org.restlet.ext.json功能演示
 * 
 */
public class GsonHelper {
    
    private static final Logger logger = LoggerFactory.getLogger(GsonHelper.class);
    
    private static final Gson gson = new GsonBuilder().serializeNulls()
            .addSerializationExclusionStrategy(new DefaultExclusionStrategy())
            //.setPrettyPrinting()  会格式化，但是会变成多行，不利于统计问题
            .create();
    
    public static void main(String[] args) {
        
        Student student = new Student();
        student.setAge(11);
        student.setName("张三");
        
        StudentExcluded studentExcluded = new StudentExcluded();
        studentExcluded.setAge(22);
        studentExcluded.setName("李四");
        
        System.out.println(GsonHelper.toJson(student));
        System.out.println(GsonHelper.toJson(studentExcluded));
    }
    public static <T> String toJson(T t) {
        try {
            return gson.toJson(t);
        } catch (Exception e) {
            logger.error("JsonHelpers.toJson Error.", e);
        }
        return null;
    }

    public static  <T> T toObject(String json, Class<T> type) {
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 排除策略
     * 
     */
    static class DefaultExclusionStrategy implements ExclusionStrategy {
        
        /**
         * 
         * 排除字段名是age的
         */
        public boolean shouldSkipField(FieldAttributes field) {
            return "age".equals(field.getName());
        }
        
        /**
         * 
         * 排除类StudentExcluded
         */
        public boolean shouldSkipClass(Class<?> clazz) {
            if(clazz.equals(GsonHelper.StudentExcluded.class)) {
                return true;
            }
            return false;
        }

    }
    
    static class Student {
        
        private String name;
        private Integer age;
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Integer getAge() {
            return age;
        }
        public void setAge(Integer age) {
            this.age = age;
        }
        
    }
    static class StudentExcluded {
        
        private String name;
        private Integer age;
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Integer getAge() {
            return age;
        }
        public void setAge(Integer age) {
            this.age = age;
        }
        
    }
}
