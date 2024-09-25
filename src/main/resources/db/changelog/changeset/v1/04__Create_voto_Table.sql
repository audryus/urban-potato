--liquibase formatted sql

--changeset audryus:desafio-04 labels:voto
--comment: create table voto
create table voto(
    sessao varchar(36) not null,
    associado varchar(11) not null,
    ts_criacao timestamp not null,
    voto varchar(1) not null,
    PRIMARY KEY (sessao, associado),
    constraint fk_sessao foreign key (sessao) references sessao(id),
    constraint fk_associado foreign key (associado) references associado(id)
);

create index idx_sessao on voto(sessao);
create index idx_associado on voto(associado);

