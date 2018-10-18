package cps.fs.APImanagerSys.model;

import java.sql.Timestamp;

/**
 * 用例模型
 * @author fs
 * @date 2018年9月12日
 * @description
 */
public class CaseModel {
	
	private int frowid;
	private String fcaseid;
	private String faid;
	private String fcasedesp;
	private String fpoint;
	private String frequest;
	private int fstatus;
	private String fexpect;
	private String fresponse;
	private	Timestamp fcreatetime;
	private Timestamp fupdatetime;
	private String fcreateuser;
	private String fupdateuser;
	
	private String former;
	private String flatter;
	private String assertResult;
	
	
	public CaseModel(String frequest, int fstatus, String fexpect, String fresponse, String fsave) {
		super();
		this.frequest = frequest;
		this.fstatus = fstatus;
		this.fexpect = fexpect;
		this.fresponse = fresponse;
		this.fsave = fsave;
	}
	public CaseModel() {
		super();
	}
	public CaseModel(String fcaseid, String faid, String fcasedesp, String fpoint, String frequest, int fstatus,
			String fexpect, String fresponse, Timestamp fcreatetime, Timestamp fupdatetime, String fcreateuser,
			String fupdateuser, String former, String flatter, String fsave, String furl) {
		super();
		this.fcaseid = fcaseid;
		this.faid = faid;
		this.fcasedesp = fcasedesp;
		this.fpoint = fpoint;
		this.frequest = frequest;
		this.fstatus = fstatus;
		this.fexpect = fexpect;
		this.fresponse = fresponse;
		this.fcreatetime = fcreatetime;
		this.fupdatetime = fupdatetime;
		this.fcreateuser = fcreateuser;
		this.fupdateuser = fupdateuser;
		this.former = former;
		this.flatter = flatter;
		this.fsave = fsave;
		this.furl = furl;
	}
	/**
	 * 存储接口返回过程中的数据
	 */
	private String fsave;
	/**
	 * 测试完整链接
	 */
	private String furl;
	
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
	public int getFstatus() {
		return fstatus;
	}
	public void setFstatus(int fstatus) {
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
	public int getFrowid() {
		return frowid;
	}
	public void setFrowid(int frowid) {
		this.frowid = frowid;
	}
	public String getFormer() {
		return former;
	}
	public void setFormer(String former) {
		this.former = former;
	}
	public String getFlatter() {
		return flatter;
	}
	public void setFlatter(String flatter) {
		this.flatter = flatter;
	}
	public String getFsave() {
		return fsave;
	}
	public void setFsave(String fsave) {
		this.fsave = fsave;
	}
	public String getFurl() {
		return furl;
	}
	public void setFurl(String furl) {
		this.furl = furl;
	}
	public String getAssertResult() {
		return assertResult;
	}
	public void setAssertResult(String assertResult) {
		this.assertResult = assertResult;
	}
	
	

}
