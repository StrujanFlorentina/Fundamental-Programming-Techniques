package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h1>Conexiunea cu Bada de Date</h1> Clasa ConnectionDatabase realizeaza
 * conexiunea cu baza de date warehouse. Contine numele driver-ului, locatia
 * bazei de date, numele user-ului si parola acestuia
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class ConnectionDatabase {

	private static final Logger LOGGER = Logger.getLogger(ConnectionDatabase.class.getName());
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/warehouse";
	private static final String USER = "root";
	private static final String PASS = "flori098";

	private static ConnectionDatabase singleInstance = new ConnectionDatabase();

	private ConnectionDatabase() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metoda este folosita pentru a se crea conexiunea cu baza de date, conexiune
	 * care se va plasa intr-un obiect de tip Singleton
	 * 
	 * @exception SQLException In cazul in care esueaza conexiuea
	 * @see SQLException
	 */
	private Connection createConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DBURL, USER, PASS);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * Metoda este folosita pentru a obtine o conexiune activa
	 */
	public static Connection getConnection() {
		return singleInstance.createConnection();
	}

	/**
	 * Metoda este folosita pentru a intrerupe conexiunea cu baza de date
	 * 
	 * @exception SQLException In cazul in care esueaza conexiuea
	 * @see SQLException
	 */
	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
			}
		}
	}

	/**
	 * Metoda este folosita pentru a inchide un Statement
	 * 
	 * @exception SQLException In cazul in care esueaza conexiuea
	 * @see SQLException
	 */
	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
			}
		}
	}

	/**
	 * Metoda este folosita pentru a inchide un ResultSet
	 * 
	 * @exception SQLException In cazul in care esueaza conexiuea
	 * @see SQLException
	 */
	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
			}
		}
	}
}
