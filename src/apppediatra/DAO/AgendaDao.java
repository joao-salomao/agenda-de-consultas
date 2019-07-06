/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apppediatra.DAO;

import apppediatra.Agenda;
import apppediatra.Consulta;
import apppediatra.Consultorio;
import apppediatra.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author raizer
 */
public class AgendaDao {
    static Connection con = Conexao.getConexao();
    
    public static ArrayList<Agenda> all() {
        ArrayList<Agenda> agendas = new ArrayList<>();
        try {
            String sqlAgendas = "SELECT * FROM agenda";
            Statement stAgendas = con.createStatement();
            ResultSet rsAgendas = stAgendas.executeQuery(sqlAgendas);
            while(rsAgendas.next()) {
                int id = rsAgendas.getInt("id");
                int diaSemana = rsAgendas.getInt("diaSemana");
                Consultorio consultorio = ConsultorioDao.getConsultorioAgenda(rsAgendas.getInt("consultorioId"));
                LocalTime horarioPrimeiraConsulta = null;
                if(rsAgendas.getString("horarioPrimeiraConsulta") != null) {
                    System.out.println(rsAgendas.getString("horarioPrimeiraConsulta"));
                    horarioPrimeiraConsulta = LocalTime.parse(rsAgendas.getString("horarioPrimeiraConsulta"));
                }
                
                LocalTime horarioUltimaConsulta = null;
                if(rsAgendas.getString("horarioUltimaConsulta") != null) {
                    horarioPrimeiraConsulta = LocalTime.parse(rsAgendas.getString("horarioUltimaConsulta"));
                }
                
                LocalTime intervaloConsulta = LocalTime.parse(rsAgendas.getString("intervaloConsulta"));
                LocalTime horarioInicioAlmoco = LocalTime.parse(rsAgendas.getString("horarioInicioAlmoco"));
                LocalTime horarioFimAlmoco = LocalTime.parse(rsAgendas.getString("horarioFimAlmoco"));
                
                Agenda agenda = new Agenda(id, diaSemana, consultorio, horarioPrimeiraConsulta, horarioUltimaConsulta,
                intervaloConsulta, horarioInicioAlmoco, horarioFimAlmoco);
                
                String sqlConsultas = "SELECT * FROM consulta c INNER JOIN agendamento a ON c.id = a.consultaId WHERE agendaId = "+id;
                Statement stConsultas = con.createStatement();
                ResultSet rsConsultas = stConsultas.executeQuery(sqlConsultas);
                while(rsConsultas.next()) {
                    int consultaId = rsConsultas.getInt("id");
                    Date data = new Date(rsConsultas.getLong("data"));
                    LocalTime hora = LocalTime.parse(rsConsultas.getString("hora"));
                    Paciente paciente = PacienteDao.getOne(rsConsultas.getInt("pacienteId"));
                    
                    Consulta consulta = new Consulta(consultaId, data, hora, paciente);
                    agenda.addConsulta(consulta);
                }
                agendas.add(agenda);
            }
        } catch(SQLException e) {
            System.out.println(e);
        }        
        return agendas;
    }
    
    public static void agendarConsulta(Agenda agenda, Consulta consulta) {
        try {
            con.setAutoCommit(false);
            String sql = "INSERT INTO agendamento VALUES(?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, agenda.getId());
            st.setInt(2, consulta.getId());
            
            st.executeUpdate();
            agenda.addConsulta(consulta);
            con.commit();
        } catch(SQLException e) {
            try {
                con.rollback();
                System.out.println(e);
            } catch(Exception d) {
                System.out.println(d);
            }
        }
    }
    
    public static void add(Agenda agenda) {   
        // FALTA CADASTRAR AS CONSULTAS DA AGENDA NO BANCO Consultas consultas
        try {
            int diaSemana = agenda.getDiaSemana();
            int consultorioId = agenda.getConsultorio().getId();
            String horarioPrimeiraConsulta = agenda.getHorarioPrimeiraConsulta().toString();
            String horarioUltimaConsulta = agenda.getHorarioUltimaConsulta().toString();
            String intervaloConsulta = agenda.getIntervaloConsulta().toString();
            String horarioInicioAlmoco = agenda.getHorarioInicioAlmoco().toString();
            String horarioFimAlmoco = agenda.getHorarioFimAlmoco().toString();
            
            String sql = "INSERT INTO agenda(diaSemana, consultorioId, horarioPrimeiraConsulta, horarioUltimaConsulta, intervaloConsulta, horarioInicioAlmoco, horarioFimAlmoco)"
                    + " VALUES(?,?,?,?,?,?)";
            String returnColumn[] = {"id"};
            
            PreparedStatement st = con.prepareStatement(sql, returnColumn);
            st.setInt(1, diaSemana);
            st.setInt(2, consultorioId);
            st.setString(3, horarioPrimeiraConsulta);
            st.setString(4, horarioUltimaConsulta);
            st.setString(5, intervaloConsulta);
            st.setString(6, horarioInicioAlmoco);
            st.setString(7, horarioFimAlmoco);
            
            st.executeUpdate();
            
            ResultSet rs;
            rs = st.getGeneratedKeys();
            rs.next();
            agenda.setId(rs.getInt("id"));
        } catch(SQLException e) {
            System.out.println(e);
        }
    }
    // Insere uma agenda sem consultas no banco;
    public static void addWithoutConsultas(Agenda agenda) {
        try {
            int diaSemana = agenda.getDiaSemana();
            int consultorioId = agenda.getConsultorio().getId();
            String intervaloConsulta = agenda.getIntervaloConsulta().toString();
            String horarioInicioAlmoco = agenda.getHorarioInicioAlmoco().toString();
            String horarioFimAlmoco = agenda.getHorarioFimAlmoco().toString();
            
            String sql = "INSERT INTO agenda(diaSemana, consultorioId, intervaloConsulta, horarioInicioAlmoco, horarioFimAlmoco)"
                    + " VALUES(?,?,?,?,?)";
            String returnColumn[] = {"id"};
            
            PreparedStatement st = con.prepareStatement(sql, returnColumn);
            st.setInt(1, diaSemana);
            st.setInt(2, consultorioId);
            st.setString(3, intervaloConsulta);
            st.setString(4, horarioInicioAlmoco);
            st.setString(5, horarioFimAlmoco);
            
            st.executeUpdate();
            
            ResultSet rs;
            rs = st.getGeneratedKeys();
            rs.next();
            agenda.setId(rs.getInt("id"));
        } catch(SQLException e) {
            System.out.println(e);
        }
    }
}