--liquibase formatted sql

--changeset audryus:desafio-03 labels:sessao
--comment: create table sessao
create table sessao(
    id varchar(36) primary key,
    pauta varchar(36) not null,
    ts_criacao timestamp not null,
    ts_fim timestamp not null,
    constraint fk_pauta foreign key (pauta) references pauta(id)
);

create index idx_pauta on sessao(pauta);
