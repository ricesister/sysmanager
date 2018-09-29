package cps.fs.APImanagerSys.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.tools.Diagnostic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;

import cps.fs.APImanagerSys.Enum.LoginMsgEnum;
import cps.fs.APImanagerSys.common.msg.MsgTemplate;
import cps.fs.APImanagerSys.common.util.CookieUtil;
import cps.fs.APImanagerSys.common.util.JacksonUtil;
import cps.fs.APImanagerSys.common.util.PageUtil;
import cps.fs.APImanagerSys.common.util.TokenUtil;
import cps.fs.APImanagerSys.common.util.UUIDUtil;
import cps.fs.APImanagerSys.dao.AuthUserDao;
import cps.fs.APImanagerSys.exceptions.ApiManagerException;
import cps.fs.APImanagerSys.model.API_User;

/**
 * 用户服务
 * @author fs
 * @date 2018年9月18日
 * @description
 */

@Service
public class UserService {
	/**
	 * token
	 */
	public static final String LOGIN_IDENTITY = "cps_zdh_identity";

	@Autowired
	private AuthUserDao userDao;
	
	/**
	 * 设置token
	 * @param apiUser
	 * @return
	 */
	private String makeToken(API_User apiUser){
        String tokenJson = JacksonUtil.writeValueAsString(apiUser);
        String tokenHex = new BigInteger(tokenJson.getBytes()).toString(16);
        return tokenHex;
    }
    private API_User parseToken(String tokenHex){
    	API_User apiUser = null;
        if (tokenHex != null) {
            String tokenJson = new String(new BigInteger(tokenHex, 16).toByteArray());      // username_password(md5)
            apiUser = JacksonUtil.readValue(tokenJson, API_User.class);
        }
        return apiUser;
    }
	
