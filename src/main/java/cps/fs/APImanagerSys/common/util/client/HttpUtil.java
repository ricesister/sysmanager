package cps.fs.APImanagerSys.common.util.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import com.alibaba.fastjson.JSON;

import cps.fs.APImanagerSys.common.util.FileUtil;
import cps.fs.APImanagerSys.common.util.RandomUtil;
import cps.fs.APImanagerSys.model.CaseModel;

/**
 * 请求方法
 * @description 
 * @author fangshu
 * @date 2018年10月9日
 */
public class HttpUtil extends BaseUtil{
	
	/**
	 * 封装请求方法
	 *
	 * @param url
	 *            请求路径
	 
	 * @throws UnsupportedEncodingException
	 */
	protected static HttpUriRequest parseHttpRequest(String url, String method, String param
			,Header[] publicHeaders,boolean requestByFormData ) throws UnsupportedEncodingException {
		// 处理url
		System.out.println("请求方式:" + method);
		System.out.println("请求路径:" + url);
		System.out.println("请求参数:" + param.replace("\r\n", "").replace("\n", ""));
		//upload表示上传，也是使用post进行请求
		if ("post".equalsIgnoreCase(method) || "upload".equalsIgnoreCase(method)) {
			// 封装post方法
			HttpPost postMethod = new HttpPost(url);
			postMethod.setHeaders(publicHeaders);
			//如果请求头的content-type的值包含form-data 或者 请求方法为upload(上传)时采用MultipartEntity形式
			HttpEntity entity  = parseEntity(param,requestByFormData || "upload".equalsIgnoreCase(method));
			postMethod.setEntity(entity);
			return postMethod;
		} else if ("put".equalsIgnoreCase(method)) {
			// 封装put方法
			HttpPut putMethod = new HttpPut(url);
			putMethod.setHeaders(publicHeaders);
			HttpEntity entity  = parseEntity(param,requestByFormData );
			putMethod.setEntity(entity);
			return putMethod;
		} else if ("delete".equalsIgnoreCase(method)) {
			// 封装delete方法
			HttpDelete deleteMethod = new HttpDelete(url);
			deleteMethod.setHeaders(publicHeaders);
			return deleteMethod;
		} else {
			// 封装get方法
			HttpGet getMethod = new HttpGet(url);
			getMethod.setHeaders(publicHeaders);
			return getMethod;
		}
	}
	
	
	/**
	 * 格式化参数，如果是form-data格式则将参数封装到MultipartEntity否则封装到StringEntity
	 * @param param 参数
	 * @param formData 是否使用form-data格式
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	protected static HttpEntity parseEntity(String param,boolean formData) throws UnsupportedEncodingException{
		if(formData){
			Map<String, String> paramMap = JSON.parseObject(param,
					HashMap.class);
			MultipartEntity multiEntity = new MultipartEntity();
			for (String key : paramMap.keySet()) {
				String value = paramMap.get(key);
				Matcher m = funPattern.matcher(value);
				if (m.matches() && m.group(1).equals("bodyfile")) {
					value = m.group(2);
					multiEntity.addPart(key, new FileBody(new File(value)));
				} else {
					multiEntity.addPart(key, new StringBody(paramMap.get(key)));
				}
			}
			return multiEntity;
		}else{
			return new StringEntity(param, "UTF-8");
		}
	}
	
	
	/**
	 * 判断返回代码
	 * @param response
	 */
	protected static void checkStatus(HttpResponse response,CaseModel caseModel) {
		int responseStatus = response.getStatusLine().getStatusCode();
		System.out.println("返回状态码："+responseStatus);
		if (caseModel.getFstatus()!= 0) {
			Assert.assertEquals(responseStatus, caseModel.getFstatus(),
					"返回状态码与预期不符合!");
		} 
	}
	
	
	protected static String getResponseData(HttpResponse response) 
			throws IllegalStateException, IOException {
		String responseData;
		HttpEntity respEntity = response.getEntity();
		Header respContentType = response.getFirstHeader("Content-Type");
		if (respContentType != null && respContentType.getValue() != null 
				&&  (respContentType.getValue().contains("download") || respContentType.getValue().contains("octet-stream"))) {
			String conDisposition = response.getFirstHeader(
					"Content-disposition").getValue();
			String fileType = conDisposition.substring(
					conDisposition.lastIndexOf("."),
					conDisposition.length());
			String filePath = "download/" + RandomUtil.getRandom(8, false)
					+ fileType;
			InputStream is = response.getEntity().getContent();
			Assert.assertTrue(FileUtil.writeFile(is, filePath), "下载文件失败。");
			// 将下载文件的路径放到{"filePath":"xxxxx"}进行返回
			responseData = "{\"filePath\":\"" + filePath + "\"}";
			
		} else {
			responseData=EntityUtils.toString(respEntity, "UTF-8");
		}
		// 输出返回数据log
		System.out.println("返回结果:" + responseData);
		return responseData;
	}
	
	/**
	 * 处理断言及保存save
	 * @param responseData
	 * @param apiDataBean
	 * @throws Exception 
	 */
	protected static void verify(String responseData,CaseModel caseModel) throws Error {
		// 验证预期信息
		//TODO contains关系
		verifyResult(responseData, caseModel.getFexpect(),
				false);
		// 对返回结果进行提取保存。
		saveResult(responseData, caseModel.getFsave());
	}
}
