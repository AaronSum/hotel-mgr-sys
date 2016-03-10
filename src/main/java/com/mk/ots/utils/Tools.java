package com.mk.ots.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.type.TypeFactory;

public class Tools {
	static ObjectMapper mapper = new ObjectMapper();

	static int currNoTimes = 0;

	// 获取默认14位数字+前缀字符
	public static String getRandomSeq(String prefix) {
		return Tools.getRandomSeq(prefix, 1).get(0);
	}

	/**
	 *
	 * 获取默认14位数字+前缀字符 获取随机不重复编号
	 *
	 * @param prefix
	 *            前缀
	 * @param num
	 *            获取数量<=1000
	 * @return 列表
	 */
	public synchronized static List<String> getRandomSeq(String prefix, int num) {
		SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmssSSS");
		String pre = prefix + df.format(new Date());

		List<String> result = new ArrayList<String>();
		if (num > 1) {
			for (int i = 0; i <= Math.min(num - 1, 999); i++) {
				result.add(pre + Tools.num2str(Tools.currNoTimes, 3));
				Tools.currNoTimes++;
				if (Tools.currNoTimes >= 1000) {
					Tools.currNoTimes = 0;
				}
			}
		} else {
			result.add(pre + Tools.num2str(Tools.currNoTimes, 3));
			Tools.currNoTimes++;
			if (Tools.currNoTimes >= 1000) {
				Tools.currNoTimes = 0;
			}
		}

		return result;
	}

	public static String getCurrentTIme(String patten) {
		return Tools.getCurrentTime(patten);
	}

