package bll.validators;

import model.OrderItem;

/**
 * Clasa OrderItemPValidator implementeaza interfata Validator si se foloseste
 * pentru a valdia cantitatea unui produs dintr-un order item
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class OrderItemPQuantityValidator implements Validator<OrderItem> {

	private static final int MIN_QUANTITY = 1;
	private static final int MAX_QUANTITY = Integer.MAX_VALUE;

	@Override
	public void validate(OrderItem t) {

		if (t.getProductQuantity() < MIN_QUANTITY || t.getProductQuantity() > MAX_QUANTITY) {
			throw new IllegalArgumentException("The Product Quantity limit is not respected!");
		}

	}

}
