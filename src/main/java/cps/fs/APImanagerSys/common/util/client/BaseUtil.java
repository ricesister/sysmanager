package cps.fs.APImanagerSys.common.util.client;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;

import com.alibaba.fastjson.JSONPath;

import cps.fs.APImanagerSys.common.util.AssertUtil;
import cps.fs.APImanagerSys.common.util.StringUtils;
import cps.fs.APImanagerSys.common.util.client.function.FunctionUtil;
import cps.fs.APImanagerSys.model.CaseModel;

/**
 * 基本工具类（用于协议请求，参数等处理）
 * @description 
 * @author fangshu
 * @date 2018年10月9日
 */
public class BaseUtil {
	/**
	 * 替换符，如果数据中包含“${}”则会被替换成公共参数中存储的数据
	 */
	protected static Pattern replaceParamPattern = Pattern.compile("\\$\\{(.*?)\\}");

	/**
	 * 截取自定义方法正则表达式：__xxx(ooo)
	 */
	protected static Pattern funPattern = Pattern
			.compile("__(\\w*?)\\((([\\w\\\\\\/:\\.\\$]*,?)*)\\)");// __(\\w*?)\\((((\\w*)|(\\w*,))*)\\)
																	// __(\\w*?)\\(((\\w*,?\\w*)*)\\)
	
	/**
	 * 公共参数数据池（全局可用）
	 */
	private static Map<String, String> saveDatas = new HashMap<String, String>();
	
	/**
	 * 存入公共参数池
	 * @param map
	 */
	protected void setSaveDates(Map<String, String> map) {
		if(map == null)
			return;
		saveDatas.putAll(map);
	}
	
	/**
	 * 组件预参数（处理__fucn()以及${xxxx}）
	 * 
	 * @param apiDataBean
	 * @return
	 */
	protected static String buildParam(String param) {
		// 处理${}
		param = getCommonParam(param);
		// Pattern pattern = Pattern.compile("__(.*?)\\(.*\\)");// 取__开头的函数正则表达式
		// Pattern pattern =
		// Pattern.compile("__(\\w*?)\\((\\w*,)*(\\w*)*\\)");// 取__开头的函数正则表达式
		Matcher m = funPattern.matcher(param);
		while (m.find()) {
			String funcName = m.group(1);
			String args = m.group(2);
			String value;
			// bodyfile属于特殊情况，不进行匹配，在post请求的时候进行处理
			if (FunctionUtil.isFunction(funcName)
					&& !funcName.equals("bodyfile")) {
				// 属于函数助手，调用那个函数助手获取。
				value = FunctionUtil.getValue(funcName, args.split(","));
				// 解析对应的函数失败
				Assert.assertNotNull(value,
						String.format("解析函数失败：%s。", funcName));
				param = StringUtils.replaceFirst(param, m.group(), value);
			}
		}
		return param;
	}
	
	/**
	 * 讲xx=xx;xx=xx加入公共参数池saveDatas
	 * @param preParam
	 */
	protected void savePreParam(String preParam) {
		// 通过';'分隔，将参数加入公共参数map中
		if (StringUtils.isEmpty(preParam)) {
			return;
		}
		String[] preParamArr = preParam.split(";");
		String key, value;
		for (String prepar : preParamArr) {
			if (StringUtils.isEmpty(prepar)) {
				continue;
			}
			key = prepar.split("=")[0];
			value = prepar.split("=")[1];
			System.out.println(String.format("存储%s参数，值为：%s。", key, value));
			saveDatas.put(key, value);
		}
	}
	
	/**
	 * 取公共参数的值  并替换参数
	 *
	 * 
	 * @param param
	 * @return
	 */
	protected static String getCommonParam(String param) {
		if (StringUtils.isEmpty(param)) {
			return "";
		}
		Matcher m = replaceParamPattern.matcher(param);// 取公共参数正则
		while (m.find()) {
			String replaceKey = m.group(1);
			String value;
			// 从公共参数池中获取值
			value = getSaveData(replaceKey);
			// 如果公共参数池中未能找到对应的值，该用例失败。
			Assert.assertNotNull(value,
					String.format("格式化参数失败，公共参数中找不到%s。", replaceKey));
			param = param.replace(m.group(), value);
		}
		return param;
	}
	
	
	/**
	 * 根据key获取公共数据池中的数据
	 * 
	 * @param key
	 *            公共数据的key
	 * @return 对应的value
	 */
	protected static String getSaveData(String key) {
		if ("".equals(key) || !saveDatas.containsKey(key)) {
			return null;
		} else {
			return saveDatas.get(key);
		}
	}
	
