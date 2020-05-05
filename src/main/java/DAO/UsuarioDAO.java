package DAO;
import DBConfig.DB;
import Entities.Usuario;
import Enums.Permissao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAO {

    public static boolean checkCredentials(String email, String password) throws SQLException, ClassNotFoundException {
        boolean credentials = false;

        //Inicializa conexão ao banco de dados
        DB connection = new DB();
        //Realiza consulta
        Statement stmt = connection.getConnection().createStatement();
        ResultSet rs=stmt.executeQuery("select * from user where email='"+email+"' and password='"+password+"'");

        if(rs.next())
            credentials = true;

        //Finaliza a conexão
        connection.closeConnection();

        return credentials;
    }
    public static Usuario getByCredentials(String email, String password) throws Exception {
        try
        {
            Usuario user = new Usuario("a","a","s","a");
            //Inicializo Conexão
            DB Connection = new DB();
            //Realiza consulta
            Statement stmt = Connection.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("select * from user where email='"+email+"' and password='"+password+"'");

            //Instancia atributos do Usuário que está logando
            user.setCpf(rs.getString("cpf"));
            user.setAdress(rs.getString("adress"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setPermissao(Permissao.USUARIO);
            user.setWallet(null);

            //Finaliza a conexão
            Connection.closeConnection();

            return user;
        }
        catch (Exception e)
        {
            throw new Exception(e);
        }
    }

    public static void add(Usuario usuario){
        try{
            DB connection = new DB();
            String sql = "INSERT INTO user (cpf, name, adress, password) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.getConnection().prepareStatement(sql);
            stmt.setString(1, usuario.getCpf());
            stmt.setString(2, usuario.getName());
            stmt.setString(3, usuario.getAdress());
            stmt.setString(4, usuario.getPassword());
            stmt.execute();
            stmt.close();
            connection.closeConnection();
        } catch (SQLException | ClassNotFoundException e ) {
            System.out.println(e.getMessage());
        }
    }
}
