package cps.fs.APImanagerSys.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户表
 * @author fs
 * @date 2018年9月12日
 * @description
 */
public class API_User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int frowid;
	private String fuserid;
	private String fname;
	private String fphone;
	private String fpassword;
	private int feffect;
	private Timestamp fcreatetime;
	private String femail;
	
	
	
	public API_User() {
		super();
	}

	public API_User(String fpassword, String fname) {
		super();
		this.fpassword = fpassword;
		this.fname = fname;
	}
	
	
	
	public API_User(String fuserid, int feffect) {
		super();
		this.fuserid = fuserid;
		this.feffect = feffect;
	}

	public API_User(String fname, String fphone, String fpassword, int feffect, String femail) {
		super();
		this.fname = fname;
		this.fphone = fphone;
		this.fpassword = fpassword;
		this.feffect = feffect;
		this.femail = femail;
	}

	public int getFrowid() {
		return frowid;
	}
	public void setFrowid(int frowid) {
		this.frowid = frowid;
	}
	public String getFuserid() {
		return fuserid;
	}
	public void setFuserid(String fuserid) {
		this.fuserid = fuserid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getFphone() {
		return fphone;
	}
	public void setFphone(String fphone) {
		this.fphone = fphone;
	}
	public String getFpassword() {
		return fpassword;
	}
	public void setFpassword(String fpassword) {
		this.fpassword = fpassword;
	}
	public int getFeffect() {
		return feffect;
	}
	public void setFeffect(int feffect) {
		this.feffect = feffect;
	}
	public Timestamp getFcreatetime() {
		return fcreatetime;
	}
	public void setFcreatetime(Timestamp fcreatetime) {
		this.fcreatetime = fcreatetime;
	}
	public String getFemail() {
		return femail;
	}
	public void setFemail(String femail) {
		this.femail = femail;
	}
	
	
	

}
