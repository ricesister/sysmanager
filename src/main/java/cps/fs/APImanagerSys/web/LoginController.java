package cps.fs.APImanagerSys.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * 
 * @author fs
 * @version 1.0.0
 * @description 登录
 * @date 2018年9月4日 上午9:58:42
 */
public class LoginController {
	
	@GetMapping("/")
	public String index(@SessionAttribute(WebSecurityConfig.s)) {}
	
	

}
