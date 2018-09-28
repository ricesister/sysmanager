package cps.fs.APImanagerSys.controller.casesuite;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cps.fs.APImanagerSys.exceptions.ApiManagerException;
import cps.fs.APImanagerSys.model.APIModel;
import cps.fs.APImanagerSys.service.APIService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("接口管理")
@Controller
public class APIController {
	
	@Autowired
	private APIService apiService;
	
	@ApiOperation("获取接口管理列表")
	@GetMapping("/getAPIList")
	public @ResponseBody Map<String, Object> getAPIList(
			@RequestParam(name = "fproid", required = false) String fproid, 
			@RequestParam(name = "faurl", required = false) String faurl,
			int offset, int pageSize){
		int begin = offset/pageSize + 1;
		Map<String, Object> page = apiService.selectAPIList(fproid, faurl, 
				begin, pageSize);
		return page;
	}
	
	
	
	@ApiOperation("根据接口id-删除接口-有用例则不能删除")
	@GetMapping("/delAPI")
	public @ResponseBody Map<String, Object> delAPIById(
			@RequestParam(name = "faid", required = true) String faid){
		Map<String, Object> rmsg = null;
		try {
			rmsg = apiService.delAPI(faid);
		} catch (ApiManagerException e) {
			rmsg = e.getErrorMsg();
		}
		return rmsg;
	}
	
	@ApiOperation("编辑api")
	@PostMapping("/editAPI")
	public @ResponseBody Map<String, Object> editAPI(
			@RequestParam(name = "faid", required = true) String faid,
			@RequestParam(name = "fapiname", required = true) String fapiname,
			@RequestParam(name = "faurl", required = true) String faurl,
			@RequestParam(name = "ftype", required = true) String ftype,
			@RequestParam(name = "fcontenttype", required = true) String fcontenttype){
		Map<String, Object> rmsg = null;
		try {
			rmsg = apiService.editAPI(faid, fapiname, faurl, ftype, fcontenttype);
		} catch (ApiManagerException e) {
			rmsg = e.getErrorMsg();
		}
		return rmsg;
	}
	
	@ApiOperation("新增API")
	@PostMapping("/addAPI")
	public @ResponseBody Map<String, Object> addAPI(
			@RequestParam(name = "fapiname", required = true) String fapiname,
			@RequestParam(name = "faurl", required = true) String faurl,
			@RequestParam(name = "ftype", required = true) String ftype,
			@RequestParam(name = "fcontenttype", required = true) String fcontenttype,
			@RequestParam(name = "fproid", required = true) String fproid){
		Map<String, Object> rmsg = null;
		try {
			rmsg = apiService.addAPI(fapiname, faurl, ftype, fcontenttype, fproid);
		} catch (ApiManagerException e) {
			rmsg = e.getErrorMsg();
		}
		return rmsg;
	}
	
	@ApiOperation("进入接口管理页面")
	@GetMapping("/toAPI")
	public String toAPI() {
		return "case/apiManager";
	}
	
	
	@ApiOperation("进入接口编辑页面")
	@GetMapping("/toEditAPI")
	public String toEdit(
			@RequestParam(name = "faid", required = true) String faid,
			HttpServletRequest request) {
		try {
			APIModel api = apiService.getAPIInfo(faid);
			request.setAttribute("api", api);
		} catch (ApiManagerException e) {
			return "error-500";
		}
		return "case/apiInfo";
	}
	
	
	@ApiOperation("进入添加接口页面")
	@GetMapping("/toAddAPI")
	public String toAdd() {
		return "case/addAPI";
	}

}
