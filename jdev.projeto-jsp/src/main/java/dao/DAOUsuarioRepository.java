package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beandto.BeanDtoGraficoSalarioUser;
import connection.SingleConnectionBanco;
import model.ModelLogin;
import model.ModelTelefone;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {

		connection = SingleConnectionBanco.getConnection();

	}

	public BeanDtoGraficoSalarioUser montarGrafioMediaSalario(Long userLogado, String dataInicial, String dataFinal) throws Exception {

		String sql = "SELECT avg(rendamensal) as media_salarial, perfil FROM model_login where usuario_id = ? and datanascimento >= ? and datanascimento <= ? group by perfil";

		PreparedStatement prepareSql = connection.prepareStatement(sql);

		prepareSql.setLong(1, userLogado);
		prepareSql.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
		prepareSql.setDate(3, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));

		ResultSet resultSet = prepareSql.executeQuery();

		List<String> perfis = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();

		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();

		while (resultSet.next()) {

			Double media_salarial = resultSet.getDouble("media_salarial");
			String perfil = resultSet.getString("perfil");

			perfis.add(perfil);
			salarios.add(media_salarial);
		}

		beanDtoGraficoSalarioUser.setPerfis(perfis);
		beanDtoGraficoSalarioUser.setSalarios(salarios);

		return beanDtoGraficoSalarioUser;
	}

	public BeanDtoGraficoSalarioUser montarGrafioMediaSalario(Long userLogado) throws Exception {

		String sql = "SELECT avg(rendamensal) as media_salarial, perfil FROM model_login where usuario_id = ? group by perfil";

		PreparedStatement prepareSql = connection.prepareStatement(sql);
		prepareSql.setLong(1, userLogado);

		ResultSet resultSet = prepareSql.executeQuery();

		List<String> perfis = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();

		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();

		while (resultSet.next()) {

			Double media_salarial = resultSet.getDouble("media_salarial");
			String perfil = resultSet.getString("perfil");

			perfis.add(perfil);
			salarios.add(media_salarial);
		}

		beanDtoGraficoSalarioUser.setPerfis(perfis);
		beanDtoGraficoSalarioUser.setSalarios(salarios);

		return beanDtoGraficoSalarioUser;

	}

	/*
	 * Método para INSERIR e ALTERAR usuário
	 */
	public ModelLogin gravaUsuario(ModelLogin objeto, Long userLogado) throws SQLException {

		if (objeto.isNovo()) { // Gravar usuário

			String sql = "insert into model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero, datanascimento, rendamensal) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
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
			prepareSql.setDate(14, objeto.getDataNascimento());
			prepareSql.setDouble(15, objeto.getRendaMensal());

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
			String sql = "update model_login set login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=?, datanascimento =?, rendamensal=? where id = "
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
			prepareSql.setDate(13, objeto.getDataNascimento());
			prepareSql.setDouble(14, objeto.getRendaMensal());

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
	 * PAGINAÇÃO
	 */

	public int totalpagina(Long userLogado) throws SQLException {

		String sql = "select count(*) as total from model_login where usuario_id = " + userLogado;

		PreparedStatement prepareSql = connection.prepareStatement(sql);
		ResultSet resultado = prepareSql.executeQuery();
		resultado.next();
		Double cadastros = resultado.getDouble("total");
		Double porpagina = 5.0;
		Double pagina = cadastros / porpagina;
		Double resto = pagina % 2;

		if (resto > 0) {
			pagina++;
		}

		return pagina.intValue();
	}

	/*
	 * Consulta para PAGINAÇÃO
	 */
	public List<ModelLogin> consultaUsuarioListPaginado(Long userLogado, Integer offset) throws SQLException {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado
				+ " order by nome offset " + offset + " limit 5";
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

	public List<ModelLogin> consultaUsuarioListRel(Long userLogado) throws SQLException {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado;

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
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));

			modelLogin.setTelefones(this.listFone(modelLogin.getId()));

			retorno.add(modelLogin);

		}
		return retorno;
	}

	public List<ModelLogin> consultaUsuarioListRel(Long userLogado, String dataInicial, String dataFinal)
			throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado
				+ "and datanascimento >= ? and datanascimento <= ?";

		PreparedStatement prepareSql = connection.prepareStatement(sql);

		prepareSql.setDate(1, Date.valueOf(
				new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
		prepareSql.setDate(2, Date.valueOf(
				new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));

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
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));

			modelLogin.setTelefones(this.listFone(modelLogin.getId()));

			retorno.add(modelLogin);

		}
		return retorno;
	}

	/*
	 * Método para LISTAR todos os usuários
	 */
	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws SQLException {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado + " limit 5";

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
	 * Método para LISTAR os usuários em uma MODAL PAGINADA
	 */
	public int consultaUsuarioListTotalPaginacao(String nome, Long userLogado) throws SQLException {

		String sql = "select count(1) as total from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ?";

		PreparedStatement prepareSql = connection.prepareStatement(sql);
		prepareSql.setString(1, "%" + nome + "%");
		prepareSql.setLong(2, userLogado);

		ResultSet resultado = prepareSql.executeQuery();

		resultado.next();
		Double cadastros = resultado.getDouble("total");
		Double porpagina = 5.0;
		Double pagina = cadastros / porpagina;
		Double resto = pagina % 2;
		if (resto > 0) {
			pagina++;
		}

		return pagina.intValue();
	}

	/*
	 * Método para LISTAR os usuários em uma MODAL
	 */
	public List<ModelLogin> consultaUsuarioListOffset(String nome, Long userLogado, int offset) throws SQLException {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? offset "
				+ offset + " limit 5";

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
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));
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
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));

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
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));
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
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));

		}

		return modelLogin;

	}

	/*
	 * Método para CONSULTAR usuários por ID
	 */
	public ModelLogin consultaUsuarioID(Long id) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where id = ? and useradmin is false";

		PreparedStatement prepareSql = connection.prepareStatement(sql);
		prepareSql.setLong(1, id);

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
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));

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

	public List<ModelTelefone> listFone(Long idUserPai) throws SQLException {

		List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();
		String sql = "select * from telefone where usuario_pai_id =?";

		PreparedStatement prepareSql = connection.prepareStatement(sql);
		prepareSql.setLong(1, idUserPai);
		ResultSet rs = prepareSql.executeQuery();

		while (rs.next()) {

			ModelTelefone modelTelefone = new ModelTelefone();

			modelTelefone.setId(rs.getLong("id"));
			modelTelefone.setNumero(rs.getString("numero"));
			modelTelefone.setUsuario_cadastro_id(this.consultaUsuarioID(rs.getLong("usuario_cadastro_id")));
			modelTelefone.setUsuario_pai_id(this.consultaUsuarioID(rs.getLong("usuario_pai_id")));

			retorno.add(modelTelefone);

		}
		return retorno;
	}

}
