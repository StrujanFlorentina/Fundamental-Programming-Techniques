package business.restaurant;

import java.util.List;

import business.Observer;
import business.Order;
import business.menuitem.MenuItem;

public interface IRestaurantProcessing {
	// Administrator
	/**
	 * Adauga un nou menu la lista restaurantului
	 * 
	 * @param item Meniul de adaugat
	 * @pre item!=null
	 * @post getNrMenus() == getNrMenus() @pre + 1;
	 * @inv isWellFormed()
	 */
	public void addMenuItem(MenuItem item);

	/**
	 * Stergeo un meniu din lista restaurantului si comenzile aferente
	 * 
	 * @param item Meniul de sters
	 * @pre item!=null
	 * @post getNrMenus() == getNrMenus() @pre - 1;
	 * @inv isWellFormed()
	 */
	public void deleteMenuItem(MenuItem item);

	/**
	 * Modifica un meniu al restaurantului
	 * 
	 * @param item meniul de modificat
	 * @param name noul nume al meniului
	 * @pre order!=null
	 * @post getNrMenus() == getNrMenus() @pre;
	 * @inv isWellFormed()
	 */
	public void editMenuItem(MenuItem item, String name);

	/**
	 * Afiseaza toate meniurile restaurantului
	 * 
	 * @pre table != null
	 * @post getNrMenus() == getNrMenus() @pre;
	 * @inv isWellFormed()
	 */
	public void viewMenuItems();

	// Waiter
	/**
	 * Adauga o noua comanda la restaurant
	 * 
	 * @param order    Comanda de adaugat
	 * @param menuItem meniul aferent comenzii
	 * @pre order != null &amp;&amp; menu != null &amp;&amp; containsMenu(menu)
	 * @post getNrOrders() == getNrOrders() @prev + 1 &amp;&amp; orders.getNrMenus()
	 *       == orders.getNrMenus() @prev + 1;
	 * @inv isWellFormed()
	 */
	public void addOrder(Order order, MenuItem menuItem);

	/**
	 * Afiseaza toate comenzile unui restaurant
	 * 
	 * @pre table != null
	 * @post getNrOrders() == getNrOrders() @prev
	 * @inv isWellFormed()
	 */
	public void viewOrders();

	/**
	 * Genereaza o chitanta pentru o camnda
	 * 
	 * @param order Comanda pentru care se face chitanta
	 * @pre order != null &amp;&amp; containsOrder(order)
	 * @post getNrOrders() == getNrOrders() @prev
	 * @inv isWellFormed()
	 */
	public void generateBillOrder(Order order);

	public void registerObserver(Observer chef);

	public List<MenuItem> getMenus();

}
