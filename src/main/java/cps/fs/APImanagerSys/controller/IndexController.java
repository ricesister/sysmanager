package cps.fs.APImanagerSys.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cps.fs.APImanagerSys.common.msg.MsgTemplate;
import cps.fs.APImanagerSys.exceptions.ApiManagerException;
import cps.fs.APImanagerSys.model.API_User;
import cps.fs.APImanagerSys.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 处理session/登录等,主控制器
 * @author fs
 * @date 2018年9月17日
 * @description
 */

@Api("登录相关接口")
@Controller
public class IndexController extends BaseController{
	private static final Logger LOGGER = LogManager.getLogger(IndexController.class);
	
	@Autowired
	private UserService userService;
	
	
	@ApiOperation("判断登录信息")
	@RequestMapping(value="/")
	public String index(Model model,HttpServletRequest request) {
		/**
		 * 判断是否有登录信息
		 */
		API_User loginUser = userService.ifLogin(request);
		if(loginUser == null) {
			return "login";
		}
		return "index";
	}
	
	/*@ApiOperation("去登录")
	@RequestMapping(value="toLogin")
	public String toLogin(Model model,HttpServletRequest request) {
		API_User loginUser = userService.ifLogin(request);
		if(loginUser == null) {
			return "redirect:/";
		}
		return "login";
	}*/
	
	
	@ApiOperation("登录接口")
	@RequestMapping(value="login", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> loginDo(HttpServletRequest request, 
			HttpServletResponse response, 
			String isRemember, String username, 
			String password) {
		//获取缓存
		Integer error_count = cache.get("login_error_count");
		boolean isRem = false;
		if("on".equals(isRemember)) {
			isRem = true;
		}
		
		Map<String, Object> loginMsg = null;
		try {
			loginMsg = userService.login(request,response, username, password, isRem);
		} catch (ApiManagerException e) {
			Map<String, Object> emsg = e.getErrorMsg();
			if("100003".equals(emsg.get("code").toString().trim())){
				error_count = (null == error_count ? 1:error_count+1);
				if(error_count > 3){
					emsg.put("msg","您输入密码错误超过3次，系统锁定3分钟");
					
				}
				cache.set("login_error_count", error_count,3 * 60);
			}
			loginMsg = emsg;
		}
		return loginMsg;
		
	}
	
	@ApiOperation("登出")
	@RequestMapping(value="logout")
	public /*@ResponseBody Map<String,Object>*/ void loginOutDo(
			HttpServletRequest request, HttpServletResponse response){
		//return userService.logout(request, response);
		userService.logout(request, response);
		 try {
			response.sendRedirect("/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	@ApiOperation("帮助")
	@RequestMapping("help")
	public String help() {
		return "help";
	}
	

}
