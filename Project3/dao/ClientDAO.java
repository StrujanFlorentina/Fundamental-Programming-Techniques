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
import model.Client;

/**
 * <h1>ClientDAO</h1> Clasa ClientDAO realizeaza interactiunea directa cu tabela
 * Client din data de baze warehouse
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class ClientDAO {

	protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO Client (name,address)" + " VALUES (?,?)";
	private final static String findStatementString = "SELECT * FROM Client where name=?";
	private final static String reportStatementString = "SELECT * FROM Client";
	private final static String deleteStatementString = "DELETE FROM Client where name=? AND address=?";

	/**
	 * Metoda este folosita pentru a gasi un client dupa numele acestuia, creand o
	 * conexiune cu baza de date, adaugand parametrii necesari query-ului definit si
	 * initializat in loc de '?', apoi excutand query-ul. Rezultatul executiei
	 * query-ului este stocat intr-un ResultSet, fiecare element al acestuia
	 * corespunzandu-i unui rand al tabelei.Valorile din coloane sunt ectrase,
	 * stiind denumirile coloanelor
	 * 
	 * @param ClientName Este singurul parametru al metodei. Reprezinta denumirea
	 *                   clientului cautat
	 * @return Client Se va returna clientul gasit in caz de reusita si null in caz
	 *         de esec
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static Client findByName(String ClientName) {
		Client toReturn = null;

		Connection dbConnection = ConnectionDatabase.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findStatementString);
			findStatement.setString(1, ClientName);
			rs = findStatement.executeQuery();
			rs.next();

			int id = rs.getInt("id");
			String address = rs.getString("address");
			toReturn = new Client(id, ClientName, address);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:findByName " + e.getMessage());
		} finally {
			ConnectionDatabase.close(rs);
			ConnectionDatabase.close(findStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return toReturn;
	}

	/**
	 * Metoda este folosita pentru a insera un client in tabela respectiva, creand o
	 * conexiune cu baza de date, adaugand parametrii necesari query-ului definit si
	 * initializat in loc de '?', apoi excutand query-ul
	 * 
	 * @param Client Este singurul parametru al metodei. Reprezinta clientul de
	 *               inserat. Rezultatul executiei query-ului este stocat intr-un
	 *               ResultSet, fiecare element al acestuia corespunzandu-i unui
	 *               rand al tabelei.Valorile din coloane sunt ectrase, stiind
	 *               denumirile coloanelor
	 * @return int Se va returna id-ul clientului inserat in caz de reusita si -1 in
	 *         caz de esec
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static int insert(Client Client) {
		Connection dbConnection = ConnectionDatabase.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, Client.getName());
			insertStatement.setString(2, Client.getAddress());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
		} finally {
			ConnectionDatabase.close(insertStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * Metoda este folosita pentru a sterge un client din tabela respectiva, creand
	 * o conexiune cu baza de date, adaugand parametrii necesari query-ului definit
	 * si initializat in loc de '?', apoi excutand query-ul.
	 * 
	 * @param Client Este singurul parametru al metodei. Reprezinta clientul de
	 *               sters
	 * @return int Se va returna 0 in caz de esec si 1 in caz de reusita
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static int delete(Client Client) {
		int ok = 0;
		Connection dbConnection = ConnectionDatabase.getConnection();
		PreparedStatement deleteStatement = null;
		try {
			deleteStatement = dbConnection.prepareStatement(deleteStatementString);
			deleteStatement.setString(1, Client.getName());
			deleteStatement.setString(2, Client.getAddress());
			deleteStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
		} finally {
			ConnectionDatabase.close(deleteStatement);
			ConnectionDatabase.close(dbConnection);
			ok = 1;
		}
		return ok;
	}

	/**
	 * Metoda este folosita pentru a popula un vector de clienti cu clientii din
	 * tabela respectiva, creand o conexiune cu baza de date, pe baza query-ului
	 * definit si initializat, apoi excutat. Rezultatul executiei query-ului este
	 * stocat intr-un ResultSet, fiecare element al acestuia corespunzandu-i unui
	 * rand al tabelei.Valorile din coloane sunt ectrase, stiind denumirile
	 * coloanelor
	 * 
	 * @return ArrayList<Client> Se va returna vectorul de clienti in caz de reusia
	 *         si null in caz de esec
	 * @exception SQLException In cazul in care esueaza operatiile
	 * @see SQLException
	 */
	public static ArrayList<Client> report() {
		ArrayList<Client> toReturn = new ArrayList<Client>();

		Connection dbConnection = ConnectionDatabase.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(reportStatementString);
			rs = findStatement.executeQuery();
			while (rs.hashCode() != 0 && rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				toReturn.add(new Client(id, name, address));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:report " + e.getMessage());
		} finally {
			ConnectionDatabase.close(rs);
			ConnectionDatabase.close(findStatement);
			ConnectionDatabase.close(dbConnection);
		}
		return toReturn;
	}

}
