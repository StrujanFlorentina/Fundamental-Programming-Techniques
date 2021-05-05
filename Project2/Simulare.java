package simulare;

import lucruFisiere.CitireFisier;
import prelucrare.Service;

public class Simulare {

	public static void main(String[] args) throws InterruptedException {
		CitireFisier date = new CitireFisier();
		date.citeste(args[1]);
		int nrClients = date.getNrClients();
		int nrQueues = date.getNrQueues();
		int tMaxSim = date.gettMaxSim();
		int tMinArr = date.gettMinArr();
		int tMaxArr = date.gettMaxArr();
		int tMinServ = date.gettMinServ();
		int tMaxServ = date.gettMaxServ();
		Service s = new Service(args[2], nrClients,
				nrQueues, tMaxSim, tMinArr, tMaxArr, tMinServ, tMaxServ);
	}

}
