/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apppediatra.DAO;

import apppediatra.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author raizer
 */
public class PacienteDao {
    static Connection con = Conexao.getConnection();
    
    public static ArrayList<Paciente> all() {
        ArrayList<Paciente> pacientes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM paciente";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                Paciente p;
                
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String endereco = rs.getString("endereco");
                
                Date dataNascimento = new Date(rs.getLong("dataNascimento"));
                
                String email = rs.getString("email");
                String telefone = rs.getString("telefone");
                
                p = new Paciente(id, nome, endereco, dataNascimento, email, telefone);
                pacientes.add(p);
            }   
        } catch(SQLException e) {
            System.out.println(e);
        }
        return pacientes;
    }
    
    public static void add(Paciente p) { 
        try {            
            PreparedStatement st;
            ResultSet rs;
            String sql = "INSERT INTO paciente(nome, endereco, dataNascimento, email, telefone) VALUES (?,?,?,?,?)";
            
            String returnColunm[] = {"id"};
            
            con.setAutoCommit(false);
            st = con.prepareStatement(sql, returnColunm);
            st.setString(1, p.getNome());
            st.setString(2, p.getEndereco());
           
            st.setLong(3, p.getDataNascimento().getTime());
            
            st.setString(4, p.getEmail());
            st.setString(5, p.getTelefone());
            
            st.executeUpdate();
            
            rs = st.getGeneratedKeys();
            rs.next();
            p.setId(rs.getInt(1));
            
            con.commit();
        } catch(SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
            System.out.println(e);
        }
    }
    
    public static Paciente getOne(int id) {
        Paciente p = null; 
        try {
            String sql = "SELECT * FROM paciente WHERE id = "+id;
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
           
            if(rs.next()) {
                String nome = rs.getString("nome");
                String endereco = rs.getString("endereco");
                
                Date dataNascimento = new Date(rs.getLong("dataNascimento"));
                
                String email = rs.getString("email");
                String telefone = rs.getString("telefone");
                
                p = new Paciente(id, nome, endereco, dataNascimento, email, telefone);
            }          
        } catch(SQLException e) {
            System.out.println(e);
        }
        return p;
    }
}
