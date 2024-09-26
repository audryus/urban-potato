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
    - rpl: ERR_PAUTA_SEM_NOME

## Abrir sessão de votação em uma pauta

> POST /api/pautas/{pauta_id}/sessoes

## Receber votos dos associados em pautas

> POST /api/pautas/{pauta_id}/sessoes/{sessao_id}/votos

## Contabilizar votos e resultado da pauta

> GET /api/pautas


## Execução
> docker compose up