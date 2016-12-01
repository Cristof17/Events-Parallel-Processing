
public class Event {

	private Type type;
	private int N;
	private int time;
	
	public Event(Type type, int N, int time){
		this.type = type;
		this.N = N;
		this.time = time;
	}
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public int getN() {
		return N;
	}
	public void setN(int n) {
		N = n;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	
	
}
