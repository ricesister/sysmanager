package cps.fs.APImanagerSys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cps.fs.APImanagerSys.model.APIModel;

/**
 * t_api
 * @description 
 * @author fangshu
 * @date 2018年9月26日
 */

@Mapper
public interface APIDao {
	
	/**
	 * 根据项目id查询关联api
	 * @param proid
	 * @return
	 */
	List<APIModel> selectAPIByPro(@Param("fproid") String proid);
	
	/**
	 * 条件获取列表
	 * @param condition
	 * @return
	 */
	List<APIModel> selectAPIList(Map<String, Object> condition);
	
	/**
	 * 获取项目数量，根据条件
	 * @param project
	 * @return
	 */
	int getCount(Map<String, Object> condition);
}
