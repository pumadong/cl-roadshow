package com.cl.roadshow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.roadshow.mapper.PersonMapper;
import com.cl.roadshow.model.Person;
import com.cl.roadshow.service.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	private PersonMapper personMapper;

	@Override
	public Person getPersonByName(String name) {
		return personMapper.getPersonByName(name);
	}
}
