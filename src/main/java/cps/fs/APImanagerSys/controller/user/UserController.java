package cps.fs.APImanagerSys.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cps.fs.APImanagerSys.exceptions.ApiManagerException;
import cps.fs.APImanagerSys.model.API_User;
import cps.fs.APImanagerSys.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api("用戶相关接口")
@Controller
public class UserController {
	private static final Logger LOGGER = LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@ApiOperation("增加用戶")
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> addUser(
			@RequestParam(name = "email", required = true)
            String email,
			@RequestParam(name = "username", required = true)
            String username,
            @RequestParam(name = "password", required = true)
            String password,
            @RequestParam(name = "password2", required = true)
            String password2,
            @RequestParam(name = "phone", required = true)
            String phone
            ){
		Map<String,Object> cmsg = null;
		try {
			cmsg = userService.addUser(email, username, password, password2, phone);
		} catch (ApiManagerException e) {
			cmsg = e.getErrorMsg();
		}
		return cmsg;
    }
	
	@ApiOperation("获取用户信息")
	@RequestMapping(value="/getUserInfo", method=RequestMethod.GET)
	public String getUserInfo(HttpServletRequest request){
		API_User loginUser = ((API_User) request.getSession().getAttribute("loginUser"));
		request.setAttribute("userInfo", userService.getUserInfo(loginUser));
		return "userInfo";
	}
	
	@ApiOperation("用戶修改個人信息")
	@RequestMapping(value="/updateUser", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> updateUser(
			@RequestParam(name = "username", required = true)
            String username,
            @RequestParam(name = "password", required = true)
            String password,
			@RequestParam(name = "email", required = true)
            String email,
            @RequestParam(name = "phone", required = true)
            String phone){
		Map<String,Object> rmsg = null;
		try {
			rmsg = userService. updateUser(email, username, password, "1", phone);
		} catch (ApiManagerException e) {
			rmsg = e.getErrorMsg();
		}
		return rmsg;
	}
	
	
	@ApiOperation("测试HTML")
	@RequestMapping(value="/userManange", method=RequestMethod.GET)
	public String test(String username){
		
		return "usermanage";
    }
	
	
	
	@ApiOperation("返回index")
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String test(){
		
		return "index";
    }
	
	
	@ApiOperation("校验用户名唯一性")
	@RequestMapping(value="/checkName", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> checkName(String username){
		Map<String,Object> cmsg = null;
		try {
			cmsg = userService.checkName(username);
		} catch (ApiManagerException e) {
			cmsg = e.getErrorMsg();
		}
		return cmsg;
    }
	
	@ApiOperation("校验用户名唯一性")
	@RequestMapping(value="/checkName2", method=RequestMethod.POST)
	public @ResponseBody boolean checkName2(String username){
		try {
			userService.checkName(username);
			return true;
		} catch (ApiManagerException e) {
			return false;
		}
    }
	
	
	@ApiOperation("找回密码并发送邮件重置")
	@RequestMapping(value="/remakePwd", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> remakePwd(
			@RequestParam(name = "email", required = true) String email){
		Map<String,Object> rmsg = null;
		try {
			rmsg = userService.remakePwd(email);
		} catch (ApiManagerException e) {
			return e.getErrorMsg();
		}
		return rmsg;
	}
	
}
