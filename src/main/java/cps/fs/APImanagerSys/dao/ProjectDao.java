package cps.fs.APImanagerSys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cps.fs.APImanagerSys.model.ProjectModel;

/**
 * 项目表
 * @description 
 * @author fangshu
 * @date 2018年9月25日
 */

@Mapper
public interface ProjectDao {
	/**
	 * 新增项目
	 * @param project
	 * @return
	 */
	int addProject(ProjectModel project);
	
	/**
	 * 删除项目
	 * @param pro_id
	 * @return
	 */
	int delProject(@Param("fproid") String pro_id);
	
	/**
	 * 编辑项目
	 * @param project
	 * @return
	 */
	int editProject(ProjectModel project);
	
	/**
	 * 获取项目数量，根据条件
	 * @param project
	 * @return
	 */
	int getCount(Map<String, Object> condition);
	
	/**
	 * 获取项目列表，根据条件-分页
	 * @param project
	 * @return
	 */
	List<ProjectModel> getProList(Map<String, Object> condition);
	
	/**
	 * 获取信息
	 * @param proid
	 * @return
	 */
	ProjectModel getProInfo(@Param("fproid") String proid);
	/**
	 * 获取项目
	 * @return
	 */
	List<ProjectModel> getPros();
	
}
