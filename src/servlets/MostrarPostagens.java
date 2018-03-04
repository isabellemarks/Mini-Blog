package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.sql.PreparedStatement;
import java.util.ArrayList;

import conexao.Conexao;
import classes.Postagem;
import classes.PostagemDao;
import classes.Usuario;

/**
 * Servlet implementation class MostrarPostagens
 */
@WebServlet("/MostrarPostagens.do")
public class MostrarPostagens extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = Conexao.conexao();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MostrarPostagens() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @throws SQLException 
	 * @throws IOException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public Usuario consultarUsuarioPorId(String login){
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("SELECT * FROM usuario WHERE login = '" + login + "';");
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				Usuario usuario = new Usuario ();
				usuario.setLogin(rs.getString(1));
				usuario.setSenha(rs.getString(2));
				usuario.setNome(rs.getString(3));
				return usuario;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void mostrarPostagens(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		ArrayList<Postagem> postagens = new ArrayList<Postagem>(); 
		String publicacao = request.getParameter("postagem");
		PrintWriter out = response.getWriter();
		PostagemDao postagemDao = new PostagemDao();
		postagemDao.mostrarTodasAsPostagens();
		HttpSession session = request.getSession(false);
		Object nomeUsuario = request.getSession().getAttribute("login");
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM postagens ORDER BY dataPostagem DESC;");
		ResultSet rs = statement.executeQuery();
		while(rs.next()){
			Postagem postagem = new Postagem();
			postagem.setId(rs.getInt(1));
			postagem.setConteudo(rs.getString(2));
			postagem.setData(rs.getString(3));
			postagem.setUsuario(consultarUsuarioPorId(rs.getString(4)));
			postagens.add(postagem);
		}
		for(int i =0; i<postagens.size(); i++){
			System.out.println(postagens.get(i).getConteudo());
			out.println("<div class=\"postsBanco\">\n"
                    + "		<p class=\"data\">" + postagens.get(i).getData() + " - " + postagens.get(i).getUsuario().getNome() + "</p>\n"
                    + "		<p class=\"posts\">" + postagens.get(i).getConteudo() + "</p>\n"
                    + "	<br>\n"
                    + " <br>\n"
                    + " </div>\n"
                    + " <br>\n"
                    + "	<br>");
		}

	}

	public void mostrarPesquisa(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out;
		System.out.println("Entrou no mostrarPesquisa");
		try {
			out = response.getWriter();
			try {
				response.setContentType("text/html;charset=UTF-8");
				out.print("<!DOCTYPE html>\n"
						+ "<html>\n"
						+ "<head>\n"
						+ "	<meta charset=\"utf-8\">\n"
						+ "	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
						+ "	<title></title>\n"
						+ "	<link rel=\"stylesheet\" href=\"estilo.css\">\n"
						+ "</head>\n"
						+ "<body>\n"
						+ "	<div id=\"superior\">\n"
						+ "		<p id=\"sup\"><a href=\"login.html\">Entrar</a></p>\n"
						+ "	</div>\n"
						+ "	<h1>Meu blog</h1>\n");
				mostrarPostagens(request, response);
				out.print("</body>\n"
						+ "</html>");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out;
		System.out.println("Entrou no mostrarPesquisa");
		try {
			out = response.getWriter();
			try {
				response.setContentType("text/html;charset=UTF-8");
				out.print("<!DOCTYPE html>\n"
						+ "<html>\n"
						+ "<head>\n"
						+ "	<meta charset=\"utf-8\">\n"
						+ "	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
						+ "	<title></title>\n"
						+ "	<link rel=\"stylesheet\" href=\"estilo.css\">\n"
						+ "</head>\n"
						+ "<body>\n"
						+ "	<div id=\"superior\">\n"
						+ "		<p id=\"sup\"><a href=\"login.html\">Entrar</a></p>\n"
						+ "	</div>\n"
						+ "	<h1>Meu blog</h1>\n");
				mostrarPostagens(request, response);
				out.print("</body>\n"
						+ "</html>");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		mostrarPesquisa(request, response);
	}

}
