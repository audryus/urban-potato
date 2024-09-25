--liquibase formatted sql

--changeset audryus:desafio-01 labels:associado
--comment: create table associado
create table associado(
    cpf varchar(11) primary key
);
