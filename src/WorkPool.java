import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Clasa ce implementeaza un "work pool" conform modelului "replicated workers".
 * Task-urile introduse in work pool sunt obiecte de tipul PartialSolution.
 *
 */
public class WorkPool {
	int nThreads; // nr total de thread-uri worker
	int maxSize;
	int nWaiting = 0; // nr de thread-uri worker care sunt blocate asteptand un task
	public boolean ready = false; // daca s-a terminat complet rezolvarea problemei 
	
	ArrayBlockingQueue<Event> tasks;

	/**
	 * Constructor pentru clasa WorkPool.
	 * @param nThreads - numarul de thread-uri worker
	 */
	public WorkPool(int nThreads, int maxSize) {
		this.nThreads = nThreads;
		this.maxSize = maxSize;
		this.tasks = new ArrayBlockingQueue<Event>(maxSize);
	}

	/**
	 * Functie care incearca obtinera unui task din workpool.
	 * Daca nu sunt task-uri disponibile, functia se blocheaza pana cand 
	 * poate fi furnizat un task sau pana cand rezolvarea problemei este complet
	 * terminata
	 * @return Un task de rezolvat, sau null daca rezolvarea problemei s-a terminat 
	 */
	public synchronized Event getWork() {
		if (tasks.size() == 0) { // workpool gol
			nWaiting++;
			/* condtitie de terminare:
			 * nu mai exista nici un task in workpool si nici un worker nu e activ 
			 */
			if (nWaiting == nThreads) {
				ready = true;
				/* problema s-a terminat, anunt toti ceilalti workeri */
				notifyAll();
				return null;
			} else {
				while (!ready && tasks.size() == 0) {
					try {
						this.wait();
					} catch(Exception e) {e.printStackTrace();}
				}
				
				if (ready)
				    /* s-a terminat prelucrarea */
				    return null;

				nWaiting--;
				
			}
		}
		try {
			return tasks.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; //should not get here if the queue gets empty
	}


	/**
	 * Functie care introduce un task in workpool.
	 * @param sp - task-ul care trebuie introdus 
	 */
	synchronized void putWork(Event sp) {
		System.out.println("WorkPool - adaugare task: " + sp);
		try {
			tasks.put(sp);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* anuntam unul dintre workerii care asteptau */
		this.notify();

	}


}


