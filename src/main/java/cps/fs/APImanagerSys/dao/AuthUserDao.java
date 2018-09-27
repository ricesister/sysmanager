package cps.fs.APImanagerSys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cps.fs.APImanagerSys.model.API_User;



@Mapper
public interface AuthUserDao {
	
	/**
	 * 
	 * @description：根据用户密码获取用户信息
	 * @param arr
	 * @return
	 */
	API_User getAuthUser(@Param("fname") String username, @Param("fpassword") String password);
	
	/**
	 * 根据userid获取用户信息
	 * @param email
	 * @return
	 */
	API_User getUserById(@Param("fuserid") String uid);
	
	/**
	 * 根据username获取用户信息
	 * @param email
	 * @return
	 */
	API_User getUserByName(@Param("fname") String username);
	/**
	 * 根据useremail获取用户信息
	 * @param email
	 * @return
	 */
	API_User getUserByMail(@Param("femail") String email);
	
	/**
	 * 根据手机号获取用户信息(模糊)
	 * @param email
	 * @return
	 */
	API_User getUserByPhone(@Param("fphone") String phone);
	
	/**
	 * 查询用户列表
	 * @param user
	 * @return
	 */
	//public List<API_User> userSelect(API_User user);
	
	/**
	 * 根据条件获取用户数量
	 * @param user
	 * @return
	 */
	int selectCount(Map<String, Object> content);
	
	/**
	 * 查看用户信息
	 * @param user
	 * @return
	 */
	//API_User lookUser(API_User user);
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 *//*
	public Integer updateUser(API_User user);*/
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	int updateUser(API_User user);
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	int addUser(API_User user);
	
	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	int deleteUser(@Param("fuserid") String uid);
	
	/**
	 * 根据条件获取用户数据
	 * @param content
	 * @return
	 */
	List<API_User> selectUserList(Map<String, Object> content);
}
