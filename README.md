# Projeto 101 (Backend em Java/Spring Boot)

Implementação do Backend do projeto [jeanbarcellos/project-101.frontend-reactjs](https://github.com/jeanbarcellos/project-101.frontend-reactjs)

<br>

## Pré-requisitos

- Java 11
- Maven 3.6.x
- Spring Boot 2.7.5

<br>

## Executar o Projeto

Comando:

```bash
mvn spring-boot:run
```

<br>

## Cobertura de Testes

Executar o abaixo

```bash
mvn clean verify
```

Acessar no navegador a URL

```
<local-do-projeto>/target/site/jacoco/index.html
```

[Link alternativo](target/site/jacoco/index.html)

<br>

## Documentação da API

Acesse o Swagger:

http://localhost:8080/swagger-ui/index.html

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
docker network create project101_net
```

Gerar o pacote

```bash
mvn clean package -DskipTests
```

Gerar imagem Docker

```bash
docker image build -t jeanbarcellos/project101_backend-java .
```

Levantar um container com a imagem recém criada, usando o comando:

```
docker run -i --rm -p 8081:8080 --network project101_net --name project101_backend-java jeanbarcellos/project101_backend-java
```
