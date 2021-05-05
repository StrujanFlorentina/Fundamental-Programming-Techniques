package bll.validators;

import model.OrderItem;

/**
 * Clasa OrderItemClientValidator implementeaza interfata Validator si se
 * foloseste pentru a valdia numele unui client dintr-un order item
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class OrderItemClientValidator implements Validator<OrderItem> {
	private static final int MIN_SIZE = 2;
	private static final int MAX_SIZE = 40;

	@Override
	public void validate(OrderItem t) {

		if (t.getClient().length() < MIN_SIZE || t.getClient().length() > MAX_SIZE) {
			throw new IllegalArgumentException("The Client Name limit is not respected!");
		}

	}
}
