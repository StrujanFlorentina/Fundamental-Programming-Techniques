package bll.validators;

import model.OrderItem;

/**
 * Clasa OrderItemPNameValidator implementeaza interfata Validator si se
 * foloseste pentru a valdia numele unui produs dintr-un order item
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class OrderItemPNameValidator implements Validator<OrderItem> {
	private static final int MIN_SIZE = 2;
	private static final int MAX_SIZE = 20;

	@Override
	public void validate(OrderItem t) {

		if (t.getProductName().length() < MIN_SIZE || t.getProductName().length() > MAX_SIZE) {
			throw new IllegalArgumentException("The Product Name limit is not respected!");
		}
	}
}
