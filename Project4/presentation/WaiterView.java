package presentation;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.menuitem.MenuItem;
import business.restaurant.IRestaurantProcessing;

public class WaiterView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField m_OrderID = new JTextField(35);

	private JButton m_addBtn = new JButton("Add new order");
	private JButton m_computeBtn = new JButton("Compute Bill");
	JComboBox<MenuItem> menus = new JComboBox<MenuItem>();

	WaiterView(IRestaurantProcessing model) {
		JPanel content = new JPanel();
		JPanel a = new JPanel();
		a.setLayout(new FlowLayout());
		a.add(new JLabel("Order ID"));
		a.add(m_OrderID);

		JPanel p = new JPanel();
		p.add(m_addBtn);
		p.add(menus);

		JPanel b = new JPanel();
		b.add(m_computeBtn);

		content.add(a);
		content.add(p);
		content.add(b);
		this.setContentPane(content);
		this.pack();

		this.setTitle("Waiter");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 600);
	}

	void addMenu(MenuItem m) {
		menus.addItem(m);
	}

	void removeMenu(MenuItem m) {
		menus.removeItem(m);
	}

	MenuItem getSelectedMenu() {
		return (MenuItem) menus.getSelectedItem();
	}

	String getOrderID() {
		return m_OrderID.getText();
	}

	void showError(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

	void addAddListener(ActionListener mal) {
		m_addBtn.addActionListener(mal);
	}

	void addComputeBillListener(ActionListener mal) {
		m_computeBtn.addActionListener(mal);
	}

}
