# Projeto 101 (Backend em Java/Spring Boot)

Implementação do Backend do projeto [jeanbarcellos/project-101.front.reactjs](https://github.com/jeanbarcellos/project-101.front.reactjs)

<br>

## Pré-requisitos

- Java 11
- Gradle 7.3.3
- Spring Boot 2.6.3

<br>

## Executar o Projeto

Comando:

```bash
 ./gradlew bootRun
```

<br>

## Documentação da API

Acesse o Swagger:

http://localhost:8101/swagger-ui/index.html

<br>

## Instruções

**Perfils/Roles:**

| Nome    | Descrição                                                                                |
| ------- | ---------------------------------------------------------------------------------------- |
| root    | Perfil do desenvolvedor/empresa. gerentia TUDO, configurações, roles e utilitários.      |
| admin   | Inclui, exclui, atualiza e visualiza usuários, perfils e outras funções administrativas. |
| default | Usuário padrão utilizador do sistema                                                     |

**Usuários**

| Login                | Senha     | Perfil  |
| -------------------- | --------- | ------- |
| root@teste.com       | teste@123 | root    |
| admin001@teste.com   | teste@123 | admin   |
| usuario001@teste.com | teste@123 | default |

<br>

## Gerar build e imagem Docker

Criar rede:

```bash
docker network create project101-net
```

Gerar o pacote

```bash
mvn clean package -DskipTests
```

Gerar imagem Docker

```bash
docker image build -t project101/demo-java .
```

Levantar um container com a imagem recém criada, usando o comando:

```
docker run -i --rm -p 8081:8080 --name p101_demo-java project101/demo-java
```

Rodar o docker compose:

```bash
docker-compose up --build -d
```
