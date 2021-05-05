package bll;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.itextpdf.text.DocumentException;

import bll.validators.ClientAdressValidator;
import bll.validators.ClientNameValidate;
import bll.validators.Validator;
import dao.ClientDAO;
import dao.OrderDAO;
import dao.OrderItemDAO;
import model.Client;
import presentation.PDFClient;

/**
 * <h1>ClientBLL/h1> Clasa ClientBLL implementeaza logica operatiilor pe baza de
 * date
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class ClientBLL {
	private List<Validator<Client>> validators = new ArrayList<Validator<Client>>();

	public ClientBLL() {
		validators.add(new ClientNameValidate());
		validators.add(new ClientAdressValidator());
	}

	/**
	 * Metoda este folosita pentru a gasi un client dupa numele acestuia,apelandu-se
	 * metoda din ClientDAO ce face conexiunea cu baza de date warehouse
	 * 
	 * @param name Este singurul parametru al metodei. Reprezinta denumirea
	 *             clientului cautat,apelandu-se metoda din ClientDAO ce face
	 *             conexiunea cu baza de date warehouse
	 * @return Client Se va returna clientul gasit in caz de reusita si null in caz
	 *         de esec
	 */
	public Client findClientByName(String name) {
		Client st = ClientDAO.findByName(name);
		if (st == null) {
			throw new NoSuchElementException("The Client with name " + name + " was not found!");
		}
		return st;
	}

	/**
	 * Metoda este folosita pentru a insera un client in tabela respectiva,
	 * apelandu-se metoda din ClientDAO ce face conexiunea cu baza de date warehouse
	 * 
	 * @param c Este singurul parametru al metodei. Reprezinta clientul de inserat
	 * @return int Se va returna id-ul clientului inserat in caz de reusita si -1 in
	 *         caz de esec
	 */

	public int insertClient(Client c) {
		for (Validator<Client> v : validators) {
			v.validate(c);
		}
		return ClientDAO.insert(c);
	}

	/**
	 * Metoda este folosita pentru a sterge un client din tabela
	 * respectiva,apelandu-se metoda din ClientDAO ce face conexiunea cu baza de
	 * date warehouse
	 * 
	 * @param c Este singurul parametru al metodei. Reprezinta clientul de sters
	 * @return int Se va returna 0 in caz de esec si 1 in caz de reusita
	 */

	public int deleteClient(Client c) {
		if (OrderItemDAO.findByClient(c.getName()) != null)
			OrderItemDAO.delete(c.getName());
		if (OrderDAO.findByName(c.getName()) != null)
			OrderDAO.delete(c.getName());
		int st = ClientDAO.delete(c);
		if (st == 0) {
			throw new NoSuchElementException("The Client " + c.toString() + " was not deleted!");
		}
		return st;
	}

	/**
	 * Metoda este folosita pentru a popula un vector de clienti cu clientii din
	 * tabela respectiva, apelandu-se metoda din ClientDAO ce face conexiunea cu
	 * baza de date warehouse
	 * 
	 * @return ArrayList<Client> Se va returna vectorul de clienti in caz de reusia
	 *         si null in caz de esec
	 */
	public ArrayList<Client> reportClient() throws FileNotFoundException, DocumentException {
		ArrayList<Client> st = ClientDAO.report();
		if (st == null) {
			throw new NoSuchElementException("The Client was not found!");
		} else {
			PDFClient p = new PDFClient(st);
			p.generate();
		}
		return st;
	}
}
