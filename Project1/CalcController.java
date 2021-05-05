package PT2020.demo.DemoProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalcController {
	private CalcModel m_model;
	private CalcView m_view;

	CalcController(CalcModel model, CalcView view) {
		m_model = model;
		m_view = view;

		view.addAdditionListener(new AdditionListener());
		view.addSubstractionListener(new SubstractionListener());
		view.addMultiplyListener(new MultiplyListener());
		view.addDerivativeListener(new DerivativeListener());
		view.addIntegrationListener(new IntegrationListener());
		view.addClearListener(new ClearListener());
	}

	class AdditionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String userInput1 = "";
			String userInput2 = "";
			try {
				userInput1 = m_view.getUserInput1();
				userInput2 = m_view.getUserInput2();
				m_model.adunare(userInput1, userInput2);
				m_view.setTotal(m_model.getValue());

			} catch (NumberFormatException nfex) {
				m_view.showError("Bad input: '" + userInput1 + " , " + userInput2 + "'");
			}
		}
	}

	class SubstractionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String userInput1 = "";
			String userInput2 = "";
			try {
				userInput1 = m_view.getUserInput1();
				userInput2 = m_view.getUserInput2();
				m_model.scadere(userInput1, userInput2);
				m_view.setTotal(m_model.getValue());

			} catch (NumberFormatException nfex) {
				m_view.showError("Bad input: '" + userInput1 + " , " + userInput2 + "'");
			}
		}
	}

	class MultiplyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String userInput1 = "";
			String userInput2 = "";
			try {
				userInput1 = m_view.getUserInput1();
				userInput2 = m_view.getUserInput2();
				m_model.inmultire(userInput1, userInput2);
				m_view.setTotal(m_model.getValue());

			} catch (NumberFormatException nfex) {
				m_view.showError("Bad input: '" + userInput1 + " , " + userInput2 + "'");
			}
		}
	}

	class DerivativeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String userInput1 = "";
			try {
				userInput1 = m_view.getUserInput1();
				m_model.derivare(userInput1);
				m_view.setTotal(m_model.getValue());

			} catch (NumberFormatException nfex) {
				m_view.showError("Bad input: '" + userInput1 + "'");
			}
		}
	}

	class IntegrationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String userInput1 = "";
			try {
				userInput1 = m_view.getUserInput1();
				m_model.integrare(userInput1);
				m_view.setTotal(m_model.getValue());

			} catch (NumberFormatException nfex) {
				m_view.showError("Bad input: '" + userInput1 + "'");
			}
		}
	}

	class ClearListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			m_model.reset();
			m_view.reset();
		}
	}
}
