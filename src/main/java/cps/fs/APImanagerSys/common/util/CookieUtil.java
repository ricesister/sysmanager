package cps.fs.APImanagerSys.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cps.fs.APImanagerSys.common.config.ParamConfig;

/**
 * cookie工具
 * @author fs
 * @date 2018年9月18日
 * @description
 */
public class CookieUtil {
	private static final int MAX_TIME = ParamConfig.COOKIE_TIMEOUT;
	/**
	 * 默认缓存半小时
	 */
	private static final int COOKIE_MAX_AGE= (int) (60 * 60 * 0.5);
	
	/**
	 * 添加cookie,处理cookie时效
	 * @param name
	 * @param value
	 * @param response
	 */
	public static void addCookie(String name, String value,HttpServletResponse response,boolean isRemember) {
		int age_time = isRemember? COOKIE_MAX_AGE:-1;
		set(name, value, response, age_time);
		
	}

	/**
	 * 移除cookie
	 * @param name
	 * @param response
	 */
	 public static void remove(String name, HttpServletResponse response,HttpServletRequest request){
		 Object cookie = get(request, name);
		 if(cookie != null) {
			 set(name, "", response, 0);
		 }
	 }
	 
	 /**
	  * 设置cookie
	  * @param name
	  * @param value
	  * @param response
	  * @param age
	  */
	 private static void set(String name, String value,HttpServletResponse response,int age) {
			Cookie cookie = new Cookie(name, value);
			cookie.setHttpOnly(true);
			cookie.setMaxAge(age);
			cookie.setPath("/");
			response.addCookie(cookie);
	 }
	 
	 /**
	  * 查询cookie
	  * @param request
	  * @param key
	  * @return
	  */
	 public static String get(HttpServletRequest request, String key) {
		 Cookie[] cookies = request.getCookies();
		 if(cookies != null && cookies.length > 0) {
			 for(Cookie cookie : cookies) {
				 if(cookie.getName().equals(key)) {
					 return cookie.getValue();
				 }
			 }
		 }
		 return null;
	 }
}
