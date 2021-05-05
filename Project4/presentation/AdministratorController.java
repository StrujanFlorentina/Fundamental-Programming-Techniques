package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import business.menuitem.BaseProduct;
import business.menuitem.CompositeProduct;
import business.menuitem.MenuItem;
import business.restaurant.IRestaurantProcessing;
import data.RestaurantSerializator;

public class AdministratorController {
	private RestaurantSerializator ser;
	private IRestaurantProcessing m_model;
	private AdministratorView m_view;
	private WaiterView w_view;

	AdministratorController(IRestaurantProcessing model, AdministratorView view, WaiterView wview) {
		m_model = model;
		m_view = view;
		w_view = wview;

		view.addAddListener(new AddListener());
		view.addDeleteListener(new DeleteListener());
		view.addEditListener(new EditListener());
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
			String menuName = "";
			int productPrice;
			try {
				menuName = m_view.getMenuItemName();
				productPrice = Integer.parseInt(m_view.getProductPrice());
				if (productPrice != 0) {
					MenuItem m = new BaseProduct(menuName, productPrice);
					m_view.addMenu(m);
					w_view.addMenu(m);
					m_model.addMenuItem(m);
					m_model.viewMenuItems();
				} else {
					List<MenuItem> list = new ArrayList<MenuItem>();
					list.add(m_view.getSelectedMenu());
					MenuItem menu = new CompositeProduct(menuName, list);
					m_view.addMenu(menu);
					w_view.addMenu(menu);
					m_model.addMenuItem(menu);
					m_model.viewMenuItems();
				}

			} catch (NumberFormatException nfex) {
				m_view.showError("Bad input");
			}
		}
	}

	class DeleteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				MenuItem menu = m_view.getSelectedMenu();
				m_model.deleteMenuItem(menu);
				m_view.removeMenu(menu);
				w_view.removeMenu(menu);
				m_model.viewMenuItems();
			} catch (NumberFormatException nfex) {
				m_view.showError("Bad input");
			}
		}
	}

	class EditListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String newName = "";
			try {
				newName = m_view.getNewMenuName();
				MenuItem menu = m_view.getSelectedMenu();
				m_model.editMenuItem(menu, newName);
				m_model.viewMenuItems();

			} catch (NumberFormatException nfex) {
				m_view.showError("Bad input");
			}
		}
	}

}
