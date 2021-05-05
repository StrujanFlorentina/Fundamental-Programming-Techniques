package PT2020.demo.DemoProject;

public class Operatii {
	static int maxim(int m, int n) {
		return (m > n) ? m : n;
	}

	public static Polinom adunare(Polinom p1, Polinom p2) {
		Polinom polFin = new Polinom();
		int max = maxim(p1.size(), p2.size());

		if (max == p1.size()) {
			for (Monom m1 : p1.getPolinom()) {
				for (Monom m2 : p2.getPolinom())
					if (m1.getExponent() == m2.getExponent())
						polFin.adaugaMonom(new Monom(m1.getCoeficient() + m2.getCoeficient(), m1.getExponent()));
				if (m1.getExponent() >= p2.size())
					polFin.adaugaMonom(m1);
			}
			return polFin;
		} else {
			for (Monom m2 : p2.getPolinom()) {
				for (Monom m1 : p1.getPolinom())
					if (m1.getExponent() == m2.getExponent())
						polFin.adaugaMonom(new Monom(m1.getCoeficient() + m2.getCoeficient(), m1.getExponent()));
				if (m2.getExponent() >= p1.size())
					polFin.adaugaMonom(m2);
			}
			return polFin;
		}
	}

	public static Polinom scadere(Polinom p1, Polinom p2) {
		Polinom polFin = new Polinom();
		int max = maxim(p1.size(), p2.size());

		if (max == p1.size()) {
			for (Monom m1 : p1.getPolinom()) {
				for (Monom m2 : p2.getPolinom())
					if (m1.getExponent() == m2.getExponent())
						polFin.adaugaMonom(new Monom(m1.getCoeficient() - m2.getCoeficient(), m1.getExponent()));
				if (m1.getExponent() >= p2.size())
					polFin.adaugaMonom(m1);
			}
			return polFin;
		} else {
			for (Monom m2 : p2.getPolinom()) {
				for (Monom m1 : p1.getPolinom())
					if (m1.getExponent() == m2.getExponent())
						polFin.adaugaMonom(new Monom(m1.getCoeficient() - m2.getCoeficient(), m1.getExponent()));
				if (m2.getExponent() >= p1.size()) {
					m2.setCoeficient(-1 * m2.getCoeficient());
					polFin.adaugaMonom(m2);
				}
			}
			return polFin;
		}
	}

	public static Polinom inmultire(Polinom p1, Polinom p2) {
		Polinom polFin = new Polinom();
		Monom mon = new Monom();
		for (Monom m1 : p1.getPolinom())
			for (Monom m2 : p2.getPolinom())
				polFin.adaugaMonom(
						new Monom(m1.getCoeficient() * m2.getCoeficient(), m1.getExponent() + m2.getExponent()));
		return polFin;
	}

	public static Polinom integrare(Polinom p) {
		Polinom polFin = new Polinom();
		for (Monom m : p.getPolinom())
			polFin.adaugaMonom(new Monom(m.getCoeficient() / (m.getExponent() + 1), m.getExponent() + 1));
		return polFin;
	}

	public static Polinom derivare(Polinom p) {
		Polinom polFin = new Polinom();
		for (Monom m : p.getPolinom())
			polFin.adaugaMonom(new Monom(m.getCoeficient() * m.getExponent(), m.getExponent() - 1));
		return polFin;
	}

}
