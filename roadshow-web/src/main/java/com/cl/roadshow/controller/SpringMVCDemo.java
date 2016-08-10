package com.cl.roadshow.controller;

import java.io.BufferedOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	@ResponseBody
    @RequestMapping(value = "/downloadCsv", method = RequestMethod.GET)
    public String downloadCsv(HttpServletResponse response) {
		String fileName;
		try {
			fileName = URLEncoder.encode("xxx.csv", "UTF-8");
			response.setContentType("application/x-excel;charset=UTF-8");
	        response.setHeader("Content-Disposition",
	                "attachment; filename=" + fileName);
	        StringBuilder sb = new StringBuilder();
	        sb.append("id,name");
	        sb.append("\r\n");
	        sb.append("1,张三");
	        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
	        bos.write(sb.toString().getBytes("UTF-8"));
	        bos.flush();
	        bos.close();
	        return "success";
		} catch (Exception e) {
			return "fail";
		}
    }
}
