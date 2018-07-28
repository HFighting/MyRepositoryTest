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
			Calendar calendar = Calendar.getInstance();//��ȡ��ǰ����
			int _year = calendar.get(Calendar.YEAR);//��ȡ��ǰ��
			int _month = calendar.get(Calendar.MONTH);//��ȡ��ǰ��
			int _day = calendar.get(Calendar.DATE);//��ȡ��ǰ��
			
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
				System.out.println("������������");
				return null;
			}
		}
		return null;
	}
	//ʱ���ʽΪ20:00~22:00�����ұ���������
	static int[] convertTime(String time){
		String[] str = time.split("~");
		String[] str1 = str[0].split(":");
		String[] str2 = str[1].split(":");
		if (str1[1].equals("00")&&str2[1].equals("00")) {
			int b_time = Integer.parseInt(str1[0]);
			int e_time = Integer.parseInt(str2[0]);
			//��Ӿ�صĿ�ʼʱ��Ϊ9��00--22��00���Ҳ���С�ڵ�ǰʱ��
			if (b_time<e_time&&e_time<=22&&b_time>=9) {
				int[] newtime = new int[]{b_time,e_time};
				return newtime;
			}else {
				System.out.println("ʱ����������");
				return null;
			}
		}else {
			System.out.println("ʱ���ʽ��������");
			return null;
		}
	}
	/*
	 * �ж��Ƿ��ͻ
	 * ��ͻ��������������ͬ��������ͬ������£�ʱ���г�ͻ���ҵĿ�ʼʱ��С����Ľ���ʱ������ҵĽ���ʱ�������Ŀ�ʼʱ�䣩
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
	 * �����ܽ��
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
		double num1 = 0;//��Ԥ���ķ���
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
			System.out.println("����"+character);
			System.out.println("----");
			List<Space> spaces = spaceprice.get(character);
			double totol = 0;//����ó��ص��ܷ���
			//�����ó����µ�Ԥ�����������
			for (int i = 0; i < spaces.size(); i++) {
				switch (spaces.get(i).getWeek()) {
				case 0:
					Price price0 = Price.Sunday;
					List<int[]> p0 = price0.getList();//�õ������ڵļ۸�����
					int[] time0 = spaces.get(i).getTime();//�õ�Ԥ����ʱ��
					spaces.get(i).printDate();
					spaces.get(i).printTime();
					double num0 = compute1(p0, time0);//��Ԥ���ķ���
					if (spaces.get(i).isIsdefault()) {//�ж��Ƿ�ΥԼ
						num0 = num0*0.25;
						System.out.print("ΥԼ��    ");
					}
					totol += num0;					
					System.out.println(num0);
					break;
				case 1:
					Price price1 = Price.Monday;
					List<int[]> p1 = price1.getList();//�õ������ڵļ۸�����
					int[] time1 = spaces.get(i).getTime();//�õ�Ԥ����ʱ��
					spaces.get(i).printDate();
					spaces.get(i).printTime();
					double num1 = compute2(p1, time1);//��Ԥ���ķ���
					if (spaces.get(i).isIsdefault()) {//�ж��Ƿ�ΥԼ
						num1 = num1*0.5;
						System.out.print("ΥԼ��    ");
					}
					totol += num1;					
					System.out.println(num1);
					break;
				case 2:
					Price price2 = Price.Tuesday;
					List<int[]> p2 = price2.getList();//�õ������ڵļ۸�����
					int[] time2 = spaces.get(i).getTime();//�õ�Ԥ����ʱ��
					spaces.get(i).printDate();
					spaces.get(i).printTime();
					double num2 = compute2(p2, time2);//��Ԥ���ķ���
					if (spaces.get(i).isIsdefault()) {//�ж��Ƿ�ΥԼ
						num2 = num2*0.5;
						System.out.print("ΥԼ��    ");
					}
					totol += num2;					
					System.out.println(num2);
					break;
				case 3:
					Price price3 = Price.Wednesday;
					List<int[]> p3 = price3.getList();//�õ������ڵļ۸�����
					int[] time3 = spaces.get(i).getTime();//�õ�Ԥ����ʱ��
					spaces.get(i).printDate();
					spaces.get(i).printTime();
					double num3 = compute2(p3, time3);//��Ԥ���ķ���
					if (spaces.get(i).isIsdefault()) {//�ж��Ƿ�ΥԼ
						num3 = num3*0.5;
						System.out.print("ΥԼ��    ");
					}
					totol += num3;					
					System.out.println(num3);
					break;
				case 4:
					Price price4 = Price.Thursday;
					List<int[]> p4 = price4.getList();//�õ������ڵļ۸�����
					int[] time4 = spaces.get(i).getTime();//�õ�Ԥ����ʱ��
					spaces.get(i).printDate();
					spaces.get(i).printTime();
					double num4 = compute2(p4, time4);//��Ԥ���ķ���
					if (spaces.get(i).isIsdefault()) {//�ж��Ƿ�ΥԼ
						num4 = num4*0.5;
						System.out.print("ΥԼ��    ");
					}
					totol += num4;					
					System.out.println(num4);
					break;
				case 5:
					Price price5 = Price.Friday;
					List<int[]> p5 = price5.getList();//�õ������ڵļ۸�����
					int[] time5 = spaces.get(i).getTime();//�õ�Ԥ����ʱ��
					spaces.get(i).printDate();
					spaces.get(i).printTime();
					double num5 = compute2(p5, time5);//��Ԥ���ķ���
					if (spaces.get(i).isIsdefault()) {//�ж��Ƿ�ΥԼ
						num5 = num5*0.5;
						System.out.print("ΥԼ��    ");
					}
					totol += num5;					
					System.out.println(num5);
					break;
				case 6:
					Price price6 = Price.Saturday;
					List<int[]> p6 = price6.getList();//�õ������ڵļ۸�����
					int[] time6 = spaces.get(i).getTime();//�õ�Ԥ����ʱ��
					spaces.get(i).printDate();
					spaces.get(i).printTime();
					double num6 = compute2(p6, time6);//��Ԥ���ķ���
					if (spaces.get(i).isIsdefault()) {//�ж��Ƿ�ΥԼ
						num6 = num6*0.25;
						System.out.print("ΥԼ��    ");
					}
					totol += num6;					
					System.out.println(num6);
					break;
				}
			}
			System.out.println("----------");
			System.out.println("�ܼƣ�"+totol);
			System.out.println("****************");
			money += totol;
		}
		System.out.println("*********");
		System.out.println("�����棺"+money);
	}
	
	public static void main(String[] args) throws IOException{
		
		Set<Space> spaces = new HashSet<Space>();//��ΪԤ���ĳ��ؼ���
		Map<Character, List<Space>> spaceprice = new HashMap<Character, List<Space>>();//���ص�����map
		spaceprice.put('A', new ArrayList<Space>(){});
		spaceprice.put('B', new ArrayList<Space>(){});
		spaceprice.put('C', new ArrayList<Space>(){});
		spaceprice.put('D', new ArrayList<Space>(){});
		//1������������
		//Scanner sc = new Scanner(System.in);//scannerһ��ѿո�tab���س���Ϊ�ָ���
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		while(true){
			input = br.readLine();
			String[] str = input.split(" ");
			if (" ".equals(input)) {
				System.out.println("��ӡ���");
				caculate(spaceprice);
				continue;
			}
			if (str.length==4) {
				String user = str[0];//�û���
				int[] date = convertDate(str[1]);//ԤԼ��������
				int[] time = convertTime(str[2]);//ԤԼ��ʱ��
				if (date==null||time==null) {
					continue;
				}
				char id = Character.toUpperCase(str[3].charAt(0));//ԤԼ�ĳ���
				if (id>='A'&&id<='D') {
					Space space = new Space(id, user, date, time);
					//�����ж���Ԥ���Ƿ������е�Ԥ�����ͻ
					if (ifconflict(space, spaces)) {//����ͻ�����������
						System.out.println("Ԥ����ʱ�������е�Ԥ�����ͻ��������Ԥ��");
						break;
					}
					System.out.println("Success: the booking is accepted!");
					//�ѳɹ�Ԥ������ӵ�����
					spaces.add(space);
					//�ѳɹ�Ԥ������ӵ���Ӧ�ĳ��ؼ���
					spaceprice.get(space.getId()).add(space);
					continue;
				}else {
					System.out.println("������������");
				}
				//�ɹ��ѷ���Ҫ���������������ಢ��ӵ������У�ȡ��Ԥ�����ֻ�û����ɣ��Լ���ӡ�ܽ��Ҳû�����
			}else if(str.length==5){//���������5����˵����ȡ����
				if (str[4].equals("C")) {
					String user = str[0];//�û���
					int[] date = convertDate(str[1]);//ԤԼ��������
					int[] time = convertTime(str[2]);//ԤԼ��ʱ��
					char id = Character.toUpperCase(str[3].charAt(0));//ԤԼ�ĳ���
					for (Space s : spaces) {
						if (s.compare(user, date, time, id)) {
							spaceprice.get(s.getId()).remove(s);
							s.setIsdefault(true);
							//��ȡ��Ԥ���ļ�����Ӧ�ĳ��ط���
							spaceprice.get(s.getId()).add(s);
							spaces.remove(s);
							System.out.println("�ɹ�ȡ��Ԥ��");							
							continue;
						}
					}
				}else {
					System.out.println("ȡ���������������");
				}
				
			}else {
				System.out.println("Error: the booking is invalid!");
			}
					
		}
	}	
}
