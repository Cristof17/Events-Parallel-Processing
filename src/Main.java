import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	private static int nrThreads = 0;
	private static Worker[] workers;
	private static ArrayList<BufferedReader> buffers;
	
	public static void main(String[] args){
		
		if (args.length < 3){
			System.out.println("Too few argumens " + args.length);
			return;
		}
		
		//each thread has it's file 
		nrThreads = args.length - 2;
		
		buffers = new ArrayList<BufferedReader>();
		//create the buffers to read from
		for (int i = 2; i < args.length; ++i){
			try{
				BufferedReader reader = new BufferedReader(new FileReader(new File(args[i])));
				buffers.add(reader);
			}catch (FileNotFoundException e){
				e.printStackTrace();
			}
		}

		//create the queue with the given size
		WorkPool workPool = new WorkPool(nrThreads, Integer.parseInt(args[1]));
		workers = new Worker[nrThreads];
		for (int i = 0; i < nrThreads; ++i){
			workers[i] = new Worker(workPool, buffers.get(i));
		}
				
		generateEvents(buffers, workers, workPool);
	}
	
	public static void writeOutput(Results results){
		
		BufferedWriter primeBuffer ;
		BufferedWriter factBuffer 	;
		BufferedWriter squareBuffer;
		BufferedWriter fibBuffer 	;
		
		try {
		primeBuffer = new BufferedWriter(new FileWriter(new File("PRIME.out")));
		factBuffer 	= new BufferedWriter(new FileWriter(new File("FACT.out")));
		squareBuffer= new BufferedWriter(new FileWriter(new File("SQUARE.out")));
		fibBuffer 	= new BufferedWriter(new FileWriter(new File("FIB.out")));
		
		for (Result result : results.getResults()){
			switch(result.getType()){
			case FACT:
				break;
			case PRIME:
				break;
			case FIB:
				break;
			case SQUARE:
				break;
			}
		}
		
		primeBuffer	.close(); 
		factBuffer 	.close();
		squareBuffer.close();
		fibBuffer 	.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void generateEvents(ArrayList<BufferedReader> buffers, Worker[] workers, WorkPool workPool){
		for (int i = 0; i < workers.length; ++i)
			workers[i].start();
	}
}
