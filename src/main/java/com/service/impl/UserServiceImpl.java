package com.service.impl;

import java.util.List;

import com.pojo.User;

/**
 * @作者：lzy
 * @时间：2019年10月16日
 */
public interface UserServiceImpl {

	boolean checkUserExist(String username);

	int register(User user);

	User login(User user);
	
	/**
	 * 上传头像
	 */
	int updatePhoto(User user);

}
