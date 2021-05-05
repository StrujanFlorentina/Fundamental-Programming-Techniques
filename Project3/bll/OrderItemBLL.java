package bll;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.itextpdf.text.DocumentException;

import bll.validators.OrderItemClientValidator;
import bll.validators.OrderItemPNameValidator;
import bll.validators.OrderItemPQuantityValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import dao.OrderDAO;
import dao.OrderItemDAO;
import dao.ProductDAO;
import model.Client;
import model.OrderItem;
import model.Orderc;
import model.Product;
import presentation.Bill;
import presentation.BillError;

/**
 * <h1>OrderItemDAO</h1> Clasa OrderItemBLL implementeaza logica operatiilor pe
 * baza de date
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class OrderItemBLL {

	private List<Validator<OrderItem>> validators = new ArrayList<Validator<OrderItem>>();

	public OrderItemBLL() {
		validators.add(new OrderItemPNameValidator());
		validators.add(new OrderItemPQuantityValidator());
		validators.add(new OrderItemClientValidator());
	}

	/**
	 * Metoda este folosita pentru a gasi un order item dupa numele clientului ce a
	 * cumparat un produs si numele produsului,apelandu-se metoda din OrderItemDAO
	 * ce face conexiunea cu baza de date warehouse
	 * 
	 * @param name Este primul parametru al metodei. Reprezinta denumirea
	 *             cumparatorului
	 * @param prod Este al doilea parametru al metodei. Reprezinta denumirea
	 *             produsului
	 * @return OrderItem Se va returna order item-ul corespunzator in caz de reusita
	 */
	public OrderItem findOrderItemByName(String name, String prod) {
		OrderItem st = OrderItemDAO.findByName(name, prod);
		if (st == null) {
			throw new NoSuchElementException("The OrderItem with client name " + name + " was not found!");
		}
		return st;
	}

	/**
	 * Metoda este folosita pentru a insera un order item in tabela
	 * respectiva,apelandu-se metode din OrderItemDAO, OrderDAO, ClientDAO si
	 * ProductDAO ce fac conexiunea cu baza de date warehouse. Aceasta metoda este
	 * cea mai ampla din proiect din cauza faptului ca in momentul inserarii unui
	 * order item exista mai multe cazuri de inserari si updatatari la nivelul
	 * celorlalte tabele din baza de date astfel: in momentul inserarii unui order
	 * item e necesar ca produsul sa existe in tabela Product, iar catitatea
	 * acestuia sa fie mai mare sau egala cu cantitatea produsului de inserat in
	 * order item si este necesar ca si numele clientului de inserat sa existe in
	 * tabela Client. In caz de succes, se updateaza cantitatea produsului
	 * corespunzator din tabela Product. Daca numele clientului exista in tabela
	 * Orderc, se updateaza totalul corespunzator.Altfel, se va adauga clientul cu
	 * pretul aferent produsului cumparat.Mai avem si situatia cand numele
	 * produsului si numele clientului de inserat exista in tabela OrderItem. In
	 * acest caz, se va updata cantitatea aferenta.
	 * 
	 * @param p Este singurul parametru al metodei. Reprezinta order item-ul de
	 *          inserat
	 * @return int Se va returna id-ul order item-ului inserat in caz de reusita si
	 *         -1 in caz de esec
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */

	public int insertOrderItem(OrderItem p) throws FileNotFoundException, DocumentException {
		int result;
		Product prod = ProductDAO.findByName(p.getProductName());
		Client cli = ClientDAO.findByName(p.getClient());
		Orderc ord = OrderDAO.findByName(p.getClient());
		float newPrice = prod.getPrice() * p.getProductQuantity();
		for (Validator<OrderItem> v : validators) {
			v.validate(p);
		}
		if (OrderItemDAO.findByName(p.getClient(), p.getProductName()) == null && prod != null
				&& prod.getQuantity() >= p.getProductQuantity() && cli != null) {
			if (ord != null)
				OrderDAO.update(newPrice, p.getClient());
			else
				OrderDAO.insert(new Orderc(0, p.getClient(), newPrice));
			OrderDAO.update(p.getProductQuantity(), p.getProductName());
			result = OrderItemDAO.insert(p);
			ProductDAO.updateMinus(p.getProductQuantity(), p.getProductName());
			ArrayList<OrderItem> arr = OrderItemDAO.findByClient(p.getClient());
			Orderc orde = OrderDAO.findByName(p.getClient());
			Bill bill = new Bill(orde, arr);
			bill.generate();
			return result;
		} else if (prod != null && prod.getQuantity() >= p.getProductQuantity() && cli != null) {
			if (ord != null)
				OrderDAO.update(newPrice, p.getClient());
			else
				OrderDAO.insert(new Orderc(0, p.getClient(), newPrice));
			OrderDAO.update(p.getProductQuantity(), p.getProductName());
			result = OrderItemDAO.updateAdauga(p);
			ProductDAO.updateMinus(p.getProductQuantity(), p.getProductName());
			ArrayList<OrderItem> arr = OrderItemDAO.findByClient(p.getClient());
			Orderc orde = OrderDAO.findByName(p.getClient());
			Bill bill = new Bill(orde, arr);
			bill.generate();
			return result;
		} else {
			BillError bi = new BillError(p);
			bi.generate();
			return -1;
		}
	}

	/**
	 * Metoda este folosita pentru a popula un vector cu order item-urile din tabela
	 * respectiva,apelandu-se metoda din OrderItemDAO ce face conexiunea cu baza de
	 * date warehouse
	 * 
	 * @return ArrayList<OrderItem> Se va returna vectorul de order item-uri in caz
	 *         de reusia si null in caz de esec
	 */
	public ArrayList<OrderItem> reportOrderItem() {
		ArrayList<OrderItem> st = OrderItemDAO.report();
		if (st == null) {
			throw new NoSuchElementException("The OrderItem  was not found!");
		}
		return st;
	}

	/**
	 * Metoda este folosita pentru a modifica cantitatea unui produs coresunzator
	 * unui oredr item din tabela orderitem pe baza numelui acestuia,apelandu-se
	 * metoda din OrderItemDAO ce face conexiunea cu baza de date warehouse
	 * 
	 * @param p Este singurul parametru al metodei. Reprezinta order item-ul pe baza
	 *          caruia se va micsora cantitatea prdusului
	 * @return int Se va returna 0 in caz de esec si 1 in caz de reusita
	 */
	public int updateAOrderItem(OrderItem p) {
		int st = OrderItemDAO.updateAdauga(p);
		if (st == 0) {
			throw new NoSuchElementException("The OrderItem was not found!");
		}
		return st;
	}

}
