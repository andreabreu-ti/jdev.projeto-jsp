package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelTelefone;

public class DAOTelefoneRepository {

	private Connection connection;

	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public DAOTelefoneRepository() {
		// TODO Auto-generated constructor stub
		connection = SingleConnectionBanco.getConnection();

	}

	public void gravaTelefone(ModelTelefone modelTelefone) throws SQLException {

		String sql = "INSERT INTO telefone(numero, usuario_pai_id, usuario_cadastro_id) VALUES (?, ?, ?);";
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		prepareSql.setString(1, modelTelefone.getNumero());
		prepareSql.setLong(2, modelTelefone.getUsuario_pai_id().getId());
		prepareSql.setLong(3, modelTelefone.getUsuario_cadastro_id().getId());

		prepareSql.execute();
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
			modelTelefone.setUsuario_cadastro_id(daoUsuarioRepository.consultaUsuarioID(rs.getLong("usuario_cadastro_id")));
			modelTelefone.setUsuario_pai_id(daoUsuarioRepository.consultaUsuarioID(rs.getLong("usuario_pai_id")));

			retorno.add(modelTelefone);

		}
		return retorno;
	}
	
	public void deleteFone(Long id) throws SQLException {

		String sql = "delete from telefone where id = ?";

		PreparedStatement prepareSql = connection.prepareStatement(sql);
		prepareSql.setLong(1, id);

		prepareSql.executeUpdate();
		connection.commit();

	}

	public boolean existeFone(String fone, Long idUse) throws SQLException {
		
		String sql = "select count(1) > 0 as existe from telefone where usuario_pai_id =? and numero =?";
		
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		prepareSql.setLong(1, idUse);
		prepareSql.setString(2, fone);
		
		ResultSet resultSet = prepareSql.executeQuery();
		
		resultSet.next();
		
		return resultSet.getBoolean("existe");
		
	}
}
