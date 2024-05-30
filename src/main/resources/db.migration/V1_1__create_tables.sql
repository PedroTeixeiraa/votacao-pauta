CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE pauta
(
    ID uuid PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    TITULO varchar(30) NOT NULL,
    DATA_CRIACAO TIMESTAMP NOT NULL
);

CREATE TABLE sessao_votacao
(
    ID uuid PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    PAUTA_ID uuid NOT NULL CONSTRAINT pauta_foreign_key REFERENCES pauta (ID),
    INICIO TIMESTAMP NOT NULL,
    FIM TIMESTAMP NOT NULL,
    DURACAO INTEGER
);

CREATE TABLE voto
(
    ID uuid PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    SESSAO_VOTACAO_ID uuid NOT NULL CONSTRAINT sessao_votacao_foreign_key REFERENCES sessao_votacao (ID),
    CPF VARCHAR(11) NOT NULL,
    VOTO  BOOLEAN NOT NULL,
    DATA_VOTO  TIMESTAMP NOT NULL
);