package cps.fs.APImanagerSys.model;


/**
 * 统一登录接口，数据格式基类
 * @author fs
 * @date 2018年9月12日
 * @description
 */
public abstract class BaseApiAuth<T> {
	protected boolean success;
	protected T data;
	protected String msg;

	public boolean isSuccess() {
		return success;
	}

	public BaseApiAuth setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public BaseApiAuth setMsg(String msg) {
		this.msg = msg;
		return this;
	}
}
