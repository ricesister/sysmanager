package cps.fs.APImanagerSys.common.util;
/**
 * 返回信息模型
 * @author fs
 * @date 2018年9月11日
 * @description
 */
public class ReturnInfoModel {
    private String msg;
    private String code;
    private String data;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
