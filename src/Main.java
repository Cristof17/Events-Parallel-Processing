
public class Main {

	private static int nrThreads = 0;
	
	public static void main(String[] args){
		
		if (args.length < 6){
			System.out.println("Too few argumens");
			return;
		}
		
		//each thread has it's file 
		nrThreads = args.length - 2;
		
		//create the queue with the given size
		WorkPool workPool = new WorkPool(nrThreads, Integer.parseInt(args[1]));
		
	}
}
