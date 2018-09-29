package cps.fs.APImanagerSys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import cps.fs.APImanagerSys.Enum.APIMsgEnum;
import cps.fs.APImanagerSys.common.msg.MsgTemplate;
import cps.fs.APImanagerSys.common.util.UUIDUtil;
import cps.fs.APImanagerSys.dao.APIDao;
import cps.fs.APImanagerSys.exceptions.ApiManagerException;
import cps.fs.APImanagerSys.model.APIModel;

@Service
public class APIService {
	
	@Autowired
	private APIDao apiDao;
	/**
	 * 获取列表
	 * @param fproid
	 * @param faurl
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> selectAPIList(
			String fproid, String faurl, int currentPage, int pageSize){
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("fproid", fproid);
		content.put("faurl", faurl);
		int start = (currentPage-1) * pageSize;
		content.put("currentPage", start);
		content.put("pageSize", pageSize);
		List<APIModel> apiList = apiDao.selectAPIList(content);
		
		Map<String, Object> content2 = new HashMap<String, Object>();
		content2.put("fproid", fproid);
		content2.put("faurl", faurl);
		int count = apiDao.getCount(content2);
		
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("rows", apiList);
		page.put("total", count);
		return page;
	} 
	
	/**
	 * 判断是否能被删除 true-不能  false-可以
	 * @param faid
	 * @return
	 */
	public Boolean isDel(String faid) {
		APIModel api = new APIModel();
		api.setFaid(faid);
		APIModel apiModel = apiDao.SelectAPI(api);
		if(apiModel.getFcasenum() > 0)
			return true;
		return false;
	}
	
	/**
	 * 获取api详细信息
	 * @param faid
	 * @return
	 * @throws ApiManagerException
	 */
	public APIModel getAPIInfo(String faid) throws ApiManagerException {
		if(faid == null || "".equals(faid)) {
			throw new ApiManagerException(APIMsgEnum.PRRAM_ERROR);
		}
		APIModel api = new APIModel();
		api.setFaid(faid);
		return apiDao.SelectAPI(api);
	}
	
	/**
	 * 删除接口
	 * @param faid
	 * @return
	 * @throws ApiManagerException
	 */
	public Map<String, Object> delAPI(String faid) 
			throws ApiManagerException{
		if(faid == null || "".equals(faid)) {
			throw new ApiManagerException(APIMsgEnum.PRRAM_ERROR);
		}
		if(isDel(faid)) {
			throw new ApiManagerException(APIMsgEnum.HAS_CASE);
		}
		int count = apiDao.delAPI(faid);
		if(count == 0) {
			throw new ApiManagerException(APIMsgEnum.OPS_FAILURE);
		}
		return MsgTemplate.successMsg(APIMsgEnum.OPS_SUCCESS);
	}
	
	/**
	 * 新增api
	 * @param fapiname
	 * @param faurl
	 * @param ftype
	 * @param fcontenttype
	 * @return
	 * @throws ApiManagerException
	 */
	public Map<String, Object> addAPI(
			String fapiname,
			String faurl,
			String ftype,
			String fcontenttype,String fproid) throws ApiManagerException{
		if(fapiname == null || faurl == null || ftype == null || fcontenttype == null) {
			throw new ApiManagerException(APIMsgEnum.PRRAM_ERROR);
		}
		String faid = UUIDUtil.getID();
		APIModel api = new APIModel(faid, fapiname, faurl, ftype, fcontenttype);
		api.setFproid(fproid);
		int count = apiDao.addAPI(api);
		if(count == 0) {
			throw new ApiManagerException(APIMsgEnum.OPS_FAILURE);
		}
		return MsgTemplate.successMsg(APIMsgEnum.OPS_SUCCESS);
	}
	
	/**
	 * 编辑API
	 * @param faid
	 * @param fapiname
	 * @param faurl
	 * @param ftype
	 * @param fcontenttype
	 * @return
	 * @throws ApiManagerException
	 */
	public Map<String, Object> editAPI(
			String faid,
			String fapiname,
			String faurl,
			String ftype,
			String fcontenttype) throws ApiManagerException{
		if(faid == null || fapiname == null || faurl == null|| ftype == null 
				|| fcontenttype == null) {
			throw new ApiManagerException(APIMsgEnum.PRRAM_ERROR);
		}
		APIModel apiModel = new APIModel(faid, fapiname, faurl, ftype, fcontenttype);
		int count = apiDao.editAPI(apiModel);
		if(count == 0) {
			throw new ApiManagerException(APIMsgEnum.OPS_FAILURE);
		}
		return MsgTemplate.successMsg(APIMsgEnum.OPS_SUCCESS);
	}

}
