package cps.fs.APImanagerSys.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
	
	public static String replaceStr(String data) {
		if(data == null) {
            return data;
        }
        return data.replace("@","\\"+"@")
                .replace(" ","")
                .replace("_","\\"+"_")
                .replace("%","\\"+"%");
	}

	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getNow() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	/**
	 * 格式化时间
	 * @param date
	 * @return
	 */
	public static String getTime(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	/**
     * 拿到当前月份加最近三个月之前的时间
     * @return
     */
    public static long getThreeMonthsEarly(){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR,year);
        instance.set(Calendar.MONTH,month);
        instance.add(Calendar.MONTH,-3);
        return instance.getTime().getTime();
    }
    
    
    /**
     * 校验搜索时间
     * @param start
     * @param end
     * @return
     */
    public static boolean checkSearchDate(String start,String end) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (start!=null&&end!=null){
           /* LOGGER.info("开始时间："+start);
            LOGGER.debug("时间校验-开始时间："+start+"         时间校验-结束时间："+end);
            LOGGER.info("结束时间："+end);*/
            long starttime = format.parse(start).getTime();
            long endtime = format.parse(end).getTime();
            return  checkStartTime(start) && starttime <= endtime?true:false;
        }
        if (start!=null&&end==null){
            return checkStartTime(start);
        }
        return true;
    }
    
    /**
     * 校验开始时间
     * @return
     * @param startTime
     * @throws ParseException
     */
    public static boolean checkStartTime(String startTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long starttime = format.parse(startTime).getTime();
        return DateUtil.getThreeMonthsEarly() <= starttime?true:false;
    }
}
