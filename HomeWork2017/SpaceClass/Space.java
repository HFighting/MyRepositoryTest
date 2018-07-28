package SpaceClass;

import java.util.Calendar;

public class Space {

	private char id;
	private String user;
	private int[] date;
	private int[] time;
	private int week;//星期，需要根据日期来计算
	private boolean isdefault = false;//是否违约
	
	//默认为false，即不违约
	public boolean isIsdefault() {
		return isdefault;
	}
	public void setIsdefault(boolean isdefault) {
		this.isdefault = isdefault;
	}
	public Space() {
		super();
	}
	public Space(char id,String user,int[] date,int[] time) {
		this.id = id;
		this.user = user;
		this.date = date;
		this.time = time;
		setWeek(date);
	}
	
	public char getId() {
		return id;
	}
	public void setId(char id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int[] getDate() {
		return date;
	}
	public void setDate(int[] date) {
		this.date = date;
	}
	public int[] getTime() {
		return time;
	}
	public void setTime(int[] time) {
		this.time = time;
	}
	public int getWeek() {
		return week;
	}
	private void setWeek(int[] date) {
		Calendar calendar = Calendar.getInstance();//获取一个日历
		calendar.set(date[0], date[1]-1, date[2]);//设置当前时间，月份是从0开始计算
		this.week = calendar.get(Calendar.DAY_OF_WEEK)-1;
	}
	
	public boolean compare(String user,int[] date,int[] time,char id){
		
		if (
				user.equals(this.user)&&
				(date[0]==this.date[0]&&date[1]==this.date[1]&&date[2]==this.date[2])&&
				(time[0]==this.time[0]&&time[1]==this.time[1])&&
				id==this.id
			) {
			return true;
		}else {
			return false;
		}
	}
	public void printDate(){
		System.out.print(date[0]+"-"+date[1]+"-"+date[2]+"   ");
	}
	public void printTime(){
		System.out.print(time[0]+"~"+time[1]+"   ");
	}
	
}
