package com.cl.roadshow.java.xml;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Map转换成XML演示
 * 
 * 代码来源于互联网，应该有更好的解决办法，记录个TODO
 *
 */
public class Map2XmlStringDemo {
	public static void main(String[] args) {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("hello", "world");

		System.out.print(Map2XmlStringDemo.toXML(map, "rest"));
		System.out.println();
		System.out.println("-----------------------------------------");
		System.out.print(Map2XmlStringDemo.toXML(map));

	}

	public static String toXML(Map<?, ?> _obj, Object key) {
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		xml.append("<").append(key).append(">\n");
		xml.append(toXML(_obj));
		xml.append("</").append(key).append(">");
		return xml.toString();
	}

	private static String toXML(Map<?, ?> _obj) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		if (_obj != null) {
			Set<?> keySet = _obj.keySet();
			for (Iterator<?> it = keySet.iterator(); it.hasNext();) {
				Object key = it.next();
				Object value = _obj.get(key);
				if (value instanceof String) {
					sb.append(toXMLOtherObject(value, key));
				} else if (value instanceof Map) {
					sb.append(toXMLMap((Map<?, ?>) value, key));
				} else if (value instanceof Collection) {
					sb.append(toXMLCollection((Collection<?>) value, key));
				}
			}
		}
		return sb.toString();
	}

	private static String toXMLCollection(Collection<?> _list, Object key) {
		StringBuffer sb = new StringBuffer();
		if (_list != null) {
			sb.append("<").append(key).append("LIST>").append("\n");
			for (Iterator<?> it = _list.iterator(); it.hasNext();) {
				sb.append("<").append(key).append(">").append("\n");
				Object value = it.next();
				if (value instanceof String) {
					sb.append(toXMLOtherObject(value, key));
				} else if (value instanceof Map) {
					sb.append(toXMLMap((Map<?, ?>) value));
				} else if (value instanceof Collection) {
					sb.append(toXMLCollection((Collection<?>) value, key));
				}
				sb.append("</").append(key).append(">").append("\n");
			}
			sb.append("</").append(key).append("LIST>").append("\n");
		}
		return sb.toString();
	}

	private static String toXMLMap(Map<?, ?> _map, Object node) {
		StringBuffer sb = new StringBuffer();
		if (_map != null) {
			sb.append("<").append(node).append(">").append("\n");
			sb.append(toXML(_map));
			sb.append("</").append(node).append(">").append("\n");
		}
		return sb.toString();
	}

	private static String toXMLMap(Map<?, ?> _map) {
		StringBuffer sb = new StringBuffer();
		if (_map != null) {
			sb.append(toXML(_map));
		}
		return sb.toString();
	}

	private static String toXMLOtherObject(Object _obj, Object key) {
		StringBuffer sb = new StringBuffer();
		if (_obj != null) {
			sb.append("<").append(key).append(">");
			sb.append(_obj.toString());
			sb.append("</").append(key).append("> ").append("\n");
		}
		return sb.toString();
	}

}