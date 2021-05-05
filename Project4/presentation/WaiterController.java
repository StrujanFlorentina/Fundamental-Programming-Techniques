package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import business.Observer;
import business.Order;
import business.menuitem.MenuItem;
import business.restaurant.IRestaurantProcessing;
import data.RestaurantSerializator;

public class WaiterController {
	private RestaurantSerializator ser;

	private IRestaurantProcessing m_model;
	private WaiterView m_view;

	WaiterController(IRestaurantProcessing model, WaiterView view) {
		m_model = model;
		m_view = view;

		view.addAddListener(new AddListener());
		view.addComputeBillListener(new ComputeBillListener());
		// view.addWindowListener(new WindowAdapter() {
		// @Override
		// public void windowClosing(WindowEvent e) {
		// ser.serialize((Restaurant) m_model);
		// System.out.println("Window Closed");
		// e.getWindow().dispose();
		// }
		// });

	}

	class AddListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Observer chef = new ChefGUI();
			int orderID;
			try {
				orderID = Integer.parseInt(m_view.getOrderID());
				MenuItem menu = m_view.getSelectedMenu();
				m_model.addOrder(new Order(orderID), menu);
				m_model.viewOrders();
				m_model.registerObserver(chef);

			} catch (NumberFormatException nfex) {
				m_view.showError("Bad input");
			}
		}
	}

	class ComputeBillListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int orderID;
			try {
				orderID = Integer.parseInt(m_view.getOrderID());

				m_model.generateBillOrder(new Order(orderID));

			} catch (NumberFormatException nfex) {
				m_view.showError("Bad input");
			}
		}
	}

}
