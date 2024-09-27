--liquibase formatted sql

--changeset audryus:desafio-05 labels:pauta
--comment: alter table pauta
alter table pauta
add ts_criacao timestamp not null default CURRENT_TIMESTAMP;
