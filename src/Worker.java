import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * Clasa ce reprezinta un thread worker.
 */
class Worker extends Thread {
	private WorkPool wp;
	private String filename;
	private Results results;
	private Semaphore semaphore;

	public Worker(WorkPool workpool, String filename,Results results, Semaphore semaphore ) {
		this.wp = workpool;
		this.filename = filename;
		this.results = results;
		this.semaphore = semaphore;
	}

	/**
	 * Procesarea unei solutii partiale. Aceasta poate implica generarea unor
	 * noi solutii partiale care se adauga in workpool folosind putWork().
	 * Daca s-a ajuns la o solutie finala, aceasta va fi afisata.
	 */
	void processEvent(Event e) {
		System.out.println("Executing " + e.getN() + " " + e.getType());
		switch(e.getType()){
		case PRIME:
			int biggestPrime = prime(e.getN());
			results.add(new Result(Type.PRIME, biggestPrime));
			break;
		case SQUARE:
			int biggestSquare = square(e.getN());
			results.add(new Result(Type.SQUARE, biggestSquare));
			break;
		case FACT:
			int maxFactoral = maxFact(e.getN());
			results.add(new Result(Type.FACT, maxFactoral));
			break;
		case FIB:
			int maxFiboPos = maxFibbo(e.getN());
			results.add(new Result(Type.FIB, maxFiboPos));
			break;
		}
	}
	
	public void run() {
		try {
			BufferedReader fileReader =  new BufferedReader(new FileReader(new File(filename)));
			String line = fileReader.readLine();
			StringTokenizer tokenizer;
			
			while (line != null){
				tokenizer = new StringTokenizer(line, " ,");
				String N = tokenizer.nextToken();
				String name = tokenizer.nextToken();
				String time = tokenizer.nextToken();
				line = fileReader.readLine();
				
				Event ev = null;
				switch(name){
				case "FACT":
					ev = new Event(Type.FACT, Integer.parseInt(N), Integer.parseInt(time));
					break;
				case "SQUARE":
					ev = new Event(Type.SQUARE, Integer.parseInt(N), Integer.parseInt(time));
					break;
				case "FIB":
					ev = new Event(Type.FIB, Integer.parseInt(N), Integer.parseInt(time));
					break;
				case "PRIME":
					ev = new Event(Type.PRIME, Integer.parseInt(N), Integer.parseInt(time));
					break;
				}
				tokenizer = null;
				
				//that's what it says in the homework
				Thread.sleep(Integer.parseInt(time));
				
				//assign tasks to the work pool
				wp.putWork(ev);
			}
			fileReader.close();
			fileReader = null;

		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Thread-ul worker " + this.getName() + " a pornit...");
		while (true) {
			Event e = wp.getWork();
			if (e == null)
				break;
			
			processEvent(e);
		}
		System.out.println("Thread-ul worker " + this.getName() + " s-a terminat...");
		semaphore.release();
	}
	
	private static boolean isPrime(int N){
		for (int i = 2; i < Math.sqrt(N); ++i)
			if ((N % i) == 0)
				return false;
		return true;
	}

	static int prime(int max){
		if (max == 0)
			return max;
		if (max == 1)
			return max;
		int maxPrime = -1;
		for (int i = 2; i <= max; ++i){
			if (isPrime(i))
				maxPrime = i;
		}
		return maxPrime;
	}
	
	static int square(int N){
		int maxSquare = -1;
		for (int i = 0; i <= Math.sqrt(N); ++i){
			if (i * i <= N)
				maxSquare = i;
		}
		return maxSquare;
	}
	
	static int maxFact(int max){
		int maxFact = -1;
		for (int i = 0; i < max; ++i){
			if (fact(i) <= max)
				maxFact = i;
		}
		return maxFact;
	}
	
	//do the factorial iterative
	static int fact(int N){
		int prod = 1;
		int i = 1; 
		while (i <= N){
			prod *= i++; 
		}
		return prod;
	}
	
	
	static int maxFibbo(int max){
		int maxPos = -1;
		for (int i = 0; i <= max; ++i){
			if (fibbo(i, 1,0) <= max){				
				maxPos = i;
			}else
				break;
		}
		return maxPos;
	}
	
	static int fibbo(int term, int val, int prev){
//		if (n == 0 || n == 1)
//			return 1;
//		return fibbo(n -1) + fibbo(n -2);
		if(term == 0) return prev;
		if(term == 1) return val;
		return fibbo(term - 1, val+prev, val);
	}
	
}

