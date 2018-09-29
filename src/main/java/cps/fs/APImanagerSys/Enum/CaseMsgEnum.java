package cps.fs.APImanagerSys.Enum;

import cps.fs.APImanagerSys.common.msg.MsgInterface;

/**
 * 项目模块返回给前端的信息
 * @author fs
 * @date 2018年9月12日
 * @description
 */
public enum CaseMsgEnum implements MsgInterface{
	/**
	 * 操作成功
	 */
	OPS_SUCCESS(110000, "操作成功"),
	/**
	 * 操作失败
	 */
	OPS_FAILURE(110001, "操作失败"),
	/**
	
	/**
	 * 参数错误
	 */
	PARAMS_ERROR(110020,"参数错误"),

	/**
	 * 系统错误
	 */
	SYS_ERROR(110021,"系统错误"),

    CHANGE_SUCCESS(140011, "信息修改成功"),
    CHANGE_FAILURE(140012, "信息修改失败");
    
	
	private int code;

    private String msg;
    
    CaseMsgEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getMsg() {
		return msg;
	}

}
