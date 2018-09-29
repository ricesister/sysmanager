package cps.fs.APImanagerSys.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cps.fs.APImanagerSys.common.msg.MsgTemplate;
import cps.fs.APImanagerSys.common.util.PageUtil;
import cps.fs.APImanagerSys.exceptions.ApiManagerException;
import cps.fs.APImanagerSys.model.API_User;
import cps.fs.APImanagerSys.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("人员用户管理相关接口")
@Controller
public class UserManageController {
	
	@Autowired
	private UserService userService;
	
	
	@ApiOperation("获取用户列表-分页")
	@GetMapping("/getUserList")
	public @ResponseBody Map<String, Object> getUserList(
			@RequestParam(name = "username", required = false) String username, 
			@RequestParam(name = "phone", required = false) String phone, 
			@RequestParam(name = "effect", required = false) String effect ,
			int offset, int pageSize){
		int begin = offset/pageSize + 1;
		Map<String, Object> page = userService.selectUserList(username, phone, 
				effect, begin, pageSize);
		return page;
		
	}
	
	
	@ApiOperation("根据用户id获取详细信息")
	@GetMapping("getUserInfoById")
	public String getUserInfo(
			@RequestParam(name="fuserid",required=true) String userid,
			HttpServletRequest request) {
		API_User editUser = null;
		try {
			editUser = userService.getUserInfoByID(userid);
			request.setAttribute("userInfo", editUser);
			return "userinfo";
		} catch (ApiManagerException e) {
			e.printStackTrace();
		}
		
		return "error-500";
	}
	
	
	@ApiOperation("根据用户id删除")
	@GetMapping("/delUserById")
	public @ResponseBody Map<String, Object> delUser(
			@RequestParam(name="fuserid", required=true) String userid){
		Map<String, Object> rmsg = null;
		try {
			rmsg = userService.delUser(userid);
		} catch (ApiManagerException e) {
			rmsg = e.getErrorMsg();
		}
		return rmsg;
	}
	
	@ApiOperation("修改账户状态，1-正常 0-锁定")
	@GetMapping("/changeUStatus")
	public @ResponseBody Map<String, Object> changeLock(
			@RequestParam(name="feffect", required=true) String effect,
			@RequestParam(name="fuserid", required=true) String userid){
		Map<String, Object> rmsg = null;
		try {
			rmsg = userService.changeStatus(effect, userid);
		} catch (ApiManagerException e) {
			rmsg = e.getErrorMsg();
		}
		return rmsg;
	}
	
	@ApiOperation("修改账户状态，1-正常 0-锁定")
	@GetMapping("/showAdd")
	public String getAddWin() {
		return "addUser";
	}
	

}
