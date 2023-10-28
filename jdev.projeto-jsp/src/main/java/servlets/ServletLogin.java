package servlets;

import java.io.IOException;

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
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * doPost: Recebe os dados enviados por um formulário
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url   = request.getParameter("url");	//Vem da index.jsp
		
		if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
			
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			
			/**
			 * 	Simulação de Login
			 */
			
			if (modelLogin.getLogin().equalsIgnoreCase("admin") 
					&& modelLogin.getSenha().equalsIgnoreCase("admin")) {
				
				/**
				 * Se o login deu certo...colocar u atributo de sessao passando o modeloLogin (Objeto)
				 */
				request.getSession().setAttribute("usuario", modelLogin.getLogin());
				
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
				request.setAttribute("msg", "Informe o login e senha ocrretamente. msg2");
				redirecionar.forward(request, response);
				
			}
			
			
		}else {
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			request.setAttribute("msg", "Informe o login e senha ocrretamente. msg1");
			redirecionar.forward(request, response);
		}
		
	}

}
