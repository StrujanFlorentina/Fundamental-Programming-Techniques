package PT2020.demo.DemoProject;

import java.util.ArrayList;
import java.util.List;

public class Polinom {
	private List<Monom> polinom;

	public Polinom() {
		this.polinom = new ArrayList<Monom>();
	}

	public void adaugaMonom(Monom m) {
		polinom.add(m);
	}

	public List<Monom> getPolinom() {
		return polinom;
	}

	@Override
	public String toString() {
		String p = "";
		for (Monom i : polinom)
			p = p + i;
		return p;
	}

	public int size() {
		return polinom.size();
	}

}
