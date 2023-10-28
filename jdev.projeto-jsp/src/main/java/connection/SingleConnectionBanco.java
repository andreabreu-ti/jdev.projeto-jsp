package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {

	private static String banco = "jdbc:postgresql://localhost:5432/jdev.projeto-jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "admin";
	private static Connection connection = null;
	
	/**
	 * Metodo para retornar a conecão existente
	 */
	
	public static Connection getConnection() {
		return connection;
	}
	
	static {
		conectar();
	}
	
	/**
	 * Quanto tiver uma instânca irá conectar
	 */
	public SingleConnectionBanco() {
		conectar();
	}
	
	private static void conectar() {
		
		try {
			
			if (connection == null) {
				
				/**
				 * Caregar o driver de conexõ do banco;
				 * setAutocommit igual a false, para não efetuar alterações no banco sem nosso comanado;
				 */
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, senha);
				connection.setAutoCommit(false);
				
			}
			
		} catch (Exception e) {
			// Mostrar qualquer erro no momento de conectar
			e.printStackTrace();
		}
	}
	
}
