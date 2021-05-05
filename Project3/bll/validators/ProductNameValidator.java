package bll.validators;

import model.Product;

/**
 * Clasa ProductQuantityValidator implementeaza interfata Validator si se
 * foloseste pentru a valdia numele unui produs
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class ProductNameValidator implements Validator<Product> {
	private static final int MIN_SIZE = 2;
	private static final int MAX_SIZE = 20;

	@Override
	public void validate(Product t) {

		if (t.getName().length() < MIN_SIZE || t.getName().length() > MAX_SIZE) {
			throw new IllegalArgumentException("The Product Name limit is not respected!");
		}

	}

}
