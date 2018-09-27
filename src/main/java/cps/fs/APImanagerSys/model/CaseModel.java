package cps.fs.APImanagerSys.model;

import java.sql.Timestamp;

/**
 * 用例模型
 * @author fs
 * @date 2018年9月12日
 * @description
 */
public class CaseModel {
	private String fcaseid;
	private String faid;
	private String fcasedesp;
	private String fpoint;
	private String frequest;
	private String fstatus;
	private String fexpect;
	private String fresponse;
	private	Timestamp fcreatetime;
	private Timestamp fupdatetime;
	private String fcreateuser;
	private String fupdateuser;
	public String getFcaseid() {
		return fcaseid;
	}
	public void setFcaseid(String fcaseid) {
		this.fcaseid = fcaseid;
	}
	public String getFaid() {
		return faid;
	}
	public void setFaid(String faid) {
		this.faid = faid;
	}
	public String getFcasedesp() {
		return fcasedesp;
	}
	public void setFcasedesp(String fcasedesp) {
		this.fcasedesp = fcasedesp;
	}
	public String getFpoint() {
		return fpoint;
	}
	public void setFpoint(String fpoint) {
		this.fpoint = fpoint;
	}
	public String getFrequest() {
		return frequest;
	}
	public void setFrequest(String frequest) {
		this.frequest = frequest;
	}
	public String getFstatus() {
		return fstatus;
	}
	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}
	public String getFexpect() {
		return fexpect;
	}
	public void setFexpect(String fexpect) {
		this.fexpect = fexpect;
	}
	public String getFresponse() {
		return fresponse;
	}
	public void setFresponse(String fresponse) {
		this.fresponse = fresponse;
	}
	public Timestamp getFcreatetime() {
		return fcreatetime;
	}
	public void setFcreatetime(Timestamp fcreatetime) {
		this.fcreatetime = fcreatetime;
	}
	public Timestamp getFupdatetime() {
		return fupdatetime;
	}
	public void setFupdatetime(Timestamp fupdatetime) {
		this.fupdatetime = fupdatetime;
	}
	public String getFcreateuser() {
		return fcreateuser;
	}
	public void setFcreateuser(String fcreateuser) {
		this.fcreateuser = fcreateuser;
	}
	public String getFupdateuser() {
		return fupdateuser;
	}
	public void setFupdateuser(String fupdateuser) {
		this.fupdateuser = fupdateuser;
	}
	
	

}
