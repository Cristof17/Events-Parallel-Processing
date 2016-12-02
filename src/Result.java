import java.util.Comparator;

public class Result implements Comparable<Result>, Comparator<Result>{
		public Type type;
		public int val;
		
		public Result(Type type){
			this.type = type;
			this.val = 0;
		}
		
		public Result(Type type, int val){
			this.type =type;
			this.val = val;
		}
	
		public Type getType() {
			return type;
		}
		public void setType(Type type) {
			this.type = type;
		}
		
		
		
		public int getVal() {
			return val;
		}

		public void setVal(int val) {
			this.val = val;
		}

		@Override
		public int compareTo(Result param) {
			if (val < param.getVal())
				return -1;
			else if (val == param.getVal())
				return 0;
			return 1;
		}

		@Override
		public int compare(Result param1, Result param2) {
			return param1.compareTo(param2);
		}
	}