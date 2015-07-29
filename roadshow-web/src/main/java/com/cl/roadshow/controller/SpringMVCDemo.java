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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cl.roadshow.model.Student;
import com.cl.roadshow.service.IStudentService;

@Controller
@RequestMapping("/controller")
public class SpringMVCDemo {

	@Autowired
	private IStudentService studentService;

	@ResponseBody
	@RequestMapping(value = "/getNameByParam", method = RequestMethod.GET)
	public String getNameByParam(@RequestParam("name") String name) {
		return "SpringMVCDemo.getNameByParam:" + name;
	}

	@ResponseBody
	@RequestMapping("/getNameByModel")
	public String getNameByModel(@ModelAttribute("person") Student person) {
		return "SpringMVCDemo.getNameByModel:" + person.getName();
	}

	@ResponseBody
	@RequestMapping("/getNameByRequest")
	public String getNameByRequest(HttpServletRequest request) {
		return "SpringMVCDemo.getNameByRequest:" + request.getParameter("name");
	}

	@RequestMapping("/studentView")
	public String studentView(ModelMap map) {
		List<Student> students = new ArrayList<Student>();
		students.add(new Student("张三"));
		students.add(new Student("李四"));
		map.put("persons", students);
		return "student.ftl";
	}

	@RequestMapping("/redirect")
	public String redirect() {
		return "redirect:http://www.baidu.com";
	}

	@ResponseBody
	@RequestMapping("/getStudentByName")
	public String getStudentByName(String name) {
		Student student = studentService.getStudentByName(name);
		String message;
		if (student != null) {
			message = student.getName() + ":" + student.getCreateDate();
		} else {
			message = "没有查询到数据";
		}
		return "SpringMVCDemo.getPersonByName:" + message;
	}
	
   @ResponseBody
    @RequestMapping(value = "/getRequest", method = RequestMethod.GET)
    public String getRequest() {
       HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
       
       return "request.getName()：" + request.getParameter("name");
    }
}
