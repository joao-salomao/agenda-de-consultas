/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apppediatra.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author João Salomão
 */
public class Conexao {
    private static Connection connection;
    private static final String serverName = "localhost";
    private static final String mydatabase = "AppPediatra";
    private static final String username = "postgres";
    private static final String password = "postgres";
    private static final String url = "jdbc:postgresql://" + serverName+ "/" + mydatabase;
    
    public static Connection getConnection(){
        if ( connection == null){
            try {
                String driverName = "org.postgresql.Driver";
                Class.forName(driverName);
                connection = DriverManager.getConnection(url,username, password);
            } catch(ClassNotFoundException e) {
                System.out.println(e);
                System.out.println("A classe não foi encontrada");
            }catch (SQLException e){
                System.out.println(e);
            }
        }
        return connection;
    }
    public static boolean encerraConexao() {
        try {
            connection.close();
            return true;
        } catch(SQLException e) {
            return false;
        }
    }

    static Connection getConexao() {
        getConnection();
        return connection;
    }
}
