package cps.fs.APImanagerSys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cps.fs.APImanagerSys.dao.CaseDao;
import cps.fs.APImanagerSys.exceptions.ApiManagerException;
import cps.fs.APImanagerSys.model.CaseModel;
import cps.fs.APImanagerSys.model.ProjectModel;

@Service
public class CaseService {
	@Autowired
	private CaseDao caseDao;
	
	/**
	 * 分页--无查询条件
	 * @param faid
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> selectCaseList(
			String faid,int currentPage, int pageSize){
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("faid", faid);
		int start = (currentPage-1) * pageSize;
		content.put("currentPage", start);
		content.put("pageSize", pageSize);
		List<CaseModel> caseList = caseDao.getCaseList(content);
		
		Map<String, Object> content2 = new HashMap<String, Object>();
		content2.put("faid", faid);
		int count = caseDao.getCount(content2);
		
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("rows", caseList);
		page.put("total", count);
		
		return page;
	}
	
	/**
	 * 根据caseid获取用例详细信息
	 * @param fcaseid
	 * @return
	 */
	public CaseModel getInfo(String fcaseid) {
		if(fcaseid == null || fcaseid == "") {
			throw new ApiManagerException()
		}
	}

}
