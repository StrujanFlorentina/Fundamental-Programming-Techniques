package componente;

import java.util.concurrent.PriorityBlockingQueue;

public class Coada implements Runnable {
	private PriorityBlockingQueue<Client> coada = new PriorityBlockingQueue<Client>();
	private String queueNr;
	private float avgServTime = 0;
	private int clientsNr = 0;

	public Coada(String queueNr) {
		this.queueNr = queueNr;
	}

	public int getSize() {
		return coada.size();
	}

	public String getQueueNr() {
		return queueNr;
	}

	public int getClientsNr() {
		return clientsNr;
	}

	public float getAvgServTime() {
		return avgServTime;
	}

	public int getTServQueue() {
		int servTime = 0;
		for (Client c : coada)
			servTime += c.getServiceT();
		return servTime;
	}

	public void addClient(Client client) {
		coada.add(client);
	}

	@Override
	public String toString() {
		String s = new String("");
		if (coada.isEmpty())
			return queueNr + ": closed";
		else {
			for (Client c : coada)
				s += c.toString() + "; ";
			return queueNr + ": " + s;
		}
	}

	public synchronized void processClient() {
		Client client = coada.peek();
		avgServTime += client.getServiceT();
		clientsNr++;
		System.out.println("--PROCESSING-- Clientul cu id ul " + client.getId() + " are timpul de procesare "
				+ client.getServiceT() + " secunde si momentul ajungerii " + client.getArrivalT());

		try {
			Thread.sleep(client.getServiceT());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		coada.remove();
		for (Client c : coada) {
			c.setServiceT(c.getServiceT() - client.getServiceT());
		}

		System.out.println("--EXIT-- Clientul" + client.getId() + " s-a procesat si a fost eliminat din" + queueNr);
	}

	@Override
	public void run() {
		System.out.println("--STARTING-- " + Thread.currentThread().getName() + " reprezinta " + queueNr);
		while (true) {
			if (!coada.isEmpty()) {
				System.out.println("--INFO-- Nr de clienti in " + queueNr + " este " + getSize());
				processClient();
			}

		}
	}
}
