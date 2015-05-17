package com.cl.roadshow.java.xml;

import java.io.File;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 演示两种XML的解析方式：DOM和SAX
 * 
 * 至于JDOM和DOM4J，只是在这两种方式之上的更高层次的封装
 *
 */
public class XmlDemo {
	public static void main(String[] args) throws Exception {
		XmlDemo xmlDemo = new XmlDemo();

		// DOM方式
		DomDemo domDemo = xmlDemo.new DomDemo("src/main/java/com/cl/roadshow/java/xml/people.xml");
		domDemo.iterateByName("PERSON");
		domDemo.recursiveElement();

		// SAX方式
		SaxDemo saxDemo = xmlDemo.new SaxDemo("src/main/java/com/cl/roadshow/java/xml/people.xml");
		saxDemo.showEvents();
		saxDemo.parseDocument();
	}

	/**
	 * DOM方式解析XML
	 * 
	 */
	class DomDemo {
		private String path;

		public DomDemo(String path) {
			this.path = path;
		}

		/**
		 * 查询所有符合给到名称的Node，大小写敏感
		 * 
		 * @param tagName
		 * @throws Exception
		 */
		public void iterateByName(String tagName) throws Exception {
			// 获得DOM解析器工厂
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			// 获得具体的DOM解析器
			DocumentBuilder db = dbf.newDocumentBuilder();
			// 解析XML文档，获得Document对象（根结点）
			Document doc = db.parse(new File(path));
			NodeList nodeList = doc.getElementsByTagName(tagName);

			for (int i = 0; i < nodeList.getLength(); i++) {

				Element element = (Element) nodeList.item(i);
				String content = element.getElementsByTagName("NAME").item(0).getFirstChild().getNodeValue();
				System.out.println("name:" + content);

				content = element.getElementsByTagName("ADDRESS").item(0).getFirstChild().getNodeValue();
				System.out.println("address:" + content);

				content = element.getElementsByTagName("TEL").item(0).getFirstChild().getNodeValue();
				System.out.println("tel:" + content);

				content = element.getElementsByTagName("FAX").item(0).getFirstChild().getNodeValue();
				System.out.println("fax:" + content);

				content = element.getElementsByTagName("EMAIL").item(0).getFirstChild().getNodeValue();
				System.out.println("email:" + content);

				System.out.println("--------------------------------------");
			}
		}

		/**
		 * 从根节点开始，遍历XML的所有元素
		 * 
		 * @throws Exception
		 */
		public void recursiveElement() throws Exception {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(new File(path));
			// 获得根元素结点
			Element root = doc.getDocumentElement();

			parseElement(root);
		}

		/**
		 * 递归方法
		 * 
		 * @param element
		 */
		private void parseElement(Element element) {

			String tagName = element.getNodeName();

			NodeList children = element.getChildNodes();

			System.out.print("<" + tagName);

			// element元素的所有属性所构成的NamedNodeMap对象，需要对其进行判断
			NamedNodeMap map = element.getAttributes();

			// 如果该元素存在属性
			if (null != map) {
				for (int i = 0; i < map.getLength(); i++) {
					// 获得该元素的每一个属性
					Attr attr = (Attr) map.item(i);

					String attrName = attr.getName();
					String attrValue = attr.getValue();

					System.out.print(" " + attrName + "=\"" + attrValue + "\"");
				}
			}

			System.out.print(">");

			for (int i = 0; i < children.getLength(); i++) {
				Node node = children.item(i);
				// 获得结点的类型
				short nodeType = node.getNodeType();

				if (nodeType == Node.ELEMENT_NODE) {
					// 是元素，继续递归
					parseElement((Element) node);
				} else if (nodeType == Node.TEXT_NODE) {
					// 递归出口
					System.out.print(node.getNodeValue());
				} else if (nodeType == Node.COMMENT_NODE) {
					System.out.print("<!--");

					Comment comment = (Comment) node;

					// 注释内容
					String data = comment.getData();

					System.out.print(data);

					System.out.print("-->");
				}
			}

			System.out.print("</" + tagName + ">");
		}
	}

	/**
	 * SAX方式解析XML
	 *
	 */
	class SaxDemo {
		private String path;

		public SaxDemo(String path) {
			this.path = path;
		}

		public void showEvents() throws Exception {

			// 获得SAX解析器工厂实例
			SAXParserFactory factory = SAXParserFactory.newInstance();

			// 获得SAX解析器实例
			SAXParser parser = factory.newSAXParser();

			// 开始进行解析
			parser.parse(new File(path), new EventHandler());
		}

		public void parseDocument() throws Exception {

			// 获得SAX解析器工厂实例
			SAXParserFactory factory = SAXParserFactory.newInstance();

			// 获得SAX解析器实例
			SAXParser parser = factory.newSAXParser();

			// 开始进行解析
			parser.parse(new File(path), new ParseHandler());
		}

		/**
		 * 演示SAX解析方式的事件驱动过程
		 *
		 */
		class EventHandler extends DefaultHandler {
			@Override
			public void startDocument() throws SAXException {
				System.out.println("\n--------------------------------------");
				System.out.println("start document");
			}

			@Override
			public void endDocument() throws SAXException {
				System.out.println("finish document");
				System.out.println("--------------------------------------");
			}

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
				System.out.println("start element");
			}

			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				System.out.println("finish element");
			}
		}

		/**
		 * 演示用SAX方式解析PERSON节点的过程
		 *
		 */
		class ParseHandler extends DefaultHandler {
			private Stack<String> stack = new Stack<String>();

			private String name;

			private String tel;

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
				stack.push(qName);

				for (int i = 0; i < attributes.getLength(); i++) {
					String attrName = attributes.getQName(i);
					String attrValue = attributes.getValue(i);

					System.out.println(attrName + "=" + attrValue);
				}
			}

			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				String tag = stack.peek();

				if ("NAME".equals(tag)) {
					name = new String(ch, start, length);
				} else if ("TEL".equals(tag)) {
					tel = new String(ch, start, length);
				}
			}

			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				stack.pop(); // 表示该元素已经解析完毕，需要从栈中弹出

				if ("PERSON".equals(qName)) {
					System.out.println("NAME：" + name);
					System.out.println("TEL：" + tel);

					System.out.println();
				}

			}
		}
	}
}
