package lucruFisiere;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CitireFisier {
	private int nrClients;
	private int nrQueues;
	private int tMaxSim;
	private int tMinArr;
	private int tMaxArr;
	private int tMinServ;
	private int tMaxServ;

	public void citeste(String fisier) {
		String st;
		try {
			File file = new File(fisier);
			BufferedReader br = new BufferedReader(new FileReader(file));
			if ((st = br.readLine()) != null)
				nrClients = Integer.parseInt(st);
			if ((st = br.readLine()) != null)
				nrQueues = Integer.parseInt(st);
			if ((st = br.readLine()) != null)
				tMaxSim = Integer.parseInt(st);
			if ((st = br.readLine()) != null) {
				String aux[] = st.split(",");
				tMinArr = Integer.parseInt(aux[0]);
				tMaxArr = Integer.parseInt(aux[1]);
			}
			if ((st = br.readLine()) != null) {
				String aux[] = st.split(",");
				tMinServ = Integer.parseInt(aux[0]);
				tMaxServ = Integer.parseInt(aux[1]);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Fisierul nu a fost gasit");
		} catch (IOException e) {
			System.out.println("Eroare la citire");
		}

	}

	public int getNrClients() {
		return nrClients;
	}

	public int getNrQueues() {
		return nrQueues;
	}

	public int gettMaxSim() {
		return tMaxSim;
	}

	public int gettMinArr() {
		return tMinArr;
	}

	public int gettMaxArr() {
		return tMaxArr;
	}

	public int gettMinServ() {
		return tMinServ;
	}

	public int gettMaxServ() {
		return tMaxServ;
	}

	@Override
	public String toString() {
		return "CitireFisier [nrClients=" + nrClients + ", nrQueues=" + nrQueues + ", tMaxSim=" + tMaxSim + ", tMinArr="
				+ tMinArr + ", tMaxArr=" + tMaxArr + ", tMinServ=" + tMinServ + ", tMaxServ=" + tMaxServ + "]";
	}

}