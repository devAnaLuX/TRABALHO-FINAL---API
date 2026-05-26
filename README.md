# 🎬 SerratecFlix API

<div align="center">

## Plataforma de Filmes, Séries e Animes

Uma API RESTful desenvolvida com **Java + Spring Boot** inspirada em plataformas de streaming.

</div>

---

# 📖 Sobre o Projeto

O **SerratecFlix** é uma API RESTful desenvolvida para gerenciamento de conteúdos de streaming, permitindo que usuários interajam com filmes, séries e animes de forma completa.

A plataforma oferece funcionalidades como:

* 🎥 Avaliação de filmes e séries
* ⭐ Favoritar conteúdos
* 📋 Criação de listas personalizadas
* 👀 Gerenciamento de watchlists
* 🏷️ Organização por categorias
* 🔐 Autenticação segura com JWT
* 🌐 Consumo de APIs externas

---

# 🚀 Tecnologias Utilizadas

## 🔧 Backend

* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate
* Maven

## 🗄️ Banco de Dados

* PostgreSQL

## 🔐 Segurança

* Spring Security
* JWT Authentication
* BCrypt Password Encoder

## 📚 Documentação

* Swagger / OpenAPI

## ✅ Validações

* Bean Validation

## 🛠️ Ferramentas

* Git
* GitHub
* Postman
* VS Code / IntelliJ IDEA

---

# 🏗️ Arquitetura do Projeto

O projeto segue arquitetura em camadas:

```bash
src/main/java
│
├── config
├── controller
├── domain
├── dto
│   ├── request
│   └── response
├── exception
├── repository
├── security
├── service
└── util
```

---

# 📦 Entidades do Sistema

## 👤 Usuario

| Campo       | Descrição                |
| ----------- | ------------------------ |
| id          | Identificador único      |
| nome        | Nome do usuário          |
| email       | Email do usuário         |
| username    | Nome de usuário único    |
| senha       | Senha criptografada      |
| fotoPerfil  | Foto de perfil opcional  |
| dataCriacao | Data de criação da conta |

---

## 🎬 Filme

| Campo                   | Descrição            |
| ----------------------- | -------------------- |
| id                      | Identificador único  |
| titulo                  | Nome do filme        |
| descricao               | Sinopse              |
| duracao                 | Duração em minutos   |
| dataLancamento          | Data de lançamento   |
| classificacaoIndicativa | Classificação etária |
| notaMedia               | Média das avaliações |

---

## 📺 Serie

| Campo          | Descrição                |
| -------------- | ------------------------ |
| id             | Identificador único      |
| titulo         | Nome da série            |
| descricao      | Sinopse                  |
| temporadas     | Quantidade de temporadas |
| episodios      | Total de episódios       |
| dataLancamento | Data de lançamento       |
| notaMedia      | Média das avaliações     |

---

## 🏷️ Categoria

| Campo     | Descrição              |
| --------- | ---------------------- |
| id        | Identificador único    |
| nome      | Nome da categoria      |
| descricao | Descrição da categoria |

---

## ⭐ Avaliações

| Campo         | Descrição             |
| ------------- | --------------------- |
| id            | Identificador único   |
| nota          | Nota entre 0 e 10     |
| comentario    | Comentário do usuário |
| dataAvaliacao | Data da avaliação     |

---

## 📋 ListaFavoritos

| Campo       | Descrição           |
| ----------- | ------------------- |
| id          | Identificador único |
| nomeLista   | Nome da lista       |
| privada     | Define visibilidade |
| dataCriacao | Data de criação     |

---

# 🔗 Relacionamentos

| Entidade A     | Relacionamento | Entidade B     |
| -------------- | -------------- | -------------- |
| Usuario        | OneToMany      | AvaliacaoFilme |
| Usuario        | OneToMany      | AvaliacaoSerie |
| Filme          | ManyToMany     | Categoria      |
| Serie          | ManyToMany     | Categoria      |
| Usuario        | OneToMany      | ListaFavoritos |
| ListaFavoritos | ManyToMany     | Filme          |
| ListaFavoritos | ManyToMany     | Serie          |

---

# 🔐 Segurança

A API possui:

* ✅ Autenticação com JWT
* ✅ Login seguro
* ✅ Rotas protegidas
* ✅ Criptografia de senha
* ✅ BCrypt Password Encoder

---

# 📚 Swagger / OpenAPI

Após iniciar a aplicação, acesse:

```bash
http://localhost:8080/swagger-ui/index.html
```

---

# ⚙️ Como Executar o Projeto

## ✅ Pré-requisitos

* Java 17
* PostgreSQL
* Maven
* IDE de preferência

---

## 🗄️ Configuração do Banco de Dados

Crie o banco:

```sql
CREATE DATABASE serratecflix;
```

Configure o arquivo:

```properties
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/serratecflix
spring.datasource.username=postgres
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## ▶️ Executando o Projeto

Clone o repositório:

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
```

Entre na pasta:

```bash
cd serratecflix
```

Execute:

```bash
mvn spring-boot:run
```

---

# 🌐 Endpoints Principais

## 👤 Usuários

| Método | Endpoint       |
| ------ | -------------- |
| GET    | /usuarios      |
| GET    | /usuarios/{id} |
| POST   | /usuarios      |
| PUT    | /usuarios/{id} |
| DELETE | /usuarios/{id} |

---

## 🎬 Filmes

| Método | Endpoint     |
| ------ | ------------ |
| GET    | /filmes      |
| GET    | /filmes/{id} |
| POST   | /filmes      |
| PUT    | /filmes/{id} |
| DELETE | /filmes/{id} |

---

## 📺 Séries

| Método | Endpoint     |
| ------ | ------------ |
| GET    | /series      |
| GET    | /series/{id} |
| POST   | /series      |
| PUT    | /series/{id} |
| DELETE | /series/{id} |

---

# ✅ Funcionalidades Implementadas

* CRUD completo
* DTO Request / Response
* Tratamento global de exceções
* Bean Validation
* JWT Authentication
* Swagger / OpenAPI
* Relacionamentos JPA
* Consumo de API externa
* Queries personalizadas
* Banco pré-populado

---

# ⚠️ Tratamento de Exceções

A aplicação utiliza:

* `@ControllerAdvice`
* Exceptions customizadas
* Respostas padronizadas
* Status HTTP adequados

| Código | Descrição              |
| ------ | ---------------------- |
| 400    | Dados inválidos        |
| 401    | Não autorizado         |
| 404    | Recurso não encontrado |
| 409    | Conflito de dados      |

---

# 📌 Diferenciais do Projeto

✨ Arquitetura profissional

✨ Segurança com JWT

✨ Código organizado em camadas

✨ Estrutura escalável

✨ Projeto inspirado em plataformas reais de streaming

✨ API documentada com Swagger

---

# 👨‍💻 Integrantes

* Adriane Tranhaqui
* Ana Luisa
* Melissa Lima
* Raquel Taveira
* Vitória de Paula


---

# 📄 Licença

Projeto desenvolvido para fins educacionais no programa Serratec.

---

# 💡 Considerações Finais

O **SerratecFlix** foi desenvolvido aplicando conceitos reais de desenvolvimento backend com foco em:

* Arquitetura em camadas
* Persistência de dados
* Segurança com JWT
* Tratamento de exceções
* Organização profissional de código
* APIs RESTful
* Boas práticas com Spring Boot

🚀 Projeto desenvolvido para aprendizado e evolução técnica.
