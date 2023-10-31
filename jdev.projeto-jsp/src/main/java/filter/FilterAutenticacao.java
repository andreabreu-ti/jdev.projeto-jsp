package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Tudo que passar de requisição, irá cair no filter, tudo irá passar por ele;
 * Ponto de Filtragem. Porta de Entrada do sistema;
 * Intercepta todas as requisições que vierem do projeto ou mapeamento.
 * 
 */
@WebFilter(urlPatterns = {"/principal/*"})
public class FilterAutenticacao extends HttpFilter implements Filter {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Connection connection;

	/**
	 * @see HttpFilter#HttpFilter()
	 */
	public FilterAutenticacao() {

	}

	/**
	 * @see Filter#destroy()
	 * Encerra os processos quando o servidor é parado;
	 * Ex.: Mataria os processos de conexão com o banco;
	 */
	public void destroy() {

		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 * Interepta as requisições e as respostas no sistema;
	 * Tudo que fizer no sistema irá passar pelo doFilter;
	 * Ex.: Validação de Autenticação;
	 *      Commit e rolback de transações no banco;
	 *      Validar e fazer redirecionamento de páginas
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			
			String usuarioLogado = (String) session.getAttribute("usuario");
			
			/**
			 * Url que está sendo acessada
			 */
			String urlParaAutenticar = req.getServletPath();
			
			/**
			 * Validar se está logado senao redirecionar para a tela de login;
			 */
			if (usuarioLogado == null &&
					!urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {
				/**
				 * Não está logado!
				 */
				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o login!!! msg3Filter");
				redireciona.forward(request, response);
				return; //Parar a execução e redireciona para o login
			
			}else {
				
				/**
				 * chain.doFilter : Deixa o processo do software continuar;
				 * Se estiver logado, seguir com o processo do sistema;
				 */
				chain.doFilter(request, response);
			}
			
			/**
			 * Deu tudo certo, então comita as alterações no banco de dados
			 */
			connection.commit();
			
			
			
		} catch (Exception e) {
			// TODO: Exceção do sistema
			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO: Exceção do banco
				e1.printStackTrace();
			}
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 * Inícia os processos ou recursos quando o servidor sob o projeto
	 * Ex.: Iniciar a conexão com o banco
	 */
	public void init(FilterConfig fConfig) throws ServletException {

		connection = SingleConnectionBanco.getConnection();
		
	}

}
