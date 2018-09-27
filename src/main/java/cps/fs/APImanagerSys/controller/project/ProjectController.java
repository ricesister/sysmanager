package cps.fs.APImanagerSys.controller.project;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cps.fs.APImanagerSys.Enum.LoginMsgEnum;
import cps.fs.APImanagerSys.common.msg.MsgTemplate;
import cps.fs.APImanagerSys.common.util.UUIDUtil;
import cps.fs.APImanagerSys.exceptions.ApiManagerException;
import cps.fs.APImanagerSys.model.ProjectModel;
import cps.fs.APImanagerSys.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("项目接口")
@Controller
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@ApiOperation("获取项目列表-分页")
	@GetMapping("/getProjectList")
	public @ResponseBody Map<String, Object> getProList(
			@RequestParam(name = "fproname", required = false) String fproname, 
			@RequestParam(name = "ftest", required = false) String ftest, 
			@RequestParam(name = "email", required = false) String email ,
			int offset, int pageSize){
		int begin = offset/pageSize + 1;
		Map<String, Object> page = projectService.selectProList(fproname, ftest, email, 
				begin, pageSize);
		return page;
	}
	
	
	@ApiOperation("根据项目id-删除")
	@GetMapping("/delProject")
	public @ResponseBody Map<String, Object> delProject(
			@RequestParam(name="fproid",required=true) String proid){
		Map<String, Object> rmsg = null;
		try {
			rmsg = projectService.delProject(proid);
		} catch (ApiManagerException e) {
			rmsg = e.getErrorMsg();
		}
		return rmsg;
	}
	
	@ApiOperation("新建项目")
	@PostMapping("/addProject")
	public @ResponseBody Map<String, Object> addProject(
			@RequestParam(name="fproname",required=true) String fproname,
			@RequestParam(name="fdescription",required=true) String fdescription,
			@RequestParam(name="fversion",required=false) String fversion,
			@RequestParam(name="ftest",required=true) String ftest,
			@RequestParam(name="furl1",required=true) String furl1,
			@RequestParam(name="furl2",required=false) String furl2,
			@RequestParam(name="furl3",required=false) String furl3,
			@RequestParam(name="furl4",required=false) String furl4,
			@RequestParam(name="email",required=false) String email
			){
		if(fproname == null || fdescription == null || ftest == null || furl1 == null) {
			return MsgTemplate.failureMsg(LoginMsgEnum.OPS_FAILURE);
		}
		ProjectModel project = new ProjectModel(UUIDUtil.getID(), fproname, 
				fdescription, fversion, ftest, furl1, furl2, furl3, furl4, email);
		Map<String, Object> rmsg = null;
		try {
			rmsg = projectService.addProject(project);
		} catch (ApiManagerException e) {
			rmsg = e.getErrorMsg();
		}
		return rmsg;
	}
	
	
	@ApiOperation("根据id-获取该项目信息")
	@GetMapping("/getProInfo")
	public String getProInfo(
			@RequestParam(name="fproid",required=true) String proid,
			HttpServletRequest request) {
		ProjectModel project;
		try {
			project = projectService.getProInfo(proid);
			request.setAttribute("project", project);
			return "projectInfo";
		} catch (ApiManagerException e) {
			e.printStackTrace();
		}
		return "error-500";
	}
	
	@ApiOperation("获取该项目信息-ajax")
	@GetMapping("/getPros")
	public @ResponseBody List<ProjectModel> getPros(){
		return projectService.getPros();
	}
	
	@ApiOperation("根据id-修改项目")
	@PostMapping("/editProject")
	public @ResponseBody Map<String, Object> editProject(
			@RequestParam(name="fproid",required=true) String fproid,
			@RequestParam(name="fproname",required=true) String fproname,
			@RequestParam(name="fdescription",required=true) String fdescription,
			@RequestParam(name="fversion",required=false) String fversion,
			@RequestParam(name="ftest",required=true) String ftest,
			@RequestParam(name="furl1",required=true) String furl1,
			@RequestParam(name="furl2",required=false) String furl2,
			@RequestParam(name="furl3",required=false) String furl3,
			@RequestParam(name="furl4",required=false) String furl4,
			@RequestParam(name="email",required=false) String email){
		Map<String, Object> rmsg = null;
		try {
			rmsg = projectService.editProject(new ProjectModel(fproid, fproname, fdescription,
					fversion, ftest, furl1, furl2, furl3, furl4, email));
		} catch (ApiManagerException e) {
			rmsg = e.getErrorMsg();
		}
		return rmsg;
		
	}

	@ApiOperation("返回项目增加页面")
	@GetMapping("/showAddProject")
	public String showAddProject() {
		return "addProject";
	}
	
	@ApiOperation("返回项目管理页面")
	@GetMapping("/proManager")
	public String toProManage() {
		return "projectManage";
	}
}
