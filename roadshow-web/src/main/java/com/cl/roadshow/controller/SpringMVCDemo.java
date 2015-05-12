package com.cl.roadshow.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cl.roadshow.model.Person;
import com.cl.roadshow.service.IUserService;

@Controller
@RequestMapping("/controller")
public class SpringMVCDemo {

	@Autowired
	private IUserService userService;

	@ResponseBody
	@RequestMapping(value = "/getNameByParam", method = RequestMethod.GET)
	public String getNameByParam(@RequestParam("name") String name) {
		return "SpringMVCDemo.getNameByParam:" + name;
	}

	@ResponseBody
	@RequestMapping("/getNameByModel")
	public String getNameByModel(@ModelAttribute("person") Person person) {
		return "SpringMVCDemo.getNameByModel:" + person.getName();
	}

	@ResponseBody
	@RequestMapping("/getNameByRequest")
	public String getNameByRequest(HttpServletRequest request) {
		return "SpringMVCDemo.getNameByRequest:" + request.getParameter("name");
	}

	@RequestMapping("/personView")
	public String personView(ModelMap map) {
		List<Person> persons = new ArrayList<Person>();
		persons.add(new Person("张三"));
		persons.add(new Person("李四"));
		map.put("persons", persons);
		return "person.ftl";
	}

	@RequestMapping("/redirect")
	public String redirect() {
		return "redirect:http://www.baidu.com";
	}

	@ResponseBody
	@RequestMapping("/getPersonByName")
	public String getPersonByName(String name) {
		Person person = userService.getPersonByName(name);
		String message;
		if (person != null) {
			message = person.getName() + ":" + person.getCreateTime();
		} else {
			message = "没有查询到数据";
		}
		return "SpringMVCDemo.getPersonByName:" + message;
	}
}
