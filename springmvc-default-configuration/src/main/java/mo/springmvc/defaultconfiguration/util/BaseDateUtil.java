package mo.springmvc.defaultconfiguration.util;

import org.apache.commons.lang3.time.DateUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * @author WindShadow
 * @verion 2020/9/6.
 */

public abstract class BaseDateUtil {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATETIME_MILLIS_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    protected static final String[] DATETIME_PATTENS = new String[]{DATETIME_FORMAT};
    protected static final String[] DATE_PATTENS = new String[]{DATE_FORMAT};
    protected static final String[] TIME_PATTRENS = new String[]{TIME_FORMAT};
    protected static final String[] DATETIME_MILLIS_PATTENS = new String[]{DATETIME_MILLIS_FORMAT};
    public static final String REGEX_DATE = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$";

    public static boolean matchDateString(String dateString) {
        return dateString == null ? false : Pattern.matches(REGEX_DATE, dateString);
    }

    public static Date datetimeStringToDate(String datetimeString) throws ParseException {
        return DateUtils.parseDate(datetimeString, DATETIME_PATTENS);
    }

    public static Date dateStringToDate(String dateString) throws ParseException {
        return DateUtils.parseDate(dateString, DATE_PATTENS);
    }

    public static Date datetimeMillisStringToDate(String datetimeMillisString) throws ParseException {
        return DateUtils.parseDate(datetimeMillisString, DATETIME_MILLIS_PATTENS);
    }

    public static String dateToDatetimeString(Date date) {
        return date == null ? null : (new SimpleDateFormat(DATETIME_FORMAT)).format(date);
    }

    public static String dateToDatetimeMillisString(Date date) {
        return date == null ? null : (new SimpleDateFormat(DATETIME_MILLIS_FORMAT )).format(date);
    }

    public static String dateToDateString(Date date) {
        return date == null ? null : (new SimpleDateFormat(DATE_FORMAT)).format(date);
    }

    public static String dateToTimeString(Date date) {
        return date == null ? null : (new SimpleDateFormat(TIME_FORMAT)).format(date);
    }

    public static boolean beforeNowOnSecondLevel(String datetimeString) throws ParseException {
        return beforeNowOnSecondLevel(datetimeStringToDate(datetimeString));
    }

    public static boolean beforeNowOnMillisLevel(String datetimeMillisString) throws ParseException {
        return beforeNowOnMillisLevel(datetimeMillisStringToDate(datetimeMillisString));
    }

    public static boolean beforeNowOnSecondLevel(Date date) {
        try {
            Date now = datetimeStringToDate(dateToDatetimeString(getNow()));
            Date date2 = datetimeStringToDate(dateToDatetimeString(date));
            return date2.before(now);
        } catch (ParseException var3) {
            var3.printStackTrace();
            throw new RuntimeException(var3);
        }
    }

    public static boolean beforeNowOnMillisLevel(Date date) {
        return date.before(getNow());
    }

    public static boolean afterToday(String dateString) throws ParseException {
        return afterToday(datetimeStringToDate(dateString));
    }

    public static boolean afterToday(Date date) {
        try {
            Date now = dateStringToDate(dateToDateString(new Date()));
            Date date2 = dateStringToDate(dateToDateString(date));
            return date2.before(now);
        } catch (ParseException var3) {
            var3.printStackTrace();
            throw new RuntimeException(var3);
        }
    }

    public static Date longTimeToDate(Long time) {
        return new Date(time);
    }

    public static String longTimeToDatetimeString(Long time) {
        return dateToDatetimeString(longTimeToDate(time));
    }

    public static String longTimeToDateString(Long time) {
        return dateToDateString(longTimeToDate(time));
    }

    public static Date getNow() {

        return new Date(System.currentTimeMillis());
    }

    public static Date getToday() {

        String now = dateToDateString(getNow());
        try {
            return dateStringToDate(now);
        } catch (ParseException var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2);
        }
    }

    public static Date getNowOnSecondLevel() {
        String now = dateToDatetimeString(getNow());

        try {
            return datetimeStringToDate(now);
        } catch (ParseException var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2);
        }
    }

    public static Date dateAddDay(Date date, int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    public static Date dateAddHours(Date date, int hours) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    @Deprecated
    public static Long interval(long start, long end, int unit) {
        throw new RuntimeException("'The function can't use!");
    }

    @Deprecated
    public static Long interval(Date start, Date end, int unit) {
        return interval(start.getTime(), end.getTime(), unit);
    }
}
