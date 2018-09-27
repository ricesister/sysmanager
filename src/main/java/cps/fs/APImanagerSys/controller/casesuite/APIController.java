package cps.fs.APImanagerSys.controller.casesuite;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@ApiOperation("进入接口管理页面")
	@GetMapping("toAPI")
	public String toAPI() {
		return "case/apiManager";
	}

}
