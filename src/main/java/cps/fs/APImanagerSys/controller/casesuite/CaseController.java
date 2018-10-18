package cps.fs.APImanagerSys.controller.casesuite;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cps.fs.APImanagerSys.Enum.CaseMsgEnum;
import cps.fs.APImanagerSys.common.msg.MsgTemplate;
import cps.fs.APImanagerSys.model.APIModel;
import cps.fs.APImanagerSys.model.CaseModel;
import cps.fs.APImanagerSys.service.CaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("调试接口用例")
@Controller
public class CaseController {
	
	@Autowired
	private CaseService caseService;
	
/*	@ApiOperation("下载api模板")
	@RequestMapping("/case/downloadExcel")
	public @ResponseBody Map<String, Object> testCase(APIModel apiModel, 
			CaseModel caseModel){
		
	}*/
	
	@ApiOperation("调试接口")
	@PostMapping("/case/debug")
	public @ResponseBody Map<String, Object> dubugCase(
			@RequestParam(name="frequest",required=true) String frequest,
			@RequestParam(name="faurl",required=true) String faurl,
			@RequestParam(name="ftype",required=true) String ftype,
			@RequestParam(name="fexpect",required=true) String fexpect,
			@RequestParam(name="fstatus",required=true) String fstatus,
			@RequestParam(name="fcontenttype",required=true) String fcontenttype){
		int fstatus2 = Integer.parseInt(fstatus);
		Map<String, Object> returnMap = new HashMap<>();
		CaseModel caseModel = new CaseModel(frequest, fstatus2, fexpect, null, null);
		APIModel apiModel = new APIModel(null, null, faurl, ftype, fcontenttype);
		CaseModel caseReturn = null;
		try {
			caseReturn = caseService.toDebugCase(caseModel, apiModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		returnMap.put("caseModel2", caseReturn);
		return returnMap;
		
	}

}
