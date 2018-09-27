package cps.fs.APImanagerSys.common.util;

/**
 * 校验参数类
 * @author fs
 * @date 2018年9月11日
 * @description
 */
public class CheckUtil {

	/**
     * 空判断
     * @param parameters 需要判断的list
     * @return 返回长度为0则表示没有空，大于0则表示有空
     */
	public static String paramEmpty(Object[] parameters) {
		for(int i=0;i<parameters.length;i++) {
			if(parameters[i] == null || parameters[i].toString().length()==0) {
				return "缺失必要参数";
			}
		}
		return "";
	}
}
