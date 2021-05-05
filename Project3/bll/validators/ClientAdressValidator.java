package bll.validators;

import model.Client;

/**
 * Clasa ClientAddressValidator implementeaza interfata Validator si se
 * foloseste pentru a valdia adresa unui client
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class ClientAdressValidator implements Validator<Client> {
	private static final int MIN_SIZE = 1;
	private static final int MAX_SIZE = 50;

	@Override
	public void validate(Client t) {

		if (t.getAddress().length() < MIN_SIZE || t.getName().length() > MAX_SIZE) {
			throw new IllegalArgumentException("The Client Name limit is not respected!");
		}

	}

}
