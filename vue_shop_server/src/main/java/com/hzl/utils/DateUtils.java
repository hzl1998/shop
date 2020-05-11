package com.hzl.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

	
	
	
	


	  
	
	    /** 
	     * 日期增长 
	     * @param time 
	     * @param dayCount 增长天数 
	     * @return 
	     */ 
	    public static String time(String time, int dayCount){ 
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd "); 
	        String str=""; 
	        try { 
	            Date date = sdf.parse(time); 
	            Calendar calendar = new GregorianCalendar(); 
	            calendar.setTime(date); 
	            calendar.add(calendar.DATE, dayCount);// 
	            //把日期往后增加一天.整数往后推,负数往前移动 
	            date = calendar.getTime(); // 这个时间就是日期往后推一天的结果 
	            str= sdf.format(date); 
	        } catch (ParseException e) { 
	            e.printStackTrace(); 
	        } 
	        return str; 
	    } 
	    
	    
	    public static String time2(String time, int dayCount){ 
	        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd "); 
	        String str=""; 
	        try { 
	            Date date = sdf.parse(time); 
	            Calendar calendar = new GregorianCalendar(); 
	            calendar.setTime(date); 
	            calendar.add(calendar.DATE, dayCount);// 
	            //把日期往后增加一天.整数往后推,负数往前移动 
	            date = calendar.getTime(); // 这个时间就是日期往后推一天的结果 
	            str= sdf.format(date); 
	        } catch (ParseException e) { 
	            e.printStackTrace(); 
	        } 
	        return str; 
	    } 
	    
	    /* 
	     * 将时间戳转换为时间
	     */
	    public static String stampToDate(String s){
	        String res;
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd ");
	        long lt = new Long(s);
	        Date date = new Date(lt);
	        res = simpleDateFormat.format(date);
	        return res;
	    }
	    
	    
	    public static String stampToDate2(String s){
	        String res;
	        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd ");
	        long lt = new Long(s);
	        Date date = new Date(lt);
	        res = simpleDateFormat.format(date);
	        return res;
	    }
	    
	    
	    /* 
	     * 将时间转换为时间戳
	     */    
	    public static String dateToStamp(String s) throws ParseException{
	        String res;
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd ");
	        Date date = simpleDateFormat.parse(s);
	        long ts = date.getTime();
	        res = String.valueOf(ts);
	        return res;
	    }
	    
	    public static String dateToStamp2(String s) throws ParseException{
	        String res;
	        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd ");
	        Date date = simpleDateFormat.parse(s);
	        long ts = date.getTime();
	        res = String.valueOf(ts);
	        return res;
	    }
	    
	    
	    public static String dateToStamp3(String s) throws ParseException{
	        String res;
	        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
	        Date date = simpleDateFormat.parse(s);
	        long ts = date.getTime();
	        res = String.valueOf(ts);
	        return res;
	    }
	    
	    //传入一个时间戳，获取该时间戳的当天00:00:00(返回时间戳格式)
	    public static String start(String str) throws ParseException {
	    	 return dateToStamp2(stampToDate2(str));
	    }
	    
	    //传入一个时间戳，获取该时间戳的当天23:59:59(返回时间戳格式)
	    public static String  end(String str) throws ParseException {
	    	 return dateToStamp(stampToDate2(str)+"23:59:59");
	    }
	    
	    
	    //传入一个时间戳，返回指定日期的时间戳,并返回当天0点
	    public static String getX(String str,int i) throws ParseException {
	         return dateToStamp2(time2(stampToDate2(str),i));
	    }
	    
	    
	    //传入两个时间戳，计算相差天数（day1>day2）
	    public static long day(String day1,String day2) throws ParseException {
	    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    	Date now = df.parse(stampToDate2(day1));
	    	Date date=df.parse(stampToDate2(day2));
	    	long l=now.getTime()-date.getTime();
	    	long day=l/(24*60*60*1000);
	    	return day;
	    }
	    
	    
	    //获取当前下个月的时间
	    public static String nextMonth(String timestamp) throws ParseException{
	    	String str = stampToDate2(timestamp);
	    	String yy = str.substring(0,4);
	    	String mm = str.substring(5,7);
	    	String dd = str.substring(8,10);
  		  int year=Integer.parseInt(yy);
  		  int month=  Integer.parseInt(mm)+1;
  		  if(month==13) {
  			  month=1;
  			  year+=1;
  		  }
  		  int day=Integer.parseInt(dd);
  		 if(day>28){  
  		      if(month==2){  
  		          if(year%400==0||(year %4==0&&year%100!=0)){  
  		              day=29;  
  		          }else day=28;  
  		      }else if((month==4||month==6||month==9||month==11)&&day==31)  
  		      {  
  		          day=30;  
  		      }  
  		  }  
  		  String y = year+"";String m ="";String d ="";  
  		  if(month<10) m = "0"+month;  
  		  else m=month+"";  
  		  if(day<10) d = "0"+day;  
  		  else d = day+"";  
  		  //System.out.println(y+"-"+m+"-"+d);  
  		  return end(dateToStamp2(y+"-"+m+"-"+d+" "));  
  	  }
	    
	    
	    //获取当前N个月后的时间
	    public static String nMonth(String timestamp,int i) throws ParseException{
	    	String str = stampToDate2(timestamp);
	    	String yy = str.substring(0,4);
	    	String mm = str.substring(5,7);
	    	String dd = str.substring(8,10);
  		  int year=Integer.parseInt(yy);
  		  int month=  Integer.parseInt(mm)+i;
  		  if(month>12) {
  			  month=month-12;
  			  year+=1;
  		  }
  		  int day=Integer.parseInt(dd);
  		 if(day>28){  
  		      if(month==2){  
  		          if(year%400==0||(year %4==0&&year%100!=0)){  
  		              day=29;  
  		          }else day=28;  
  		      }else if((month==4||month==6||month==9||month==11)&&day==31)  
  		      {  
  		          day=30;  
  		      }  
  		  }  
  		  String y = year+"";String m ="";String d ="";  
  		  if(month<10) m = "0"+month;  
  		  else m=month+"";  
  		  if(day<10) d = "0"+day;  
  		  else d = day+"";  
  		  //System.out.println(y+"-"+m+"-"+d);  
  		  return end(dateToStamp2(y+"-"+m+"-"+d+" "));  
  	  }
	    
	    
	    //传入时间戳，返回当前月份1号00:00:00
	    public static String getMonth(String timestamp) throws ParseException{
	    	String str = stampToDate2(timestamp);
	    	String month = str.substring(0,7);
	    	return dateToStamp3(month);
  	    }
	    
	    //传入时间戳，返回1年后的时间戳
	    public static String getNyear(long time) {
	    	
	        Date date = new Date(time);
	    	
	    	Calendar cal = Calendar.getInstance();
	    	cal.setTime(date);//设置起时间
	    	cal.add(Calendar.YEAR, 1);//增加一年
	    	return String.valueOf(cal.getTimeInMillis());
	    }
	    
	    
	    //反斜杠日期转时间戳
	    public static String dateToStamp4(String s) throws ParseException{
	        String res;
	        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	        Date date = simpleDateFormat.parse(s);
	        long ts = date.getTime();
	        res = String.valueOf(ts);
	        return res;
	    }
	    
	    
	    public static void main(String[] args) throws ParseException  {
	    	String str  = "2017/5/9";
	    	String str2 = "2017/10/9";
	    	if(Long.valueOf(dateToStamp4(str2)) >Long.valueOf(dateToStamp4(str))) {
	    		/*2019.11.20-清理控制台输出*/
//	    		System.out.println("ok");
	    	}
//	    	String date ="1561366800008";
//	    	System.out.println(stampToDate(date));
//	    	System.out.println(stampToDate(date).substring(11,13));
	    	//System.out.println(str.substring(9,11));
	    	
//	    	Calendar cal = Calendar.getInstance();
//	    	cal.setTime(new Date());//设置起时间
//	    	cal.add(Calendar.YEAR, 1);//增加一年
//	    	System.out.println("当前的时间戳："+DateUtils.getUTCTime()+"日期格式： "+stampToDate(String.valueOf(DateUtils.getUTCTime())));
//	    	System.out.println("一年后的时间戳："+cal.getTimeInMillis()+"日期格式： "+stampToDate(String.valueOf(cal.getTimeInMillis())));
	    	
	    	
	    	//System.out.println(getNyear());
	    	
	    	
	    	
	    	//System.out.println(getNyear(1564588800000l));
	    	
		}
	    
	    /**
	     * 获得世界协调时的近似值(弃用！！！)
	     * jyb - 2019/09/26
	     * @return  Date
	     */
	   public static long getUTCTime(){
	   	Calendar cal = Calendar.getInstance();
	   	/*//获得时区和 GMT-0 的时间差,偏移量
	   	int offset = cal.get(Calendar.ZONE_OFFSET);
	   	//获得夏令时  时差
	   	int dstoff = cal.get(Calendar.DST_OFFSET);
	   	cal.add(Calendar.MILLISECOND, - (offset + dstoff));*/
		return cal.getTimeInMillis();
	   }
	   
	   /**
	    * 获取utc时间(弃用！！！)
	    * @return
	    */
	   public static Date getUTCDate() {
		   // 1、取得本地时间：
	        Calendar cal = Calendar.getInstance() ;
	      /* // 2、取得时间偏移量：
	        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
	        // 3、取得夏令时差：
	        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
	        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
	        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));*/
	        return cal.getTime();
	   }
	}
