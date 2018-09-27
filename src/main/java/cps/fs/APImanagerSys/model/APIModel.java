package cps.fs.APImanagerSys.model;

import java.sql.Timestamp;

/**
 * 接口表
 * @author fs
 * @date 2018年9月12日
 * @description
 */
public class APIModel {
	private int frowid;
	private String faid;
	private String fproid;
	private String fapiname;
	private String faurl;
	private String ftype;
	private String fcontenttype;
	private int fcasenum;
	private Timestamp fcreatetime;
	private Timestamp fupdatetime;
	public String getFaid() {
		return faid;
	}
	public void setFaid(String faid) {
		this.faid = faid;
	}
	public String getFproid() {
		return fproid;
	}
	public void setFproid(String fproid) {
		this.fproid = fproid;
	}
	public String getFapiname() {
		return fapiname;
	}
	public void setFapiname(String fapiname) {
		this.fapiname = fapiname;
	}
	public String getFaurl() {
		return faurl;
	}
	public void setFaurl(String faurl) {
		this.faurl = faurl;
	}
	public String getFtype() {
		return ftype;
	}
	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	public String getFcontenttype() {
		return fcontenttype;
	}
	public void setFcontenttype(String fcontenttype) {
		this.fcontenttype = fcontenttype;
	}
	public int getFcasenum() {
		return fcasenum;
	}
	public void setFcasenum(int fcasenum) {
		this.fcasenum = fcasenum;
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
	public int getFrowid() {
		return frowid;
	}
	public void setFrowid(int frowid) {
		this.frowid = frowid;
	}
	
}
