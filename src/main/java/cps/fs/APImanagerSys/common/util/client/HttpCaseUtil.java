package cps.fs.APImanagerSys.common.util.client;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

import cps.fs.APImanagerSys.model.APIModel;
import cps.fs.APImanagerSys.model.CaseModel;

/**
 * 发送接口工具
 * @description 
 * @author fangshu
 * @date 2018年10月9日
 */
public class HttpCaseUtil extends HttpUtil{
	
	/**
	 * 所有公共header，会在发送请求的时候添加到http header上
	 */
	private static Header[] publicHeaders;
	/**
	 * 是否使用form-data传参 会在post与put方法封装请求参数用到
	 */
	private static boolean requestByFormData = false;
	
	private static CookieStore cookieStore;
	
	public static CaseModel apiTest(CaseModel caseModel,APIModel apiModel) throws Exception {
		ClientConnectionManager connManager = new PoolingClientConnectionManager();
		/**
		 * 新增新的client
		 */
	    DefaultHttpClient client = new DefaultHttpClient(connManager);
		System.out.println("--- API test start ---");
		String apiParam = buildRequestParam(caseModel);
		//封装参数
		if(apiModel.getFcontenttype() != null && apiModel.getFcontenttype().equals("form")) {
			apiModel.setFaurl(apiModel.getFaurl()+"?"+caseModel.getFrequest());
		}
		// 封装请求方法
		HttpUriRequest method = parseHttpRequest(apiModel.getFaurl(),
				apiModel.getFtype(),caseModel.getFrequest()
				,publicHeaders,false);
		String responseData;
		try {
			client.setCookieStore(cookieStore);
		      // 执行
		    HttpResponse response = client.execute(method);
		      //获取响应头
		    cookieStore= client.getCookieStore();
			checkStatus(response, caseModel);
			responseData = getResponseData(response);
			caseModel.setFresponse(responseData);
		} catch (Exception e) {
			throw e;
		} finally {
			method.abort();
		}
		try {
			verify(responseData, caseModel);
		} catch (Error e) {
			e.printStackTrace();
			caseModel.setAssertResult(e.getMessage());
		}
		return caseModel;
	}
}
