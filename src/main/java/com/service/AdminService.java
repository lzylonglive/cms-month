package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.AdminMapper;
import com.pojo.User;
import com.service.impl.AdminServiceImpl;

/**
 * @作者：lzy
 * @时间：2019年10月24日
 */
@Service
public class AdminService implements AdminServiceImpl{

	@Autowired
	AdminMapper mapper;

	/**
	 * 	获取所有用户的列表
	 * @param pageNum        分页页码
	 * @param name           用户名模糊查询
	 * @return
	 */
	@Override
	public PageInfo<User> userList(Integer pageNum, String name) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum,10);
		List<User> userList = mapper.userList(name);
		return new PageInfo<>(userList);
	}

	/**
	 * 	修改用户状态
	 * @param id
	 * @param locked         要修改的状态
	 * @return
	 */
	@Override
	public int modifyUserStatus(Integer id, Integer locked) {
		// TODO Auto-generated method stub
		int result = mapper.modifyUserStatus(id,locked);
		return result;
	}
	
}
