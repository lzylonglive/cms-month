package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapper.UserMapper;
import com.pojo.User;
import com.service.impl.UserServiceImpl;
import com.utils.MD5Util;

/**
 * @作者：lzy
 * @时间：2019年10月16日
 */
@Service
public class UserService implements UserServiceImpl {

	@Autowired
	UserMapper mapper;

	@Override
	public boolean checkUserExist(String username) {
		// TODO Auto-generated method stub
		return null != mapper.findByName(username);
	}

	@Override
	public int register(User user) {
		// TODO Auto-generated method stub
		User existUser = mapper.findByName(user.getUsername());
		
		if(existUser != null) {
			return -1;
		}
		
		user.setPassword(MD5Util.md5(user.getPassword(), user.getUsername()));
		
		return mapper.insert(user);
	}

	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		
		String pwd = MD5Util.md5(user.getPassword(), user.getUsername());
		
		User loginUser = mapper.findByName(user.getUsername());
		
		if(loginUser != null && pwd.equals(loginUser.getPassword())) {
			return loginUser;
		}
		
		return null;
	}

	/**
	 * 上传头像
	 */
	@Override
	public int updatePhoto(User user) {
		// TODO Auto-generated method stub
		return mapper.updatePhoto(user);
	}

}
