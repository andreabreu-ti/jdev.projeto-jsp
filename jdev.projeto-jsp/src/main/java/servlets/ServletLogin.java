package servlets;

import java.io.IOException;

import dao.DAOLoginRepository;
import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

/**
 * Servlet implementation class ServletLogin
 * Servlet também são Controllers
 * Mapeamento da url que vem da tela
 */

public class ServletLogin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLogin() {
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * doGet: Recebe os dados pela url em parametros
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");

		/**
		 * Invalida a sessão, apaga todos os atributos colocado na sessão
		 */
		if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logou")) {
			request.getSession().invalidate();
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			redirecionar.forward(request, response);
			
		}else {
			doPost(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * doPost: Recebe os dados enviados por um formulário
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url   = request.getParameter("url");	//Vem da index.jsp
		
		try {
			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
				
				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setLogin(login);
				modelLogin.setSenha(senha);
				
				/**
				 * 	Simulação de Login
				 */
				
				if (daoLoginRepository.validarAutenticaca(modelLogin)) {
					
					/*
					 * Pesquisar no banco para saber se o usuário é admin
					 */
					modelLogin = daoUsuarioRepository.consultaUsuarioLogado(login);
					
					
					/**
					 * Se o login deu certo...colocar um atributo de sessao passando o modeloLogin (Objeto)
					 */
					request.getSession().setAttribute("usuario", modelLogin.getLogin());
					request.getSession().setAttribute("perfil", modelLogin.getPerfil());
					
					/**
					 * Colocou na seção, redirecionar para o principal.jsp
					 * Realizar uma validação da url antes de redirecionar
					 */
					if (url == null || url.equals("null")) {
						url = "principal/principal.jsp";
					}
					
					RequestDispatcher redirecionar = request.getRequestDispatcher(url);
					redirecionar.forward(request, response);
					
				}else {
					/**
					 * Se não for o usuário e senha admin, redirecionar para a tela com mensagem de erro
					 */
					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "Informe o login e senha Corretamente. msg2");
					redirecionar.forward(request, response);
					
				}
				
				
			}else {
				RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
				request.setAttribute("msg", "Informe o login e senha Corretamente. msg1");
				redirecionar.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
		
	}

}
