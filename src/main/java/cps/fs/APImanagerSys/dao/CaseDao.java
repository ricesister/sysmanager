package cps.fs.APImanagerSys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cps.fs.APImanagerSys.model.CaseModel;

@Mapper
public interface CaseDao {
	/**
	 * 获取case列表-分页
	 * @return
	 */
	List<CaseModel> getCaseList(Map<String, Object> condition);
	/**
	 * 获取某个接口下的用例数目
	 * @param condition
	 * @return
	 */
	int getCount(Map<String, Object> condition);
	

}
