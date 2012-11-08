package com.bs.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLHandshakeException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;

import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName 类名：httpClient工具
 * @Description 功能说明：
 * <p>
 * TODO httpClient工具
 *</p>
 ************************************************************************
 * @date        创建日期：2011-3-7
 * @author      创建人：林光延
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2011-3-7   林光延  创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class HttpClientUtil {
	
	private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
	
	public  static final String CHARSET_GBK = "GBK";
	
	private static DefaultHttpClient  hpcliet= null;

	/**
	 * HttpRequest会抛出的IOException通常中某些的异常比如服务器无响应,连接被重置等情况
	 * 通常被认为是非致命可恢复的,可以通过设置自动异常回复进行重试
	 * 
	 * @return 自定义的自动异常恢复处理
	 */
	public static HttpRequestRetryHandler getRetryHandler() {
		// 常见的一种自动异常回复,在允许的情况下进行最多10次重试
		HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception,
					int executionCount, HttpContext context) {
				if (executionCount >= 10) {
					// Do not retry if over max retry count
					return false;
				}
				if (exception instanceof NoHttpResponseException) {
					// Retry if the server dropped connection on us
					return true;
				}
				if (exception instanceof SSLHandshakeException) {
					// Do not retry on SSL handshake exception
					return false;
				}
				HttpRequest request = (HttpRequest) context
						.getAttribute(ExecutionContext.HTTP_REQUEST);
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					// Retry if the request is considered idempotent
					return true;
				}
				return false;
			}
		};
		return retryHandler;
	}

	
	private static DefaultHttpClient crtHttpClient(){
		HttpParams params = new BasicHttpParams();
		// 增加最大连接到200
		 // 模拟浏览器，一些服务器程序只允许浏览器访问
		params.setParameter(CoreProtocolPNames.USER_AGENT,
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
		params.setParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
		params.setParameter(
				CoreProtocolPNames.HTTP_CONTENT_CHARSET, CHARSET_GBK);

		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

       // 设置超时
		params.setIntParameter("http.socket.timeout", 180000);
		//设置关闭Nagle算法
		params.setBooleanParameter("http.tcp.nodelay", false);
		
		// 连接超时
		params.setIntParameter("http.connection.timeout", 160000);

		
		//Cookie管理策略,
	/*	HttpClientParams.setCookiePolicy(params,
				CookiePolicy.BROWSER_COMPATIBILITY);

		// 只生成单个Cookie header传输到服务器
		params.setParameter(
				"http.protocol.single-cookie-header", true);*/
		
		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager();
		cm.setMaxTotal(25000);
		// 增加每个路由的默认最大连接到20
		cm.setDefaultMaxPerRoute(25000);
		// 对localhost:80增加最大连接到50
		HttpHost localhost = new HttpHost("locahost", 80);
		cm.setMaxForRoute(new HttpRoute(localhost),25000);

        DefaultHttpClient   httpclient= new DefaultHttpClient(cm, params);
     // 设置自动异常回复
        httpclient.setHttpRequestRetryHandler(getRetryHandler());
        
        IdleConnectionMonitorThread thd = new IdleConnectionMonitorThread(cm);
        thd.start();
		return httpclient;
	}
	
	public static DefaultHttpClient getHttpClient() {
		
	    if(hpcliet == null){
	    	hpcliet =crtHttpClient();
	    };
	    return hpcliet;
	}
	
	
	public static String excuteGet(DefaultHttpClient httpClient, String url){
		if (url == null || url.trim().length()==0) {
			 return "";
		 }
		 String url2= StringUtils.substringBefore(url, "?");
		 String paramList= StringUtils.substringAfter(url, "?");
		 String[] paramArray = StringUtils.split(paramList, "&");
		 HashMap<String,String> params = new HashMap<String,String>();
		 for(int i =0;i< paramArray.length;i++){
			 String[] param = new String[2];
		     param[0] = StringUtils.substringBefore(paramArray[i], "=");
		     param[1] = StringUtils.substringAfter(paramArray[i], "=");
		     params.put(param[0], param[1]);
		 }
		 return excuteGet(httpClient,url2,params,null);
	}
	
	public static String excuteGet(DefaultHttpClient httpClient, String url, Map<String, String> params){
          return excuteGet(httpClient,url,params,null);
	}
	
	public static String excuteGet(DefaultHttpClient httpClient, String url, Map<String, String> params, String charset) {
		    if (url == null || url.trim().length()==0) {
			 return "";
		    }
		    if(charset == null || charset.trim().length()==0){
		    	charset = CHARSET_GBK;
		    }
			List<NameValuePair> qparams = getParamsList(params);
			if (qparams != null && qparams.size() > 0) {
			charset = (charset == null ? CHARSET_GBK : charset);
			String formatParams = URLEncodedUtils.format(qparams, charset);
			url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams) : (url
			.substring(0, url.indexOf("?") + 1) + formatParams);
			}
			HttpGet hg = new HttpGet(url);
			HttpContext context =  new BasicHttpContext();
			// 发送请求，得到响应
			try {
				
				log.debug("执行GETt请求: " + hg.getURI());
				HttpResponse response = httpClient.execute(hg, context);
				log.debug("执行GET结果: " + response.getStatusLine());
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity, charset);
				EntityUtils.consume(entity);
				hg.abort();
				//httpClient.getConnectionManager().shutdown();
				return result;
			} catch (Exception e) {
				hg.abort();
				//httpClient.getConnectionManager().shutdown();
				//hpcliet = null;
				log.debug("执行GET出错: " + e.getMessage());
				return  "";
			}
			
	}

	
	public static String excutePost(DefaultHttpClient httpClient, String url, Map<String, String> params){
        return excutePost(httpClient,url,params,null);
	}
	
	public static String excutePost(DefaultHttpClient httpClient, String url){
		if (url == null || url.trim().length()==0) {
			 return "";
		 }
		 String url2= StringUtils.substringBefore(url, "?");
		 String paramList= StringUtils.substringAfter(url, "?");
		 String[] paramArray = StringUtils.split(paramList, "&");
		 HashMap<String,String> params = new HashMap<String,String>();
		 for(int i =0;i< paramArray.length;i++){
		     String[] param = new String[2];
		     param[0] = StringUtils.substringBefore(paramArray[i], "=");
		     param[1] = StringUtils.substringAfter(paramArray[i], "=");
		    	 //StringUtils.split(, "=");
		     params.put(param[0], param[1]);
		 }
		 return excutePost(httpClient,url2,params,null);
	}
	
	public static String excutePost(DefaultHttpClient httpClient, String url, Map<String, String> params, String charset) {
		    if (url == null || url.trim().length()==0) {
			 return "";
		    }
		    if(charset == null || charset.trim().length()==0){
		    	charset = CHARSET_GBK;
		    }
		    UrlEncodedFormEntity formEntity = null;
		    try {
		    if (charset == null || StringUtils.isEmpty(charset)) {
		    formEntity = new UrlEncodedFormEntity(getParamsList(params));
		    } else {
		    formEntity = new UrlEncodedFormEntity(getParamsList(params), charset);
		    }
		    } catch (UnsupportedEncodingException e) {
		    throw new IllegalArgumentException("不支持的编码集", e);
		    }
		    
		    HttpPost hp = new HttpPost(url);
		    HttpContext context =  new BasicHttpContext();
		    hp.setEntity(formEntity);

		 // 发送请求，得到响应
			try {
				
				log.debug("执行POST请求: " + hp.getURI());
				HttpResponse response = httpClient.execute(hp, context);
				log.debug("执行POST结果: " + response.getStatusLine());
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity, charset);
				EntityUtils.consume(entity);
				hp.abort();
				//httpClient.getConnectionManager().shutdown();
				return result;
			} catch (Exception e) {
				hp.abort();
				//httpClient.getConnectionManager().shutdown();
				//hpcliet = null;
				log.debug("执行POST出错: " + e.getMessage());
				return  "";
			}

	}
	
	/**
	 * 
	 * <p>函数名称：excutePostUrl        </p>
	 * <p>功能说明：执行Post请求
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param httpClient
	 * @param url 完整的Url地址
	 * @return
	 *
	 * @date   创建时间：2011-4-19
	 * @author 作者：Josen.Lin
	 */
	public static String excutePostUrl(DefaultHttpClient httpClient, String url) {
	    if (url == null || url.trim().length()==0) {
		 return "";
	    }
	    
	    HttpPost hp = new HttpPost(url);
	    HttpContext context =  new BasicHttpContext();

	 // 发送请求，得到响应
		try {
			
			log.debug("执行POST请求: " + hp.getURI());
			HttpResponse response = httpClient.execute(hp, context);
			log.debug("执行POST结果: " + response.getStatusLine());
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, CHARSET_GBK);
			EntityUtils.consume(entity);
			hp.abort();
			//httpClient.getConnectionManager().shutdown();
			return result;
		} catch (Exception e) {
			hp.abort();
			//httpClient.getConnectionManager().shutdown();
			//hpcliet = null;
			log.debug("执行POST出错: " + e.getMessage());
			return  "";
		}

}


	
	
		
	    
	    /**
	    * 将传入的键/值对参数转换为NameValuePair参数集
	    * 
	    * @param paramsMap
	    * 参数集, 键/值对
	    * @return NameValuePair参数集
	    */
	  private static  List<NameValuePair> getParamsList(Map<String, String> paramsMap ){
	      if (paramsMap == null || paramsMap.size() == 0) {
	       return null;
	      }
	      List<NameValuePair> params = new ArrayList<NameValuePair>();
	      for (Map.Entry<String, String> map : paramsMap.entrySet()) {
	          params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
	      }
	      return params;
	    }


}
