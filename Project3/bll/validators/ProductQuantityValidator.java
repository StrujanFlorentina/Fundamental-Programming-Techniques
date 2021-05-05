package bll.validators;

import model.Product;

/**
 * Clasa ProductQuantityValidator implementeaza interfata Validator si se
 * foloseste pentru a valdia cantitatea unui produs
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class ProductQuantityValidator implements Validator<Product> {
	private static final int MIN_QUANTITY = 1;
	private static final int MAX_QUANTITY = Integer.MAX_VALUE;

	@Override
	public void validate(Product t) {

		if (t.getQuantity() < MIN_QUANTITY || t.getQuantity() > MAX_QUANTITY) {
			throw new IllegalArgumentException("The Product Quantity limit is not respected!");
		}

	}
}
