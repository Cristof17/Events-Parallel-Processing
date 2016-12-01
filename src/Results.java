import java.util.ArrayList;
import java.util.List;

public class Results {

	private Type type;
	private List<Result> results;
	
	public Results(int nrThreads, int nrEvents){
		this.results = new ArrayList<Result>(nrThreads * nrEvents);	
	}
	
	public void add(Event e){
		if (results == null)
			return;
		else
			results.add(new Result(e.getType()));
	}
			
	public List<Result> getResults(){
		return this.results;
	}
}

