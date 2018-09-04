package cps.fs.APImanagerSys.dao;

import cps.fs.APImanagerSys.model.Auth_User;

public interface AuthUserDao {
	
	/**
	 * 
	 * @description：根据用户获取用户
	 * @param arr
	 * @return
	 */
	Auth_User getAuthUser(Auth_User user);

}
