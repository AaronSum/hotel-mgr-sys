package com.mk.hms.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.security.cert.CertificateException;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mk.hms.model.OutModel;

import net.sf.json.JSONObject;

/**
 * http client 工具类
 * 
 * @author hdy
 *
 */
public class HttpClientUtils {

	// 超时间隔
	private static int connectTimeOut = 20000;
	// 返回数据编码格式
	private static String encoding = "UTF-8";
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	/**
	 * post请求
	 * 
	 * @param address
	 *            请求地址
	 * @param params
	 *            参数集合
	 * @throws IOException
	 *             异常
	 */
	public static JSONObject post(String address, Map<String, String> params) throws IOException {
		/*
		 * RestTemplate rest = new RestTemplate(); ResponseEntity<HashMap>
		 * responseEntity = rest.postForEntity(address, params, HashMap.class);
		 * System.out.println(responseEntity.getStatusCode());
		 * System.out.println(responseEntity.getBody().toString());
		 */
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httpPost = new HttpPost(address);
		// 设置超时
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(HttpClientUtils.connectTimeOut).setConnectTimeout(HttpClientUtils.connectTimeOut).build();
		httpPost.setConfig(requestConfig);
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		// 追加参数
		for (String s : params.keySet()) {
			formparams.add(new BasicNameValuePair(s, params.get(s)));
		}
		UrlEncodedFormEntity uefEntity = null;
		CloseableHttpResponse response = null;
		JSONObject entityObj = null;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, HttpClientUtils.encoding);
			httpPost.setEntity(uefEntity);
			response = httpclient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String entityStr = EntityUtils.toString(entity, HttpClientUtils.encoding);
					entityObj = JSONObject.fromObject(entityStr);
					HttpClientUtils.logger.info("Response content: " + entityStr);
				}
			}

		} catch (UnsupportedEncodingException e) {
			HttpClientUtils.logger.error("http请求异常(UnsupportedEncodingException)：" + e.getMessage(), e);
		} catch (ClientProtocolException e) {
			HttpClientUtils.logger.error("http请求异常(ClientProtocolException)：" + e.getMessage(), e);
		} catch (ParseException e) {
			HttpClientUtils.logger.error("http请求异常(ParseException)：" + e.getMessage(), e);
		} catch (IOException e) {
			HttpClientUtils.logger.error("http请求异常(IOException)：" + e.getMessage(), e);
		} finally {
			if(null!=response)
				response.close();
			if(null!=httpclient)
				httpclient.close();
		}
		return entityObj;
	}
	
	/**
	 * 异步调用http请求
     */
    public static void doPostAsyn(final String address, final Map<String, String> params, final OutModel out) {

        new Thread(new Runnable() {
            @Override
            public void run() {
            	try {
            		JSONObject obj = post(address, params);
            		if(null == out){
            			return;
            		}
    				if (null == obj) {
    					out.setSuccess(false);
    				}
    				if (!obj.getBoolean("success")) {
    					out.setSuccess(false);
    					out.setErrorMsg(obj.getString("errmsg"));
    				}else{
    					out.setSuccess(true);    					
    				}
				} catch (IOException e) {
					e.printStackTrace();
					out.setSuccess(false);
				}
            }
        }).start();
    }
}
