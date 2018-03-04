package classes;

import java.sql.Date;

public class Postagem {
	private int id;
	private String conteudo;
	private String data;
	private Usuario usuario;
	
	public Postagem() {
		
	}
	
	public Postagem(String conteudo, String data, Usuario umUsuario) {
		this.conteudo = conteudo;
		this.data = data;
		this.usuario = umUsuario;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
