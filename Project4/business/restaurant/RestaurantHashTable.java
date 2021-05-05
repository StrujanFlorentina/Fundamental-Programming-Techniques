package business.restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.Order;
import business.menuitem.MenuItem;

public class RestaurantHashTable implements Serializable {
	private static final long serialVersionUID = 1L;
	private HashMap<Order, List<MenuItem>> orders;

	public RestaurantHashTable() {
		orders = new HashMap<Order, List<MenuItem>>();
	}

	// adauga un menu item la lista de menu item a unei comenzi
	public void put(Order order, MenuItem item) {
		int ok = 0;
		for (Order o : getO())
			if (order.getOrderID() == o.getOrderID()) {
				orders.get(o).add(item);
				ok = 1;
			}
		if (ok == 0) { // creeaza o noua lista de menu item de adaugat in hash map
			List<MenuItem> list = new ArrayList<MenuItem>();
			list.add(item);
			orders.put(order, list);
		}
	}

	// returneaza lista de meniuri ale unei comanzi
	public List<MenuItem> get(Order order) {
		return orders.get(order);
	}

	public String toStringM(List<MenuItem> l) {
		String s = "";
		for (MenuItem m : l)
			s += m.getItems() + ", ";
		return s;

	}

	public int getNrMenus() {
		int nr = 0;
		for (Order o : orders.keySet())
			nr += get(o).size();
		return nr;
	}

	public List<Order> getO() {
		List<Order> o = new ArrayList<Order>();
		for (Order ord : orders.keySet())
			o.add(ord);
		return o;
	}

	// verifica daca tabela contine un anumit meniu
	public boolean containsValue(MenuItem item) {
		for (Order ord : orders.keySet()) {
			List<MenuItem> itemsForOrd = orders.get(ord);
			for (MenuItem it : itemsForOrd)
				if (it.equals(item))
					return true;
		}
		return false;
	}

	// verifica daca tabela contine un anumit o comanda anume
	public boolean containsKey(Order order) {
		return orders.containsKey(order);
	}

	// sterge un meniu din lista de meniuri ale unei comenzi
	public boolean remove(Order order, MenuItem item) {
		if (!containsKey(order) || !containsValue(item))
			return false;
		List<MenuItem> itemsForOrd = get(order);
		itemsForOrd.remove(item);
		orders.put(order, itemsForOrd);
		return true;
	}

	// sterge o comanda din orders cu toate meniurile aferente
	public boolean removeO(Order order) {
		if (!orders.containsKey(order))
			return false;
		List<MenuItem> itemsForOrd = get(order);
		for (MenuItem item : itemsForOrd)
			remove(order, item);
		orders.remove(order);
		return true;
	}

	// verifica daca tabela e goala
	public boolean isEmpty() {
		return orders.isEmpty();
	}

	// returneaza marimea tabelei( nr de mapari cheie-valoare )
	public int getSize() {
		return orders.size();
	}

	public List<MenuItem> values() {
		List<MenuItem> list = new ArrayList<MenuItem>();
		for (Order o : orders.keySet()) {
			List<MenuItem> itemsForOrd = orders.get(o);
			for (MenuItem it : itemsForOrd)
				list.add(it);
		}
		return list;
	}

	// verifica corectitudinea tabelei
	public boolean isWellFormed() {
		for (Order ord : orders.keySet()) {
			if (orders.get(ord) == null)
				return false;
		}
		return true;
	}
}
