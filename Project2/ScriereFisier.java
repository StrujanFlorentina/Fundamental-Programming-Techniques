package lucruFisiere;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import componente.Client;
import componente.Coada;

public class ScriereFisier {
	private PriorityBlockingQueue<Client> clienti;
	private ArrayList<Coada> asteptare;

	public ScriereFisier(PriorityBlockingQueue<Client> clienti, ArrayList<Coada> asteptare) {
		this.clienti = clienti;
		this.asteptare = asteptare;
	}

	public void scrie(int time, String fisier) {
		FileWriter f = null;
		try {
			f = new FileWriter(fisier, true);
			f.write("Time " + time + "\n");
			f.write("Waiting clients" + clienti.toString() + "\n");
			for (Coada c : asteptare)
				f.write(c.toString() + "\n");
			f.flush();
			f.close();
		} catch (IOException e) {
			System.out.println("Eroare la scriere");
		}
	}

}
