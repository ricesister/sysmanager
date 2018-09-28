package cps.fs.APImanagerSys.Enum;

import cps.fs.APImanagerSys.common.msg.MsgInterface;

/**
 * 项目模块返回给前端的信息
 * @author fs
 * @date 2018年9月12日
 * @description
 */
public enum APIMsgEnum implements MsgInterface{
	/**
	 * 操作成功
	 */
	OPS_SUCCESS(110000, "操作成功"),
	/**
	 * 操作失败
	 */
	OPS_FAILURE(110001, "操作失败"),
	/**
	 * 有挂接用例
	 */
	HAS_CASE(110002,"接口有用例数据"),
	/**
	 * 参数错误
	 */
	PRRAM_ERROR(110003,"参数错误");
	
    
	
	private int code;

    private String msg;
    
    APIMsgEnum(int code, String msg) {
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
