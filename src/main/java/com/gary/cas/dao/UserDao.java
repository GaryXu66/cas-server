package com.gary.cas.dao;

import java.util.List;
import java.util.Map;

import com.gary.cas.common.MyBatisDao;
import com.gary.cas.entity.User;

@MyBatisDao
public interface UserDao {

	List<User> findByNameAndPass(Map<String, String> params);

}
