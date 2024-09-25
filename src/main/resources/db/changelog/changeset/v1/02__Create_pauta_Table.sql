--liquibase formatted sql

--changeset audryus:desafio-02 labels:pauta
--comment: create table pauta
create table pauta(
    id varchar(36) primary key,
    nome varchar(120) not null
);
