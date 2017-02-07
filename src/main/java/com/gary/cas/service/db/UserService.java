package com.gary.cas.service.db;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gary.cas.dao.UserDao;
import com.gary.cas.entity.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;

	public List<User> findUserByPass(Map<String, String> params) {
		return userDao.findByNameAndPass(params);
	}
}
