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
	public ModelLogin gravaUsuario(ModelLogin objeto, Long userLogado) throws SQLException {

		if (objeto.isNovo()) { // Gravar usuário

			String sql = "insert into model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement prepareSql = connection.prepareStatement(sql);

			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2, objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());
			prepareSql.setLong(5, userLogado);
			prepareSql.setString(6, objeto.getPerfil());
			prepareSql.setString(7, objeto.getSexo());
			prepareSql.setString(8, objeto.getCep());
			prepareSql.setString(9, objeto.getLogradouro());
			prepareSql.setString(10, objeto.getBairro());
			prepareSql.setString(11, objeto.getLocalidade());
			prepareSql.setString(12, objeto.getUf());
			prepareSql.setString(13, objeto.getNumero());

			prepareSql.execute();
			connection.commit(); // Garantir a inclusão de dados

			/*
			 * Após a inserção, realizar um update se realmente existir a foto
			 */

			if (objeto.getFotoUser() != null && !objeto.getFotoUser().isEmpty()) {
				sql = "update model_login set fotouser = ?, extensaofotouser = ? where login = ?";
				prepareSql = connection.prepareStatement(sql);
				prepareSql.setString(1, objeto.getFotoUser());
				prepareSql.setString(2, objeto.getExtensaofotouser());
				prepareSql.setString(3, objeto.getLogin());
				
				prepareSql.execute();
				connection.commit();

			}

		} else {
			String sql = "update model_login set login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=? where id = "
					+ objeto.getId() + ";";
			PreparedStatement prepareSql = connection.prepareStatement(sql);

			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2, objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());
			prepareSql.setString(5, objeto.getPerfil());
			prepareSql.setString(6, objeto.getSexo());
			prepareSql.setString(7, objeto.getCep());
			prepareSql.setString(8, objeto.getLogradouro());
			prepareSql.setString(9, objeto.getBairro());
			prepareSql.setString(10, objeto.getLocalidade());
			prepareSql.setString(11, objeto.getUf());
			prepareSql.setString(12, objeto.getNumero());

			prepareSql.executeUpdate();
			connection.commit();

			/*
			 * Após a atualiação, realizar um update se realmente existir a foto
			 */

			if (objeto.getFotoUser() != null && !objeto.getFotoUser().isEmpty()) {
				sql = "update model_login set fotouser = ?, extensaofotouser = ? where id = ?";
				prepareSql = connection.prepareStatement(sql);
				prepareSql.setString(1, objeto.getFotoUser());
				prepareSql.setString(2, objeto.getExtensaofotouser());
				prepareSql.setLong(3, objeto.getId());

				prepareSql.execute();
				connection.commit();

			}

		}
		return this.consultaUsuario(objeto.getLogin(), userLogado);

	}
	
	/*
	 * Consulta para PAGINAÇÃO
	 */
	public List<ModelLogin> consultaUsuarioListPaginado(Long userLogado, Integer offset) throws SQLException {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado +" order by nome offset "+offset+" limit 5";

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
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);

		}
		return retorno;
	}
	

	/*
	 * Método para LISTAR todos os usuários
	 */
	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws SQLException {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado +" limit 5";

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
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);

		}
		return retorno;
	}

	/*
	 * Método para LISTAR os usuários em uma MODAL
	 */
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws SQLException {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5";

		PreparedStatement prepareSql = connection.prepareStatement(sql);

		prepareSql.setString(1, "%" + nome + "%");
		prepareSql.setLong(2, userLogado);

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
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);

		}
		return retorno;
	}

	/*
	 * Método para CONSULTAR usuário logado
	 */
	public ModelLogin consultaUsuarioLogado(String login) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login) = upper('" + login + "')";

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
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
		}

		return modelLogin;

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
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			
		}

		return modelLogin;

	}

	/*
	 * Método
	 */
	public ModelLogin consultaUsuario(String login, Long userLogado) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login) = upper('" + login
				+ "') and useradmin is false and usuario_id = " + userLogado;

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
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
		}

		return modelLogin;

	}

	/*
	 * Método para CONSULTAR usuários por ID
	 */
	public ModelLogin consultaUsuarioID(String id, Long userLogado) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where id = ? and useradmin is false and usuario_id =?";

		PreparedStatement prepareSql = connection.prepareStatement(sql);
		prepareSql.setLong(1, Long.parseLong(id));
		prepareSql.setLong(2, userLogado);

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
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));

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
