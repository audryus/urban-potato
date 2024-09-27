# Desafio tecnico da NT 

## Arquitetura
![Alt text](https://herbertograca.com/wp-content/uploads/2017/03/2008-onion-architecture5.png)

cotroller -> usecase

usecase -> domain serviceS
> usecase: Regras da aplicação

domain service -> domain repo
> domain Service: Regra do dominio

A ideia é que cada dominio possa ser separados em um "nano-serviço", pensando em `clustering`.

### Erros
Erro em regras irão retornar um JSON padrão:

```json
{
    "rpl": "ERR_"
}
```

Cada "regra" pode ter um status code diferente.

## Cadastrar nova pauta

> POST /api/pautas

Payload:
```json
{
    "nome": string // Nome da Pauta, usado para conseguir diferenciar
}
```
Response:
```json
{
    "id": string, // UUID
    "nome": string // Nome da pauta
}
```
Regras:
- Pauta sem nome: 
  - Response 
    - code: 400 
    - ERR_PAUTA_SEM_NOME

## Abrir sessão de votação em uma pauta

> POST /api/pautas/{pauta_id}/sessoes

Payload
```json
{
    "duracao": integer // Duração, padrão 1 minuto.
}
```
Response:
```json
{
    "id": string, // ID
    "pauta": string, // ID da pauta,
    "tsCriacao": string, // timestamp de criação,
    "tsFim": string // timestamp do fim da sessão
}
```
> Formato Timestamp: "2024-09-26T20:14:00.5643454"

Regras:
- Pauta não existe:
  - Response
    - Code 404
    - ERR_PAUTA_NAO_EXISTE
- Pauta já possui Sessão
  - Response
    - Code 406
    - ERR_PAUTA_POSSUI_SESSAO

### Cache
- Ao buscar a Pauta, para verificar a existencia.
- Ao consultar Sessões (a partir da Pauta), para inibir mais de uma Sessão na Pauta.

## Receber votos dos associados em pautas

> POST /api/sessoes/{sessao_id}/votos

- URL mudou, pois não preciso da Pauta, e caso precise obtenho-a pela Sessão enviada.

Payload
```json
{
    "associado": string, // CPF
    "voto": enum string ("Sim"|"Não") // Apenas o SIM é analisado, caso contrário é NÃO 
}
```

> Não possuo cadastro de Associado, poderia ser feito uma carga (migração, liquibase), todavia é mais produtivo, neste desafio, o `ID único` ser o `CPF`, na verdade pode ser qualquer `String`.

Response // Status code 201
```json
{
    "rpl": "RPL_VOTO"
}
```

Regras
- Sessão não existe
  - Response
    - Code 404
    - ERR_SESSAO_NAO_EXISTE
- Sessão já está encerrada (após tsFim)
  - Response
    - Code 412
    - ERR_SESSAO_ENCERRADA
- Voto já foi cadastrado
  - Response
    - 409
    - ERR_VOTO_CADASTRADO

## Contabilizar votos e resultado da pauta

> GET /api/pautas


## Execução
> docker compose up