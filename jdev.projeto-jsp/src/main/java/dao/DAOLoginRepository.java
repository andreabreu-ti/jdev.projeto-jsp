package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOLoginRepository {

	/**
	 * Objeto de conexão
	 */
	private Connection connection;
	
	public DAOLoginRepository() {

		connection = SingleConnectionBanco.getConnection();
	}
	
	/*
	 * Metodo para validar o login, recebendo um objeto inteiro ModelLogin;
	 */
	public boolean validarAutenticaca(ModelLogin modelLogin) throws Exception {
		
		String sql = "select * from model_login where upper(login) = upper(?) and upper(senha) = upper(?)";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			return true;	//Autenticado
		}
		
		return false;  //Não autenticado
		
	}
	
}






