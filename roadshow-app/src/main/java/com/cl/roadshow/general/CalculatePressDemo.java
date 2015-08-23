package com.cl.roadshow.general;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *  计算耗时演示
 * 
 */
public class CalculatePressDemo {

    public static void main(String[] args) {
        final int TIMES = 10000000;
        long start = System.currentTimeMillis();
        
        ExecutorService excutorService = Executors.newFixedThreadPool(20);
        List<Future<Student>> futureList = new ArrayList<Future<Student>>();
        for(int i=0; i<TIMES; i++) {
            Future<Student> futureStudent = excutorService.submit(new Calculate());
            futureList.add(futureStudent);
        }
        
        int i = 0;
        while(futureList.get(i) != null) {
            i++;
            if(i == TIMES) {
                break;
            }
        }
        excutorService.shutdown();
        
        long end = System.currentTimeMillis();
        
        System.out.println("time consuming " + (end - start));
    }
    
    static class Calculate implements Callable<Student> {

        @Override
        public Student call() throws Exception {
            return applyStrategy();
        }
        
        /**
         * 随机使用某种策略
         */
        public Student applyStrategy() {
            Random random = new Random();
            Student student = new Student();
            student.setName("zhangsan");
            student.setAge(random.nextInt(1000000));
            int n = random.nextInt(4);
            switch(n) {
            case 0:
                student.setAge(random.nextInt(1000000));
                break;
            case 1:
                student.setAge(random.nextInt(1000000));
                break;
            case 2:
                student.setAge(random.nextInt(1000000));
                break;
            case 3:
                student.setAge(random.nextInt(1000000));
                break;
            default:
                student.setAge(random.nextInt(1000000));
                break;
            }
            return student;
        }
        
    }
    
    static class Student {
        int age;
        String name;
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
