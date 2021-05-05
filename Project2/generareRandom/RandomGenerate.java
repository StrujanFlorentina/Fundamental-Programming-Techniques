package generareRandom;

import java.util.Random;

import componente.Client;

public class RandomGenerate {
	private int tMinArr;
	private int tMaxArr;
	private int tMinServ;
	private int tMaxServ;
	private Random random = new Random();

	public RandomGenerate(int tMinArr, int tMaxArr, int tMinServ, int tMaxServ) {
		this.tMinArr = tMinArr;
		this.tMaxArr = tMaxArr;
		this.tMinServ = tMinServ;
		this.tMaxServ = tMaxServ;
	}

	public Client generate(int id) {
		int arrivalT = tMinArr + random.nextInt(tMaxArr - tMinArr + 1);
		int serviceT = tMinServ + random.nextInt(tMaxServ - tMinServ + 1);
		return new Client(id, arrivalT, serviceT);
	}

}
