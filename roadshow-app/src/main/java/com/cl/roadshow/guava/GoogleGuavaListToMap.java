package com.cl.roadshow.guava;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;

import java.util.List;
import java.util.Map;
 
/**
 * https://idodevjobs.wordpress.com/2013/08/08/convert-list-to-map-using-google-guava-in-java/
 * @author dongyongjin
 *
 */
public class GoogleGuavaListToMap {
 
    private String input;
 
    public GoogleGuavaListToMap(String input) {
        this.input = input;
    }
 
    public static void main(String[] args) {
 
    	// Maps.uniqueIndex
        List<GoogleGuavaListToMap> yourList = Lists.newArrayList(new GoogleGuavaListToMap("input1"), new GoogleGuavaListToMap("input2"));
        Map<String, GoogleGuavaListToMap> newMap = Maps.uniqueIndex(yourList, new Function<GoogleGuavaListToMap, String>() {
            @Override
            public String apply(GoogleGuavaListToMap googleGuavaListToMap) {
                return googleGuavaListToMap.getInput(); // or something else
            }
        });
        System.out.println(newMap);
        
        // Multimaps.index
        yourList = Lists.newArrayList(new GoogleGuavaListToMap("input1"), new GoogleGuavaListToMap("input1"), new GoogleGuavaListToMap("input2"));
        ImmutableListMultimap<String, GoogleGuavaListToMap> newMultiMap = Multimaps.index(yourList, new Function<GoogleGuavaListToMap, String>() {
            @Override
            public String apply(GoogleGuavaListToMap googleGuavaListToMap) {
                return googleGuavaListToMap.getInput();
            }
        });
        System.out.println(newMultiMap);
        for(Map.Entry<String, GoogleGuavaListToMap> entry : newMultiMap.entries()) {
        	System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        for(String key : newMultiMap.keySet()) {
        	System.out.println("key : " + key);
        	System.out.println("value : " + newMultiMap.get(key));
        }
    }
 
    public String getInput() {
        return input;
    }
 
    public void setInput(String input) {
        this.input = input;
    }
 
    @Override
    public String toString() {
        return "GoogleGuavaExample{" +
                "input='" + input + '\'' +
                '}';
    }
}