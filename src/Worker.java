/**
 * Clasa ce reprezinta un thread worker.
 */
class Worker extends Thread {
	WorkPool wp;

	public Worker(WorkPool workpool) {
		this.wp = workpool;
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

