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
Erro oriundos de regras irão retornar um JSON padrão:

```json
{
    "rpl": "ERR_*"
}
```

Cada "regra" pode ter um status code diferente.
Este retorno pode ser usado pelo `client` para fazer algum tratamento adequado.

### Qualidade
Durante o desenvolvimento, na IDE, foi utilizado o SonarLint.

Utilizado o JaCoCo na camada de regra de negocio (usecase).

## Execução
> docker compose up

## Cadastrar nova pauta

> POST /api/pautas

#### Payload:
```json
{
    "nome": string // Nome da Pauta, usado para conseguir diferenciar
}
```
#### Response:
```json
{
    "id": string, // UUID
    "nome": string, // Nome da pauta
    "tsCriacao": string -> "2024-09-26 22:07",
    "sessao": null // Indica que não há Sessão
}
```
#### Regras:
- Pauta sem nome: 
  - Response 
    - code: 400 
    - ERR_PAUTA_SEM_NOME

## Abrir sessão de votação em uma pauta

> POST /api/pautas/{pauta_id}/sessoes

#### Payload
```json
{
    "duracao": integer // Duração, padrão 1 minuto.
}
```
#### Response:
```json
{
    "id": string, // ID
    "pauta": string, // ID da pauta,
    "tsCriacao": string, // timestamp de criação,
    "tsFim": string, // timestamp do fim da sessão
    "votos": null // Indica que não há votos na Sessão
}
```
> Formato Timestamp: "2024-09-26 20:14"

#### Regras:
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

#### Payload
```json
{
    "associado": string, // CPF
    "voto": enum string ("Sim"|"Não") // Apenas o SIM é analisado, caso contrário é NÃO 
}
```

> Não possuo cadastro de Associado, poderia ser feito uma carga (migração, liquibase), todavia é mais produtivo, neste desafio, o `ID único` ser o `CPF`, na verdade pode ser qualquer `String`.

> Caso o `CPF` não exista, será criado um Associado com o valor informado.

#### Response // Status code 201
```json
{
    "rpl": "RPL_VOTO"
}
```

#### Regras
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
#### Response
```json
[
    {
        "id": string,
        "pauta": string,
        "tsCriacao": string -> "2024-09-26 21:47",
        "sessao": {
            "id": string,
            "pauta": string (id pauta),
            "tsCriacao": string -> "2024-09-26 21:47",
            "tsFim": string -> "2024-09-26 22:07",
            "votos": [
                {
                    "escolha": string -> "Sim"|"Não",
                    "total": integer
                }
            ]
        }
    }
]
```

## Tarefas bônus

### Integração de sistemas externos
Infelizmente a API fornecida não se encontra disponivel.

### Mensageria e filas
> Normalmente para "perceber" o encerramento, eu usaria o TTL no Redis. Neste desafio irei fazer um Scheduler.

### Versionamento de API

#### URI Path
- Minha escolha de versionamento, devido a facilidade e a explicidade. Cria-se um novo "ponto de acesso", que passa a ser utilizado. Usando feature flag pode-se alterar o "component" (strategy pattern).

#### Query parameters
- Menos esforço de alteração. Todavia haveria alteração em algo que já existe, dependendo da alteração, criaria mais problemas.

#### Custom Headers
- Requer adição de um client especifico quando quer adicionar uma nova versão.

#### Content Negotiation
- Requer adição de um client especifico quando quer adicionar uma nova versão.

## Performance
- Novo endpoit de votos 
  - Os dois endpoints deverão funcionar em conjunto
    - Essa escolha é para caso precise dar rollback da solução.
  - Irá seguir o conceito de optimistic.
  - O request será aceito, e entendido como sucesso.
- Utilizado o K6 para teste de performance:

### Endpoint antigo de votos
```
     http_req_blocked...............: avg=107.02µs min=0s     med=0s      `max=4.22ms`  p(90)=0s       p(95)=0s
     http_req_connecting............: avg=22.75µs  min=0s     med=0s      max=1.28ms  p(90)=0s       p(95)=0s
     http_req_duration..............: avg=14.14ms  min=3.64ms med=11.19ms `max=86.74ms` p(90)=19.19ms  p(95)=26.11ms
       { expected_response:true }...: avg=14.14ms  min=3.64ms med=11.19ms max=86.74ms p(90)=19.19ms  p(95)=26.11ms
     http_req_failed................: 0.00%  ✓ 0        ✗ 3000
     http_req_receiving.............: avg=76.15µs  min=0s     med=0s      max=7.75ms  p(90)=504.92µs p(95)=526.3µs
     http_req_sending...............: avg=17.03µs  min=0s     med=0s      max=4.72ms  p(90)=0s       p(95)=0s
     http_req_tls_handshaking.......: avg=0s       min=0s     med=0s      max=0s      p(90)=0s       p(95)=0s
     http_req_waiting...............: avg=14.05ms  min=3.64ms med=11.11ms max=86.16ms p(90)=18.99ms  p(95)=26.11ms
     http_reqs......................: 3000   98.03489/s
     iteration_duration.............: avg=1.01s    min=1s     med=1.01s   max=1.09s   p(90)=1.02s    p(95)=1.03s
     iterations.....................: 3000   98.03489/s
     vus............................: 100    min=100    max=100
     vus_max........................: 100    min=100    max=100

```
### Novo endpoint

```
     http_req_blocked...............: avg=141.62µs min=0s med=0s     `max=5.72ms`  p(90)=0s       p(95)=0s
     http_req_connecting............: avg=35.61µs  min=0s med=0s     max=2.84ms  p(90)=0s       p(95)=0s
     http_req_duration..............: avg=4.43ms   min=0s med=3.81ms `max=17.19ms` p(90)=6.74ms   p(95)=9.33ms
       { expected_response:true }...: avg=4.43ms   min=0s med=3.81ms max=17.19ms p(90)=6.74ms   p(95)=9.33ms
     http_req_failed................: 0.00%  ✓ 0         ✗ 3000
     http_req_receiving.............: avg=195.15µs min=0s med=0s     max=6.31ms  p(90)=625.91µs p(95)=1.06ms
     http_req_sending...............: avg=55.66µs  min=0s med=0s     max=7.97ms  p(90)=0s       p(95)=293.7µs
     http_req_tls_handshaking.......: avg=0s       min=0s med=0s     max=0s      p(90)=0s       p(95)=0s
     http_req_waiting...............: avg=4.17ms   min=0s med=3.61ms max=16.47ms p(90)=6.42ms   p(95)=8.17ms
     http_reqs......................: 3000   98.682749/s
     iteration_duration.............: avg=1.01s    min=1s med=1.01s  max=1.02s   p(90)=1.01s    p(95)=1.01s
     iterations.....................: 3000   98.682749/s
     vus............................: 100    min=100     max=100
     vus_max........................: 100    min=100     max=100
```

### OpenApi Spec
> Não foi feito, devido a tamanha complexidade que é aquele YAML.

### Swagger da API
> API pública não deveria ter Swagger, por questões de segurança.
