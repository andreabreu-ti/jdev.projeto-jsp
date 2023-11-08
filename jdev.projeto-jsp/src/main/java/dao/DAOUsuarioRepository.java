package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {

		connection = SingleConnectionBanco.getConnection();

	}

	/*
	 * Método para INSERIR e ALTERAR usuário
	 */
	public ModelLogin gravaUsuario(ModelLogin objeto) throws SQLException {

		if (objeto.isNovo()) { // Gravar usuário

			String sql = "insert into model_login(login, senha, nome, email) values(?,?,?,?)";
			PreparedStatement prepareSql = connection.prepareStatement(sql);

			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2, objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());

			prepareSql.execute();
			connection.commit(); // Garantir a inclusão de dados

		} else {
			String sql = "update model_login set login=?, senha=?, nome=?, email=? where id = " + objeto.getId() + "";
			PreparedStatement prepareSql = connection.prepareStatement(sql);

			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2, objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());

			prepareSql.executeUpdate();
			connection.commit();

		}
		return this.consultaUsuario(objeto.getLogin());

	}

	/*
	 * Método para LISTAR todos os usuários
	 */
	public List<ModelLogin> consultaUsuarioList() throws SQLException {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false order by id";

		PreparedStatement prepareSql = connection.prepareStatement(sql);

		ResultSet resultado = prepareSql.executeQuery();

		/*
		 * Percorrer as linhs de resultado do SQL
		 */
		while (resultado.next()) {

			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));

			retorno.add(modelLogin);

		}
		return retorno;
	}

	/*
	 * Método para LISTAR os usuários em uma MODAL
	 */
	public List<ModelLogin> consultaUsuarioList(String nome) throws SQLException {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false";

		PreparedStatement prepareSql = connection.prepareStatement(sql);

		prepareSql.setString(1, "%" + nome + "%");

		ResultSet resultado = prepareSql.executeQuery();

		/*
		 * Percorrer as linhs de resultado do SQL
		 */
		while (resultado.next()) {

			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));

			retorno.add(modelLogin);

		}
		return retorno;
	}

	/*
	 * Método para CONSULTAR usuários por login
	 */
	public ModelLogin consultaUsuario(String login) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login) = upper('" + login + "') and useradmin is false";

		PreparedStatement prepareSql = connection.prepareStatement(sql);
		ResultSet resultado = prepareSql.executeQuery();

		/*
		 * Se consultar e achar no banco, setar os dados
		 */
		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
		}

		return modelLogin;

	}

	/*
	 * Método para CONSULTAR usuários por ID
	 */
	public ModelLogin consultaUsuarioID(String id) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where id = ? and useradmin is false";

		PreparedStatement prepareSql = connection.prepareStatement(sql);
		prepareSql.setLong(1, Long.parseLong(id));

		ResultSet resultado = prepareSql.executeQuery();

		/*
		 * Se consultar e achar no banco, setar os dados
		 */
		while (resultado.next()) {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
		}

		return modelLogin;

	}

	/*
	 * Método para VALIDAR LOGIN
	 */
	public boolean validarLogin(String login) throws SQLException {

		String sql = "select count(1) > 0 as existe  from model_login where upper(login) = upper('" + login + "')";

		PreparedStatement prepareSql = connection.prepareStatement(sql);
		ResultSet resultado = prepareSql.executeQuery();

		resultado.next(); // Para entrar nos resultados do sql
		return resultado.getBoolean("existe");

	}

	/*
	 * Método para DELETAR usuário
	 */

	public void deletarUser(String idUser) throws SQLException {
		String sql = "delete from model_login where id = ? and useradmin is false";
		PreparedStatement prepareSql = connection.prepareStatement(sql);

		prepareSql.setLong(1, Long.parseLong(idUser));
		prepareSql.executeUpdate();
		connection.commit();
	}

}
