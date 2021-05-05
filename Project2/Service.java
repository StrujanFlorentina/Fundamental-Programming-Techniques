package prelucrare;

import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import componente.Client;
import componente.Coada;
import generareRandom.RandomGenerate;
import lucruFisiere.ScriereFisier;
import lucruFisiere.WriteAvgWaitingTime;

public class Service {
	private int nrClients;
	private int nrQueues;
	private int tMaxSim;
	private int tMinArr;
	private int tMaxArr;
	private int tMinServ;
	private int tMaxServ;
	private String fisier;

	private int avgWT;
	private int clientsProc;

	private PriorityBlockingQueue<Client> clienti = new PriorityBlockingQueue<Client>();
	private ArrayList<Coada> cozi = new ArrayList<>(nrQueues);
	public ArrayList<Thread> threads = new ArrayList<>(nrQueues);

	private int currentSecond = 0;

	private ScriereFisier s = new ScriereFisier(clienti, cozi);

	public void createThreads() {
		for (int i = 0; i < nrQueues; i++) {
			int aux = i + 1;
			Coada c = new Coada("Queue " + aux + ": ");
			cozi.add(c);
			Thread t = new Thread(cozi.get(i));
			threads.add(t);
			threads.get(i).setName("Thread " + aux + ": ");
		}
	}

	public void startThreads() {
		for (Thread thread : threads) {
			thread.start();
		}
	}

	public void stopThreads() {
		for (Thread thread : threads)
			thread.interrupt();
	}

	public void generateClients() {
		RandomGenerate r = new RandomGenerate(tMinArr, tMaxArr, tMinServ, tMaxServ);
		for (int i = 0; i < nrClients; i++) {
			Client c = r.generate(i + 1);
			clienti.add(c);
		}

	}

	public int getMinTimeServQueues() {
		int tMinServQueues = 99999;
		int minQueue = 0;
		for (int i = 0; i < nrQueues; i++) {
			if (cozi.get(i).getTServQueue() < tMinServQueues) {
				tMinServQueues = cozi.get(i).getTServQueue();
				minQueue = i;
			}
		}
		return minQueue;
	}

	public void sharingClients() throws InterruptedException {
		while (currentSecond <= tMaxSim) {
			s.scrie(currentSecond, fisier);

			if (currentSecond > tMaxSim || clientsProc == nrClients) {
				for (Thread thread : threads) {
					System.out.println("--STOPPING-- " + thread.getName());
					thread.interrupt();
				}
				break;
			}
			Client c = clienti.peek();
			int getRightQueue = getMinTimeServQueues();
			cozi.get(getRightQueue).addClient(c);
			avgWT += cozi.get(getRightQueue).getTServQueue();
			clientsProc++;
			Thread.sleep(c.getServiceT());
			clienti.remove();
			currentSecond += c.getServiceT();
		}
		WriteAvgWaitingTime w = new WriteAvgWaitingTime(avgWT, clientsProc);
		w.scrie(fisier);

	}

	public Service(String fisier, int nrClients, int nrQueues, int tMaxSim, int tMinArr, int tMaxArr, int tMinServ,
			int tMaxServ) throws InterruptedException {
		this.fisier = fisier;
		this.nrClients = nrClients;
		this.nrQueues = nrQueues;
		this.tMaxSim = tMaxSim;
		this.tMinArr = tMinArr;
		this.tMaxArr = tMaxArr;
		this.tMinServ = tMinServ;
		this.tMaxServ = tMaxServ;
		createThreads();
		startThreads();
		generateClients();
		sharingClients();
		stopThreads();
	}

}
