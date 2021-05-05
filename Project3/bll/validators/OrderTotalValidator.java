package bll.validators;

import model.Orderc;

/**
 * Clasa OrderTotalValidator implementeaza interfata Validator si se foloseste
 * pentru a valdia suma de bani totala cheltuita de un client
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class OrderTotalValidator implements Validator<Orderc> {
	private static final float MIN_QUANTITY = 1;
	private static final float MAX_QUANTITY = Integer.MAX_VALUE;

	@Override
	public void validate(Orderc t) {
		if (t.getTotal() < MIN_QUANTITY || t.getTotal() > MAX_QUANTITY) {
			throw new IllegalArgumentException("The Product Quantity limit is not respected!");
		}

	}
}
