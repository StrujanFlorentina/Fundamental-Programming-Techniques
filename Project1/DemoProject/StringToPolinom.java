package PT2020.demo.DemoProject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringToPolinom {
	public static Polinom toPolinom(String s) {
		Polinom pol = new Polinom();
		Pattern p = Pattern.compile("(-?\\b\\d+)[xX]\\^(-?\\d+\\b)");
		Matcher m = p.matcher(s);
		while (m.find()) {
			pol.adaugaMonom(new Monom(Double.parseDouble(m.group(1)), Integer.parseInt(m.group(2))));
		}
		return pol;
	}
}
