package TestClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import PriceClass.Price;
import SpaceClass.Space;

public class Test {

	static int[] convertDate(String string){
		String[] str = string.split("-");
		if (str.length!=3) {
			System.out.println("Error: the booking is invalid!");
		}else {
			Calendar calendar = Calendar.getInstance();//获取当前日期
			int _year = calendar.get(Calendar.YEAR);//获取当前年
			int _month = calendar.get(Calendar.MONTH);//获取当前月
			int _day = calendar.get(Calendar.DATE);//获取当前日
			
			int year = Integer.parseInt(str[0]);
			int month = Integer.parseInt(str[1]);
			int day = Integer.parseInt(str[2]);
			if (year>_year) {
				int[] date = new int[]{year,month,day};
				return date;
			}else if(year==_year){
				if (month>_month&&month<=12) {
					int[] date = new int[]{year,month,day};
					return date;
				}else if(month==_month){
					if (day>=_day&&day<=30) {
						int[] date = new int[]{year,month,day};
						return date;
					}
				}
			}else {
				System.out.println("日期输入有误");
				return null;
			}
		}
		return null;
	}
	//时间格式为20:00~22:00，而且必须是整点
	static int[] convertTime(String time){
		String[] str = time.split("~");
		String[] str1 = str[0].split(":");
		String[] str2 = str[1].split(":");
		if (str1[1].equals("00")&&str2[1].equals("00")) {
			int b_time = Integer.parseInt(str1[0]);
			int e_time = Integer.parseInt(str2[0]);
			//游泳池的开始时间为9：00--22：00，且不能小于当前时间
			if (b_time<e_time&&e_time<=22&&b_time>=9) {
				int[] newtime = new int[]{b_time,e_time};
				return newtime;
			}else {
				System.out.println("时间输入有误");
				return null;
			}
		}else {
			System.out.println("时间格式输入有误");
			return null;
		}
	}
	/*
	 * 判断是否冲突
	 * 冲突的条件：场地相同，日期相同的情况下，时间有冲突（我的开始时间小于你的结束时间或者我的结束时间大于你的开始时间）
	 */
	public static boolean ifconflict(Space s1,Set<Space> spaces){
		for (Space space : spaces) {
			if(space.getId()==s1.getId()){
				int[] date1 = s1.getDate();
				int[] date2 = space.getDate();
				if (date1[0]==date2[0]&&date1[1]==date2[1]&&date1[2]==date2[2]) {
					int[] time1 = s1.getTime();
					int[] time2 = space.getTime();
					if (time1[0]<time2[1]||time1[1]>time2[0]) {
						return true;
					}
				}
			}
		}
		return false;
	}
	/*
	 * 计算总金额
	 */
	public static double compute1(List<int[]> p0,int[] time){
		double num0 = 0;
		if (p0.get(0)[0]<=time[0]&&time[0]<=p0.get(0)[1]) {						
			if (time[1]<=p0.get(0)[1]) {
				num0 = (time[1]-time[0])*p0.get(0)[2];
			}else if(time[1]<=p0.get(1)[1]){
				num0 = (p0.get(0)[1]-time[0])*p0.get(0)[2]+(time[1]-p0.get(1)[0])*p0.get(1)[2];
			}else if (time[1]<=p0.get(2)[1]) {
				num0 = (p0.get(0)[1]-time[0])*p0.get(0)[2]+(p0.get(1)[1]-p0.get(1)[0])*p0.get(1)[2]+(time[1]-p0.get(2)[0])*p0.get(2)[2];
			}
		}else if(p0.get(1)[0]<=time[0]&&time[0]<=p0.get(1)[1]) {
			if (time[1]<=p0.get(1)[1]) {
				num0 = (time[1]-time[0])*p0.get(1)[2];
			}else if (p0.get(1)[0]<=time[1]&&time[1]<=p0.get(2)[1]) {
				num0 = (p0.get(1)[1]-time[0])*p0.get(1)[2]+(time[1]-p0.get(1)[1])*p0.get(2)[3];
			}
		}else if (p0.get(2)[0]<=time[0]&&time[0]<=p0.get(2)[1]) {
			num0 = (time[1]-time[0])*p0.get(2)[2];
		}
		return num0;
	}
	public static double compute2(List<int[]> p1,int[] time1){
		double num1 = 0;//该预定的费用
		if (p1.get(0)[0]<=time1[0]&&time1[0]<=p1.get(0)[1]) {						
			if (time1[1]<=p1.get(0)[1]) {
				num1 = (time1[1]-time1[0])*p1.get(0)[2];
			}else if(time1[1]<=p1.get(1)[1]){
				num1 = (p1.get(0)[1]-time1[0])*p1.get(0)[2]+(time1[1]-p1.get(1)[0])*p1.get(1)[2];
			}else if (time1[1]<=p1.get(2)[1]) {
				num1 = (p1.get(0)[1]-time1[0])*p1.get(0)[2]+(p1.get(1)[1]-p1.get(1)[0])*p1.get(1)[2]+(time1[1]-p1.get(2)[0])*p1.get(2)[2];
			}else if (time1[1]<=p1.get(3)[1]) {
				num1 = (p1.get(0)[1]-time1[0])*p1.get(0)[2]+(p1.get(1)[1]-p1.get(1)[0])*p1.get(1)[2]+(p1.get(2)[1]-p1.get(2)[0])*p1.get(2)[2]+(time1[1]-p1.get(3)[0])*p1.get(3)[2];
			}
		}else if(p1.get(1)[0]<=time1[0]&&time1[0]<=p1.get(1)[1]) {
			if(time1[1]<=p1.get(1)[1]){
				num1 = (time1[1]-time1[0])*p1.get(1)[2];
			}else if (time1[1]<=p1.get(2)[1]) {
				num1 = (p1.get(1)[1]-time1[0])*p1.get(1)[2]+(time1[1]-p1.get(2)[0])*p1.get(2)[2];
			}else if (time1[1]<=p1.get(3)[1]) {
				num1 = (p1.get(1)[1]-time1[0])*p1.get(1)[2]+(p1.get(2)[1]-p1.get(2)[0])*p1.get(2)[2]+(time1[1]-p1.get(3)[0])*p1.get(3)[2];
			}
		}else if (p1.get(2)[0]<=time1[0]&&time1[0]<=p1.get(2)[1]) {
			if (time1[1]<=p1.get(2)[1]) {
				num1 = (time1[1]-time1[0])*p1.get(2)[2];
			}else if (time1[1]<=p1.get(3)[1]) {
				num1 = (p1.get(2)[1]-time1[0])*p1.get(2)[2]+(time1[1]-p1.get(3)[0])*p1.get(3)[2];
			}
		}else if (p1.get(3)[0]<=time1[0]) {
			num1 = (time1[1]-time1[0])*p1.get(3)[2];
		}
		return num1;
	}
	public static void  caculate(Map<Character, List<Space>> spaceprice){
		
		Set<Character> keys = spaceprice.keySet();
		double money = 0;
		for (Character character : keys) {
			System.out.println("****************");
			System.out.println("场地"+character);
			System.out.println("----");
			List<Space> spaces = spaceprice.get(character);
			double totol = 0;//定义该场地的总费用
			//遍历该场地下的预定，计算费用
			for (int i = 0; i < spaces.size(); i++) {
				switch (spaces.get(i).getWeek()) {
				case 0:
					Price price0 = Price.Sunday;
					List<int[]> p0 = price0.getList();//得到该星期的价格区间
					int[] time0 = spaces.get(i).getTime();//得到预定的时间
					spaces.get(i).printDate();
					spaces.get(i).printTime();
					double num0 = compute1(p0, time0);//该预定的费用
					if (spaces.get(i).isIsdefault()) {//判断是否违约
						num0 = num0*0.25;
						System.out.print("违约金    ");
					}
					totol += num0;					
					System.out.println(num0);
					break;
				case 1:
					Price price1 = Price.Monday;
					List<int[]> p1 = price1.getList();//得到该星期的价格区间
					int[] time1 = spaces.get(i).getTime();//得到预定的时间
					spaces.get(i).printDate();
					spaces.get(i).printTime();
					double num1 = compute2(p1, time1);//该预定的费用
					if (spaces.get(i).isIsdefault()) {//判断是否违约
						num1 = num1*0.5;
						System.out.print("违约金    ");
					}
					totol += num1;					
					System.out.println(num1);
					break;
				case 2:
					Price price2 = Price.Tuesday;
					List<int[]> p2 = price2.getList();//得到该星期的价格区间
					int[] time2 = spaces.get(i).getTime();//得到预定的时间
					spaces.get(i).printDate();
					spaces.get(i).printTime();
					double num2 = compute2(p2, time2);//该预定的费用
					if (spaces.get(i).isIsdefault()) {//判断是否违约
						num2 = num2*0.5;
						System.out.print("违约金    ");
					}
					totol += num2;					
					System.out.println(num2);
					break;
				case 3:
					Price price3 = Price.Wednesday;
					List<int[]> p3 = price3.getList();//得到该星期的价格区间
					int[] time3 = spaces.get(i).getTime();//得到预定的时间
					spaces.get(i).printDate();
					spaces.get(i).printTime();
					double num3 = compute2(p3, time3);//该预定的费用
					if (spaces.get(i).isIsdefault()) {//判断是否违约
						num3 = num3*0.5;
						System.out.print("违约金    ");
					}
					totol += num3;					
					System.out.println(num3);
					break;
				case 4:
					Price price4 = Price.Thursday;
					List<int[]> p4 = price4.getList();//得到该星期的价格区间
					int[] time4 = spaces.get(i).getTime();//得到预定的时间
					spaces.get(i).printDate();
					spaces.get(i).printTime();
					double num4 = compute2(p4, time4);//该预定的费用
					if (spaces.get(i).isIsdefault()) {//判断是否违约
						num4 = num4*0.5;
						System.out.print("违约金    ");
					}
					totol += num4;					
					System.out.println(num4);
					break;
				case 5:
					Price price5 = Price.Friday;
					List<int[]> p5 = price5.getList();//得到该星期的价格区间
					int[] time5 = spaces.get(i).getTime();//得到预定的时间
					spaces.get(i).printDate();
					spaces.get(i).printTime();
					double num5 = compute2(p5, time5);//该预定的费用
					if (spaces.get(i).isIsdefault()) {//判断是否违约
						num5 = num5*0.5;
						System.out.print("违约金    ");
					}
					totol += num5;					
					System.out.println(num5);
					break;
				case 6:
					Price price6 = Price.Saturday;
					List<int[]> p6 = price6.getList();//得到该星期的价格区间
					int[] time6 = spaces.get(i).getTime();//得到预定的时间
					spaces.get(i).printDate();
					spaces.get(i).printTime();
					double num6 = compute2(p6, time6);//该预定的费用
					if (spaces.get(i).isIsdefault()) {//判断是否违约
						num6 = num6*0.25;
						System.out.print("违约金    ");
					}
					totol += num6;					
					System.out.println(num6);
					break;
				}
			}
			System.out.println("----------");
			System.out.println("总计："+totol);
			System.out.println("****************");
			money += totol;
		}
		System.out.println("*********");
		System.out.println("总收益："+money);
	}
	
