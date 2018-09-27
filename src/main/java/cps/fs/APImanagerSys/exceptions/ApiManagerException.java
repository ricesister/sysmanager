package cps.fs.APImanagerSys.exceptions;

import java.util.Map;

import cps.fs.APImanagerSys.Enum.LoginMsgEnum;
import cps.fs.APImanagerSys.common.msg.MsgInterface;
import cps.fs.APImanagerSys.common.msg.MsgTemplate;

/**
 * 自定义异常
 * @author fang
 *
 */
public class ApiManagerException extends Exception {
	private MsgInterface message;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ApiManagerException() {
	}
	public ApiManagerException(MsgInterface paramsError) {
		super();
		this.message = paramsError;
	}

	
	public String getMessage() {
		return this.message.toString();
	}
	
	public Map<String,Object> getErrorMsg() {
		return MsgTemplate.failureMsg(this.message);
	}

	public void setMessage(LoginMsgEnum message) {
		this.message = message;
	}

	
}
