package com.mk.hms.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.http.HTTPException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;

import cn.com.winhoo.mikeweb.exception.MyErrorEnum;

public class HttpTools {
	protected static Logger logger = LoggerFactory.getLogger(HttpTools.class);

	private static int timeout = 32 * 1000;

	public static String getIpAddr() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			return "183.131.145.108";
		}
	}

	/**
	 * 发送请求
	 * 
	 * @param url
	 * @param send
	 * @return
	 * @throws Exception
	 */
	public static String dopostjson(String url, String send) throws Exception {
		logger.info("请求地址：url:{},timeout:{}", url, timeout);
		return postJsonAsHttps(url, send, timeout, 3);
	}

	public static Document StringtoXML(String xmlstring) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder(false);
		StringReader read = new StringReader(xmlstring);
		Document doc = builder.build(read);
		return doc;
	}

	public static String XMLtoString(Document doc) {
		String encoding = "utf-8";
		Format f = Format.getPrettyFormat();
		f.setEncoding(encoding);
		f.setIndent("  ");
		f.setLineSeparator("\r\n");
		XMLOutputter output = new XMLOutputter();
		output.setFormat(f);
		return output.outputString(doc);
	}

	public static String dopost(String url, String send) throws Exception {
		HttpClient theclient = new HttpClient();
		PostMethod method = new PostMethod(url);

		Transaction t = Cat.newTransaction("OtsPost", url);

		try {
			method.setRequestEntity(new StringRequestEntity(send, "text/xml", "UTF-8"));
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10 * 1000);
			theclient.getHttpConnectionManager().getParams().setConnectionTimeout(10 * 1000);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(1, false));
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			int status = theclient.executeMethod(method);
			if (status == HttpStatus.SC_OK) {
				t.setStatus(Transaction.SUCCESS);
				return method.getResponseBodyAsString();
			} else {
				throw new HTTPException(status);
			}
		} catch (Exception e) {
			t.setStatus(e);
			throw e;
		} finally {
			t.complete();
			method.releaseConnection();
		}
	}

	public static X509TrustManager x509m = new X509TrustManager() {
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
	};

	/**
	 * 
	 * @param url
	 * @param send
	 * @param timeout
	 * @param tryTime
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String postJsonAsHttp(String url, String send, int timeout, int tryTime) throws Exception {

		HttpClient theclient = new HttpClient();
		PostMethod method = new PostMethod(url);

		Transaction t = Cat.newTransaction("OtsPostJson", url);

		try {
			method.setRequestEntity(new StringRequestEntity(send, "application/json", "UTF-8"));
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 32 * 1000);
			theclient.getHttpConnectionManager().getParams().setConnectionTimeout(32 * 1000);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(1, false));
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			int status = theclient.executeMethod(method);
			if (status == HttpStatus.SC_OK) {
				t.setStatus(Transaction.SUCCESS);
				return method.getResponseBodyAsString();
			} else {
				logger.info("访问url:{},返回状态:{}", url, status);
				throw new HTTPException(status);
			}
		} catch (Exception ex) {
			t.setStatus(ex);
			throw ex;
		} finally {
			t.complete();
			method.releaseConnection();
		}
	}

	/**
	 * @param url
	 * @param sendParamStr
	 * @param timeout
	 * @param tryTime
	 * @return
	 * @throws Exception
	 */
	public static String postJsonAsHttps(String url, String sendParamStr, int timeout, int tryTime) throws Exception {

		Transaction t = Cat.newTransaction("OtsHttpsPost", url);
		CloseableHttpClient httpclient = null;
		int code = 0;
		try {
			HttpPost post = new HttpPost(url);

			StringEntity entity = new StringEntity(sendParamStr, ContentType.create("application/json", "UTF-8"));
			post.setEntity(entity);
			httpclient = createSSLClientDefault(null);

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();// 设置请求和传输超时时间
			post.setConfig(requestConfig);
			CloseableHttpResponse response = httpclient.execute(post);
			code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				t.setStatus(Transaction.SUCCESS);
				return EntityUtils.toString(response.getEntity(),"UTF-8");
			} else {
				throw new HTTPException(code);
			}
		} catch (Exception e) {
			t.setStatus(e);
			throw e;
		} finally {
			if (httpclient != null) {
				httpclient.close();
			}
			t.complete();
		}
	}

	public static CloseableHttpClient createSSLClientDefault(CookieStore cookieStore) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			// 信任所有
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		}).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
		if (cookieStore != null) {
			return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultCookieStore(cookieStore).build();
		} else {
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		}
	}

	public static String caculateCF(String value, String charset) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(value.getBytes(charset));
			String str = caculateCf(bis);
			bis.close();
			return str;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String caculateCf(InputStream in) throws NoSuchAlgorithmException, IOException {
		byte[] readBytes = new byte[1024 * 500];
		byte[] md5Bytes = null;
		int readCount;
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		while ((readCount = in.read(readBytes)) != -1) {
			md5.update(readBytes, 0, readCount);
		}
		md5Bytes = md5.digest();
		String result = toHexString(md5Bytes);
		return result;
	}

	public final static String toHexString(byte[] res) {
		StringBuffer sb = new StringBuffer(res.length << 1);
		for (int i = 0; i < res.length; i++) {
			String digit = Integer.toHexString(0xFF & res[i]);
			if (digit.length() == 1) {
				digit = '0' + digit;
			}
			sb.append(digit);
		}
		return sb.toString().toUpperCase();
	}

	public static int get100price(String price) {
		try {
			return get100price(new BigDecimal(price));
		} catch (Exception e) {
			throw MyErrorEnum.errorParm.getMyException("支付金额验证有误.");
		}
	}

	public static int get100price(BigDecimal price) {
		try {
			price = price.multiply(new BigDecimal(100));
			return price.intValue();
		} catch (Exception e) {
			throw MyErrorEnum.errorParm.getMyException("支付金额验证有误.");
		}
	}

	public static String getUrl(HttpServletRequest request) {
		String path = request.getContextPath();
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	}

	/**
	 * 32位 UUID
	 */
	public static String getUuid() {
		String uuid = UUID.randomUUID().toString();
		return uuid.toString().replace("-", "");
	}

	/**
	 * yyyymmddh24miss
	 */
	public static String getTimestamp() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(new Date());
	}

	public static boolean  isPositive(BigDecimal bg) {
		 if(bg==null){
			 return false;
		 }else if(bg.compareTo(BigDecimal.ZERO)==1){
			 return true;
		 }
		 return false;
	}
	
	 /**
   	 *yyyymmddh24miss
   	 */
    public static String getYesterday(){
    	Calendar   cal   =   Calendar.getInstance();
    	cal.add(Calendar.DATE,   -1);
    	return  new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
    }
}