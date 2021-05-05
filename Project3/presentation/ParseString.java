package presentation;

import java.util.ArrayList;

/**
 * <h1>Parse String</h1> Clasa Parse String trasforma un String intr-un vector
 * de String-uri ce reprezinta cuvintele din care e format String-ul dat
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class ParseString {
	ArrayList<String> comanda = new ArrayList<String>();

	public void parse(String line) {

		String aux[] = line.split(": ");
		if (aux.length == 2) {
			String instructiune[] = aux[0].split(" ");
			if (instructiune.length == 2) {
				comanda.add(instructiune[0]);
				comanda.add(instructiune[1]);
				String argumente[] = aux[1].split(", ");
				if (argumente.length == 3) {
					comanda.add(argumente[0]);
					comanda.add(argumente[1]);
					comanda.add(argumente[2]);
				} else if (argumente.length == 2) {
					comanda.add(argumente[0]);
					comanda.add(argumente[1]);
				} else
					comanda.add(argumente[0]);
			} else {
				comanda.add(instructiune[0]);
				String argumente[] = aux[1].split(", ");
				if (argumente.length == 3) {
					comanda.add(argumente[0]);
					comanda.add(argumente[1]);
					comanda.add(argumente[2]);
				} else if (argumente.length == 2) {
					comanda.add(argumente[0]);
					comanda.add(argumente[1]);
				} else
					comanda.add(argumente[0]);
			}
		} else {
			String report[] = aux[0].split(" ");
			comanda.add(report[0]);
			comanda.add(report[1]);
		}
	}

	public ArrayList<String> toCommands() {
		return comanda;
	}

}
