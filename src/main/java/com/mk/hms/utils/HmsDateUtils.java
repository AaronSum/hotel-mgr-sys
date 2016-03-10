/*
 * Copyright (c) 2011 Ruaho All rights reserved.
 */
package com.mk.hms.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDateTime;

/***
 * 处理所有和日期相关的处理.
 * @author Jerry Li
 * @version $Id$
 */
public class HmsDateUtils extends DateUtils {
    /*** 系统总的失效日期. */
    public static final String DATE_FOREVER = "9999-12-31";
    /** 时间格式. yyyy-MM-dd HH:mm:ss*/
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    /** 时间格式. yyyy年MM月dd日*/
    public static final String FORMAT_DATE_CN = "yyyy年MM月dd日";
    /** 时间格式. yyyy-MM-dd HH:mm:ss:SSS */
    public static final String FORMAT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss:SSS";
    /** 到小时分钟的日期格式. yyyy-MM-dd HH:mm*/
    public static final String FORMAT_DATETIME_HM = "yyyy-MM-dd HH:mm";
    /** 全时间格式. yyMMddHHmmssSSS*/
    public static final String FORMAT_FULLTIME = "yyMMddHHmmssSSS";
    /** 日期格式. yyyy-MM-dd*/
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    /** 日期格式. yyyy-MM*/
    public static final String FORMAT_YEARMONTH = "yyyy-MM";
    /**日期格式 yyyyMM*/
    public static final String FORMAT_YEARMONTH_NOPARTING = "yyyyMM";
    /** 纯时间格式. HH:mm:ss*/
    public static final String FORMAT_TIME = "HH:mm:ss";

    /**
	 * 检测check时间是否在begin和end之间，只比较日期
	 * @param check
	 * @param begin
	 * @param end
	 * @return
	 */
	public static boolean checkDate(Date check,Date begin,Date end){
		String c = getFormatDateString(check, "yyyy-MM-dd 00:00:00");
		String b = getFormatDateString(begin, "yyyy-MM-dd 00:00:00");
		String e = getFormatDateString(end, "yyyy-MM-dd 00:00:00");
		if(getDiffHoure(c, b)>0){
			return false;
		}
		if(getDiffTime(c, e)<0){
			return false;
		}
		return true;
	}
    /**
	 * 格式化某个时间
	 * @param date 时间对象
	 * @param format 日期格式化
	 * @return 格式化之后日期字符串
	 */
	public static String getFormatDateString(Date date, String format) {
		SimpleDateFormat foramt = new SimpleDateFormat(format);
		return foramt.format(date);
	}
	
	/**
	 * 获取指定格式的当前日期
	 * @param format 日期格式
	 * @return 当前格式化日期字符串
	 */
	public static String getNowFormatDateString(String format) {
		SimpleDateFormat foramt = new SimpleDateFormat(format);
		Date date = new Date();
		return foramt.format(date);
	}
	
    /**
	 * 日期字符串转化成日期对象
	 * @param dateString 日期字符串
	 * @return date对象
	 * @throws ParseException 异常
	 */
	public static Date getDateFromeDateString(String dateString, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(dateString);
	}
	
