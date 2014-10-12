package com.vjburra.todoapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class computeNextAlarmDate {
	
	public String generateNextAlarmDate(String sdt, Boolean [] selectedDays, String edt, Boolean calNextFlag){
		//Logic for NextTaskAt field
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	edt = edt + " 00:00:00";
	String nxtDt = sdt;
	Calendar calNow,calEnd,calStart;
	calNow = Calendar.getInstance();
	calEnd = Calendar.getInstance();
	calStart = Calendar.getInstance();
	//Calendar calStart = Calendar.getInstance();
		try{
			boolean flag = true;
			//Date tempDt = dateFormat.parse(sdt);
			if(edt != null || edt != ""){
				//Date endDt = dateFormat.parse(edt);
				calEnd.setTime(dateFormat.parse(edt));
				//Calendar calEnd = Calendar.getInstance();
				//calEnd.set(endDt.getYear(), endDt.getMonth(), endDt.getDay(), endDt.getHours(), endDt.getMinutes());
				if(calEnd.before(calNow)){
					flag = false;
				}
			}
			
			calStart.setTime(dateFormat.parse(sdt));
			//calStart.set(tempDt.getYear(), tempDt.getMonth(), tempDt.getDay(), tempDt.getHours(), tempDt.getMinutes());
		
			if(flag){
				long timeDiff = (calNow.getTimeInMillis() - calStart.getTimeInMillis())/(1000);
				//if(calNow.before(calStart)){
				if(timeDiff < 0){
					//check for freq is repeat
					//nxtDt = calStart.toString();
					if(!selectedDays[calStart.DAY_OF_WEEK])
					{
						for(Integer count =1; count<8; count++)
						{
							calStart.add(Calendar.DATE, 1);
							int dow = (calStart.get(Calendar.DAY_OF_WEEK));
							if(selectedDays[dow])
							{
								break;
							}
						}
					}
					nxtDt = calStart.getTime().toString();
				}
				else if(timeDiff < 300 && calNextFlag == false){//time diff is in secs so checking for 5 min or less
					nxtDt = dateFormat.format(calNow.getTime());
							//calNow.get(Calendar.YEAR) + "-" +  calNow.get(Calendar.MONTH) + 
							//"-" +  calNow.get(Calendar.DATE) + " " +  calNow.get(Calendar.HOUR) 
							//+ ":" +  calNow.get(Calendar.MINUTE) + ":00";
				}
				else{ //(timeDiff > 120){
				//else if(calNow.after(calStart){
					//find next possible date
					//calNow.add(Calendar.DATE, 1);//adding a day to current date
					if(calNextFlag)
					{	
						calNow.add(Calendar.DATE, 1);
					}
					if(!selectedDays[calNow.get(Calendar.DAY_OF_WEEK)])
					{
						for(Integer count =1; count<8; count++)
						{
							calNow.add(Calendar.DATE, 1);
							int dow = (calNow.get(Calendar.DAY_OF_WEEK));// + count)%7;
							if(selectedDays[dow])
							{
								break;
							}
						}
					}
					//int mnth = (calNow.get(Calendar.MONTH)+1);
					//int dateInt = calNow.get(Calendar.DATE);
					
					nxtDt = dateFormat.format(calNow.getTime());
							//calNow.get(Calendar.YEAR) + "-" +  mnth + 
							//"-" +  dateInt + " " +  calNow.get(Calendar.HOUR) 
							//+ ":" +  calNow.get(Calendar.MINUTE) + ":00";//.toString();
				}
				
			}
			else{
				nxtDt = "";
			}
				
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		}
	return nxtDt;
}

}
