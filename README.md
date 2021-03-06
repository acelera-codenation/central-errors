# Central de Erros

![Java CI with Gradle](https://github.com/acelera-codenation/erros-center/workflows/Java%20CI%20with%20Gradle/badge.svg)

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=acelera-codenation_erros-center&branch=master)](https://sonarcloud.io/dashboard?id=acelera-codenation_erros-center)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=acelera-codenation_erros-center&metric=coverage)](https://sonarcloud.io/dashboard?id=acelera-codenation_erros-center)

[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=acelera-codenation_erros-center&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=acelera-codenation_erros-center)

## Objetivo

Em projetos modernos é cada vez mais comum o uso de arquiteturas baseadas em serviços ou microsserviços. Nestes ambientes complexos, erros podem surgir em diferentes camadas da aplicação (backend, frontend, mobile, desktop) e mesmo em serviços distintos. Desta forma, é muito importante que os desenvolvedores possam centralizar todos os registros de erros em um local, de onde podem monitorar e tomar decisões mais acertadas. Neste projeto vamos implementar uma API Rest para centralizar registros de erros de aplicações.

Abaixo estão os requisitos desta API, o time terá total liberdade para tomar as decisões técnicas e de arquitetura da API, desde que atendam os requisitos abaixo.

## API

- Tecnologia
    
    Utilizar a tecnologia base da aceleração para o desenvolvimento (Exemplo: Java, Node.js)
- Premissas
    
    A API deve ser pensada para atender diretamente um front-end
    Deve ser capaz de gravar os logs de erro em um banco de dados relacional
    O acesso a ela deve ser permitido apenas por requisições que utilizem um token de acesso válido

- Funcionalidades
    
    - Deve permitir a autenticação do sistema que deseja utilizar a API gerando o Token de Acesso
    - Pode ser acessado por multiplos sistemas
    - Deve permitir gravar registros de eventos de log salvando informações de Level(error, warning, info), Descrição do Evento, LOG do Evento, ORIGEM(Sistema ou Serviço que originou o evento), DATA(Data do evento), QUANTIDADE(Quantidade de Eventos de mesmo tipo)
    - Deve permitir a listagem dos eventos juntamente com a filtragem de eventos por qualquer parâmetro especificado acima
    - Deve suportar Paginação
    - Deve suportar Ordenação por diferentes tipos de atributos
    - A consulta de listagem não deve retornar os LOGs dos Eventos
    - Deve permitir a busca de um evento por um ID, dessa maneira exibindo o LOG desse evento em específico
    
    
## Links

 - [Demo - Api Central de Erros](https://centralerrors.herokuapp.com) 
 - [Sonar (_Métricas_)](https://sonarcloud.io/dashboard?id=acelera-codenation_erros-center)
 - [Gerenciamento das Tarefas - Board](https://github.com/orgs/acelera-codenation/projects/1) 
 - [Spring Admin](https://central-admin.herokuapp.com/applications)
 - [Postman](https://samuelsantos-dev.postman.co/collections/1130955-57bc8d31-fda0-4ae3-b7e3-3080ed91fc75?version=latest&workspace=ba1f86a7-4d07-4597-bcc3-b23c3c58956a)
 - [Slides da Apresentação](./docs/index.html)

![Alt Text](./docs/demo.png)