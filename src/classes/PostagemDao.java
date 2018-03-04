package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

public class PostagemDao {
	private static final String INSERIR_POST = "INSERT INTO postagens(titulo, conteudo) VALUES (?,?)";

	public PostagemDao() {
	}

	public int cadastrarUmaPostagem(Postagem umaPostagem) {
		Connection connection = Conexao.conexao();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(INSERIR_POST, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, umaPostagem.getConteudo());
			preparedStatement.setString(2, umaPostagem.getData());
			preparedStatement.setString(3, umaPostagem.getUsuario().getLogin());

			preparedStatement.executeUpdate();

			ResultSet chavesGeradas = preparedStatement.getGeneratedKeys();

			if (chavesGeradas.next()) {
				System.out.println("Cadastrado com sucesso!");
				
				return chavesGeradas.getInt(1);

			} else {
				System.out.println("Não foi possivel conectar com o banco de dados.");
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return -1;
	}
	
	public List<Postagem> mostrarTodasAsPostagens() {
		Connection connection = Conexao.conexao();
        List<Postagem> todasAsPostagens = new ArrayList();
        ResultSet ResultSet;
        final String MOSTRAR_TODAS_POSTAGENS = "SELECT * FROM postagens";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(MOSTRAR_TODAS_POSTAGENS);
            ResultSet = preparedStatement.executeQuery();

            while (ResultSet.next()) {
                Usuario usuario = new Usuario();
                Postagem postagens = new Postagem();

                //usuario.setNome(ResultSet.getString("nome"));
                postagens.setConteudo(ResultSet.getString("conteudo"));
                postagens.setData(ResultSet.getString("dataPostagem"));

                todasAsPostagens.add(postagens);
                //cliente_resultados.add(cliente);
            }

        } catch (SQLException ex) {
			ex.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
        return todasAsPostagens;
	}
	
	public void removerUmaPostagem(Postagem umaPostagem) {
        try {
            try (PreparedStatement preparedStatement = Conexao.conexao().prepareStatement("DELETE FROM postagens WHERE id=?;")) {
                preparedStatement.setInt(1, umaPostagem.getId());
                preparedStatement.execute();
            }

        } catch (SQLException ex) {
        	ex.printStackTrace();
        }

    }
}
