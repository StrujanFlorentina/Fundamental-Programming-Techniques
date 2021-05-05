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
import model.OrderItem;

/**
 * <h1>OrderItemDAO</h1> Clasa OrderItemDAO realizeaza interactiunea directa cu
 * tabela OrderItem din data de baze warehouse
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class OrderItemDAO {

	protected static final Logger LOGGER = Logger.getLogger(OrderItemDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO OrderItem (productName,productQuantity,client)"
			+ " VALUES (?,?,?)";
	private final static String findStatementString = "SELECT * FROM OrderItem where client=? and productname=?";
	private final static String finddStatementString = "SELECT * FROM OrderItem where client=?";
	private final static String reportStatementString = "SELECT * FROM OrderItem";
	private final static String updateAStatementString = "UPDATE OrderItem SET productQuantity=productQuantity+? where productName=? and client=?";
	private final static String deleteStatementString = "DELETE FROM OrderItem where client=?";

	/**
	 * Metoda este folosita pentru a gasi un vector de order item -uri dupa numele
	 * clientului ce a cumparat un produs, creand o conexiune cu baza de date,
	 * adaugand parametrii necesari query-ului definit si initializat in loc de '?',
	 * apoi excutand query-ul. Rezultatul executiei query-ului este stocat intr-un
	 * ResultSet, fiecare element al acestuia corespunzandu-i unui rand al
	 * tabelei.Valorile din coloane sunt ectrase, stiind denumirile coloanelor
	 * 
	 * @param clientName Este singurul parametru al metodei. Reprezinta denumirea
	 *                   cumparatorului
	 * @return ArrayList<OrderItem> Se va returna un vector de order item-uri in caz
	 *         de reusita
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static ArrayList<OrderItem> findByClient(String clientName) {
		ArrayList<OrderItem> toReturn = new ArrayList<OrderItem>();

		Connection dbConnection = ConnectionDatabase.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(finddStatementString);
			findStatement.setString(1, clientName);
			rs = findStatement.executeQuery();
			while (rs.hashCode() != 0 && rs.next()) {
				int id = rs.getInt("id");
				String productName = rs.getString("productName");
				int productQuantity = rs.getInt("productQuantity");
				toReturn.add(new OrderItem(id, productName, productQuantity, clientName));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderItemDAO:findByClientName " + e.getMessage());
		} finally {
			ConnectionDatabase.close(rs);
			ConnectionDatabase.close(findStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return toReturn;
	}

	/**
	 * Metoda este folosita pentru a gasi un order item dupa numele clientului ce a
	 * cumparat un produs si numele produsului, creand o conexiune cu baza de date,
	 * adaugand parametrii necesari query-ului definit si initializat in loc de '?',
	 * apoi excutand query-ul. Rezultatul executiei query-ului este stocat intr-un
	 * ResultSet, fiecare element al acestuia corespunzandu-i unui rand al
	 * tabelei.Valorile din coloane sunt ectrase, stiind denumirile coloanelor
	 * 
	 * @param clientName Este primul parametru al metodei. Reprezinta denumirea
	 *                   cumparatorului
	 * @param prod       Este al doilea parametru al metodei. Reprezinta denumirea
	 *                   produsului
	 * @return OrderItem Se va returna order item-ul corespunzator in caz de reusita
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static OrderItem findByName(String clientName, String prod) {
		OrderItem toReturn = null;

		Connection dbConnection = ConnectionDatabase.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findStatementString);
			findStatement.setString(1, clientName);
			findStatement.setString(2, prod);
			rs = findStatement.executeQuery();
			rs.next();

			int id = rs.getInt("id");
			int productQuantity = rs.getInt("productQuantity");
			toReturn = new OrderItem(id, prod, productQuantity, clientName);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderItemDAO:findByClientName " + e.getMessage());
		} finally {
			ConnectionDatabase.close(rs);
			ConnectionDatabase.close(findStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return toReturn;
	}

	/**
	 * Metoda este folosita pentru a insera un order item in tabela respectiva,
	 * creand o conexiune cu baza de date, adaugand parametrii necesari query-ului
	 * definit si initializat in loc de '?', apoi excutand query-ul. Rezultatul
	 * executiei query-ului este stocat intr-un ResultSet, fiecare element al
	 * acestuia corespunzandu-i unui rand al tabelei.Valorile din coloane sunt
	 * ectrase, stiind denumirile coloanelor
	 * 
	 * @param OrderItem Este singurul parametru al metodei. Reprezinta order item-ul
	 *                  de inserat
	 * @return int Se va returna id-ul order item-ului inserat in caz de reusita si
	 *         -1 in caz de esec
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static int insert(OrderItem OrderItem) {
		Connection dbConnection = ConnectionDatabase.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, OrderItem.getProductName());
			insertStatement.setInt(2, OrderItem.getProductQuantity());
			insertStatement.setString(3, OrderItem.getClient());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderItemDAO:insert " + e.getMessage());
		} finally {
			ConnectionDatabase.close(insertStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * Metoda este folosita pentru a popula un vector cu order item-urile din tabela
	 * respectiva, creand o conexiune cu baza de date, pe baza query-ului definit si
	 * initializat, apoi excutat. Rezultatul executiei query-ului este stocat
	 * intr-un ResultSet, fiecare element al acestuia corespunzandu-i unui rand al
	 * tabelei.Valorile din coloane sunt ectrase, stiind denumirile coloanelor
	 * 
	 * @return ArrayList<OrderItem> Se va returna vectorul de order item-uri in caz
	 *         de reusia si null in caz de esec
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static ArrayList<OrderItem> report() {
		ArrayList<OrderItem> toReturn = new ArrayList<OrderItem>();
		Connection dbConnection = ConnectionDatabase.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(reportStatementString);
			rs = findStatement.executeQuery();
			while (rs.hashCode() != 0 && rs.next()) {
				int id = rs.getInt("id");
				String productName = rs.getString("productName");
				int productQuantity = rs.getInt("productQuantity");
				String client = rs.getString("client");
				toReturn.add(new OrderItem(id, productName, productQuantity, client));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderItemDAO:report " + e.getMessage());
		} finally {
			ConnectionDatabase.close(rs);
			ConnectionDatabase.close(findStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return toReturn;
	}

	/**
	 * Metoda este folosita pentru a modifica cantitatea unui produs coresunzator
	 * unui oredr item din tabela orderitem pe baza numelui acestuia, creand o
	 * conexiune cu baza de date, adaugand parametrii necesari query-ului definit si
	 * initializat in loc de '?', apoi excutand query-ul
	 * 
	 * @param pr Este singurul parametru al metodei. Reprezinta order item-ul pe
	 *           baza caruia se va micsora cantitatea prdusului
	 * @return int Se va returna 0 in caz de esec si 1 in caz de reusita
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */

	public static int updateAdauga(OrderItem pr) {
		int i = 0;
		Connection dbConnection = ConnectionDatabase.getConnection();
		PreparedStatement findStatement = null;
		try {
			findStatement = dbConnection.prepareStatement(updateAStatementString);
			findStatement.setInt(1, pr.getProductQuantity());
			findStatement.setString(2, pr.getProductName());
			findStatement.setString(3, pr.getClient());
			findStatement.executeUpdate();
			i = 1;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderItemDAO:updateMinusProduct " + e.getMessage());
		} finally {
			ConnectionDatabase.close(findStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return i;
	}

	/**
	 * Metoda este folosita pentru a sterge un order item din tabela respectiva,
	 * creand o conexiune cu baza de date, adaugand parametrii necesari query-ului
	 * definit si initializat in loc de '?', apoi excutand query-ul
	 * 
	 * @param ClName Este singurul parametru al metodei. Reprezinta clientul al
	 *               carui nume determina stergerea order item-ului
	 * @return int Se va returna 0 in caz de esec si 1 in caz de reusita
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static int delete(String ClName) {
		int ok = 0;
		Connection dbConnection = ConnectionDatabase.getConnection();
		PreparedStatement deleteStatement = null;
		try {
			deleteStatement = dbConnection.prepareStatement(deleteStatementString);
			deleteStatement.setString(1, ClName);
			deleteStatement.executeUpdate();
			ok = 1;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderItemDAO:delete " + e.getMessage());
		} finally {
			ConnectionDatabase.close(deleteStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return ok;
	}
}
