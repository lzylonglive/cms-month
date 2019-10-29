package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.pojo.User;

/**
 * @作者：lzy
 * @时间：2019年10月16日
 */
public interface UserMapper {

	/**
	 * 根据用户名查找
	 * @param username
	 * @return
	 */
	@Select("select id,username,password,role,locked,photo from cms_user where username=#{value} limit 1")
	User findByName(String username);

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	@Insert("insert into cms_user (username,password,gender,create_time) values(#{username},#{password},#{gender},now())")
	int insert(User user);

	/**
	 *  根据id获取对应的用户
	 * @param id
	 * @return
	 */
	User findUserById(Integer id);

	/**
	 * 上传头像
	 */
	@Update("update cms_user set photo = #{photo} where id = #{id}")
	int updatePhoto(User user);
	
}
