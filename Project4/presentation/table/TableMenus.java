package presentation.table;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import business.menuitem.MenuItem;

public class TableMenus {
	JFrame f;
	JTable j;
	List<MenuItem> m;

	// Constructor
	public TableMenus(List<MenuItem> m) {
		this.m = m;

		f = new JFrame();
		String[] columnNames = { "Name", "Price", "Content" };
		int s = m.size();
		Object[][] data = new Object[s][3];
		for (int i = 0; i < s; i++)
			data[i] = new Object[] { m.get(i).getName(), m.get(i).computePrice(), m.get(i).getItems() };

		j = new JTable(data, columnNames);
		j.setBounds(50, 50, 500, 500);

		JScrollPane sp = new JScrollPane(j);
		f.setTitle("Menu Item");
		f.add(sp);
		f.setSize(500, 600);
	}

	public void run() {
		f.setVisible(true);
	}

}
