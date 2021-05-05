package bll;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.itextpdf.text.DocumentException;

import bll.validators.OrderCNameValidator;
import bll.validators.OrderTotalValidator;
import bll.validators.Validator;
import dao.OrderDAO;
import dao.OrderItemDAO;
import model.OrderItem;
import model.Orderc;
import presentation.PDFOrder;

/**
 * <h1>OrderBLL</h1> Clasa OrderBLL implementeaza logica operatiilor pe baza de
 * date
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class OrderBLL {
	private List<Validator<Orderc>> validators = new ArrayList<Validator<Orderc>>();

	public OrderBLL() {
		validators.add(new OrderCNameValidator());
		validators.add(new OrderTotalValidator());
	}

	/**
	 * Metoda este folosita pentru a gasi un order dupa numele clientului ce
	 * realizeaza comanda,apelandu-se metoda din OrderDAO ce face conexiunea cu baza
	 * de date warehouse
	 * 
	 * @param name Este singurul parametru al metodei. Reprezinta denumirea
	 *             clientului ce realizeaza comanda
	 * @return Orderc Se va returna orderul corespunzator gasit in caz de reusita
	 */
	public Orderc findOrderByName(String name) {
		Orderc st = OrderDAO.findByName(name);
		if (st == null) {
			throw new NoSuchElementException("The Order with name " + name + " was not found!");
		}
		return st;
	}

	/**
	 * Metoda este folosita pentru a insera un order in tabela
	 * respectiva,apelandu-se metoda din OrderDAO ce face conexiunea cu baza de date
	 * warehouse
	 * 
	 * @param c Este singurul parametru al metodei. Reprezinta order-ul de inserat
	 * @return int Se va returna id-ul order-ului inserat in caz de reusita si -1 in
	 *         caz de esec
	 */
	public int insertOrder(Orderc c) {
		for (Validator<Orderc> v : validators) {
			v.validate(c);
		}
		return OrderDAO.insert(c);
	}

	/**
	 * Metoda este folosita pentru a popula un vector cu order-urile din tabela
	 * respectiva,apelandu-se metoda din OrderDAO ce face conexiunea cu baza de date
	 * warehouse
	 * 
	 * @return ArrayList<Orderc> Se va returna vectorul de order-uri in caz de
	 *         reusia si null in caz de esec
	 */
	public ArrayList<Orderc> reportOrder() throws FileNotFoundException, DocumentException {
		ArrayList<OrderItem> or = OrderItemDAO.report();
		ArrayList<Orderc> st = OrderDAO.report();
		PDFOrder p = new PDFOrder(st, or);
		p.generate();
		if (st == null) {
			throw new NoSuchElementException("The Order was not found!");
		}
		return st;
	}
}
