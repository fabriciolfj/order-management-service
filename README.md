# Order Manager

## Pré requisitos
- instale o lombok na sua idea
- docker e docker-compose
- acesse a raiz do projeto e suba os containers necessários
````
docker-compose up
````

## Validando se a app encontra-se saúdavél
- aplicação utiliza a porta 8080 por padrão
- para verificar se a mesma encontra-se up, abre seu browser e coloque a url abaixo
```
http://localhost:8080/actuator/health
```
- caso encontre a mensagem status up, está ok.

## Funcionamento básico
- Recebe-se eventos dos pedidos via rabbitmq 
- salva os dados na base relacional postgres
- afim de atender alto volume, está preparada pra processamentos em batch

## Arquitetura de código
- clean architecture
- code clean