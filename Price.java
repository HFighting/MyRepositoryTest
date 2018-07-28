package PriceClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Price {

	Monday{
		int week = 1;
		private List<int[]> price = new ArrayList<int[]>(){
			{
				add(new int[]{9,12,30});
				add(new int[]{12,18,50});
				add(new int[]{18,20,80});
				add(new int[]{20,22,60});
			}
		};
		public List<int[]> getList(){
			return price;
		}
		
	},Tuesday{
		int week = 2;
		private List<int[]> price = new ArrayList<int[]>(){
			{
				add(new int[]{9,12,30});
				add(new int[]{12,18,50});
				add(new int[]{18,20,80});
				add(new int[]{20,22,60});
			}
		};
		public List<int[]> getList(){
			return price;
		}
	},Wednesday{
		int week = 3;
		private List<int[]> price = new ArrayList<int[]>(){
			{
				add(new int[]{9,12,30});
				add(new int[]{12,18,50});
				add(new int[]{18,20,80});
				add(new int[]{20,22,60});
			}
		};
		public List<int[]> getList(){
			return price;
		}
	},Thursday{
		int week = 4;
		private List<int[]> price = new ArrayList<int[]>(){
			{
				add(new int[]{9,12,30});
				add(new int[]{12,18,50});
				add(new int[]{18,20,80});
				add(new int[]{20,22,60});
			}
		};
		public List<int[]> getList(){
			return price;
		}
	},Friday{
		int week = 5;
		private List<int[]> price = new ArrayList<int[]>(){
			{
				add(new int[]{9,12,30});
				add(new int[]{12,18,50});
				add(new int[]{18,20,80});
				add(new int[]{20,22,60});
			}
		};
		public List<int[]> getList(){
			return price;
		}
	},Saturday{
		int week = 6;
		private List<int[]> price = new ArrayList<int[]>(){
			{
				add(new int[]{9,12,40});
				add(new int[]{12,18,50});
				add(new int[]{18,20,60});
			}
		};
		public List<int[]> getList(){
			return price;
		}
	},Sunday{
		int week = 0;
		private List<int[]> price = new ArrayList<int[]>(){
			{
				add(new int[]{9,12,40});
				add(new int[]{12,18,50});
				add(new int[]{18,20,60});
			}
		};
		public List<int[]> getList(){
			return price;
		}
	};
	public abstract List<int[]> getList();
}
