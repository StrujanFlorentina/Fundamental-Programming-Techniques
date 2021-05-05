package bll.validators;

import model.Orderc;

/**
 * Clasa OrderCNameValidator implementeaza interfata Validator si se foloseste
 * pentru a valdia numele unui client ce realizeaza o achizitie
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class OrderCNameValidator implements Validator<Orderc> {
	private static final int MIN_SIZE = 5;
	private static final int MAX_SIZE = 40;

	@Override
	public void validate(Orderc t) {

		if (t.getClientname().length() < MIN_SIZE || t.getClientname().length() > MAX_SIZE) {
			throw new IllegalArgumentException("The Client Name limit is not respected!");
		}

	}
}
