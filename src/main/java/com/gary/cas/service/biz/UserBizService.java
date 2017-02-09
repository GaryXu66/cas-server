package com.gary.cas.service.biz;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gary.cas.base.BaseLog;
import com.gary.cas.constant.SysConstant;
import com.gary.cas.entity.User;
import com.gary.cas.service.db.UserService;
import com.gary.cas.util.MD5Util;

/**
 * 2017年2月7日下午1:53:53 @author Gary
 */
@Service
public class UserBizService extends BaseLog{
	
	@Value(value="password.encryptkey")
	private String encryptKey;
	
	@Autowired
	private UserService userService;
	
	public boolean loginValid(String name, String pass) {
		String encryptPass = MD5Util.encrypt(pass, encryptKey, Charset.forName(SysConstant.ENCODE_UTF8));
		Map<String,String> params = new HashMap<String, String>();
		params.put("name", name);
		params.put("pass", encryptPass);
		List<User> result = userService.findUserByPass(params);
		if(null != result && result.size() > 0){
			return true;
		}else{
			return false;
		}
	}
}
