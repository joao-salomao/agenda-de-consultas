-- Foi usado o Banco de dados PostgreSQL
CREATE TABLE paciente(
        id SERIAL PRIMARY KEY,
        nome VARCHAR,
        endereco VARCHAR,
        dataNascimento BIGINT,
        email VARCHAR,
        telefone VARCHAR(11)
);

CREATE TABLE consulta(
    id SERIAL PRIMARY KEY,
    pacienteId INTEGER,
    data BIGINT,
    hora VARCHAR,
    FOREIGN KEY(pacienteId) REFERENCES paciente(id)
);

CREATE TABLE consultorio(
    id SERIAL PRIMARY KEY,
    nome VARCHAR
);

CREATE TABLE agenda(
    id SERIAL PRIMARY KEY,
    diaSemana INTEGER,
    consultorioId INTEGER,
    horarioPrimeiraConsulta VARCHAR,
    horarioUltimaConsulta VARCHAR,
    intervaloConsulta VARCHAR,
    horarioInicioAlmoco VARCHAR,
    horarioFimAlmoco VARCHAR,
    FOREIGN KEY(consultorioId) REFERENCES consultorio(id)
);

CREATE TABLE agendamento(
    agendaId INTEGER,
    consultaId INTEGER,
    FOREIGN KEY(agendaId) REFERENCES agenda(id),
    FOREIGN KEY(consultaId) REFERENCES consulta(id),
    PRIMARY KEY(agendaId, consultaId)
);

