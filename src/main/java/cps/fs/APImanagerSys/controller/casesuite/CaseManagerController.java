package cps.fs.APImanagerSys.controller.casesuite;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cps.fs.APImanagerSys.dao.APIDao;
import cps.fs.APImanagerSys.exceptions.ApiManagerException;
import cps.fs.APImanagerSys.model.APIModel;
import cps.fs.APImanagerSys.model.CaseModel;
import cps.fs.APImanagerSys.service.APIService;
import cps.fs.APImanagerSys.service.CaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("用例管理")
@Controller
public class CaseManagerController {
	@Autowired
	private CaseService caseService;
	@Autowired
	private APIService apiService;
	@Autowired
	private APIDao apiDao;
	
	@ApiOperation("获取case-分页-返回某接口的所有case")
	@GetMapping("/getCaseList")
	public @ResponseBody Map<String, Object> getCaseList(
			@RequestParam(name="faid",required=true) String faid,
			int offset, int pageSize){
		int begin = offset/pageSize + 1;
		Map<String, Object> page = caseService.selectCaseList(faid, begin, pageSize);
		return page;
	}
	
	/*public @ResponseBody Map<String, Object> getInfo(
			@RequestParam(name="fcaseid",required=true) String fcaseid){
		Map<String, Object> page = caseService.selectCaseList(faid, begin, pageSize);
		return page;
	}*/
	
	@ApiOperation("获取用例详细信息")
	@GetMapping("/getCaseInfo")
	public String getCaseInfo(
			HttpServletRequest request,
			@RequestParam(name="fproid",required=true) String fproid,
			@RequestParam(name="faid",required=true) String faid,
			@RequestParam(name="fcaseid",required=true) String fcaseid) {
		try {
			CaseModel caseModel = caseService.getInfo(fcaseid);
			APIModel apiModel = apiService.getAPIInfo(faid);
			/**
			 * 拼接路径
			 */
			request.setAttribute("apiModel", apiModel);
			request.setAttribute("caseModel", caseModel);
			request.setAttribute("fproid", fproid);
		} catch (ApiManagerException e) {
			return "error-500";
		}
		return "case/caseInfo";
	}
	
	
	
	@ApiOperation("跳转到用例管理页面")
	@GetMapping("/toCaseManager")
	public String toCaseManager(
			HttpServletRequest request,
			@RequestParam(name="fproid",required=true) String fproid,
			@RequestParam(name="faid",required=true) String faid) {
		APIModel api = apiDao.SelectAPI(new APIModel(faid, null, null, null, null));
		request.setAttribute("api", api);
		request.setAttribute("fproid", fproid);
		return "case/caseManager";
	}
	
	
	@ApiOperation("测试页面")
	@GetMapping("/testTemplate")
	public String index() {
		return "case/caseInfo";
	}
	
	

}
