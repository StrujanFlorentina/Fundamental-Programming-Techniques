package bll.validators;

/**
 * <h1>Validator<T></h1> Interfata Validator defineste metoda validate()
 * folosind Java generic T ca tip al parametrului
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public interface Validator<T> {

	public void validate(T t);
}