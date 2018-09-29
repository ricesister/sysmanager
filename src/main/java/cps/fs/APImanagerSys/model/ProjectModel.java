package cps.fs.APImanagerSys.model;

/**
 * 项目表
 * @author fs
 * @date 2018年9月12日
 * @description
 */
public class ProjectModel {

	private int frowid;
	private String fproid;
	private String fproname;
	private String fdescription;
	private String fversion;
	private String ftest;
	private String furl1;
	private String furl2;
	private String furl3;
	private String furl4;
	private String fcreatetime;
	private String email;
	
	
	
	public ProjectModel() {
		super();
	}
	public ProjectModel(String fproid, String fproname, String fdescription, String fversion, String ftest, String furl1,
			String furl2, String furl3, String furl4, String email) {
		super();
		this.fproid = fproid;
		this.fproname = fproname;
		this.setFdescription(fdescription);
		this.fversion = fversion;
		this.ftest = ftest;
		this.furl1 = furl1;
		this.furl2 = furl2;
		this.furl3 = furl3;
		this.furl4 = furl4;
		this.email = email;
	}
	public String getFproid() {
		return fproid;
	}
	public void setFproid(String fproid) {
		this.fproid = fproid;
	}
	public String getFproname() {
		return fproname;
	}
	public void setFproname(String fproname) {
		this.fproname = fproname;
	}
	public String getFversion() {
		return fversion;
	}
	public void setFversion(String fversion) {
		this.fversion = fversion;
	}
	public String getFtest() {
		return ftest;
	}
	public void setFtest(String ftest) {
		this.ftest = ftest;
	}
	public String getFurl1() {
		return furl1;
	}
	public void setFurl1(String furl1) {
		this.furl1 = furl1;
	}
	public String getFurl2() {
		return furl2;
	}
	public void setFurl2(String furl2) {
		this.furl2 = furl2;
	}
	public String getFurl3() {
		return furl3;
	}
	public void setFurl3(String furl3) {
		this.furl3 = furl3;
	}
	public String getFurl4() {
		return furl4;
	}
	public void setFurl4(String furl4) {
		this.furl4 = furl4;
	}
	public String getFcreatetime() {
		return fcreatetime;
	}
	public void setFcreatetime(String fcreatetime) {
		this.fcreatetime = fcreatetime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getFrowid() {
		return frowid;
	}
	public void setFrowid(int frowid) {
		this.frowid = frowid;
	}
	public String getFdescription() {
		return fdescription;
	}
	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}
	
	
}
