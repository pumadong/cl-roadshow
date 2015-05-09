package com.cl.roadshow.designpattern.creation;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 多例是单例的自然延伸
 * 
 * 多例模式中的多例类可以有多个实例，而且多例类必须自己创建、管理自己的实例，并向外界提供自己的实例
 *
 */
public class MultitonDemo {
	public static void main(String[] args) {
		LingualResource lr = LingualResource.getInstance("en", "US");
		String usDollar = lr.getLocaleString("USD");
		System.out.println("USD=" + usDollar);
	}
}

class LingualResource {
	private String localeCode = "en_US";
	private static final String FILE_NAME = "res";
	private static Map<String, LingualResource> instances = new HashMap<String, LingualResource>();
	private Locale locale = null;
	private ResourceBundle resourceBundle = null;

	private LingualResource(String language, String region) {
		this.localeCode = language;
		localeCode = makeLocaleCode(language, region);
		locale = new Locale(language, region);
		resourceBundle = ResourceBundle.getBundle(FILE_NAME, locale);
		instances.put(localeCode, this);
	}

	private LingualResource() {
	}

	public synchronized static LingualResource getInstance(String language, String region) {
		if (instances.get(makeLocaleCode(language, region)) != null) {
			return instances.get(makeLocaleCode(language, region));
		} else {
			return new LingualResource(language, region);
		}
	}

	public String getLocaleString(String code) {
		return resourceBundle.getString(code);
	}

	private static String makeLocaleCode(String language, String region) {
		return language + "_" + region;
	}

}