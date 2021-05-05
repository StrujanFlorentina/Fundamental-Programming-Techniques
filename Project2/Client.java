package componente;

public class Client implements Comparable<Client> {
	private int id;
	private int arrivalT;
	private int serviceT;

	public Client(int id, int arrivalT, int serviceT) {
		this.id = id;
		this.arrivalT = arrivalT;
		this.serviceT = serviceT;
	}

	public int getId() {
		return id;
	}

	public int getArrivalT() {
		return arrivalT;
	}

	public int getServiceT() {
		return serviceT;
	}

	public void setServiceT(int serv) {
		serviceT = serv;
	}

	@Override
	public String toString() {
		return "(" + id + "," + arrivalT + "," + serviceT + ") ";
	}

	@Override
	public int compareTo(Client c) {
		return arrivalT - c.getArrivalT();
	}

}
