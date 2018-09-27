package cps.fs.APImanagerSys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cps.fs.APImanagerSys.Enum.LoginMsgEnum;
import cps.fs.APImanagerSys.Enum.ProMsgEnum;
import cps.fs.APImanagerSys.common.msg.MsgTemplate;
import cps.fs.APImanagerSys.dao.APIDao;
import cps.fs.APImanagerSys.dao.ProjectDao;
import cps.fs.APImanagerSys.exceptions.ApiManagerException;
import cps.fs.APImanagerSys.model.APIModel;
import cps.fs.APImanagerSys.model.ProjectModel;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private APIDao apiDao;
	
	
	/**
	 * 获取分页列表
	 * @param fproname
	 * @param ftest
	 * @param email
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> selectProList(
			String fproname, String ftest, String email,
			int currentPage, int pageSize){
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("fproname", fproname);
		content.put("ftest", ftest);
		content.put("email", email);
		int start = (currentPage-1) * pageSize;
		content.put("currentPage", start);
		content.put("pageSize", pageSize);
		List<ProjectModel> proList = projectDao.getProList(content);
		
		Map<String, Object> content2 = new HashMap<String, Object>();
		content2.put("fproname", fproname);
		content2.put("ftest", ftest);
		content2.put("email", email);
		int count = projectDao.getCount(content2);
		
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("rows", proList);
		page.put("total", count);
		
		return page;
		
	}
	
	
	/**
	 * 判断项目是否有接口信息 true-有信息 false-无关联api
	 * @param proid
	 * @return
	 */
	public boolean hasApi(String proid) {
		List<APIModel> apiList = apiDao.selectAPIByPro(proid);
		if(apiList.size() != 0)
			return true;
		return false;
	}
	
	/**
	 * 删除项目
	 * @param proid
	 * @return
	 * @throws ApiManagerException 
	 */
	public Map<String, Object> delProject(String proid) throws ApiManagerException{
		if(proid == null || "".equals(proid)) {
			throw new ApiManagerException(ProMsgEnum.PARAMS_ERROR);
		}
		if(hasApi(proid)) {
			throw new ApiManagerException(ProMsgEnum.NO_PERMISSION);
		}
		int count = projectDao.delProject(proid);
		if(count != 1) {
			throw new ApiManagerException(ProMsgEnum.OPS_FAILURE);
		}
		return MsgTemplate.successMsg(ProMsgEnum.OPS_SUCCESS);
	}
	
	/**
	 * 新增项目
	 * @param proid
	 * @return
	 * @throws ApiManagerException 
	 */
	public Map<String, Object> addProject(ProjectModel project) throws ApiManagerException{
		int count = projectDao.addProject(project);
		if(count != 1) {
			throw new ApiManagerException(ProMsgEnum.OPS_FAILURE);
		}
		return MsgTemplate.successMsg(ProMsgEnum.OPS_SUCCESS);
	}
	
	
	/**
	 * 编辑项目
	 * @param proid
	 * @return
	 * @throws ApiManagerException
	 */
	public Map<String, Object> editProject(ProjectModel project) throws ApiManagerException{
		if(project.getFproid() == null || project.getFproname() == null ||
				project.getFdescription() == null || project.getFtest() == null
				|| project.getFurl1() == null) {
			throw new ApiManagerException(ProMsgEnum.PARAMS_ERROR);
		}
		int count = projectDao.editProject(project);
		if(count != 1) {
			throw new ApiManagerException(ProMsgEnum.OPS_FAILURE);
		}
		return MsgTemplate.successMsg(ProMsgEnum.OPS_SUCCESS);
	}
	
	/**
	 * 获取具体项目内容
	 * @return
	 * @throws ApiManagerException 
	 */
	public ProjectModel getProInfo(String proid) throws ApiManagerException{
		if(proid == null || "".equals(proid)) {
			throw new ApiManagerException(ProMsgEnum.PARAMS_ERROR);
		}
		ProjectModel project = projectDao.getProInfo(proid);
		if(project == null) {
			throw new ApiManagerException(ProMsgEnum.NO_PROJECT);
		}
		return project;
	}
	
	/**
	 * 获取所有项目
	 * @return
	 */
	public List<ProjectModel> getPros(){
		return projectDao.getProList(null);
	}

}
