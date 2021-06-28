package com.example.loadmorelinechart.util;


import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

/**
 * 时间日期转化工具类
 * Created by wyy on 2019/5/7 0007.
 */

public class DateUtils {

    /**
     * 获取现在时间戳
     *
     * @return 返回时间时间戳
     */
    public static long getNowTime() {
        Date currentTime = new Date();
        return currentTime.getTime();
    }

    /**
     * 获取两天前的时间戳
     *
     * @return 返回时间时间戳
     */
    public static long getPreTwoDayTime() {
        Date currentTime = new Date();
        long preTwoDayTime = currentTime.getTime() - (1000 * 60 * 60 * 24 * 2);
        return preTwoDayTime;
    }


    /**
     * long转化为当前时间
     *
     * @param lefttime
     * @return
     */
    public static CharSequence format(long lefttime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        String sDateTime = sdf.format(lefttime);  //得到精确到秒的表示：08/31/2006 21:08:00
        return sDateTime;
    }


    /**
     * String转化为long
     *
     * @param time     字符串时间,注意:格式要与template定义的一样
     * @param template 要格式化的格式:如time为09:21:12那么template为"HH:mm:ss"
     * @return
     */
    public static long formatToLong(String time, String template) {
        SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.CHINA);
        try {
            Date d = sdf.parse(time);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            long l = c.getTimeInMillis();
            return l;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     *      * 日期格式字符串转换成时间戳
     *      *
     *      * @param dateStr
     *      *            字符串日期
     *      * @param format
     *      *            如：yyyy-MM-dd HH:mm:ss
     *      *
     *      * @return
     *     
     */
    public static long Date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String time =  String.valueOf(sdf.parse(dateStr).getTime());
            return Long.parseLong(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }


    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return String类型时间 yyyy-MM-dd
     */
    public static String formatDateByYYMMDD(long date) {
        if (date == 0) {
            return "--";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(date);
            return dateString;
        }
    }

    /**
     * 获取现在时间
     *
     * @return String类型时间 yy年MM月dd日 HH:mm
     */
    public static String formatDateByYYMMDDV2(long date) {
        if (date == 0) {
            return "--";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yy年MM月dd日 HH:mm");
            String dateString = formatter.format(date);
            return dateString;
        }
    }

    /**
     * 格式化时间
     *
     * @return String类型时间 yyyy-MM-dd HH:mm
     */
    public static String formatDateByYYMMDDHHmm(long date) {
        if (date == 0) {
            return "--";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateString = formatter.format(date);
            return dateString;
        }
    }

    /**
     * 格式化时间
     *
     * @return String类型时间 yy-MM-dd HH:mm
     */
    public static String formatDateByYYMMDDHHmmV2(long date) {
        if (date == 0) {
            return "--";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm");
            String dateString = formatter.format(date);
            return dateString;
        }
    }

    /**
     * 格式化时间
     *
     * @return String类型时间 yyyy-MM-dd HH:mm
     */
    public static String formatDateByYYMMDDHHmmss(long date) {
        if (date == 0) {
            return "--";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            String dateString = formatter.format(date);
            return dateString;
        }
    }

    /**
     * 格式化时间
     *
     * @return String类型时间 yyyy.MM.dd HH:mm:ss
     */
    public static String formatDateByYYMMDDHHmmssV2(long date) {
        if (date == 0) {
            return "--";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            String dateString = formatter.format(date);
            return dateString;
        }
    }

    /**
     * 格式化时间
     *
     * @return String类型时间 HH:mm:ss
     */
    public static String formatDateByHHmmss(long date) {
        if (date == 0) {
            return "--";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            String dateString = formatter.format(date);
            return dateString;
        }
    }



    /**
     *
     * @return String类型时间 yyyy(年)
     */
    public static String formatDateByyyyy(long date) {
        if (date == 0) {
            return "--";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
            String dateString = formatter.format(date);
            return dateString;
        }
    }
    /**
     *
     * @return String类型时间 yyyy(年)
     */
    public static String formatDateByyyyy2(String date) {
        if (TextUtils.isEmpty(date)) {
            return "--";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
            String dateString = formatter.format(date);
            return dateString;
        }
    }


    /**
     *
     * @return String类型时间 MM(月)
     */
    public static String formatDateByMM(long date) {
        if (date == 0) {
            return "--";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("MM");
            String dateString = formatter.format(date);
            return dateString;
        }
    }

    /**
     *
     * @return String类型时间 yyyy(年)
     */
    public static String formatDateByMM2(String date) {
        if (TextUtils.isEmpty(date)) {
            return "--";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("MM");
            String dateString = formatter.format(date);
            return dateString;
        }
    }

    /**
     *
     * @return String类型时间 dd(月)
     */
    public static String formatDateBydd(long date) {
        if (date == 0) {
            return "--";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd");
            String dateString = formatter.format(date);
            return dateString;
        }
    }

    /**
     *
     * @return String类型时间 yyyy(年)
     */
    public static String formatDateBydd2(String date) {
        if (TextUtils.isEmpty(date)) {
            return "--";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd");
            String dateString = formatter.format(date);
            return dateString;
        }
    }

    /**
     * 格式化时间
     *
     * @return String类型时间 hh:mm(12小时制)
     */
    public static String formatDateByhhmm(long date) {
        if (date == 0) {
            return "--";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
            String dateString = formatter.format(date);
            return dateString;
        }
    }

    /**
     * 格式化时间
     *
     * @return String类型时间 hh:mm(24小时制)
     */
    public static String formatDateByHHmm(long date) {
        if (date == 0) {
            return "--";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            String dateString = formatter.format(date);
            return dateString;
        }
    }


    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return
     */
    public static String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取时间 小时：分 HH：mm
     *
     * @return
     */
    public static String getTimeHourMin() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 格式化时间以HH：mm的形式返回
     *
     * @param time
     * @return
     */
    public static String formatterTimeByHHmm(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date(time);
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @return
     */
    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 得到现在时间
     *
     * @return
     */
    public static Date getNow() {
        Date currentTime = new Date();
        return currentTime;
    }

    /**
     * 提取一个月中的最后一天
     *
     * @param day
     * @return
     */
    public static Date getLastDate(long day) {
        Date date = new Date();
        long date_3_hm = date.getTime() - 3600000 * 34 * day;
        Date date_3_hm_date = new Date(date_3_hm);
        return date_3_hm_date;
    }

    /**
     * 得到现在时间
     *
     * @return 字符串 yyyyMMdd HHmmss
     */
    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 得到现在时间,到日
     *
     * @return 字符串 yyyyMMdd
     */
    public static String getStringTodayDay() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 得到现在小时
     */
    public static String getHour() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    /**
     * 得到现在分钟
     *
     * @return
     */
    public static String getTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String min;
        min = dateString.substring(14, 16);
        return min;
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     *
     * @param sformat yyyyMMddhhmmss
     * @return
     */
    public static String getUserDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
     */
    public static String getTwoHour(String st1, String st2) {
        String[] kk = null;
        String[] jj = null;
        kk = st1.split(":");
        jj = st2.split(":");
        if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
            return "0";
        else {
            double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
            double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
            if ((y - u) > 0)
                return y - u + "";
            else
                return "0";
        }
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 时间前推或后推分钟,其中JJ表示分钟.
     */
    public static String getPreTime(String sj1, String jj) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mydate1 = "";
        try {
            Date date1 = format.parse(sj1);
            long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
            date1.setTime(Time * 1000);
            mydate1 = format.format(date1);
        } catch (Exception e) {
        }
        return mydate1;
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     */
    public static String getNextDay(String nowdate, String delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            Date d = strToDate(nowdate);
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 判断是否润年
     *
     * @param ddate
     * @return
     */
    public static boolean isLeapYear(String ddate) {
        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        Date d = strToDate(ddate);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(Calendar.YEAR);
        if ((year % 400) == 0)
            return true;
        else if ((year % 4) == 0) {
            if ((year % 100) == 0)
                return false;
            else
                return true;
        } else
            return false;
    }

    /**
     * 返回美国时间格式 26 Apr 2006
     *
     * @param str
     * @return
     */
    public static String getEDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
    }

    /**
     * 获取一个月的最后一天
     *
     * @param dat
     * @return
     */
    public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
        String str = dat.substring(0, 8);
        String month = dat.substring(5, 7);
        int mon = Integer.parseInt(month);
        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            str += "31";
        } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            str += "30";
        } else {
            if (isLeapYear(dat)) {
                str += "29";
            } else {
                str += "28";
            }
        }
        return str;
    }

