package cps.fs.APImanagerSys.controller.casesuite;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cps.fs.APImanagerSys.Enum.CaseMsgEnum;
import cps.fs.APImanagerSys.common.msg.MsgTemplate;
import cps.fs.APImanagerSys.exceptions.ApiManagerException;
import cps.fs.APImanagerSys.model.APIModel;
import cps.fs.APImanagerSys.model.CaseModel;
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
	
	@ApiOperation("下载api模板")
	@RequestMapping("/case/downloadExcel")
	public @ResponseBody void downloadExcel(HttpServletResponse response,HttpServletRequest request){
		try {
            //获取要下载的模板名称
            String fileName = "importAPI.xlsx";
            //设置要下载的文件的名称
            response.setHeader("Content-disposition", "attachment;fileName=" + fileName);
            //通知客服文件的MIME类型
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            //获取文件的路径
            String filePath = getClass().getResource("/templet/" + fileName).getPath();
            FileInputStream input = new FileInputStream(filePath);
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[2048];
            int len;
            while ((len = input.read(b)) != -1) {
                out.write(b, 0, len);
            }
            //修正 Excel在“xxx.xlsx”中发现不可读取的内容。是否恢复此工作薄的内容？如果信任此工作簿的来源，请点击"是"
           // response.setHeader("Content-Length", String.valueOf(input.getChannel().size()));
            response.setHeader("Content-Length", String.valueOf(input.available()));
            response.setHeader("Content-disposition","attachment;fileName="+fileName);
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            input.close();
            //return Response.ok("应用导入模板下载完成");
        } catch (Exception ex) {
        }
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
	
	
	@ApiOperation("进入上传excel页面")
	@GetMapping("/toUploadApi")
	public String toUploadApi() {
		return "case/uploadAPI";
	}
}
