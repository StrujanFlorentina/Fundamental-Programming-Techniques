package PT2020.demo.DemoProject;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class CalcView extends JFrame {
	private JTextField m_userInputTf1 = new JTextField(20);
	private JTextField m_userInputTf2 = new JTextField(20);
	private JTextField m_totalTf = new JTextField(40);
	private JButton m_additionBtn = new JButton("Aduna");
	private JButton m_substractionBtn = new JButton("Scade");
	private JButton m_multiplyBtn = new JButton("Inmulteste");
	private JButton m_derivativeBtn = new JButton("Deriveaza");
	private JButton m_integrateBtn = new JButton("Integreaza");
	private JButton m_clearBtn = new JButton("Clear");

	private CalcModel m_model;

	CalcView(CalcModel model) {
		m_model = model;
		m_model.setValue(CalcModel.INITIAL_VALUE);
		m_totalTf.setText(m_model.getValue());
		m_totalTf.setEditable(false);
		JPanel content = new JPanel();
		JPanel pp = new JPanel();
		pp.setLayout(new FlowLayout());
		pp.add(new JLabel("Polinom1"));
		pp.add(m_userInputTf1);
		pp.add(new JLabel("Polinom2"));
		pp.add(m_userInputTf2);
		JPanel p = new JPanel();
		p.add(m_additionBtn);
		p.add(m_substractionBtn);
		p.add(m_multiplyBtn);
		p.add(m_derivativeBtn);
		p.add(m_integrateBtn);
		JPanel p1 = new JPanel();
		p1.add(new JLabel("Rezultat"));
		p1.add(m_totalTf);
		JPanel j = new JPanel();
		j.add(m_clearBtn);

		content.add(pp);
		content.add(p);
		content.add(p1);
		content.add(j);
		content.setBackground(Color.black);
		pp.setBackground(Color.gray);
		p.setBackground(Color.gray);
		p1.setBackground(Color.gray);
		j.setBackground(Color.gray);
		m_additionBtn.setBackground(Color.white);
		m_substractionBtn.setBackground(Color.white);
		m_multiplyBtn.setBackground(Color.white);
		m_derivativeBtn.setBackground(Color.white);
		m_integrateBtn.setBackground(Color.white);
		m_clearBtn.setBackground(Color.white);

		this.setContentPane(content);
		this.pack();

		this.setTitle("Calculator polinomial");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 200);
	}

	void reset() {
		m_totalTf.setText(CalcModel.INITIAL_VALUE);
	}

	String getUserInput1() {
		return m_userInputTf1.getText();
	}

	String getUserInput2() {
		return m_userInputTf2.getText();
	}

	void setTotal(String newTotal) {
		m_totalTf.setText(newTotal);
	}

	void showError(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

	void addAdditionListener(ActionListener mal) {
		m_additionBtn.addActionListener(mal);
	}

	void addSubstractionListener(ActionListener mal) {
		m_substractionBtn.addActionListener(mal);
	}

	void addMultiplyListener(ActionListener mal) {
		m_multiplyBtn.addActionListener(mal);
	}

	void addDerivativeListener(ActionListener mal) {
		m_derivativeBtn.addActionListener(mal);
	}

	void addIntegrationListener(ActionListener mal) {
		m_integrateBtn.addActionListener(mal);
	}

	void addClearListener(ActionListener cal) {
		m_clearBtn.addActionListener(cal);
	}
}
