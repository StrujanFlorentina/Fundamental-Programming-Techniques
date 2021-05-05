package bll.validators;

import model.Product;

/**
 * Clasa ProductPriceValidator implementeaza interfata Validator si se foloseste
 * pentru a valdia pretul unui produs
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class ProductPriceValidator implements Validator<Product> {
	private static final float MIN_QUANTITY = 1;
	private static final float MAX_QUANTITY = Integer.MAX_VALUE;

	@Override
	public void validate(Product t) {
		if (t.getPrice() < MIN_QUANTITY || t.getPrice() > MAX_QUANTITY) {
			throw new IllegalArgumentException("The Product Quantity limit is not respected!");
		}

	}
}
