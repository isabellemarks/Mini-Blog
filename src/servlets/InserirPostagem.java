package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import conexao.Conexao;

/**
 * Servlet implementation class Postagem
 */
@WebServlet("/InserirPostagem.do")
public class InserirPostagem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InserirPostagem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String postagem = request.getParameter("postagem");
		String data =  request.getParameter("postagem");
		
		HttpSession session = request.getSession(false);
		Object nomeUsuario = request.getSession().getAttribute("login");
		
		Date date = new Date(System.currentTimeMillis());
		String dataFormatada = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm").format(date);
		Cookie cookie = new Cookie("postagem", request.getParameter("postagem"));
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		
		final String inserir = "INSERT INTO postagens(conteudo, dataPostagem, login) VALUES ('" + postagem + "','" + date + "','" + nomeUsuario + "');";
		Connection connection = Conexao.conexao();
		Statement statement;
		try {
			statement = connection.createStatement();
			int rs = statement.executeUpdate(inserir);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "	<head>\n"
				+ "		<meta charset=\"utf-8\">\n"
				+ "		<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
				+ "		<title>Postagem</title>\n"
				+ "		<link rel=\"stylesheet\" type=\"text/css\" href=\"certo.css\" />\n"
				+ "	</head>\n"
				+ "	<body>\n"
				+ "		<div id=\"postagem\">\n"
				+ "		<h1>Postagem adicionada com sucesso!</h1>\n"
				+ " <div id=\"foto\">\n"
				+ " </div>\n"
				+ "	<h2><a href= MostrarPostagens.do> Ver postagens </a></h2>"
				+ "	</body>\n"
				+ "</html>");
		
		Cookie[] cookies = request.getCookies();
		for (int i=0; i < cookies.length; i++) {
			System.out.println(cookies[i].getName() + "=" + cookies[i].getValue());
		}
	}

}
