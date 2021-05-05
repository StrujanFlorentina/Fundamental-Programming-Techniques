package data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import business.Order;
import business.menuitem.MenuItem;

public class GenerateBill {

	public GenerateBill() {
	}

	public void generate(Order ord, List<MenuItem> list) {
		int total = 0;
		FileWriter f = null;
		try {
			f = new FileWriter("D:\\PT2020\\pt2020_302210_strujan_florentina_assignment_4\\BillClientId"
					+ ord.getOrderID() + ".txt", true);
			f.write("Order id: " + ord.getOrderID() + "\n");
			f.write("Date: " + ord.getDate() + "\n");
			for (MenuItem m : list)
				total += m.computePrice();
			f.write("Total price: " + total + "\n");
			f.close();
		} catch (IOException e) {
			System.out.println("Eroare la scriere");
		}
	}

}
