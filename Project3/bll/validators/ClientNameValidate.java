package bll.validators;

import model.Client;

/**
 * Clasa ClientNameValidator implementeaza interfata Validator si se foloseste
 * pentru a valdia numele unui client
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class ClientNameValidate implements Validator<Client> {
	private static final int MIN_SIZE = 5;
	private static final int MAX_SIZE = 40;

	@Override
	public void validate(Client t) {

		if (t.getName().length() < MIN_SIZE || t.getName().length() > MAX_SIZE) {
			throw new IllegalArgumentException("The Client Name limit is not respected!");
		}

	}

}
