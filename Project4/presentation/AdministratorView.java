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

public class AdministratorView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField m_MenuName = new JTextField(20);
	private JTextField m_ProductPrice = new JTextField("0", 20);
	private JTextField m_newName = new JTextField("null", 20);
	private JButton m_addBtn = new JButton("Add new menu ");
	private JButton m_deleteBtn = new JButton("Delete menu");
	private JButton m_editBtn = new JButton("Edit menu");
	private JPanel content = new JPanel();
	JComboBox<MenuItem> menus = new JComboBox<MenuItem>();

	AdministratorView(IRestaurantProcessing model) {
		JPanel a = new JPanel();

		a.setLayout(new FlowLayout());
		a.add(new JLabel("Menu item name"));
		a.add(m_MenuName);

		JPanel b = new JPanel();
		b.add(new JLabel("Base product price"));
		b.add(m_ProductPrice);

		JPanel c = new JPanel();
		c.setLayout(new FlowLayout());
		c.add(m_addBtn);

		JPanel f = new JPanel();
		f.setLayout(new FlowLayout());
		f.add(new JLabel("New name for menu"));
		f.add(m_newName);

		JPanel p = new JPanel();
		p.add(m_deleteBtn);
		p.add(m_editBtn);

		content.add(a);
		content.add(b);
		content.add(c);
		content.add(f);
		content.add(p);
		content.add(menus);

		this.setContentPane(content);
		this.pack();

		this.setTitle("Administrator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
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

	String getMenuItemName() {
		return m_MenuName.getText();
	}

	String getNewMenuName() {
		return m_newName.getText();
	}

	String getProductPrice() {
		return m_ProductPrice.getText();
	}

	void showError(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

	void addAddListener(ActionListener mal) {
		m_addBtn.addActionListener(mal);
	}

	void addEditListener(ActionListener mal) {
		m_editBtn.addActionListener(mal);
	}

	void addDeleteListener(ActionListener mal) {
		m_deleteBtn.addActionListener(mal);
	}

}
