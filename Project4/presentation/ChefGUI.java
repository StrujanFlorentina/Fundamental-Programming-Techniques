package presentation;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.Observer;
import business.Order;

public class ChefGUI extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;

	private JTextField m_text = new JTextField();

	public ChefGUI() {
		JPanel content = new JPanel();
		content.setLayout(new FlowLayout());
		m_text.setFont(new Font("Imprint MT Shadow", Font.BOLD, 20));
		content.add(m_text);
		this.setContentPane(content);
		this.pack();
		this.setTitle("Chef");
		this.setSize(600, 600);
	}

	@Override
	public void update(Order o) {
		String message = "The chef is preparing the order with id: " + o.getOrderID();
		m_text.setText(message);
		this.setVisible(true);
	}

}
