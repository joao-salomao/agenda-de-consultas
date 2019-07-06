/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apppediatra.DAO;

import apppediatra.Consultorio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author raizer
 */
public class ConsultorioDao {
    static Connection con = Conexao.getConnection();
    
    public static ArrayList<Consultorio> all() {
        ArrayList<Consultorio> consultorios = new ArrayList<>();
        try {
            String sql = "SELECT * FROM consultorio";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()) {
                Consultorio c;
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                c = new Consultorio(id, nome);
                consultorios.add(c);
            }   
        } catch(SQLException e) {
            System.out.println(e);
        }
        
        return consultorios;
    }
    public static void add(Consultorio c) {
        int id = 0;
        try {
            String sql = "INSERT INTO consultorio(nome) VALUES(?)";
            String returnColunm[] = {"id"};
            
            PreparedStatement st = con.prepareStatement(sql, returnColunm);
            st.setString(1, c.getNome());
            st.executeUpdate();
            
            ResultSet rs;
            rs = st.getGeneratedKeys();
            rs.next();
           
            c.setId(rs.getInt(1));
        } catch(SQLException e) {
            System.out.println(e);
        }
    }
    
    public static Consultorio getConsultorioAgenda(int consultorioId) {
        Consultorio consultorio = null;
        try {
            String sql = "SELECT * FROM consultorio WHERE id = "+consultorioId;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String nome = rs.getString("nome");
            consultorio = new Consultorio(consultorioId, nome);
        } catch(SQLException e) {
            System.out.println(e);
        }
        return consultorio;
    }
}
