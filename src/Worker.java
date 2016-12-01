import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Clasa ce reprezinta un thread worker.
 */
class Worker extends Thread {
	WorkPool wp;
	BufferedReader fileReader;

	public Worker(WorkPool workpool, BufferedReader reader) {
		this.wp = workpool;
		this.fileReader = reader;
	}

	/**
	 * Procesarea unei solutii partiale. Aceasta poate implica generarea unor
	 * noi solutii partiale care se adauga in workpool folosind putWork().
	 * Daca s-a ajuns la o solutie finala, aceasta va fi afisata.
	 */
	void processEvent(Event e) {
		//...
	}
	
	public void run() {
		
		try {
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
				wp.putWork(ev);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Thread-ul worker " + this.getName() + " a pornit...");
		while (true) {
			Event e = wp.getWork();
			if (e == null)
				break;
			
			processEvent(e);
		}
		System.out.println("Thread-ul worker " + this.getName() + " s-a terminat...");
	}

	
}

