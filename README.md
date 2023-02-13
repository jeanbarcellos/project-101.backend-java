# Projeto 101 (Backend em Java/Spring Boot)

Implementação do Backend do projeto [jeanbarcellos/project-101.frontend-reactjs](https://github.com/jeanbarcellos/project-101.frontend-reactjs)

<br>

## Pré-requisitos

- Java 11
- Maven 3.6.x
- Spring Boot 2.7.8

<br>

## Libs/Dependências utilizadas

### Compilação e Runtime

- Spring Boot Starter Web
  - Starter para construir aplicações web, incluindo RESTful, usando Spring MVC. Usa o Tomcat como o contêiner incorporado padrão
- Spring Boot Actuator
  - Oferece suporte a endpoints integrados (ou personalizados) que permitem monitorar e gerenciar seu aplicativo - como integridade do aplicativo, métricas, sessões, etc.
- Spring Data JPA
  - Persista dados em armazenamentos SQL com Java Persistence API usando Spring Data e Hibernate.
- PostgreSQL Driver
  - Um driver JDBC e R2DBC que permite que programas Java se conectem a um banco de dados PostgreSQL usando código Java padrão e independente de banco de dados.
- Flyway Migration
  - Controle de versão para seu banco de dados para que você possa migrar de qualquer versão (incluindo um banco de dados vazio) para a versão mais recente do esquema
- Spring Security
  - Autenticação altamente personalizável e estrutura de controle de acesso para aplicativos Spring
- Validation
  - Validação de bean com validador Hibernate
- Lombok
  - Biblioteca de anotações Java que ajuda a reduzir o código veboso
- Spring Boot DevTools
  - Fornece reinicializações rápidas de aplicativos, LiveReload e configurações para uma experiência de desenvolvimento aprimorada.
- Springdoc OpenAPI [[Doc](https://springdoc.org/)]
  - Ajuda a automatizar a geração de documentação da API
- Java JWT [[Doc](https://github.com/jwtk/jjwt)]
  - Implementação Java pura baseada exclusivamente nas especificações JWT , JWS , JWE , JWK e JWA

### Teste

- Spring Boot Starter Test
  - Starter para testar aplicativos Spring Boot com bibliotecas, incluindo `JUnit Jupiter`, `Hamcrest` e `Mockito`
- JaCoCo [[Doc](https://www.eclemma.org/index.html)]
  - Geração de relatório de cobertura de testes
- Java Faker [[Doc](https://github.com/DiUS/java-faker)]
  - Gerador de dados Fakes para testes

<br>

## Estrutura do projeto

```bash
/src/com/jeanbarcellos
  /core                     # Projeto - Classes bases e implementações comuns para melhorar a construção do MS
    # ...
  /demo                     # Projeto - Exclusivo do Microserviço
    /application            # Camada de Aplicação
      /dto                  # Objetos de transferência de dados entre camadas
      /mappers              # Objetos que fazem o mapeamento entre objetos DTO para objetos de Domínio e vice-versa
      /services             # Serviços da Applicação
    /config                 # Configurações em geral
    /domain                 # Camada de Domínio
      /entities             # Entidades
      /enums                # Enums
      /repositories         # Objetos de acesso a dados utilizando Repository Pattern
    /web                    # Camada WEB
      /controllers          # Controladores / Endpoints do Microserviço
      /exceptions           # Exceções / Manipuladores de exceções
      /filters              # Filtros WEB
    DemoApplication.java    # Entrypoint main
```

<br>

## Executar o Projeto

Comando:

```bash
mvn spring-boot:run
```

Acessar no navegador

http://localhost:8081

<br>

## Cobertura de Testes

Para gerar o Relatório de cobertura de código rodar o comando `verify`

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
