package business.restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import business.Observable;
import business.Observer;
import business.Order;
import business.menuitem.BaseProduct;
import business.menuitem.CompositeProduct;
import business.menuitem.MenuItem;
import data.GenerateBill;
import presentation.table.TableMenus;
import presentation.table.TableOrders;

public class Restaurant implements IRestaurantProcessing, Serializable, Observable {
	private List<Observer> observers;
	private static final long serialVersionUID = 1L;
	private List<MenuItem> menus;
	private RestaurantHashTable orders;
	private GenerateBill bill = new GenerateBill();

	public Restaurant(List<MenuItem> menus, RestaurantHashTable orders) {
		this.observers = new ArrayList<>();
		this.menus = menus;
		this.orders = orders;
	}

	public Restaurant() {
	}

	@Override
	public void addMenuItem(MenuItem item) {
		// precondition
		assert item != null;
		int prevNrMenus = menus.size();
		if (item instanceof BaseProduct)
			menus.add(item);
		else if (item instanceof CompositeProduct) {
			int ok = 0;
			for (MenuItem m : menus)
				if (item.getName().equals(m.getName())) {
					((CompositeProduct) m).addMenuItem(item);
					ok = 1;
				}
			if (ok == 0)
				menus.add(item);
		}
		// postcondition
		assert getNrMenus() == prevNrMenus + 1;
		// invariant
		assert isWellFormed();

	}

	@Override
	public void deleteMenuItem(MenuItem item) {
		// precondition
		assert item != null && containsMenu(item);
		int prevNrMenus = menus.size();
		menus.remove(item);
		if (orders.containsValue(item))
			for (Order o : orders.getO())
				orders.remove(o, item);
		// postcondition
		assert getNrMenus() == prevNrMenus - 1;
		// invariant
		assert isWellFormed();
	}

	@Override
	public void editMenuItem(MenuItem item, String name) {
		// precondition
		assert item != null && containsMenu(item);
		int nrMenus = menus.size();
		for (MenuItem m : menus)
			if (m.equals(item)) {
				m.editName(name);
			}
		// postcondition
		assert nrMenus == getNrMenus();
		// invariant
		assert isWellFormed();
	}

	@Override
	public void viewMenuItems() {
		TableMenus table = new TableMenus(menus);
		// precondition
		assert table != null;
		int nrMenus = menus.size();
		table.run();
		// postcondition
		assert nrMenus == getNrMenus();
		// invariant
		assert isWellFormed();
	}

	@Override
	public void addOrder(Order order, MenuItem menu) {
		// precondition
		assert order != null && menu != null && containsMenu(menu);
		int prevNrOrders = orders.getSize();
		int prevNrMenus = orders.getNrMenus();
		orders.put(order, menu);
		notifyObservers(order);
		// postcondition
		assert getNrOrders() == prevNrOrders + 1 && orders.getNrMenus() == prevNrMenus + 1;
		// invariant
		assert isWellFormed();
	}

	@Override
	public void viewOrders() {
		TableOrders table = new TableOrders(orders);
		// preconditie
		assert table != null;
		int nrOrders = orders.getSize();
		table.run();
		// postcondition
		assert nrOrders == getNrOrders();
		// invariant
		assert isWellFormed();
	}

	@Override
	public void generateBillOrder(Order order) {
		// precondition
		assert order != null && containsOrder(order);
		int nrOrders = orders.getSize();
		List<MenuItem> m = orders.values();
		bill.generate(order, m);
		// postcondition
		assert nrOrders == getNrOrders();
		// invariant
		assert isWellFormed();
	}

	public List<MenuItem> getMenusOfOrder(Order order) {
		return orders.get(order);
	}

	public List<Order> getOrders() {
		return orders.getO();
	}

	@Override
	public List<MenuItem> getMenus() {
		return orders.values();
	}

	public int getNrOrders() {
		List<Order> o = orders.getO();
		int size = orders.getSize();
		// postcondititon
		assert o.equals(orders.getO());
		// invariant
		assert isWellFormed();
		return size;
	}

	public int getNrMenus() {
		List<MenuItem> m = menus;
		int size = menus.size();
		// postcondititon
		assert m.equals(menus);
		// invariant
		assert isWellFormed();
		return size;
	}

	public boolean isWellFormed() {
		return orders.isWellFormed();
	}

	public boolean containsMenu(MenuItem menu) {
		// precondition
		assert menu != null;
		List<MenuItem> m = getMenus();
		boolean success = menus.contains(menu);
		// postconditions
		assert m.equals(getMenus());
		// invariant
		assert isWellFormed();
		return success;
	}

	public boolean containsOrder(Order order) {
		// precondition
		assert order != null;
		List<MenuItem> m = getMenus();
		List<Order> o = orders.getO();
		boolean success = orders.containsKey(order);
		// postconditions
		assert m.equals(getMenus()) && o.equals(orders.getO());
		// invariant
		assert isWellFormed();
		return success;
	}

	@Override
	public void registerObserver(Observer obs) {
		observers.add(obs);

	}

	@Override
	public void removeObserver(Observer obs) {
		observers.remove(obs);
	}

	@Override
	public void notifyObservers(Order order) {
		for (Observer obs : observers)
			obs.update(order);

	}

}
