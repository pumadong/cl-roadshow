package com.cl.roadshow.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



/**
 * 公牛和母牛
 * https://leetcode.com/problems/bulls-and-cows/
 * @author dongyongjin
 *
 */
public class Leetcode299 {
	public static void main(String[] args) {
		Leetcode299 lc = new Leetcode299();
		// 1A3B
		System.out.println(lc.getHint("1807","7810"));
		// 1A0B
		System.out.println(lc.getHint("11","01"));
		// 0A0B
		System.out.println(lc.getHint("1","0"));
		// 3A0B
		System.out.println(lc.getHint("1122","1222"));
	}
	
    public String getHint(String secret, String guess) {
    	int bulls = 0;
    	Set<Integer> bullPosition = new HashSet<Integer>();
    	int cows = 0;
    	int length = secret.length();
    	Map<Character,Integer> secretMap = new HashMap<Character,Integer>();
    	// guess bull
    	for(int i = 0; i < length; i++) {
    		char secretChar = secret.charAt(i);
    		char guessChar = guess.charAt(i);
    		if(secretChar == guessChar) {
    			bulls++;
    			bullPosition.add(i);
    			continue;
    		}
    		if(secretMap.get(secretChar) != null) {
    			secretMap.put(secretChar, secretMap.get(secretChar) + 1);
    		} else {
    			secretMap.put(secretChar, 1);
    		}
    	}
    	// guess cow
    	for(int i = 0; i < length; i++) {
    		if(bullPosition.contains(i)) {
    			continue;
    		}
    		char guessChar = guess.charAt(i);
			Integer appearTimes = secretMap.get(guessChar);
			if(appearTimes != null && appearTimes > 0) {
				secretMap.put(guessChar, appearTimes - 1);
				cows++;
			}     		
    	}    	
        return bulls + "A" + cows + "B";
    }
}
