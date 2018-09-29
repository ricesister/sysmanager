package cps.fs.APImanagerSys.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import cps.fs.APImanagerSys.common.config.ParamConfig;

/**
 * 获取token工具类
 * @author fs
 * @date 2018年9月12日
 * @description
 */
public class TokenUtil {
	
	public static String getToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String token = null;
		if(cookies != null && cookies.length > 0) {
			for(Cookie cookie: cookies) {
				if(ParamConfig.USER_COOKIE_NAME.equals(cookie.getName())) {
					token = cookie.getValue();
					break;
				}
			}
		}
		return token;
	}

}
