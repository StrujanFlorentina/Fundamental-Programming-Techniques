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
import model.Orderc;

/**
 * <h1>OrderDAO</h1> Clasa OrderDAO realizeaza interactiunea directa cu tabela
 * Order din data de baze warehouse
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class OrderDAO {
	protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO Orderc (clientname,total)" + " VALUES (?,?)";
	private final static String findStatementString = "SELECT * FROM Orderc where clientname=?";
	private final static String reportStatementString = "SELECT * FROM Orderc";
	private final static String updateStatementString = "UPDATE Orderc SET total=total+? where clientname=?";
	private final static String deleteStatementString = "DELETE FROM Orderc where clientname=?";

	/**
	 * Metoda este folosita pentru a gasi un order dupa numele clientului ce
	 * realizeaza comanda, creand o conexiune cu baza de date, adaugand parametrii
	 * necesari query-ului definit si initializat in loc de '?', apoi excutand
	 * query-ul. Rezultatul executiei query-ului este stocat intr-un ResultSet,
	 * fiecare element al acestuia corespunzandu-i unui rand al tabelei.Valorile din
	 * coloane sunt ectrase, stiind denumirile coloanelor
	 * 
	 * @param clienttName Este singurul parametru al metodei. Reprezinta denumirea
	 *                    clientului ce realizeaza comanda
	 * @return Orderc Se va returna orderul corespunzator gasit in caz de reusita
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static Orderc findByName(String clientName) {
		Orderc toReturn = null;
		Connection dbConnection = ConnectionDatabase.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findStatementString);
			findStatement.setString(1, clientName);
			rs = findStatement.executeQuery();
			rs.next();

			int id = rs.getInt("id");
			float total = rs.getFloat("total");
			toReturn = new Orderc(id, clientName, total);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderDAO:findByClientName " + e.getMessage());
		} finally {
			ConnectionDatabase.close(rs);
			ConnectionDatabase.close(findStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return toReturn;
	}

	/**
	 * Metoda este folosita pentru a insera un order in tabela respectiva, creand o
	 * conexiune cu baza de date, adaugand parametrii necesari query-ului definit si
	 * initializat in loc de '?', apoi excutand query-ul. Rezultatul executiei
	 * query-ului este stocat intr-un ResultSet, fiecare element al acestuia
	 * corespunzandu-i unui rand al tabelei.Valorile din coloane sunt ectrase,
	 * stiind denumirile coloanelor
	 * 
	 * @param o Este singurul parametru al metodei. Reprezinta order-ul de inserat
	 * @return int Se va returna id-ul order-ului inserat in caz de reusita si -1 in
	 *         caz de esec
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static int insert(Orderc o) {
		Connection dbConnection = ConnectionDatabase.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, o.getClientname());
			insertStatement.setFloat(2, o.getTotal());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderDAO:insert " + e.getMessage());
		} finally {
			ConnectionDatabase.close(insertStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * Metoda este folosita pentru a popula un vector cu order-urile din tabela
	 * respectiva, creand o conexiune cu baza de date, pe baza query-ului definit si
	 * initializat, apoi excutat. Rezultatul executiei query-ului este stocat
	 * intr-un ResultSet, fiecare element al acestuia corespunzandu-i unui rand al
	 * tabelei.Valorile din coloane sunt ectrase, stiind denumirile coloanelor
	 * 
	 * @return ArrayList<Orderc> Se va returna vectorul de order-uri in caz de
	 *         reusia si null in caz de esec
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static ArrayList<Orderc> report() {
		ArrayList<Orderc> toReturn = new ArrayList<Orderc>();

		Connection dbConnection = ConnectionDatabase.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(reportStatementString);
			rs = findStatement.executeQuery();
			while (rs.hashCode() != 0 && rs.next()) {
				int id = rs.getInt("id");
				String clientname = rs.getString("clientname");
				Float total = rs.getFloat("total");
				toReturn.add(new Orderc(id, clientname, total));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderDAO:report " + e.getMessage());
		} finally {
			ConnectionDatabase.close(rs);
			ConnectionDatabase.close(findStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return toReturn;
	}

	/**
	 * Metoda este folosita pentru a modifica pretul total a unui order din tabela
	 * respectiva pe baza numelui clientului, creand o conexiune cu baza de date,
	 * adaugand parametrii necesari query-ului definit si initializat in loc de '?',
	 * apoi excutand query-ul
	 * 
	 * @param total Este primul parametru al metodei. Reprezinta numarul cu care se
	 *              va mari pretul cumparaturilor
	 * @param name  Este al doilea parametru al metodei. Reprezinta numele
	 *              clientului a carui total de cheltuieli va fi modificat
	 * @return int Se va returna 0 in caz de esec si 1 in caz de reusita
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static int update(float total, String name) {
		int i = 0;
		Connection dbConnection = ConnectionDatabase.getConnection();
		PreparedStatement findStatement = null;
		try {
			findStatement = dbConnection.prepareStatement(updateStatementString);
			findStatement.setFloat(1, total);
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
	 * Metoda este folosita pentru a sterge un order din tabela respectiva, creand o
	 * conexiune cu baza de date, adaugand parametrii necesari query-ului definit si
	 * initializat in loc de '?', apoi excutand query-ul
	 * 
	 * @param ClName Este singurul parametru al metodei. Reprezinta clientul al
	 *               carui nume determina stergerea order-ului
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
			LOGGER.log(Level.WARNING, "OrderDAO:delete " + e.getMessage());
		} finally {
			ConnectionDatabase.close(deleteStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return ok;
	}

}
