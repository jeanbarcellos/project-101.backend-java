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
| root    | Perfil do desenvolvedor/empresa. gerencia TUDO, configurações, roles e utilitários.      |
| admin   | Inclui, exclui, atualiza e visualiza usuários, perfils e outras funções administrativas. |
| default | Usuário padrão utilizador do sistema                                                     |

**Usuários**

| Login                | Senha     | Perfil  |
| -------------------- | --------- | ------- |
| root@teste.com       | teste@123 | root    |
| admin001@teste.com   | teste@123 | admin   |
| usuario001@teste.com | teste@123 | default |

<br>

## Docker

Criar rede:

```bash
docker network create project101_net
```

### **Build da imagem**

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

### **Build da imagem - Multi stage**

```bash
docker image build -f Dockerfile.multistage -t jeanbarcellos/project101_backend-java_tst .
```

### **Developement**

Executar o container

```bash
# Opção 1

docker run -it --rm -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven --name my-maven-project maven:3.6.3-jdk-11-slim bash

docker run -it --rm \
  --network project101_net \
  -p 8080:8080 \
  -v "$(pwd)":/usr/src/mymaven \
  -w /usr/src/mymaven \
  --name my-maven-project \
  maven:3.6.3-jdk-11-slim bash


# Opção 2 - Fazendo cache local

docker volume create --name maven_repo

docker run -it --rm \
  --network project101_net \
  -p 8080:8080 \
  -e DB_HOST=database \
  -e DB_PORT=5432 \
  -v "maven_repo":/root/.m2 \
  -v "$PWD":/usr/src/mymaven  \
  -v "$PWD/target":/usr/src/mymaven/target \
  -w /usr/src/mymaven \
  --name my-maven-project \
  maven:3.6.3-jdk-11-slim bash


# Opção 3 -  Rodar usando diretório de cache .m2 comparilhado

docker run -it --rm \
  --network project101_net \
  -p 8080:8080 \
  -e DB_HOST=database \
  -e DB_PORT=5432 \
  -v "$HOME/.m2":/root/.m2 \
  -v "$PWD":/usr/src/mymaven  \
  -v "$PWD/target:/usr/src/mymaven/target" \
  -w /usr/src/mymaven \
  --name my-maven-project \
  maven:3.6.3-jdk-11-slim bash
```
