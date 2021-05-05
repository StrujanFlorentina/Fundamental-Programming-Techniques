package presentation;

import java.util.ArrayList;
import java.util.List;

import business.menuitem.MenuItem;
import business.restaurant.IRestaurantProcessing;
import business.restaurant.Restaurant;
import business.restaurant.RestaurantHashTable;
import data.RestaurantDeserializator;

public class RestaurantMVC {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		RestaurantDeserializator deser = new RestaurantDeserializator();
		List<MenuItem> menus = new ArrayList<MenuItem>();
		RestaurantHashTable orders = new RestaurantHashTable();

		IRestaurantProcessing model = new Restaurant(menus, orders);
		deser.deserialize((Restaurant) model);
		WaiterView viewW = new WaiterView(model);
		WaiterController controllerW = new WaiterController(model, viewW);

		AdministratorView viewA = new AdministratorView(model);
		AdministratorController controllerA = new AdministratorController(model, viewA, viewW);

		viewW.setVisible(true);
		viewA.setVisible(true);
	}
}
