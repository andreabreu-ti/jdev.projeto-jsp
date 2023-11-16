package servlets;

import java.io.Serializable;
import java.sql.SQLException;

import dao.DAOUsuarioRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.ModelLogin;


/*
 * Método que retorno o usuário logado
 */
public class ServletGenericUtil extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public Long getUserLogado(HttpServletRequest request) throws SQLException {

		HttpSession session = request.getSession();
		String usuarioLogado = (String) session.getAttribute("usuario");
		return daoUsuarioRepository.consultaUsuarioLogado(usuarioLogado).getId();
	}
	
	public ModelLogin getUserLogadoObject(HttpServletRequest request) throws SQLException {

		HttpSession session = request.getSession();
		String usuarioLogado = (String) session.getAttribute("usuario");
		return daoUsuarioRepository.consultaUsuarioLogado(usuarioLogado);
	}

}
