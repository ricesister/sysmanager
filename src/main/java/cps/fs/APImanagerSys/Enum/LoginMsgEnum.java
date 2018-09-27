package cps.fs.APImanagerSys.Enum;

import cps.fs.APImanagerSys.common.msg.MsgInterface;

/**
 * 登录模块返回给前端的信息
 * @author fs
 * @date 2018年9月12日
 * @description
 */
public enum LoginMsgEnum implements MsgInterface{
	ERROR_PASSWORDORUSER(100003,"用户或密码错误"),
	NOSUCH_USER(100001,"无此用户"),
	LOCK_USER(100002,"用户锁定"),
	/**
	 * 操作成功
	 */
	OPS_SUCCESS(110000, "操作成功"),
	/**
	 * 操作失败
	 */
	OPS_FAILURE(110001, "操作失败"),

	/**
	 * 参数为空
	 */
	PARAMS_NULL(110002, "参数为空"),
	
	/**
	 * 手机号格式错误
	 */
	FPHONE_CANT_REGISTER(110005, "请输入正确手机号"),
	/**
	 * 用户已存在
	 */
	USER_ISEXIST(110009, "用户已存在"),
	/**
	 * 新密码不能和旧密码一样
	 */
	PASSOWRD_NOTSAME(110012, "新密码不能和旧密码一样"),
	/**
	 * 用户没有此权限
	 */
	NO_PERMISSION(110014, "用户没有此权限"),

	/**
	 * 参数校验错误
	 */
	PARAMS_WRONG(110015, "参数校验错误"),

	/**
	 * 没有登录信息
	 */
	NO_LOGIN_MESSAGE(110016,"没有登录信息"),

	/**
	 * 系统版本错误
	 */
	SYSTEM_VERSION_ERROR(110017,"系统版本错误"),

	/**
	 * 页码错误
	 */
	PAGE_ERROR(110018,"页码大小错误"),

	/**
	 * 分页大小错误
	 */
	PAGE_SIZE_ERROR(110019,"分页大小错误"),

	/**
	 * 参数错误
	 */
	PARAMS_ERROR(110020,"参数错误"),

	/**
	 * 系统错误
	 */
	SYS_ERROR(110021,"系统错误"),

	/**
	 * 鏈接超時
	 */
	TIMEOUT_ERROR(110022,"系统超时"),
	
	/**
	 * 用户锁定
	 */
	EFFECT_ERROR(110023,"用户没有生效或已锁定"),
	 /**
     * 修改密码成功
     */
    CHANGE_PSWD_SUCCESS(140011, "信息修改成功"),
    /**
     * Change pswd failure msg enum.
     */
    CHANGE_PSWD_FAILURE(140012, "信息修改失败"),
    
    TOEKN_NULL(140014, "登录参数获取超时或Token过期NULL");
	
	private int code;

    private String msg;
    
    LoginMsgEnum(int code, String msg) {
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
