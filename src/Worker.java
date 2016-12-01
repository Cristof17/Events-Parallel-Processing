import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Clasa ce reprezinta un thread worker.
 */
class Worker extends Thread {
	private WorkPool wp;
	private String filename;

	public Worker(WorkPool workpool, String filename) {
		this.wp = workpool;
		this.filename = filename;
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
	}
}

