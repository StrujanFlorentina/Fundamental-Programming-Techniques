package presentation.table;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import business.restaurant.RestaurantHashTable;

public class TableOrders {
	JFrame f;
	JTable j;
	RestaurantHashTable o;

	// Constructor
	public TableOrders(RestaurantHashTable o) {
		this.o = o;

		f = new JFrame();
		String[] columnNames = { "ID", "Date", "Content" };
		int s = o.getSize();
		Object[][] data = new Object[s][4];
		for (int i = 0; i < s; i++)
			data[i] = new Object[] { o.getO().get(i).getOrderID(), o.getO().get(i).getDate(),
					o.toStringM(o.get(o.getO().get(i))) };
		j = new JTable(data, columnNames);
		j.setBounds(50, 50, 500, 500);

		JScrollPane sp = new JScrollPane(j);
		f.setTitle("Order");
		f.add(sp);
		f.setSize(500, 600);
	}

	public void run() {
		f.setVisible(true);
	}

}