    /**
     * 判断二个时间是否在同一个周
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    /**
     * 产生周序列,即得到当前时间所在的年度是第几周
     *
     * @return
     */
    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1)
            week = "0" + week;
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + week;
    }

    /**
     * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
     *
     * @param sdate
     * @param num
     * @return
     */
    public static String getWeek(String sdate, String num) {
        // 再转换为时间
        Date dd = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        if (num.equals("1")) // 返回星期一所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        else if (num.equals("2")) // 返回星期二所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        else if (num.equals("3")) // 返回星期三所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        else if (num.equals("4")) // 返回星期四所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        else if (num.equals("5")) // 返回星期五所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        else if (num.equals("6")) // 返回星期六所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        else if (num.equals("0")) // 返回星期日所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate
     * @return
     */
    public static String getWeek(String sdate) {
        // 再转换为时间
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    public static String getWeekStr(String sdate) {
        String str = "";
        str = getWeek(sdate);
        if ("1".equals(str)) {
            str = "星期日";
        } else if ("2".equals(str)) {
            str = "星期一";
        } else if ("3".equals(str)) {
            str = "星期二";
        } else if ("4".equals(str)) {
            str = "星期三";
        } else if ("5".equals(str)) {
            str = "星期四";
        } else if ("6".equals(str)) {
            str = "星期五";
        } else if ("7".equals(str)) {
            str = "星期六";
        }
        return str;
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间
     * 此函数返回该日历第一行星期日所在的日期
     *
     * @param sdate
     * @return
     */
    public static String getNowMonth(String sdate) {
        // 取该时间所在月的一号
        sdate = sdate.substring(0, 8) + "01";
        // 得到这个月的1号是星期几
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int u = c.get(Calendar.DAY_OF_WEEK);
        String newday = getNextDay(sdate, (1 - u) + "");
        return newday;
    }

    /**
     * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
     *
     * @param k 表示是取几位随机数，可以自己定
     */
    public static String getNo(int k) {
        return getUserDate("yyyyMMddhhmmss") + getRandom(k);
    }

    /**
     * 返回一个随机数
     *
     * @param i
     * @return
     */
    public static String getRandom(int i) {
        Random jjj = new Random();
        // int suiJiShu = jjj.nextInt(9);
        if (i == 0)
            return "";
        String jj = "";
        for (int k = 0; k < i; k++) {
            jj = jj + jjj.nextInt(9);
        }
        return jj;
    }

    /**
     * @param date
     */
    public static boolean RightDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ;
        if (date == null)
            return false;
        if (date.length() > 10) {
            sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        try {
            sdf.parse(date);
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    /**
     * 获得当天0点时间
     *
     * @return
     */
    public static long getTimesmorning() {
        TimeZone curTimeZone = TimeZone.getTimeZone("GMT+8");
        Calendar calendar = Calendar.getInstance(curTimeZone);
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获得当天24点时间
     *
     * @return
     */
    public static long getTimesnight() {
        TimeZone curTimeZone = TimeZone.getTimeZone("GMT+8");
        Calendar calendar = Calendar.getInstance(curTimeZone);
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        return calendar.getTimeInMillis();
    }

    /**
     * 判断传入时间是上午还是下午
     *
     * @param time
     * @return
     */
    public static String getAmOrPm(long time) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int apm = mCalendar.get(Calendar.AM_PM);
        if (apm == 0) {// apm=0 表示上午，apm=1表示下午。
            return " 上午 ";
        } else {
            return " 下午 ";
        }
    }

    /**
     * 根据传入的两个相邻消息的关系，判断是否需要显示时间戳
     *
     * @param create_time1
     * @param create_time2
     * @return
     */
    public static boolean timeStampShow(String create_time1, String create_time2) {
        if (create_time1 != null && create_time2 != null) {
            String[] split1 = create_time1.split("\\.");
            String[] split2 = create_time2.split("\\.");
            Date oldDate2 = new Date(Long.parseLong(split2[0]));
            Date oldDate1 = new Date(Long.parseLong(split1[0]));
            //计算两个创建时间的差值是否超过5分钟
            int differentTime = (int) Double.parseDouble(DateUtils.timeDifference(oldDate2, oldDate1));
            if (differentTime >= 5) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 计算两个时间之间的相差多少分钟
     */
    public static String timeDifference(Date endDate, Date nowDate) {
        if (null == endDate || null == nowDate) {
            return null;
        } else {
            long nm = 1000 * 60;
            // 获得两个时间的毫秒时间差异
            long diff = endDate.getTime() - nowDate.getTime();
            // 计算差多少分钟
            BigDecimal divisor = new BigDecimal(diff);
            BigDecimal dividend = new BigDecimal(nm);
            double min = divisor.divide(dividend, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return String.valueOf(min);
        }
    }

    /**
     * 根据毫秒时间戳来格式化字符串
     * 返回今天、昨天、前天还是其他
     *
     * @param timeStamp 毫秒值
     * @return 今天 昨天 前天 或者其他 类型字符串
     */
    public static String getDataModel(long timeStamp) {
        long curTimeMillis = System.currentTimeMillis();
        Date curDate = new Date(curTimeMillis);
        int todayHoursSeconds = curDate.getHours() * 60 * 60;
        int todayMinutesSeconds = curDate.getMinutes() * 60;
        int todaySeconds = curDate.getSeconds();
        int todayMillis = (todayHoursSeconds + todayMinutesSeconds + todaySeconds) * 1000;
        long todayStartMillis = curTimeMillis - todayMillis;
        if (timeStamp >= todayStartMillis) {
            return "今天";
        }
        int oneDayMillis = 24 * 60 * 60 * 1000;
        long yesterdayStartMilis = todayStartMillis - oneDayMillis;
        if (timeStamp >= yesterdayStartMilis) {
            return "昨天";
        }
        long yesterdayBeforeStartMilis = yesterdayStartMilis - oneDayMillis;
        if (timeStamp >= yesterdayBeforeStartMilis) {
            return "前天";
        }
        return "其他";
    }

}
