package bll;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.itextpdf.text.DocumentException;

import bll.validators.ProductNameValidator;
import bll.validators.ProductPriceValidator;
import bll.validators.ProductQuantityValidator;
import bll.validators.Validator;
import dao.ProductDAO;
import model.Product;
import presentation.PDFProduct;

/**
 * <h1>ProductBLL</h1> Clasa ProductBLL implementeaza logica operatiilor pe baza
 * de date
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class ProductBLL {
	private List<Validator<Product>> validators = new ArrayList<Validator<Product>>();

	public ProductBLL() {
		validators.add(new ProductNameValidator());
		validators.add(new ProductQuantityValidator());
		validators.add(new ProductPriceValidator());

	}

	/**
	 * Metoda este folosita pentru a gasi un produs dupa numele acestuia,apelandu-se
	 * metoda din ProductDAO ce face conexiunea cu baza de date warehouse
	 * 
	 * @param name Este singurul parametru al metodei. Reprezinta denumirea
	 *             produsului cautat
	 * @return Product Se va returna produsul gasit in caz de reusita si null in caz
	 *         de esec
	 */
	public Product findProductByName(String name) {
		Product st = ProductDAO.findByName(name);
		if (st == null) {
			throw new NoSuchElementException("The Product with name " + name + " was not found!");
		}
		return st;
	}

	/**
	 * Metoda este folosita pentru a insera un produs in tabela
	 * respectiva,apelandu-se metoda din ProductDAO ce face conexiunea cu baza de
	 * date warehouse
	 * 
	 * @param p Este singurul parametru al metodei. Reprezinta produsul de inserat
	 * @return int Se va returna id-ul produsului inserat in caz de reusita si -1 in
	 *         caz de esec
	 */
	public int insertProduct(Product p) {
		for (Validator<Product> v : validators) {
			v.validate(p);
		}
		if (ProductDAO.findByName(p.getName()) == null) {
			return ProductDAO.insert(p);
		} else
			return updateAProduct(p.getQuantity(), p.getName());
	}

	/**
	 * Metoda este folosita pentru a sterge un produs din tabela
	 * respectiva,apelandu-se metoda din ProductDAO ce face conexiunea cu baza de
	 * date warehouse
	 * 
	 * @param name Este singurul parametru al metodei. Reprezinta numele produsului
	 *             de sters
	 * @return int Se va returna 0 in caz de esec si 1 in caz de reusita
	 */
	public int deleteProduct(String name) {
		int i = ProductDAO.delete(name);
		if (i == 0) {
			throw new NoSuchElementException("The Product with name " + name + " was not deleted!");
		}
		return i;
	}

	/**
	 * Metoda este folosita pentru a popula un vector de produse cu produsele din
	 * tabela respectiva,apelandu-se metoda din ProductDAO ce face conexiunea cu
	 * baza de date warehouse
	 * 
	 * @return ArrayList<Product> Se va returna vectorul de produse in caz de reusia
	 *         si null in caz de esec
	 */
	public ArrayList<Product> reportProduct() throws FileNotFoundException, DocumentException {
		ArrayList<Product> st = ProductDAO.report();
		PDFProduct p = new PDFProduct(st);
		p.generate();
		if (st == null) {
			throw new NoSuchElementException("The Product  was not found!");
		}
		return st;
	}

	/**
	 * Metoda este folosita pentru a modifica cantitatea unui produs din tabela
	 * respectiva pe baza numelui acestuia,apelandu-se metoda din ProductDAO ce face
	 * conexiunea cu baza de date warehouse
	 * 
	 * @param quant Este primul parametru al metodei. Reprezinta numarul cu care se
	 *              va micsora cantitatea prdusului
	 * @param name  Este al doilea parametru al metodei. Reprezinta numele
	 *              produsului ce va fi modificat
	 * @return int Se va returna 0 in caz de esec si 1 in caz de reusita
	 */
	public int updateProduct(int quant, String name) {
		int st = ProductDAO.updateMinus(quant, name);
		if (st == 0) {
			throw new NoSuchElementException("The Product was not found!");
		}
		return st;
	}

	/**
	 * Metoda este folosita pentru a modifica cantitatea unui produs din tabela
	 * respectiva pe baza numelui acestuia,apelandu-se metoda din ProductDAO ce face
	 * conexiunea cu baza de date warehouse
	 * 
	 * @param quant Este primul parametru al metodei. Reprezinta numarul cu care se
	 *              va mari cantitatea prdusului
	 * @param name  Este al doilea parametru al metodei. Reprezinta numele
	 *              produsului ce va fi modificat
	 * @return int Se va returna 0 in caz de esec si 1 in caz de reusita
	 */
	public int updateAProduct(int quant, String name) {
		int st = ProductDAO.updatePlus(quant, name);
		if (st == 0) {
			throw new NoSuchElementException("The Product was not found!");
		}
		return st;
	}

}
