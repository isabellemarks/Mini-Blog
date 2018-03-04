package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Conexao {
	public static Connection connection;
	
	public Conexao() {
	}
	
	public static Connection conexao() {
		
        try {		
        	try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (Conexao.connection == null) {
                Conexao.connection = DriverManager.getConnection("jdbc:mysql://10.225.2.202/INTEGRADOR_VC", "20121164010317", "ester24");
            }

        } catch (SQLException e) {
            System.out.println("Deu erro sql");
            //JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados\nError:" + e.getMessage());
        }
        return Conexao.connection;
    }

}
