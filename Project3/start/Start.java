package start;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.itextpdf.text.DocumentException;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.OrderItemBLL;
import bll.ProductBLL;
import model.Client;
import model.OrderItem;
import model.Product;
import presentation.ParseString;
import presentation.ReadingFromFile;

/**
 * In clasa Start combinam toate clasele, medodele si operatiile definite in
 * cadrul acestui proiect
 * 
 * @author FlorentinaStrujan
 *
 */
public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

	public static void main(String[] args) throws SQLException, FileNotFoundException, DocumentException {
		ReadingFromFile reading = new ReadingFromFile();
		ParseString parsing = new ParseString();
		ClientBLL c = new ClientBLL();
		ProductBLL p = new ProductBLL();
		OrderItemBLL o = new OrderItemBLL();
		OrderBLL or = new OrderBLL();
		reading.citeste(args[0]);
		ArrayList<String> lines = reading.getLines();
		for (String l : lines) {
			parsing.parse(l);
			ArrayList<String> comenzi = parsing.toCommands();
			switch (comenzi.get(0)) {
			case "Insert":
				switch (comenzi.get(1)) {
				case "product":
				case "Product":
					p.insertProduct(new Product(0, comenzi.get(2), Integer.parseInt(comenzi.get(3)),
							Float.parseFloat(comenzi.get(4))));
					break;
				case "client":
				case "Client":
					c.insertClient(new Client(0, comenzi.get(2), comenzi.get(3)));
					break;
				}
				break;
			case "Delete":
				switch (comenzi.get(1)) {
				case "client":
				case "Client":
					c.deleteClient(new Client(0, comenzi.get(2), comenzi.get(3)));
					break;
				case "product":
				case "Product":
					p.deleteProduct(comenzi.get(2));
					break;
				}
				break;
			case "Report":
				switch (comenzi.get(1)) {
				case "client":
				case "Client":
					c.reportClient();
					break;
				case "product":
				case "Product":
					p.reportProduct();
					break;
				case "order":
				case "Order":
					or.reportOrder();
					break;
				}
				break;
			case "Order":
				o.insertOrderItem(new OrderItem(0, comenzi.get(2), Integer.parseInt(comenzi.get(3)), comenzi.get(1)));
				break;
			}
			comenzi.clear();
		}
	}

}
