package com.cl.roadshow.apache;


public class Lover {
    private String name;
    private int age;
    private String[] lover;

    public Lover() {
    }

    /**
     * @param name
     * @param age
     * @param lover
     */
    public Lover(String name, int age, String[] lover) {
        super();
        this.name = name;
        this.age = age;
        this.lover = lover;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the lover
     */
    public String[] getLover() {
        return lover;
    }

    /**
     * @param lover
     */
    public void setLover(String[] lover) {
        this.lover = lover;
    }

}
