package lucruFisiere;

import java.io.FileWriter;
import java.io.IOException;

public class WriteAvgWaitingTime {
	private int sumTimpi;
	private int nrClients;
	double a;

	public WriteAvgWaitingTime(int sumTimpi, int nrClients) {
		this.sumTimpi = sumTimpi;
		this.nrClients = nrClients;
	}

	public void scrie(String fisier) {
		FileWriter f = null;
		try {
			a = (double) sumTimpi / (double) nrClients;
			f = new FileWriter(fisier, true);
			f.append("Average waiting time:  " + a);
			f.flush();
			f.close();
		} catch (IOException e) {
			System.out.println("Eroare la scriere");
		}
	}
}
