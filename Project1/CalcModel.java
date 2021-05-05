package PT2020.demo.DemoProject;

public class CalcModel {
	static final String INITIAL_VALUE = "0";

	private String m_total;

	CalcModel() {
		reset();
	}

	public void reset() {
		m_total = INITIAL_VALUE;
	}

	public void adunare(String polinom1, String polinom2) {
		Polinom total = Operatii.adunare(StringToPolinom.toPolinom(polinom1), StringToPolinom.toPolinom(polinom2));
		m_total = total.toString();
	}

	public void scadere(String polinom1, String polinom2) {
		Polinom total = Operatii.scadere(StringToPolinom.toPolinom(polinom1), StringToPolinom.toPolinom(polinom2));
		m_total = total.toString();
	}

	public void inmultire(String polinom1, String polinom2) {
		Polinom total = Operatii.inmultire(StringToPolinom.toPolinom(polinom1), StringToPolinom.toPolinom(polinom2));
		m_total = total.toString();
	}

	public void derivare(String polinom) {
		Polinom total = Operatii.derivare(StringToPolinom.toPolinom(polinom));
		m_total = total.toString();
	}

	public void integrare(String polinom) {
		Polinom total = Operatii.integrare(StringToPolinom.toPolinom(polinom));
		m_total = total.toString();
	}

	public void setValue(String value) {
		m_total = value;
	}

	public String getValue() {
		return m_total;
	}
}
