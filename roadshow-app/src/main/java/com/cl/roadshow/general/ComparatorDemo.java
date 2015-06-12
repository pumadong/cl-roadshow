package com.cl.roadshow.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 比较器功能演示
 * 
 */
public class ComparatorDemo {
    public static void main(String[] args) {
        List<User> users = new ArrayList<User>();
        users.add(new User(2, false, "张三"));
        users.add(new User(5, true, "李四"));
        users.add(new User(1, false, "王五"));

        System.out.println(users);

        /*
         * 可以这样来理解排序比较器
         * 比较两个数时，在某个条件成立时，返回-1，代表不需要交换；返回1，代表需要交换
         */
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                // vip=true在前
                if (u1.getVip() && !u2.getVip()) {
                    return -1;
                } else {
                    if (!u1.getVip() && u2.getVip()) {
                        return 1;
                    }
                }
                // id小的在前
                if (u1.getId() > u2.getId()) {
                    return 1;
                } else {
                    if (u1.getId() < u2.getId()) {
                        return -1;
                    }
                }

                return 0;
            }
        });

        System.out.println(users);
    }
}

class User {
    private Integer id;
    private Boolean vip;
    private String name;

    /**
     * @param id
     * @param vip
     * @param name
     */
    public User(Integer id, Boolean vip, String name) {
        super();
        this.id = id;
        this.vip = vip;
        this.name = name;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the vip
     */
    public Boolean getVip() {
        return vip;
    }

    /**
     * @param vip
     */
    public void setVip(Boolean vip) {
        this.vip = vip;
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
     * TODO 在这里编写被覆盖方法的注释
     */
    @Override
    public String toString() {
        return "User [id=" + id + ", vip=" + vip + ", name=" + name + "]";
    }

}
