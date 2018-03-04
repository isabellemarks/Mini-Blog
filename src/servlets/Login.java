package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import classes.Usuario;
import conexao.Conexao;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login.do")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	public void pegarLogin(HttpServletRequest request, HttpServletResponse response) {
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @throws SQLException 
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	public void metodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		Connection connection = Conexao.conexao();
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");


		PreparedStatement preparedStatement = connection.prepareStatement("SELECT login, senha, nome FROM usuario WHERE login=? AND senha=?;");
		preparedStatement.setString(1, login);
		preparedStatement.setString(2, senha);


		ResultSet ResultSet = preparedStatement.executeQuery();



		while (ResultSet.next()) {
			Usuario usuario = new Usuario();

			usuario.setLogin(ResultSet.getString(1));
			usuario.setSenha(ResultSet.getString(2));

			HttpSession session = request.getSession(true);
			session.setAttribute("login", login);


			PrintWriter out;
			out = response.getWriter();

			String nome = ResultSet.getString("nome");


			response.setContentType("text/html;charset=UTF-8");

			out.println("<!DOCTYPE html>\n"
					+ "<html>\n"
					+ "	<head>\n"
					+ "		<meta charset=\"utf-8\">\n"
					+ "		<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
					+ "		<title>Postagem</title>\n"
					+ "		<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />\n"
					+ "	</head>\n"
					+ "	<body>\n"
					+ "		<div id=\"superior\">\n"
					+ "			<p id=\"sup\"><Strong>Logado como: " + nome + " </Strong><a href=\"Logout.do\"> Sair</a></p>\n"
					+ "		</div>\n"
					+ "		<div id=\"postagem\">\n"
					+ "			<h2>Conteúdo:</h2>\n"
					+ "			<form action=\"InserirPostagem.do\" method=\"post\">\n"
					+ "				<textarea name=\"postagem\" rows=\"20\" cols=\"80\"></textarea><br>\n"	
					+ "				<input type=\"submit\" name=\"Enviar\">\n"
					+ "			</form>\n"
					+ "			<br>\n"
					+ "			<br>\n"
					+ "			<h2><a href= MostrarPostagens.do> Ver postagens </a></h2>"
					+ "		</div>\n"
					+ "	</body>\n"
					+ "</html>");

		}


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = Conexao.conexao();

		if (connection == null) {
			System.out.println("Conexao nula");
		} else if (connection != null) {
			try {
				metodo(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}


