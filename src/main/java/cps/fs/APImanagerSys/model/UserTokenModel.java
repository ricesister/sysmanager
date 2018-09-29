package cps.fs.APImanagerSys.model;

import java.util.Date;

/**
 * token实体表
 * @author fs
 * @date 2018年9月18日
 * @description
 */
public class UserTokenModel {
	private String fuserid;
	private String token;
	private Date expire_time;
	private Date update_time;
	public String getFuserid() {
		return fuserid;
	}
	public void setFuserid(String fuserid) {
		this.fuserid = fuserid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(Date expire_time) {
		this.expire_time = expire_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
	
}