	public static void main(String[] args) throws IOException{
		
		Set<Space> spaces = new HashSet<Space>();//作为预定的场地集合
		Map<Character, List<Space>> spaceprice = new HashMap<Character, List<Space>>();//场地的收入map
		spaceprice.put('A', new ArrayList<Space>(){});
		spaceprice.put('B', new ArrayList<Space>(){});
		spaceprice.put('C', new ArrayList<Space>(){});
		spaceprice.put('D', new ArrayList<Space>(){});
		//1、首先是输入
		//Scanner sc = new Scanner(System.in);//scanner一般把空格、tab、回车作为分隔符
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		while(true){
			input = br.readLine();
			String[] str = input.split(" ");
			if (" ".equals(input)) {
				System.out.println("打印金额");
				caculate(spaceprice);
				continue;
			}
			if (str.length==4) {
				String user = str[0];//用户名
				int[] date = convertDate(str[1]);//预约的年月日
				int[] time = convertTime(str[2]);//预约的时间
				if (date==null||time==null) {
					continue;
				}
				char id = Character.toUpperCase(str[3].charAt(0));//预约的场地
				if (id>='A'&&id<='D') {
					Space space = new Space(id, user, date, time);
					//还需判定该预定是否与已有的预定相冲突
					if (ifconflict(space, spaces)) {//若冲突，则输出错误
						System.out.println("预定的时间与已有的预定相冲突，请重新预定");
						break;
					}
					System.out.println("Success: the booking is accepted!");
					//把成功预定的添加到集合
					spaces.add(space);
					//把成功预定的添加到相应的场地集合
					spaceprice.get(space.getId()).add(space);
					continue;
				}else {
					System.out.println("场地输入有误");
				}
				//成功把符合要求的输入情况放入类并添加到集合中，取消预定部分还没有完成，以及打印总金额也没有完成
			}else if(str.length==5){//如果长度是5，则说明有取消的
				if (str[4].equals("C")) {
					String user = str[0];//用户名
					int[] date = convertDate(str[1]);//预约的年月日
					int[] time = convertTime(str[2]);//预约的时间
					char id = Character.toUpperCase(str[3].charAt(0));//预约的场地
					for (Space s : spaces) {
						if (s.compare(user, date, time, id)) {
							spaceprice.get(s.getId()).remove(s);
							s.setIsdefault(true);
							//把取消预定的加入相应的场地费中
							spaceprice.get(s.getId()).add(s);
							spaces.remove(s);
							System.out.println("成功取消预定");							
							continue;
						}
					}
				}else {
					System.out.println("取消的命令输入出错");
				}
				
			}else {
				System.out.println("Error: the booking is invalid!");
			}
					
		}
	}	
}
