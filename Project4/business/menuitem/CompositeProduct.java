package business.menuitem;

import java.util.ArrayList;
import java.util.List;

public class CompositeProduct implements MenuItem {
	private static final long serialVersionUID = 1L;
	private String name;
	private List<MenuItem> items = new ArrayList<MenuItem>();

	public CompositeProduct(String name, List<MenuItem> items) {
		this.name = name;
		this.items = items;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getItems() {
		String s = "";
		for (MenuItem m : items)
			s += m.getItems() + ", ";
		return s;
	}

	public void addMenuItem(MenuItem m) {
		items.add(m);
	}

	@Override
	public int computePrice() {
		int total = 0;
		for (MenuItem m : items)
			total += m.computePrice();
		return total;
	}

	@Override
	public void editName(String newName) {
		this.name = newName;
	}

	@Override
	public String toString() {
		return name + " :" + getItems();
	}
}
