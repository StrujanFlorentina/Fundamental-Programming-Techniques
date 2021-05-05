package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionDatabase;
import model.Product;

/**
 * <h1>ProductDAO</h1> Clasa ProductDAO realizeaza interactiunea directa cu
 * tabela Product din data de baze warehouse
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class ProductDAO {

	protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO Product (name,quantity,price)" + " VALUES (?,?,?)";
	private final static String findStatementString = "SELECT * FROM Product where name=?";
	private final static String reportStatementString = "SELECT * FROM Product";
	private final static String deleteStatementString = "DELETE FROM Product where name=?";
	private final static String updateStatementString = "UPDATE Product SET quantity=quantity-? where name=?";
	private final static String updateAStatementString = "UPDATE Product SET quantity=quantity+? where name=?";

	/**
	 * Metoda este folosita pentru a gasi un produs dupa numele acestuia, creand o
	 * conexiune cu baza de date, adaugand parametrii necesari query-ului definit si
	 * initializat in loc de '?', apoi excutand query-ul. Rezultatul executiei
	 * query-ului este stocat intr-un ResultSet, fiecare element al acestuia
	 * corespunzandu-i unui rand al tabelei.Valorile din coloane sunt ectrase,
	 * stiind denumirile coloanelor
	 * 
	 * @param ProductName Este singurul parametru al metodei. Reprezinta denumirea
	 *                    produsului cautat
	 * @return Product Se va returna produsul gasit in caz de reusita si null in caz
	 *         de esec
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static Product findByName(String ProductName) {
		Product toReturn = null;

		Connection dbConnection = ConnectionDatabase.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findStatementString);
			findStatement.setString(1, ProductName);
			rs = findStatement.executeQuery();
			rs.next();

			int id = rs.getInt("id");
			int quantity = rs.getInt("quantity");
			float price = rs.getFloat("price");
			toReturn = new Product(id, ProductName, quantity, price);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:findByName " + e.getMessage());
		} finally {
			ConnectionDatabase.close(rs);
			ConnectionDatabase.close(findStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return toReturn;
	}

	/**
	 * Metoda este folosita pentru a insera un produs in tabela respectiva, creand o
	 * conexiune cu baza de date, adaugand parametrii necesari query-ului definit si
	 * initializat in loc de '?', apoi excutand query-ul. Rezultatul executiei
	 * query-ului este stocat intr-un ResultSet, fiecare element al acestuia
	 * corespunzandu-i unui rand al tabelei.Valorile din coloane sunt ectrase,
	 * stiind denumirile coloanelor
	 * 
	 * @param Product Este singurul parametru al metodei. Reprezinta produsul de
	 *                inserat
	 * @return int Se va returna id-ul produsului inserat in caz de reusita si -1 in
	 *         caz de esec
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static int insert(Product Product) {
		Connection dbConnection = ConnectionDatabase.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, Product.getName());
			insertStatement.setInt(2, Product.getQuantity());
			insertStatement.setFloat(3, Product.getPrice());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:insert " + e.getMessage());
		} finally {
			ConnectionDatabase.close(insertStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * Metoda este folosita pentru a sterge un produs din tabela respectiva, creand
	 * o conexiune cu baza de date, adaugand parametrii necesari query-ului definit
	 * si initializat in loc de '?', apoi excutand query-ul
	 * 
	 * @param ProductName Este singurul parametru al metodei. Reprezinta numele
	 *                    produsului de sters
	 * @return int Se va returna 0 in caz de esec si 1 in caz de reusita
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static int delete(String ProductName) {
		int ok = 0;
		Connection dbConnection = ConnectionDatabase.getConnection();
		PreparedStatement deleteStatement = null;
		try {
			deleteStatement = dbConnection.prepareStatement(deleteStatementString);
			deleteStatement.setString(1, ProductName);
			deleteStatement.executeUpdate();
			ok = 1;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:delete " + e.getMessage());
		} finally {
			ConnectionDatabase.close(deleteStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return ok;
	}

	/**
	 * Metoda este folosita pentru a popula un vector de produse cu produsele din
	 * tabela respectiva, creand o conexiune cu baza de date, pe baza query-ului
	 * definit si initializat, apoi excutat. Rezultatul executiei query-ului este
	 * stocat intr-un ResultSet, fiecare element al acestuia corespunzandu-i unui
	 * rand al tabelei.Valorile din coloane sunt ectrase, stiind denumirile
	 * coloanelor
	 * 
	 * @return ArrayList<Product> Se va returna vectorul de produse in caz de reusia
	 *         si null in caz de esec
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static ArrayList<Product> report() {
		ArrayList<Product> toReturn = new ArrayList<Product>();
		Connection dbConnection = ConnectionDatabase.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(reportStatementString);
			rs = findStatement.executeQuery();
			while (rs.hashCode() != 0 && rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int quantity = rs.getInt("quantity");
				float price = rs.getFloat("price");
				toReturn.add(new Product(id, name, quantity, price));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:report " + e.getMessage());
		} finally {
			ConnectionDatabase.close(rs);
			ConnectionDatabase.close(findStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return toReturn;
	}

	/**
	 * Metoda este folosita pentru a modifica cantitatea unui produs din tabela
	 * respectiva pe baza numelui acestuia, creand o conexiune cu baza de date,
	 * adaugand parametrii necesari query-ului definit si initializat in loc de '?',
	 * apoi excutand query-ul
	 * 
	 * @param quant Este primul parametru al metodei. Reprezinta numarul cu care se
	 *              va micsora cantitatea prdusului
	 * @param name  Este al doilea parametru al metodei. Reprezinta numele
	 *              produsului ce va fi modificat
	 * @return int Se va returna 0 in caz de esec si 1 in caz de reusita
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static int updateMinus(int quant, String name) {
		int i = 0;
		Connection dbConnection = ConnectionDatabase.getConnection();
		PreparedStatement findStatement = null;
		try {
			findStatement = dbConnection.prepareStatement(updateStatementString);
			findStatement.setInt(1, quant);
			findStatement.setString(2, name);
			findStatement.executeUpdate();
			i = 1;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:updateMinus " + e.getMessage());
		} finally {
			ConnectionDatabase.close(findStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return i;
	}

	/**
	 * Metoda este folosita pentru a modifica cantitatea unui produs din tabela
	 * respectiva pe baza numelui acestuia, creand o conexiune cu baza de date,
	 * adaugand parametrii necesari query-ului definit si initializat in loc de '?',
	 * apoi excutand query-ul
	 * 
	 * @param quant Este primul parametru al metodei. Reprezinta numarul cu care se
	 *              va mari cantitatea prdusului
	 * @param name  Este al doilea parametru al metodei. Reprezinta numele
	 *              produsului ce va fi modificat
	 * @return int Se va returna 0 in caz de esec si 1 in caz de reusita
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static int updatePlus(int quant, String name) {
		int i = 0;
		Connection dbConnection = ConnectionDatabase.getConnection();
		PreparedStatement findStatement = null;
		try {
			findStatement = dbConnection.prepareStatement(updateAStatementString);
			findStatement.setInt(1, quant);
			findStatement.setString(2, name);
			findStatement.executeUpdate();
			i = 1;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:updateMinus " + e.getMessage());
		} finally {
			ConnectionDatabase.close(findStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return i;
	}

}
