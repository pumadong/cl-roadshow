package com.cl.roadshow.thrift.demo;

import org.apache.thrift.TException;

public class StudentServiceImpl implements StudentService.Iface {

    /**
     * http://blog.csdn.net/puma_dong/article/details/9837005#t7
     */
    public StudentResponse getStudentResponse()  throws TException { 
        //return new StudentResponse();
        return null;
    }
    
    /**
     * http://blog.csdn.net/puma_dong/article/details/9837005#t7
     */
    public StudentResponseRequired getStudentResponseRequired()  throws TException { 
        return new StudentResponseRequired();
    }

}