	/**
	 *
	 * 获取当前时间字符串
	 *
	 * @param patten
	 *            时间格式，NULL为标准日期时间
	 * @return
	 */
	public static String getCurrentTime(String patten) {
		if (patten == null) {
			patten = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		return sdf.format(new java.util.Date());
	}

	/**
	 * 按char型分隔符分割字符,将所给分隔符变成拆分成多个char,然后分别分割
	 *
	 * @param str
	 *            原始字符
	 * @param spec
	 *            分隔
	 * @return 分割后的字符数组
	 */
	public static String[] strSplit(String str, String spec) {
		return Tools.strSplit(str, spec, false);
	}

	/**
	 * 按char型分隔符分割字符,将所给分隔符变成拆分成多个char,然后分别分割
	 *
	 * @param str
	 *            原始字符
	 * @param spec
	 *            分隔
	 * @param withNull
	 *            是否统计空字符串
	 * @return 分割后的字符数组
	 */
	public static String[] strSplit(String str, String spec, boolean withNull) {
		if (Tools.isEmpty(str)) {
			return new String[0];
		}
		StringTokenizer token = new StringTokenizer(str, spec);
		int count = token.countTokens();
		Vector<String> vt = new Vector<String>();
		for (int i = 0; i < count; i++) {
			String tmp = token.nextToken();
			if (withNull || !Tools.isEmpty(tmp)) {
				vt.addElement(tmp.trim());
			}
		}
		return vt.toArray(new String[0]);
	}

	/**
	 * 替换字符
	 *
	 * @param str
	 *            原是字符
	 * @param pattern
	 *            被替换的字符
	 * @param replace
	 *            要替换成的字
	 * @return 替换后的字符
	 */
	public static String replace(String str, String pattern, String replace) {
		if (str == null) {
			return null;
		}
		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer();
		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}

	/**
	 * 按分隔符分割字符
	 *
	 * @param str
	 *            原始字符
	 * @param spec
	 *            分隔
	 * @return 分割后的字符数组
	 */
	public static String[] str2array(String str, String spec) {
		return Tools.str2array(str, spec, false);
	}

	/**
	 * 按分隔符分割字符
	 *
	 * @param str
	 *            原始字符
	 * @param spec
	 *            分隔
	 * @param withNull
	 *            是否统计空字符串
	 * @return 分割后的字符数组
	 */
	public static String[] str2array(String str, String spec, boolean withNull) {
		if (Tools.isEmpty(str) && !withNull) {
			return new String[0];
		}
		if (Tools.isEmpty(str) && withNull) {
			return new String[] { "" };
		}
		List<String> vt = Tools.str2List(str, spec, withNull);
		String[] ar = vt.toArray(new String[0]);
		return ar;
	}

	/**
	 * 按分隔符分割字符
	 *
	 * @param str
	 *            原始字符
	 * @param spec
	 *            分隔
	 * @param withNull
	 *            是否统计空字符串
	 * @return 分割后的字符链表
	 */
	public static List<String> str2List(String str, String spec, boolean withNull) {
		if (str == null) {
			str = "";
		}
		Vector<String> vt = new Vector<String>();
		while (str.indexOf(spec) != -1) {
			String tmp = str.substring(0, str.indexOf(spec));
			if (withNull || !tmp.equals("")) {
				vt.addElement(tmp.trim());
			}
			str = str.substring(str.indexOf(spec) + spec.length());
		}
		if (withNull || !Tools.isEmpty(str.trim())) {
			vt.addElement(str.trim());
		}
		return vt;
	}

	/**
	 * 判断个字符是否为
	 *
	 * @param source
	 *            要判断字符串
	 * @return 如果为空返回真，反之为假
	 */
	public static boolean isEmpty(String source) {
		return ((source == null) || source.trim().equals(""));
	}

	/**
	 * 判断个对象是否为
	 *
	 * @param source
	 *            要判断对
	 * @return 如果为空返回真，反之为假
	 */
	public static boolean isEmpty(Object source) {
		return ((source == null) || source.toString().trim().equals(""));
	}

	/**
	 * 获取输入流内容变成字节流
	 *
	 * @param is
	 * @param length
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesFromInputStream(InputStream is, int length) throws IOException {
		if (is == null) {
			return null;
		}
		if (length < 0) {
			length = 0;
		}
		byte[] allBytes = new byte[length];
		DataInputStream in = null;
		in = new DataInputStream(is);

		int bytesRead = 0;
		int totalBytesRead = 0;
		int sizeCheck = 0;
		if (length > 0) {
			while (bytesRead != -1) {
				byte[] readBytes = new byte[8192];
				bytesRead = in.read(readBytes);
				if (bytesRead != -1) {
					totalBytesRead += bytesRead;

					if (totalBytesRead <= length) {
						System.arraycopy(Tools.midBytes(readBytes, 0, bytesRead), 0, allBytes, totalBytesRead - bytesRead, bytesRead);
					} else {
						sizeCheck = totalBytesRead - length;
						System.arraycopy(Tools.midBytes(readBytes, 0, bytesRead), 0, allBytes, totalBytesRead - bytesRead, bytesRead - sizeCheck);
						break;
					}
				}
			}
			in.close();

			if (totalBytesRead < length) {
				return Tools.midBytes(allBytes, 0, totalBytesRead);
			} else {
				return allBytes;
			}
		}
		// 读未知长度数据流
		length = 2048 * 1024;
		allBytes = new byte[length];
		while (bytesRead != -1) {
			byte[] readBytes = new byte[8192];
			bytesRead = in.read(readBytes);
			if (bytesRead != -1) {
				totalBytesRead += bytesRead;
				if (totalBytesRead <= length) {
					System.arraycopy(Tools.midBytes(readBytes, 0, bytesRead), 0, allBytes, totalBytesRead - bytesRead, bytesRead);
				} else {
					length += 2048 * 1024;
					byte[] tmpBytes = allBytes;
					allBytes = new byte[length];
					System.arraycopy(tmpBytes, 0, allBytes, 0, tmpBytes.length);
					System.arraycopy(Tools.midBytes(readBytes, 0, bytesRead), 0, allBytes, totalBytesRead - bytesRead, bytesRead - sizeCheck);
				}
			}
		}
		in.close();
		is.close();
		return Tools.midBytes(allBytes, 0, totalBytesRead);
	}

	public static byte[] getFileBytes(File file1) throws IOException {
		if (!file1.exists()) {
			return null;
		}
		FileInputStream fileinputstream = new FileInputStream(file1);
		int fileLength = (int) file1.length();
		return Tools.getBytesFromInputStream(fileinputstream, fileLength);
	}

	/**
	 * 写文件
	 *
	 * @param filename
	 *            文件名
	 * @param dataBytes
	 *            文件数据字节数组
	 * @throws IOException
	 */
	public static void writeFile(File file, byte[] dataBytes) throws IOException {
		if (dataBytes == null) {
			return;
		}
		Tools.writeFile(file, dataBytes, 0, dataBytes.length);
	}

	/**
	 * 写文件
	 *
	 * @param filename
	 *            文件名
	 * @param dataBytes
	 *            文件数据字节数组
	 * @param start
	 *            开始位置
	 * @param length
	 *            文件长度
	 * @throws IOException
	 */
	public static void writeFile(File file, byte[] dataBytes, int start, int length) throws IOException {
		if (dataBytes == null) {
			return;
		}
		String filename = file.getPath();
		// file.mkdirs();
		if (file.exists() && !file.canWrite()) {
			file.delete();
		}
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(filename);
			fileOut.write(dataBytes, start, length);
		} finally {
			try {
				fileOut.close();
			} catch (Throwable t) {
			}
		}
	}