    /**
     * 登录
     * @return
     * @throws ApiManagerException 
     */
    public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response,
    		String userName,String password,boolean isRemember) 
    				throws ApiManagerException{
    	if(userName == null || "".equals(userName) || password == null || "".equals(password)) {
    		//return MsgTemplate.failureMsg(LoginMsgEnum.PARAMS_ERROR);
    		throw new ApiManagerException(LoginMsgEnum.PARAMS_ERROR);
    	}
    	/**
    	 * 先判断是否有该用户
    	 */
    	API_User user = userDao.getUserByName(userName);
    	if(user == null){
    		throw new ApiManagerException(LoginMsgEnum.NOSUCH_USER);
    	}
    	user = userDao.getAuthUser(userName,password);
    	if(user == null) {
    		throw new ApiManagerException(LoginMsgEnum.ERROR_PASSWORDORUSER);
    	}
    	if(!(user.getFeffect()==1)) {
    		throw new ApiManagerException(LoginMsgEnum.EFFECT_ERROR);
    	}
    	String loginToken = makeToken(user);
    	CookieUtil.addCookie(LOGIN_IDENTITY, loginToken, response, isRemember);
    	request.getSession().setAttribute("loginUser", user);
    	return MsgTemplate.successMsg(LoginMsgEnum.OPS_SUCCESS);
    }
    
    /**
     * 校验用户名是否唯一
     * @param username
     * @return
     * @throws ApiManagerException 
     */
    public Map<String,Object> checkName(String username) throws ApiManagerException{
    	API_User user = userDao.getUserByName(username);
    	if(user != null){
    		throw new ApiManagerException(LoginMsgEnum.USER_ISEXIST);
    	}
    	return MsgTemplate.successMsg(LoginMsgEnum.OPS_SUCCESS);
    }
    
    /**
     * 重置密码通过邮箱
     * @param email
     * @return
     * @throws ApiManagerException
     */
    public Map<String,Object> remakePwd(String email) 
    				throws ApiManagerException{
    	if(null == email || "".equals(email)){
    		throw new ApiManagerException(LoginMsgEnum.PARAMS_ERROR);
    	}
    	/**
    	 * 确认是否有该邮箱下存在用户
    	 */
    	API_User user = userDao.getUserByMail(email);
    	if(null == user){
    		throw new ApiManagerException(LoginMsgEnum.NOSUCH_USER);
    	}
    	/**
    	 * TODO 发送邮件并重置密码
    	 */
    	user.setFpassword("111111");
    	userDao.updateUser(user);
    	return MsgTemplate.successMsg(LoginMsgEnum.OPS_SUCCESS);
    	
    }
    
    /**
     * 新增用户
     * @param email
     * @param username
     * @param password
     * @param password2
     * @param phone
     * @return
     * @throws ApiManagerException 
     */
    public Map<String,Object> addUser(String email,String username,String password,String password2,
    		String phone) throws ApiManagerException{
    	if(null == email || "".equals(email) || null == username
    			|| "".equals(username) || null == password ||
    			"".equals(password2) || "".equals(phone)){
    		throw new ApiManagerException(LoginMsgEnum.PARAMS_ERROR);
    	}
    	if(!password.equals(password2)){
    		throw new ApiManagerException(LoginMsgEnum.PASSOWRD_NOTSAME);
    	}
    	try {
			checkName(username);
		} catch (ApiManagerException e) {
			return e.getErrorMsg();
		}
    	API_User user = new API_User(username, phone, password, 1, email);
    	user.setFuserid(UUIDUtil.getID());
    	int num = userDao.addUser(user);
    	if(num != 1){
    		throw new ApiManagerException(LoginMsgEnum.OPS_FAILURE);
    	}
    	return MsgTemplate.successMsg(LoginMsgEnum.OPS_SUCCESS);
    	
    }
    
    /**
     * 修改用户(用户自己修改）
     * @param email
     * @param username
     * @param password
     * @param phone
     * @return
     * @throws ApiManagerException 
     */
    public Map<String,Object> updateUser(String email, String username, String password,
    		String effect, String phone) throws ApiManagerException{
	    if(email == null || username == null || password == null || phone == null 
	    		|| (!"1".equals(effect) && !"0".equals(effect))){
	    	return MsgTemplate.failureMsg(LoginMsgEnum.PARAMS_ERROR);
	    }
	    int feffect = Integer.parseInt(effect);
		int num = userDao.updateUser(new API_User(username, phone, password, feffect, email));
		if(num != 1){
			throw new ApiManagerException(LoginMsgEnum.CHANGE_PSWD_FAILURE);
		}
		return MsgTemplate.successMsg(LoginMsgEnum.CHANGE_PSWD_SUCCESS);
    }
	
    /**
     * 变更账户状态
     * @param effect
     * @param userid
     * @return
     * @throws ApiManagerException
     */
    public Map<String,Object> changeStatus(String effect, String userid) 
    		throws ApiManagerException{
    	if((!"1".equals(effect) && !"0".equals(effect)) || userid == null) {
    		throw new ApiManagerException(LoginMsgEnum.PARAMS_ERROR);
    	}
    	int feffect = Integer.parseInt(effect);
 		int num = userDao.updateUser(new API_User(userid, feffect));
 		if(num != 1) {
 			throw new ApiManagerException(LoginMsgEnum.OPS_FAILURE);
 		}
 		return MsgTemplate.successMsg(LoginMsgEnum.OPS_SUCCESS);
    }
    
    /**
     * 登出
     * @param request
     * @param response
     */
	public Map<String,Object> logout(HttpServletRequest request,HttpServletResponse response) {
		CookieUtil.remove(LOGIN_IDENTITY, response, request);
		return MsgTemplate.successMsg(LoginMsgEnum.OPS_SUCCESS);
	}
    
    /**
     * 判断是否有登录信息
     * @param request
     * @return
     */
	public API_User ifLogin(HttpServletRequest request) {
		String cookieToken = CookieUtil.get(request, LOGIN_IDENTITY);
		if(cookieToken != null) {
			API_User cookieUser = parseToken(cookieToken);
			if(cookieUser != null) {
				API_User dbUser = userDao.getUserById(cookieUser.getFuserid());
				if(cookieUser.getFpassword().equals(dbUser.getFpassword()))
					return dbUser;
			}
		}
		return null;
	}
	
	/**
	 * 获取用户信息
	 * @param user
	 * @return
	 */
	public API_User getUserInfo(API_User user){
		API_User loginUser = null;
		if(user.getFname() != null){
			loginUser = userDao.getUserByName(user.getFname());
		}
		return loginUser;
	}
	
	/**
	 * 根据id获取用户信息
	 * @param userid
	 * @return
	 * @throws ApiManagerException 
	 */
	public API_User getUserInfoByID(String userid) throws ApiManagerException {
		API_User editUser = null;
		if(userid == null || "".equals(userid)) {
			throw new ApiManagerException(LoginMsgEnum.PARAMS_ERROR);
		}
		editUser = userDao.getUserById(userid);
		if(editUser == null) {
			throw new ApiManagerException(LoginMsgEnum.NOSUCH_USER);
		}
		return editUser;
	}
	
	/**
	 * 删除用户
	 * @param userid
	 * @return
	 * @throws ApiManagerException 
	 */
	public Map<String, Object> delUser(String userid) throws ApiManagerException{
		if(userid == null || "".equals(userid)) {
			throw new ApiManagerException(LoginMsgEnum.PARAMS_ERROR);
		}
		int count = userDao.deleteUser(userid);
		if(count != 1) {
			throw new ApiManagerException(LoginMsgEnum.OPS_FAILURE);
		}
		return MsgTemplate.successMsg(LoginMsgEnum.OPS_SUCCESS);
	}
	
	/**
	 * 获取分页User信息
	 * @param username
	 * @param phone
	 * @param effect
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> selectUserList(String username, String phone,
			String effect, Integer currentPage, Integer pageSize){
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("fname", username);
		content.put("fphone", phone);
		content.put("feffect", effect);
		int start = (currentPage-1) * pageSize;
		content.put("currentPage", start);
		content.put("pageSize", pageSize);
		List<API_User> userList = userDao.selectUserList(content);
		
		Map<String, Object> content2 = new HashMap<String, Object>();
		content2.put("fname", username);
		content2.put("fphone", phone);
		content2.put("feffect", effect);
		int count = userDao.selectCount(content2);
		
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("rows", userList);
		page.put("total", count);
		return page;
	}
}
