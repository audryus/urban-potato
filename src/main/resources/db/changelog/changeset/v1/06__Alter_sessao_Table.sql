--liquibase formatted sql

--changeset audryus:desafio-06 labels:sessao runOnChange:true
--comment: alter table sessao
alter table sessao
add notificado boolean default false;
