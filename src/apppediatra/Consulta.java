/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apppediatra;

import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author raizer
 */
public class Consulta {
    private int id;
    private Date data;
    private LocalTime hora;
    private Paciente paciente;

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Consulta(Date data, LocalTime hora) {
        this.data = data;
        this.hora = hora;
    }
    
    public Consulta(Date data, LocalTime hora, Paciente paciente) {
        this.data = data;
        this.hora = hora;
        this.paciente = paciente;
    }
    
    public Consulta(int id, Date data, LocalTime hora) {
        this.id = id;
        this.data = data;
        this.hora = hora;
    }
    
    public Consulta(int id, Date data, LocalTime hora, Paciente paciente) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.paciente = paciente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    public void marcarConsulta() {
        
    }
    
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}
