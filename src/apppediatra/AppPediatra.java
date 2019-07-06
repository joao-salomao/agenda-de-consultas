/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apppediatra;

import apppediatra.DAO.AgendaDao;
import apppediatra.DAO.ConsultaDao;
import apppediatra.DAO.ConsultorioDao;
import apppediatra.DAO.PacienteDao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author raizer
 */
public class AppPediatra {    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<Paciente> pacientes = PacienteDao.all();
        ArrayList<Consultorio> consultorios = ConsultorioDao.all();
        ArrayList<Agenda> agendas = AgendaDao.all();
        
        Scanner input = new Scanner(System.in);
        String op = "";
        while(!op.equalsIgnoreCase("sair")) {
            System.out.println("1 - Cadastrar Paciente");
            System.out.println("2 - Cadastrar Agenda");
            System.out.println("3 - Cadastrar Consultorio");
            System.out.println("4 - Fazer Agendamento");
            System.out.println("5 - Listar pacientes");
            System.out.println("6 - Listar consultórios");
            System.out.println("7 - Listar Agendas");
            System.out.println("8 - Listar Consultas");
            op = input.next();
            
            switch(op) {
                case "1":
                    cadastrarPaciente();
                    pacientes = PacienteDao.all();
                    break;
                case "2":
                    cadastrarAgenda(consultorios, agendas);
                    agendas = AgendaDao.all();
                    break;
                case "3":
                    cadastrarConsultorio();
                    consultorios = ConsultorioDao.all();
                    break;
                case "4":
                    agendarConsulta(agendas, pacientes);
                    break;
                case "5":
                    listarPacientes(pacientes);
                    break;
                case "6":
                    listarConsultorios(consultorios);
                    break;
                case "7":
                    listarAgendas(agendas);
                    break;
                case "8":
                    listarConsultas(agendas);
                    break;
                default:
                    System.out.println("Opção Inválida");
                    break;
            }
        }
    }
    
    public static void listarConsultas(ArrayList<Agenda> agendas) {
        for(Agenda a : agendas ) {
            System.out.println("Clínica: "+a.getConsultorio().getNome()+" | Dia de Atendimento: "+diaDaSemana(a.getDiaSemana())+" | Consultas Marcadas: "+a.getNumConsultasMarcadas());
            for(Consulta c : a.getConsultas()) {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                String formatedDate = format.format(c.getData());
                
                System.out.println("Paciente: "+c.getPaciente().getNome()+" | Data: "+formatedDate+" | Hora: "+c.getHora());
            }
            System.out.println(" ");
        }
        System.out.println("-------------------------------");
        
    }
    
    public static String diaDaSemana(int diaint) {
        String dia = null;
        switch(diaint){
            case 0:
                dia = "Domingo";
                break;
            case 1:
                dia = "Segunda-Feira";
                break;
            case 2:
                dia = "Terça-Feira";
                break;
            case 3:
                dia = "Quarta-Feira";
                break;
            case 4:
                dia = "Quinta-Feira";
                break;
            case 5:
                dia = "Sexta-Feira";
                break;
            case 6:
                dia = "Sábado";
                break;
        }
        return dia;
    }
    
    public static void listarAgendas(ArrayList<Agenda> agendas) {
        System.out.println("----------------------------");
        for(Agenda a : agendas) {
            int numAgendamentosDisp = 3 - a.getNumConsultasMarcadas();
            System.out.println("Consultório: "+a.getConsultorio().getNome()+" | Dia da Semana: "+diaDaSemana(a.getDiaSemana())+" | Agendamentos disponíveis: "+numAgendamentosDisp);
            System.out.println("Horário primeira consulta: "+a.getHorarioPrimeiraConsulta()+" | Horário última consulta: "+a.getHorarioUltimaConsulta()+" | Intervalo entre as consultas: "+a.getIntervaloConsulta());
            System.out.println("Início do horário de almoço: "+a.getHorarioInicioAlmoco()+" | Fim do horário de almoço: "+a.getHorarioFimAlmoco());
            System.out.println(" ");
        }
        System.out.println("----------------------------");
    }
    
    public static void listarConsultorios(ArrayList<Consultorio> consultorios) {
        if(!consultorios.isEmpty()) {
            System.out.println("----------------------------");
            consultorios.forEach((c) -> {
                System.out.println(c.getNome());
            });
            System.out.println("----------------------------");
        } else {
            System.out.println("Não há consultórios cadastrados.");
            System.out.println("    ");
        }
        
    }
    
    public static void listarPacientes(ArrayList<Paciente> pacientes) {
        if(!pacientes.isEmpty()) {
            System.out.println("----------------------------");
            pacientes.forEach((p) ->{
                System.out.println(p.getNome());
            });
            System.out.println("----------------------------");
        } else {
            System.out.println("Não existem pacientes cadastrados.");
            System.out.println("    ");
        }
    }
    
    public static void agendarConsulta(ArrayList<Agenda> agendas, ArrayList<Paciente> pacientes) {
        if(agendas.isEmpty()) {
            System.out.println("Não há agendas cadastradas no sistem.");
            System.out.println("    ");
            return;
        }
        if(pacientes.isEmpty()) {
            System.out.println("Não há pacientes cadastrados no sistema.");
            System.out.println("    ");
            return;
        }
        
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Qual o dia da semana ? (0 ~ 6)");
            int diaSemana = input.nextInt();
            
            Agenda agenda = null;
            for(Agenda g : agendas) {
                if(g.getDiaSemana() == diaSemana) {
                    agenda = g;
                }
            }
            if(agenda == null) { 
                System.out.println("Não haverá atendimento nesse dia");
                return;
            }            
            
            System.out.println("Qual o paciente ?");
            for(Paciente p : pacientes) {
                System.out.println(p.getNome());
            }
            String nomePaciente = input.next();
            Paciente paciente = null;
            for(Paciente p : pacientes) {
                if(p.getNome().equalsIgnoreCase(nomePaciente)) {
                    paciente = p;
                }
            }
            if(paciente == null) { 
                System.out.println("Paciente não encontrado"); 
                return;
            }

            System.out.println("Digite a data desejada (DD-MM-YYYY)");
            Date data = new SimpleDateFormat("dd-MM-yyyy").parse(input.next());
            
            System.out.println("Digite a hora do atendimento (HH:MM)");
            LocalTime hora = LocalTime.parse(input.next());
            
            Consulta consulta = new Consulta(data, hora, paciente);
            boolean podeAgendar = agenda.podeAgendar(consulta);
            if(!podeAgendar) { 
                return;
            }
            ConsultaDao.add(consulta);
            agenda.addConsulta(consulta);
            AgendaDao.agendarConsulta(agenda, consulta);
        } catch(ParseException e ) {
            System.out.println(e);
        }
    }
    
    public static void cadastrarConsultorio() {
        try {
            System.out.println("Digite o nome do escritório");
            Scanner input = new Scanner(System.in);
            String nome = input.next();
            
            Consultorio c = new Consultorio(nome);
            ConsultorioDao.add(c);
            
            System.out.println("Consultório cadastrado com sucesso");
            System.out.println("-------------------------------------");
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    
    public static void cadastrarAgenda(ArrayList<Consultorio> consultorios, ArrayList<Agenda> agendas) {
        if(consultorios.isEmpty()) {
            System.out.println("É necessário ter pelo menos um consultório cadastrado para cadastrar uma agenda.");
            return;
        }
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Qual o dia da semana (0 ~ 6)");
            int diaSemana = input.nextInt();
            
            
            for(Agenda a : agendas) {
                if(a.getDiaSemana() == diaSemana) {
                    System.out.println("Já existe uma agenda cadastrada para atender nesse dia");
                    return;
                }
            }

            System.out.println("Qual o consultório ?");
            consultorios.forEach((c) -> {
                System.out.println(c.getNome());
            });
            String nomeConsultorio = input.next();

            Consultorio consultorio = null;
            boolean consultorioEncontrado = false;
            for(Consultorio con : consultorios) {
                if(con.getNome().equalsIgnoreCase(nomeConsultorio)) {
                    consultorio = con;
                    consultorioEncontrado = true;
                }
            }
            if(!consultorioEncontrado) {
                System.out.println("Consultório não encontrado");
                return;
            }

            System.out.println("Qual o intervalo entre as consultas ? (HH:MM)");
            LocalTime intervaloConsulta = LocalTime.parse(input.next());

            System.out.println("Qual o horário que inicia o período de almoço ? (HH:MM)");
            LocalTime horarioInicioAlmoco = LocalTime.parse(input.next());

            System.out.println("Qual o horário que finaliza o período de almoço ? (HH:MM)");
            LocalTime horarioFimAlmoco = LocalTime.parse(input.next());
            
            Agenda agenda = new Agenda(diaSemana, consultorio, intervaloConsulta, horarioInicioAlmoco, horarioFimAlmoco);
            
            AgendaDao.addWithoutConsultas(agenda);
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    
    public static void cadastrarPaciente() {
        try { 
            String nome;
            String endereco;
            Date dataNascimento;
            String email;
            String telefone;
            Scanner input = new Scanner(System.in);


            System.out.println("Digite o nome do paciente");
            nome = input.next();

            System.out.println("Digite o endereço");
            endereco = input.next();

            System.out.println("Digite a data de nascimento do paciente (DD-MM-YYYY)");
            dataNascimento = new SimpleDateFormat("dd-MM-yyyy").parse(input.next());

            System.out.println("Digite o e-mail do paciente");
            email = input.next();    

            System.out.println("Digite o telefone do paciente");
            telefone = input.next();


            Paciente p = new Paciente(nome, endereco, dataNascimento, email, telefone);
            PacienteDao.add(p);
            System.out.println("Paciente cadastrado com sucesso");
        } catch(ParseException e) {
            System.out.println(e);
        }
    }
}
