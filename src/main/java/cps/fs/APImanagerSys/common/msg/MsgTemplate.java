package cps.fs.APImanagerSys.common.msg;

import java.util.HashMap;
import java.util.Map;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * 
 * @author fs
 * @date 2018年9月11日
 * @description 返回消息实体
 */
public class MsgTemplate {
	
	
	/**
	 * 成功输出
	 * @return
	 */
	public static Map<String,Object> successMsg(){
		
		return successMsg(new Object());
	}
	
	/**
	 * 成功输出
	 * @param data
	 * @return
	 */
	public static Map<String,Object> successMsg(Object data){
		return msgCustom(true, 10000, "", data);
	}
	
	/**
     * 用于服务输出对象转发输出
     * @param data 输出数据
     * @return
     */
    public static Map<String, Object> successMsg(String data) {
        return msgCustom(data);
    }
	
    /**
     * 错误输出
     * @param message MsgInterface接口类型
     * @return
     */
    public static Map<String, Object> failureMsg(MsgInterface message) {
        return msgCustom(false, message.getCode(), message.getMsg(), null);
    }
    
    /**
     * 错误输出
     * @param message MsgInterface接口类型
     * @return
     */
    public static Map<String, Object> failureMsg(MsgInterface message, Object data) {
        return msgCustom(false, message.getCode(), message.getMsg(), data);
    }
    
    /**
     * 错误输出,不建议使用，违反统一输出规范
     * @param error 错误输出
     * @return
     */

    public static Map<String, Object> failureMsg(String error) {
        return msgCustom(false, -10000, error, null);
    }
    
    /**
     * 错误输出,不建议使用，违反统一输出规范
     * @param error 错误输出
     * @return
     */

    public static Map<String, Object> failureMsg(int msgCode,String error) {
        return msgCustom(false, msgCode, error, "");
    }
	
    /**
     * 错误输出
     * @param ret 错误输出
     * @return
     */
    public static Map<String, Object> failureMsg(ComplexResult ret) {
        if(ret.getErrors().size() > 0){

            return msgCustom(false,
                    ret.getErrors().get(0).getErrorCode() > 0 ?  ret.getErrors().get(0).getErrorCode() : -10000,
                    ret.getErrors().get(0).getErrorMsg() + "，错误字段："+ret.getErrors().get(0).getField(), null);
        }
        return msgCustom(false, -10000, null, null);
    }
    
    
	/**
	 * 默认模板普通对象
	 * @param success
	 * @param msgCode
	 * @param message
	 * @param data
	 * @return
	 */
	private static Map<String,Object> msgCustom(boolean success, int msgCode, String message,
			Object data){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("code", msgCode);
		result.put("msg", message);
		result.put("data", data);
		return result;
	}
	
	
	/**
	 * gson输出模板
	 * @param data
	 * @return
	 */
	public static Map<String, Object> msgCustom(String data) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			JsonParser parser = new JsonParser();
			JsonObject object = parser.parse(data).getAsJsonObject();
			result.put("success", object.has("success")? object.get("success") : false);
			result.put("code", object.has("code") ? object.get("code") : "");
			result.put("msg", object.has("msg") ? object.get("msg") : "");
			result.put("data", object.has("data") ? object.get("data") : "");
			String total = "total";
			if(object.has(total)) {
				result.put("total", object.has("total") ? object.get("total") : "");
			}
			String totalCountName = "totalCount";
			if(object.has(totalCountName) ){
			    result.put("totalCount", object.has("totalCount") ? object.get("totalCount") : "");
			}
		} catch (JsonSyntaxException e) {
			result = new HashMap<String, Object>();
			e.printStackTrace();
		}
		return result;
	}
}
