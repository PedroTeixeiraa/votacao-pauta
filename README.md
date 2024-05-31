# Sistema de Gerenciamento de Votações para Cooperativas

## Introdução

Este README descreve a solução para um sistema de gerenciamento de votações para cooperativas. O objetivo é permitir que os associados participem de sessões de votação por meio de dispositivos móveis. A solução é baseada em uma API REST desenvolvida em Java, utilizando o framework Spring Boot. O sistema permite o cadastro de pautas, abertura de sessões de votação, recebimento de votos dos associados e contabilização dos votos.

## Funcionalidades

O sistema oferece as seguintes funcionalidades:

- Cadastro de uma nova pauta.
- Abertura de uma sessão de votação em uma pauta, com um tempo determinado (ou 1 minuto por padrão).
- Recebimento de votos dos associados em pautas (votos 'Sim'/'Não', associados identificados por um ID único, permitindo apenas um voto por associado por pauta).
- Contabilização dos votos e apresentação do resultado da votação na pauta.

## Execução da Aplicação

Para executar a aplicação, siga os seguintes passos:

1. Clone o repositório do projeto.
2. Certifique-se de ter o Docker, Docker Compose e o JDK 17 instalados em sua máquina.
3. No terminal, navegue até o diretório do projeto onde se encontra o arquivo `docker-compose.yml`.
4. Execute o comando `docker-compose up` para iniciar a aplicação.
5. Aguarde até que todos os serviços estejam em execução.
6. A aplicação estará disponível nos endpoints especificados.

## Tecnologias e Ferramentas Utilizadas

A solução foi desenvolvida utilizando as seguintes tecnologias e ferramentas:

- **Spring Boot**: Framework utilizado para o desenvolvimento da aplicação.
- **Spring WebFlux**: Módulo do Spring Framework para construção de aplicativos web reativos.
- **Spring Data R2DBC**: Biblioteca para acesso a dados reativos utilizando o R2DBC (Reactive Relational Database Connectivity).
- **Spring Validation**: Biblioteca para validação de dados no Spring Framework.
- **R2DBC PostgreSQL**: Driver para integração reativa com o PostgreSQL.
- **Flyway**: Ferramenta para versionamento e migração de banco de dados.
- **PostgreSQL**: Banco de dados relacional utilizado para persistência dos dados.
- **Lombok**: Biblioteca para redução de código boilerplate em classes Java.
- **Spring Boot Starter Test**: Dependência para testes no Spring Boot.
- **Reactor Test**: Biblioteca de teste para programas reativos baseados no Reactor.
- **Springdoc OpenAPI**: Ferramenta para geração de documentação OpenAPI (anteriormente conhecida como Swagger) para aplicativos Spring WebFlux.
- **SLF4J API**: API de logging para Java.
- **Logback Classic**: Implementação de logging para o SLF4J.

## Principais Endpoints da Aplicação

A seguir estão listados os principais endpoints da aplicação:

- **POST http://localhost:8080/v1/votacao**: Endpoint para votar em uma sessão relacionada a uma pauta.
- **GET http://localhost:8080/v1/resultado/{pautaId}**: Endpoint para obter o resultado da votação de uma pauta específica.
- **POST http://localhost:8080/v1/sessao-votacao/iniciar**: Endpoint para iniciar uma sessão de votação.
- **POST http://localhost:8080/v1/pauta**: Endpoint para salvar uma nova pauta.

## Documentação da API

A documentação da API pode ser acessada através do Swagger, disponível em: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
![image](https://github.com/PedroTeixeiraa/votacao-pauta/assets/54821438/3f869368-5e6f-46e2-bacd-cdd4acbb76e9)