    /**
	 * 获取当天时间凌晨时刻
	 * @return 时间戳
	 * @throws ParseException  异常
	 */
	public static long getTodayStateTimeVal() throws ParseException {
		String nowDate = getNowFormatDateString(FORMAT_DATE);
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME);
	    Date date = sdf.parse(nowDate + " 00:00:00"); 
	    return date.getTime();
	}
	
	/**
	 * 获取当天时间凌晨时刻
	 * @return 时间戳
	 * @throws ParseException  异常
	 */
	public static long getTodayEndTimeVal() throws ParseException {
		String nowDate = getNowFormatDateString(FORMAT_DATE);
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME);
	    Date date = sdf.parse(nowDate + " 23:59:59");
	    return date.getTime();
	}
	
	/**
	 * 获取当前月第一天时间戳
	 * @return 时间戳
	 * @throws ParseException 
	 */
	public static long getThisMonthStateTimeVal() throws ParseException {
		//获取当前月第一天：
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String dateString = getFormatDateString(c.getTime(), FORMAT_DATE);
        return getDateFromeDateString(dateString + " 00:00:00", FORMAT_DATETIME).getTime();
	}
	
	/**
	 * 获取当前余额最后一天时间戳
	 * @return 时间戳
	 * @throws ParseException 
	 */
	public static long getThisMonthEndTimeVal() throws ParseException {
		Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String dateString = getFormatDateString(ca.getTime(), FORMAT_DATE);
        return getDateFromeDateString(dateString + " 23:59:59", FORMAT_DATETIME).getTime();
	}
	
	/**
	 * 获取当月第几天时间戳，以00:00:00为准
	 * @param days 追加天数
	 * @return 时间戳
	 * @throws ParseException 异常
	 */
	public static long getThisMonthOneDayTimeVal(int days) throws ParseException {
		return addMonths(new Date(getThisMonthStateTimeVal()), days).getTime();
	}
	/**
	 * 获取小时
	 * @param hms 时间串，格式为hh:mm 或者是hh:mm:ss
	 * @return 小时整数
	 * @throws Exception 
	 */
	public static String getHMSFromHMSString(String hms) throws Exception {
		if (HmsStringUtils.isBlank(hms)) {
			throw new Exception("时间串不可为空");
		}
		int len = hms.split(ContentUtils.CHAR_COLON).length;
		if (len > 3 || len < 1) {
			throw new Exception("时间串不合法");
		}
		if (len == 2) {
			hms += "00";
		}
		return hms.replaceAll(ContentUtils.CHAR_COLON, "");
	}
	
    /**
     * compare two kinds String with format : 12:00 , 08:00; or 12:00:00, 08:00:00.<br>
     * <br>
     * @param firstTime the first time string.
     * @param secondTime the second time string.
     * @return 0 -- same 1 -- first bigger than second -1 -- first smaller than second -2 -- invalid time format
     */
    public static int compareOnlyByTime(String firstTime, String secondTime) {
        try {
            String timeDelm = ":";

            // calculate the first time to integer
            int pos = firstTime.indexOf(timeDelm);
            int iFirst = Integer.parseInt(firstTime.substring(0, pos)) * 10000;
            firstTime = firstTime.substring(pos + 1);
            pos = firstTime.indexOf(timeDelm);

            if (pos > 0) {
                iFirst = iFirst + (Integer.parseInt(firstTime.substring(0, pos)) * 100)
                        + Integer.parseInt(firstTime.substring(pos + 1));
            } else {
                iFirst = iFirst + (Integer.parseInt(firstTime) * 100);
            }

            // calculate the second time string to integer
            pos = secondTime.indexOf(timeDelm);
            int iSecond = Integer.parseInt(secondTime.substring(0, pos)) * 10000;
            secondTime = secondTime.substring(pos + 1);
            pos = secondTime.indexOf(timeDelm);

            if (pos > 0) {
                iSecond = iSecond + (Integer.parseInt(secondTime.substring(0, pos)) * 100)
                        + Integer.parseInt(secondTime.substring(pos + 1));
            } else {
                iSecond = iSecond + (Integer.parseInt(secondTime) * 100);
            }

            // compare two
            if (iFirst == iSecond) {
                return 0;
            } else if (iFirst > iSecond) {
                return 1;
            } else {
                return -1;
            }
        } catch (Exception e) {
            return -2;
        }
    }

    /**
     * 根据规定格式的字符串得到Calendar.<br>
     * <br>
     * @param dateString 日期串.
     * @return 对应Calendar
     */
    public static Calendar getCalendar(String dateString) {
        Calendar calendar = Calendar.getInstance();
        String[] items = dateString.split("[-| |:]");
        int len = items.length;
        if (len > 0) {
            calendar.set(Calendar.YEAR, Integer.parseInt(items[0]));
        }
        if (len > 1) {
            calendar.set(Calendar.MONTH, Integer.parseInt(items[1]) - 1);
        } else {
            calendar.set(Calendar.MONTH, 0);
        }
        if (len > 2) {
            calendar.set(Calendar.DATE, Integer.parseInt(items[2]));
        } else {
            calendar.set(Calendar.DATE, 1);
        }
        if (len > 3) {
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(items[3]));
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
        }
        if (len > 4) {
            calendar.set(Calendar.MINUTE, Integer.parseInt(items[4]));
        } else {
            calendar.set(Calendar.MINUTE, 0);
        }
        if (len > 5) {
            calendar.set(Calendar.SECOND, Integer.parseInt(items[5]));
        } else {
            calendar.set(Calendar.SECOND, 0);
        }
        return calendar;
    }

    /**
     * 得到与当前日期相差指定天数的日期字符串.<br>
     * <br>
     * @param days 前后的天数，正值为后， 负值为前.
     * @return 日期字符串
     */
    public static String getCertainDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        return getStringFromDate(calendar.getTime(), FORMAT_DATE);
    }

    /**
     * 得到与指定日期相差指定天数的日期字符串.<br>
     * <br>
     * @param dateString 指定的日期.
     * @param days 前后的天数，正值为后， 负值为前.
     * @return 日期字符串
     */
    public static String getCertainDate(String dateString, int days) {
        Calendar calendar = getCalendar(dateString);
        calendar.add(Calendar.DATE, days);
        return getStringFromDate(calendar.getTime(), FORMAT_DATE);
    }

    /**
     * 得到与指定日期相差指定天数的日期字符串.<br>
     * <br>
     * @param dateString 指定的日期.
     * @param period 前后的天数，正值为后， 负值为前.
     * @param periodType 周期类别 可以是天、月、年.
     * @return 日期字符串
     */
    public static String getCertainDate(String dateString, int period, int periodType) {
        Calendar calendar = getCalendar(dateString);

        switch (periodType) {
        case 1: // 天
            calendar.add(Calendar.DATE, period);
            break;
        case 2: // 月
            calendar.add(Calendar.MONTH, period);
            break;
        case 3: // 年
            calendar.add(Calendar.MONTH, period * 12);
            break;
        default:
        }
        return getStringFromDate(calendar.getTime(), FORMAT_DATE);
    }

    /**
     * 某日期（带时间）加上几天得到另外一个日期(带时间).<br>
     * <br>
     * @param datetime 需要调整的日期(带时间).
     * @param days 调整天数.
     * @return 调整后的日期(带时间)
     */
    public static String getCertainDatetime(String datetime, int days) {
        Date curDate = getDateFromString(datetime, FORMAT_DATETIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.add(Calendar.DATE, days);
        return getStringFromDate(calendar.getTime(), FORMAT_DATETIME);
    }

    /**
     * 得到与当前日期相差指定月数的日期字符串.
     * @param dif 前后的月数，正值为后， 负值为前.
     * @return 日期字符串
     */
    public static String getCertainMonth(int dif) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, dif);
        return getStringFromDate(calendar.getTime(), FORMAT_DATE);
    }

    /**
     * 得到与当前日期相差指定月数的日期字符串.
     * @param dif 前后的月数，正值为后， 负值为前.
     * @param format 格式
     * @return 日期字符串
     */
    public static String getCertainMonth(int dif, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, dif);
        return getStringFromDate(calendar.getTime(), format);
    }

    /**
     * 得到当前日期的中文日期字符串.<br>
     * <br>
     * @return 中文日期字符串
     */
    public static String getChineseDate() {
        return getChineseDate(getDate());
    }

    /**
     * 根据日期值得到中文日期字符串.<br>
     * <br>
     * @param date 日期值.
     * @return 中文日期字符串
     */
    public static String getChineseDate(String date) {
        if (date.length() < Integer.valueOf("10")) {
            return "";
        } else {
            String year = date.substring(0, 4); // 年
            String month = date.substring(5, 7); // 月
            String day = date.substring(8, 10); // 日
            String y1 = year.substring(0, 1); // 年 字符1
            String y2 = year.substring(1, 2); // 年 字符1
            String y3 = year.substring(2, 3); // 年 字符3
            String y4 = year.substring(3, 4); // 年 字符4
            String m2 = month.substring(1, 2); // 月 字符2
            String d1 = day.substring(0, 1); // 日 1
            String d2 = day.substring(1, 2); // 日 2
            String cy1 = getChineseNum(y1);
            String cy2 = getChineseNum(y2);
            String cy3 = getChineseNum(y3);
            String cy4 = getChineseNum(y4);
            String cm2 = getChineseNum(m2);
            String cd1 = getChineseNum(d1);
            String cd2 = getChineseNum(d2);
            String cYear = cy1 + cy2 + cy3 + cy4 + "年";
            String cMonth = "月";

            if (Integer.parseInt(month) > 9) {
                cMonth = "十" + cm2 + cMonth;
            } else {
                cMonth = cm2 + cMonth;
            }

            String cDay = "日";

            if (Integer.parseInt(day) > 9) {
                cDay = cd1 + "十" + cd2 + cDay;
            } else {
                cDay = cd2 + cDay;
            }

            String chineseday = cYear + cMonth + cDay;
            return chineseday;
        }
    }

    /**
     * 得到当前日期的星期数 : 例如 '星期一', '星期二'等.<br>
     * <br>
     * @return 当前日期的星期数
     */
    public static String getChineseDayOfWeek() {
        return getChineseDayOfWeek(getDate());
    }

    /**
     * 得到指定日期的星期数.<br>
     * <br>
     * @param strDate 指定日期字符串.
     * @return 指定日期的星期数
     */
    public static String getChineseDayOfWeek(String strDate) {
        Calendar calendar = getCalendar(strDate);

        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String strWeek = "";

        switch (week) {
        case Calendar.SUNDAY:
            strWeek = "星期日";
            break;
        case Calendar.MONDAY:
            strWeek = "星期一";
            break;
        case Calendar.TUESDAY:
            strWeek = "星期二";
            break;
        case Calendar.WEDNESDAY:
            strWeek = "星期三";
            break;
        case Calendar.THURSDAY:
            strWeek = "星期四";
            break;
        case Calendar.FRIDAY:
            strWeek = "星期五";
            break;
        case Calendar.SATURDAY:
            strWeek = "星期六";
            break;
        default:
            strWeek = "星期一";
            break;
        }

        return strWeek;
    }

    /**
     * 根据数字得到中文数字.<br>
     * <br>
     * @param number 数字.
     * @return 中文数字
     */
    public static String getChineseNum(String number) {
        String chinese = "";
        int x = Integer.parseInt(number);

        switch (x) {
        case 0:
            chinese = "零";
            break;
        case 1:
            chinese = "一";
            break;
        case 2:
            chinese = "二";
            break;
        case 3:
            chinese = "三";
            break;
        case 4:
            chinese = "四";
            break;
        case 5:
            chinese = "五";
            break;
        case 6:
            chinese = "六";
            break;
        case 7:
            chinese = "七";
            break;
        case 8:
            chinese = "八";
            break;
        case 9:
            chinese = "九";
            break;
        default:
        }
        return chinese;
    }

    /**
     * 根据日期值得到中文日期字符串.<br>
     * <br>
     * @param date 给定日期.
     * @return 2005年09月23日格式的日期
     */
    public static String getChineseTwoDate(String date) {
        if (date.length() < 10) {
            return "";
        } else {
            String year = date.substring(0, 4); // 年
            String month = date.substring(5, 7); // 月
            String day = date.substring(8, 10); // 日

            return year + "年" + month + "月" + day + "日";
        }
    }

    /**
     * 自定义当前时间格式.<br>
     * <br>
     * @param customFormat 自定义格式
     * @return 日期时间字符串
     */
    public static String getCustomDateTime(String customFormat) {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), customFormat);
    }

    /**
     * 得到当前的日期字符串.<br>
     * <br>
     * @return 日期字符串
     */
    public static String getDate() {
        return getDate(Calendar.getInstance());
    }

    /**
     * 得到指定日期的字符串.<br>
     * <br>
     * @param calendar 指定的日期.
     * @return 日期字符串
     */
    public static String getDate(Calendar calendar) {
        return getStringFromDate(calendar.getTime(), FORMAT_DATE);
    }

    /**
     * 得到当前的日期字符串.<br>
     * <br>
     * @return 日期字符串
     */
    public static String getDateCN() {
        return getStringFromDate(Calendar.getInstance().getTime(), FORMAT_DATE_CN);
    }

    /**
     * 得到日期字符串.<br>
     * <br>
     * @param dateStr 日期字符串
     * @return 日期字符串
     */
    public static String getDateCN(String dateStr) {
        Date date = getDateFromString(dateStr);
        return getStringFromDate(date, FORMAT_DATE_CN);
    }

    /**
     * 某日期加上几天得到另外一个日期.<br>
     * <br>
     * @param addNum 要增加的天数.
     * @param getDate 某日期.
     * @return 与某日期相差addNum天的日期
     */
    public static String getDateAdded(int addNum, String getDate) {
        return getCertainDate(getDate, addNum);
    }

    /**
     * 将指定格式的字符串格式化为日期.<br>
     * <br>
     * @param s 字符串内容.
     * @return 日期
     */
    public static Date getDateFromString(String s) {
        int len = s.length();
        String format;
        switch (len) {
        case 19:
            format = FORMAT_DATETIME;
            break;
        case 24:
            format = FORMAT_TIMESTAMP;
            break;
        default:
            format = FORMAT_DATE;
        }
        return getDateFromString(s, format);
    }

    /**
     * 将指定格式的字符串格式化为日期.<br>
     * <br>
     * @param s 字符串内容.
     * @param format 字符串格式.
     * @return 日期
     */
    public static Date getDateFromString(String s, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(s);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 得到当前的日期时间字符串.<br>
     * <br>
     * @return 日期时间字符串
     */
    public static String getDatetime() {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), FORMAT_DATETIME);
    }
    
    /**
     * 得到当前的日期时间字符串.<br>
     * <br>
     * @param millTimes 毫秒时间
     * @return 日期时间字符串
     */
    public static String getDatetime(long millTimes) {
        return getStringFromDate(new Date(millTimes), FORMAT_DATETIME);
    }

    
    /**
     * 得到当前的日期时间字符串.<br>
     * <br>
     * @param time 指定时间
     * @return 日期时间字符串
     */
    public static String getDatetime(Date time) {
        return getStringFromDate(time, FORMAT_DATETIME);
    }
    
    /**
     * 得到当前的日期时间字符串,到小时分钟.<br>
     * <br>
     * @return 日期时间字符串
     */
    public static String getDateTimeHm() {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), FORMAT_DATETIME_HM);
    }

    /**
     * 得到当前的日期时间字符串.<br>
     * <br>
     * @return 日期时间字符串
     */
    public static String getDatetimeW3C() {
        return getDate() + "T" + getTime();
    }

    /**
     * 得到整点的日期时间字符串.<br>
     * <br>
     * @return 日期时间字符串
     */
    public static String getDatetimeZd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return getStringFromDate(calendar.getTime(), FORMAT_DATETIME);
    }

    /**
     * 得到当前的日期.<br>
     * <br>
     * @return 当前日期
     */
    public static int getDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取相差时间，精确到分钟.<br>
     * <br>
     * @param beginTime 开始时间.
     * @param endTime 结束时间.
     * @return 相差时间
     */
    public static long getDiffTime(String beginTime, String endTime) {
        return getDiffTime(beginTime, endTime, FORMAT_DATETIME);
    }

    /**
     * 获取相差时间，后一个时间减去前一个时间<br>
     * <br>
     * @param beginTime 开始时间。
     * @param endTime 结束时间。
     * @param format 时间格式
     * @return 相差时间
     */
    public static long getDiffTime(String beginTime, String endTime, String format) {
        if (beginTime.length() == 0) {
            beginTime = getDatetime();
        }
        if (endTime.length() == 0) {
            endTime = getDatetime();
        }
        Date eTime = getDateFromString(endTime, format);
        Date bTime = getDateFromString(beginTime, format);
        return eTime.getTime() - bTime.getTime();
    }

    /**
     * 比较两个时间，判断是否开始时间早于结束时间 <br>
     * @param begin 开始时间.
     * @param end 结束时间.
     * @return 是否开始时间早于结束时间
     */
    public static boolean isBefore(String begin, String end) {
        Date beginDate = getDateFromString(begin);
        Date endDate = getDateFromString(end);
        return beginDate.before(endDate);
    }

    /**
     * 获取相差时间，精确到分钟.<br>
     * <br>
     * @param beginTime 开始时间.
     * @param endTime 结束时间.
     * @return 相差时间
     */
    public static String getDiffString(String beginTime, String endTime) {
        try {
            if (endTime == null || endTime.length() == 0) {
                endTime = getDatetime();
            }
            Date eTime = getDateFromString(endTime, FORMAT_DATETIME);
            Date bTime = getDateFromString(beginTime, FORMAT_DATETIME);
            long time = eTime.getTime() - bTime.getTime();
            StringBuilder sb = new StringBuilder();
            int day = (int) Math.floor(time / (24 * 3600000));
            if (day > 0) {
                sb.append(day).append("天");
            }
            time = time % (24 * 3600000);
            int hour = (int) Math.floor(time / 3600000);
            if (hour > 0) {
                sb.append(hour).append("小时");
            }
            time = time % 3600000;
            int minute = (int) Math.ceil(time / 60000);
            if (minute > 0) {
                sb.append(minute).append("分钟");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 得到本周星期一的日期.<br>
     * <br>
     * @return 日期字符串
     */
    public static String getFirstDateOfWeek() {
        return getFirstDateOfWeek(getDate());
    }

    /**
     * 得到指定日期的星期一的日期.<br>
     * <br>
     * @param dateString 日期字符串.
     * @return 本周星期一的日期
     */
    public static String getFirstDateOfWeek(String dateString) {
        Calendar calendar = getCalendar(dateString);
        int iCount;
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            iCount = -6;
        } else {
            iCount = Calendar.MONDAY - calendar.get(Calendar.DAY_OF_WEEK);
        }

        return getCertainDate(dateString, iCount);
    }

    /**
     * 得到当前的全时间字符串，包含毫秒.<br>
     * <br>
     * @return 日期时间字符串
     */
    public static String getFulltime() {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), FORMAT_FULLTIME);
    }

    /**
     * 得到当前的月份.<br>
     * <br>
     * @return 当前月份
     */
    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取上一个月月份
     * @return yyyyMM
     */
    public static String getPreviousMonth() {
    	Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return getFormatDateString(c.getTime(), FORMAT_YEARMONTH_NOPARTING);
    }
    
    /**
     * 取得两日期间的月份差数.
     * @param startDate 起始日期.
     * @param endDate 结束日期.
     * @return 月份差数
     */
    public static int getMonthDiff(String startDate, String endDate) {
        String[] startArray = startDate.split("-");
        String[] endArray = endDate.split("-");
        int startYear = Integer.parseInt(startArray[0]);
        int startMonth = Integer.parseInt(startArray[1]);
        int endYear = Integer.parseInt(endArray[0]);
        int endMonth = Integer.parseInt(endArray[1]);
        return Math.abs((endYear - startYear) * 12 + endMonth - startMonth);
    }

    /**
     * 得到当前日期所在星期的起始的日期.<br>
     * <br>
     * @param date 当前日期.
     * @return Date 返回的日期
     */
    public static Date getWeekFirstDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int weekDay = c.get(Calendar.DAY_OF_WEEK) == 1 ? 8 : c.get(Calendar.DAY_OF_WEEK);
        c.add(Calendar.DATE, Calendar.MONDAY - weekDay);
        Date start = c.getTime();
        // c.add(Calendar.DATE, 6);
        // Date end = c.getTime();
        // System.out.print(String.format("%1$tY-%1$tm-%1$td", start) + " "+ String.format("%1$tY-%1$tm-%1$td", end));
        return start;
    }

    /**
     * 得到当前日期的所在月的第一天的日期.<br>
     * <br>
     * @param date 当前日期.
     * @return String 返回的日期
     */
    public static Date getMonthFirstDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String year = String.valueOf(cal.get(Calendar.YEAR));
        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }
        String resultStr = year + "-" + month + "-01" + " 00:00:00";
        Date result = null;
        result = getDateFromString(resultStr, "yyyy-MM-dd hh:mm:ss");
        return result;
    }

    /**
     * 得到当前日期的所在月的第一天的日期.<br>
     * <br>
     * @param date 当前日期.
     * @return String 返回的日期
     */
    public static String getMonthFirstDay(String date) {
        Calendar cal = getCalendar(date);
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String year = String.valueOf(cal.get(Calendar.YEAR));
        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }
        return year + "-" + month + "-01";
    }

    /**
     * 得到当前日期的所在月的最后一天的日期.
     * @param date 当前日期.
     * @return 返回的日期
     */
    public static String getMonthLastDay(String date) {
        Calendar cal = getCalendar(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        int nextMonth = month + 1;
        int nextYear = year;
        if (nextMonth == 13) {
            nextMonth = 1;
            nextYear = nextYear + 1;
        }
        String nextMonthFirstDay = nextYear + "-" + nextMonth + "-01";
        return getCertainDate(nextMonthFirstDay, -1);
    }

    /**
     * 将日期格式化为指定的字符串.<br>
     * <br>
     * @param d 日期.
     * @param format 输出字符串格式.
     * @return 日期字符串
     */
    public static String getStringFromDate(Date d, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }

    /**
     * 将指定日期格式化成时间字符串
     * @param d 指定日期
     * @return 格式化成时间
     */
    public static String formatTime(Date d) {
        return getStringFromDate(d, FORMAT_TIME);
    }

    /**
     * 将指定日期格式化成时间字符串
     * @param d 指定日期
     * @return 格式化成时间
     */
    public static String formatDate(Date d) {
        return getStringFromDate(d, FORMAT_DATE);
    }

    /**
     * 将指定日期格式化成时间字符串
     * @param d 指定日期
     * @return 时间字符串
     */
    public static String formatDatetime(Date d) {
        return getStringFromDate(d, FORMAT_DATETIME);
    }

    /**
     * 得到当前的纯时间字符串.<br>
     * <br>
     * @return 时间字符串
     */
    public static String getTime() {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), FORMAT_TIME);
    }

    /**
     * 如果当前日期是周六或者周日，则返回下周一的日期.<br>
     * <br>
     * @param date 当前日期.
     * @return 下周一日期
     */
    public static String getWorkDate(final String date) {
        Date curDate = getDateFromString(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        int week = calendar.get(Calendar.DAY_OF_WEEK);

        if (week == Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, 2);
        } else if (week == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        return getDate(calendar);
    }

    /**
     * 得到当前的年份.<br>
     * <br>
     * @return 当前年份
     */
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 得到当前的年月字符串 <br>
     * @return 年月字符串
     */
    public static String getYearMonth() {
        return getYearMonth(0);
    }

    /**
     * 得到当前日期指定月份差额的年月字符串 <br>
     * @param monthDiff 月份差额：-1 上个月，1 下个月
     * @return 年月字符串
     */
    public static String getYearMonth(int monthDiff) {
        Calendar calendar = Calendar.getInstance();
        return getYearMonth(calendar, monthDiff);
    }

    /**
     * 得到指定日期月份差额的年月字符串 <br>
     * @param calendar 指定日期
     * @param monthDiff 月份差额：-1 上个月，1 下个月
     * @return 年月字符串
     */
    public static String getYearMonth(Calendar calendar, int monthDiff) {
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + monthDiff);
        return getStringFromDate(calendar.getTime(), FORMAT_YEARMONTH);
    }

    /**
     * 当前日期与参数传递的日期的相差天数.<br>
     * <br>
     * @param dateinfo 指定的日期.
     * @return 相差的天数
     */
    public static int selectDateDiff(String dateinfo) {
        return selectDateDiff(dateinfo, getDate());
    }

    /**
     * 当得到两个日期相差天数.<br>
     * <br>
     * @param first 第一个日期.
     * @param second 第二个日期.
     * @return 相差的天数
     */
    public static int selectDateDiff(String first, String second) {
        int dif = 0;
        try {
            Date fDate = getDateFromString(first, FORMAT_DATE);
            Date sDate = getDateFromString(second, FORMAT_DATE);
            dif = (int) ((fDate.getTime() - sDate.getTime()) / 86400000);
        } catch (Exception e) {
            dif = 0;
        }
        return dif;
    }

    /**
     * 两个时间相差小时数
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return 相差小时数
     */
    public static double getDiffHoure(String beginTime, String endTime) {
        double dif = 0.00;
        try {
            Date eDatetime = getDateFromString(endTime, "yyyy-MM-dd HH:mm:ss");
            Date bDatetime = getDateFromString(beginTime, "yyyy-MM-dd HH:mm:ss");
            double ed = eDatetime.getTime();
            double sd = bDatetime.getTime();
            BigDecimal et = new BigDecimal(ed / 1000 / 3600 - sd / 1000 / 3600);
            dif = et.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception e) {
            dif = 0;
        }
        return dif;
    }

    /**
     * 获取当前时间戳字符串（带毫秒）
     * @return 时间字符串（带毫秒）
     */
    public static String getDatetimeTS() {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), FORMAT_TIMESTAMP);
    }

    /**
     * 获取当前时间戳
     * @return 时间戳
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取时间戳
     * @param timestamp 时间戳字符串
     * @return 时间戳
     */
    public static Timestamp getTimestamp(String timestamp) {
        return new Timestamp(getDateFromString(timestamp, FORMAT_TIMESTAMP).getTime());
    }

    /**
     * 获取对应的时间戳字符串
     * @param tm 时间戳
     * @return 时间戳字符串
     */
    public static String getByTimestamp(Timestamp tm) {
        if (tm == null) {
            return "";
        } else {
            Date date = new Date(tm.getTime());
            return getStringFromDate(date, FORMAT_TIMESTAMP);
        }
    }

    /**
     * Date format pattern used to parse HTTP date headers in RFC 1123 format.
     */
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";

    /**
     * Date format pattern used to parse HTTP date headers in RFC 1036 format.
     */
    public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";

    /**
     * Date format pattern used to parse HTTP date headers in ANSI C <code>asctime()</code> format.
     */
    public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
    // These are included for back compat
    private static final Collection<String> DEFAULT_HTTP_CLIENT_PATTERNS = Arrays.asList(PATTERN_ASCTIME,
            PATTERN_RFC1036, PATTERN_RFC1123);

    private static final Date DEFAULT_TWO_DIGIT_YEAR_START;

    static {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.US);
        calendar.set(2000, Calendar.JANUARY, 1, 0, 0);
        DEFAULT_TWO_DIGIT_YEAR_START = calendar.getTime();
    }

    private static final TimeZone GMT = TimeZone.getTimeZone("GMT");

    // ---------------------------------------------------------------------------------------

    /**
     * A suite of default date formats that can be parsed, and thus transformed to the Solr specific format
     */
    public static final Collection<String> DEFAULT_DATE_FORMATS = new ArrayList<String>();

    static {
        DEFAULT_DATE_FORMATS.add("yyyy-MM-dd'T'HH:mm:ss'Z'");
        DEFAULT_DATE_FORMATS.add("yyyy-MM-dd'T'HH:mm:ss");
        DEFAULT_DATE_FORMATS.add("yyyy-MM-dd");
        DEFAULT_DATE_FORMATS.add("yyyy-MM-dd hh:mm:ss");
        DEFAULT_DATE_FORMATS.add("yyyy-MM-dd HH:mm:ss");
        DEFAULT_DATE_FORMATS.add("EEE MMM d hh:mm:ss z yyyy");
        DEFAULT_DATE_FORMATS.addAll(DEFAULT_HTTP_CLIENT_PATTERNS);
    }

    /**
     * Returns a formatter that can be use by the current thread if needed to convert Date objects to the Internal
     * representation.
     * 
     * @param d The input date to parse
     * @return The parsed {@link java.util.Date}
     * @throws java.text.ParseException If the input can't be parsed
     * @throws ParseException org.apache.commons.httpclient.util.DateParseException If the input can't be parsed
     */
    public static Date parseDate(String d) throws ParseException {
        return parseDate(d, DEFAULT_DATE_FORMATS);
    }

    /**
     * Returns a formatter that can be use by the current thread if needed to convert Date objects to the Internal
     * representation.
     * @param d date string
     * @param fmts fmts
     * @return Date
     * @throws ParseException org.apache.commons.httpclient.util.DateParseException If the input can't be parsed
     */
    public static Date parseDate(String d, Collection<String> fmts) throws ParseException {
        // 2007-04-26T08:05:04Z
        if (d.endsWith("Z") && d.length() > 20) {
            return getThreadLocalDateFormat().parse(d);
        }
        return parseDate(d, fmts, null);
    }

    /**
     * get utc time (solr used utc time)
     * @param date date string,format: yyyy-MM-dd hh:mm:ss
     * @return utc time string
     * @throws IOException ioexception
     */
    public static String getUTCTime(Date date) throws IOException {
        StringBuilder dateBuilder = new StringBuilder();
        formatDate(date, null, dateBuilder);
        return dateBuilder.toString();
    }

    /**
     * Slightly modified from org.apache.commons.httpclient.util.DateUtil.parseDate
     * <p/>
     * Parses the date value using the given date formats.
     * 
     * @param dateValue the date value to parse
     * @param dateFormats the date formats to use
     * @param startDate During parsing, two digit years will be placed in the range <code>startDate</code> to
     *            <code>startDate + 100 years</code>. This value may be <code>null</code>. When <code>null</code> is
     *            given as a parameter, year <code>2000</code> will be used.
     * @return the parsed date
     * @throws ParseException if none of the dataFormats could parse the dateValue
     */
    public static Date parseDate(String dateValue, Collection<String> dateFormats, Date startDate)
            throws ParseException {

        if (dateValue == null) {
            throw new IllegalArgumentException("dateValue is null");
        }
        if (dateFormats == null) {
            dateFormats = DEFAULT_HTTP_CLIENT_PATTERNS;
        }
        if (startDate == null) {
            startDate = DEFAULT_TWO_DIGIT_YEAR_START;
        }
        // trim single quotes around date if present
        // see issue #5279
        if (dateValue.length() > 1 && dateValue.startsWith("'") && dateValue.endsWith("'")) {
            dateValue = dateValue.substring(1, dateValue.length() - 1);
        }

        SimpleDateFormat dateParser = null;
        Iterator<String> formatIter = dateFormats.iterator();

        while (formatIter.hasNext()) {
            String format = formatIter.next();
            if (dateParser == null) {
                dateParser = new SimpleDateFormat(format, Locale.US);
                dateParser.setTimeZone(GMT);
                dateParser.set2DigitYearStart(startDate);
            } else {
                dateParser.applyPattern(format);
            }
            return dateParser.parse(dateValue);
        }

        // we were unable to parse the date
        throw new ParseException("Unable to parse the date " + dateValue, 0);
    }

    /**
     * Returns a formatter that can be use by the current thread if needed to convert Date objects to the Internal
     * representation.
     * 
     * @return The {@link java.text.DateFormat} for the current thread
     */
    public static DateFormat getThreadLocalDateFormat() {
        return fmtThreadLocal.get();
    }

    /** utc time zone */
    public static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    private static ThreadLocalDateFormat fmtThreadLocal = new ThreadLocalDateFormat();

    /**
     * @author liwei
     * 
     */
    private static class ThreadLocalDateFormat extends ThreadLocal<DateFormat> {
        private DateFormat proto;

        /**
		 * 
		 */
        public ThreadLocalDateFormat() {
            super();
            // 2007-04-26T08:05:04Z
            SimpleDateFormat tmp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            tmp.setTimeZone(UTC);
            proto = tmp;
        }

        @Override
        protected DateFormat initialValue() {
            return (DateFormat) proto.clone();
        }
    }

    /**
     * Formats the date and returns the calendar instance that was used (which may be reused)
     * @param date <CODE>Date</CODE>
     * @param cal Calendar
     * @param out Appendable
     * @return Calendar
     * @throws IOException IOException
     */
    private static Calendar formatDate(Date date, Calendar cal, Appendable out) throws IOException {
        // using a stringBuilder for numbers can be nice since
        // a temporary string isn't used (it's added directly to the
        // builder's buffer.

        StringBuilder sb = out instanceof StringBuilder ? (StringBuilder) out : new StringBuilder();
        if (cal == null) {
            cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.US);
        }
        cal.setTime(date);

        int i = cal.get(Calendar.YEAR);
        sb.append(i);
        sb.append('-');
        i = cal.get(Calendar.MONTH) + 1; // 0 based, so add 1
        if (i < 10) {
            sb.append('0');
        }
        sb.append(i);
        sb.append('-');
        i = cal.get(Calendar.DAY_OF_MONTH);
        if (i < 10) {
            sb.append('0');
        }
        sb.append(i);
        sb.append('T');
        i = cal.get(Calendar.HOUR_OF_DAY); // 24 hour time format
        if (i < 10) {
            sb.append('0');
        }
        sb.append(i);
        sb.append(':');
        i = cal.get(Calendar.MINUTE);
        if (i < 10) {
            sb.append('0');
        }
        sb.append(i);
        sb.append(':');
        i = cal.get(Calendar.SECOND);
        if (i < 10) {
            sb.append('0');
        }
        sb.append(i);
        i = cal.get(Calendar.MILLISECOND);
        if (i != 0) {
            sb.append('.');
            if (i < 100) {
                sb.append('0');
            }
            if (i < 10) {
                sb.append('0');
            }
            sb.append(i);

            // handle canonical format specifying fractional
            // seconds shall not end in '0'. Given the slowness of
            // integer div/mod, simply checking the last character
            // is probably the fastest way to check.
            int lastIdx = sb.length() - 1;
            if (sb.charAt(lastIdx) == '0') {
                lastIdx--;
                if (sb.charAt(lastIdx) == '0') {
                    lastIdx--;
                }
                sb.setLength(lastIdx + 1);
            }

        }
        sb.append('Z');

        if (out != sb) {
            out.append(sb);
        }

        return cal;
    }

    /**
     * 
     * @return 取得当前时间的date对象
     */
    public static Date createDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 为指定日期增加一段指定单位的时间
     * @param date 日期对象
     * @param calendarField 时间单位。
     * @param amount 数量
     * @return 增加指定单位之间之后的Date对象
     */
    private static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 为指定日期增加几分钟
     * @param date 指定日期
     * @param amount 增加的数量
     * @return 增加指定单位之间之后的Date对象
     */
    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    /**
     * 为指定日期增加几小时
     * @param date 指定日期
     * @param amount 增加的数量
     * @return 增加指定单位之间之后的Date对象
     */
    public static Date addHours(Date date, int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 为指定日期增加几天
     * @param date 指定日期
     * @param amount 增加的数量
     * @return 增加指定单位之间之后的Date对象
     */
    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 为指定日期增加几星期
     * @param date 指定日期
     * @param amount 增加的数量
     * @return 增加指定单位之间之后的Date对象
     */
    public static Date addWeeks(Date date, int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    /**
     * 为指定日期增加几个月
     * @param date 指定日期
     * @param amount 增加的数量
     * @return 增加指定单位之间之后的Date对象
     */
    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 为指定日期增加几年
     * @param date 指定日期
     * @param amount 增加的数量
     * @return 增加指定单位之间之后的Date对象
     */
    public static Date addYears(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    /**
     * 
     * @param month 月
     * @param year 年
     * @return 给定年 对应 月的 天数
     */
    public static int getDayOfMonth(int month, int year) {
        int days = 0;

        if (month == 2) {
            if (year % 4 != 0) {
                days = 28;
            } else {
                if (year % 100 == 0 && year % 400 != 0) {
                    days = 28;
                } else {
                    days = 29;
                }
            }

        } else {
            switch (month) {
            case 1:
                days = 31;
                break;
            case 3:
                days = 31;
                break;
            case 4:
                days = 30;
                break;
            case 5:
                days = 31;
                break;
            case 6:
                days = 30;
                break;
            case 7:
                days = 31;
                break;
            case 8:
                days = 31;
                break;
            case 9:
                days = 30;
                break;
            case 10:
                days = 31;
                break;
            case 11:
                days = 30;
                break;
            case 12:
                days = 31;
                break;
            default:
                break;
            }
        }

        return days;

    }
    /**
     * 获取月份的天数
     * @param beginTime
     * @param endTime
     * @param weekState
     * @return
     */
	public static Map<String,List<Integer>> getMonthDays(Date beginTime,Date endTime,Map<String, Boolean> weekState){
		Map<String,List<Integer>> monthMap = new HashMap<String, List<Integer>>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
		Calendar ca = Calendar.getInstance();
		ca.setTime(beginTime);
		int curMonth = -1;
		List<Integer> list = null;
		while(!ca.getTime().after(endTime)){
			String key = sf.format(ca.getTime());
			if(ca.get(Calendar.MONTH)!=curMonth ){
				list = new ArrayList<Integer>();
				curMonth = ca.get(Calendar.MONTH);
			}
			if(weekState.get(ca.get(Calendar.DAY_OF_WEEK)+"")){
				list.add(ca.get(Calendar.DAY_OF_MONTH));
			}	
			monthMap.put(key, list);
			ca.add(Calendar.DATE, 1);
		}
		return monthMap;
	}

    /**
     * 执行测试方法
     * @param args 参数
     */
    public static void main(String[] args) {
    }
    
    @SuppressWarnings("unused")
	private LocalDateTime calcNextSunday(LocalDateTime d) {
        return d.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withDayOfWeek(DateTimeConstants.SUNDAY);
    }

    @SuppressWarnings("unused")
	private LocalDateTime calcPreviousMonday(final LocalDateTime d) {
        return d.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withDayOfWeek(DateTimeConstants.MONDAY);
    }
    /**
     * java.uitl.Date转化为java.sql.Date
     * @param date
     * @return
     */
    public static java.sql.Date convert(Date date){
    	return new java.sql.Date(date.getTime());
    }
    public static String getCurrMonthFirstDay(){
    	SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
    	Date now = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(now);
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	return sdf.format(cal.getTime());
    }
    /*
     * 获得下月第一天的日期
     */
    public static long getNextMonthFirstDay(){
    	Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DATE, 1); 
		long rlt = cal.getTimeInMillis();
		return rlt;
    }
    /*
     * 获得下月第一天的日期
     */
    public static Date getNextMonthFirstDay2(){
    	Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DATE, 1); 
		return cal.getTime();
    }
    /*
     * 获得下月第一天的日期
     */
    public static String getNextMonthFirstDay3(){
    	SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
    	Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DATE, 1); 
		String first = sdf.format(cal.getTime());
		return first;
    }
    public static Date addDate(int m){
    	Date d = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DAY_OF_YEAR, m);
		return cal.getTime();
    }
    
    /**
	 * 获取上月最后一天时间戳
	 * @return 时间戳
	 * @throws ParseException 
	 */
	public static long getPrevMonthStateTimeVal() throws ParseException {
		//获取上月最后一天：
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));//设置上月最后一天
        String dateString = getFormatDateString(c.getTime(), FORMAT_DATE);
        return getDateFromeDateString(dateString + " 23:59:59", FORMAT_DATETIME).getTime();
	}
	
	/**
	 * nextDate
	 * */
	public static String getNextDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
	    Date nextDate = add(new Date(), Calendar.DAY_OF_MONTH, 1);
        return sdf.format(nextDate);
	}
}
