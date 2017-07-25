package com.cl.roadshow.java.lang;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Double 四舍五入
 * @author dongyongjin
 *
 */
public class DoubleDemo {
	
	public static void main(String[] args) {
		double d = 0.1234567;
		BigDecimal bg = new BigDecimal(d).setScale(4, RoundingMode.UP);
		System.out.println(bg.doubleValue());
	}

}
