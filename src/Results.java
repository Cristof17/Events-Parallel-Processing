import java.util.ArrayList;
import java.util.List;

public class Results {

	public Type type;
	public List<Result> results;
	
	public Results(int nrThreads, int nrEvents){
		this.results = new ArrayList<Result>(nrThreads * nrEvents);	
	}
	
	public void add(Event e){
		if (results == null)
			return;
		else
			results.add(new Result(e.getType()));
	}
		
	private class Result{
		public Type type;
		public Result(Type type){
			this.type =type;
		}
		public Type getType() {
			return type;
		}
		public void setType(Type type) {
			this.type = type;
		}
		
	}
}
