package com.cl.roadshow.general;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * org.restlet.ext.json功能演示
 * 
 */
public class JSONDemo {


    public static void main(String[] args) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "张三");
        jsonObject.put("age", 35);
        System.out.println(jsonObject.toString());
    }

}
