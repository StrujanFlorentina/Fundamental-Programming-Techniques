package PT2020.demo.DemoProject;

public class Monom {
	private double coeficient;
	private int exponent;

	public Monom() {
		this.coeficient = 0;
		this.exponent = 0;
	}

	public Monom(double coeficient, int exponent) {
		this.coeficient = coeficient;
		this.exponent = exponent;
	}

	public double getCoeficient() {
		return coeficient;
	}

	public void setCoeficient(double coeficient) {
		this.coeficient = coeficient;
	}

	public int getExponent() {
		return exponent;
	}

	public void setExponent(int exponent) {
		this.exponent = exponent;
	}

	@Override
	public String toString() {
		if (coeficient == 0)
			return "";
		if (coeficient > 0 && exponent == 0)
			return "+" + coeficient;
		if (coeficient < 0 && exponent == 0)
			return "" + coeficient;
		if (coeficient == 1 && exponent == 1)
			return "+x";
		if (coeficient == -1 && exponent == 1)
			return "-x";
		if (coeficient == 1)
			return "+x^" + exponent;
		if (coeficient == -1)
			return "-x^" + exponent;
		if (coeficient > 0 && exponent == 1)
			return "+" + coeficient + "x";
		if (coeficient > 0 && exponent == 1)
			return coeficient + "x";
		if (coeficient > 0 && exponent > 0)
			return "+" + coeficient + "x^" + exponent;
		else
			return coeficient + "x^" + exponent;
	}

}
