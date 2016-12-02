import java.util.ArrayList;
import java.util.List;

public class Results {

	private Type type;
	private List<Result> results;
	
	public Results(){
		this.results = new ArrayList<Result>();	
	}
	
	public void add(Result res){
		if (results == null)
			return;
		else
			results.add(res);
	}
			
	public List<Result> getResults(){
		return this.results;
	}
}