	public static String[] getStreamStringArray(InputStream is, String charsetName, int lineCount) throws IOException {
		return Tools.getStreamStringArray(is, charsetName, false, lineCount);
	}

	public static String[] getStreamStringArray(InputStream is, String charsetName, boolean withNull, int lineCount) throws IOException {
		return Tools.getStreamStringList(is, charsetName, withNull, lineCount).toArray(new String[0]);
	}

	public static List<String> getStreamStringList(InputStream is, String charsetName, boolean withNull, int lineCount) throws IOException {
		Vector<String> vt = new Vector<String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, charsetName));
		String tmp = br.readLine();
		int count = 1;
		while ((tmp != null) && (((lineCount > 0) && (count <= lineCount)) || (lineCount <= 0))) {
			if (!Tools.isEmpty(tmp) || withNull) {
				vt.addElement(tmp.trim());
			}
			tmp = br.readLine();
			count++;
		}
		br.close();
		is.close();
		return vt;
	}

	public static byte[] midBytes(byte[] src, int start, int length) {
		if (start < 0) {
			start = 0;
		}
		if (length <= 0) {
			return null;
		}
		if (start >= src.length) {
			return null;
		}
		if ((start + length) > src.length) {
			length = src.length - start;
		}
		byte[] dBytes = new byte[length];
		System.arraycopy(src, start, dBytes, 0, length);

		return dBytes;
	}

	public static String num2str(int i, int length) {
		String temp = Tools.getLengthChar(length, '0');
		String num = i + "";
		if (num.length() >= temp.length()) {
			return num;
		}
		return temp.substring(0, temp.length() - num.length()) + num;
	}

	static public String getLengthChar(int length, char spec) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(spec);
		}
		return buffer.toString();
	}

	public static String num2str(long i, int length) {
		String temp = Tools.getLengthChar(length, '0');
		String num = i + "";
		if (num.length() >= temp.length()) {
			return num;
		}
		return temp.substring(0, temp.length() - num.length()) + num;
	}

	/**
	 * 将字符整型转换成整形
	 *
	 * @param value
	 *            字符整型
	 * @return 整型
	 */
	public static Integer getIntValue(String value) {
		return Tools.getIntValue(value, 0);
	}

	public static Integer getIntValue(String value, int defaultValue) {
		try {
			value = value.concat(".");
			return Integer.parseInt(value.substring(0, value.indexOf(".")));
		} catch (Throwable e) {
			return defaultValue;
		}
	}

	/**
	 * 将整型对象转换成整形
	 *
	 * @param value
	 *            整型对象
	 * @return 整型
	 */
	public static Integer getIntValue(Object value) {
		if (value == null) {
			return 0;
		}
		return Tools.getIntValue(value.toString());
	}

	public static Double getDoubleValue(String value) {
		if (Tools.isNumber(value)) {
			return Double.parseDouble(value);
		}
		return 0.0d;
	}

	public static boolean isNumber(String value) {
		if ((value == null) || value.equals("")) {
			return false;
		}
		if (value.indexOf(".") != value.lastIndexOf(".")) {
			return false;
		}
		int count = 0;
		for (int i = 0; i < value.length(); i++) {
			if (((value.charAt(i) >= '0') && (value.charAt(i) <= '9')) || (value.charAt(i) == '.')) {
				count++;
			}
		}
		if (count == value.length()) {
			return true;
		}
		return false;
	}

	public static long getLongValue(String value) {
		try {
			value = value.concat(".");
			return Long.parseLong(value.substring(0, value.indexOf(".")));
		} catch (Throwable e) {
			return 0;
		}
	}

	public static long getLongValue(Object value) {
		try {
			if (value == null) {
				return 0;
			}
			if (value instanceof Long) {
				return (Long) value;
			}
			String val = value.toString().concat(".");
			return Long.parseLong(val.substring(0, val.indexOf(".")));
		} catch (Throwable e) {
			return 0;
		}
	}

	public static String obj2json(Object obj) {
		try {
			if (obj == null) {
				return "{}";
			}
			Tools.mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
			Tools.mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			Tools.mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);

			return Tools.mapper.writeValueAsString(obj);
		} catch (Exception e) {
			System.out.println("==>> Object to JSON occer error: " + e);
		}
		return "{}";
	}

	public static String getStringValue(Object val) {
		return Tools.getStringValue(val, "");
	}

	public static String getStringValue(Object val, String defaultValue) {
		if (val == null) {
			return defaultValue;
		}
		return val.toString();
	}

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Collection object) {
		return (null == object) || (object.size() <= 0);
	}

	@SuppressWarnings("deprecation")
	public static <T> Object json2Object(String json, Class<T> clazz, boolean isCollection) {
		Object responseObject = null;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DeserializationConfig deserializationConfig = Tools.mapper.getDeserializationConfig();
		deserializationConfig.setDateFormat(dateFormat);
		try {
			if (isCollection) {
				// 集合时调用
				TypeFactory t = TypeFactory.defaultInstance();
				responseObject = Tools.mapper.readValue(json, t.constructCollectionType(List.class, clazz));
			} else {
				// 普通对象调用
				responseObject = Tools.mapper.readValue(json, clazz);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseObject;
	}

	public static Date getToday() {
		return Tools.getDateFromToday(0);
	}

	/**
	 * 获取当天8位数字型日期
	 *
	 * @return String
	 */
	public static String getTodayString() {
		Calendar calendar = Calendar.getInstance();
		int i = calendar.get(1);
		int j = calendar.get(2) + 1;
		int k = calendar.get(5);
		return i + (j >= 10 ? j + "" : "0" + j) + (k >= 10 ? k + "" : "0" + k);
	}

	/**
	 * 获取从今天开始的天数的日期
	 *
	 * @param avail
	 *            int
	 * @return String
	 */
	public static Date getDateFromToday(int avail) {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		long lg = cal.getTimeInMillis();
		lg = (lg / 1000) * 1000;
		return new java.sql.Date(lg + (avail * 86400000L));
	}

	/**
	 * 获取从今天开始的天数的日期
	 *
	 * @param avail
	 *            int
	 * @return String
	 */
	public static Date getDatetimeFromToday(int avail) {
		long lg = System.currentTimeMillis();
		return new java.sql.Timestamp(lg + (avail * 86400000L));
	}

	/**
	 * 将毫秒数字转换为日期
	 *
	 * @param mill
	 *            毫秒
	 * @return yyyy-mm-dd 格式的日期字符串
	 */
	public static String getDateString(long mill) {
		java.sql.Date date = new java.sql.Date(mill);
		return date.toString();
	}

	/**
	 * 将毫秒数字转换为日期时间
	 *
	 * @param mill
	 *            毫秒
	 * @return yyyy-mm-dd hh:mi:ss 格式的日期时间字符串
	 */
	public static String getDateTimeString(long mill) {
		java.sql.Timestamp date = new java.sql.Timestamp(mill);
		String sDate = date.toString();
		return sDate.substring(0, sDate.lastIndexOf("."));
	}

	/**
	 * 将字符串日期换成java.sql.Date
	 *
	 * @param sDate
	 *            有效时间字符�\uFFFD
	 * @return 返回日期
	 */
	public static java.sql.Date str2date(String sDate) {
		if ((sDate == null) || sDate.equals("")) {
			return null;
		}
		if ((sDate.charAt(0) > '9') || (sDate.charAt(0) < '0')) {
			return null;
		}
		sDate = sDate.replace('/', '-');
		String[] ar = Tools.strSplit(sDate, "- :");
		if (ar.length < 3) {
			return null;
		}
		return java.sql.Date.valueOf(ar[0] + "-" + ar[1] + "-" + ar[2]);
	}

	/**
	 * 根据名称Map获取集合总称（逗号分隔）
	 *
	 * @param ids
	 *            ID集合
	 * @param nameMap
	 *            名称Map
	 * @return
	 */
	public static String getNameByMap(List<Long> ids, Map<Long, String> nameMap) {
		String name = "";
		if (ids != null) {
			for (int j = 0; j < ids.size(); j++) {
				if (j > 0) {
					name += ",";
				}
				name += nameMap.get(ids.get(j));
			}
		}
		return name;
	}

	/**
	 * 判断是否为闰�\uFFFD
	 *
	 * @param year
	 *            要判断的年份
	 * @return 如果为闰年返回真，反之为�\uFFFD
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 4) != 0) {
			return false;
		}
		return ((year % 100) != 0) || ((year % 400) == 0);
	}

	/**
	 * 判断是否为日�\uFFFD
	 *
	 * @param year
	 *            要判断的年份
	 * @param month
	 *            要判断的月份
	 * @param day
	 *            要判断的日期
	 * @return 如果为正确日期返回真，反之为�\uFFFD
	 */
	public static boolean isDate(String year, String month, String day) {
		int[] day_of_months = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		try {
			if (year.length() != 4) {
				return false;
			}
			int iYear = Tools.getIntValue(year);
			int iMonth = Tools.getIntValue(month);
			int iDay = Tools.getIntValue(day);
			if (Tools.isLeapYear(iYear)) {
				day_of_months[1] = 29;
			}
			if ((iMonth < 1) || (iMonth > 12)) {
				return false;
			}
			if ((iDay < 1) || (iDay > day_of_months[iMonth - 1])) {
				return false;
			}
			return true;
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 判断�\uFFFD个字符是否为日期
	 *
	 * @param sDate
	 *            要判断的日期字符
	 * @return 如果为正确日期返回真，反之为�\uFFFD
	 */
	public static boolean isDate(String sDate) {
		if (Tools.isEmpty(sDate)) {
			return false;
		}
		if (sDate.length() > 23) {
			return false;
		}
		sDate = sDate.concat(" ");
		sDate = sDate.substring(0, sDate.indexOf(" ")).trim();
		sDate = sDate.replace('/', '-');
		if (sDate.equals("")) {
			return false;
		}
		String[] date = Tools.str2array(sDate, "-");
		if (date.length < 3) {
			return false;
		}
		return Tools.isDate(date[0], date[1], date[2]);
	}

	/**
	 * 获取子类实体
	 *
	 * @param c
	 *            子类类
	 * @param p
	 *            父类实体
	 * @return
	 */
	public static <T> T getChildClass(Class<T> c, Object p) {
		try {
			T o = c.newInstance();
			Method[] rs = p.getClass().getMethods();
			Map<String, Object> map = new HashMap<String, Object>();
			for (Method method : rs) {
				String mn = method.getName();
				if (mn.startsWith("get")) {
					Object val = method.invoke(p, (Object[]) null);
					map.put("set" + mn.substring(3), val);
				} else if (mn.startsWith("is")) {
					Object val = method.invoke(p, (Object[]) null);
					map.put("set" + mn.substring(2), val);
				}
			}
			rs = c.getMethods();
			for (Method method : rs) {
				String mn = method.getName();
				if (mn.startsWith("set")) {
					if (map.containsKey(mn)) {
						method.invoke(o, new Object[] { map.get(mn) });
					}
				}
			}
			return o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
