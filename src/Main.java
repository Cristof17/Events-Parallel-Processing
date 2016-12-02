import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Main {

	private static int nrThreads = 0;
	private static Worker[] workers;
	private static Results results;
	private static Semaphore semaphore;
	
	public static void main(String[] args){
		
		if (args.length < 3){
			System.out.println("Too few argumens " + args.length);
			return;
		}
		
		//each thread has it's file 
		nrThreads = args.length - 2;
		
		//create the queue with the given size
		WorkPool workPool = new WorkPool(nrThreads, Integer.parseInt(args[0]));
		semaphore = new Semaphore(nrThreads);
		results = new Results();
		workers = new Worker[nrThreads];
		for (int i = 0; i < nrThreads; ++i){
			workers[i] = new Worker(workPool, args[i + 2], results, semaphore);
		}

		
		/**
		 * Run logic 
		 */
		try {
			semaphore.acquire(nrThreads);
			generateEvents(workers, workPool);
			semaphore.acquire(nrThreads);
			writeOutput(results);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**
		 * End of run logic
		 */
	}
	
	public static void writeOutput(Results results){
		
		BufferedWriter primeBuffer ;
		BufferedWriter factBuffer 	;
		BufferedWriter squareBuffer;
		BufferedWriter fibBuffer 	;
		
		List<Result> primeResults = new ArrayList<Result>();
		List<Result> squareResults = new ArrayList<Result>();
		List<Result> factResults = new ArrayList<Result>();
		List<Result> fibResults = new ArrayList<Result>();
		
		try {
		primeBuffer = new BufferedWriter(new FileWriter(new File("PRIME.out")));
		factBuffer 	= new BufferedWriter(new FileWriter(new File("FACT.out")));
		squareBuffer= new BufferedWriter(new FileWriter(new File("SQUARE.out")));
		fibBuffer 	= new BufferedWriter(new FileWriter(new File("FIB.out")));
		
		for (Result result : results.getResults()){
			switch(result.getType()){
			case FACT:
				factResults.add(result);
				break;
			case PRIME:
				primeResults.add(result);
				break;
			case FIB:
				fibResults.add(result);
				break;
			case SQUARE:
				squareResults.add(result);
				break;
			}
		}
		
		//sort the results
		Collections.sort(primeResults);
		Collections.sort(squareResults);
		Collections.sort(fibResults);
		Collections.sort(factResults);
		
		for(Result primeResult : primeResults){
			primeBuffer.append(primeResult.getVal() + "\r\n");
		}
		
		for(Result squareResult : squareResults){
			squareBuffer.append(squareResult.getVal() + "\r\n");
		}
		
		for(Result fibResult : fibResults){
			fibBuffer.append(fibResult.getVal() + "\r\n");
		}
		
		for(Result factResult : factResults){
			factBuffer.append(factResult.getVal() + "\r\n");
		}

		
		primeBuffer	.flush(); 
		factBuffer 	.flush();
		squareBuffer.flush();
		fibBuffer 	.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void generateEvents(Worker[] workers, WorkPool workPool){
		for (int i = 0; i < workers.length; ++i)
			workers[i].start();
	}
}
