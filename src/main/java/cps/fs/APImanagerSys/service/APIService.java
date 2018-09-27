package cps.fs.APImanagerSys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cps.fs.APImanagerSys.dao.APIDao;
import cps.fs.APImanagerSys.model.APIModel;

@Service
public class APIService {
	
	@Autowired
	private APIDao apiDao;
	
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

}