	/**
	 * 断言结果$.ddd=xx
	 * @param sourchData
	 * @param verifyStr
	 * @param contains
	 */
	protected static void verifyResult(String sourchData, String verifyStr,
			boolean contains) throws Error{
		if (StringUtils.isEmpty(verifyStr)) {
			return;
		}
		String allVerify = getCommonParam(verifyStr);
		System.out.println("验证数据：" + allVerify);
		if (contains) {
			// 验证结果包含
			AssertUtil.contains(sourchData, allVerify);
		} else {
			// 通过';'分隔，通过jsonPath进行一一校验
			Pattern pattern = Pattern.compile("([^;]*)=([^;]*)");
			Matcher m = pattern.matcher(allVerify.trim());
			while (m.find()) {
				String actualValue = getBuildValue(sourchData, m.group(1));
				String exceptValue = getBuildValue(sourchData, m.group(2));
				System.out.println(String.format("验证转换后的值%s=%s", actualValue,
						exceptValue));
				
				try {
					Assert.assertEquals(actualValue, exceptValue, "验证预期结果失败。");
				} catch (Error e) {
					throw e;
				}
			}
		}
	}

	/**
	 * 获取格式化后的值
	 * @param sourchJson
	 * @param key
	 * @return
	 */
	private static String getBuildValue(String sourchJson, String key) {
		key = key.trim();
		Matcher funMatch = funPattern.matcher(key);
		if (key.startsWith("$.")) {// jsonpath
			key = JSONPath.read(sourchJson, key).toString();
		} else if (funMatch.find()) {
			// String args;
			// if (funMatch.group(2).startsWith("$.")) {
			// args = JSONPath.read(sourchJson, funMatch.group(2)).toString();
			// } else {
			// args = funMatch.group(2);
			// }
			String args = funMatch.group(2);
			String[] argArr = args.split(",");
			for (int index = 0; index < argArr.length; index++) {
				String arg = argArr[index];
				if (arg.startsWith("$.")) {
					argArr[index] = JSONPath.read(sourchJson, arg).toString();
				}
			}
			String value = FunctionUtil.getValue(funMatch.group(1), argArr);
			key = StringUtils.replaceFirst(key, funMatch.group(), value);

		}
		return key;
	}

	/**
	 * 提取json串中的值保存至公共池中
	 * 
	 * @param json
	 *            将被提取的json串。
	 * @param allSave
	 *            所有将被保存的数据：xx=$.jsonpath.xx;oo=$.jsonpath.oo，将$.jsonpath.
	 *            xx提取出来的值存放至公共池的xx中，将$.jsonpath.oo提取出来的值存放至公共池的oo中
	 */
	protected static void saveResult(String json, String allSave) {
		if (null == json || "".equals(json) || null == allSave
				|| "".equals(allSave)) {
			return;
		}
		allSave = getCommonParam(allSave);
		String[] saves = allSave.split(";");
		String key, value;
		for (String save : saves) {
			// key = save.split("=")[0].trim();
			// value = JsonPath.read(json,
			// save.split("=")[1].trim()).toString();
			// ReportUtil.log(String.format("存储公共参数   %s值为：%s.", key, value));
			// saveDatas.put(key, value);

			Pattern pattern = Pattern.compile("([^;=]*)=([^;]*)");
			Matcher m = pattern.matcher(save.trim());
			while (m.find()) {
				key = getBuildValue(json, m.group(1));
				value = getBuildValue(json, m.group(2));
				if(value.contains("oncetoken")) {
					value = value.split("oncetoken=")[1];
				}

				System.out.println(String.format("存储公共参数   %s值为：%s.", key, value));
				saveDatas.put(key, value);
			}
		}
	}
	
	/**
	 * 保存预存参数 用于后面接口参数中使用和接口返回验证中;处理接口请求参数
	 * @param apiDataBean
	 * @return
	 */
	protected static String buildRequestParam(CaseModel caseModel) {
		/*// 分析处理预参数 （函数生成的参数）
		String preParam = buildParam(apiDataBean.getPreParam());
		savePreParam(preParam);// 保存预存参数 用于后面接口参数中使用和接口返回验证中
*/		// 处理参数
		String apiParam = buildParam(caseModel.getFrequest());
		return apiParam;
	}
}
