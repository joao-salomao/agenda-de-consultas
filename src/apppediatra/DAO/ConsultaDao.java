/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apppediatra.DAO;

import apppediatra.Consulta;
import apppediatra.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author raizer
 */
public class ConsultaDao {
    static Connection con = Conexao.getConexao();
    
    public static void add(Consulta c) {
        try {
            String sql = "INSERT INTO consulta(data, hora, pacienteId) VALUES(?, ?, ?)";
            String returnColunm[] = {"id"};
            
            PreparedStatement st = con.prepareStatement(sql, returnColunm);
            st.setLong(1, c.getData().getTime());
            st.setString(2, c.getHora().toString());
            st.setInt(3, c.getPaciente().getId());
            st.executeUpdate();
            
            ResultSet rs;
            rs = st.getGeneratedKeys();
            rs.next();
           
            c.setId(rs.getInt(1));
        } catch(SQLException e) {
            System.out.println(e);
        }
    }
    
    public static Consulta getOne(int id) {
        Consulta c = null; 
        try {
            String sql = "SELECT * FROM consulta WHERE id = "+id;
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
           
            rs.next();
            
            Date data = new Date(rs.getLong("data"));
            LocalTime hora = LocalTime.parse(rs.getString("hora"));
            int pacienteId = rs.getInt("pacienteId");
            
            Paciente paciente = PacienteDao.getOne(pacienteId);
           
            c = new Consulta(id, data, hora, paciente);
        } catch(SQLException e) {
            System.out.println(e);
        }
        return c;
    }
}
