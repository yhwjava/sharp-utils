package com.sharp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Title: FileUtil</p>
 * <p>Description: 时间日期操作工具类</p>
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * @author yuanhongwei
 * @version 1.0 2019-6-30 下午6:48:33 【初版】
 */
public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static String YYYY = "yyyy";
    public static String YYYY_MM = "yyyy-MM";
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String yyyyMMdd = "yyyyMMdd";
    public static String yyyyMMddHH = "yyyyMMddHH";
    public static String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    private static final ThreadLocal<DateFormat> format_yyyy = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy");
        }
    };
    private static final ThreadLocal<DateFormat> format_YYYY_MM = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM");
        }
    };
    private static final ThreadLocal<DateFormat> format_YYYY_MM_DD = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    private static final ThreadLocal<DateFormat> format_YYYY_MM_DD_HH_MM_SS = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private static final ThreadLocal<DateFormat> format_yyyyMMdd = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };
    private static final ThreadLocal<DateFormat> format_yyyyMMddHH = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHH");
        }
    };
    private static final ThreadLocal<DateFormat> format_yyyyMMddHHmmss = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };

    /**
     * 获取某时间点N秒钟之后的时间
     *
     * @param date
     * @param seconds
     * @return
     */
    private static Date getFirstOrBackSecondsOfDate(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * 得到当前日期前或则后N天的日期
     *
     * @param pattern 时间格式
     * @param days    天数
     * @return
     */
    public static Date getFirstOrBackDaysOfNowDate(String pattern, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        Date time = calendar.getTime();
        return time;
    }

    public static String getFirstOrBackDaysOfNowStr(String pattern, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        Date time = calendar.getTime();
        return new SimpleDateFormat(pattern).format(time);
    }

    /**
     * 获取当前时间前或者后一段时间的时间
     *
     * @param pattern    时间格式
     * @param difference 时间差
     * @param unit       时间单位 例如：Calendar.MONTH
     * @return
     */
    public static String getFirstOrBackOfDate(Date date, String pattern, int difference, int unit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(unit, difference);
        Date time = calendar.getTime();
        return new SimpleDateFormat(pattern).format(time);
    }

    /**
     * 得到当前日期前或则后N月的日期
     *
     * @param pattern
     * @param months
     * @return
     */
    public static Date getFirstOrBackMonthsOfNowDate(String pattern, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, months);
        Date time = calendar.getTime();
        return time;
    }

    public static String getFirstOrBackMonthsOfNowStr(String pattern, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, months);
        Date time = calendar.getTime();
        return new SimpleDateFormat(pattern).format(time);
    }

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate(final String pattern) {
        Date nowDate = null;
        try {
            nowDate = new SimpleDateFormat(pattern).parse(getNowStrDate(pattern));
        } catch (Exception e) {
            logger.error("获取当前Date型日期", e);
        }
        return nowDate;
    }

    public static String getNowStrDate(final String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    public static final String parseDateToStr(final Date date, final String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 按照日期格式，将字符串解析为日期对象
     *
     * @param pattern 输入字符串的格式
     * @param strDate 一个按aMask格式排列的日期的字符串描述
     * @return Date 对象
     * @see SimpleDateFormat
     */
    public static final Date convertStringToDate(String strDate, final String pattern) {
        Date date = null;
        DateFormat df = new SimpleDateFormat(pattern);

        try {
            date = df.parse(strDate);
        } catch (ParseException e) {
            logger.error("ParseException: ", e);
        }

        return date;
    }

}
