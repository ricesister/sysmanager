package cps.fs.APImanagerSys.controller.casesuite;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cps.fs.APImanagerSys.dao.APIDao;
import cps.fs.APImanagerSys.model.APIModel;
import cps.fs.APImanagerSys.service.CaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("用例管理")
@Controller
public class CaseManagerController {
	@Autowired
	private CaseService caseService;
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
	
	@ApiOperation("跳转到用例管理页面")
	@GetMapping("/toCaseManager")
	public String toCaseManager(
			HttpServletRequest request,
			@RequestParam(name="faid",required=true) String faid) {
		APIModel api = apiDao.SelectAPI(new APIModel(faid, null, null, null, null));
		request.setAttribute("api", api);
		return "case/caseManager";
		
	}
	
	

}
