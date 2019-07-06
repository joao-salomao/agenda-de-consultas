/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apppediatra;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author raizer
 */
public class Agenda {
    private int id;
    private int diaSemana;
    private Consultorio consultorio;
    private LocalTime horarioPrimeiraConsulta;
    private LocalTime horarioUltimaConsulta;
    private LocalTime intervaloConsulta;
    private LocalTime horarioInicioAlmoco;
    private LocalTime horarioFimAlmoco;
    private ArrayList<Consulta> consultas;
    private int numConsultasMarcadas = 0;

    public int getNumConsultasMarcadas() {
        return numConsultasMarcadas;
    }

    public void setNumConsultasMarcadas(int numConsultasMarcadas) {
        this.numConsultasMarcadas = numConsultasMarcadas;
    }
    
    public boolean podeAgendar(Consulta consulta) {
        if(this.numConsultasMarcadas >= 3) {
            System.out.println("O número máximo de agendamentos por dia é 3");
            return false;
        }
        
        if(consulta.getHora().isAfter(horarioInicioAlmoco) && consulta.getHora().isBefore(horarioFimAlmoco)) {
            System.out.println("Horário da consulta conflitante com o horário de almoço");
            return false;
        }
        
        int diaDaSemana = consulta.getData().getDay();
        if(diaDaSemana  == 0 || diaDaSemana == 6) {
            System.out.println("Não é possível fazer agendamentos para o final de semana");
            return false;
        }
        
        for(Consulta c : this.consultas) {
            if(c.getData().compareTo(consulta.getData()) == 0) {
                if(c.getHora().getHour() == consulta.getHora().getHour()) {
                    if(c.getHora().getMinute() == consulta.getHora().getMinute()) {
                        System.out.println("Já existe uma consulta agendada nesse horário.");
                        return false;
                    }
                    int oldMinuteConsulta = c.getHora().getMinute();
                    int newMinuteConsulta = c.getHora().getMinute();
                    int diferenca;
                    if(oldMinuteConsulta >= newMinuteConsulta) {
                        diferenca = oldMinuteConsulta - newMinuteConsulta;
                        if(diferenca < this.intervaloConsulta.getMinute()) {
                            System.out.println("O horário da consulta é conflitante com outra já agendada.");
                            return false;
                        } 
                    } else {
                        diferenca = newMinuteConsulta - oldMinuteConsulta;
                        if(diferenca < this.intervaloConsulta.getMinute()) {
                            System.out.println("O horário da consulta é conflitante com outra já agendada.");
                            return false;
                        }
                    }
                }   
            }
        }
        return true;
    }

    public Agenda(int diaSemana, Consultorio consultorio, LocalTime intervaloConsulta, LocalTime horarioInicioAlmoco, LocalTime horarioFimAlmoco) {
        this.diaSemana = diaSemana;
        this.consultorio = consultorio;
        this.intervaloConsulta = intervaloConsulta;
        this.horarioInicioAlmoco = horarioInicioAlmoco;
        this.horarioFimAlmoco = horarioFimAlmoco;
        this.consultas = new ArrayList<>();
    }
    public Agenda(int id, int diaSemana, Consultorio consultorio, LocalTime horarioPrimeiraConsulta, LocalTime horarioUltimaConsulta,
               LocalTime intervaloConsulta, LocalTime horarioInicioAlmoco, LocalTime horarioFimAlmoco) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.consultorio = consultorio;
        this.horarioPrimeiraConsulta = horarioPrimeiraConsulta;
        this.horarioUltimaConsulta = horarioUltimaConsulta;
        this.intervaloConsulta = intervaloConsulta;
        this.horarioInicioAlmoco = horarioInicioAlmoco;
        this.horarioFimAlmoco = horarioFimAlmoco;
        this.horarioPrimeiraConsulta = null;
        this.horarioUltimaConsulta = null;
        this.consultas = new ArrayList<>();
    }
    
    public void addConsulta(Consulta consulta) {
        if(this.horarioPrimeiraConsulta == null) {
           this.horarioPrimeiraConsulta = consulta.getHora();
        } else {
            boolean isTheFirst = false;
            for(Consulta c : this.consultas) {
                if(consulta.getHora().isBefore(c.getHora())) {
                    isTheFirst = true;
                } else {
                    isTheFirst = false;
                    break;
                }
            }
            if(isTheFirst) {
                this.horarioPrimeiraConsulta = consulta.getHora();
            }
        }
        
        if(this.horarioUltimaConsulta == null) {
            this.horarioUltimaConsulta = consulta.getHora();
        } else {
            boolean isTheLast = false;
            for(Consulta c : this.consultas) {
                if(consulta.getHora().isAfter(c.getHora())) {
                    isTheLast = true;
                } else {
                    isTheLast = false;
                    break;
                }
            }
            if(isTheLast) {
                this.horarioUltimaConsulta = consulta.getHora();
            }
        }
        
        if(this.numConsultasMarcadas == 2) {
            this.horarioUltimaConsulta = consulta.getHora();
        }
        this.numConsultasMarcadas++;
        this.consultas.add(consulta);
    }
    
    public ArrayList<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(ArrayList<Consulta> consultas) {
        this.consultas = consultas;
    }

    public Agenda(int diaSemana, Consultorio consultorio, LocalTime horarioPrimeiraConsulta, LocalTime horarioUltimaConsulta, LocalTime intervaloConsulta, LocalTime horarioInicioAlmoco, LocalTime horarioFimAlmoco) {
        this.diaSemana = diaSemana;
        this.consultorio = consultorio;
        this.horarioPrimeiraConsulta = horarioPrimeiraConsulta;
        this.horarioUltimaConsulta = horarioUltimaConsulta;
        this.intervaloConsulta = intervaloConsulta;
        this.horarioInicioAlmoco = horarioInicioAlmoco;
        this.horarioFimAlmoco = horarioFimAlmoco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }    

    public int getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHorarioPrimeiraConsulta() {
        return horarioPrimeiraConsulta;
    }

    public void setHorarioPrimeiraConsulta(LocalTime horarioPrimeiraConsulta) {
        this.horarioPrimeiraConsulta = horarioPrimeiraConsulta;
    }

    public LocalTime getHorarioUltimaConsulta() {
        return horarioUltimaConsulta;
    }

    public void setHorarioUltimaConsulta(LocalTime horarioUltimaConsulta) {
        this.horarioUltimaConsulta = horarioUltimaConsulta;
    }

    public LocalTime getIntervaloConsulta() {
        return intervaloConsulta;
    }

    public void setIntervaloConsulta(LocalTime intervaloConsulta) {
        this.intervaloConsulta = intervaloConsulta;
    }

    public LocalTime getHorarioInicioAlmoco() {
        return horarioInicioAlmoco;
    }

    public void setHorarioInicioAlmoco(LocalTime horarioInicioAlmoco) {
        this.horarioInicioAlmoco = horarioInicioAlmoco;
    }

    public LocalTime getHorarioFimAlmoco() {
        return horarioFimAlmoco;
    }

    public void setHorarioFimAlmoco(LocalTime horarioFimAlmoco) {
        this.horarioFimAlmoco = horarioFimAlmoco;
    }
}
